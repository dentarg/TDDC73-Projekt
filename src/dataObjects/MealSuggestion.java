package dataObjects;

import java.util.ArrayList;
import java.util.List;

/**
 * Class representing a single meal with recipe suggestions.
 */
public class MealSuggestion {
   private MealDate     date;
   private List<String> recipes;

   /**
    * Create a new meal.
    * 
    * @param date
    *           Date of the meal
    * @param recipeList
    *           List of names of recipes for the meal
    */
   public MealSuggestion(MealDate date, List<String> recipeList) {
      this.date = date;
      this.recipes = recipeList;
   }

   public MealSuggestion(MealDate date, String recipe) {
      this.date = date;
      recipes = new ArrayList<String>();
      recipes.add(recipe);
   }

   public void addRecipe(String recipeName) {
      if (!recipes.contains(recipeName)) {
         recipes.add(recipeName);
      }
   }

   /**
    * Date of the meal
    * 
    * @return MealDate
    */
   public MealDate getDate() {
      return date;
   }

   /**
    * Recipe name.
    * 
    * @return Name, String.
    */
   public String getFirstRecipe() {
      return recipes.get(0);
   }

   /**
    * List of suggested recipe names.
    * 
    * @return List
    */
   public List<String> getRecipes() {
      return recipes;
   }

   /**
    * String representation of a meal.
    * 
    * @return String.
    */
   @Override
   public String toString() {
      String suggestions = "Date: " + date.getDate() + " Meal number: "
            + date.getMealNumber() + "\n";

      suggestions += "Suggestions:";
      for (int index = 0; index < recipes.size(); index++) {
         suggestions += " " + recipes.get(index);
      }
      return suggestions + "\n";
   }
}
