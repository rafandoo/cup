package dev.rafandoo.cup.reflection;

import lombok.experimental.UtilityClass;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.List;

/**
 * Utility class for finding classes in a package.
 */
@UtilityClass
public final class ClassFinder {

    /**
     * Finds all classes in the specified package.
     *
     * @param packageName the package name.
     * @return a list of classes in the package.
     * @throws ClassNotFoundException if a class cannot be found.
     * @throws IOException            if an I/O error occurs.
     */
    public static List<Class<?>> getClasses(String packageName) throws ClassNotFoundException, IOException {
        ClassLoader classLoader = Thread.currentThread().getContextClassLoader();
        String path = packageName.replace('.', '/');
        Enumeration<URL> resources = classLoader.getResources(path);
        List<File> directories = new ArrayList<>();

        while (resources.hasMoreElements()) {
            URL resource = resources.nextElement();
            directories.add(new File(resource.getFile()));
        }

        List<Class<?>> classes = new ArrayList<>();
        for (File directory : directories) {
            classes.addAll(findClasses(directory, packageName));
        }
        return classes;
    }

    /**
     * Recursively finds all classes in the specified directory.
     *
     * @param directory   the directory to search.
     * @param packageName the package name for classes found.
     * @return a list of classes in the directory.
     * @throws ClassNotFoundException if a class cannot be found.
     */
    public static List<Class<?>> findClasses(File directory, String packageName) throws ClassNotFoundException {
        List<Class<?>> classes = new ArrayList<>();
        if (!directory.exists()) {
            return classes;
        }

        File[] files = directory.listFiles();
        if (files == null) return classes;

        for (File file : files) {
            if (file.isDirectory()) {
                // Recurse nos subdiretórios
                classes.addAll(findClasses(file, packageName + "." + file.getName()));
            } else if (file.getName().endsWith(".class")) {
                // Remove ".class" do nome e carrega a classe
                String className = packageName + '.' + file.getName().replaceAll("\\.class$", "");
                classes.add(Class.forName(className));
            }
        }
        return classes;
    }
}
