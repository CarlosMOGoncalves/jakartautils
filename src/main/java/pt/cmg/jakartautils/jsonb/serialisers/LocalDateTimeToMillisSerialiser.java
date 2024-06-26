/**
 * Copyright (c) 2024 Carlos Gonçalves (https://www.linkedin.com/in/carlosmogoncalves/)
 * Likely open-source, so copy at will, bugs will be yours as well.
 */
package pt.cmg.jakartautils.jsonb.serialisers;

import java.time.LocalDateTime;
import jakarta.json.bind.annotation.JsonbDateFormat;
import jakarta.json.bind.serializer.JsonbSerializer;
import jakarta.json.bind.serializer.SerializationContext;
import jakarta.json.stream.JsonGenerator;
import pt.cmg.jakartautils.time.DateTimeUtil;

/**
 * Turns a LocalDatimeTime object into a Long number, which is its milliseconds representation.
 * Normally, just using {@link JsonbDateFormat.TIME_IN_MILLIS} would be enough, but Yasson's default
 * writes LocalDateTime in millis as Strings and numbers are useful as Longs
 * <br>
 * <br>
 * Note that this effectively overrides the default Serializer so it pretty much disregards any additional
 * configuration in place, it just writes LocalDateTimes, converted to milliseconds as Longs.
 *
 */
public class LocalDateTimeToMillisSerialiser implements JsonbSerializer<LocalDateTime> {

    @Override
    public void serialize(LocalDateTime dateToSerialize, JsonGenerator generator, SerializationContext serializationContext) {

        if (dateToSerialize != null) {
            generator.write(DateTimeUtil.toMilliseconds(dateToSerialize));
        } else {
            generator.write((String) null);
        }
    }

}
