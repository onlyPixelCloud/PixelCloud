package de.haizon.pixelcloud.master;

import de.haizon.pixelcloud.master.backend.database.DatabaseAdapter;
import de.haizon.pixelcloud.master.backend.database.functions.DatabaseGroupFunction;
import de.haizon.pixelcloud.master.backend.database.functions.DatabaseTemplateFunction;
import de.haizon.pixelcloud.master.backend.dependency.DependencyLoader;
import de.haizon.pixelcloud.master.backend.files.FileManager;
import de.haizon.pixelcloud.master.backend.modules.ModuleHandler;
import de.haizon.pixelcloud.master.backend.packets.PacketFunction;
import de.haizon.pixelcloud.master.backend.packets.functions.inout.PacketInAndOutSendBackToClient;
import de.haizon.pixelcloud.master.backend.packets.functions.inbound.PacketInPlayerConnected;
import de.haizon.pixelcloud.master.backend.packets.functions.PacketReceiveServiceOnlineFunction;
import de.haizon.pixelcloud.master.backend.runner.CloudServiceRunner;
import de.haizon.pixelcloud.master.backend.runner.CloudServiceStartQueue;
import de.haizon.pixelcloud.master.backend.versions.VersionFetcher;
import de.haizon.pixelcloud.master.console.ConsoleManager;
import de.haizon.pixelcloud.master.console.command.CommandManager;
import de.haizon.pixelcloud.master.console.sender.ConsoleSender;
import de.haizon.pixelcloud.master.console.setups.SetupBuilder;
import de.haizon.pixelcloud.master.functions.CloudGroupFunctions;
import de.haizon.pixelcloud.master.functions.CloudServiceFunctions;
import de.haizon.pixelcloud.master.logger.CloudLogger;
import de.haizon.pixelcloud.master.template.TemplateManager;
import de.haizon.pixelcloud.master.wrapper.WrapperManager;

import java.io.File;

/**
 * JavaDoc this file!
 * Created: 02.11.2022
 *
 * @author Haizoooon (maxhewa@gmail.com)
 */
public class CloudMaster {

    private static CloudMaster master;

    private final FileManager fileManager;

    private final ConsoleManager consoleManager;
    private final CloudLogger cloudLogger;
    private final CommandManager commandManager;
    private final DependencyLoader dependencyLoader;
    private final ConsoleSender consoleSender;
    private SetupBuilder setupBuilder;
    private final DatabaseAdapter databaseAdapter;
    private final WrapperManager wrapperManager;
    private DatabaseGroupFunction databaseGroupFunction;
    private DatabaseTemplateFunction databaseTemplateFunction;

    private final TemplateManager templateManager;
    private final VersionFetcher versionFetcher;
    private final CloudGroupFunctions cloudGroupFunctions;
    private final CloudServiceFunctions cloudServiceFunctions;
    private final CloudServiceRunner cloudServiceRunner;
    private final CloudServiceStartQueue cloudServiceStartQueue;

    private final PacketFunction packetFunction;
    private final ModuleHandler moduleHandler;

    public static void main(String[] args) {
        new CloudMaster();
    }

    public CloudMaster() {
        master = this;

        fileManager = new FileManager();

        fileManager.createFolders("storage/jars", "modules", "dependencies", "storage", "storage/servers/temp", "storage/servers/templates", "storage/servers/static");
        fileManager.createFile("storage", "wrappers.json");

        consoleManager = new ConsoleManager();
        cloudLogger = new CloudLogger();
        consoleSender = new ConsoleSender();
        setupBuilder = new SetupBuilder();
//        wrapperManager = new WrapperManager();

        dependencyLoader = new DependencyLoader();
        dependencyLoader.loadDependency("com.google.collections", "google-collections", "1.0");
        dependencyLoader.loadDependency("com.google.code.gson", "gson", "2.10");
        dependencyLoader.loadDependency("org.jline", "jline-reader", "3.19.0");
        dependencyLoader.loadDependency("org.jline", "jline-terminal", "3.19.0");
        dependencyLoader.loadDependency("org.fusesource.jansi", "jansi", "2.3.2");
        dependencyLoader.loadDependency("org.json", "json", "20201115");
        dependencyLoader.loadDependency("mysql", "mysql-connector-java", "8.0.23");
        dependencyLoader.loadDependency("commons-io", "commons-io", "2.11.0");
        dependencyLoader.loadDependency("org.reflections", "reflections", "0.9.12");
        dependencyLoader.loadDependency("org.objenesis", "objenesis", "3.2");

//        dependencyLoader.addDependencies();

        dependencyLoader.getDependencies().forEach(dependency -> {
            getCloudLogger().success("Loaded dependency " + dependency.getGroupId() + " " + dependency.getArtifactId() + " " + dependency.getVersion());
        });

        getCloudLogger().clear();
        getCloudLogger().info("Trying to start cloud...");

        commandManager = new CommandManager();
        databaseAdapter = new DatabaseAdapter().connect();

        if(databaseAdapter.getConnection() != null){
            databaseTemplateFunction = new DatabaseTemplateFunction();
            databaseGroupFunction = new DatabaseGroupFunction();
        }

        templateManager = new TemplateManager();
        templateManager.fetch();

        fileManager.deleteFiles(new File("storage/servers/temp"));

        wrapperManager = new WrapperManager();

        packetFunction = new PacketFunction();

        versionFetcher = new VersionFetcher();
        versionFetcher.fetch();

        cloudServiceRunner = new CloudServiceRunner();
        cloudServiceStartQueue = new CloudServiceStartQueue();

        cloudGroupFunctions = new CloudGroupFunctions();
        cloudGroupFunctions.fetch();

        cloudServiceFunctions = new CloudServiceFunctions();
        cloudServiceFunctions.fetch();

        moduleHandler = new ModuleHandler();
        moduleHandler.registerModules();

        cloudServiceRunner.startAll();

        packetFunction.registerPacketReceiver(new PacketReceiveServiceOnlineFunction());
        packetFunction.registerPacketReceiver(new PacketInPlayerConnected());
        packetFunction.registerPacketReceiver(new PacketInAndOutSendBackToClient());

//        new RestServer();

    }

    public void setDatabaseGroupFunction(DatabaseGroupFunction databaseGroupFunction) {
        this.databaseGroupFunction = databaseGroupFunction;
    }

    public void setDatabaseTemplateFunction(DatabaseTemplateFunction databaseTemplateFunction) {
        this.databaseTemplateFunction = databaseTemplateFunction;
    }

    public ModuleHandler getModuleHandler() {
        return moduleHandler;
    }

    public CloudServiceStartQueue getCloudServiceStartQueue() {
        return cloudServiceStartQueue;
    }

    public PacketFunction getPacketFunction() {
        return packetFunction;
    }

    public CloudServiceFunctions getCloudServiceFunctions() {
        return cloudServiceFunctions;
    }

    public CloudServiceRunner getCloudServiceRunner() {
        return cloudServiceRunner;
    }

    public CloudGroupFunctions getCloudGroupFunctions() {
        return cloudGroupFunctions;
    }

    public VersionFetcher getVersionFetcher() {
        return versionFetcher;
    }

    public TemplateManager getTemplateManager() {
        return templateManager;
    }

    public DatabaseTemplateFunction getDatabaseTemplateFunction() {
        return databaseTemplateFunction;
    }

    public DatabaseGroupFunction getDatabaseGroupFunction() {
        return databaseGroupFunction;
    }

    public WrapperManager getWrapperManager() {
        return wrapperManager;
    }

    public DatabaseAdapter getDatabaseAdapter() {
        return databaseAdapter;
    }

    public ConsoleSender getConsoleSender() {
        return consoleSender;
    }

    public FileManager getFileManager() {
        return fileManager;
    }

    public DependencyLoader getDependencyLoader() {
        return dependencyLoader;
    }

    public ConsoleManager getConsoleManager() {
        return consoleManager;
    }

    public CommandManager getCommandManager() {
        return commandManager;
    }

    public CloudLogger getCloudLogger() {
        return cloudLogger;
    }

    public void setSetupBuilder(SetupBuilder setupBuilder) {
        this.setupBuilder = setupBuilder;
    }

    public SetupBuilder getSetupBuilder() {
        return setupBuilder;
    }

    public static CloudMaster getInstance() {
        return master;
    }
}
