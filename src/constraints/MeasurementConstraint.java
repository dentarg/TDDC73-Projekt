package constraints;

import dataObjects.Recipe;
import dataObjects.units.Measurement;
import exceptions.RecipeNotFoundException;

/**
 * Constraint for any measurement content. The value is constrained between a
 * maximum and a mimimum value. This class does most of the work for it's
 * subclasses.
 * 
 * @author jernlas
 */
public abstract class MeasurementConstraint implements SingleRecipeConstraint {

   /**
    * The maximum allowed value before violating the constraint.
    */
   private Measurement maxValue;

   /**
    * The minimum allowed value before violating the constraint.
    */
   private Measurement minValue;

   /**
    * The weight of the constraint. 1 implies hard, 1+ implies soft.
    */
   private int         weight;

   /**
    * Creates a new MeasurementConstraint with weight 1.
    * 
    * @param minValue
    *           The mimimum allowed value.
    * @param maxValue
    *           The maximum allowed value.
    */
   public MeasurementConstraint(final Measurement minValue,
         final Measurement maxValue) {
      this.minValue = minValue;
      this.maxValue = maxValue;
      this.weight = 1;
   }

   /**
    * Creates a new MeasurementConstraint.
    * 
    * @param minValue
    *           The mimimum allowed value.
    * @param maxValue
    *           The maximum allowed value.
    * @param weight
    *           The weight of the constraint. 1 means hard, 1+ means soft.
    */
   public MeasurementConstraint(final Measurement minValue,
         final Measurement maxValue, final int weight) {
      this.minValue = minValue;
      this.maxValue = maxValue;
      this.weight = weight;
   }

   /**
    * Delegates the fetching of the actual value to the subclass.
    * 
    * @param recipe
    *           The recipe to extract the value from.
    * @return The extracted measurement.
    * @throws RecipeNotFoundException
    *            This should not occur anymore. Kept for compatibility.
    */
   public abstract Measurement getCurrentValue(Recipe recipe)
         throws RecipeNotFoundException;

   /*
    * (non-Javadoc)
    * 
    * @see constraints.Constraint#getWeight()
    */
   public int getWeight() {
      return weight;
   }

   /*
    * (non-Javadoc)
    * 
    * @see constraints.Constraint#valuation(dataObjects.Recipe)
    */
   public float valuation(Recipe assign) {
      Measurement currentValue;
      float penalty = 0;
      try {
         currentValue = getCurrentValue(assign);

         if (currentValue.compareTo(minValue) < 0) {
            if (minValue.subtract(currentValue).getQuantity() > 50) {
               penalty += 1;
            } else {
               penalty += minValue.subtract(currentValue).getQuantity() / 50;
            }
         } else if (currentValue.compareTo(maxValue) > 0) {
            if (currentValue.subtract(maxValue).getQuantity() > 50) {
               penalty += 1;
            } else {
               penalty += currentValue.subtract(maxValue).getQuantity() / 50;
            }
         }
      } catch (RecipeNotFoundException e) {
         e.printStackTrace();
      }
      return penalty;
   }
}
