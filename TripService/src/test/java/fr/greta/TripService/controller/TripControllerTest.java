package fr.greta.TripService.controller;

import fr.greta.TripService.model.user.User;
import fr.greta.TripService.model.user.UserPreferences;
import fr.greta.TripService.service.TripService;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;
import tripPricer.Provider;


import java.time.Instant;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.UUID;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ActiveProfiles("test")
@WebMvcTest(TripController.class)
public class TripControllerTest {


    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private TripService tripService;

    private static User user;
    private  static List<Provider> providerList;
    private static  final UUID uuid1 = UUID.randomUUID();

    @BeforeAll
    static void beforeAll() {
        providerList = new ArrayList<>();
        user = new User(uuid1, "userName", "phoneNumber", "emailAddress",
                Date.from(Instant.now()), new ArrayList<>(), new ArrayList<>(),new UserPreferences(), providerList);
    }

    @Test
    void getTripDealsTest() throws Exception {
        when(tripService.getTripDeals(any(UUID.class), anyInt(), anyInt(), anyInt(), anyInt())).thenReturn(providerList);
        mockMvc.perform(get("/trips/" + user.getUsername()
                        + "?userId=" + user.getUserId()
                        + "&numberOfAdults=" + user.getUserPreferences().getNumberOfAdults()
                        + "&numberOfChildren=" + user.getUserPreferences().getNumberOfChildren()
                        + "&tripDuration=" + user.getUserPreferences().getTripDuration()
                        + "&cumulativeRewardPoints=" + 2))
                .andExpect(status().isOk());
    }
}
