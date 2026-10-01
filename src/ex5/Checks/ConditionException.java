package ex5.Checks;

/**
 * The ConditionException class represents a custom exception thrown when a condition validation fails.
 * This exception is used to indicate issues with conditions in code, such as invalid or malformed conditions.
 */
public class ConditionException extends Exception {

    /**
     * Constructs a new ConditionException with a specified error message.
     *
     * @param message The detail message explaining the cause of the exception.
     */
    public ConditionException(String message) {
        super("ConditionException: " + message);
    }
}
