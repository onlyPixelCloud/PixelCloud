package de.haizon.notify.config;

import de.haizon.pixelcloud.master.json.JsonLib;
import org.json.JSONObject;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;

/**
 * JavaDoc this file!
 * Created: 04.12.2022
 *
 * @author Haizoooon (maxhewa@gmail.com)
 */
public class JsonConfig {

    private JsonLib jsonLib;
    private final JSONObject jsonObject;

    public JsonConfig() throws IOException {

        if(new File("modules/notify", "config.json").exists()){
            try {
                jsonObject = new JSONObject(Files.readString(Paths.get(new File("modules/notify", "config.json").toURI())));
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
            return;
        }

        jsonLib = JsonLib.empty();
        jsonLib.append("service_logged_in", "§8[§a»§8] §7{service_name}");
        jsonLib.append("service_logged_out", "§8[§c»§8] §7{service_name}");
        jsonLib.append("service_preparing", "§8[§e»§8] §7{service_name}");
        jsonLib.saveAsFile(new File("modules/notify", "config.json"));

        jsonObject = new JSONObject(Files.readString(Paths.get(new File("modules/notify", "config.json").toURI())));

    }

    public String getProperty(String property){
        return jsonObject.getString(property);
    }

}
