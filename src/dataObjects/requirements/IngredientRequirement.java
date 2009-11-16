package dataObjects.requirements;

import dataObjects.units.Measurement;

/**
 * Class representing a required ingredient. 
 */
public class IngredientRequirement {
   private Measurement amount;
   private String      name;

   /**
    * Create a new required ingredient with amount.
    * 
    * @param name
    *           Name of ingredient
    * @param amount
    *           Amount of ingredient
    * @param unit
    *           Unit of amount
    */
   public IngredientRequirement(String name, float amount, String unit) {
      this.name = name;
      this.amount = Measurement.createMeasurement(unit, amount);
   }

   /**
    * Amount required.
    * 
    * @return Amount, float.
    */
   public Measurement getAmount() {
      return amount;
   }

   /**
    * Name of ingredient.
    * 
    * @return Name, String.
    */
   public String getName() {
      return name;
   }

   /**
    * String representation of an ingredient requirement.
    * 
    * @return String
    */
   @Override
   public String toString() {
      return name + " - " + amount.getQuantity() + " " + amount.getUnit();
   }
}
