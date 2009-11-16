package dataObjects;

/**
 * Class representing a single assignment. Variable and value.
 */
public class SingleAssignment {
   private String   value;
   private Variable variable;

   /**
    * Create a new single assignment.
    * 
    * @param var
    *           Variable to set.
    * @param val
    *           Value of Variable, String,
    */
   public SingleAssignment(Variable var, String val) {
      variable = var;
      value = val;
   }

   /**
    * Gets the value.
    * 
    * @return Value of this assignment, String.
    */
   public String getValue() {
      return value;
   }

   /**
    * Gets the Variable.
    * 
    * @return Variable.
    */
   public Variable getVariable() {
      return variable;
   }

   /**
    * String representation of a single assignment.
    * 
    * @return String.
    */
   @Override
   public String toString() {
      return variable.getName() + ": " + value;
   }
}
