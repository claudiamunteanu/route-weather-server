package main.service;

import main.domain.City;
import org.json.JSONArray;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.time.*;
import java.util.Properties;

@Service
public class ForecastService {
    Properties properties;

    @Autowired
    public ForecastService() throws IOException {
        properties = new Properties();
        String propFileName = "app.properties";

        InputStream inputStream = getClass().getClassLoader().getResourceAsStream(propFileName);

        if (inputStream != null) {
            properties.load(inputStream);
        } else {
            throw new FileNotFoundException("property file '" + propFileName + "' not found in the classpath");
        }
    }

    public JSONObject getForecast(City city, LocalDateTime dateTime){
        String uri = properties.getProperty("HOURLY_FORECAST_URL");
        final String apiKey = properties.getProperty("WEATHER_API_KEY");

        uri = uri.replace("{lat}", city.getLatitude().toString());
        uri = uri.replace("{lon}", city.getLongitude().toString());
        uri = uri.replace("{api_key}", apiKey);

        JSONObject forecast = new JSONObject();
        RestTemplate restTemplate = new RestTemplate();
        try {
            String json = restTemplate.getForObject(uri, String.class);
            JSONObject obj = new JSONObject(json);
            JSONArray hourlyForecast = obj.getJSONArray("list");
            forecast = hourlyForecast.getJSONObject(0);

            for(int i=0; i<hourlyForecast.length(); i++){
                JSONObject prevForecast = hourlyForecast.getJSONObject(i);
                JSONObject nextForecast = hourlyForecast.getJSONObject(i+1);

                LocalDateTime prevDateTime = LocalDateTime.ofInstant(Instant.ofEpochMilli(prevForecast.getLong("dt")*1000), ZoneId.systemDefault());
                LocalDateTime nextDateTime = LocalDateTime.ofInstant(Instant.ofEpochMilli(nextForecast.getLong("dt")*1000), ZoneId.systemDefault());

                if(prevDateTime.isBefore(dateTime) && nextDateTime.isAfter(dateTime)){
                    if(Duration.between(prevDateTime, dateTime).getSeconds() < Duration.between(dateTime, nextDateTime).getSeconds()){
                        forecast = prevForecast;
                    } else {
                        forecast = nextForecast;
                    }
                    break;
                }
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return forecast;
    }
}
