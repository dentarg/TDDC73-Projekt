package dataObjects;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Class representing the meal plan for a day.
 *
 */
public class DayPlan {

   volatile private Map<String, Recipe> plannedMeals = new HashMap<String, Recipe>();
   private List<String>                 allowed      = new ArrayList<String>();

   public DayPlan(List<String> meals) {
      allowed.addAll(meals);
   }

   public Recipe getMeal(String name) {
      return plannedMeals.get(name);
   }

   public void setMeal(String name, Recipe recipe) {
      if (allowed.contains(name)) {
         plannedMeals.put(name, recipe);
      } else {
         throw new IllegalArgumentException(name);
      }
   }

   @Override
   public String toString() {
      return plannedMeals.toString();
   }

}
