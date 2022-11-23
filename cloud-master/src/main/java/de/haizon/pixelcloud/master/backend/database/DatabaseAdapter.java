package de.haizon.pixelcloud.master.backend.database;

import de.haizon.pixelcloud.master.CloudMaster;
import de.haizon.pixelcloud.master.backend.database.object.DatabaseObject;
import de.haizon.pixelcloud.master.backend.database.type.DatabaseType;
import de.haizon.pixelcloud.master.backend.reader.JsonReader;
import de.haizon.pixelcloud.master.console.setups.DatabaseSetup;

import java.io.File;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.sql.*;
import java.util.HashMap;
import java.util.Map;

/**
 * JavaDoc this file!
 * Created: 22.11.2022
 *
 * @author Haizoooon (maxhewa@gmail.com)
 */
public class DatabaseAdapter {

    private DatabaseObject databaseObject;

    private Connection connection;

    public DatabaseAdapter connect(){

        CloudMaster.getInstance().getCloudLogger().info("Trying to connect to mysql...");

        if(CloudMaster.getInstance().getFileManager().fileExist("storage", "database.json")){
            try {

                JsonReader jsonReader = null;
                try {
                    jsonReader = new JsonReader(Files.readString(Paths.get(new File("storage", "database.json").toURI())));
                } catch (IOException exception) {
                    exception.printStackTrace();
                }

                assert jsonReader != null;

                databaseObject = new DatabaseObject((String) jsonReader.read("host"), (Integer) jsonReader.read("port"), (String) jsonReader.read("database"), (String) jsonReader.read("username"), (String) jsonReader.read("password"));

                connection = DriverManager.getConnection("jdbc:mysql://" + databaseObject.getHost() + ":" + databaseObject.getPort() + "/" + databaseObject.getDatabase(), databaseObject.getUsername(), databaseObject.getPassword());
                CloudMaster.getInstance().getCloudLogger().success("Successfully connected to mysql.");
            } catch (SQLException throwables) {
                throwables.printStackTrace();
            }
        } else {
            new DatabaseSetup(this);
        }


        return this;
    }

    public void connect(DatabaseObject databaseObject){

        CloudMaster.getInstance().getCloudLogger().info("Trying to connect to mysql...");

        try {
            connection = DriverManager.getConnection("jdbc:mysql://" + databaseObject.getHost() + ":" + databaseObject.getPort() + "/" + databaseObject.getDatabase(), databaseObject.getUsername(), databaseObject.getPassword());
            CloudMaster.getInstance().getCloudLogger().success("Successfully connected to mysql.");
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }

    }

    public <T> void executeQuery(String query, ISqlFunction<ResultSet, T> consumer){
        try (PreparedStatement preparedStatement = connection.prepareStatement(query)){
            try (ResultSet resultSet = preparedStatement.executeQuery()){
                consumer.apply(resultSet);
            } catch (Exception ignored) {
            }
        } catch (SQLException throwable) {
            throwable.printStackTrace();
        }
    }

    public void executeUpdate(String query){
        try (Statement preparedStatement = connection.createStatement()){
            preparedStatement.executeUpdate(query);
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
    }

    public void createTable(String table, String[] keys, DatabaseType[] databaseTypes){
        Map<String, DatabaseType> content = new HashMap<>();
        for(int i = 0; i < keys.length; i++) content.put(keys[i], databaseTypes[i]);
        StringBuilder stringBuilder = new StringBuilder("CREATE TABLE IF NOT EXISTS `").append(table).append("` (`");
        int count = 0;
        for (String key : content.keySet()) {
            stringBuilder.append(key).append("` ").append(content.get(key).append()).append(count + 1 >= content.size() ? ")" : ", `");
            count++;
        }
        executeUpdate(stringBuilder.toString());
    }

    public Connection getConnection() {
        return connection;
    }

    public DatabaseObject getDatabaseObject() {
        return databaseObject;
    }

}
