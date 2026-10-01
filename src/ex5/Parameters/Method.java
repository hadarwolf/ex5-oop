package ex5.Parameters;

import java.util.ArrayList;

/**
 * The Method class represents a method, including its name and
 * a list of variables associated with it.
 */
public class Method {
    private ArrayList<Variable> varList; // A list of variables associated with the method.
    private String name;                 // The name of the method.

    /**
     * Constructor to initialize a Method object.
     *
     * @param name The name of the method.
     * @param varList An ArrayList of Variable objects associated with the method.
     */
    public Method(String name, ArrayList<Variable> varList) {
        this.name = name;
        this.varList = varList;
    }

    /**
     * Gets the list of variables associated with the method.
     *
     * @return An ArrayList of Variable objects.
     */
    public ArrayList<Variable> getVarList() {
        return varList;
    }



    /**
     * Gets the name of the method.
     *
     * @return The name of the method as a String.
     */
    public String getName() {
        return name;
    }
}
