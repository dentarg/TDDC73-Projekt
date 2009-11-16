package constraints;

import dataObjects.Recipe;
import dataObjects.units.Measurement;
import exceptions.RecipeNotFoundException;

/**
 * Constraint for Cholestrol content. Most work is done in base class.
 * 
 * @author jernlas
 */
public class CholestrolConstraint extends MeasurementConstraint {

   public CholestrolConstraint(Measurement minValue, Measurement maxValue) {
      super(minValue, maxValue);
   }

   public CholestrolConstraint(Measurement minValue, Measurement maxValue,
         int weight) {
      super(minValue, maxValue, weight);
   }

   /*
    * (non-Javadoc)
    * 
    * @see constraints.MeasurementConstraint#getCurrentValue(dataObjects.Recipe)
    */
   @Override
   public Measurement getCurrentValue(Recipe sa) throws RecipeNotFoundException {
      return sa.getCholesterol();
   }

}
