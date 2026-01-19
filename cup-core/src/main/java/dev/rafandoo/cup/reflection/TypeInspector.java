package dev.rafandoo.cup.reflection;

import lombok.experimental.UtilityClass;

import java.lang.reflect.Constructor;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static java.lang.reflect.Modifier.isAbstract;

/**
 * Utility class for inspecting Java types.
 */
@UtilityClass
public final class TypeInspector {

    /**
     * Checks whether the given type is a concrete class.
     *
     * @param type the class to inspect.
     * @return {@code true} if the class is concrete.
     */
    public static boolean isConcrete(Class<?> type) {
        return !type.isInterface() && !isAbstract(type.getModifiers());
    }

    /**
     * Checks whether the given type can be instantiated.
     *
     * @param type the class to inspect.
     * @return {@code true} if the class can be instantiated.
     */
    public static boolean isInstantiable(Class<?> type) {
        return isConcrete(type)
            && !type.isEnum()
            && !type.isAnnotation()
            && !type.isPrimitive();
    }

    /**
     * Checks whether the given type has a public no-args constructor.
     *
     * @param type the class to inspect.
     * @return {@code true} if a default constructor exists.
     */
    public static boolean hasDefaultConstructor(Class<?> type) {
        return defaultConstructor(type).isPresent();
    }

    /**
     * Returns the public no-args constructor, if present.
     *
     * @param type the class to inspect.
     * @return an optional default constructor.
     */
    public static Optional<Constructor<?>> defaultConstructor(Class<?> type) {
        try {
            return Optional.of(type.getDeclaredConstructor());
        } catch (NoSuchMethodException e) {
            return Optional.empty();
        }
    }

    /**
     * Returns the class hierarchy including the given type and its superclasses.
     *
     * @param type the class to inspect.
     * @return the class hierarchy.
     */
    public static List<Class<?>> hierarchy(Class<?> type) {
        List<Class<?>> hierarchy = new ArrayList<>();
        Class<?> current = type;

        while (current != null && current != Object.class) {
            hierarchy.add(current);
            current = current.getSuperclass();
        }

        return hierarchy;
    }

    /**
     * Returns all interfaces implemented by the given type.
     *
     * @param type the class to inspect.
     * @return a list of interfaces.
     */
    public static List<Class<?>> interfaces(Class<?> type) {
        List<Class<?>> interfaces = new ArrayList<>();
        collectInterfaces(type, interfaces);
        return interfaces;
    }

    /**
     * Recursively collects interfaces implemented by the given type.
     *
     * @param type       the class to inspect.
     * @param interfaces the list to collect interfaces into.
     */
    private static void collectInterfaces(Class<?> type, List<Class<?>> interfaces) {
        for (Class<?> iface : type.getInterfaces()) {
            if (!interfaces.contains(iface)) {
                interfaces.add(iface);
                collectInterfaces(iface, interfaces);
            }
        }

        Class<?> superclass = type.getSuperclass();
        if (superclass != null) {
            collectInterfaces(superclass, interfaces);
        }
    }
}
