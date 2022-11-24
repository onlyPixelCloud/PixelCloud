package de.haizon.pixelcloud.master.backend.database.functions;

import de.haizon.pixelcloud.api.console.Color;
import de.haizon.pixelcloud.api.template.ITemplate;
import de.haizon.pixelcloud.api.template.TemplateType;
import de.haizon.pixelcloud.master.CloudMaster;
import de.haizon.pixelcloud.master.backend.database.DatabaseAdapter;
import de.haizon.pixelcloud.master.backend.database.type.DatabaseType;

import java.sql.SQLException;
import java.util.UUID;

/**
 * JavaDoc this file!
 * Created: 22.11.2022
 *
 * @author Haizoooon (maxhewa@gmail.com)
 */
public class DatabaseTemplateFunction {

    private final DatabaseAdapter databaseAdapter;

    public DatabaseTemplateFunction() {
        this.databaseAdapter = CloudMaster.getInstance().getDatabaseAdapter();
        this.databaseAdapter.createTable("module_templates",
                new String[]{"uniqueId", "name", "type"},
                new DatabaseType[]{DatabaseType.VARCHAR, DatabaseType.VARCHAR, DatabaseType.VARCHAR});
    }

    public boolean templateExists(String name){
        this.databaseAdapter.executeQuery("SELECT name FROM module_templates WHERE name ='" + name + "'", resultSet -> {
            try {
                return resultSet.next();
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
        });
        return false;
    }

    public void createTemplate(String name, TemplateType templateType){
        UUID uuid = UUID.randomUUID();

        ITemplate template = new ITemplate() {
            @Override
            public UUID getUniqueId() {
                return uuid;
            }

            @Override
            public String getName() {
                return name;
            }

            @Override
            public TemplateType getTemplateType() {
                return templateType;
            }
        };

        CloudMaster.getInstance().getTemplateManager().getTemplates().add(template);

        this.databaseAdapter.executeUpdate("INSERT INTO module_templates (uniqueId, name, type) VALUES ('" + uuid + "', '" + name + "', '" + templateType.name() + "');");

        CloudMaster.getInstance().getCloudLogger().info("The template " + Color.RED.getColor() + name + Color.RESET.getColor() + " was created successfully!");

        if (!templateType.equals(TemplateType.STATIC)) {
            CloudMaster.getInstance().getFileManager().createFolder("storage/servers/templates/" + name);

            

        }

    }

}
