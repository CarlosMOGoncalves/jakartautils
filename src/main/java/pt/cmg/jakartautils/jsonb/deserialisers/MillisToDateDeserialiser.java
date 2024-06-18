/**
 * Copyright (c) 2024 Carlos Gonçalves (https://www.linkedin.com/in/carlosmogoncalves/)
 * Likely open-source, so copy at will, bugs will be yours as well.
 */
package pt.cmg.jakartautils.jsonb.deserialisers;

import java.lang.reflect.Type;
import java.util.Date;
import jakarta.json.bind.serializer.DeserializationContext;
import jakarta.json.bind.serializer.JsonbDeserializer;
import jakarta.json.stream.JsonParser;
import pt.cmg.jakartautils.jsonb.serialisers.DateToMillisSerialiser;

/**
 * 
 * NOTE: Please don't use java.util.Date anymore. It has been superseded by java.time classes.
 * 
 * This deserialiser is used for one and only one reason, which is to convert a date represented
 * in milliseconds (a Long data type) to a Date.
 * <br>
 * <br>
 * It is the counterpart to {@link DateToMillisSerialiser}..
 * <br>
 * <br>
 * Note that this effectively overrides the default Deserializer so it pretty much disregards any additional
 * configuration in place, it just converts those Milliseconds into a LocalDateTime at the UTC TimeZone
 * 
 */
public class MillisToDateDeserialiser implements JsonbDeserializer<Date> {

    @Override
    public Date deserialize(JsonParser parser, DeserializationContext ctx, Type rtType) {

        String value = parser.getString();

        if (value == null) {
            return null;
        }

        return new Date(Long.parseLong(value));
    }

}
