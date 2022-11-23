package de.haizon.pixelcloud.master.console.setups;

import de.haizon.pixelcloud.master.console.setups.abstracts.SetupEnd;
import de.haizon.pixelcloud.master.console.setups.interfaces.ISetup;

/**
 * JavaDoc this file!
 * Created: 22.11.2022
 *
 * @author Haizoooon (maxhewa@gmail.com)
 */
public class GroupSetup extends ISetup {

    public GroupSetup() {

        setSetupEnd(new SetupEnd() {
            @Override
            public void handle() {

            }
        });



    }

}
