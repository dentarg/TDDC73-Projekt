package constraints;

import dataObjects.Recipe;
import dataObjects.units.Measurement;
import exceptions.RecipeNotFoundException;

/**
 * Constraint for Protein content. Most work is done in base class.
 * 
 * @author jernlas
 */
public class ProteinConstraint extends MeasurementConstraint {

   public ProteinConstraint(Measurement minValue, Measurement maxValue) {
      super(minValue, maxValue);
   }

   public ProteinConstraint(Measurement minValue, Measurement maxValue,
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
      return sa.getProtein();
   }

}
