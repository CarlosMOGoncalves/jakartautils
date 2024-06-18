package pt.cmg.jakartautils.jsonb.deserialisers;

import java.lang.reflect.Type;
import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneId;
import jakarta.json.bind.serializer.DeserializationContext;
import jakarta.json.bind.serializer.JsonbDeserializer;
import jakarta.json.stream.JsonParser;

/**
 * This deserialiser is used to convert a date represented in milliseconds (a Long data type) to a LocalDateTime.
 * <br>
 * <br>
 * Note that this effectively overrides the default Deserializer so it pretty much disregards any additional
 * configuration in place, it just converts those Milliseconds into a LocalDateTime at the UTC TimeZone
 * 
 */
public class MillisToLocalDateTimeDeserialiser implements JsonbDeserializer<LocalDateTime> {

    @Override
    public LocalDateTime deserialize(JsonParser parser, DeserializationContext ctx, Type rtType) {

        String value = parser.getString();

        if (value == null) {
            return null;
        }

        return LocalDateTime.ofInstant(Instant.ofEpochMilli(Long.parseLong(value)), ZoneId.of("UTC"));
    }

}
