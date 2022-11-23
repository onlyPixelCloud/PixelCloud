package de.haizon.pixelcloud.master.json;

import com.google.gson.*;
import de.haizon.pixelcloud.master.json.serializer.JsonLibSerializer;

import java.io.*;
import java.nio.charset.StandardCharsets;
import java.util.Map;
import java.util.Set;
import java.util.stream.Stream;

/**
 * JavaDoc this file!
 * Created: 22.11.2022
 *
 * @author Haizoooon (maxhewa@gmail.com)
 */
public class JsonLib {

    protected static JsonElement jsonElement;
    protected static final Gson gson = new GsonBuilder()
            .registerTypeAdapter(JsonLib.class, new JsonLibSerializer())
            .setPrettyPrinting()
            .serializeNulls()
            .create();

    public JsonLib(JsonElement jsonElement) {
        JsonLib.jsonElement = jsonElement;
    }

    public void append(String property, String value) {
        if (!(jsonElement instanceof JsonObject))
            throw new UnsupportedOperationException("Can't append element to JsonPrimitive.");
        jsonElement.getAsJsonObject().addProperty(property, value);
    }

    public void append(String property, Object value) {
        if (!(jsonElement instanceof JsonObject))
            throw new UnsupportedOperationException("Can't append element to JsonPrimitive.");
        jsonElement.getAsJsonObject().add(property, gson.toJsonTree(value));
    }

    public void append(String property, Integer value) {
        if (!(jsonElement instanceof JsonObject))
            throw new UnsupportedOperationException("Can't append element to JsonPrimitive.");
        jsonElement.getAsJsonObject().addProperty(property, value);
    }

    public void append(String property, boolean value) {
        if (!(jsonElement instanceof JsonObject))
            throw new UnsupportedOperationException("Can't append element to JsonPrimitive.");
        jsonElement.getAsJsonObject().addProperty(property, value);
    }

    public JsonElement getProperty(String name) {
        if (!(jsonElement instanceof JsonObject)) return null;
        return jsonElement.getAsJsonObject().get(name);
    }

    public Integer getInt(String property) {
        if (!(jsonElement instanceof JsonObject))
            throw new UnsupportedOperationException("Can't get element from JsonPrimitive.");
        if (!jsonElement.getAsJsonObject().has(property)) {
            return null;
        } else {
            return jsonElement.getAsJsonObject().get(property).getAsInt();
        }
    }

    public Long getLong(String property) {
        if (!(jsonElement instanceof JsonObject))
            throw new UnsupportedOperationException("Can't get element from JsonPrimitive.");
        if (!jsonElement.getAsJsonObject().has(property)) {
            return null;
        } else {
            return jsonElement.getAsJsonObject().get(property).getAsLong();
        }
    }

    public Double getDouble(String property) {
        if (!(jsonElement instanceof JsonObject))
            throw new UnsupportedOperationException("Can't get element from JsonPrimitive.");
        if (!jsonElement.getAsJsonObject().has(property)) {
            return null;
        } else {
            return jsonElement.getAsJsonObject().get(property).getAsDouble();
        }
    }

    public Float getFloat(String property) {
        if (!(jsonElement instanceof JsonObject))
            throw new UnsupportedOperationException("Can't get element from JsonPrimitive.");
        if (!jsonElement.getAsJsonObject().has(property)) {
            return null;
        } else {
            return jsonElement.getAsJsonObject().get(property).getAsFloat();
        }
    }

    public JsonArray getArray(String property) {
        if (!(jsonElement instanceof JsonObject))
            throw new UnsupportedOperationException("Can't get element from JsonPrimitive.");
        if (!jsonElement.getAsJsonObject().has(property)) {
            return null;
        } else {
            return jsonElement.getAsJsonObject().get(property).getAsJsonArray();
        }
    }

    public String getString(String property) {
        if (!(jsonElement instanceof JsonObject))
            throw new UnsupportedOperationException("Can't get element from JsonPrimitive.");
        if (!jsonElement.getAsJsonObject().has(property)) {
            return null;
        } else {
            return jsonElement.getAsJsonObject().get(property).getAsString();
        }
    }

    public <T> Object getObject(String property, Class<T> tClass) {
        if (!(jsonElement instanceof JsonObject))
            throw new UnsupportedOperationException("Can't get element from JsonPrimitive.");
        if (tClass == JsonLib.class) {
            return getProperty(property);
        }
        if (!jsonElement.getAsJsonObject().has(property)) {
            return null;
        } else {
            return gson.fromJson(jsonElement.getAsJsonObject().get(property), tClass);
        }
    }

    public <T> T getObject(Class<T> tClass) {
        return gson.fromJson(getAsJsonString(), tClass);
    }

    public <T> Object getObjectOrNull(Class<T> tClass) {
        if (getAsJsonString().isEmpty()) return null;
        try {
            return gson.fromJson(getAsJsonString(), tClass);
        } catch (Exception exception) {
            return null;
        }
    }

    public void saveAsFile(String path) {
        saveJsonElementAsFile(new File(path));
    }

    public void saveAsFile(File file) {
        saveJsonElementAsFile(file);
    }

    public boolean saveJsonElementAsFile(String path) {
        return saveJsonElementAsFile(new File(path));
    }

    public boolean saveJsonElementAsFile(File file) {
        File dir = file.getParentFile();
        if (dir != null && !dir.exists()) {
            dir.mkdirs();
        }
        try {
            FileOutputStream fileOutputStream = new FileOutputStream(file);
            fileOutputStream.write(getJsonStringAsBytes());
            fileOutputStream.flush();
            fileOutputStream.close();
        } catch (Exception exception) {
            return false;
        }

        return false;
    }

    public String getAsJsonString() {
        return gson.toJson(jsonElement);
    }

    public byte[] getJsonStringAsBytes() {
        return getAsJsonString().getBytes(StandardCharsets.UTF_8);
    }

    public Set<Map.Entry<String, JsonElement>> getEntrySet() {
        JsonElement jsonElement = JsonLib.jsonElement;
        if (!(jsonElement instanceof JsonObject))
            throw new UnsupportedOperationException("Can't get keys from JsonPrimitive.");
        return jsonElement.getAsJsonObject().entrySet();
    }

    public Stream<String> getAllKeys() {
        Set<Map.Entry<String, JsonElement>> elementEntry = getEntrySet();
        return elementEntry.stream().map(Map.Entry::getKey);
    }

    public static JsonLib empty() {
        return new JsonLib(new JsonObject());
    }

    public static JsonLib fromJsonElement(JsonElement jsonElement) {
        return new JsonLib(jsonElement);
    }

    public static JsonLib fromObject(Object object) {
        return fromJsonString(gson.toJson(object));
    }

    public static JsonLib fromJsonFile(String path) {
        return fromJsonFile(new File(path));
    }

    public static JsonLib fromJsonFile(File file) {
        if (!file.exists()) return null;
        return fromJsonString(loadFile(file));
    }

    public static JsonLib fromInputStream(InputStream inputStream) {
        return fromJsonString(loadFromInputStream(inputStream));
    }

    public static JsonLib fromJsonString(String string) {
        try {
            JsonObject jsonObject = gson.fromJson(string, JsonObject.class);
            return new JsonLib(jsonObject);
        } catch (Exception exception) {
            try {
                JsonArray jsonArray = gson.fromJson(string, JsonArray.class);
                return new JsonLib(jsonArray);
            } catch (Exception exception1) {
                try {
                    JsonPrimitive jsonPrimitive = gson.fromJson(string, JsonPrimitive.class);
                    return new JsonLib(jsonPrimitive);
                } catch (Exception e) {
                    try {
                        throw new IllegalAccessException("Can't parse string " + string);
                    } catch (IllegalAccessException ex) {
                        ex.printStackTrace();
                    }
                }
                return null;
            }
        }
    }

    protected static String loadFromInputStream(InputStream inputStream) {
        try {
            byte[] data = new byte[inputStream.available()];
            inputStream.read(data);
            inputStream.close();
            return new String(data, StandardCharsets.UTF_8);
        } catch (IOException exception) {
            exception.printStackTrace();
        }
        return "";
    }

    protected static String loadFile(File file) {
        if (!file.exists()) return "";
        try {
            return loadFromInputStream(new FileInputStream(file));
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        return "";
    }

    public String toString() {
        return getAsJsonString();
    }

    public JsonElement getJsonElement() {
        return jsonElement;
    }
}
