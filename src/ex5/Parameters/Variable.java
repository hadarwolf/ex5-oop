package ex5.Parameters; // Package declaration for organizing the class in the 'ex5' package.

/**
 * The Variable class represents a variable with properties such as
 * its name, type, value, and whether it is marked as final.
 */
public class Variable {
    private boolean isThisVaribleFinal; // Indicates whether the variable is final.
    private String variableType;       // The type of the variable (e.g., int, String, etc.).
    private String variableValue;      // The value assigned to the variable.
    private String variableName;       // The name of the variable.

    /**
     * Constructor to initialize a Variable object.
     *
     * @param variableName The name of the variable.
     * @param variableType The type of the variable (e.g., int, String, etc.).
     * @param variableValue The value assigned to the variable.
     * @param isThisVaribleFinal A boolean indicating whether the variable is final.
     */
    public Variable(String variableName, String variableType, String variableValue, boolean
            isThisVaribleFinal) {
        this.variableName = variableName;
        this.variableType = variableType;
        this.variableValue = variableValue;
        this.isThisVaribleFinal = isThisVaribleFinal;
    }

    /**
     * Gets the name of the variable.
     *
     * @return The variable's name.
     */
    public String getVariableName() {
        return variableName;
    }

    /**
     * Sets the name of the variable.
     *
     * @param variableName The name to set for the variable.
     */
    public void setVariableName(String variableName) {
        this.variableName = variableName;
    }

    /**
     * Gets the value of the variable.
     *
     * @return The value of the variable.
     */
    public String getVariableValue() {
        return variableValue;
    }

    /**
     * Sets the value of the variable.
     *
     * @param variableValue The value to set for the variable.
     */
    public void setVariableValue(String variableValue) {
        this.variableValue = variableValue;
    }

    /**
     * Gets the type of the variable.
     *
     * @return The variable's type (e.g., int, String, etc.).
     */
    public String getVariableType() {
        return variableType;
    }

    /**
     * Sets the type of the variable.
     *
     * @param variableType The type to set for the variable.
     */
    public void setVariableType(String variableType) {
        this.variableType = variableType;
    }

    /**
     * Checks if the variable is final.
     *
     * @return true if the variable is final, false otherwise.
     */
    public boolean isThisVaribleFinal() {
        return isThisVaribleFinal;
    }

    /**
     * Sets whether the variable is final.
     *
     * @param thisVaribleFinal A boolean indicating whether the variable is final.
     */
    public void setThisVaribleFinal(boolean thisVaribleFinal) {
        isThisVaribleFinal = thisVaribleFinal;
    }
}
