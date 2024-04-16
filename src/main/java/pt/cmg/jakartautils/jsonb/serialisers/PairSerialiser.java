package pt.cmg.jakartautils.jsonb.serialisers;

import javax.json.bind.serializer.JsonbSerializer;
import javax.json.bind.serializer.SerializationContext;
import javax.json.stream.JsonGenerator;
import org.apache.commons.lang3.tuple.Pair;

/**
 * This Serializer is used to emulate the Jackson behaviour of serializing a Pair (from guava)
 * to a {key:value} JSON object, since the standard Yasson does not provide a satisfiable default one
 * for that matter.<br>
 */
public class PairSerialiser implements JsonbSerializer<Pair> {

    /*
     * NOTE: It serializes the non-parameterized Pair (instead of Pair<T,X>) because there is likely also a problem
     * when the runtime registers one such Serializer. It does so as a ParameterizedType serializer but then looks for
     * the ImmutablePair class registered as a Type, which eventually fails on ComponentMatcher.matches(). We should check
     * on later versions of Yasson whether this is still a thing and maybe open a ticket.
     */
    @Override
    public void serialize(Pair obj, JsonGenerator generator, SerializationContext ctx) {

        if (obj != null) {
            generator.writeStartObject();

            generator.writeKey(obj.getLeft().toString());

            ctx.serialize(obj.getRight(), generator);

            generator.writeEnd();
        } else {
            generator.writeNull();
        }

    }

}
