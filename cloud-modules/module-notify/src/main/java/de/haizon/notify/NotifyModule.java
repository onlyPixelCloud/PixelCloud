package de.haizon.notify;

import de.haizon.notify.config.JsonConfig;
import de.haizon.notify.listener.CloudNotifyListener;
import de.haizon.pixelcloud.api.logger.ICloudLogger;
import de.haizon.pixelcloud.api.modules.ICloudModule;
import de.haizon.pixelcloud.api.modules.Module;
import de.haizon.pixelcloud.master.CloudMaster;

import java.io.IOException;

/**
 * JavaDoc this file!
 * Created: 04.12.2022
 *
 * @author Haizoooon (maxhewa@gmail.com)
 */
@Module(name = "notify_module", authors = {"Haizoooon"})
public class NotifyModule implements ICloudModule {

    private static NotifyModule instance;
    private JsonConfig jsonConfig;

    @Override
    public void onInitialization(ICloudLogger cloudLogger) {
        instance = this;
        try {
            jsonConfig = new JsonConfig();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        CloudMaster.getInstance().getEventBus().register(new CloudNotifyListener());
    }

    public JsonConfig getJsonConfig() {
        return jsonConfig;
    }

    public static NotifyModule getInstance() {
        return instance;
    }
}
