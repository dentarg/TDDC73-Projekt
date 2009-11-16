/*
 * PlanVariables.java
 */

package dataObjects;

import java.util.ArrayList;
import java.util.List;

import mappings.AvailabilityMap;
import mappings.CostMap;
import mappings.DifficultyMap;
import mappings.SpecIngredientsMap;
import mappings.SubjectMap;
import mappings.TimeMap;
import mappings.VariationMap;

import dataAccess.RecipeDA;
import dataAccess.SubjectDA;

/**
 * Variables for the CS solver.
 */
public class PlanVariables {

   /** Mapping between availability and dates */
   private AvailabilityMap              availabilityMap;

   /** Availible ingredients */
   private List<IngredientAvailability> availableIngredients;

   /** Mapping between categories to avoid and dates */
   private SpecIngredientsMap           avoidCategoriesMap;

   /** Mapping between ingredients to avoid and dates */
   private SpecIngredientsMap           avoidIngredientsMap;

   /** Mapping between cost and dates */
   private CostMap                      costMap;

   /** Mapping between difficulty and dates */
   private DifficultyMap                difficultyMap;

   /** Mapping between available ingredient categories and dates */
   private SpecIngredientsMap           includeCategoriesMap;

   /** Mapping between availible ingredients and dates */
   private SpecIngredientsMap           includeIngredientsMap;

   /** The name for this plan. */
   private String                       planName;

   /** The planned time period, ie the planned dates */
   private ArrayList<MealDate>          plannedDates;

   /** Info about the recipes */
   private RecipeDA                     recipeDA;

   /** Info about the users */
   private SubjectDA                    subjectDA;

   /** Mapping between users and dates */
   private SubjectMap                   subjectMap;

   /** Mapping between cooking time and dates */
   private TimeMap                      timeMap;

   /** Mapping between variation and dates */
   private VariationMap                 variationMap;

   /** Creates an empty parameter set for the planner. */
   public PlanVariables() {
      planName = "namn";
      subjectMap = new SubjectMap();
      difficultyMap = new DifficultyMap();
      timeMap = new TimeMap();
      costMap = new CostMap();
      availabilityMap = new AvailabilityMap();
      plannedDates = new ArrayList<MealDate>();
      variationMap = new VariationMap();
      includeIngredientsMap = new SpecIngredientsMap();
      avoidIngredientsMap = new SpecIngredientsMap();
      includeCategoriesMap = new SpecIngredientsMap();
      avoidCategoriesMap = new SpecIngredientsMap();
      availableIngredients = new ArrayList<IngredientAvailability>();
      subjectDA = new SubjectDA();
   }

   /**
    * Gets a mapping between availability and dates
    * 
    * @return Mapping between availability and dates
    */
   public AvailabilityMap getAvailabilityMap() {
      return availabilityMap;
   }

   /**
    * Gets a the available ingredients
    * 
    * @return The available ingresdients
    */
   public List<IngredientAvailability> getAvailableIngredients() {
      return availableIngredients;
   }

   /**
    * Gets a mapping between avoided categories and dates
    * 
    * @return Mapping between avoided categories and dates
    */
   public SpecIngredientsMap getAvoidCategoriesMap() {
      return avoidCategoriesMap;
   }

   /**
    * Gets a mapping between avoided ingredients and dates
    * 
    * @return Mapping between avoided ingredients and dates
    */
   public SpecIngredientsMap getAvoidIngredientsMap() {
      return avoidIngredientsMap;
   }

   /**
    * Gets a mapping between cost and dates
    * 
    * @return Mapping between cost and dates
    */
   public CostMap getCostMap() {
      return costMap;
   }

   /**
    * Gets a mapping between difficulty and dates
    * 
    * @return Mapping between difficulty and dates
    */
   public DifficultyMap getDifficultyMap() {
      return difficultyMap;
   }

   /**
    * Gets a mapping between available categories and dates
    * 
    * @return Mapping between available categories and dates
    */
   public SpecIngredientsMap getIncludeCategoriesMap() {
      return includeCategoriesMap;
   }

   /**
    * Gets a mapping between available ingredients and dates
    * 
    * @return Mapping between available ingredients and dates
    */
   public SpecIngredientsMap getIncludeIngredientsMap() {
      return includeIngredientsMap;
   }

   /**
    * Gets the name for this plan
    * 
    * @return the name
    */
   public String getPlanName() {
      return planName;
   }

   /**
    * Gets a the planning period, ie the selected dates.
    * 
    * @return The selected dates.
    */
   public ArrayList<MealDate> getPlannedDates() {
      return plannedDates;
   }

   /**
    * Gets information object for recipes
    * 
    * @return information object for recipes.
    */
   public RecipeDA getRecipeDA() {
      return recipeDA;
   }

   /**
    * Gets information object for users
    * 
    * @return information object for users.
    */
   public SubjectDA getSubjectDA() {
      return subjectDA;
   }

   /**
    * Gets a mapping between users and dates
    * 
    * @return Mapping between users and dates
    */
   public SubjectMap getSubjectMap() {
      return subjectMap;
   }

   /**
    * Gets a mapping between cooking time and dates
    * 
    * @return Mapping between cooking time and dates
    */
   public TimeMap getTimeMap() {
      return timeMap;
   }

   /**
    * Gets a mapping between variation and dates
    * 
    * @return Mapping between variation and dates
    */
   public VariationMap getVariationMap() {
      return variationMap;
   }

   /**
    * Sets a mapping between availability and dates
    * 
    * @param availabilityMap Mapping between availability and dates
    */
   public void setAvailabilityMap(AvailabilityMap availabilityMap) {
      this.availabilityMap = availabilityMap;
   }

   /**
    * Sets available ingredients
    * 
    * @param ingrs available ingredients.
    */
   public void setAvailableIngredients(List<IngredientAvailability> ingrs) {
      this.availableIngredients = ingrs;
   }

   /**
    * Sets a mapping between categories to avoid and dates
    * 
    * @param avoidCategoriesMap Mapping between categories to avoid and dates
    */
   public void setAvoidCategoriesMap(SpecIngredientsMap avoidCategoriesMap) {
      this.avoidCategoriesMap = avoidCategoriesMap;
   }

   /**
    * Sets a mapping between ingredients to avoid and dates
    * 
    * @param avoidIngredientsMap Mapping between ingreduients to avoid and dates
    */
   public void setAvoidIngredientsMap(SpecIngredientsMap avoidIngredientsMap) {
      this.avoidIngredientsMap = avoidIngredientsMap;
   }

   /**
    * Sets a mapping between cost and dates
    * 
    * @param costMap Mapping between cost and dates
    */
   public void setCostMap(CostMap costMap) {
      this.costMap = costMap;
   }

   /**
    * Sets a mapping between difficulty and dates
    * 
    * @param difficultyMap Mapping between difficulty and dates
    */
   public void setDifficultyMap(DifficultyMap difficultyMap) {
      this.difficultyMap = difficultyMap;
   }

   /**
    * Sets the planning period
    * 
    * @param plannedDates the planned dates
    */
   public void setPlannedDates(ArrayList<MealDate> plannedDates) {
      this.plannedDates = plannedDates;
   }

   /**
    * Sets information object for recipes
    * 
    * @param recipeDA
    *           information object for recipes.
    */
   public void setRecipeDA(RecipeDA recipeDA) {
      this.recipeDA = recipeDA;
   }

   /**
    * Sets information object for users
    * 
    * @param userDA
    *           information object for users.
    */
   public void setSubjectDA(SubjectDA subjectDA) {
      this.subjectDA = subjectDA;
   }

   /**
    * Sets a mapping between users and dates
    * 
    * @param subjectMap Mapping between users and dates
    */
   public void setSubjectMap(SubjectMap subjectMap) {
      this.subjectMap = subjectMap;
   }

   /**
    * Sets a mapping between cooking time and dates
    * 
    * @param timeMap Mapping between cooking time and dates
    */
   public void setTimeMap(TimeMap timeMap) {
      this.timeMap = timeMap;
   }

   /**
    * Sets a mapping between variation and dates
    * 
    * @param variationMap Mapping between variation and dates
    */
   public void setVariationMap(VariationMap variationMap) {
      this.variationMap = variationMap;
   }
}
