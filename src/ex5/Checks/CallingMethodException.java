package ex5.Checks;

/**
 * Represents an exception that is thrown when there is an error during a method call.
 * This exception is specifically used to handle method invocation-related errors in the program.
 */
public class CallingMethodException extends Exception {

    // Constant to hold the exception prefix
    private static final String EXCEPTION_PREFIX = "CallingMethodException: ";

    /**
     * Constructs a new CallingMethodException with a specific error message.
     *
     * @param message The detailed error message describing the reason for the exception.
     */
    public CallingMethodException(String message) {
        super(EXCEPTION_PREFIX + message);
    }
}
