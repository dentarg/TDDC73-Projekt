/*
 * Nutrition.java
 */

package dataObjects;

import dataObjects.units.GramMeasurement;
import dataObjects.units.Measurement;

/**
 * A nutritional value
 */
public class Nutrition {

   /** Has this been checked */
   private boolean     checked;

   /** The maximum value. */
   private Measurement maximum;

   /** The minimum value */
   private Measurement minimum;

   /** This nutritional value's unique name. */
   private String      name;

   /** This nutritional value's unique name. */
   private Integer     nr;

   /**
    * Creates an empty nutritional value.
    */
   public Nutrition() {
      name = new String();
      maximum = new GramMeasurement(0f);
      minimum = new GramMeasurement(0f);
      checked = false;
   }

   /**
    * Creates a new nutritional value.
    * 
    * @param name
    *           The name of the value.
    * @param unit
    *           The unit of the value.
    * @param maximum
    *           The maximum value.
    * @param minimum
    *           The minimum value.
    */
   public Nutrition(String name, Measurement maximum, Measurement minimum) {
      this.name = name;
      this.maximum = maximum;
      this.minimum = minimum;
   }

   /**
    * Returns the maximum value for this mutritional value
    * 
    * @return The maximum value.
    */
   public Measurement getMaximum() {
      return maximum;
   }

   /**
    * Returns the minimum value for this mutritional value
    * 
    * @return The minimum value.
    */
   public Measurement getMinimum() {
      return minimum;
   }

   /**
    * Returns the name for this mutritional value
    * 
    * @return The name.
    */
   public String getName() {
      return name;
   }

   /**
    * Returns the number for this mutritional value
    * 
    * @return The number.
    */
   public Integer getNr() {
      return nr;
   }

   /**
    * Determines if this mutritional values min and max values are invalid.
    * 
    * @return True if the min and max values are not valid.
    */
   public boolean hasInvalidValues() {
      if (maximum.compareTo(minimum) < 0)
         return true;
      else
         return false;
   }

   /**
    * Has this instance been checked for invalid values.
    * 
    * @return True if checked
    */
   public boolean isChecked() {
      return checked;
   }

   /**
    * Sets the maximum value for this nutritional value.
    * 
    * @param max
    *           A maximum value.
    */
   public void setMaximum(Measurement max) {
      maximum = max;
   }

   /**
    * Sets the minimum value for this nutritional value.
    * 
    * @param max
    *           A minimum value.
    */
   public void setMinimum(Measurement min) {
      minimum = min;
   }

   /**
    * Sets the name for this nutritional value.
    * 
    * @param max
    *           A name.
    */
   public void setName(String n) {
      name = n;
   }

   /**
    * Sets the number for this nutritional value.
    * 
    * @param max
    *           A number.
    */
   public void setNr(Integer nr) {
      this.nr = nr;
   }

   /**
    * Returns this nutritional value in string format.
    * 
    * @return The string representation of this nutritional value.
    */
   @Override
   public String toString() {
      return new String(getName() + ": " + getMinimum() + "-" + getMaximum());
   }
}
