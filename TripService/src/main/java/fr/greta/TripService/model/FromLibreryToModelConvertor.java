package fr.greta.TripService.model;


import tripPricer.Provider;

public class FromLibreryToModelConvertor {



    public static Provider convertProvider(tripPricer.Provider libraryProvider){
        return  new Provider(libraryProvider.tripId, libraryProvider.name, libraryProvider.price);
    }
}
