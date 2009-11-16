package model;

import java.util.ArrayList;

import dataObjects.Recipe;

/**
 * Interface for listening to filter changes.
 * @author jernlas
 *
 */
public interface MealSuggestionFilterListener {
   public void updateFilteredData(ArrayList<Recipe> list);
}
