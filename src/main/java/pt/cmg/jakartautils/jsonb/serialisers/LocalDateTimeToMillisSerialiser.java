/**
 * Copyright (c) 2024 Carlos Gonçalves (https://www.linkedin.com/in/carlosmogoncalves/)
 * Likely open-source, so copy at will, bugs will be yours as well.
 */
package pt.cmg.jakartautils.jsonb.serialisers;

import java.util.Date;
import javax.json.bind.annotation.JsonbDateFormat;
import javax.json.bind.serializer.JsonbSerializer;
import javax.json.bind.serializer.SerializationContext;
import javax.json.stream.JsonGenerator;

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
public class LocalDateTimeToMillisSerialiser implements JsonbSerializer<Date> {

    @Override
    public void serialize(Date dateToSerialize, JsonGenerator generator, SerializationContext serializationContext) {

        if (dateToSerialize != null) {
            generator.write(dateToSerialize.getTime());
        } else {
            generator.write((String) null);
        }
    }

}
