package de.haizon.pixelcloud.master.console.setups;

import de.haizon.pixelcloud.api.services.version.GroupType;
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
public class GroupSetup extends ISetup {

    private String name, version, template;
    private GroupType groupType;
    private int maxServices, minServices, maxHeap, maxPlayers, percentageToStartNewService;

    public GroupSetup() {

        setSetupEnd(new SetupEnd() {
            @Override
            public void handle() {

                percentageToStartNewService = 100;

                CloudMaster.getInstance().getDatabaseGroupFunction().createGroup(name, template, maxServices, minServices, maxHeap, maxPlayers, percentageToStartNewService, groupType, version);

            }
        });

        setSetupInputs(new SetupInput("Please provide the group name") {
            @Override
            public List<String> getSuggestions() {
                return null;
            }

            @Override
            public boolean handle(String input) {
                name = input;
                return true;
            }
        }, new SetupInput("Please provide the group template") {
            @Override
            public List<String> getSuggestions() {
                List<String> names = new ArrayList<>();
                CloudMaster.getInstance().getTemplateManager().getTemplates().forEach(iTemplate -> {
                    names.add(iTemplate.getName());
                });
                return names;
            }

            @Override
            public boolean handle(String input) {
                template = input;
                return CloudMaster.getInstance().getTemplateManager().getTemplates().stream().anyMatch(iTemplate -> iTemplate.getName().equalsIgnoreCase(input));
            }
        }, new SetupInput("Please provide the maximal service count") {
            @Override
            public List<String> getSuggestions() {
                return null;
            }

            @Override
            public boolean handle(String input) {
                if (input.matches("[0-9]+")) {
                    maxServices = Integer.parseInt(input);
                }
                return input.matches("[0-9]+");
            }
        }, new SetupInput("Please provide the minimal service count") {
            @Override
            public List<String> getSuggestions() {
                return null;
            }

            @Override
            public boolean handle(String input) {
                if (input.matches("[0-9]+")) {
                    minServices = Integer.parseInt(input);
                }
                return input.matches("[0-9]+");
            }
        }, new SetupInput("Please provide the maximal player count") {
            @Override
            public List<String> getSuggestions() {
                return null;
            }

            @Override
            public boolean handle(String input) {
                if (input.matches("[0-9]+")) {
                    maxPlayers = Integer.parseInt(input);
                }
                return input.matches("[0-9]+");
            }
        }, new SetupInput("Please provide the maximal heap (RAM) in MB") {
            @Override
            public List<String> getSuggestions() {
                return null;
            }

            @Override
            public boolean handle(String input) {
                if (input.matches("[0-9]+")) {
                    maxHeap = Integer.parseInt(input);
                }
                return input.matches("[0-9]+");
            }
        }, new SetupInput("Please provide the service type") {
            @Override
            public List<String> getSuggestions() {
                List<String> types = new ArrayList<>();
                for (GroupType value : GroupType.values()) types.add(value.name());
                return types;
            }

            @Override
            public boolean handle(String input) {
                groupType = Arrays.stream(GroupType.values()).filter(groupType1 -> groupType1.name().equalsIgnoreCase(input)).findFirst().orElse(null);
                return Arrays.stream(GroupType.values()).anyMatch(groupType -> groupType.name().equalsIgnoreCase(input));
            }
        }, new SetupInput("Please provide the group version") {


            @Override
            public List<String> getSuggestions() {
                List<String> values = new ArrayList<>();
                CloudMaster.getInstance().getVersionFetcher().getFetchedVersions().forEach(iGroupVersion -> {
                    if(iGroupVersion.getType().equals(groupType)) values.add(iGroupVersion.getName());
                });
                return values;
            }

            @Override
            public boolean handle(String input) {
                version = input;
                return getSuggestions().stream().anyMatch(s -> s.equalsIgnoreCase(input));
            }
        });

    }

}
