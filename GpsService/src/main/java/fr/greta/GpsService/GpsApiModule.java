package fr.greta.GpsService;

import gpsUtil.GpsUtil;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class GpsApiModule {


    @Bean
    public GpsUtil getGpsUtil(){
        return  new GpsUtil();
    }
}
