package constraints;

import dataObjects.Recipe;

/**
 * Interface for single recipe constraint classes. All constraints must have a
 * valuation function and a weight.
 */
public interface SingleRecipeConstraint {

   /**
    * Gets the weight factor of the constraint. Should be 1 for hard
    * constraints, 1+ for soft.
    * 
    * @return Weight factor of the constraint, given as int.
    */
   int getWeight();

   /**
    * Determines the degree of constraint violation.
    * 
    * @param recipe
    *           Assignment to be evaluated
    * @return Degree of violation, should range from 0 to 1
    */
   public float valuation(Recipe recipe);
}
