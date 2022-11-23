package de.haizon.pixelcloud.master.backend.database.functions;

import de.haizon.pixelcloud.master.CloudMaster;
import de.haizon.pixelcloud.master.backend.database.DatabaseAdapter;
import de.haizon.pixelcloud.master.backend.database.type.DatabaseType;

import java.sql.SQLException;

/**
 * JavaDoc this file!
 * Created: 22.11.2022
 *
 * @author Haizoooon (maxhewa@gmail.com)
 */
public class DatabaseGroupFunction {

    private final DatabaseAdapter databaseAdapter;

    public DatabaseGroupFunction() {
        this.databaseAdapter = CloudMaster.getInstance().getDatabaseAdapter();
        databaseAdapter.createTable("module_groups",
                new String[]{"uniqueId", "name", "maxServices", "minServices", "maxHeap", "maxPlayers", "percentageToStartNewService", "type", "version"},
                new DatabaseType[]{DatabaseType.VARCHAR, DatabaseType.VARCHAR, DatabaseType.INT, DatabaseType.INT, DatabaseType.INT, DatabaseType.INT, DatabaseType.INT, DatabaseType.VARCHAR, DatabaseType.VARCHAR});
    }

    public boolean groupExists(String name){

        databaseAdapter.executeQuery("SELECT name FROM module_groups WHERE name = '" + name + "'", resultSet -> {
            try {
                return resultSet.next();
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
        });

        return false;
    }

}
