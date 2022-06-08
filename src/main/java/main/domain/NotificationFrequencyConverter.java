package main.domain;

import javax.persistence.AttributeConverter;
import javax.persistence.Converter;

@Converter
public class NotificationFrequencyConverter implements AttributeConverter<NotificationFrequency, Integer> {
    @Override
    public Integer convertToDatabaseColumn(NotificationFrequency attribute) {
        if (attribute == null)
            return null;

        return attribute.getValue();
    }

    @Override
    public NotificationFrequency convertToEntityAttribute(Integer dbData) {
        if (dbData == null)
            return null;

        return NotificationFrequency.getByValue(dbData);
    }
}