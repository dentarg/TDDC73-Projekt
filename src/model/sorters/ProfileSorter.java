package model.sorters;

import java.util.Comparator;

import dataObjects.Recipe;

/**
 * Does no sorting at all.
 * @author jernlas
 *
 */
public class ProfileSorter implements RecipeSorter {

   public Comparator<Recipe> getComparator() {
      return null;
   }

   @Override
   public String toString() {
      return "Sortera efter min profil";
   }

}
