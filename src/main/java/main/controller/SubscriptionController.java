package main.controller;

import main.domain.EmptyBodyJson;
import main.domain.MessageJson;
import main.domain.Subscription;
import main.service.ServiceException;
import main.service.SubscriptionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URLDecoder;
import java.nio.charset.StandardCharsets;

@CrossOrigin
@RestController
@RequestMapping("/prognozaMeteo/subscription")
public class SubscriptionController extends Controller {
    @Autowired
    SubscriptionService subscriptionService;

    @RequestMapping(method = RequestMethod.POST, value = "/subscribe")
    public ResponseEntity<?> addSubscription(@RequestBody Subscription newSubscription, @RequestParam String language) {
        try{
            newSubscription = subscriptionService.addSubscription(newSubscription, language);
            return new ResponseEntity<>(newSubscription, HttpStatus.OK);
        } catch (ServiceException e) {
            return new ResponseEntity<>(new MessageJson(e.getMessage()), HttpStatus.BAD_REQUEST);
        }
    }

    @RequestMapping(method = RequestMethod.DELETE, value = "/unsubscribe")
    public ResponseEntity<?> deleteSubscription(@RequestParam(value = "email") String emailEncoded,
                                   @RequestParam(value = "startCity") String startingCityEncoded,
                                   @RequestParam(value = "destinationCity") String destinationCityEncoded,
                                   @RequestParam(value = "hash") String hashEncoded,
                                                @RequestParam String language) {
        String email = URLDecoder.decode(emailEncoded, StandardCharsets.UTF_8);
        String startingCity = URLDecoder.decode(startingCityEncoded, StandardCharsets.UTF_8);
        String destinationCity = URLDecoder.decode(destinationCityEncoded, StandardCharsets.UTF_8);
        String hash = URLDecoder.decode(hashEncoded, StandardCharsets.UTF_8);
        int deletedRows = subscriptionService.deleteSubscription(email, startingCity, destinationCity, hash);

        String message = "";
        switch (language) {
            case "english" -> message = "Could not unsubscribe! Please try again later!";
            case "romanian" -> message = "Nu s-a putut face dezabonarea! Vă rugăm să încercați mai târziu!";
        }
        return this.processData(deletedRows,message, HttpStatus.BAD_REQUEST);
    }


    @Override
    void initializeCondition() {
        condition = object -> Integer.parseInt(object.toString())==1;
    }

    @Override
    ResponseEntity<?> goodResponse(Object object) {
        return new ResponseEntity<>(new EmptyBodyJson(), HttpStatus.OK);
    }

    @Override
    ResponseEntity<?> badResponse(String message, HttpStatus status) {
        return new ResponseEntity<>(new MessageJson(message), status);
    }
}
