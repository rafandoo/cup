package br.dev.rplus.cup.enums;

/**
 * Class to store typed values. Used to store values of different types.
 * Can be used in conjunction with Enums.
 */
public class TypedValue {

    private final Object value;

    /**
     * Constructor.
     *
     * @param value the value to store.
     */
    public TypedValue(Object value) {
        this.value = value;
    }

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
}
