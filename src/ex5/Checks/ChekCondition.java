package ex5.Checks;

import ex5.Parameters.Variable;

import java.util.HashMap;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * The ChekCondition class validates conditional statements in the code.
 * It checks if the values in a condition expression are of the correct type (boolean).
 */
public class ChekCondition extends VariableCheck {

    // Regex patterns
//    private static final String CONDITION_PATTERN =
//    "^\\s*\\w+\\s*\\(\\s*(.*?)\\s*(\\|\\||&&|\\))\\s*\\{\\s*";
    private static final int ONE = 1;
    private  static final int ZERO = 0;
    private static final String BOOLEAN = "boolean";
    private static final String OR_LINE ="\\(|\\)";
    private static final String OR_AND ="\\|\\||&&";

    // Error messages
    private static final String INVALID_TYPE_ERROR = "%s is not of type: %s.";
    private static final String EMPTY_STATEMENT_ERROR = "The statement is empty.";

    /**
     * Constructs a ChekCondition object with a list of variable scopes.
     *
     * @param variablesList A list of variable scopes represented by HashMaps.
     */
    public ChekCondition(List<HashMap<String, Variable>> variablesList) {
        super(variablesList);
    }

    /**
     * Checks if a conditional statement is valid.
     *
     * @param line The line of code containing the conditional statement.
     * @return true if the statement is valid, false otherwise.
     * @throws Exception If the statement is invalid or an error occurs during validation.
     */
    public boolean chekStatment(String line) throws Exception {
        return chekSentValues(line);
    }

    /*
     * Validates the individual conditions in the statement.
     *
     * @param line The line of code containing the conditions.
     * @return true if all conditions are valid, false otherwise.
     * @throws Exception If any condition is invalid.
     */
    private boolean chekSentValues(String line) throws Exception {
        checkBracets(line);
        String[] condition = line.split(OR_LINE);
        String[] parts = condition[ONE].trim().split(OR_AND);

        for (int i = ZERO; i < parts.length ; i++) {
            if (!validateByType(BOOLEAN, parts[i].trim())) {
                throw new ConditionException(String.format(INVALID_TYPE_ERROR, parts[i].trim(), BOOLEAN));
            }
        }
        return true;
    }
}
//        while (line!=null) { // Continue until the line is empty
//
//
//
//           // Pattern pattern = Pattern.compile("^\\s*\\w+\\s*\\(\\s*(.*?)\\s*(\\| | &|\\))\\s*\\{\\s*");
//            //Pattern pattern = Pattern.compile("^\\s*\\w+\\s*\\(\\s*(.*?)\\s*[^|&)]\\s*\\{\\s*");
//            //Pattern pattern = Pattern.compile("^\\s*\\w+\\s*\\(\\s*([^|&]*)\\s*[|&)]\\s*\\{\\s*");
//            Pattern pattern = Pattern.compile("^\\s*\\w*\\s*\\(*\\s*([^|&)]+).*");
//
//
//            //Pattern pattern = Pattern.compile(CONDITION_PATTERN);
//            Matcher matcher = pattern.matcher(line);
//
//            if (matcher.find()) {
//                String conditionValue = matcher.group(ONE).trim();
//                if (!validateByType(BOOLEAN, conditionValue)) {
//                    throw new ConditionException(String.format(INVALID_TYPE_ERROR,
//                    conditionValue, BOOLEAN));
//                }
//                // Update the line to process the remaining conditions
////                line = line.substring(matcher.end()).trim();
//                if (line.matches(".*\\|\\|.*")) {
//                    int equalsIndex = line.indexOf("|");
//                    line = line.substring(equalsIndex + 2).trim();
//                } else if (line.matches(".*&&.*")) {
//                    int equalsIndex = line.indexOf("&");
//                    line = line.substring(equalsIndex+ 2).trim();
//                }
//                else if(line.matches(".*\\).*")) {
//                    line = null;
//                }
//                else {
//                    throw new ConditionException("invallid syntax block");
//                }
//
//            }
//            else {
//                throw new ConditionException(EMPTY_STATEMENT_ERROR);
//            }
//        }
//        return true;
//    }
//    private boolean chekSentValues(String line) throws Exception {
//        while (!line.trim().isEmpty()) { // Continue until the line is empty
//            Pattern pattern = Pattern.compile("^(true|false|\\d+|\\w+|'[^']'
//            |\"[^\"]\"|\\d+\\.\\d+)\\s*(\\|\\||&&|$)");
//            Matcher matcher = pattern.matcher(line);
//
//            if (matcher.find()) {
//                // Capture the condition value
//                String conditionValue = matcher.group(1).trim();
//                if (!validateByType("boolean", conditionValue)) {
//                    throw new ConditionException(String.format("Invalid condition
//                    type: %s, expected: boolean", conditionValue));
//                }
//
//                // Update the line to process the remaining conditions
//                line = line.substring(matcher.end()).trim();
//            } else {
//                throw new ConditionException("Empty or invalid condition statement.");
//            }
//        }
//        return true;
//    }

//}
