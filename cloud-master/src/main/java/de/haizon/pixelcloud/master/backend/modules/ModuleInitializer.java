package de.haizon.pixelcloud.master.backend.modules;

import de.haizon.pixelcloud.api.logger.ICloudLogger;
import de.haizon.pixelcloud.api.modules.Module;
import de.haizon.pixelcloud.master.CloudMaster;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLClassLoader;
import java.util.Arrays;
import java.util.regex.Pattern;

/**
 * JavaDoc this file!
 * Created: 04.12.2022
 *
 * @author Haizoooon (maxhewa@gmail.com)
 */
public class ModuleInitializer {

    public static final Pattern ID_PATTERN = Pattern.compile("[a-z][a-z0-9-_]{0,63}");

    public void initModule(String file, PixelModuleDescription pixelModuleDescription){
        try {
            URLClassLoader urlClassLoader = new URLClassLoader(new URL[]{new ModuleHandler().findModule(file).toURI().toURL()}, getClass().getClassLoader());

            Class<?> clazz = Class.forName(pixelModuleDescription.getMain(), true, urlClassLoader);

            if(clazz.isEnum() || clazz.isInterface()){
                CloudMaster.getInstance().getCloudLogger().severe("An error occured adding a module. Module main Class is a §c" + (clazz.isEnum() ? "enum" : clazz.isAnnotation() ? "annotation" : clazz.isInterface() ? "interface" : "...") + "§r! Code 71");
                return;
            }

            Object instance = clazz.getDeclaredConstructor().newInstance();

            if (clazz.getAnnotation(Module.class) == null) {
                CloudMaster.getInstance().getCloudLogger().severe("An error occured adding a module. Module does not have a Module §cannotation§r! Code 72");
                return;
            }

            Module module = clazz.getAnnotation(Module.class);

            if(ID_PATTERN.matcher(module.name()).matches()){
                String name = module.name();
                String version = module.version();
                String[] authors = module.authors();

                CloudMaster.getInstance().getCloudLogger().success("Module §c" + name + " §rby §e" + Arrays.toString(authors) + " §rwas added to cloud!");

                CloudMaster.getInstance().getModuleHandler().getModules().add(new PixelModule(name, version, authors, module.groupType()));

            }

            Method method = clazz.getMethod("onInitialization", ICloudLogger.class);
            method.invoke(instance, CloudMaster.getInstance().getCloudLogger());

        } catch (MalformedURLException | IllegalAccessException | InvocationTargetException | NoSuchMethodException |
                 InstantiationException | ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
    }

}
