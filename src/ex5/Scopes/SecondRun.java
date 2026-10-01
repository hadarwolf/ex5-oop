package ex5.Scopes;


import ex5.Parameters.Method;
import ex5.Parameters.Variable;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.regex.Matcher;
import java.util.regex.Pattern;


/**
 * The SecondRun class handles the second pass of validation, including scope and method checks.
 */
public class SecondRun {
    private static final String METHOD_DEFINITION_REGEX = "^\\s*void\\b\\s*.*";
    private static final String METHOD_NAME_REGEX = "^\\s*void\\s+(\\w+)\\s*\\(";
    private static final int ONE = 1;
    private HashMap<String , Variable> variables;
    private static  HashMap<String , Method> methods;
    private boolean inscope;
    private Scope scope;




//    public SecondRun
//    (HashMap<String , Variable> variables,HashMap<String , Method> methods,String fileName)
//    throws Exception {
//        this.variables = new ArrayList<HashMap<String , Variable>>();
//
//        this.variables.add(variables);
//        this.methods = methods;
//        run(fileName);
//    }

    /**
     * Constructor for SecondRun.
     *
     * @param variables Global variables from the first run.
     * @param methods Defined methods from the first run.
     * @param fileName The name of the file to process in the second run.
     * @throws Exception If any error occurs during processing.
     */
    public SecondRun
    (HashMap<String , Variable> variables,HashMap<String , Method> methods,String fileName)
            throws Exception {
        this.variables=variables;
        this.methods = methods;
        run(fileName);
    }


    /*
     * Executes the second pass of the program by reading and processing each line of the given file.
     * It handles method definitions and scopes dynamically as it encounters them.
     *
     * @param fileName The name of the file to be processed during the second run.
     * @throws Exception If any validation error or runtime issue occurs during file processing.
     */
    private void run(String fileName) throws Exception {
        String line;
        try(FileReader fileReader = new FileReader(fileName);
            BufferedReader bufferedReader = new BufferedReader(fileReader)){
            while ((line = bufferedReader.readLine()) != null) {
                 if(line.matches(METHOD_DEFINITION_REGEX)){
                     ArrayList<HashMap<String , Variable>> vribblrList =
                             new ArrayList<HashMap<String , Variable>>();
                     vribblrList.add(variables);
                     Pattern pattern = Pattern.compile(METHOD_NAME_REGEX);
                     Matcher matcher = pattern.matcher(line);
                     if (matcher.find()) {
                         // Extract the name from group 1
                         String methodName = matcher.group(ONE);
                         HashMap<String, Variable> newVariables = new HashMap<>();
                         for (Variable v : methods.get(methodName).getVarList()) {
                             newVariables.put(v.getVariableName(), v);
                         }
                         vribblrList.add(newVariables);
                         scope = new Scope(vribblrList,methods);
                         inscope = true;
                     }
                }
                 else {
                     if(inscope){
                         inscope = scope.run(line);//what will hapend when getiing if or while
                     }
                 }
            }
        }
        catch(IOException e){
            e.printStackTrace();
            throw e;
        }
        catch (Exception e) {
            throw e;
        }
    }


}
