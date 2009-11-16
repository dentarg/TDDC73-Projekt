package model.sorters;

import java.util.Comparator;

import constraints.SingleRecipeConstraint;
import dataObjects.Recipe;
import dataObjects.requirements.RequirementManager;

/**
 * Sorts the recipes in order of how well they conform to constraints.
 * @author jernlas
 *
 */
public class ConstraintSorter implements RecipeSorter {

   /**
    * Comparator that compares "reverse penalties" for constraint violations.
    * @author jernlas
    *
    */
   private class ValuationComparator implements Comparator<Recipe> {

      /*
       * (non-Javadoc)
       * @see java.util.Comparator#compare(java.lang.Object, java.lang.Object)
       */
      @Override
      public int compare(Recipe r1, Recipe r2) {
         float val1 = 0;
         float val2 = 0;

         for (SingleRecipeConstraint c : RequirementManager.getAllConstraints()) {
            if (c != null) {
               val1 += c.valuation(r1);
               val2 += c.valuation(r2);
            }
         }

         return Math.round((val2 - val1) * -10);
      }

   }

   /*
    * (non-Javadoc)
    * @see model.sorters.RecipeSorter#getComparator()
    */
   @Override
   public Comparator<Recipe> getComparator() {
      return new ValuationComparator();
   }

   /*
    * (non-Javadoc)
    * @see java.lang.Object#toString()
    */
   @Override
   public String toString() {
      return "Bästa match först";
   }

}
