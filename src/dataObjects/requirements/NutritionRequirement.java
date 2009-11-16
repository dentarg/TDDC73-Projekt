package dataObjects.requirements;

import constraints.MeasurementConstraint;
import dataObjects.units.Measurement;

/**
 * A class representing a nutrition requirement.
 */
public abstract class NutritionRequirement {
   private Measurement maxValue;
   private Measurement minValue;

   public NutritionRequirement(final Measurement minValue,
         final Measurement maxValue) {
      this.minValue = minValue;
      this.maxValue = maxValue;
   }

   public abstract MeasurementConstraint getConstraint(
         int recipeNutritionWeightFactor);

   public Measurement getMaxValue() {
      return maxValue;
   }

   public Measurement getMinValue() {
      return minValue;
   }

}
