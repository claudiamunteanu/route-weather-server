package main.controller;

import main.domain.MessageJson;
import main.domain.User;
import main.service.ServiceException;
import main.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@CrossOrigin
@RestController
@RequestMapping("/prognozaMeteo")
public class UserController {
    @Autowired
    UserService userService;

    @PostMapping(value = "/auth")
    public ResponseEntity<?> login(@RequestBody User user, @RequestParam String language) {
        try{
            User loggedUser = userService.login(user, language);
            return new ResponseEntity<>(loggedUser, HttpStatus.OK);
        }catch (ServiceException ex){
            return new ResponseEntity<>(new MessageJson(ex.getMessage()), HttpStatus.BAD_REQUEST);
        }
    }
    @RequestMapping(value = "/newUser", method = RequestMethod.POST)
    public ResponseEntity<?> addUser(@RequestBody User newUser, @RequestParam String language) {
        try{
            User user = userService.addUser(newUser, language);
            return new ResponseEntity<>(user, HttpStatus.OK);
        }catch (ServiceException ex){
            return new ResponseEntity<>(new MessageJson(ex.getMessage()), HttpStatus.BAD_REQUEST);
        }
    }

}
