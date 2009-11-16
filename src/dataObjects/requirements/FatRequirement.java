package dataObjects.requirements;

import constraints.FatConstraint;
import constraints.MeasurementConstraint;
import dataObjects.units.Measurement;

public abstract class FatRequirement extends NutritionRequirement {

   public FatRequirement(Measurement minValue, Measurement maxValue) {
      super(minValue, maxValue);
   }

   @Override
   public MeasurementConstraint getConstraint(int recipeNutritionWeightFactor) {
      return new FatConstraint(getMinValue(), getMaxValue(),
            recipeNutritionWeightFactor);
   }

}
