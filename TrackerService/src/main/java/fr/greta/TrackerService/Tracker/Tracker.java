package fr.greta.TrackerService.Tracker;

import fr.greta.TrackerService.executer.TrackerExecutor;
import fr.greta.TrackerService.service.UserService;
import fr.greta.TrackerService.models.user.User;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.apache.commons.lang3.time.StopWatch;

import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.TimeUnit;

public class Tracker implements Runnable{

    private static final Logger LOGGER = LoggerFactory.getLogger(Tracker.class);
    private  final UserService userService;
    private  final TrackerExecutor trackerExecutor;

    public Tracker(final UserService userService,final TrackerExecutor trackerExecutor) {
        this.userService = userService;
        this.trackerExecutor = trackerExecutor;
    }


    @Override
    public void run() {
        StopWatch stopWatch = new StopWatch();
        List<User> users = userService.getAllUsers();

        LOGGER.debug("begin tracker n. tracking " +users.size() + " users.");

        stopWatch.start();

        CompletableFuture<?>[] comparableFutures = users.stream()
                .map(trackerExecutor::trackUserLocation)
                .toArray(CompletableFuture[]::new);

        CompletableFuture.allOf(comparableFutures).join();

        stopWatch.stop();

        LOGGER.debug("Tracker Time Elapsed: "
        + TimeUnit.MILLISECONDS.toSeconds(stopWatch.getTime()) + " seconds. ");

        stopWatch.reset();

        trackerExecutor.addShutDownHook();

    }
}
