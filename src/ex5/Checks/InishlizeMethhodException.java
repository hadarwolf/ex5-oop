package ex5.Checks;

/**
 * The InishlizeMethhodException class represents a custom exception thrown when
 * there is an error during the initialization of a method.
 * This exception is used to indicate issues such as invalid or improper method initialization.
 */
public class InishlizeMethhodException extends Exception {

    /**
     * Constructs a new InishlizeMethhodException with a specified error message.
     *
     * @param message The detail message explaining the cause of the exception.
     */
    public InishlizeMethhodException(String message) {
        super("InishlizeMethhodException " + message);
    }
}
