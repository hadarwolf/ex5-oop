package ex5.Checks;

/**
 * Represents an exception that is thrown when an invalid assignment operation is detected.
 * This exception is specifically used to handle assignment-related errors in the program.
 */
public class AssigmentException extends Exception {

    // Constant to hold the exception prefix
    private static final String EXCEPTION_PREFIX = "AssignmentException: ";

    /**
     * Constructs a new AssignmentException with a specific error message.
     *
     * @param message The detailed error message describing the reason for the exception.
     */
    public AssigmentException(String message) {
        super(EXCEPTION_PREFIX + message);
    }
}
