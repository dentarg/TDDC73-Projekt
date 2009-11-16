/*
 * Created using IntelliJ IDEA. User: x04nicsu Date: 2005-jan-17 Time: 10:15:51
 */
package dataObjects;

import java.util.ArrayList;
import java.util.List;

/**
 * Class representing a meal plan.
 */
public class MealPlan {

   /**
    * The meals in the plan.
    */
   private List<List<MealSuggestion>> mealList;

   /**
    * The name of the plan
    */
   private String                     name;

   /**
    * For several alternative plans, this number indicates the next plan to
    * return.
    */
   private int                        nextSuggestionNumber;

   /**
    * Create an empty meal plan.
    * 
    * @param name
    *           Name of plan.
    */
   public MealPlan(String name) {
      this.name = name;
      mealList = new ArrayList<List<MealSuggestion>>();
      mealList.add(new ArrayList<MealSuggestion>());
   }

   /**
    * Create a new meal plan.
    * 
    * @param name
    *           Name of plan.
    * @param mealList
    *           List of MealSuggestion.
    */
   public MealPlan(String name, List<List<MealSuggestion>> mealList) {
      this.name = name;
      this.mealList = mealList;
      nextSuggestionNumber = 1;
   }

   /**
    * Add a meal to plan.
    * 
    * @param meal
    *           MealSuggestion to add.
    */
   public void addMeal(MealSuggestion meal) {
      mealList.get(0).add(meal);
   }

   /**
    * Add a meal to an alternative plan. addMeal(meal) is equivalent to
    * addMeal(meal, 0).
    * 
    * @param meal
    *           MealSuggestion to add.
    */
   public void addMeal(MealSuggestion meal, int number) {
      mealList.get(number).add(meal);
   }

   /**
    * MealSuggestion list.
    * 
    * @return List of MealSuggestion.
    */
   public List<MealSuggestion> getMealList() {
      nextSuggestionNumber = 1;
      return mealList.get(0);
   }

   /**
    * Get alternative suggestions. getMealList() is equivalent to
    * getMealList(0).
    * 
    * @return List of MealSuggestion
    */
   public List<MealSuggestion> getMealList(int number) {
      nextSuggestionNumber = number + 1;
      return mealList.get(number);
   }

   /**
    * Name of plan.
    * 
    * @return String.
    */
   public String getName() {
      return name;
   }

   /**
    * Are there more alternative plans?
    * @return True if there are more plans to be fetched.
    */
   public boolean hasMoreSuggestions() {
      return nextSuggestionNumber < mealList.size();
   }

   /**
    * Gets the number of the next alternative plan.
    * @return The number of the next plan.
    */
   public int nextSuggestionNumber() {
      return nextSuggestionNumber;
   }

   /**
    * String representation of a MealPlan.
    * 
    * @return String.
    */
   @Override
   public String toString() {
      String s = "Name: " + name + "\n";
      for (int index = 0; index < mealList.get(0).size(); index++) {
         s += mealList.get(0).get(index) + "\n";
      }
      return s;
   }
}
