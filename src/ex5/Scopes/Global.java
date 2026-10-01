package ex5.Scopes;

import ex5.Checks.InishlizeMethhodException;
import ex5.Checks.InishlizeVariblleException;
import ex5.Checks.MethodsCheck;
import ex5.Checks.VariableCheck;
import ex5.Parameters.Method;
import ex5.Parameters.Variable;

import java.io.*;
import java.util.*;

/**
 * The Global class handles the first pass of the program. It processes the global scope,
 * including variable declarations and method definitions, ensuring syntax and semantic correctness.
 */
public class Global {

    private static final String COMMENT_OR_BLANK_LINE_REGEX = "^//.*|^\\s*";
    private static final String VARIABLE_DECLARATION_REGEX = "^\\s*(int|String|double|char|boolean|final)" +
            "\\b\\s+.*;\\s*";
    private static final String VARIABLE_ASSIGNMENT_REGEX = "^(?!\\d)(?!_$)\\w+\\s*=\\s*.*;\\s*";
    private static final String METHOD_DEFINITION_REGEX = "^\\s*void\\b\\s+\\w+\\s*\\(.*\\)\\s*\\{?\\s*";
    private static final String METHOD_CALL_REGEX = "^\\s*[a-zA-Z]\\w*\\s*\\(.*\\)\\s*;\\s*";
    private static final String RETURN_STATEMENT_REGEX = "^\\s*return\\b\\s*;\\s*";
    private static final String CLOSING_BRACE_REGEX = "^\\s*}\\s*";
    private static final String CONDITIONAL_BLOCK_REGEX = "^\\s*(if|while)\\b\\s*\\(.*\\)\\s*\\{\\s*";
    private static final String ERROR_METHOD_IN_METHOD = "Definition of method inside another method is " +
            "not allowed.";
    private static final String ERROR_RETURN_OUTSIDE_SCOPE = "Return statement not allowed outside of a" +
            " scope.";
    private static final String ERROR_SCOPE_CLOSE_WITHOUT_RETURN = "Cannot close scope without a return" +
            " statement.";
    private static final String ERROR_SCOPE_CLOSE_AFTER_RETURN = "} must be one line after return.";
    private static final String ERROR_UNRECOGNIZED_LINE = "Unrecognized line.";
    private static final String ERROR_UNMATCHED_BRACES = "Every '{' must have a matching closing '}'.";


    private static final int GLOBAL_SCOPE_LEVEL = 0;
    private static final int ONE = 1;
    private static final int ZERO = 0;

    private HashMap<String, Variable> variables;
    private static HashMap<String, Method> methods;
    private VariableCheck variableCheck;
    private MethodsCheck methodCheck;
    private int inScopeDeep;
    private boolean canClose;
    private boolean lastReturn;
    private int scope;

    /**
     * Constructs a Global object and initializes its utilities and data structures.
     *
     * @param filename The file name to process during the global pass.
     * @throws Exception If a validation or syntax error occurs.
     */
    public Global(String filename) throws Exception {
        variables = new HashMap<>();
        methods = new HashMap<>();
        List<HashMap<String, Variable>> list = new ArrayList<>();
        list.add(variables);
        variableCheck = new VariableCheck(list);
        methodCheck = new MethodsCheck(list);
        inScopeDeep = GLOBAL_SCOPE_LEVEL;
        runGlobal(filename);
        canClose = false;
        lastReturn = false;

    }

    /**
     * Retrieves the global variables.
     *
     * @return A HashMap containing the global variables.
     */
    public HashMap<String, Variable> getVariables() {
        return variables;
    }

    /**
     * Retrieves the defined methods.
     *
     * @return A HashMap containing the methods.
     */
    public HashMap<String, Method> getMethods() {
        return methods;
    }

    /**
     * Executes the first pass of the program to process the global scope.
     * This includes parsing variable declarations, method definitions, and managing scope depth.
     *
     * @param fileName The file name to process.
     * @throws Exception If a syntax or semantic error occurs.
     */
    public void runGlobal(String fileName) throws Exception {
        String line;
        try (FileReader fileReader = new FileReader(fileName);
             BufferedReader bufferedReader = new BufferedReader(fileReader)) {
            while ((line = bufferedReader.readLine()) != null) {
                if (line.matches(COMMENT_OR_BLANK_LINE_REGEX)) {}
                else if (line.matches(VARIABLE_DECLARATION_REGEX)) {
                    varibbleFeclrationHandel(line);
                } else if (line.matches(VARIABLE_ASSIGNMENT_REGEX)) {
                    assigmentHandle(line);
                } else if (line.matches(METHOD_DEFINITION_REGEX)) {
                    methodDefenitionHandle(line);
                } else if (line.matches(METHOD_CALL_REGEX)) {
                    lastReturn = false;
                } else if (line.matches(RETURN_STATEMENT_REGEX)) {
                    returnUpdate();
                } else if (line.matches(CLOSING_BRACE_REGEX)) {
                    chekIfCanClose();
                } else if (line.matches(CONDITIONAL_BLOCK_REGEX)) {
                    inScopeDeep++;
                } else {
                    throw new SyntaxErrorException(ERROR_UNRECOGNIZED_LINE);
                }
            }
            if(inScopeDeep!=ZERO){
                throw new SyntaxErrorException(ERROR_UNMATCHED_BRACES);
            }
        } catch (IOException e) {
            e.printStackTrace();
            throw e;
        } catch (SyntaxErrorException | InishlizeVariblleException | InishlizeMethhodException e) {
            throw e;
        }
    }

    private void varibbleFeclrationHandel(String line) throws Exception {
        lastReturn = false;
        if (inScopeDeep == GLOBAL_SCOPE_LEVEL) {
            variables = variableCheck.checkVarible(line);
        }
    }

    private void assigmentHandle(String line) throws Exception {
        lastReturn = false;
        if (inScopeDeep == GLOBAL_SCOPE_LEVEL) {
            variables = variableCheck.getAssigment(line);
        }
    }

    private void methodDefenitionHandle(String line) throws Exception {
        lastReturn = false;
        if (inScopeDeep != GLOBAL_SCOPE_LEVEL) {
            throw new SyntaxErrorException(ERROR_METHOD_IN_METHOD);
        }
        Method tempMethod = methodCheck.checkMethod(line,methods);
        methods.put(tempMethod.getName(), tempMethod);
        inScopeDeep++;
    }

    private void returnUpdate() throws SyntaxErrorException {
        if (inScopeDeep == GLOBAL_SCOPE_LEVEL) {
            throw new SyntaxErrorException(ERROR_RETURN_OUTSIDE_SCOPE);
        }
        canClose = true;
        lastReturn = true;
    }

    private void chekIfCanClose() throws SyntaxErrorException {
        if (inScopeDeep ==  ONE && !canClose) {
            throw new SyntaxErrorException(ERROR_SCOPE_CLOSE_WITHOUT_RETURN);
        }
        if(inScopeDeep == ONE && !lastReturn){
            throw new SyntaxErrorException(ERROR_SCOPE_CLOSE_AFTER_RETURN);
        }
        inScopeDeep--;
        canClose = false;
    }
}
//    private void canBeHere() throws Exception {
//        if (canClose && inScopeDeep==1) {
//            throw new SyntaxErrorException("} need to be one line after return");
//        }
//    }