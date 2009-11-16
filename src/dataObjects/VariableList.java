package dataObjects;

import java.util.ArrayList;

/**
 * Class representing a list of variables. Wrappers like these are not necessary
 * in Java 1.5.0 which is why only variables have one.
 */
public class VariableList {
   private ArrayList<Variable> variables;

   /**
    * Creates a new variable list.
    */
   public VariableList() {
      variables = new ArrayList<Variable>();
   }

   /**
    * Creates a new variable list from an ArrayList.
    * 
    * @param vars
    *           ArrayList of Variable
    */
   @SuppressWarnings("unchecked")
   public VariableList(ArrayList<Variable> vars) {
      variables = (ArrayList<Variable>) vars.clone();
   }

   /**
    * Adds a variable to the list.
    * 
    * @param var
    *           Variable to add
    */
   public void addVariable(Variable var) {
      variables.add(var);
   }

   /**
    * Gets a variable from the list.
    * 
    * @param index
    *           Index of variable, int.
    * @return Variable
    */
   public Variable getVariable(int index) {
      return variables.get(index);
   }

   public boolean isEmpty() {
      return variables.isEmpty();
   }

   public Variable removeVariable(int index) {
      return variables.remove(index);
   }

   /**
    * Size of the list.
    * 
    * @return Size, int.
    */
   public int size() {
      return variables.size();
   }
}
