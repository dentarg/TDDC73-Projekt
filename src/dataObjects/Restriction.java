package dataObjects;

import java.util.ArrayList;

/**
 * A restriction, contains information about possible values and more.
 */
public class Restriction {

   /** Initial value for this restriction eg 0 */
   private int                initValue;

   /** The name of this restriction eg cost */
   private String             name;

   /** The number of this restriction, eg RESTR_TIME. */
   private Integer            nr;

   /** The number of possible values for this restriction, eg 3. */
   private int                nrOfValues;

   /**
    * Names for the different values. Eg ["billigt", "medeldyrt", "dyrt"].
    */
   private ArrayList<String>  valueNames;

   /** Possible values, eg [1,2,3] */
   private ArrayList<Integer> values;

   /** Creates a new Restriction-object. */
   public Restriction() {
      name = new String();
      valueNames = new ArrayList<String>();
      values = new ArrayList<Integer>();
   }

   /**
    * Gets the initial value.
    * 
    * @return the initial value
    */
   public int getInitValue() {
      return initValue;
   }

   /**
    * Gets the name for this restriction
    * 
    * @return the name
    */
   public String getName() {
      return name;
   }

   /**
    * Gets the name for one of the values . Eg: 0 -> "billigt"
    * 
    * @param value
    *           The value
    * @return The name.
    */
   public String getNameFromValue(int value) {
      int index = values.indexOf(value);
      String name = valueNames.get(index);
      return name;
   }

   /**
    * Gets the identifying number of this constraint
    * 
    * @return the identifying number
    */
   public Integer getNr() {
      return nr;
   }

   /**
    * Gets the number of possible values for this constraint.
    * 
    * @return the number of values
    */
   public int getnrOfValues() {
      return nrOfValues;
   }

   /**
    * Gets the names of the value names for this constraint
    * 
    * @return The list of values names.
    */
   public ArrayList<String> getValueNames() {
      return valueNames;
   }

   /**
    * Gets the names of the values for this constraint
    * 
    * @return The list of values.
    */
   public ArrayList<Integer> getValues() {
      return values;
   }

   /**
    * Sets the starting value for this constraint.
    * 
    * @param initValue
    *           The stating value.
    */
   public void setInitValue(int initValue) {
      this.initValue = initValue;
   }

   /**
    * Sets the name for this constraint.
    * 
    * @param name
    *           The name
    */
   public void setName(String name) {
      this.name = name;
   }

   /**
    * Sets the number for this constraint.
    * 
    * @param nr
    *           the identifying number.
    */
   public void setNr(Integer nr) {
      this.nr = nr;
   }

   /**
    * Sets the number of possible values for this constraint.
    * 
    * @param nrOfValues
    *           The number of values
    */
   public void setnrOfValues(int nrOfValues) {
      this.nrOfValues = nrOfValues;
   }

   /**
    * Sets the names of the value names for this constraint.
    * 
    * @param valueNames
    *           The names of the value names.
    */
   public void setValueNames(ArrayList<String> valueNames) {
      this.valueNames = valueNames;
   }

   /**
    * Sets the names of the values for this constraint.
    * 
    * @param valueNames
    *           The names of the values.
    */
   public void setValues(ArrayList<Integer> values) {
      this.values = values;
   }

   /*
    * (non-Javadoc)
    * 
    * @see java.lang.Object#toString()
    */
   @Override
   public String toString() {
      // InitMappings initMappings = new InitMappings();
      // String initValueName =
      // initMappings.getRestrictionEngName(getInitValue(), getNr());
      return new String(getName() + ": " + getInitValue());
   }
}
