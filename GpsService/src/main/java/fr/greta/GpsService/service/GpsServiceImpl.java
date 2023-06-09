package fr.greta.GpsService.service;

import fr.greta.GpsService.exception.ElementNotFoundException;
import fr.greta.GpsService.model.FromLibraryToModelConvertor;
import fr.greta.GpsService.model.location.Attraction;
import fr.greta.GpsService.model.location.VisitedLocation;
import gpsUtil.GpsUtil;

import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Locale;
import java.util.UUID;
import org.slf4j.Logger;
import java.util.stream.Collectors;

@Service
public class GpsServiceImpl implements GpsService{
    private  static  final Logger LOGGER = LoggerFactory.getLogger(GpsServiceImpl.class);
    private final GpsUtil gpsUtil;

//constructeur avc parametres
    public GpsServiceImpl(final GpsUtil gpsUtil1) {
       this.gpsUtil = gpsUtil1;
        Locale.setDefault(Locale.US);
    }

    @Override
    public Attraction getAttraction(final String attractionName) {
        LOGGER.info("Search for attraction with name " + attractionName);
        return FromLibraryToModelConvertor.convertAttraction(gpsUtil.getAttractions().stream()
                .filter(attraction -> attractionName.equals(attraction.attractionName))
                .findAny()
                .orElseThrow(() -> new ElementNotFoundException("Attraction with name : " + attractionName + " was not found.")));
    }

    @Override
    public List<Attraction> getAllAttractions() {
        LOGGER.info("Getting all referenced attraction");
        return gpsUtil.getAttractions()
                .stream()
                .map(FromLibraryToModelConvertor::convertAttraction)
                .collect(Collectors.toList());
    }

    @Override
    public VisitedLocation getUserActualLocation(final UUID userId) {
        LOGGER.info("Getting user "+ userId + " actual location with gps .");
        return FromLibraryToModelConvertor.convertVisitedLocation(gpsUtil.getUserLocation(userId));
    }
}
