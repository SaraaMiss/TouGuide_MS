package fr.greta.GpsService.controller;

import fr.greta.GpsService.exception.ElementNotFoundException;
import fr.greta.GpsService.model.location.Attraction;
import fr.greta.GpsService.model.location.Location;
import fr.greta.GpsService.model.location.VisitedLocation;
import fr.greta.GpsService.service.GpsService;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;

import java.util.Date;
import java.time.Instant;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import static org.mockito.ArgumentMatchers.any;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ActiveProfiles("test")
@WebMvcTest(GpsController.class)
public class GpsControllerTest {

    @Autowired
    private MockMvc mockMvc;
   @MockBean
    private GpsService gpsService;
    private static Attraction attraction;
    private static List<Attraction> attractionList = new ArrayList<>();

    private static VisitedLocation visitedLocation;

    private static UUID uuid;



    @BeforeAll
    static  void beforeAll() {
        attraction = new Attraction("name","city","state",1d,1d);
        attractionList.add(attraction);
        uuid = UUID.randomUUID();
        visitedLocation = new VisitedLocation(uuid, new Location(2d, 2d), Date.from(Instant.now()));

    }

    //get attraction info tst //

    @Test
    void getAttractionWithExistingNameTest() throws Exception {
        when(gpsService.getAttraction(anyString()))
                .thenReturn(attraction);
        mockMvc.perform(get("/attractions/{attractionName}", attraction.getAttractionName()))
                .andExpect(status().isOk());
    }

    @Test
    void getAttractionWithNonExistentNameTest() throws Exception {
        when(gpsService.getAttraction(anyString())).thenThrow(ElementNotFoundException.class);
        mockMvc.perform(get("/attractions/{attractionName}", attraction.getAttractionName()))
                .andExpect(status().isNotFound());
    }

    // GET ALL ATTRACTIONS TESTS //

    @Test
    void getAllAttractionsTest() throws Exception {
        when(gpsService.getAllAttractions()).thenReturn(attractionList);
        mockMvc.perform(get("/attractions"))
                .andExpect(status().isOk());
    }

    // GET USER LOCATION TESTS //

    @Test
    void getUserLocationTest() throws Exception {
        when(gpsService.getUserActualLocation(any(UUID.class))).thenReturn(visitedLocation);
        mockMvc.perform(get("/location/" + uuid))
                .andExpect(status().isOk());
    }



}
