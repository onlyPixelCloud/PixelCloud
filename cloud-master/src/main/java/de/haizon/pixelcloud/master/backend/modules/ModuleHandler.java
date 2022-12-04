package de.haizon.pixelcloud.master.backend.modules;

import org.apache.commons.io.IOUtils;
import org.json.JSONObject;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.jar.JarEntry;
import java.util.jar.JarFile;
import java.util.stream.Collectors;

/**
 * JavaDoc this file!
 * Created: 04.12.2022
 *
 * @author Haizoooon (maxhewa@gmail.com)
 */
public class ModuleHandler {

    private final ModuleInitializer moduleInitializer;
    private static final List<PixelModule> modules = new CopyOnWriteArrayList<>();

    public ModuleHandler() {
        moduleInitializer = new ModuleInitializer();
    }

    private File getDirectory(){
        return new File("modules");
    }

    public void registerModules(){
        File[] files = getDirectory().listFiles();
        if(files == null){
            return;
        }


        JarFile jarFile = null;
        for(File file : Arrays.stream(files).filter(Objects::nonNull).filter(this::isJarFile).toList()){
            try {

                jarFile = new JarFile(file);
                JarEntry jarEntry = jarFile.getJarEntry("cloud_module.json");

                JSONObject jsonObject;

                try (InputStream inputStream = jarFile.getInputStream(jarEntry)){
                    String content = IOUtils.toString(inputStream, StandardCharsets.UTF_8);
                    jsonObject = new JSONObject(content);
                }

                moduleInitializer.initModule(file.getName(), new PixelModuleDescription(jsonObject.getString("name"), jsonObject.getString("main")));

            } catch (IOException e) {
                throw new RuntimeException(e);
            } finally {
                try {
                    assert jarFile != null;
                    jarFile.close();
                } catch (IOException ignored) {}
            }
        }

    }

    public boolean isJarFile(File file){
        return file.getName().endsWith(".jar");
    }

    public File findModule(String name) { return new File("modules", name); }

    public List<PixelModule> getModules() {
        return modules;
    }
}
