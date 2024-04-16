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
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.ext.ContextResolver;
import javax.ws.rs.ext.Provider;
import pt.cmg.jakartautils.jsonb.deserialisers.MillisToDateDeserialiser;
import pt.cmg.jakartautils.jsonb.deserialisers.MillisToLocalDateTimeDeserialiser;
import pt.cmg.jakartautils.jsonb.serialisers.DateToMillisSerialiser;
import pt.cmg.jakartautils.jsonb.serialisers.LocalDateTimeToMillisSerialiser;
import pt.cmg.jakartautils.jsonb.serialisers.PairSerialiser;

/**
 * This is a Context Resolver that configures the JSONB feature globally.
 */
@Provider
@Produces(MediaType.APPLICATION_JSON)
public class JSONBConfigurator implements ContextResolver<Jsonb> {

    @Override
    public Jsonb getContext(Class<?> type) {

        return JsonbBuilder.newBuilder()
            .withConfig(createConfiguration())
            .build();

    }

    private JsonbConfig createConfiguration() {
        return new JsonbConfig()
            .withDateFormat(JsonbDateFormat.TIME_IN_MILLIS, Locale.getDefault())
            .withDeserializers(new MillisToLocalDateTimeDeserialiser(), new MillisToDateDeserialiser())
            .withSerializers(new DateToMillisSerialiser(), new LocalDateTimeToMillisSerialiser(), new PairSerialiser())
            .withNullValues(true);
    }

}
