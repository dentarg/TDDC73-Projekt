package dataObjects.requirements;

import constraints.EnergyConstraint;
import constraints.MeasurementConstraint;
import dataObjects.units.Measurement;

public abstract class EnergyRequirement extends NutritionRequirement {

   public EnergyRequirement(Measurement minValue, Measurement maxValue) {
      super(minValue, maxValue);
   }

   @Override
   public MeasurementConstraint getConstraint(int recipeNutritionWeightFactor) {
      return new EnergyConstraint(getMinValue(), getMaxValue(),
            recipeNutritionWeightFactor);
   }

}
