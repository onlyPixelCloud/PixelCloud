package de.haizon.pixelcloud.master.console.setups.interfaces;

import de.haizon.pixelcloud.master.CloudMaster;
import de.haizon.pixelcloud.master.console.setups.SetupBuilder;
import de.haizon.pixelcloud.master.console.setups.abstracts.SetupEnd;
import de.haizon.pixelcloud.master.console.setups.abstracts.SetupInput;

/**
 * JavaDoc this file!
 * Created: 22.11.2022
 *
 * @author Haizoooon (maxhewa@gmail.com)
 */
public abstract class ISetup {

    private SetupInput[] setupInputs;
    private SetupInput currentInput;
    private SetupEnd setupEnd;

    public void setSetupInputs(SetupInput... setupInputs){
        this.setupInputs = setupInputs;
        this.currentInput = setupInputs[0];

        CloudMaster.getInstance().setSetupBuilder(new SetupBuilder(this, setupEnd, setupInputs));
    }

    public void setSetupEnd(SetupEnd setupEnd){
        this.setupEnd = setupEnd;
    }

    public void setCurrentInput(SetupInput currentInput) {
        this.currentInput = currentInput;
    }

    public SetupEnd getSetupEnd() {
        return setupEnd;
    }

    public SetupInput[] getSetupInputs() {
        return setupInputs;
    }

    public SetupInput getCurrentInput() {
        return currentInput;
    }
}
