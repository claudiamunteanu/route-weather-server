package main.controller;

import main.domain.*;
import main.service.DrivingTipService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@CrossOrigin
@RestController
@RequestMapping("/prognozaMeteo/drivingTips")
public class DrivingTipController {
    @Autowired
    DrivingTipService drivingTipService;

    @RequestMapping(value = "/filter", method = RequestMethod.GET)
    public List<DrivingTip> getAllByUsername(@RequestParam(name = "username") String username) {
        return drivingTipService.getAllByUsername(username);
    }

    @RequestMapping(value = "/save", method = RequestMethod.POST)
    public DrivingTip addDrivingTip(@RequestBody DrivingTip newDrivingTip, @RequestParam(name = "username") String username) {
        return drivingTipService.addDrivingTip(newDrivingTip, username);
    }

    @RequestMapping(value = "/delete", method = RequestMethod.DELETE)
    public ResponseEntity<?> deleteDrivingTip(@RequestParam long id, @RequestParam String language){
        int deletedRows = drivingTipService.deleteDrivingTip(id);
        if(deletedRows==1){
            return new ResponseEntity<>(new EmptyBodyJson(), HttpStatus.OK);
        } else {
            String message = "";
            switch (language) {
                case "english" -> message = "Could not delete driving tip";
                case "romanian" -> message = "Nu s-a putut șterge sfatul de condus";
            }
            return new ResponseEntity<>(new MessageJson(message), HttpStatus.BAD_REQUEST);
        }
    }

    @RequestMapping(method = RequestMethod.PUT)
    public ResponseEntity<?> updateDrivingTip(@RequestBody DrivingTip drivingTip, @RequestParam String language){
        try{
            DrivingTip updatedDrivingTip = drivingTipService.updateDrivingTip(drivingTip);
            return new ResponseEntity<>(updatedDrivingTip, HttpStatus.OK);
        }catch (Exception ex){
            String message = "";
            switch (language) {
                case "english" -> message = "Could not update!";
                case "romanian" -> message = "Nu s-a putut actualiza!";
            }
            return new ResponseEntity<>(new MessageJson(message), HttpStatus.BAD_REQUEST);
        }
    }

    @RequestMapping(value = "/undo", method = RequestMethod.PATCH)
    public ResponseEntity<?> undoDrivingTip(@RequestParam String language){
        try{
            boolean isUndoed = drivingTipService.undo();
            if(isUndoed){
                return new ResponseEntity<>(new EmptyBodyJson(), HttpStatus.OK);
            } else {
                String message = "";
                switch (language) {
                    case "english" -> message = "Could not undo!";
                    case "romanian" -> message = "Nu s-a putut face undo!";
                }
                return new ResponseEntity<>(new MessageJson(message), HttpStatus.BAD_REQUEST);
            }
        }catch (Exception ex){
            String message = "";
            switch (language) {
                case "english" -> message = "Could not undo!";
                case "romanian" -> message = "Nu s-a putut face undo!";
            }
            return new ResponseEntity<>(new MessageJson(message), HttpStatus.BAD_REQUEST);
        }
    }

    @RequestMapping(value = "/redo", method = RequestMethod.PATCH)
    public ResponseEntity<?> redoDrivingTip(@RequestParam String language){
        try{
                boolean isRedoed = drivingTipService.redo();
            if(isRedoed){
                return new ResponseEntity<>(new EmptyBodyJson(), HttpStatus.OK);
            } else {
                String message = "";
                switch (language) {
                    case "english" -> message = "Could not redo!";
                    case "romanian" -> message = "Nu s-a putut face redo!";
                }
                return new ResponseEntity<>(new MessageJson(message), HttpStatus.BAD_REQUEST);
            }
        }catch (Exception ex){
            String message = "";
            switch (language) {
                case "english" -> message = "Could not redo!";
                case "romanian" -> message = "Nu s-a putut face redo!";
            }
            return new ResponseEntity<>(new MessageJson(message), HttpStatus.BAD_REQUEST);
        }
    }
}