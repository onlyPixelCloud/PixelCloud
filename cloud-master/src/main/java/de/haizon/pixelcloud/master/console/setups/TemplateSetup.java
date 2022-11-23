package de.haizon.pixelcloud.master.console.setups;

import de.haizon.pixelcloud.api.template.TemplateType;
import de.haizon.pixelcloud.master.CloudMaster;
import de.haizon.pixelcloud.master.console.setups.abstracts.SetupEnd;
import de.haizon.pixelcloud.master.console.setups.abstracts.SetupInput;
import de.haizon.pixelcloud.master.console.setups.interfaces.ISetup;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * JavaDoc this file!
 * Created: 22.11.2022
 *
 * @author Haizoooon (maxhewa@gmail.com)
 */
public class TemplateSetup extends ISetup {

    private String name, type;

    public TemplateSetup() {

        setSetupEnd(new SetupEnd() {
            @Override
            public void handle() {

                TemplateType templateType = Arrays.stream(TemplateType.values()).filter(templateType1 -> templateType1.name().equalsIgnoreCase(type)).findFirst().orElse(null);

                if(templateType == null) return;

                CloudMaster.getInstance().getDatabaseTemplateFunction().createTemplate(name, templateType);

            }
        });

        setSetupInputs(new SetupInput("Please provide the template name") {
            @Override
            public List<String> getSuggestions() {
                return null;
            }

            @Override
            public boolean handle(String input) {
                name = input;
                return CloudMaster.getInstance().getTemplateManager().getTemplates().stream().noneMatch(iTemplate -> iTemplate.getName().equalsIgnoreCase(input));
            }
        }, new SetupInput("Please provide the template type") {
            @Override
            public List<String> getSuggestions() {
                List<String> templates = new ArrayList<>();
                for (TemplateType value : TemplateType.values()) templates.add(value.name());
                return templates;
            }

            @Override
            public boolean handle(String input) {
                type = input;
                return Arrays.stream(TemplateType.values()).anyMatch(templateType -> templateType.name().equalsIgnoreCase(input));
            }
        });

    }
}
