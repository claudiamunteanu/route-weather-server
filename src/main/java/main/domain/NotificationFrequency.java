package main.domain;

import com.fasterxml.jackson.annotation.JsonValue;

import java.util.HashMap;
import java.util.Map;

public enum NotificationFrequency {
    ONE_DAY(1),
    THREE_DAYS(3),
    FIVE_DAYS(5),
    SEVEN_DAYS(7);

    private final int value;
    private static final Map<Integer, NotificationFrequency> BY_VALUE = new HashMap<>();

    NotificationFrequency(final int value) {
        this.value = value;
    }

    static {
        for (NotificationFrequency e: values()) {
            BY_VALUE.put(e.value, e);
        }
    }

    public static NotificationFrequency getByValue(int value) {
        return BY_VALUE.get(value);
    }

    @JsonValue
    public int getValue() { return value; }


}
