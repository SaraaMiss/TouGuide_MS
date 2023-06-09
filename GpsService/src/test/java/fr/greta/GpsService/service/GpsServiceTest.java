package fr.greta.GpsService.service;

import fr.greta.GpsService.exception.ElementNotFoundException;
import fr.greta.GpsService.model.location.VisitedLocation;
import gpsUtil.GpsUtil;
import gpsUtil.location.Attraction;
import gpsUtil.location.Location;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.time.Instant;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
@ActiveProfiles("test")
@ExtendWith(SpringExtension.class)
public class GpsServiceTest {

    @Mock
    private GpsUtil gpsUtil;
    private static GpsService gpsService;
    private static gpsUtil.location.Attraction attraction1;
    private static gpsUtil.location.Attraction attraction2;
    private static gpsUtil.location.Attraction attraction3;
    private static gpsUtil.location.Attraction attraction4;
    private static gpsUtil.location.Attraction attraction5;
    private static gpsUtil.location.Attraction attraction6;
    private static gpsUtil.location.Attraction attraction7;
    private static gpsUtil.location.Attraction attraction8;
    private static gpsUtil.location.Attraction attraction9;
    private static gpsUtil.location.Attraction attraction10;

    private static VisitedLocation visitedLocation;
    private static UUID uuid;

    private static List<Attraction> attractionList = new ArrayList<gpsUtil.location.Attraction>();


    @BeforeEach
    void beforeEach() {
        gpsService = new GpsServiceImpl(gpsUtil);
        attraction1 = new gpsUtil.location.Attraction("name1", "city1", "state1", 1d, 1d);
        attraction2 = new gpsUtil.location.Attraction("name2", "city2", "state2", 2d, 2d);
        attraction3 = new gpsUtil.location.Attraction("name3", "city3", "state3", 3d, 3d);
        attraction4 = new gpsUtil.location.Attraction("name4", "city4", "state4", 4d, 4d);
        attraction5 = new gpsUtil.location.Attraction("name5", "city5", "state5", 5d, 5d);
        attraction6 = new gpsUtil.location.Attraction("name6", "city6", "state6", 6d, 6d);
        attraction7 = new gpsUtil.location.Attraction("name7", "city7", "state7", 7d, 7d);
        attraction8 = new gpsUtil.location.Attraction("name8", "city8", "state8", 8d, 8d);
        attraction9 = new gpsUtil.location.Attraction("name9", "city9", "state9", 9d, 9d);
        attraction10 = new gpsUtil.location.Attraction("name10", "city10", "state10", 10d, 10d);
        uuid = UUID.randomUUID();
        visitedLocation = new VisitedLocation(uuid, new Location(2d, 2d), Date.from(Instant.now()));
        attractionList.clear();
        attractionList.add(attraction1);
        attractionList.add(attraction2);
        attractionList.add(attraction3);
        attractionList.add(attraction4);
        attractionList.add(attraction5);
        attractionList.add(attraction6);
        attractionList.add(attraction7);
        attractionList.add(attraction8);
        attractionList.add(attraction9);
        attractionList.add(attraction10);
    }

    // GET ATTRACTION TESTS //

    @Test
    void getExistingAttractionTest() {
        when(gpsUtil.getAttractions()).thenReturn(attractionList);
        assertEquals(attraction1.attractionName, gpsService.getAttraction(attraction1.attractionName).getAttractionName());
    }

    @Test
    void getNonExistentAttractionTest() {
        when(gpsUtil.getAttractions()).thenReturn(new ArrayList<>());
        assertThrows(ElementNotFoundException.class, () -> gpsService.getAttraction(attraction1.attractionName));
    }

    // GET ALL ATTRACTIONS TESTS //

    @Test
    void getAllAttractionsTest() {
        when(gpsUtil.getAttractions()).thenReturn(attractionList);
        assertEquals(10, gpsService.getAllAttractions().size());
    }

    // GET USER ACTUAL LOCATION TESTS //

//    @Test
//    void getUserActualLocationTest() {
//        when(gpsUtil.getUserLocation(any(UUID.class)).thenReturn(visitedLocation);
//        assertEquals(uuid, gpsService.getUserActualLocation(uuid).getUserId());
//    }
}
