package main.service;

import main.domain.City;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import main.persistence.CityRepository;

import java.util.List;

@Service
public class CityService {
    @Autowired
    CityRepository cityRepository;

    final private int DISTANCE_LIMIT = 10;

    private double deg2rad(double deg) {
        return deg * (Math.PI / 180);
    }

    //Haversine formula
    private double distanceInKm(double lat1, double long1, double lat2, double long2) {
        final int R = 6371;
        double dLat = deg2rad(lat2 - lat1);
        double dLong = deg2rad(long2 - long1);
        double a = Math.sin(dLat / 2) * Math.sin(dLat / 2) +
                Math.cos(deg2rad(lat1)) * Math.cos(deg2rad(lat2)) *
                        Math.sin(dLong / 2) * Math.sin(dLong / 2);
        double c = 2 * Math.atan2(Math.sqrt(a), Math.sqrt(1-a));
        return R * c;
    }

    public City getLocationCity(double lat, double lon){
        City closestCity = null;
        double smallestDistance = DISTANCE_LIMIT;
        List<City> cities = cityRepository.findAll();
        for(City city : cities){
            double distance = distanceInKm(city.getLatitude(), city.getLongitude(), lat, lon);
            if(distance<=smallestDistance)
            {
                closestCity = city;
                smallestDistance = distance;
            }
        }
        return closestCity;
    }

    public City findByName(String name){
        return cityRepository.findByName(name);
    }
}
