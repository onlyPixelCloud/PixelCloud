package de.haizon.pixelcloud.master.backend.dependency;

import de.haizon.pixelcloud.master.CloudMaster;
import de.haizon.pixelcloud.master.backend.downloader.UrlDownloader;

import java.io.File;
import java.io.FileInputStream;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLClassLoader;
import java.util.ArrayList;
import java.util.List;
import java.util.jar.JarEntry;
import java.util.jar.JarInputStream;

/**
 * JavaDoc this file!
 * Created: 02.11.2022
 *
 * @author Haizoooon (maxhewa@gmail.com)
 */
public class DependencyLoader {

    private final List<Dependency> dependencies;
    private final List<URL> dependencyUrls;

    public DependencyLoader() {
        this.dependencies = new ArrayList<>();
        this.dependencyUrls = new ArrayList<>();
    }

    public void loadDependency(String groupId, String artifactId, String version) {
        CloudMaster.getInstance().getCloudLogger().info("Loading dependency " + artifactId + "...");
        dependencies.add(new Dependency(groupId, artifactId, version));
        if (new File("dependencies", artifactId + "-" + version + ".jar").exists()) {
            return;
        }
        new UrlDownloader("https://repo1.maven.org/maven2/" + groupId.replace(".", "/") + "/" + artifactId + "/" + version + "/" + artifactId + "-" + version + ".jar", new File("dependencies", artifactId + "-" + version + ".jar")).download();
    }

    public void addDependencies() {

        URL[] urls = new URL[dependencyUrls.size()];
        dependencyUrls.toArray(urls);

        URLClassLoader urlClassLoader = URLClassLoader.newInstance(urls, ClassLoader.getSystemClassLoader());
        Thread.currentThread().setContextClassLoader(urlClassLoader);

    }

    private ArrayList<String> getClassNamesFromJar(JarInputStream jarFile) throws Exception {
        ArrayList<String> classNames = new ArrayList<>();
        try {
            //JarInputStream jarFile = new JarInputStream(jarFileStream);
            JarEntry jar;

            //Iterate through the contents of the jar file
            while (true) {
                jar = jarFile.getNextJarEntry();
                if (jar == null) {
                    break;
                }
                //Pick file that has the extension of .class
                if ((jar.getName().endsWith(".class"))) {
                    String className = jar.getName().replaceAll("/", ".");
                    String myClass = className.substring(0, className.lastIndexOf('.'));
                    classNames.add(myClass);
                }
            }
        } catch (Exception e) {
            throw new Exception("Error while getting class names from jar", e);
        }
        return classNames;
    }


    // Returns an arraylist of class names in a JarInputStream
// Calls the above function by converting the jar path to a stream
    private ArrayList<String> getClassNamesFromJar(String jarPath) throws Exception {
        return getClassNamesFromJar(new JarInputStream(new FileInputStream(jarPath)));
    }


    public List<Dependency> getDependencies() {
        return dependencies;
    }
}
