package de.haizon.pixelcloud.bootstrap.velocity;

import com.google.inject.Inject;
import com.velocitypowered.api.command.CommandManager;
import com.velocitypowered.api.event.Subscribe;
import com.velocitypowered.api.event.proxy.ProxyInitializeEvent;
import com.velocitypowered.api.plugin.Plugin;
import com.velocitypowered.api.proxy.ProxyServer;
import com.velocitypowered.api.proxy.server.ServerInfo;
import de.haizon.pixelcloud.CloudPlugin;
import de.haizon.pixelcloud.api.packets.Packet;
import de.haizon.pixelcloud.api.packets.PacketType;
import de.haizon.pixelcloud.bootstrap.velocity.events.ConnectEvents;
import de.haizon.pixelcloud.implementation.CloudServiceImplementation;
import de.haizon.pixelcloud.packets.receiver.ServiceConnectedReceive;
import org.json.JSONObject;
import org.slf4j.Logger;

import java.net.InetSocketAddress;
import java.util.Objects;

/**
 * JavaDoc this file!
 * Created: 23.11.2022
 *
 * @author Haizoooon (maxhewa@gmail.com)
 */
@Plugin(id = "cloud_plugin", name = "CloudPlugin", authors = {"Haizoooon"}, version = "1.0.0")
public class VelocityBootstrap {

    private final ProxyServer proxyServer;
    private final Logger logger;

    @Inject
    public VelocityBootstrap(ProxyServer proxyServer, Logger logger) {
        this.proxyServer = proxyServer;
        this.logger = logger;

        new CloudPlugin();

        CloudPlugin.getInstance().getPacketFunction().registerPacketReceiver(new ServiceConnectedReceive());

        registerFallback();
    }

    @Subscribe
    public void onProxyInitialization(ProxyInitializeEvent event) {
        proxyServer.getEventManager().register(this, new ConnectEvents());

        CommandManager commandManager = proxyServer.getCommandManager();
        //register cloud command
    }

    public void registerFallback(){
        registerService("fallback", new InetSocketAddress("127.0.0.1", 0));
    }

    public void removeService(String name){
        proxyServer.unregisterServer(Objects.requireNonNull(proxyServer.getAllServers().stream().filter(registeredServer -> registeredServer.getServerInfo().getName().equalsIgnoreCase(name)).findFirst().orElse(null)).getServerInfo());
        logger.info("Unregistered service " + name + ".");
    }

    public void registerService(String name, InetSocketAddress inetSocketAddress){
        ServerInfo serverInfo = new ServerInfo(name, inetSocketAddress);
        proxyServer.registerServer(serverInfo);
    }

    public ProxyServer getProxyServer() {
        return proxyServer;
    }

    public Logger getLogger() {
        return logger;
    }
}
