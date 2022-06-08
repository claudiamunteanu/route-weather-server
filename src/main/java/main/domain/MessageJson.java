package main.domain;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;

@JsonSerialize
public class MessageJson {
    private String message;

    public MessageJson(String message) {
        this.message = message;
    }

    public MessageJson() {
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
