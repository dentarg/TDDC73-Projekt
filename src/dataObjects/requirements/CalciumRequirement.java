package dataObjects.requirements;

import constraints.CalciumConstraint;
import constraints.MeasurementConstraint;
import dataObjects.units.Measurement;

public abstract class CalciumRequirement extends NutritionRequirement {

   public CalciumRequirement(Measurement minValue, Measurement maxValue) {
      super(minValue, maxValue);
   }

   @Override
   public MeasurementConstraint getConstraint(int recipeNutritionWeightFactor) {
      return new CalciumConstraint(getMinValue(), getMaxValue(),
            recipeNutritionWeightFactor);
   }

}
