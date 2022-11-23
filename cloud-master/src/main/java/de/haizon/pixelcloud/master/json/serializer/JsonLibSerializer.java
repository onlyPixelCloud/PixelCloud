package de.haizon.pixelcloud.master.json.serializer;

import com.google.gson.*;
import de.haizon.pixelcloud.master.json.JsonLib;

import java.lang.reflect.Type;

/**
 * JavaDoc this file!
 * Created: 22.11.2022
 *
 * @author Haizoooon (maxhewa@gmail.com)
 */
public class JsonLibSerializer implements JsonSerializer<JsonLib>, JsonDeserializer<JsonLib> {

    @Override
    public JsonLib deserialize(JsonElement jsonElement, Type type, JsonDeserializationContext jsonDeserializationContext) throws JsonParseException {
        return JsonLib.fromJsonElement(jsonElement);
    }

    @Override
    public JsonElement serialize(JsonLib jsonLib, Type type, JsonSerializationContext jsonSerializationContext) {
        return jsonLib.getJsonElement();
    }
}
