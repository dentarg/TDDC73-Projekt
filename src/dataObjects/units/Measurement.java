package dataObjects.units;

import java.util.Scanner;

/**
 * Represents one measurement, ie a quantity and a unit. This class defines
 * handling for the quantity, but it is up to every subclass to handle the
 * units.
 * 
 * @author jernlas
 */
public abstract class Measurement implements Comparable<Measurement> {

   /**
    * Crates a Measurement from a string on the form "quantity unit"
    * 
    * @param str
    *           The string to parse
    * @return A Measurement
    */
   public static Measurement createFromString(String str) {
      Scanner s = new Scanner(str);
      final Float quantity = s.nextFloat();
      String unit;
      if (s.hasNext()) {
         unit = s.next();
      } else {
         throw new IllegalArgumentException("No unit found");
      }
      final Measurement ret = createMeasurement(unit, quantity);
      return ret;
   }

   /**
    * Creates a Measurement from a unit and a quantity. The actual class
    * returned is a subclass of this one.
    * 
    * @param unit
    *           The unit of the measurement.
    * @param quantity
    *           The quantity of the measurement.
    * @return The created unit.
    */
   public static Measurement createMeasurement(String unit, Float quantity) {
      Measurement retVal;
      if (unit == null) {
         throw new NullPointerException("Unit can not be null");
         // retVal = new MiscMeasurement("");
      } else if (unit.equals("")) {
         throw new IllegalArgumentException("Unit can not be empty string");
      } else if (unit.equals("g")) {
         retVal = new GramMeasurement();
      } else if (unit.equals("kg")) {
         retVal = new KiloGramMeasurement();
      } else {
         retVal = new MiscMeasurement(unit);
      }
      retVal.setQuantity(quantity);
      return retVal;
   }

   /**
    * The quantity of this measurement.
    */
   private Float quantity = 0f;

   /**
    * Adds another measurement to this one. Not all measurements are compatible.
    * 
    * @param m
    *           The measurement to add.
    * @return The sum of the measurements. Possibly represented by a different
    *         class from both this and m.
    */
   public Measurement add(final Measurement m) {
      if (this.isWeightUnit() && m.isWeightUnit()) {
         return Measurement.createMeasurement("g", getGrams() + m.getGrams());
      } else if (this.getUnit().equals(m.getUnit())) {
         return Measurement.createMeasurement(getUnit(), getQuantity()
               + m.getQuantity());
      } else {
         throw new ClassCastException(
               "Can not cast non weight based measurement to weight based");
      }
   }

   /*
    * (non-Javadoc)
    * 
    * @see java.lang.Comparable#compareTo(java.lang.Object)
    */
   public int compareTo(Measurement m) {
      if (m == null) {
         throw new NullPointerException();
      }
      if (m.getUnit().equals(getUnit())) {
         return getQuantity().compareTo(m.getQuantity());
      } else if (!m.isWeightUnit()) {
         throw new ClassCastException("Can not compare non-weight units");
      } else {
         return getGrams().compareTo(m.getGrams());
      }
   }

   /**
    * Gets a copy of the measurement.
    * 
    * @return The copy.
    */
   public abstract Measurement Copy();

   /**
    * Divide two measurements, only for weights at this time.
    * 
    * @param m
    *           The Measurement to divide by
    * @return The result.
    */
   public float divide(final Measurement m) {
      if (this.isWeightUnit() && m.isWeightUnit()) {
         return getGrams() / m.getGrams();
      } else {
         throw new ClassCastException(
               "Can not cast non weiight based measurement to weight based");
      }
   }

   /*
    * (non-Javadoc)
    * 
    * @see java.lang.Object#equals(java.lang.Object)
    */
   @Override
   public boolean equals(Object o) {
      if (o == null) {
         return false;
      } else if (this == o) {
         return true;
      } else if (o instanceof Measurement) {
         Measurement m = (Measurement) o;
         if (isWeightUnit() && m.isWeightUnit()) {
            return getGrams().equals(m.getGrams());
         } else {
            return (getUnit().equals(m.getUnit()) && getQuantity().equals(
                  m.getQuantity()));
         }
      } else {
         return false;
      }
   }

   /**
    * Gets the cost of this quantity given the cost per unit.
    * 
    * @param costPerUnit
    *           The cost per unit.
    * @return The total cost.
    */
   public float getCost(final float costPerUnit) {
      return getQuantity() * costPerUnit;
   }

   /**
    * Get the measurement converted to grams, or null if not a weight
    * measurement.
    * 
    * @return The measurement in grams.
    */
   public abstract Float getGrams();

   
   /**
    * Get the number of units in this measurement.
    * @return The quantity.
    */
   public Float getQuantity() {
      return quantity;
   }

   /**
    * Gets the name of the unit of this measurement
    * @return The unit as a string.
    */
   public abstract String getUnit();

   /**
    * Is this a weight unit.
    * @return True if this unit represents weight.
    */
   public boolean isWeightUnit() {
      return true;
   }

   /**
    * Multiplies this measurement with another quantity, same unit is assumed.
    * @param m The quantity to multiply by.
    * @return The product.
    */
   public Measurement multiply(final float m) {
      return Measurement.createMeasurement(getUnit(), getQuantity() * m);
   }

   
   /**
    * Multiplies this measurement with another Measurement.
    * @param m The measurement to multiply by.
    * @return The product.
    */
   public Measurement multiply(final Measurement m) {
      if (this.isWeightUnit() && m.isWeightUnit()) {
         return Measurement.createMeasurement("g", getGrams() * m.getGrams());
      } else if (this.getUnit().equals(m.getUnit())) {
         return Measurement.createMeasurement(getUnit(), getQuantity()
               * m.getQuantity());
      } else {
         throw new ClassCastException(
               "Can not cast non weiight based measurement to weight based");
      }
   }

   /**
    * Sets the quantity of this measurement.
    * @param quantity The quantity to set.
    */
   public void setQuantity(float quantity) {
      this.quantity = quantity;
   }

   
   /**
    * Subtracts another measurement from this one.
    * @param m The measurement to subtract.
    * @return The difference.
    */
   public Measurement subtract(final Measurement m) {
      if (this.isWeightUnit() && m.isWeightUnit()) {
         return Measurement.createMeasurement("g", getGrams() - m.getGrams());
      } else if (this.getUnit().equals(m.getUnit())) {
         return Measurement.createMeasurement(getUnit(), getQuantity()
               - m.getQuantity());
      } else {
         throw new ClassCastException(
               "Can not cast non weiight based measurement to weight based");
      }
   }

   /**
    * Gets a string on the form "quantity unit"
    */
   @Override
   public String toString() {
      return String.format("%.2f %s", getQuantity(), getUnit());
   }

}
