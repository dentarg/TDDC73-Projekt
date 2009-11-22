package model.sorters;

import java.util.Comparator;

import dataObjects.Recipe;

/**
 * Does no sorting at all.
 * @author jernlas
 *
 */
public class IgnoreSorter implements RecipeSorter {

   public Comparator<Recipe> getComparator() {
      return null;
   }

   @Override
   public String toString() {
      return "Sortera inte";
   }

}
