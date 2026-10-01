package ex5.Scopes;

import ex5.Checks.CheckMethodCall;
import ex5.Checks.ChekCondition;
import ex5.Checks.VariableCheck;
import ex5.Parameters.Method;
import ex5.Parameters.Variable;

import java.util.HashMap;
import java.util.List;

/**
 * The Scope class handles scope validation, including variable and method management
 * within nested or conditional blocks (e.g., if, while).
 */
public class Scope {
    private static final String COMMENT_OR_BLANK_REGEX = "^//|^\\s*$";
    private static final String VARIABLE_DECLARATION_REGEX = "^\\s*(int|String|double|char|boolean|final)" +
            "\\b\\s+.*;\\s*";
    private static final String VARIABLE_ASSIGNMENT_REGEX = "^(?!\\d)(?!_$)\\w+\\s*=\\s*.*;\\s*";
    private static final String METHOD_CALL_REGEX = "^\\s*[a-zA-Z]\\w*\\s*\\(.*\\)\\s*;\\s*$";
    private static final String RETURN_STATEMENT_REGEX = "^\\s*return\\s*;\\s*$";
    private static final String CLOSING_BRACE_REGEX = "^\\s*}\\s*$";
    private static final String CONDITIONAL_BLOCK_REGEX = "^\\s*(if|while)\\b\\s*\\(.*\\)\\s*\\{\\s*";
    private static final int ONE = 1;
    private static final String MISSING_END = "mis }";
    private static final String UNRECOGNIZED_LINE = "unrecognized line";

    private List<HashMap<String, Variable>> variables;
    private HashMap<String, Method> methods;
    private VariableCheck variableCheck;
    private Scope child;
    private boolean inscope;
    private boolean endMethod;
    private CheckMethodCall check;
    private ChekCondition chekStatment;
    private Method corentMethod;


    /**
     * Constructor to initialize a Scope instance.
     *
     * @param variables List of variable maps for scope resolution.
     * @param methods   Map of defined methods.
     */
    public Scope(List<HashMap<String, Variable>> variables, HashMap<String, Method> methods) {
        this.variables = variables;
        this.methods = methods;
        this.variableCheck = new VariableCheck(variables);
        this.inscope = false;
        this.check = new CheckMethodCall(variables, methods);
        this.chekStatment = new ChekCondition(variables);
    }


    /**
     * Processes a single line of code within the current scope.
     *
     * @param line The line of code to process.
     * @return True if the scope is still active, false if it ends.
     * @throws Exception If a validation error occurs.
     */
    public boolean run(String line) throws Exception {
        if (inscope) {
            inscope = child.run(line);
            return true;
        } else {
            if (line.matches(COMMENT_OR_BLANK_REGEX)) {
                return true;
            } else if (line.matches(VARIABLE_DECLARATION_REGEX)) {
                variables.set(ONE, variableCheck.checkVarible(line));
                endMethod = false;
                return true;
            } else if (line.matches(VARIABLE_ASSIGNMENT_REGEX)) {
                variables.set(ONE, variableCheck.getAssigment(line));
                endMethod = false;
                return true;
            } else if (line.matches(METHOD_CALL_REGEX)) {
                check.checkMethodCall(line);
                endMethod = false;
                return true;
            } else if (line.matches(RETURN_STATEMENT_REGEX)) {
                endMethod = true;
                return true;
            } else if (line.matches(CLOSING_BRACE_REGEX)) {
                variables.remove(variables.size() - ONE);
                endMethod = false;
                return false;

            } else if (line.matches(CONDITIONAL_BLOCK_REGEX)) {
                if (chekStatment.chekStatment(line)) {
                    HashMap<String, Variable> newVars = new HashMap<>();
                    variables.add(newVars);
                    child = new Scope(variables, methods);
                    inscope = true;
                    return true;
                }
            } else {
                throw new SyntaxErrorException(UNRECOGNIZED_LINE);
            }
            throw new SyntaxErrorException(MISSING_END);
        }
    }
}

//    private void canBeHere() throws Exception {
//        if (endMethod) {
//            throw new SyntaxErrorException("} need to be one line after return");
//        }
//    }


//}

