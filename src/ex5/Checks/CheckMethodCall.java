package ex5.Checks;

import ex5.Parameters.Method;
import ex5.Parameters.Variable;

import java.util.HashMap;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * This class validates method calls in the code by checking if the method exists
 * and verifying that the arguments passed to the method match the expected types.
 */
public class CheckMethodCall extends VariableCheck {

    // Regex patterns and constant strings
    private static final String METHOD_NAME_PATTERN = "^\\s*([a-zA-Z]\\w*)\\s*\\(";
    private static final String ARGUMENT_PATTERN = "^\\s*(.*?)\\s*[,\\)]";
    private static final String EXTRA_ARGUMENTS_ERROR = " receives more arguments than expected.";
    private static final String FEWER_ARGUMENTS_ERROR = " receives fewer arguments than expected.";
    private static final String METHOD_DOES_NOT_EXIST_ERROR = "The method %s does not exist.";
    private static final String INVALID_METHOD_CALL_ERROR = "Invalid method call format.";
    private static final String INVALID_TYPE_ERROR = "%s is not of type: %s";
    private static final int ONE =1;
    private static final String OPEN_BRACET = "(";
    private static final String FINISH_WITH_COMA = "^\\s*.*\\s*,\\s*.*";
    private static final String COMA = ",";
    private static final String END = "\\s*\\)\\s*;\\s*";

    // Map of method names to their corresponding Method objects
    private HashMap<String, Method> methods;

    // Name of the method being validated
    private String methodName;

    /**
     * Constructs a CheckMethodCall object with a list of variables and a map of methods.
     *
     * @param variables A list of variable scopes represented by HashMaps.
     * @param methods   A map of method names to their corresponding Method objects.
     */
    public CheckMethodCall
    (List<HashMap<String, Variable>> variables, HashMap<String, Method> methods) {
        super(variables);
        this.methods = methods;
    }

    /**
     * Validates the method call in the provided line of code.
     *
     * @param line The line of code containing the method call.
     * @throws Exception If the method call is invalid or the arguments are incorrect.
     */
    public void checkMethodCall(String line) throws Exception {
        checkBracets(line);
        methodName = null; // Reset the method name
        line = checkIfMethodExists(line); // Validate and extract the method name
        checkSentValues(line); // Validate the arguments passed to the method
    }

    /*
     * Validates the arguments passed to the method.
     *
     * @param line The string containing the arguments to the method.
     * @throws Exception If the arguments do not match the method's expected types.
     */
    private void checkSentValues(String line) throws Exception {
        for (Variable expectedVariable : methods.get(methodName).getVarList()) {
            String expectedType = expectedVariable.getVariableType();

            // Regex pattern to match a single argument until a comma or closing parenthesis
            Pattern pattern = Pattern.compile(ARGUMENT_PATTERN);
            Matcher matcher = pattern.matcher(line);

            if (matcher.find()) {
                String argument = matcher.group(ONE).trim();
                if (!validateByType(expectedType, argument)) {
                    throw new CallingMethodException(String.format(INVALID_TYPE_ERROR, argument,
                            expectedType));
                }

                // If there are more arguments, extract the remaining part of the line
                if (line.matches(FINISH_WITH_COMA)) {
                    int commaIndex = line.indexOf(COMA);
                    line = line.substring(commaIndex + ONE).trim();
                } else {
                    line = null; // No more arguments
                }
            } else {
                throw new CallingMethodException(methodName + FEWER_ARGUMENTS_ERROR);
            }
        }
        if (line != null && line.matches(END)) {
            line = null;
        }

        // Check if there are extra arguments left
        if (line != null) {
            throw new CallingMethodException(methodName + EXTRA_ARGUMENTS_ERROR);
        }
    }

    /*
     * Checks if the method exists and extracts its name.
     *
     * @param line The line containing the method call.
     * @return The remaining part of the line after the method name and opening parenthesis.
     * @throws Exception If the method does not exist or is invalid.
     */
    private String checkIfMethodExists(String line) throws Exception {
        // Regex pattern to match the method name
        Pattern pattern = Pattern.compile(METHOD_NAME_PATTERN);
        Matcher matcher = pattern.matcher(line);

        if (matcher.find()) {
            String potentialMethodName = matcher.group(ONE);

            if (methods.containsKey(potentialMethodName)) {
                methodName = potentialMethodName;
                int openParenthesisIndex = line.indexOf(OPEN_BRACET);
                return line.substring(openParenthesisIndex + ONE).trim();
            }
            throw new CallingMethodException
                    (String.format(METHOD_DOES_NOT_EXIST_ERROR, potentialMethodName));
        }
        throw new CallingMethodException(INVALID_METHOD_CALL_ERROR);
    }
}
