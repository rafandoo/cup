package dev.rafandoo.cup.reflection.discovery;

import lombok.experimental.UtilityClass;
import lombok.extern.slf4j.Slf4j;

import java.util.ArrayList;
import java.util.List;

import dev.rafandoo.cup.reflection.TypeInspector;

/**
 * Utility class for finding classes that implement a specific interface.
 */
@Slf4j
@UtilityClass
public final class InterfaceFinder {

    /**
     * Finds all non-abstract classes that implement the specified interface
     * inside the given package.
     *
     * @param interfaceType the interface or parent type to search for.
     * @param packageName   the base package to scan.
     * @param <T>           the interface type.
     * @return a list of classes implementing the given interface.
     * @throws RuntimeException if classpath scanning fails.
     */
    public static <T> List<Class<? extends T>> findImplementations(Class<T> interfaceType, String packageName) {
        List<Class<? extends T>> implementations = new ArrayList<>();

        try {
            for (Class<?> candidate : ClassFinder.find(packageName)) {
                if (
                    interfaceType.isAssignableFrom(candidate)
                    && TypeInspector.isConcrete(candidate)
                ) {
                    @SuppressWarnings("unchecked")
                    Class<? extends T> impl = (Class<? extends T>) candidate;
                    implementations.add(impl);
                }
            }
        } catch (Exception e) {
            log.error("Failed to find classes in package: {}. Error: {}", packageName, e.getMessage());
        }

        return implementations;
    }
}
