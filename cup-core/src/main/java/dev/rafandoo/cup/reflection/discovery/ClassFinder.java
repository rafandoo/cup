package dev.rafandoo.cup.reflection.discovery;

import lombok.experimental.UtilityClass;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.List;

/**
 * Utility class for discovering classes available in a given package.
 * <p>
 * <strong>Note:</strong> This implementation supports only classes loaded
 * from the filesystem (i.e. exploded directories). Classes packaged inside
 * JAR files are not supported.
 */
@UtilityClass
public final class ClassFinder {

    /**
     * Finds all classes located in the specified package and its subpackages.
     *
     * @param packageName the fully qualified package name.
     * @return a list of discovered classes.
     * @throws IOException if an error occurs while accessing classpath resources.
     */
    public static List<Class<?>> find(String packageName) throws IOException {
        ClassLoader classLoader = Thread.currentThread().getContextClassLoader();
        String path = packageName.replace('.', '/');

        Enumeration<URL> resources = classLoader.getResources(path);
        List<Class<?>> classes = new ArrayList<>();

        while (resources.hasMoreElements()) {
            URL resource = resources.nextElement();
            File directory = new File(resource.getFile());

            if (directory.exists() && directory.isDirectory()) {
                classes.addAll(scanDirectory(directory, packageName));
            }
        }

        return classes;
    }

    /**
     * Recursively scans a directory to locate {@code .class} files
     * and load them as Java classes.
     *
     * @param directory   the directory to scan.
     * @param packageName the current package name.
     * @return a list of discovered classes.
     */
    public static List<Class<?>> scanDirectory(File directory, String packageName) {
        List<Class<?>> classes = new ArrayList<>();

        File[] files = directory.listFiles();
        if (files == null) {
            return classes;
        }

        for (File file : files) {
            if (file.isDirectory()) {
                classes.addAll(
                    scanDirectory(file, packageName + "." + file.getName())
                );
            } else if (file.getName().endsWith(".class")) {
                String className = packageName + '.' + file.getName().replaceAll("\\.class$", "");

                try {
                    classes.add(Class.forName(className));
                } catch (ClassNotFoundException ignored) {
                }
            }
        }

        return classes;
    }
}
