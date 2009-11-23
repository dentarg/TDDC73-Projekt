package model.sorters;

import java.util.Comparator;

import dataObjects.Recipe;

/**
 * Sorts the recipes in alphabetical order.
 * @author jernlas
 *
 */
public class AlphabeticSorter implements RecipeSorter {

   private class AlphabeticComparator implements Comparator<Recipe> {
      private final Comparator<String> c = String.CASE_INSENSITIVE_ORDER;

      /*
       * (non-Javadoc)
       * @see java.util.Comparator#compare(java.lang.Object, java.lang.Object)
       */
      public int compare(Recipe r1, Recipe r2) {
         return c.compare(r1.getName(), r2.getName());
      }

   }

   /*
    * (non-Javadoc)
    * @see model.sorters.RecipeSorter#getComparator()
    */
   public Comparator<Recipe> getComparator() {
      return new AlphabeticComparator();
   }

   /*
    * (non-Javadoc)
    * @see java.lang.Object#toString()
    */
   @Override
   public String toString() {
      return "Alfabetisk ordning";
   }

}
