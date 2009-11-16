package dataAccess;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Random;

import model.RecipeManager;
import dataObjects.Assignment;
import dataObjects.Ingredient;
import dataObjects.Preference;
import dataObjects.Recipe;
import dataObjects.SingleAssignment;
import dataObjects.Subject;
import dataObjects.requirements.IngredientRequirement;
import dataObjects.units.Measurement;
import dataStorage.RecipeDS;
import exceptions.RecipeNotFoundException;

/**
 * Data access class for recipes.
 */
public class RecipeDA {
   private class RecipeValuation {
      Recipe recipe;
      float  value;

      RecipeValuation(Recipe recipe, float value) {
         this.recipe = recipe;
         this.value = value;
      }

      Recipe getRecipe() {
         return recipe;
      }

      float getValue() {
         return value;
      }
   }

   /**
    * The datasource to use for getting recipes.
    */
   private RecipeDS recipeDS;

   /**
    * Create a new recipe data access object.
    */
   public RecipeDA() {
      recipeDS = new RecipeDS();
   }

   /**
    * Adds a recipe to underlying storage.
    * 
    * @param r
    *           Recipe to add.
    */
   public void addRecipe(final Recipe r) {
      recipeDS.add(r);
   }

   /**
    * Calculates the sum of the grades of the graded recipies.
    * 
    * @param recipe
    *           The recipie to claculate a sum for.
    * @param preferences
    *           The user preferences to take into account.
    * @return The sum of the grades.
    */
   private Float calculatePreferenceSum(String recipe,
         List<Preference> preferences) {
      float preferenceScore = 0;
      Preference currentPref;
      for (int preferenceIndex = 0; preferenceIndex < preferences.size(); preferenceIndex++) {
         currentPref = preferences.get(preferenceIndex);
         if (currentPref.getRecipeName().equalsIgnoreCase(recipe)) {
            preferenceScore += currentPref.getRating();
         }
      }
      return new Float(preferenceScore);
   }

   /**
    * Gets random categories, intended for testing.
    * 
    * @param categoryNumber
    *           The maximum number of categories in the response.
    * @return The generated list of categories.
    */
   private ArrayList<String> generateCategories(final int categoryNumber) {
      final Random RNG = new Random();
      String category;
      final ArrayList<String> usedCategories = new ArrayList<String>();
      final ArrayList<String> validCategories = recipeDS.getCategoryList();
      final ArrayList<String> categories = new ArrayList<String>();
      for (int index2 = 0; index2 < categoryNumber; index2++) {
         category = validCategories.get(Math.abs(RNG.nextInt()
               % validCategories.size()));
         if (!usedCategories.contains(category)) {
            usedCategories.add(category);
            categories.add(category);
         }
      }
      return categories;
   }

   /**
    * Gets random ingredients, intended for testing.
    * 
    * @param categoryNumber
    *           The maximum number of ingredients in the response.
    * @return The generated list of ingredients.
    */
   private ArrayList<IngredientRequirement> generateIngredients(
         final int ingredientNumber) {
      final Random RNG = new Random();
      IngredientRequirement ingredientRequirement;
      String ingredientName;
      final ArrayList<String> usedIngredients = new ArrayList<String>();
      final ArrayList<Ingredient> validIngredients = new ArrayList<Ingredient>(
            recipeDS.getIngredientMap().values());
      final ArrayList<IngredientRequirement> ingredients = new ArrayList<IngredientRequirement>();
      for (int index2 = 0; index2 < ingredientNumber; index2++) {
         ingredientName = validIngredients.get(
               Math.abs(RNG.nextInt() % validIngredients.size())).getName();
         if (!usedIngredients.contains(ingredientName)) {
            usedIngredients.add(ingredientName);
            ingredientRequirement = new IngredientRequirement(ingredientName,
                  Math.abs(RNG.nextInt() % 10), "BogusUnit");
            ingredients.add(ingredientRequirement);
         }
      }
      return ingredients;
   }

   /**
    * Generates a number of random recipes for testing purposes.
    * 
    * @param numberOfRecipes
    *           The number of recipes to generate.
    * @return ArrayList containing the Recipes.
    */
   /*
    * Does unchecked conversion of cloned ArrayLists.
    */
   @SuppressWarnings("unchecked")
   public ArrayList<Recipe> generateRandomRecipes(final int numberOfRecipes) {
      final Random RNG = new Random();
      int ingredientNumber;
      int categoryNumber;
      ArrayList<IngredientRequirement> ingredients;
      ArrayList<Recipe> recipes = new ArrayList<Recipe>();
      ArrayList<String> categories;
      int randomDifficulty;
      int randomTime;
      float cost;
      Recipe recipe;

      for (int index = 0; index < numberOfRecipes; index++) {
         ingredientNumber = Math.abs((int) (10 + RNG.nextGaussian() * 4)) + 1;
         ingredients = generateIngredients(ingredientNumber);
         categoryNumber = Math.abs((int) (3 + RNG.nextGaussian() * 1)) + 1;
         categories = generateCategories(categoryNumber);
         randomDifficulty = Math.abs(RNG.nextInt() % 3) + 1;
         randomTime = Math.abs((int) (60 + RNG.nextGaussian() * 25));
         recipe = new Recipe("RandomRecipe" + index,
               (ArrayList<String>) categories.clone(),
               (ArrayList<IngredientRequirement>) ingredients.clone(),
               randomDifficulty, 0, randomTime, "");
         cost = RecipeManager.calculateCost(recipe);
         recipe.setCost(cost);
         recipes.add(recipe);
         ingredients.clear();
      }
      return recipes;
   }

   /**
    * Returns an Arraylist containing all the recipes currently in the database.
    * 
    * @return ArrayList of all recipes.
    */
   public ArrayList<Recipe> getAllRecipes() {
      return new ArrayList<Recipe>(recipeDS.getAllRecipes());
   }

   /**
    * Gets a map of ingredient names and ingredients.
    * 
    * @return The map
    */
   public Map<String, Ingredient> getIngredientMap() {
      return recipeDS.getIngredientMap();
   }

   /**
    * Matches a given assignment to a recipe. If more than one recipe matches
    * (this should not happen) any one of them is returned. <b>This function is
    * intended for use with the constraint solver, and is thus not supposed to
    * be used in this project.</b>
    * 
    * @param assign
    *           The assignment to match to a recipe.
    * @return A matching recipe, or null if none exists.
    */
   public Recipe getMatchingRecipe(final Assignment assign) {
      if (assign.completeAssignment()) {
         final ArrayList<Recipe> recipeList = getAllRecipes();
         Recipe currentRecipe;
         SingleAssignment currentAssignment;
         String currentVariableName;
         String currentValue;
         RecipeLoop: for (int index = 0; index < recipeList.size(); index++) {
            currentRecipe = recipeList.get(index);
            for (int index2 = 0; index2 < 10; index2++) {
               currentAssignment = assign.getAssignment(index2);
               currentVariableName = currentAssignment.getVariable().getName();
               currentValue = currentAssignment.getValue();
               if (currentVariableName.equalsIgnoreCase("difficulty")) {
                  if (Integer.parseInt(currentValue) != currentRecipe
                        .getDifficulty()) {
                     continue RecipeLoop;
                  }
               } else if (currentVariableName.equalsIgnoreCase("time")) {
                  if (Integer.parseInt(currentValue) != currentRecipe.getTime()) {
                     continue RecipeLoop;
                  }
               } else if (currentVariableName.equalsIgnoreCase("cost")) {
                  if (Float.parseFloat(currentValue) != currentRecipe.getCost()) {
                     continue RecipeLoop;
                  }
               } else if (currentVariableName.equalsIgnoreCase("carbohydrates")) {
                  if (Measurement.createFromString(currentValue).compareTo(
                        currentRecipe.getCarbohydrates()) != 0) {
                     continue RecipeLoop;
                  }
               } else if (currentVariableName.equalsIgnoreCase("energyContent")) {
                  if (Measurement.createFromString(currentValue).compareTo(
                        currentRecipe.getEnergy()) != 0) {
                     continue RecipeLoop;
                  }
               } else if (currentVariableName.equalsIgnoreCase("protein")) {
                  if (Measurement.createFromString(currentValue).compareTo(
                        currentRecipe.getProtein()) != 0) {
                     continue RecipeLoop;
                  }
               } else if (currentVariableName.equalsIgnoreCase("fat")) {
                  if (Measurement.createFromString(currentValue).compareTo(
                        currentRecipe.getFat()) != 0) {
                     continue RecipeLoop;
                  }
               } else if (currentVariableName.equalsIgnoreCase("cholesterol")) {
                  if (Measurement.createFromString(currentValue).compareTo(
                        currentRecipe.getCholesterol()) != 0) {
                     continue RecipeLoop;
                  }
               } else if (currentVariableName.equalsIgnoreCase("natrium")) {
                  if (Measurement.createFromString(currentValue).compareTo(
                        currentRecipe.getSodium()) != 0) {
                     continue RecipeLoop;
                  }
               } else if (currentVariableName.equalsIgnoreCase("calcium")) {
                  if (Measurement.createFromString(currentValue).compareTo(
                        currentRecipe.getCalcium()) != 0) {
                     continue RecipeLoop;
                  }
               }
            }
            return currentRecipe;
         }
      }
      return null;
   }

   /**
    * Gets a recipe from underlying storage.
    * 
    * @param name
    *           Name of Recipe to get.
    * @return Recipe or null
    * @throws RecipeNotFoundException
    */
   public Recipe getRecipe(final String name) throws RecipeNotFoundException {
      return recipeDS.get(name);
   }

   /**
    * Loads recipes to underlying storage from a file in internal format.
    * 
    * @param fileName
    *           Name of file to read from.
    */
   public void loadGeneratedRecipes(final String fileName) {
      recipeDS.readGeneratedFromFile(fileName);
   }

   /**
    * Loads recipes from a file in external format.
    * 
    * @param fileName
    *           Name of file to load from
    */
   public void loadRecipes(final String fileName) {
      recipeDS.readFromFile(fileName);
   }

   /**
    * Modifies a recipe in underlying storage.
    * 
    * @param r
    *           Modified Recipe.
    */
   public void modifyRecipe(final Recipe r) {
      recipeDS.set(r);
   }

   /**
    * Gets a list of recipies sorted by valuation.
    * 
    * @param valuations
    *           the recipievaluations to sort
    * @return the sorted recipes
    */
   private List<Recipe> quickSortRecipes(List<RecipeValuation> valuations) {
      List<RecipeValuation> sortedValuations = quickSortValuations(valuations);
      List<Recipe> sortedRecipes = new ArrayList<Recipe>();
      for (int index = 0; index < sortedValuations.size(); index++) {
         sortedRecipes.add(sortedValuations.get(index).getRecipe());
      }
      return sortedRecipes;
   }

   /**
    * Sorts a list of RecipeValuations
    * 
    * @param values
    *           The list to sort
    * @return The sorted list.
    */
   private List<RecipeValuation> quickSortValuations(
         List<RecipeValuation> values) {
      float pivot = values.get(0).getValue();
      float currentScore;
      RecipeValuation currentValuation;
      List<RecipeValuation> largerList = new ArrayList<RecipeValuation>();
      List<RecipeValuation> smallerList = new ArrayList<RecipeValuation>();
      List<RecipeValuation> equalList = new ArrayList<RecipeValuation>();
      List<RecipeValuation> sortedList = new ArrayList<RecipeValuation>();
      for (int index = 0; index < values.size(); index++) {
         currentValuation = values.get(index);
         currentScore = currentValuation.getValue();
         if (currentScore > pivot) {
            largerList.add(currentValuation);
         } else if (currentScore < pivot) {
            smallerList.add(currentValuation);
         } else {
            equalList.add(currentValuation);
         }
      }
      if (largerList.size() != 0) {
         sortedList.addAll(quickSortValuations(largerList));
      }
      sortedList.addAll(equalList);
      if (smallerList.size() != 0) {
         sortedList.addAll(quickSortValuations(smallerList));
      }
      return sortedList;
   }

   /**
    * Removes a recipe from underlying storage.
    * 
    * @param name
    *           Name of Recipe to remove.
    */
   public void removeRecipe(final String name) {
      recipeDS.remove(name);
   }

   /**
    * Saves recipes to a file in internal format.
    * 
    * @param recipes
    *           List of Recipes.
    * @param fileName
    *           Name of file to save to.
    */
   public void saveGeneratedRecipes(final List<Recipe> recipes,
         final String fileName) {
      recipeDS.saveGeneratedToFile(recipes, fileName);
   }

   /**
    * Sorts the whole recipe list according to all users' preferences.
    * 
    * @param subjectDA
    *           The subjectDA object to read user preferences from.
    * @return The sorted list of recipes.
    */
   public List<Recipe> sortRecipeList(SubjectDA subjectDA) {
      List<Recipe> recipes = getAllRecipes();
      List<Subject> subjects = subjectDA.getAllSubjects();
      List<RecipeValuation> valuations = new ArrayList<RecipeValuation>();
      List<Recipe> sortedRecipes;
      List<Preference> preferences = new ArrayList<Preference>();
      Recipe currentRecipe;
      Subject currentSubject;

      for (int index = 0; index < subjects.size(); index++) {
         currentSubject = subjects.get(index);
         preferences.addAll(currentSubject.getPreferences().values());
      }

      for (int recipeIndex = 0; recipeIndex < recipes.size(); recipeIndex++) {
         currentRecipe = recipes.get(recipeIndex);
         valuations.add(new RecipeValuation(currentRecipe,
               calculatePreferenceSum(currentRecipe.getName(), preferences)
                     .floatValue()));
      }

      sortedRecipes = quickSortRecipes(valuations);
      return sortedRecipes;
   }
}
