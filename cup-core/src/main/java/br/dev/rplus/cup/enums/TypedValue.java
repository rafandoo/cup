package br.dev.rplus.cup.enums;

import lombok.AllArgsConstructor;

/**
 * Class to store typed values. Used to store values of different types.
 * Can be used in conjunction with Enums.
 */
@SuppressWarnings("unused")
@AllArgsConstructor
public class TypedValue {

    private final Object value;

    /**
     * Returns the value as the given type.
     *
     * @param type the type to cast the value (e.g. String.class).
     * @param <T>  the type to cast the value.
     * @return the value as the given type.
     */
    @SuppressWarnings("unchecked")
    public <T> T getTypedValue(Class<T> type) {
        if (type.isInstance(this.value)) {
            return (T) this.value;
        } else {
            String clazz = this.value.getClass().getName();
            throw new ClassCastException("Cannot cast " + clazz + " to " + type.getName());
        }
    }

    /**
     * Returns the raw value.
     *
     * @return the raw value.
     */
    public Object getRawValue() {
        return this.value;
    }

    /**
     * Returns the value as a string.
     *
     * @return the value as a string.
     */
    public String asString() {
        return this.getTypedValue(String.class);
    }

    /**
     * Returns the value as an integer.
     *
     * @return the value as an integer.
     */
    public int asInteger() {
        return this.getTypedValue(Integer.class);
    }

    /**
     * Returns the value as a boolean.
     *
     * @return the value as a boolean.
     */
    public boolean asBoolean() {
        return this.getTypedValue(Boolean.class);
    }
}
