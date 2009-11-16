package dataObjects;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

import data.Settings;

/**
 * Represents an ingredient category
 */
public class IngredientCategory {

   /**
    * Gets a translation map between english and swedish.
    * 
    * @deprecated Does it's own translations, this should be done in a
    *             centralized manner for the entire system.
    * @return A mapping between english and swedish names.
    */
   public static HashMap<String, String> getCategoryTranslations() {
      HashMap<String, String> categoryTranslationsMap = new HashMap<String, String>();
      String englishName;
      String swedishName;
      String bothNames[];
      try {
         InputStreamReader ir = new InputStreamReader(
               ClassLoader
                     .getSystemResourceAsStream(Settings
                           .getFilePath("IngredientCategory.TranslatedCategoriesDataFile")),
               "UTF-8");
         BufferedReader in = new BufferedReader(ir);
         while ((englishName = in.readLine()) != null) {
            bothNames = englishName.split(" ");
            englishName = bothNames[0];
            swedishName = bothNames[1];
            for (int index = 2; index < bothNames.length; ++index) {
               swedishName += " " + bothNames[index];
            }
            categoryTranslationsMap.put(englishName, swedishName);
         }
      } catch (FileNotFoundException e) {
         e.printStackTrace();
      } catch (IOException e) {
         e.printStackTrace();
      }
      return categoryTranslationsMap;
   }

   /**
    * Reads the ingredient categories from file. 
    * @return A list of all ingredient categories.
    */
   public static ArrayList<IngredientCategory> getIngredientCategoriesFromFile() {
      ArrayList<IngredientCategory> ingredientCategories = null;

      try {
         InputStreamReader ir = new InputStreamReader(ClassLoader
               .getSystemResourceAsStream(Settings
                     .getFilePath("IngredientCategory.dataFile")), "UTF-8");
         BufferedReader catFile = new BufferedReader(ir);
         ingredientCategories = new ArrayList<IngredientCategory>();
         Scanner scanner = null;

         scanner = new Scanner(catFile);
         int nr = 0;
         while (scanner.hasNextLine()) {
            String catName = scanner.nextLine();
            IngredientCategory newCat = new IngredientCategory(catName, nr);
            ingredientCategories.add(newCat);
            nr++;
         }
      } catch (Exception e) {
         System.err
               .format(
                     "Unable to read file %s.", Settings.getFilePath("IngredientCategory.dataFile")); 
         e.printStackTrace();
      }
      return ingredientCategories;
   }

   /**
    * Gets tha mappings from category id to Ingredient lists.
    * @return The mappings
    */
   public static HashMap<Integer, ArrayList<IngredientAvailability>> getIngredientCategoryMapping() {
      HashMap<Integer, ArrayList<IngredientAvailability>> ingredientMap = new HashMap<Integer, ArrayList<IngredientAvailability>>();
      String ingredient;
      Integer category;
      String ingrcat[];
      try {
         InputStreamReader ir = new InputStreamReader(ClassLoader
               .getSystemResourceAsStream(Settings
                     .getFilePath("IngredientCategory.CategoryMappingFile")),
               "UTF-8");
         BufferedReader in = new BufferedReader(ir);
         MealDate expDate = new MealDate("2005-12-12"); 
         float amount = new Float(1);
         while ((ingredient = in.readLine()) != null) {
            ingrcat = ingredient.split(" "); 
            ingredient = ingrcat[0];
            for (int index = 1; index < ingrcat.length - 1; ++index) {
               ingredient += " " + ingrcat[index]; 
            } 
            category = new Integer(ingrcat[ingrcat.length - 1]); 

            IngredientAvailability ingrAvailability = new IngredientAvailability(
                  ingredient, amount, expDate);

            if (ingredientMap.containsKey(category))
               ingredientMap.get(category).add(ingrAvailability);
            else {
               ArrayList<IngredientAvailability> ingrList = new ArrayList<IngredientAvailability>();
               ingrList.add(ingrAvailability);
               ingredientMap.put(category, ingrList);
            }
         }
      } catch (FileNotFoundException e) {
         e.printStackTrace();
      } catch (IOException e) {
         e.printStackTrace();
      }
      return ingredientMap;
   }

   /** A list of ingredients in this category. */
   private ArrayList<IngredientAvailability> ingredients;

   /** The unique name of this category. */
   private String                            name;

   /** The unique number of the category. */
   private int                               nr;

   /**
    * Creates a new IngredientCategory-object.
    * 
    * @param aName
    *           The name of the category.
    * @param aNr
    *           The number of the category.
    */
   public IngredientCategory(String aName, int aNr) {
      name = aName;
      nr = aNr;
      ingredients = getIngredientCategoryMapping().get(nr + 1);
      if (ingredients == null) {
         ingredients = new ArrayList<IngredientAvailability>();
      }
   }

   /**
    * Returns the list of this category's ingredients.
    * 
    * @return The list of ingredients.
    */
   public ArrayList<IngredientAvailability> getIngredients() {
      return ingredients;
   }

   /**
    * Gets the name of the category.
    * 
    * @return The name.
    */
   public String getName() {
      return name;
   }

   /**
    * Gets the number of this category.
    * 
    * @return The number.
    */
   public int getNr() {
      return nr;
   }

   /**
    * Sets the list with the ingredients in this category.
    * 
    * @param ingredients
    *           A list vith the ingredients for this category.
    */
   public void setIngredients(ArrayList<IngredientAvailability> ingredients) {
      this.ingredients = ingredients;
   }

   /**
    * Returns the human readable swedish name of the category.
    */
   @Override
   public String toString() {
      Map<String, String> translate = IngredientCategory
            .getCategoryTranslations();
      return translate.get(getName());
   }
}
