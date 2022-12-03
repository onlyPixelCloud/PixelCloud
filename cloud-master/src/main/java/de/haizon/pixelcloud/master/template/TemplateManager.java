package de.haizon.pixelcloud.master.template;

import de.haizon.pixelcloud.api.services.impl.TemplateImpl;
import de.haizon.pixelcloud.api.template.ITemplate;
import de.haizon.pixelcloud.api.template.TemplateType;
import de.haizon.pixelcloud.master.CloudMaster;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.UUID;

/**
 * JavaDoc this file!
 * Created: 22.11.2022
 *
 * @author Haizoooon (maxhewa@gmail.com)
 */
public class TemplateManager {

    private final List<TemplateImpl> templates;

    public TemplateManager() {
        this.templates = new ArrayList<>();
    }

    public void fetch(){

        if(CloudMaster.getInstance().getDatabaseAdapter().getConnection() == null) return;

        CloudMaster.getInstance().getDatabaseAdapter().executeQuery("SELECT * FROM module_templates", resultSet -> {

            try {
                while(resultSet.next()){

                    UUID uuid = UUID.fromString(resultSet.getString("uniqueId"));
                    String name = resultSet.getString("name");
                    TemplateType templateType = Arrays.stream(TemplateType.values()).filter(templateType1 -> {
                        try {
                            return templateType1.name().equalsIgnoreCase(resultSet.getString("type"));
                        } catch (SQLException e) {
                            throw new RuntimeException(e);
                        }
                    }).findFirst().orElse(null);

                    if(templateType == null) return false;

                    templates.add(new TemplateImpl(uuid.toString(), name, templateType));
                }
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }

            return null;
        });

        CloudMaster.getInstance().getCloudLogger().info("Registered following templates:");
        for (ITemplate template : templates) {
            CloudMaster.getInstance().getCloudLogger().info("- " + template.getName() + " [" + template.getUniqueId() + "/" + template.getTemplateType() + "]");
        }

    }

    public List<TemplateImpl> getTemplates() {
        return templates;
    }
}
