package de.haizon.pixelcloud.master.backend.runner;

import de.haizon.pixelcloud.api.console.Color;
import de.haizon.pixelcloud.api.services.ICloudService;
import de.haizon.pixelcloud.api.services.status.CloudServiceStatus;
import de.haizon.pixelcloud.master.CloudMaster;

import java.io.IOException;
import java.util.concurrent.ConcurrentLinkedQueue;
import java.util.concurrent.CopyOnWriteArrayList;

/**
 * JavaDoc this file!
 * Created: 03.12.2022
 *
 * @author Haizoooon (maxhewa@gmail.com)
 */
public class CloudServiceStartQueue {

    private final ConcurrentLinkedQueue<ICloudService> queue = new ConcurrentLinkedQueue<>();
    private final CopyOnWriteArrayList<ICloudService> startingServices = new CopyOnWriteArrayList<>();

    public void addToQueue(ICloudService cloudService){
        this.queue.add(cloudService);
    }

    public void startThread(){
        new Thread(() -> {

            while(true){
                startingServices.removeIf(cloudService -> cloudService.getServiceStatus().equals(CloudServiceStatus.STARTED) || cloudService.getServiceStatus().equals(CloudServiceStatus.STOPPED));

                boolean canStartService = !queue.isEmpty() && startingServices.size() < 2;
                if(canStartService){
                    ICloudService cloudService = queue.poll();
                    try {
                        CloudMaster.getInstance().getCloudServiceRunner().start(cloudService);
                        CloudMaster.getInstance().getCloudLogger().info("Starting service §c" + cloudService.getName() + " §ron §eNode-01§r.");
                    } catch (IOException e) {
                        throw new RuntimeException(e);
                    }
                    startingServices.add(cloudService);
                }
                try {
                    Thread.sleep(20);
                } catch (InterruptedException e) {
                    throw new RuntimeException(e);
                }
            }

        }).start();
    }

}
