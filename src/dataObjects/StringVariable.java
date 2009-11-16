package dataObjects;

import java.util.ArrayList;

/**
 * Class representing a variable to be set by CS algorithms.
 */
public class StringVariable extends Variable {
   private ArrayList<String> domain;

   /**
    * Creates a new variable.
    * 
    * @param name
    *           Name of the variable.
    */
   public StringVariable(String name) {
      super(name);
      domain = new ArrayList<String>();
   }

   /**
    * Creates a new variable with domain.
    * 
    * @param name
    *           Name of the variable.
    * @param domain
    *           ArrayList of String.
    */
   @SuppressWarnings("unchecked")
   public StringVariable(String name, ArrayList<String> domain) {
      super(name);
      this.domain = (ArrayList<String>) domain.clone();
   }

   @Override
   public void addToDomain(String s) {
      domain.add(s);

   }

   @Override
   public String getDomainElementAtIndex(int i) {
      return domain.get(i);
   }

   @Override
   public int getDomainSize() {
      return domain.size();
   }

   @Override
   public void removeDomainElementAtIndex(int i) {
      domain.remove(i);
   }

}
