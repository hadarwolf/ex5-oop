package ex5.Checks;

/**
 * The InishlizeVariblleException class represents a custom exception thrown when
 * there is an error during the initialization of a variable.
 * This exception is used to indicate issues such as invalid or improper variable initialization.
 */
public class InishlizeVariblleException extends Exception {

    /**
     * Constructs a new InishlizeVariblleException with a specified error message.
     *
     * @param message The detail message explaining the cause of the exception.
     */
    public InishlizeVariblleException(String message) {
        super("InishlizeVariblleException :" + message);
    }
}