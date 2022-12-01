package de.haizon.pixelcloud.config;

import org.json.JSONObject;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;

/**
 * JavaDoc this file!
 * Created: 24.11.2022
 *
 * @author Haizoooon (maxhewa@gmail.com)
 */
public class PluginServiceIdentifier {

    private final JSONObject jsonObject;

    public PluginServiceIdentifier() {

        try {
            String content = Files.readString(Paths.get(new File("cloud_identify.json").toURI()));

            this.jsonObject = new JSONObject(content);

        } catch (IOException e) {
            throw new RuntimeException(e);
        }

    }

    public JSONObject getJsonObject() {
        return jsonObject;
    }
}
