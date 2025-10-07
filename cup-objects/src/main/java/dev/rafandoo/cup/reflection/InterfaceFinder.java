package dev.rafandoo.cup.reflection;

import lombok.experimental.UtilityClass;
import lombok.extern.slf4j.Slf4j;

import java.util.ArrayList;
import java.util.List;

import static java.lang.reflect.Modifier.isAbstract;

/**
 * Utility class for finding classes that implement a specific interface.
 */
@Slf4j
@UtilityClass
public final class InterfaceFinder {

    /**
     * Finds all classes that implement the specified interface in the specified package.
     *
     * @param interfaceClass the interface class.
     * @param packageName    the package name.
     * @param <T>            the type of the interface.
     * @return a list of classes that implement the interface.
     */
    public static <T> List<T> findImplementations(Class<T> interfaceClass, String packageName) {
        List<T> implementations = new ArrayList<>();
        try {
            // Obtém as classes do pacote
            List<Class<?>> classes = ClassFinder.getClasses(packageName);

            for (Class<?> clazz : classes) {
                // Verifica se a classe implementa a interface e não é abstrata
                if (interfaceClass.isAssignableFrom(clazz)
                    && !clazz.isInterface()
                    && !isAbstract(clazz.getModifiers())) {
                    try {
                        implementations.add(interfaceClass.cast(clazz.getDeclaredConstructor().newInstance()));
                    } catch (Exception e) {
                        log.error("Failed to instantiate class: {}. Error: {}", clazz.getName(), e.getMessage());
                    }
                }
            }
        } catch (Exception e) {
            log.error("Failed to find classes in package: {}. Error: {}", packageName, e.getMessage());
        }
        return implementations;
    }
}
