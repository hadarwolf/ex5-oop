package ex5.Scopes;
/**
 * Custom exception class for syntax errors in the program.
 * This exception is thrown when a syntax-related issue is encountered
 * during parsing or execution.
 */
public class SyntaxErrorException extends Exception {
    private static final String SYTAZ_ERROR = "SyntaxErrorException ";

    /**
     * Constructs a new SyntaxErrorException with a specified error message.
     *
     * @param message The detailed error message describing the syntax error.
     */
    public SyntaxErrorException(String message) {
        super( SYTAZ_ERROR +message);
    }
}
