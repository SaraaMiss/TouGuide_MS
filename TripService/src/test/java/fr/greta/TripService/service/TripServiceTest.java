package fr.greta.TripService.service;
import fr.greta.TripService.model.user.User;
import fr.greta.TripService.model.user.UserPreferences;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import tripPricer.Provider;

import java.time.Instant;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

@ActiveProfiles("test")
@ExtendWith(SpringExtension.class)
public class TripServiceTest {

    private static TripService tripService;
    private static User user;
    private static  final UUID uuid1 = UUID.randomUUID();

    @BeforeEach
    private void beforeEach(){
        tripService = new TripServiceImpl();
        user = new User(uuid1,"userName","phoneNumber","emailAddress",
                Date.from(Instant.now()),new ArrayList<>(),new ArrayList<>(),new UserPreferences(),new ArrayList<>());
    }

    @Test
    void getTripDealsTest(){
        List<Provider> providers = tripService.getTripDeals(user.getUserId(),user.getUserPreferences().getNumberOfAdults(),
                user.getUserPreferences().getNumberOfChildren(),user.getUserPreferences().getTripDuration(),2);
        assertNotNull(providers);
        assertEquals(5, providers.size());
    }

}
