package de.haizon.pixelcloud.master.backend.reader;

import org.json.JSONObject;

/**
 * JavaDoc this file!
 * Created: 22.11.2022
 *
 * @author Haizoooon (maxhewa@gmail.com)
 */
public class JsonReader {

    private final JSONObject jsonObject;

    public JsonReader(String content) {
        this.jsonObject = new JSONObject(content);
    }

    public Object read(String var1){
        return jsonObject.get(var1);
    }
}
