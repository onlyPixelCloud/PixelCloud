package de.haizon.pixelcloud.bootstrap.velocity;

import com.google.inject.Inject;
import com.velocitypowered.api.command.CommandManager;
import com.velocitypowered.api.event.Subscribe;
import com.velocitypowered.api.event.proxy.ProxyInitializeEvent;
import com.velocitypowered.api.plugin.Plugin;
import com.velocitypowered.api.proxy.ProxyServer;
import com.velocitypowered.api.proxy.server.ServerInfo;
import de.haizon.pixelcloud.CloudPlugin;
import de.haizon.pixelcloud.bootstrap.velocity.commands.CloudCommand;
import de.haizon.pixelcloud.bootstrap.velocity.events.ConnectEvents;
import de.haizon.pixelcloud.bootstrap.velocity.packets.inbound.PacketInCloudPlayerConnect;
import de.haizon.pixelcloud.bootstrap.velocity.packets.inbound.PacketInCloudPlayerKick;
import de.haizon.pixelcloud.bootstrap.velocity.packets.inbound.PacketInCloudPlayerSendActionBar;
import de.haizon.pixelcloud.bootstrap.velocity.packets.inbound.PacketInCloudPlayerSendMessage;
import de.haizon.pixelcloud.packets.receiver.ServiceConnectedReceive;
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

    private static VelocityBootstrap instance;

    @Inject
    public VelocityBootstrap(ProxyServer proxyServer, Logger logger) {
        instance = this;
        this.proxyServer = proxyServer;
        this.logger = logger;

        new CloudPlugin();

        CloudPlugin.getInstance().getPacketFunction().registerPacketReceiver(new ServiceConnectedReceive());

        CloudPlugin.getInstance().getPacketFunction().registerPacketReceiver(new PacketInCloudPlayerConnect());
        CloudPlugin.getInstance().getPacketFunction().registerPacketReceiver(new PacketInCloudPlayerKick());
        CloudPlugin.getInstance().getPacketFunction().registerPacketReceiver(new PacketInCloudPlayerSendActionBar());
        CloudPlugin.getInstance().getPacketFunction().registerPacketReceiver(new PacketInCloudPlayerSendMessage());

        registerFallback();
    }

    @Subscribe
    public void onProxyInitialization(ProxyInitializeEvent event) {
        proxyServer.getEventManager().register(this, new ConnectEvents());

        CommandManager commandManager = proxyServer.getCommandManager();
        commandManager.register(commandManager.metaBuilder("cloud").aliases("cl").plugin(this).build(), new CloudCommand());
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

    public static VelocityBootstrap getInstance() {
        return instance;
    }

    public ProxyServer getProxyServer() {
        return proxyServer;
    }

    public Logger getLogger() {
        return logger;
    }
}
