package br.dev.rplus.cup.object;

import br.dev.rplus.cup.log.Logger;

import java.lang.reflect.Field;

/**
 * Utility class to close an object and its fields.
 */
public abstract class ObjectCloser {

    /**
     * Closes an object and its fields.
     *
     * @param object object to be closed.
     */
    public static void close(Object object) {
        if (object == null) {
            return;
        }
        try {
            for (Field field : object.getClass().getDeclaredFields()) {
                try {
                    if (field.getType().isPrimitive()) {
                        setPrimitiveDefault(field, object);
                    } else {
                        field.set(object, null);
                    }
                } catch (IllegalAccessException e) {
                    Logger.error("Failed to close field {%s} of object %s", field.getName(), object.getClass().getName());
                }
            }
        } catch (Exception e) {
            Logger.error("Error finalizing object: %s", object.getClass().getName());
        } finally {
            object = null;
        }
    }

    /**
     * Closes an array of objects and their fields.
     *
     * @param objects objects to be closed.
     */
    public static void close(Object... objects) {
        for (Object object : objects) {
            close(object);
        }
    }

    /**
     * Sets the default value for a given primitive field in an object.
     * <p>
     * This method assigns the default value to a field of primitive type within the provided object.
     * The default values assigned are:
     * - `boolean`: false
     * - `byte`: 0
     * - `short`: 0
     * - `int`: 0
     * - `long`: 0L
     * - `float`: 0.0f
     * - `double`: 0.0
     * - `char`: '\u0000' (null character)
     *
     * @param field  the field for which the default value should be set.
     * @param object the object whose field will be set to the default value.
     * @throws IllegalAccessException if the field is not accessible.
     */
    private static void setPrimitiveDefault(Field field, Object object) throws IllegalAccessException {
        Class<?> type = field.getType();
        if (type == boolean.class) {
            field.setBoolean(object, false);
        } else if (type == byte.class) {
            field.setByte(object, (byte) 0);
        } else if (type == short.class) {
            field.setShort(object, (short) 0);
        } else if (type == int.class) {
            field.setInt(object, 0);
        } else if (type == long.class) {
            field.setLong(object, 0L);
        } else if (type == float.class) {
            field.setFloat(object, 0.0f);
        } else if (type == double.class) {
            field.setDouble(object, 0.0);
        } else if (type == char.class) {
            field.setChar(object, '\u0000');
        }
    }
}
