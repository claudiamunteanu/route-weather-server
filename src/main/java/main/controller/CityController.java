package main.controller;

import main.domain.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import main.service.CityService;

import java.net.URLDecoder;
import java.nio.charset.StandardCharsets;
import java.util.Objects;

@CrossOrigin
@RestController
@RequestMapping("/prognozaMeteo/cities")
public class CityController extends Controller
{
    @Autowired
    CityService cityService;

    @RequestMapping(value = "/closestCity", method = RequestMethod.GET)
    public ResponseEntity<?> getLocationCity(@RequestParam(value = "lat") double latitude, @RequestParam(value = "long") double longitude) {
        City city = cityService.getLocationCity(latitude, longitude);
        return this.processData(city, "", HttpStatus.OK);
    }

    @RequestMapping(value = "/city", method = RequestMethod.GET)
    public ResponseEntity<?> getCityByName(@RequestParam(value = "name") String encodedName, @RequestParam String language) {
        String name = URLDecoder.decode(encodedName, StandardCharsets.UTF_8);
        City city = cityService.findByName(name);

        String message = "";
        switch (language){
            case "english" -> message = "Could not find data for city " + name + "!";
            case "romanian" -> message = "Nu s-au putut găsit date pentru orașul " + name + "!";
        }
        return this.processData(city, message, HttpStatus.NOT_FOUND);
    }

    @Override
    void initializeCondition() {
        super.condition = Objects::nonNull;
    }

    @Override
    ResponseEntity<?> goodResponse(Object city) {
        return new ResponseEntity<>(city, HttpStatus.OK);
    }

    @Override
    ResponseEntity<?> badResponse(String message, HttpStatus status) {
        if (message.isEmpty()){
            return new ResponseEntity<>(new EmptyBodyJson(), status);
        } else {
            return new ResponseEntity<>(new MessageJson(message), status);
        }
    }
}


