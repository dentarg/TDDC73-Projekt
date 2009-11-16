package model.sorters;

import java.util.Comparator;

import dataObjects.Recipe;

/**
 * Interface for recipe sorters.
 * @author jernlas
 *
 */
public interface RecipeSorter {
   
   /**
    * Gets a comparator for sorting recipes.
    * @return The comparator to use.
    */
   public Comparator<Recipe> getComparator();

   
   /**
    * Gets the swedish name of the sorter.
    * @return The name.
    */
   public String toString();
}
