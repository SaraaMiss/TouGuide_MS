package fr.greta.TrackerService.executer;

import fr.greta.TrackerService.service.LocationService;
import fr.greta.TrackerService.service.UserService;
import fr.greta.TrackerService.models.user.User;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;
@Service
public class TrackerExecutorImpl implements TrackerExecutor {

    private static final Logger LOGGER = LoggerFactory.getLogger(TrackerExecutorImpl.class);

    private final LocationService locationService;
    private final UserService userService;
    private final RewardExecutor rewardExecutor;
    private final ExecutorService executor;

    public TrackerExecutorImpl(final LocationService locationService1, final UserService userService1, final RewardExecutor rewardExecutor1) {
       locationService = locationService1;
        userService = userService1;
       rewardExecutor = rewardExecutor1;
       executor = Executors.newFixedThreadPool(100);
    }


    @Override
    public CompletableFuture<?> trackUserLocation(User user) {
        LOGGER.info("Tracking user location for user "+user);
        return CompletableFuture.supplyAsync(() -> locationService.getUserLocation(user.getUserId()),executor)
                .thenAccept(visitedLocation -> userService.addToVisitedLocationsOfUser(visitedLocation,user))
                .thenRunAsync(() -> rewardExecutor.calculateRewards(user))
                ;
    }

    @Override
    public void addShutDownHook() {

        LOGGER.info("Tracker Executor is shutting down");
        try{
            if(!executor.awaitTermination(10, TimeUnit.SECONDS)){
                executor.shutdownNow();
                rewardExecutor.addShutDownHook();
            }
        }catch (InterruptedException e){
            e.printStackTrace();
            executor.shutdownNow();
            Thread.currentThread().interrupt();
        }

    }
}
