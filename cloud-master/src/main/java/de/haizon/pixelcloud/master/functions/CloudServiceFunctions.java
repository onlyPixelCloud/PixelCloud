package de.haizon.pixelcloud.master.functions;

import de.haizon.pixelcloud.api.services.ICloudService;
import de.haizon.pixelcloud.api.services.impl.CloudServiceImpl;
import de.haizon.pixelcloud.api.services.status.CloudServiceStatus;
import de.haizon.pixelcloud.api.template.TemplateType;
import de.haizon.pixelcloud.master.CloudMaster;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

/**
 * JavaDoc this file!
 * Created: 23.11.2022
 *
 * @author Haizoooon (maxhewa@gmail.com)
 */
public class CloudServiceFunctions {

    private final List<CloudServiceImpl> cloudServices;

    public CloudServiceFunctions() {
        this.cloudServices = new ArrayList<>();
    }

    public void fetch(){

        CloudMaster.getInstance().getCloudGroupFunctions().getCloudGroups().forEach(iCloudGroup -> {
            for(int i = iCloudGroup.getMinServices(); i < (iCloudGroup.getMaxServices() + 1); i++){
                CloudServiceImpl cloudService = new CloudServiceImpl(iCloudGroup.getName(), iCloudGroup.getName()  + "-" + i, iCloudGroup, i, randomPort(), iCloudGroup.getTemplate().getTemplateType().equals(TemplateType.STATIC), CloudServiceStatus.STOPPED);
                cloudServices.add(cloudService);

            }
        });

    }

    private int randomPort(){
        return new Random().nextInt(40000 - 10000 + 1) + 10000;
    }

    public List<CloudServiceImpl> getCloudServices() {
        return cloudServices;
    }
}
