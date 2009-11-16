package model;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import dataAccess.RecipeDA;
import dataObjects.Ingredient;
import dataObjects.Recipe;
import dataObjects.requirements.IngredientRequirement;
import dataObjects.units.Measurement;
import dataStorage.RecipeDS;
import exceptions.RecipeNotFoundException;

/**
 * Class containing methods for recipe management. This should normally be
 * instanciated by an UI.
 */
public class RecipeManager {

   /**
    * Calculates the cost for a given recipe.
    * 
    * @param recipe
    *           The Recipe to calculate cost for.
    * @return Total cost of the ingredients required.
    */
   public static float calculateCost(final Recipe recipe) {
      final List<IngredientRequirement> ingredientRequirements = recipe
            .getIngredients();
      final Map<String, Ingredient> ingredientCosts = RecipeDS
            .getIngredientsFromFile();
      float cost = 0;
      float quantity;
      float ingredientCost;
      String ingredientName;

      for (int index = 0; index < ingredientRequirements.size(); index++) {
         quantity = ingredientRequirements.get(index).getAmount().getQuantity();
         // System.out.println("Amount: " + amount);
         ingredientName = ingredientRequirements.get(index).getName();
         // System.out.println("INgredient name: " + ingredientName);
         ingredientCost = ingredientCosts.get(ingredientName.toLowerCase())
               .getCost();
         // System.out.println("Inrgedient cost: " + ingredientCost);
         cost += quantity * ingredientCost;
      }
      return cost;
   }

   /**
    * **Used by the Solver** Returns the domain of the chosen nutrition variable
    * from a set of recipes
    * 
    * @param nutritionType
    *           Nutrition type.
    * @param recipeList
    *           List of recipes to generate domain from
    * @return ArrayList of String containting the valid values
    */
   public static ArrayList<Measurement> generateCalciumDomain(
         final List<Recipe> recipeList) {
      ArrayList<Measurement> calciumDomain = new ArrayList<Measurement>();
      for (Recipe r : recipeList) {
         Measurement value = r.getCalcium();
         if (!calciumDomain.contains(value)) {
            calciumDomain.add(value);
         }
      }
      return calciumDomain;
   }

   public static ArrayList<Measurement> generateCarbohydratesDomain(
         final List<Recipe> recipeList) {
      ArrayList<Measurement> carbohydratesDomain = new ArrayList<Measurement>();
      for (Recipe r : recipeList) {
         Measurement value = r.getCarbohydrates();
         if (!carbohydratesDomain.contains(value)) {
            carbohydratesDomain.add(value);
         }
      }
      return carbohydratesDomain;
   }

   public static ArrayList<Measurement> generateCholesterolDomain(
         final List<Recipe> recipeList) {
      ArrayList<Measurement> cholesterolDomain = new ArrayList<Measurement>();
      for (Recipe r : recipeList) {
         Measurement value = r.getCholesterol();
         if (!cholesterolDomain.contains(value)) {
            cholesterolDomain.add(value);
         }
      }
      return cholesterolDomain;
   }

   /**
    * **Used by the Solver** Returns the domain of the cost variable from a set
    * of recipes
    * 
    * @param recipeList
    *           List of recipes to generate domain from
    * @return ArrayList of String containting the valid values
    */
   public static ArrayList<String> generateCostDomain(
         final List<Recipe> recipeList) {
      final ArrayList<String> domain = new ArrayList<String>();
      String cost;
      for (int index = 0; index < recipeList.size(); index++) {
         cost = String.valueOf(recipeList.get(index).getCost());
         if (!domain.contains(cost)) {
            domain.add(cost);
         }
      }
      return domain;
   }

   /**
    * **Used by the Solver** Returns the domain of the difficulty variable,
    * change this if more levels of difficulty are desired.
    * 
    * @return ArrayList of String, valid values for the difficulty variable
    */
   public static ArrayList<String> generateDifficultyDomain() {
      final ArrayList<String> domain = new ArrayList<String>();
      domain.add("-1");
      domain.add("1");
      domain.add("2");
      domain.add("3");
      return domain;
   }

   public static ArrayList<Measurement> generateEnergyDomain(
         final List<Recipe> recipeList) {
      ArrayList<Measurement> energyDomain = new ArrayList<Measurement>();
      for (Recipe r : recipeList) {
         Measurement value = r.getEnergy();
         if (!energyDomain.contains(value)) {
            energyDomain.add(value);
         }
      }
      return energyDomain;
   }

   public static ArrayList<Measurement> generateFatDomain(
         final List<Recipe> recipeList) {
      ArrayList<Measurement> fatDomain = new ArrayList<Measurement>();
      for (Recipe r : recipeList) {
         Measurement value = r.getFat();
         if (!fatDomain.contains(value)) {
            fatDomain.add(value);
         }
      }
      return fatDomain;
   }

   public static ArrayList<Measurement> generateProteinDomain(
         final List<Recipe> recipeList) {
      ArrayList<Measurement> proteinDomain = new ArrayList<Measurement>();
      for (Recipe r : recipeList) {
         Measurement value = r.getProtein();
         if (!proteinDomain.contains(value)) {
            proteinDomain.add(value);
         }
      }
      return proteinDomain;
   }

   /**
    * **Used by the Solver** Returns the domain of the recipeName variables from
    * a set of recipes
    * 
    * @param recipeList
    *           List of recipes to generate domain from
    * @return ArrayList of String containing the valid names
    */
   public static ArrayList<String> generateRecipeDomain(
         final List<Recipe> recipeList) {
      final ArrayList<String> domain = new ArrayList<String>();
      String name;
      for (int index = 0; index < recipeList.size(); index++) {
         name = recipeList.get(index).getName();
         domain.add(name);
      }
      return domain;
   }

   public static ArrayList<Measurement> generateSodiumDomain(
         final List<Recipe> recipeList) {
      ArrayList<Measurement> sodiumDomain = new ArrayList<Measurement>();
      for (Recipe r : recipeList) {
         Measurement value = r.getSodium();
         if (!sodiumDomain.contains(value)) {
            sodiumDomain.add(value);
         }
      }
      return sodiumDomain;
   }

   /**
    * **Used by the Solver** Returns the domain of the time variable from a set
    * of recipes
    * 
    * @param recipeList
    *           List of recipes to generate domain from
    * @return ArrayList of String containting the valid values
    */
   public static ArrayList<String> generateTimeDomain(
         final List<Recipe> recipeList) {
      final ArrayList<String> domain = new ArrayList<String>();
      String time;
      for (int index = 0; index < recipeList.size(); index++) {
         time = "" + recipeList.get(index).getTime();
         if (!domain.contains(time)) {
            domain.add(time);
         }
      }
      return domain;
   }

   private RecipeDA recipeDA;

   /**
    * Creates a new recipe manager.
    */
   public RecipeManager() {
      recipeDA = new RecipeDA();
   }

   /**
    * **Used for testing** Generates random recipes in internal (benchmark)
    * format to a given file.
    * 
    * @param numberOfRecipes
    *           Number of Recipes to generate.
    * @param recipeFile
    *           Name of the file to save recipes to.
    */
   public void generateRecipesToFile(final int numberOfRecipes,
         final String recipeFile) {
      System.out.println("Starting to generate random recipes.");
      final ArrayList<Recipe> recipes = recipeDA
            .generateRandomRecipes(numberOfRecipes);
      System.out.println("Calling recipeDA.saveGeneratedRecipes.");
      recipeDA.saveGeneratedRecipes(recipes, recipeFile);
   }

   /**
    * **Used for testing** Retrives all recipes in storage.
    * 
    * @return ArrayList of all Recipes in storage.
    */
   public ArrayList<Recipe> getAllRecipes() {
      return recipeDA.getAllRecipes();
   }

   /**
    * Retrieves a recipe with the given name from storage. Preferably
    * verifyRecipe should be called first to make sure that the recipe exists.
    * 
    * @param recipeName
    *           Name of the recipe to retrieve, given as String.
    * @return Recipe with the given name, or null if it does not exist.
    */
   public Recipe getRecipe(final String recipeName) {
      Recipe recipe = null;
      try {
         recipe = recipeDA.getRecipe(recipeName);
      } catch (RecipeNotFoundException e) {
         e.printStackTrace();
      }
      return recipe;
   }

   /**
    * Returns this managers recipe data access object, needed by the Solver's
    * generatePlan method.
    * 
    * @return RecipeDA object.
    */
   public RecipeDA getRecipeDA() {
      return recipeDA;
   }

   /**
    * **Used for testing** Loads recipes in internal (benchmark) format from a
    * given file.
    * 
    * @param fileName
    *           Name of the file to load recipes from.
    */
   public void loadGeneratedRecipes(final String fileName) {
      recipeDA.loadGeneratedRecipes(fileName);
   }

   /**
    * Loads recipes in external format from a given file.
    * 
    * @param fileName
    *           File name to load from
    */
   public void loadRecipes(final String fileName) {
      recipeDA.loadRecipes(fileName);
   }

   /**
    * **Used for testing** Saves recipes in internal (benchmark) format to a
    * given file.
    * 
    * @param recipes
    *           Recipes to save.
    * @param fileName
    *           Name of the file to save recipes to.
    */
   public void saveGeneratedRecipes(final List<Recipe> recipes,
         final String fileName) {
      recipeDA.saveGeneratedRecipes(recipes, fileName);
   }

   /**
    * Verifies that a recipe with the given name exists.
    * 
    * @param recipeName
    *           Name of the Recipe to look for.
    * @throws RecipeNotFoundException
    *            If the Recipe does not exist.
    */
   public void verifyRecipe(final String recipeName)
         throws RecipeNotFoundException {
      recipeDA.getRecipe(recipeName);
   }
}
