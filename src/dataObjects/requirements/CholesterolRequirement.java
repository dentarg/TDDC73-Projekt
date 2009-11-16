package dataObjects.requirements;

import constraints.CholestrolConstraint;
import constraints.MeasurementConstraint;
import dataObjects.units.Measurement;

public abstract class CholesterolRequirement extends NutritionRequirement {

   public CholesterolRequirement(Measurement minValue, Measurement maxValue) {
      super(minValue, maxValue);
   }

   @Override
   public MeasurementConstraint getConstraint(int recipeNutritionWeightFactor) {
      return new CholestrolConstraint(getMinValue(), getMaxValue(),
            recipeNutritionWeightFactor);
   }

}
