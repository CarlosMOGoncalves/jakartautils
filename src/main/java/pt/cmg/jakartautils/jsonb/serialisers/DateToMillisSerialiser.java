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
 * Turns a Date object into a Long number, which is its milliseconds representation.
 * Normally, just using {@link JsonbDateFormat.TIME_IN_MILLIS} would be enough, but Yasson's default
 * writes Dates in millis as Strings and it is useful to receive numbers as Longs
 * <br>
 * <br>
 * Note that this effectively overrides the default Serializer so it pretty much disregards any additional
 * configuration in place, it just writes Dates converted to milliseconds as Longs
 *
 */
public class DateToMillisSerialiser implements JsonbSerializer<Date> {

    @Override
    public void serialize(Date dateToSerialize, JsonGenerator generator, SerializationContext serializationContext) {

        if (dateToSerialize != null) {
            generator.write(dateToSerialize.getTime());
        } else {
            generator.write((String) null);
        }
    }

}
