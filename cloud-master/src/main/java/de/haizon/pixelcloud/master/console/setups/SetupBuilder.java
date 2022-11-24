package de.haizon.pixelcloud.master.console.setups;

import de.haizon.pixelcloud.master.CloudMaster;
import de.haizon.pixelcloud.master.console.setups.abstracts.SetupEnd;
import de.haizon.pixelcloud.master.console.setups.abstracts.SetupInput;
import de.haizon.pixelcloud.master.console.setups.interfaces.ISetup;

import java.util.ArrayList;
import java.util.List;

/**
 * JavaDoc this file!
 * Created: 22.11.2022
 *
 * @author Haizoooon (maxhewa@gmail.com)
 */
public class SetupBuilder {

    private final List<ISetup> setupQueue = new ArrayList<>();

    private SetupInput currentInput;
    private SetupEnd setupEnd;
    private SetupInput[] setupInputs;
    private int currentIndex = 0;
    private ISetup currentSetup;

    public SetupBuilder() {
    }

    public SetupBuilder(ISetup setup, SetupEnd setupEnd, SetupInput... setupInputs) {
        this.currentSetup = setup;
        setupQueue.add(currentSetup);
        this.setupEnd = setupEnd;
        this.setupInputs = setupInputs;
        this.currentInput = setupInputs[currentIndex];
        CloudMaster.getInstance().getConsoleManager().getConsoleCompleter().setSuggestions(currentInput.getSuggestions());
        CloudMaster.getInstance().getCloudLogger().setup(currentInput.getQuestion());
    }

    public void nextQuestion(boolean value){
        if(value){
            if(currentIndex == setupInputs.length - 1){
                setupEnd.handle();
                currentSetup = null;
                setupQueue.clear();
                currentIndex = 0;
                CloudMaster.getInstance().getCloudLogger().success("Setup was successfully completed.");
                List<String> commands = new ArrayList<>();
                CloudMaster.getInstance().getCommandManager().getCommandHandlers().forEach((command, iCommandHandler) -> {
                    commands.add(command.name());
                });
                CloudMaster.getInstance().getConsoleManager().getConsoleCompleter().setSuggestions(commands);
                return;
            }
            currentIndex = currentIndex + 1;
            currentInput = setupInputs[currentIndex];
        } else {
            CloudMaster.getInstance().getCloudLogger().warning("The input was incorrect...");
        }
        CloudMaster.getInstance().getConsoleManager().getConsoleCompleter().setSuggestions(currentInput.getSuggestions());
        CloudMaster.getInstance().getCloudLogger().setup(currentInput.getQuestion());
        getCurrentSetup().setCurrentInput(currentInput);
    }

    public void cancel(){

    }

    public ISetup getCurrentSetup() {
        return currentSetup;
    }

}
