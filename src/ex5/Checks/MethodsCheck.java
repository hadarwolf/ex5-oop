package ex5.Checks;

import ex5.Parameters.Method;
import ex5.Parameters.Variable;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * The MethodsCheck class validates method declarations and variable declarations within methods.
 */
public class MethodsCheck extends VariableCheck {

    private static final String END_METHOD_STATMENT ="method need to finish with:  ){" ;


    // Regex patterns
    private static final String METHOD_NAME_PATTERN = "^\\s*\\w+\\s+([a-zA-Z]\\w*)\\s*\\(";
    private static final String VARIABLE_NAME_PATTERN = "^(?!\\d)(?!_$)(\\w+)(?=\\s*[,\\)])";
    private static final String VARIABLE_TYPE_PATTERN = "^\\s*(int|String|double|char|boolean)\\s+(.*)";

    // Error messages
    private static final String NAME_USED_ERROR = "The name '%s' is already in use.";
    private static final String INVALID_NAME_ERROR = "Invalid method or variable name.";
    private static final String INVALID_TYPE_ERROR = "No valid variable type found.";

    private static final int ONE =1;
    private static final int TWO =2;
    private static final String OPEN_PRANESS = "(";
    private static final String IS_FINSH_WITH_COMA = "^(?!\\d)(?!_$)\\w+\\s*,.*";
    private static final String IS_FINISH_WITH_PRENTESES ="\\s*\\)\\s*\\{\\s*";
    private static final String COMA =",";
    private static final String END_LINE = "\\s*\\)\\s*\\{\\s*";
    private static final String FINAL ="final ";
    // Instance variables
    private ArrayList<Variable> variableList;
    private  HashMap<String, Method> methods;
    private boolean isVariableFinal;
    private String variableType;
    private String variableName;
    private String methodName;

    /**
     * Constructor for MethodsCheck.
     *
     * @param variablesList A list of variable scopes represented by HashMaps.
     */
    public MethodsCheck(List<HashMap<String, Variable>> variablesList) {
        super(variablesList);
    }

    /**
     * Validates and parses a method declaration line.
     *
     * @param line The method declaration line.
     * @return A Method object containing method details.
     * @throws Exception If the method declaration is invalid.
     */
    public Method checkMethod(String line,HashMap<String, Method> method) throws Exception {
        variableName = null;
        variableType = null;
        methodName = null;
        isVariableFinal = false;
        variableList = new ArrayList<>();
        methods = method;
        checkBracets(line);
        line = validateMethodName(line);
        while (line != null) {
            line = checkIfFinal(line);
            line = findVariableType(line);
            if (line == null) {
                break;
            }
            line = extractVariableName(line);
            variableList.add(new Variable(variableName, variableType, null, isVariableFinal));
        }
//        METHODS.put(methodName,variableList);
        return new Method(methodName, variableList);
    }

    /*
     * Validates and extracts the method name from the line.
     *
     * @param line The line containing the method declaration.
     * @return The remaining part of the line after the method name.
     * @throws Exception If the method name is invalid or already used.
     */
    private String validateMethodName(String line) throws Exception {
        Pattern pattern = Pattern.compile(METHOD_NAME_PATTERN);
        Matcher matcher = pattern.matcher(line);

        if (matcher.find()) {
            String identifier = matcher.group(ONE);
            if (isNameUsed(identifier)) {
                throw new InishlizeMethhodException(String.format(NAME_USED_ERROR, identifier));
            }
            methodName = identifier;
            int startIndex = line.indexOf(OPEN_PRANESS);
            return line.substring(startIndex + ONE).trim();
        }

        throw new InishlizeMethhodException(INVALID_NAME_ERROR);
    }

    /**
     * Checks if a name is already used in the current context.
     *
     * @param name The name to check.
     * @return true if the name is used, false otherwise.
     */
    private boolean isNameUsed(String name) {
        return methods.containsKey(name);
    }

    /**
     * Extracts the variable name from the line.
     *
     * @param line The line containing the variable declaration.
     * @return The remaining part of the line after the variable name.
     * @throws Exception If the variable name is invalid or already used.
     */
    private String extractVariableName(String line) throws Exception {
        Pattern pattern = Pattern.compile(VARIABLE_NAME_PATTERN);
        Matcher matcher = pattern.matcher(line);

        if (matcher.find()) {
            String identifier = matcher.group(ONE);
            if (isNameUsedInMethod(identifier)) {
                throw new Exception(String.format(NAME_USED_ERROR, identifier));
            }
            variableName = identifier;

            if (line.matches(IS_FINSH_WITH_COMA)) {
                int commaIndex = line.indexOf(COMA);
                return line.substring(commaIndex + ONE).trim();
            }
            else if (line.matches(variableName+IS_FINISH_WITH_PRENTESES)) {
                return null;
            }
            throw new InishlizeMethhodException(END_METHOD_STATMENT);
        }

        throw new InishlizeMethhodException(INVALID_NAME_ERROR);
    }

    /**
     * Checks if a variable name is already used within the method.
     *
     * @param identifier The variable name to check.
     * @return true if the name is used, false otherwise.
     */
    private boolean isNameUsedInMethod(String identifier) {
        for (Variable variable : variableList) {
            if (variable.getVariableName().equals(identifier)) {
                return true;
            }
        }
        return false;
    }

    /**
     * Finds and validates the variable type from the line.
     *
     * @param line The line containing the variable declaration.
     * @return The remaining part of the line after the variable type.
     * @throws Exception If no valid variable type is found.
     */
    public String findVariableType(String line) throws Exception {
        Pattern pattern = Pattern.compile(VARIABLE_TYPE_PATTERN);
        Matcher matcher = pattern.matcher(line);

        if (matcher.find()) {
            variableType = matcher.group(ONE);
            return matcher.group(TWO).trim();
        } else if (line.matches(END_LINE)) {
            variableType = null;
            variableName = null;
            return null;
        }
        throw new InishlizeMethhodException(INVALID_TYPE_ERROR);
    }

    /**
     * Checks if a variable is declared as final in the line.
     *
     * @param line The line containing the declaration.
     * @return The remaining part of the line after checking for 'final'.
     */
    private String checkIfFinal(String line) {
        if (line.startsWith(FINAL)) {
            isVariableFinal = true;
            return line.substring(FINAL.length()).trim();
        }
        return line;
    }
}
