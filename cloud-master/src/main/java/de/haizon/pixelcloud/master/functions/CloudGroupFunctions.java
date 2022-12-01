package de.haizon.pixelcloud.master.functions;

import de.haizon.pixelcloud.api.group.ICloudGroup;
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

    private final List<ICloudGroup> cloudGroups;

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

                    cloudGroups.add(new ICloudGroup() {

                        @Override
                        public String getName() {
                            return name;
                        }

                        @Override
                        public int getMaxServices() {
                            return maxServices;
                        }

                        @Override
                        public int getMinServices() {
                            return minServices;
                        }

                        @Override
                        public int getMaxHeap() {
                            return maxHeap;
                        }

                        @Override
                        public int getPercentageToStartNewService() {
                            return percentage;
                        }

                        @Override
                        public IGroupVersion getGroupVersion() {
                            return CloudMaster.getInstance().getVersionFetcher().getFetchedVersions().stream().filter(iGroupVersion -> iGroupVersion.getName().equalsIgnoreCase(version)).findFirst().orElse(null);
                        }

                        @Override
                        public GroupType getGroupType() {
                            return Arrays.stream(GroupType.values()).filter(groupType -> groupType.name().equalsIgnoreCase(type)).findFirst().orElse(null);
                        }

                        @Override
                        public boolean isMaintenance() {
                            return maintenance;
                        }

                        @Override
                        public int getMaxPlayers() {
                            return maxPlayers;
                        }

                        @Override
                        public ITemplate getTemplate() {
                            return CloudMaster.getInstance().getTemplateManager().getTemplates().stream().filter(iTemplate -> iTemplate.getName().equalsIgnoreCase(template)).findFirst().orElse(null);
                        }
                    });

                }

            } catch (SQLException e) {
                throw new RuntimeException(e);
            }

            return null;
        });
    }

    public List<ICloudGroup> getCloudGroups() {
        return cloudGroups;
    }
}
