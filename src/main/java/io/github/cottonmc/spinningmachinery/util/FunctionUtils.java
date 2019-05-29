package io.github.cottonmc.spinningmachinery.util;

import java.util.Objects;
import java.util.function.Function;

public final class FunctionUtils {
    /**
     * Applies the function to the value if the value is not null, otherwise returns null.
     *
     * @param value the nullable value
     * @param function the function
     * @param <T> the input type
     * @param <R> the return type
     *
     * @return the result of {@code function.apply(value)} if the value is not null, otherwise null
     */
    public static <T, R> R runNullable(T value, Function<T, R> function) {
        if (value == null)
            return null;
        else
            return Objects.requireNonNull(function, "function must not be null").apply(value);
    }
}
