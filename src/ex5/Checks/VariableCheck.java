package ex5.Checks;

import ex5.Parameters.Method;
import ex5.Parameters.Variable;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.function.Supplier;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * The VariableCheck class validates and processes variable declarations, assignments,
 * and their values according to the type and context.
 */
public class VariableCheck {

    private static final String FINAL_REGEX = "^\\s*final\\s+.*";
    private static final String TYPE_REGEX = "^\\S*";
    private static final String NAME_VALIDATION_REGEX = "^(?!\\d)(?!_$)\\w+(?=\\s*[,=;])";
    private static final String NAME_ASSIGNMENT_REGEX = "^(?!\\d)(?!_$)\\w+(?=\\s*=)\\s*.*;\\s*";
    private static final String NAME_COMA_REGEX = "^(?!\\d)(?!_$)\\w+(?=\\s*,)\\s*.*;\\s*";
    //private static final String NAME_COMA_REGEX = "^(?!\\d)(?!_$)\\w+(?=\\s*,)\\s*.*;\\s*";
    private static final String VALUE_REGEX = "^[^,;]+";
    private static final String BOOLEAN_VALUE_REGEX="^true|false$|^[+-]?\\d+$|^[+-]?(\\d+\\.\\d*|\\.\\d+)$";
    private static final String STRING_VALUE_REGEX = "^\\s*\".*\"\\s*$";
    private static final String CHAR_VALUE_REGEX = "^'.'$";
    private static final String DOUBLE_VALUE_REGEX = "^[+-]?(\\d+\\.\\d*|\\.\\d+)$";
    private static final String INT_VALUE_REGEX = "^[+-]?\\d+";
    private static final String VARIABLE_NAME_REGEX = "^(?!\\d)(?!_$)\\w*";
    private static final String INVALID_VARIABLE_NAME_FORMAT_MESSAGE = "Invalid variable name format";
    private static final String VARIABLE_NOT_DEFINED_MESSAGE = "The variable %s is not defined";
    private static final String SEMICOLON_REGEX = "\\s*;\\s*.*\\s*";
    private static final String NON_EMPTY_AFTER_SEMICOLON_REGEX = "\\s*;\\s*.+\\s*";
    private static final String COMMA_REGEX = "\\s*.*\\s*,.*";
    private static final String TYPE_NAME_REGEX = "\\s+(.+)";
    private static final String INVALID_TYPE_FORMAT_MESSAGE = "not in a format of type name =";
    private static final String INVALID_FINAL_FORMAT_MESSAGE = "just final is not premited";
    private static final String INVALID_NAME_MESSAGE = "Invalid name";
    private static final String NAME_ALREADY_USED_MESSAGE = "The name is used";
    private static final String INVALID_VALUE_MESSAGE = "Invalid value for type %s: %s";
    private static final String LINE_NEEDS_SEMICOLON_MESSAGE = "line need to finish with ;";
    private static final String FINAL_VAR_NO_ASSIGNMENT_MESSAGE = "need to assign in line final varrible";
    private static final String LINE_FINISH_SEMICOLON_OR_COMMA_MESSAGE="a line neded to finsh with ; or ,";
    private static final String INVALID_TYPE_ERROR = "No valid variable type found.";
    private static final String VARIABLE_TYPE_PATTERN = "^\\s*(int|String|double|char|boolean)\\s+(.*)";
    private static final String END_LINE = "\\s*\\)\\s*\\{\\s*";
    private static final int   TWO =2;
    private static final int ONE = 1;
    private static final int ZERO = 0;
    private static final String FINAL_VARIBLE = "final varrible cant be assigned";
    private static final String FINAL_COMPILE = "^\\s*final\\s+(.*)";
    private static final String EQWAL = "=";
    private static final String SEMI_COLON = ";";
    private static final String COMMA = ",";
    private static final String INT = "int";
    private static final String DOUBLE = "double";
    private static final String BOOLEAN = "boolean";
    private static final String STRING = "String";
    private static final String CHAR = "char";
    private static final String PARENTASIS_AMOUNT = "the amount of ) and ( need to be eqwal";
    private static final char LEFT_PARENTASIS = '(';
    private static final char RIGHT_PARENTASIS = ')';

    private List<HashMap<String, Variable>> variablesList;
    private HashMap<String, Variable> variables;
    private boolean isThisVaribleFinal;
    private String variableType;
    private String variableValue;
    private String variableName;

    /**
     * Constructor to initialize VariableCheck with a list of variable maps for scope resolution.
     *
     * @param variablesList The list of variable maps for different scopes.
     */
    public VariableCheck(List<HashMap<String, Variable>> variablesList) {
        this.variablesList = variablesList;
        this.variables = new HashMap<>();
    }

    /**
     * Validates and processes a variable declaration line.
     *
     * @param line The line to process.
     * @return A map of processed variables.
     * @throws Exception If the variable declaration is invalid.
     */
    public HashMap<String, Variable> checkVarible(String line) throws Exception {
        variableValue = null;
        variableType = null;
        isThisVaribleFinal = false;
        variableName = null;

        line = isItFinal(line);
        line = findType(line);
        return isNameValid(line);
//        while (line != null) {
//            line = isNameValid(line);
//            line = fillUpValue(variableType, line);
//            Variable variable = new Variable
//            (variableName, variableType, variableValue, isThisVaribleFinal);
//            variables.put(variableName, variable);
//            variablesList.add(variablesList.size() - ONE, variables);
//        }
//        return variables;

    }

    /**
     * Checks if the variable is declared as final.
     *
     * @param line The line to process.
     * @return The line without the "final" keyword.
     */
    protected String isItFinal(String line) {
        if (line.matches(FINAL_REGEX)) {
            Pattern pattern = Pattern.compile(FINAL_COMPILE);
            Matcher matcher = pattern.matcher(line);
            if (matcher.find()) {
                isThisVaribleFinal = true;
                return matcher.group(ONE);
            }
        }
        return line;
    }

    /**
     * Extracts and validates the variable type from the line.
     *
     * @param line The line to process.
     * @return The remaining part of the line.
     * @throws Exception If the type is invalid or missing.
     */

    public String findType(String line) throws Exception {
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

//    protected String findType(String line) throws Exception {
//        Pattern pattern = Pattern.compile(TYPE_REGEX);
//        Matcher matcher = pattern.matcher(line);
//        if (matcher.find()) {
//            switch (matcher.group()) {
//                case "int":
//                    variableType = "int";
//                    break;
//                case "double":
//                    variableType = "double";
//                    break;
//                case "String":
//                    variableType = "String";
//                    break;
//                case "boolean":
//                    variableType = "boolean";
//                    break;
//                case "char":
//                    variableType = "char";
//                    break;
//                default:
//                    throw new InishlizeVariblleException(INVALID_TYPE_FORMAT_MESSAGE);
//
//            }
//            pattern = Pattern.compile(TYPE_NAME_REGEX);
//            matcher = pattern.matcher(line);
//            if (matcher.find()) {
//                return matcher.group(ONE);
//            }
//            return null;
//        }
//        throw new InishlizeVariblleException(INVALID_FINAL_FORMAT_MESSAGE);
//    }

    /**
     * Validates the variable name and ensures it is not already used.
     *
     * @param name The variable name to validate.
     * @return The remaining part of the line after the name.
     * @throws Exception If the name is invalid or already used.
     */
    private HashMap<String, Variable> isNameValid(String name) throws Exception {
        while (name !=null) {
            Pattern pattern = Pattern.compile(NAME_VALIDATION_REGEX);
            Matcher matcher = pattern.matcher(name);

            if (matcher.find()) {
                String identifier = matcher.group();
                if (nameIsUsed(identifier)) {
                    throw new Exception(NAME_ALREADY_USED_MESSAGE);
                } else {
                    variableName = identifier;

                }


                if (name.matches(NAME_ASSIGNMENT_REGEX)) {
                    int equalsIndex = name.indexOf(EQWAL);
                    name = name.substring(equalsIndex + ONE).trim();
                    name = fillUpValue(variableType, name);
                } else if (name.matches(NAME_COMA_REGEX)) {
                    variableValue = null;
                    int equalsIndex = name.indexOf(COMMA);
                    name = name.substring(equalsIndex + ONE).trim();
                } else{
                    variableValue = null;
                    //int equalsIndex = name.indexOf(SEMI_COLON);
                    name =null;
                }
//                else{
//                    throw new InishlizeVariblleException("line ned to finish with ;");
//                }

                Variable variable = new Variable
                        (variableName, variableType, variableValue, isThisVaribleFinal);
                variables.put(variableName, variable);
                variablesList.add(variablesList.size() - ONE, variables);

            }

            else{
                throw new InishlizeVariblleException(INVALID_NAME_MESSAGE);
            }
        }
        return variables;

    }
//        Pattern pattern = Pattern.compile(NAME_VALIDATION_REGEX);
//        Matcher matcher = pattern.matcher(name);
//
//        if (matcher.find()) {
//            String identifier = matcher.group();
//            if (nameIsUsed(identifier)) {
//                throw new Exception(NAME_ALREADY_USED_MESSAGE);
//            } else {
//                variableName = identifier;
//            }
//
//            if (name.matches(NAME_ASSIGNMENT_REGEX)) {
//                int equalsIndex = name.indexOf(EQWAL);
//                return name.substring(equalsIndex + ONE).trim();
//            } else if(name.matches(NAME_COMA_REGEX)) {
//                int equalsIndex = name.indexOf(COMMA);
//                return name.substring(equalsIndex + ONE).trim();
//            }else{
//                int equalsIndex = name.indexOf(SEMI_COLON);
//                return name.substring(equalsIndex).trim();
//            }
//        }
//
//        throw new InishlizeVariblleException(INVALID_NAME_MESSAGE);


    /**
     * Checks if the variable name is already used in the current scope.
     *
     * @param name The variable name to check.
     * @return True if the name is already used, false otherwise.
     */
    private boolean nameIsUsed(String name) {
        if (variablesList.get(variablesList.size() - ONE).containsKey(name)) {
            return true;
        }
        return false;
    }

    /**
     * Validates and assigns a value to the variable based on its type.
     *
     * @param type The variable type.
     * @param input The input value.
     * @return The remaining part of the line after the value.
     * @throws Exception If the value is invalid or missing.
     */
    protected String fillUpValue(String type, String input) throws Exception {
        if (input.matches(SEMICOLON_REGEX)) {
            if (input.matches(NON_EMPTY_AFTER_SEMICOLON_REGEX)) {
                throw new InishlizeVariblleException(LINE_NEEDS_SEMICOLON_MESSAGE);
            } else {
                if (isThisVaribleFinal) {
                    throw new InishlizeVariblleException(FINAL_VAR_NO_ASSIGNMENT_MESSAGE);
                }
                variableValue = null;
                return null;
            }
        } else {
            Pattern pattern = Pattern.compile(VALUE_REGEX);
            Matcher matcher = pattern.matcher(input);
            if (matcher.find()) {
                String value = matcher.group().trim();
                if (!validateByType(type, value)) {
                    throw new InishlizeVariblleException
                            (String.format(INVALID_VALUE_MESSAGE, type, value));
                }

                if (input.matches(COMMA_REGEX)) {
                    int equalsIndex = input.indexOf(COMMA);
                    return input.substring(equalsIndex + ONE).trim();
                } else {
                    return null;
                }
            }
            throw new InishlizeVariblleException(LINE_FINISH_SEMICOLON_OR_COMMA_MESSAGE);
        }
    }

    /**
     * Validates an input value based on the variable type.
     *
     * @param type The variable type.
     * @param input The input value.
     * @return True if the input matches the expected type, false otherwise.
     * @throws Exception If the input is invalid.
     */
    protected boolean validateByType(String type, String input) throws Exception {
        switch (type) {
            case INT:
                if (input.matches(INT_VALUE_REGEX)) {
                    variableValue = input;
                    return true;
                } else {
                    return isValidArgument(type, input);
                }
            case DOUBLE:
                if (input.matches(DOUBLE_VALUE_REGEX)) {
                    variableValue = input;
                    return true;}
                else if(input.matches(INT_VALUE_REGEX)) {
                    variableValue = input;
                    return true;
                } else {
                    return isValidArgument(type, input);
                }
            case STRING:
                if (input.matches(STRING_VALUE_REGEX)) {
                    variableValue = input;
                    return true;
                } else {
                    return isValidArgument(type, input);
                }
            case BOOLEAN:
                if (input.matches(BOOLEAN_VALUE_REGEX)) {
                    variableValue = input;
                    return true;
                } else {
                    return isValidArgument(type, input);
                }
            case CHAR:
                if (input.matches(CHAR_VALUE_REGEX)) {
                    variableValue = input;
                    return true;
                } else {
                    return isValidArgument(type, input);
                }
            default:
                return false;
        }
    }

    /**
     * Checks if the input matches a valid argument in the required type.
     *
     * @param type The variable type.
     * @param input The input value to check.
     * @return True if the input matches a valid argument, false otherwise.
     * @throws Exception If the input is invalid.
     */
    private boolean isValidArgument(String type, String input) throws Exception {
        for (int i = variablesList.size() - ONE; i >= ZERO; i--) {
            HashMap<String, Variable> variables = variablesList.get(i);

                if (type.equals(BOOLEAN)||type.equals(DOUBLE)) {
                    if (variables.containsKey(input) &&
                            validateByType(type, variables.get(input).getVariableValue())
                            && variables.get(input).getVariableValue() != null) {
                        variableValue = variables.get(input).getVariableValue();
                        return true;
                    }
                }
                else {
                    if (variables.containsKey(input) &&variables.get(input).getVariableType().equals(type)
                            && variables.get(input).getVariableValue() != null) {
                        variableValue = variables.get(input).getVariableValue();
                        return true;
                    }
                }
        }
        return false;
    }

    /**
     * Validates and processes a variable assignment.
     *
     * @param line The assignment line to process.
     * @return A map of updated variables.
     * @throws Exception If the assignment is invalid.
     */


    public HashMap<String, Variable> getAssigment(String line) throws Exception {
        variableValue = null;
        variableType = null;
        variableName = null;
        variables = new HashMap<>();

        while (line!=null){
            line = chekIfNameExsist(line);
            line = fillUpValue(variableType, line);
//            for(int i = variablesList.size()- 1;i>=0;i--){
//                HashMap<String, Variable> variables = variablesList.get(i);
//
//                if (variables.containsKey(input) && variables.get(input).getVariableType().equals(type)
//                        && variables.get(input).getVariableValue() != null) {
//                    variableValue = variables.get(input).getVariableValue();
//                    return true;

            if (variables!=null&&variables.containsKey(variableName)) {
                if(!variables.get(variableName).isThisVaribleFinal()) {
                    variables.get(variableName).setVariableValue(variableValue);
                }
                throw new AssigmentException(FINAL_VARIBLE);
            }
            else {

                Variable variable = new Variable(variableName, variableType, variableValue,
                        isThisVaribleFinal);
                variables.put(variableName, variable);

            }
        }
        return variables;

    }

    /**
     * Checks if the variable name exists in the current or parent scopes.
     *
     * @param line The line containing the variable to check.
     * @return The remaining part of the line after the variable name.
     * @throws Exception If the variable does not exist or the line is invalid.
     */
    private String chekIfNameExsist(String line) throws Exception {
        Pattern pattern = Pattern.compile(VARIABLE_NAME_REGEX);
        Matcher matcher = pattern.matcher(line);
        int equalsIndex = line.indexOf(EQWAL);
        // Check if the pattern matches
        if (matcher.find()) {
            for (int i = variablesList.size() - ONE; i >= ZERO; i--) {
                HashMap<String, Variable> v = variablesList.get(i);
                if (v.containsKey(matcher.group())) {

                    variableName = matcher.group();
                    if(v.get(variableName).isThisVaribleFinal()){
                        throw new AssigmentException(FINAL_VARIBLE);
                    }
                    variableType = v.get(variableName).getVariableType();
                    isThisVaribleFinal = v.get(variableName).isThisVaribleFinal();
                    return line.substring(equalsIndex + ONE).trim();
                }
            }
            throw new Exception(String.format(VARIABLE_NOT_DEFINED_MESSAGE, matcher.group()));
        }
        throw new Exception(INVALID_VARIABLE_NAME_FORMAT_MESSAGE);
    }

    /**
     * Checks if the given input string has balanced parentheses.
     * @param input The string to be checked for balanced parentheses.
     * @throws ConditionException if the number of opening and closing parentheses do not match.
     * @throws Exception if any other unexpected error occurs.
     */
    protected void checkBracets(String input) throws Exception {
        int openCount = ZERO;  // Count of '('
        int closeCount = ZERO; // Count of ')'

        for (char c : input.toCharArray()) {
            if (c == LEFT_PARENTASIS) {
                openCount++;
            } else if (c == RIGHT_PARENTASIS) {
                closeCount++;
            }
        }
        if(openCount!=closeCount){
            throw new ConditionException(PARENTASIS_AMOUNT);
        }
    }
}



