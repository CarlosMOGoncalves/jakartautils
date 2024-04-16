package pt.cmg.jakartautils.jsonb.deserialisers;

import java.lang.reflect.Type;
import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneId;
import javax.json.bind.serializer.DeserializationContext;
import javax.json.bind.serializer.JsonbDeserializer;
import javax.json.stream.JsonParser;
import org.eclipse.yasson.internal.serializer.LocalDateTimeTypeDeserializer;
import pt.cmg.jakartautils.jsonb.serialisers.LocalDateTimeToMillisSerialiser;

/**
 * This deserialiser is used for one and only one reason, which is to convert a date represented
 * in milliseconds (a Long data type) to a LocalDateTime.
 * <br>
 * <br>
 * It is the counterpart to {@link LocalDateTimeToMillisSerialiser} and it was written instead of using
 * the default one {@link LocalDateTimeTypeDeserializer} because there was an incompatibility between the
 * original and the one we developed (ours was not in UTC and the default is) so that now we don't rely on
 * defaults.
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
