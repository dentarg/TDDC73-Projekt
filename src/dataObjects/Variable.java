package dataObjects;

/**
 * Class representing a variable to be set by CS algorithms.
 */
public abstract class Variable {
   private int    currentValue;
   private String name;

   public Variable(String name) {
      this.name = name;
   }

   public abstract void addToDomain(String s);

   /**
    * Adds a value to the domain.
    * 
    * @param value
    *           Value to add, String.
    */
   public void addValue(String value) {
      addToDomain(value);
   }

   /**
    * Gets the current value oif this variable.
    * 
    * @return Current value, String.
    */
   public String getCurrentValue() {
      return getDomainElementAtIndex(currentValue);
   }

   public abstract String getDomainElementAtIndex(int i);

   public abstract int getDomainSize();

   /**
    * Gets the first value to be tested for this variable.
    * 
    * @return The first value to be tested, String.
    */
   public String getFirstValue() {
      currentValue = 0;
      return getDomainElementAtIndex(currentValue);
   }

   /**
    * Gets name of the variable.
    * 
    * @return String.
    */
   public String getName() {
      return name;
   }

   /**
    * Gets the next value to be tested for this variable.
    * 
    * @return The next value to be tested, String.
    */
   public String getNextValue() {
      return getDomainElementAtIndex(++currentValue);
   }

   public String getValue(int index) {
      return getDomainElementAtIndex(index);
   }

   /**
    * @return true if this is the last value to be tested, false otherwise.
    */
   public boolean lastValue() {
      return getDomainSize() == (currentValue + 1);
   }

   public void makeUnassigned() {
      currentValue = -1;
   }

   public abstract void removeDomainElementAtIndex(int i);

   public void removeValue(int index) {
      removeDomainElementAtIndex(index);
   }

   /**
    * Tells us if this variable hasn't been assigned yet.
    * 
    * @return true if unassigned, false otherwise.
    */
   public boolean unassigned() {
      return currentValue == -1;
   }
}
