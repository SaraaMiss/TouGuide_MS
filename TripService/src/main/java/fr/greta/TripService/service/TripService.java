package fr.greta.TripService.service;

import tripPricer.Provider;

import java.util.List;
import java.util.UUID;

public interface TripService {


    List<Provider> getTripDeal(UUID userId,int numberOfAdults,int numberOfChildren,int tripDuration ,int cumulativeRewardPoints);
}
