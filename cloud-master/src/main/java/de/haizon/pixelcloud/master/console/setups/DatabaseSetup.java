package de.haizon.pixelcloud.master.console.setups;

import de.haizon.pixelcloud.master.CloudMaster;
import de.haizon.pixelcloud.master.backend.database.DatabaseAdapter;
import de.haizon.pixelcloud.master.backend.database.functions.DatabaseGroupFunction;
import de.haizon.pixelcloud.master.backend.database.functions.DatabaseTemplateFunction;
import de.haizon.pixelcloud.master.backend.database.object.DatabaseObject;
import de.haizon.pixelcloud.master.backend.loader.WebsiteContentLoader;
import de.haizon.pixelcloud.master.json.JsonLib;
import de.haizon.pixelcloud.master.console.setups.abstracts.SetupEnd;
import de.haizon.pixelcloud.master.console.setups.abstracts.SetupInput;
import de.haizon.pixelcloud.master.console.setups.interfaces.ISetup;
import org.json.JSONObject;

import java.io.File;
import java.io.IOException;
import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.regex.Pattern;

/**
 * JavaDoc this file!
 * Created: 22.11.2022
 *
 * @author Haizoooon (maxhewa@gmail.com)
 */
public class DatabaseSetup extends ISetup {

    private String host, database, username, password;
    private int port;

    public DatabaseSetup(DatabaseAdapter databaseAdapter) {

        setSetupEnd(new SetupEnd() {
            @Override
            public void handle() {

                JsonLib jsonLib = JsonLib.empty();
                jsonLib.append("host", host);
                jsonLib.append("port", port);
                jsonLib.append("database", database);
                jsonLib.append("username", username);
                jsonLib.append("password", password);

                jsonLib.saveAsFile(new File("storage", "database.json"));

                databaseAdapter.connect(new DatabaseObject(host, port, database, username, password));

                CloudMaster.getInstance().setDatabaseGroupFunction(new DatabaseGroupFunction());
                CloudMaster.getInstance().setDatabaseTemplateFunction(new DatabaseTemplateFunction());

            }
        });

        setSetupInputs(new SetupInput("Please provide the mysql host") {
            @Override
            public List<String> getSuggestions() {

                var hostAddress = "";
                try {
                    JSONObject jsonObject = new JSONObject(new WebsiteContentLoader().loadContent("https://api.ipify.org/?format=json"));
                    hostAddress = jsonObject.getString("ip");
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }

                return Arrays.asList("127.0.0.1", hostAddress);
            }

            @Override
            public boolean handle(String input) {
                if(input.endsWith(" ")){
                    host = input.substring(0, input.length() - 1);
                } else {
                    host = input;
                }
                return true;
            }
        }, new SetupInput("Please provide the mysql port") {
            @Override
            public List<String> getSuggestions() {
                return Collections.singletonList("3306");
            }

            @Override
            public boolean handle(String input) {
                if(input.matches("[0-9]+")) port = Integer.parseInt((input.endsWith(" ") ? input.substring(0, input.length() - 1) : input));
                return input.matches("[0-9]+");
            }
        }, new SetupInput("Please provide the mysql username") {
            @Override
            public List<String> getSuggestions() {
                return null;
            }

            @Override
            public boolean handle(String input) {
                username = input;
                return true;
            }
        }, new SetupInput("Please provide the mysql password") {
            @Override
            public List<String> getSuggestions() {
                return null;
            }

            @Override
            public boolean handle(String input) {
                password = input;
                return true;
            }
        }, new SetupInput("Please provide the mysql database name") {
            @Override
            public List<String> getSuggestions() {
                return null;
            }

            @Override
            public boolean handle(String input) {
                database = input;
                return true;
            }
        });

    }
}
