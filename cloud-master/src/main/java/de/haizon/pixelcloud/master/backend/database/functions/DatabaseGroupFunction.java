package de.haizon.pixelcloud.master.backend.database.functions;

import de.haizon.pixelcloud.api.services.impl.CloudGroupImpl;
import de.haizon.pixelcloud.api.services.impl.GroupVersionImpl;
import de.haizon.pixelcloud.api.services.version.GroupType;
import de.haizon.pixelcloud.api.services.version.IGroupVersion;
import de.haizon.pixelcloud.master.CloudMaster;
import de.haizon.pixelcloud.master.backend.database.DatabaseAdapter;
import de.haizon.pixelcloud.master.backend.database.type.DatabaseType;
import de.haizon.pixelcloud.master.backend.downloader.UrlDownloader;

import java.io.File;
import java.sql.SQLException;
import java.util.Arrays;
import java.util.UUID;

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
                new String[]{"uniqueId", "name", "template", "maxServices", "minServices", "maxHeap", "maxPlayers", "percentageToStartNewService", "type", "version", "maintenance"},
                new DatabaseType[]{DatabaseType.VARCHAR, DatabaseType.VARCHAR, DatabaseType.VARCHAR, DatabaseType.INT, DatabaseType.INT, DatabaseType.INT, DatabaseType.INT, DatabaseType.INT, DatabaseType.VARCHAR, DatabaseType.VARCHAR, DatabaseType.VARCHAR});
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

    public void createGroup(String name, String template, int maxServices, int minServices, int maxHeap, int maxPlayers, int percentage, GroupType groupType, String version){
        if (!groupExists(name)) {

            UUID uuid = UUID.randomUUID();

            databaseAdapter.executeUpdate("INSERT INTO module_groups (uniqueId, name, template, maxServices, minServices, maxHeap, maxPlayers, percentageToStartNewService, type, version, maintenance) VALUES ('" + uuid + "', '" + name + "', '" + template + "', '" + maxServices + "', '" + minServices + "', '" + maxHeap + "', '" + maxPlayers + "', '" + percentage + "', '" + groupType.name() + "', '" + version + "', 'true');");

            GroupVersionImpl groupVersion = CloudMaster.getInstance().getVersionFetcher().getFetchedVersions().stream().filter(iGroupVersion -> iGroupVersion.getName().equalsIgnoreCase(version)).findFirst().orElse(null);

            if(groupVersion == null) return;

            new UrlDownloader(groupVersion.getUrl(), new File("storage/jars", groupVersion.getName() + ".jar")).download();

            CloudGroupImpl cloudGroup = new CloudGroupImpl(name, groupVersion, maxServices, minServices, maxHeap, percentage, maxPlayers, true, CloudMaster.getInstance().getTemplateManager().getTemplates().stream().filter(template1 -> template1.getName().equalsIgnoreCase(template)).findFirst().orElse(null), groupType.name());

            CloudMaster.getInstance().getCloudGroupFunctions().getCloudGroups().add(cloudGroup);
            CloudMaster.getInstance().getCloudServiceRunner().start(cloudGroup);

        }
    }

}
