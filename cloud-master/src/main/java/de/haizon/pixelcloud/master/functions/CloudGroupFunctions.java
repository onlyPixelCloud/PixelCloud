package de.haizon.pixelcloud.master.functions;

import de.haizon.pixelcloud.api.group.ICloudGroup;
import de.haizon.pixelcloud.api.services.impl.CloudGroupImpl;
import de.haizon.pixelcloud.api.services.version.GroupType;
import de.haizon.pixelcloud.api.services.version.IGroupVersion;
import de.haizon.pixelcloud.api.template.ITemplate;
import de.haizon.pixelcloud.master.CloudMaster;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.UUID;

/**
 * JavaDoc this file!
 * Created: 23.11.2022
 *
 * @author Haizoooon (maxhewa@gmail.com)
 */
public class CloudGroupFunctions {

    private final List<CloudGroupImpl> cloudGroups;

    public CloudGroupFunctions() {
        this.cloudGroups = new ArrayList<>();
    }

    public void fetch() {

        if (CloudMaster.getInstance().getDatabaseAdapter().getConnection() == null) return;

        CloudMaster.getInstance().getDatabaseAdapter().executeQuery("SELECT * FROM module_groups", resultSet -> {

            try {
                while (resultSet.next()) {

                    String name = resultSet.getString("name");
                    String template = resultSet.getString("template");
                    String type = resultSet.getString("type");
                    String version = resultSet.getString("version");
                    int maxServices = resultSet.getInt("maxServices");
                    int minServices = resultSet.getInt("minServices");
                    int maxHeap = resultSet.getInt("maxHeap");
                    int maxPlayers = resultSet.getInt("maxPlayers");
                    int percentage = resultSet.getInt("percentageToStartNewService");
                    boolean maintenance = Boolean.parseBoolean(resultSet.getString("maintenance"));

                    cloudGroups.add(new CloudGroupImpl(name,  CloudMaster.getInstance().getVersionFetcher().getFetchedVersions().stream().filter(iGroupVersion -> iGroupVersion.getName().equalsIgnoreCase(version)).findFirst().orElse(null), maxServices, minServices, maxHeap, percentage, maxPlayers, maintenance, CloudMaster.getInstance().getTemplateManager().getTemplates().stream().filter(iTemplate -> iTemplate.getName().equalsIgnoreCase(template)).findFirst().orElse(null), type));

                }

            } catch (SQLException e) {
                throw new RuntimeException(e);
            }

            return null;
        });
    }

    public List<CloudGroupImpl> getCloudGroups() {
        return cloudGroups;
    }
}
