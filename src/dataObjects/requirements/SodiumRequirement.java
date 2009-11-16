package dataObjects.requirements;

import constraints.MeasurementConstraint;
import constraints.SodiumConstraint;
import dataObjects.units.Measurement;


public abstract class SodiumRequirement extends NutritionRequirement {

   public SodiumRequirement(Measurement minValue, Measurement maxValue) {
      super(minValue, maxValue);
   }

   @Override
   public MeasurementConstraint getConstraint(int recipeNutritionWeightFactor) {
      return new SodiumConstraint(getMinValue(), getMaxValue(),
            recipeNutritionWeightFactor);
   }

}
