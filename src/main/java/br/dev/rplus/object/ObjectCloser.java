package br.dev.rplus.object;

import br.dev.rplus.log.LoggerFactory;

import java.lang.reflect.Field;

public abstract class ObjectCloser {

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
                    LoggerFactory.error("Failed to close field {%s} of object %s", field.getName(), object.getClass().getName());
                }
            }
        } catch (Exception e) {
            LoggerFactory.error( "Error finalizing object: %s", object.getClass().getName());
        } finally {
            object = null;
        }
    }

    public static void close(Object... objects) {
        for (Object object : objects) {
            close(object);
        }
    }

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
