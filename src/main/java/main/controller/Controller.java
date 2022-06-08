package main.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.function.Predicate;

public abstract class Controller{
    Predicate<Object> condition;

    abstract void initializeCondition();

    abstract ResponseEntity<?> goodResponse(Object object);

    abstract ResponseEntity<?> badResponse(String message, HttpStatus status);

    //template method
    public final ResponseEntity<?> processData(Object object, String message, HttpStatus status) {
        initializeCondition();

        if(condition.test(object)){
            return goodResponse(object);
        } else {
            return badResponse(message, status);
        }
    }
}
