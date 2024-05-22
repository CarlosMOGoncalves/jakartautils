/**
 * Copyright (c) 2024 Carlos Gonçalves (https://www.linkedin.com/in/carlosmogoncalves/)
 * Likely open-source, so copy at will, bugs will be yours as well.
 */
package pt.cmg.jakartautils.jsonb;

import java.util.Locale;
import javax.json.bind.Jsonb;
import javax.json.bind.JsonbBuilder;
import javax.json.bind.JsonbConfig;
import javax.json.bind.annotation.JsonbDateFormat;
import javax.json.bind.config.PropertyNamingStrategy;
import pt.cmg.jakartautils.jsonb.deserialisers.MillisToDateDeserialiser;
import pt.cmg.jakartautils.jsonb.deserialisers.MillisToLocalDateTimeDeserialiser;
import pt.cmg.jakartautils.jsonb.serialisers.DateToMillisSerialiser;
import pt.cmg.jakartautils.jsonb.serialisers.LocalDateTimeToMillisSerialiser;
import pt.cmg.jakartautils.jsonb.serialisers.PairSerialiser;

/**
 * Utility class used to convert JSON<->String resorting to any JSON-B implementation provided by the server.
 *
 */
public class JsonbConverter {

    private static Jsonb INSTANCE;
    private static Jsonb IGNORE_CASE_INSTANCE;

    private JsonbConverter() {

    }

    private static void init(boolean ignoreCase) {
        JsonbConfig configuration = new JsonbConfig()
            .withDateFormat(JsonbDateFormat.TIME_IN_MILLIS, Locale.getDefault())
            .withSerializers(new DateToMillisSerialiser(), new LocalDateTimeToMillisSerialiser(), new PairSerialiser())
            .withDeserializers(new MillisToLocalDateTimeDeserialiser(), new MillisToDateDeserialiser())
            .withNullValues(true);
        if (ignoreCase) {
            configuration.withPropertyNamingStrategy(PropertyNamingStrategy.CASE_INSENSITIVE);
            IGNORE_CASE_INSTANCE = JsonbBuilder.newBuilder().withConfig(configuration).build();
        } else {
            INSTANCE = JsonbBuilder.newBuilder().withConfig(configuration).build();
        }
    }

    public static Jsonb getJsonB() {
        return getJsonB(false);

    }

    public static Jsonb getJsonB(boolean ignoreCase) {
        if (ignoreCase) {
            if (IGNORE_CASE_INSTANCE == null) {
                init(true);
            }
            return IGNORE_CASE_INSTANCE;
        } else {
            if (INSTANCE == null) {
                init(false);
            }
            return INSTANCE;
        }
    }

}
