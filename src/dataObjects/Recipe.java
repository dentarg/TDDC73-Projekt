/*
 * Created using IntelliJ IDEA. User: x04nicsu Date: 2005-jan-13 Time: 15:45:19
 */
package dataObjects;

import java.util.ArrayList;

import javax.swing.ImageIcon;

import dataObjects.requirements.IngredientRequirement;
import dataObjects.units.Measurement;
import dataStorage.IngredientNutritionDataDS;

/**
 * Class representing a recipe.
 */
public class Recipe {
   private final static IngredientNutritionDataDS nutritionDS = new IngredientNutritionDataDS();
   private ArrayList<String>                      categoryList;
   private float                                  cost;
   private int                                    difficulty;
   private ArrayList<IngredientRequirement>       ingredientList;
   private String                                 name;
   private NutritionData                          nutrition   = new NutritionData();
   private int                                    time;
   private String                                 description;
   private static ImageIcon                       img         = new ImageIcon(
                                                                    Recipe.class
                                                                          .getClassLoader()
                                                                          .getResource(
                                                                                "img/dish.jpg"));

   /**
    * Create a new recipe.
    * 
    * @param name
    *           Name of the recipe.
    * @param ingredientList
    *           An ArrayList of ingredients.
    * @param difficulty
    *           0 = easy, 1 = medium, 2 = hard
    * @param cost
    *           Cost of preparing the meal
    * @param time
    *           Time required in minutes
    * @param energyContent
    *           Energy content (calories)
    */
   public Recipe(String name, ArrayList<String> categoryList,
         ArrayList<IngredientRequirement> ingredientList, int difficulty,
         float cost, int time, String description) {
      if (ingredientList == null) {
         throw new IllegalArgumentException();
      }
      this.name = name;
      this.categoryList = categoryList;
      this.ingredientList = ingredientList;
      this.difficulty = difficulty;
      this.cost = cost;
      this.time = time;
      this.description = description;
      NutritionData n;
      for (IngredientRequirement i : ingredientList) {
         n = nutritionDS.get(i.getName());
         if (n != null) {
            nutrition.add(n, i.getAmount());
         }
      }
   }

   /**
    * Gets the calcium measurement
    * @return the measurement
    */
   public Measurement getCalcium() {
      return nutrition.getCalcium();
   }

   /**
    * Gets the carbon hydrates measurement
    * @return the measurement
    */
   public Measurement getCarbohydrates() {
      return nutrition.getCarbohydrates();
   }

   /**
    * Categories of the recipe.
    * 
    * @return ArrayList of String.
    */
   public ArrayList<String> getCategories() {
      return categoryList;
   }

   /**
    * Gets the cholesterol measurement
    * @return the measurement
    */
   public Measurement getCholesterol() {
      return nutrition.getCholesterol();
   }

   /**
    * Cost of the recipe.
    * 
    * @return int
    */
   public float getCost() {
      return cost;
   }

   /**
    * @return the description
    */
   public String getDescription() {
      return description;
   }

   /**
    * Difficulty of the recipe.
    * 
    * @return Difficulty, 1, 2 or 3, int.
    */
   public int getDifficulty() {
      return difficulty;
   }

   /**
    * Gets the energy measurement
    * @return the measurement
    */
   public Measurement getEnergy() {
      return nutrition.getEnergy();
   }

   /**
    * Gets the fat measurement
    * @return the measurement
    */
   public Measurement getFat() {
      return nutrition.getFat();
   }

   /**
    * @return the img
    */
   public ImageIcon getImg() {

      return img;
   }

   /**
    * Ingredients required for the recipe.
    * 
    * @return ArrayList of IngredientRequirement.
    */
   public ArrayList<IngredientRequirement> getIngredients() {
      return ingredientList;
   }

   /**
    * Name of the recipe.
    * 
    * @return Name, String.
    */
   public String getName() {
      return name;
   }

   /**
    * Gets the protein measurement
    * @return the measurement
    */
   public Measurement getProtein() {
      return nutrition.getProtein();
   }

   /**
    * Gets the sodium measurement
    * @return the measurement
    */
   public Measurement getSodium() {
      return nutrition.getSodium();
   }

   /**
    * Time required for recipe.
    * 
    * @return int, time in minutes.
    */
   public int getTime() {
      return time;
   }

   /**
    * Sets the calcium measurement
    * @param calcium the measurement
    */
   public void setCalcium(Measurement calcium) {
      this.nutrition.setCalcium(calcium);
   }

   /**
    * Sets the carbon hydrates measurement
    * @param carbohydrates the measurement
    */
   public void setCarbohydrates(Measurement carbohydrates) {
      this.nutrition.setCarbohydrates(carbohydrates);
   }

   /**
    * Sets categories of the recipe.
    * 
    * @param categories
    *           ArrayList of String.
    */
   public void setCategories(ArrayList<String> categories) {
      categoryList = categories;
   }

   /**
    * Sets the cholesterol measurement
    * @param cholesterol the measurement
    */
   public void setCholesterol(Measurement cholesterol) {
      this.nutrition.setCholesterol(cholesterol);
   }

   /**
    * Sets the cost
    * @param the cost
    */
   public void setCost(float cost) {
      this.cost = cost;
   }

   /**
    * @param description
    *           the description to set
    */
   public void setDescription(String description) {
      this.description = description;
   }

   /**
    * Sets the energy measurement
    * @param energy the measurement
    */
   public void setEnergy(Measurement energy) {
      this.nutrition.setEnergy(energy);
   }

   
   /**
    * Sets the fat measurement
    * @param fat the measurement
    */
   public void setFat(Measurement fat) {
      this.nutrition.setEnergy(fat);
   }

   /**
    * @param img
    *           the img to set
    */
   public void setImg(ImageIcon img) {
      throw new AssertionError();
      // Recipe.img = img;
   }

   /**
    * Sets the protein measurement
    * @param protein the measurement
    */
   public void setProtein(Measurement protein) {
      this.nutrition.setProtein(protein);
   }

   /**
    * Sets the sodium measurement
    * @param sodium the measurement
    */
   public void setSodium(Measurement sodium) {
      this.nutrition.setSodium(sodium);
   }

   /**
    * String representation of a Recipe.
    * 
    * @return String.
    */
   @Override
   public String toString() {
      return "Name: " + name + "\nCategories: " + categoryList
            + "\nDifficulty: " + difficulty + "\nCost: " + cost + "\nTime: "
            + time + "\nEnergy: " + nutrition.getEnergy() + "\nIngredients: "
            + ingredientList;
   }
}
