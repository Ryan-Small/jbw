package com.harbinger.jbw;

/**
 * Helper class for argument verification.
 */
public class Verifier {

    /**
     * Verifies that the {@code object} is not {@code null}. This method is designed primarily for
     * doing parameter validation in methods and constructors, as demonstrated below:
     *
     * <pre>
     * public Foo(final Bar bar) {
     *     this.bar = Verifier.requireNonNull(bar);
     * }
     * </pre>
     *
     * @param object
     *            the Object to check for nullity.
     *
     * @return the {@code object}
     *
     * @throws IllegalArgumentException
     *             Thrown if {@code object} is {@code null}.
     */
    public static <T> T requireNonNull(final T object) {
        if (object == null) {
            throw new IllegalArgumentException();
        }
        return object;
    }

    /**
     * Verifies that the {@code object} is not {@code null}. This method is designed primarily for
     * doing parameter validation in methods and constructors, as demonstrated below:
     *
     * <pre>
     * public Foo(final Bar bar) {
     *     this.bar = Verifier.requireNonNull(bar, &quot;bar cannot be null&quot;);
     * }
     * </pre>
     *
     * @param object
     *            the Object to check for nullity
     *
     * @param message
     *            the message to use if the exception is thrown
     *
     * @return the {@code object}
     *
     * @throws IllegalArgumentException
     *             Thrown if {@code object} is {@code null}.
     */
    public static <T> T requireNonNull(final T object, final String message) {
        if (object == null) {
            throw new IllegalArgumentException(message);
        }
        return object;
    }
}
