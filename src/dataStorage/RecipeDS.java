package dataStorage;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;

import org.xml.sax.InputSource;
import org.xml.sax.SAXException;
import org.xml.sax.XMLReader;

import xmlParsers.RecipeHandler;
import data.Settings;
import dataObjects.Ingredient;
import dataObjects.Recipe;
import dataObjects.requirements.IngredientRequirement;
import exceptions.RecipeNotFoundException;

/**
 * Class for recipe data storage.
 */
public class RecipeDS {

   /**
    * Get all ingredients by reading them from file.
    * 
    * @return A map of &lt;ingredient name, ingredient&gt;
    */
   public static Map<String, Ingredient> getIngredientsFromFile() {
      return readIngredientsToMap();
   }

   /**
    * Reads a recipe file into a map of &lt;ingredient name, ingredient&gt;
    * pairs.
    * 
    * @return The map of ingredient names to ingredients.
    */
   private static HashMap<String, Ingredient> readIngredientsToMap() {
      HashMap<String, Ingredient> ingredientMap = new HashMap<String, Ingredient>();
      String ingredient;
      String name = "";
      String nameAndCost[];
      int costIndex;
      try {
         InputStreamReader ir = new InputStreamReader(ClassLoader
               .getSystemResourceAsStream(Settings
                     .getFilePath("RecipeDS.IngredientsDataFile")));
         BufferedReader in = new BufferedReader(ir);
         while ((ingredient = in.readLine()) != null) {
            nameAndCost = ingredient.split(" ");
            costIndex = nameAndCost.length - 1;
            for (int index = 0; index < costIndex; index++) {
               name += nameAndCost[index] + " ";
            }
            name = name.substring(0, name.length() - 1);
            ingredientMap.put(name.toLowerCase(), new Ingredient(name, Float
                  .parseFloat(nameAndCost[costIndex])));
            // System.out.println(name); // DEBUG
            name = "";
         }
      } catch (FileNotFoundException e) {
         e.printStackTrace();
      } catch (IOException e) {
         e.printStackTrace();
      }
      return ingredientMap;
   }

   /**
    * The different ingredient category names.
    */
   ArrayList<String>           categoryList;

   /**
    * The different ingredients, stored in a map as &lt;ingredient name,
    * ingredient&gt; pairs.
    */
   HashMap<String, Ingredient> ingredientMap;

   /**
    * The different recipes, stored in a map as &lt;recipe name, recipe&gt;
    * pairs.
    */
   HashMap<String, Recipe>     recipeMap;

   /**
    * Create a new recipe data storage object.
    */
   public RecipeDS() {
      recipeMap = new HashMap<String, Recipe>();
      readFromFile(Settings.getFilePath("RecipeDS.RecipieDataFile"));
      ingredientMap = readIngredientsToMap();
      readCategoriesToList();
   }

   /**
    * Adapts a recipe file from the old format to the new one.
    * 
    * @deprecated Just use resulting recipe list instead of converting on every
    *             run.
    * @param sourceFile
    *           The file in old format.
    * @param targetFile
    *           The file in new format.
    * @throws IOException
    */
   /*
    * This method should be unused since it's deoprecated.
    */
   @SuppressWarnings("unused")
   private void adaptRecipeFile(String sourceFile, String targetFile)
         throws IOException {

      if (sourceFile.startsWith("file:")) {
         sourceFile = sourceFile.substring(5);
      }

      InputStream is = ClassLoader.getSystemResourceAsStream(sourceFile);

      BufferedReader in = new BufferedReader(new InputStreamReader(is));
      char[] read = new char[20000000];
      int index = 4;
      addRecipeListBeginTag(read);
      do {
         removeLeadingRecipeMarking(in);
         removeXMLTags(in);
         do {
            read[index] = (char) in.read();
            if (read[index] == '&') {
               read[index] = '_';
            }
         } while (read[index++] != '}');
         in.read();
         in.read();
         index -= 2;
      } while (in.readLine() != null);
      in.close();
      addRecipeListEndTag(read, index);
      index += 5;
      FileWriter out = new FileWriter(targetFile);
      out.write(read, 0, index);
      out.close();
   }

   /**
    * Adds a recipe to storage.
    * 
    * @param r
    *           Recipe to add.
    */
   public void add(Recipe r) {
      recipeMap.put(r.getName().toLowerCase(), r);
   }

   /**
    * Adds <rl> at the beginning of the char array, overwriting any previous
    * information.
    * 
    * @param chars
    */
   private void addRecipeListBeginTag(char[] chars) {
      chars[0] = '<';
      chars[1] = 'r';
      chars[2] = 'l';
      chars[3] = '>';
   }

   /**
    * Adds </rl> at the given index in the char array, overwriting any previous
    * information.
    * 
    * @param chars
    * @param index
    */
   private void addRecipeListEndTag(char[] chars, int index) {
      chars[index] = '<';
      chars[++index] = '/';
      chars[++index] = 'r';
      chars[++index] = 'l';
      chars[++index] = '>';
   }

   /**
    * Sums up the recipe data for cost, difficulty ant required time.
    * 
    * @return A human readable string containing the summary.
    */
   public String doRangeCounts() {
      // String result;
      int shortTime = 0;
      int mediumTime = 0;
      int longTime = 0;
      int easyDiff = 0;
      int mediumDiff = 0;
      int hardDiff = 0;
      int lowCost = 0;
      int mediumCost = 0;
      int highCost = 0;
      Recipe currentRecipe;
      int currentTime;
      int currentDiff;
      float currentCost;
      for (String currentName : recipeMap.keySet()) {
         currentRecipe = recipeMap.get(currentName);
         currentTime = currentRecipe.getTime();
         currentDiff = currentRecipe.getDifficulty();
         currentCost = currentRecipe.getCost();
         if (currentTime <= 20) {
            shortTime++;
         } else if (currentTime <= 50) {
            mediumTime++;
         } else {
            longTime++;
         }

         if (currentDiff == 1) {
            easyDiff++;
         } else if (currentDiff == 2) {
            mediumDiff++;
         } else {
            hardDiff++;
         }

         if (currentCost <= 20) {
            lowCost++;
         } else if (currentCost <= 50) {
            mediumCost++;
         } else {
            highCost++;
         }
      }
      return "Time - Short: " + shortTime + " Medium: " + mediumTime + " Long: " + longTime + "\n" + //$NON-NLS-2$ //$NON-NLS-3$ //$NON-NLS-4$
            "Cost - Low: "
            + lowCost
            + " Medium: " + mediumCost + " High: " + highCost + "\n" + //$NON-NLS-2$ //$NON-NLS-3$ 
            "Difficulty - Easy: " + easyDiff
            + " Medium: " + mediumDiff + " Hard: " + hardDiff + "\n"; //$NON-NLS-2$ //$NON-NLS-3$ 
   }

   /**
    * Gets a recipe from storage.
    * 
    * @param name
    *           Name of Recipe to get.
    * @return Recipe or null.
    * @throws RecipeNotFoundException
    */
   public Recipe get(String name) throws RecipeNotFoundException {
      Recipe tempRecipe = recipeMap.get(name.toLowerCase());
      if (tempRecipe == null) {
         throw new RecipeNotFoundException();
      }
      return tempRecipe;
   }

   /**
    * Gets all recipes from storage.
    * 
    * @return Collection of Recipe.
    */
   public Collection<Recipe> getAllRecipes() {
      return recipeMap.values();
   }

   /**
    * Gets all categories from storage.
    * 
    * @return The list of category names.
    */
   public ArrayList<String> getCategoryList() {
      return categoryList;
   }

   /**
    * Gets the ingredients as a map of &lt;ingredeint name, ingredient&gt;.
    * 
    * @return
    */
   public Map<String, Ingredient> getIngredientMap() {
      return ingredientMap;
   }

   /**
    * Creates a XMLReader to use for parsing a recipe xml file.
    * 
    * @return The reader to use
    * @throws Exception
    *            if anything goes wrong.
    */
   private XMLReader makeXMLReader() throws Exception {
      SAXParserFactory saxParserFactory = SAXParserFactory.newInstance();
      SAXParser saxParser = saxParserFactory.newSAXParser();
      XMLReader parser = saxParser.getXMLReader();
      return parser;
   }

   /**
    * Reads the category information from file.
    * 
    * @param in
    *           A buffered reader thar reads the category file.
    * @return A list of category names.
    */
   private ArrayList<String> readCategories(BufferedReader in) {
      String s;
      String category;
      ArrayList<String> categoryList = new ArrayList<String>();
      try {
         while (!(s = readNextTag(in)).equalsIgnoreCase("</categoryList>")) {
            if (!s.equalsIgnoreCase("<category>")) {
               throw new IOException("<category> expected");
            }
            category = readNextValue(in);
            if (!readNextTag(in).equalsIgnoreCase("</category>")) {
               throw new IOException("</category> expected");
            }
            categoryList.add(category);
         }
      } catch (IOException e) {
         e.printStackTrace();
      }
      return categoryList;
   }

   /**
    * Reads the category strings into the local "cache" variable categoryList.
    */
   private void readCategoriesToList() {
      categoryList = new ArrayList<String>();
      String category;
      try {
         InputStreamReader ir = new InputStreamReader(ClassLoader
               .getSystemResourceAsStream(Settings
                     .getFilePath("RecipeDS.CategoryDataFile")));
         BufferedReader in = new BufferedReader(ir);
         while ((category = in.readLine()) != null) {
            categoryList.add(category);
         }
      } catch (FileNotFoundException e) {
         e.printStackTrace();
      } catch (IOException e) {
         e.printStackTrace();
      }
   }

   /**
    * Reads recipe data from physical storage.
    */
   public void readFromFile(final String fileName) {
      recipeMap.clear();
      XMLReader reader = null;
      try {
         reader = makeXMLReader();
         reader.setContentHandler(new RecipeHandler(this));

         /*
          * Adapting old recipe files is deprecated.
          */
         //adaptRecipeFile(fileName, System.getProperty("user.dir") + "/" + fileName + ".adapted");  //$NON-NLS-2$ //$NON-NLS-3$
         // reader.parse(new InputSource(System.getProperty("user.dir") + "/"
         // +fileName + ".adapted"));

         reader.parse(new InputSource(ClassLoader
               .getSystemResourceAsStream(Settings
                     .getFilePath("RecipeDS.RecipieDataFileAdapted"))));
      } catch (SAXException e) {
         e.printStackTrace();
      } catch (IOException e) {
         e.printStackTrace();
      } catch (Exception e) {
         e.printStackTrace();
      }
   }

   /**
    * Reads (generated) recipies from file and adds them to the storage.
    * 
    * @param fileName
    *           File name of the file to be read from (ending excluded)
    */
   public void readGeneratedFromFile(String fileName) {
      recipeMap.clear();
      try {
         BufferedReader in = new BufferedReader(new FileReader("data/"
               + fileName + ".data"));
         if (!readNextTag(in).equalsIgnoreCase("<rl>")) {
            throw new IOException("<rl> expected");
         }
         readRecipes(in);
         in.close();
      } catch (FileNotFoundException e) {
         e.printStackTrace();
      } catch (IOException e) {
         e.printStackTrace();
      }
   }

   /**
    * Reads ingredient requirements for a certain recipe
    * 
    * @param in
    *           The buffered reader, positioned top read the ingredient list.
    * @return An ArrayList containing the required ingredients.
    */
   private ArrayList<IngredientRequirement> readIngredients(BufferedReader in) {
      String s;
      String name;
      String amount;
      String unit;
      IngredientRequirement ingredientRequirement;
      ArrayList<IngredientRequirement> ingredientList = new ArrayList<IngredientRequirement>();
      try {
         while (!(s = readNextTag(in)).equalsIgnoreCase("</ingredientList>")) {
            if (!s.equalsIgnoreCase("<ingredient>")) {
               throw new IOException("<ingredient> expected");
            }
            if (!readNextTag(in).equalsIgnoreCase("<name>")) {
               throw new IOException("<name> expected");
            }
            name = readNextValue(in);
            if (!readNextTag(in).equalsIgnoreCase("</name>")) {
               throw new IOException("</name> expected");
            }
            if (!readNextTag(in).equalsIgnoreCase("<amount>")) {
               throw new IOException("<amount> expected");
            }
            amount = readNextValue(in);
            if (!readNextTag(in).equalsIgnoreCase("</amount>")) {
               throw new IOException("</amount> expected");
            }
            if (!readNextTag(in).equalsIgnoreCase("<unit>")) {
               throw new IOException("<unit> expected");
            }
            unit = readNextValue(in);
            if (!readNextTag(in).equalsIgnoreCase("</unit>")) {
               throw new IOException("</unit> expected");
            }
            if (!readNextTag(in).equalsIgnoreCase("</ingredient>")) {
               throw new IOException("</ingredient> expected");
            }
            name = name.replace("-", " ");
            ingredientRequirement = new IngredientRequirement(name, Float
                  .parseFloat(amount), unit);
            ingredientList.add(ingredientRequirement);
         }
      } catch (IOException e) {
         e.printStackTrace();
      }
      return ingredientList;
   }

   /**
    * Reads the next tag from the reader
    * 
    * @param in
    *           The positioned buffered reader
    * @return The string containing the first tag.
    */
   private String readNextTag(BufferedReader in) {
      char[] tag = new char[100];
      tag[0] = '<';
      int pos = 1;
      try {
         do {
            tag[pos] = (char) in.read();
         } while (tag[pos] == '\n' || tag[pos] == '\r');

         if (tag[pos] == '<') {
            pos--;
         }
         do {
            pos++;
            tag[pos] = (char) in.read();
         } while (tag[pos] != '>');
      } catch (IOException e) {
         e.printStackTrace();
      }
      return new String(tag, 0, pos + 1);
   }

   /**
    * Reads the value of a tag (between start and end tags).
    * 
    * @param in
    *           the positioned buffered reader.
    * @return the value.
    */
   private String readNextValue(BufferedReader in) {
      char[] value = new char[100];
      int pos = 0;
      try {
         do {
            value[pos] = (char) in.read();
         } while (value[pos] == '\n' || value[pos] == '\r');

         do {
            if (value[pos] != '\n' && value[pos] != '\r') {
               pos++;
            }
            value[pos] = (char) in.read();
         } while (value[pos] != '<');
      } catch (IOException e) {
         e.printStackTrace();
      }
      return new String(value, 0, pos);
   }

   /**
    * Reads all recipes in that are availible from the buffered reader in.
    * 
    * @param in
    *           The buffered reader to read from.
    */
   private void readRecipes(BufferedReader in) {
      String s;
      String name;
      String difficulty;
      String time;
      String cost;
      ArrayList<IngredientRequirement> ingredientList;
      ArrayList<String> categoryList;
      try {
         while (!(s = readNextTag(in)).equalsIgnoreCase("</rl>")) {
            if (!s.equalsIgnoreCase("<recipe>")) {
               throw new IOException("<recipe> expected, found " + s);
            }
            if (!readNextTag(in).equalsIgnoreCase("<name>")) {
               throw new IOException("<name> expected");
            }
            name = readNextValue(in);
            if (!readNextTag(in).equalsIgnoreCase("</name>")) {
               throw new IOException("</name> expected");
            }
            if (!readNextTag(in).equalsIgnoreCase("<categoryList>")) {
               throw new IOException("<categoryList> expected");
            }
            categoryList = readCategories(in);
            if (!readNextTag(in).equalsIgnoreCase("<difficulty>")) {
               throw new IOException("<difficulty> expected");
            }
            difficulty = readNextValue(in);
            if (!readNextTag(in).equalsIgnoreCase("</difficulty>")) {
               throw new IOException("</difficulty> expected");
            }
            if (!readNextTag(in).equalsIgnoreCase("<time>")) {
               throw new IOException("<time> expected");
            }
            time = readNextValue(in);
            if (!readNextTag(in).equalsIgnoreCase("</time>")) {
               throw new IOException("</time> expected");
            }
            if (!readNextTag(in).equalsIgnoreCase("<cost>")) {
               throw new IOException("<cost> expected");
            }
            cost = readNextValue(in);
            if (!readNextTag(in).equalsIgnoreCase("</cost>")) {
               throw new IOException("</cost> expected");
            }
            if (!readNextTag(in).equalsIgnoreCase("<nutrition>")) {
               throw new IOException("<nutrition> expected");
            }
            if (!readNextTag(in).equalsIgnoreCase("<carbohydrates>")) {
               throw new IOException("<carbohydrates> expected");
            }
            // carbohydrates =
            readNextValue(in);
            if (!readNextTag(in).equalsIgnoreCase("</carbohydrates>")) {
               throw new IOException("</carbohydrates> expected");
            }
            if (!readNextTag(in).equalsIgnoreCase("<energyContent>")) {
               throw new IOException("<energyContent> expected");
            }
            // energyContent =
            readNextValue(in);
            if (!readNextTag(in).equalsIgnoreCase("</energyContent>")) {
               throw new IOException("</energyContent> expected");
            }
            if (!readNextTag(in).equalsIgnoreCase("<fat>")) {
               throw new IOException("<fat> expected");
            }
            // fat =
            readNextValue(in);
            if (!readNextTag(in).equalsIgnoreCase("</fat>")) {
               throw new IOException("</fat> expected");
            }
            if (!readNextTag(in).equalsIgnoreCase("<protein>")) {
               throw new IOException("<protein> expected");
            }
            // protein =
            readNextValue(in);
            if (!readNextTag(in).equalsIgnoreCase("</protein>")) {
               throw new IOException("</protein> expected");
            }
            if (!readNextTag(in).equalsIgnoreCase("<cholesterol>")) {
               throw new IOException("<cholesterol> expected");
            }
            // cholesterol =
            readNextValue(in);
            if (!readNextTag(in).equalsIgnoreCase("</cholesterol>")) {
               throw new IOException("</cholesterol> expected");
            }
            if (!readNextTag(in).equalsIgnoreCase("<natrium>")) {
               throw new IOException("<natrium> expected");
            }
            // natrium =
            readNextValue(in);
            if (!readNextTag(in).equalsIgnoreCase("</natrium>")) {
               throw new IOException("</natrium> expected");
            }
            if (!readNextTag(in).equalsIgnoreCase("<calcium>")) {
               throw new IOException("<calcium> expected");
            }
            // calcium =
            readNextValue(in);
            if (!readNextTag(in).equalsIgnoreCase("</calcium>")) {
               throw new IOException("</calcium> expected");
            }
            if (!readNextTag(in).equalsIgnoreCase("</nutrition>")) {
               throw new IOException("</nutrition> expected");
            }
            if (!readNextTag(in).equalsIgnoreCase("<ingredientList>")) {
               throw new IOException("<ingredientList> expected");
            }
            ingredientList = readIngredients(in);
            if (!readNextTag(in).equalsIgnoreCase("</recipe>")) {
               throw new IOException("</recipe> expected");
            }
            Recipe recipe = new Recipe(name, categoryList, ingredientList,
                  Integer.parseInt(difficulty), Float.parseFloat(cost), Integer
                        .parseInt(time), "");
            this.add(recipe);
         }
      } catch (IOException e) {
         e.printStackTrace();
      }
   }

   /**
    * Removes a recipe from storage.
    * 
    * @param name
    *           Name of Recipe.
    */
   public void remove(String name) {
      recipeMap.remove(name.toLowerCase());
   }

   /**
    * Removes the "recipe #\n{" at the beginning of a recipe.
    * 
    * @param in
    * @throws IOException
    */
   private void removeLeadingRecipeMarking(BufferedReader in)
         throws IOException {
      while (in.read() != '{') {

      }
      in.read();
   }

   /**
    * Removes the two <?text?> tags at the beginning of a recipe.
    * 
    * @param in
    * @throws IOException
    */
   private void removeXMLTags(BufferedReader in) throws IOException {
      for (int questionMarks = 0; questionMarks < 4; questionMarks++) {
         while (in.read() != '?') {

         }
         in.read();
         in.read();
      }
   }

   /**
    * Writes the calcium tag from a recipe to file
    * 
    * @param recipe
    *           The recipe to save.
    * @param out
    *           The printwriter to print to.
    */
   private void saveCalcium(Recipe recipe, PrintWriter out) {
      out.print("<calcium>");
      out.print(recipe.getCalcium());
      out.println("</calcium>");
   }

   /**
    * Writes the carbon hydrates tag from a recipe to file
    * 
    * @param recipe
    *           The recipe to save.
    * @param out
    *           The printwriter to print to.
    */
   private void saveCarbonHydrates(Recipe recipe, PrintWriter out) {
      out.print("<carbohydrates>");
      out.print(recipe.getCarbohydrates());
      out.println("</carbohydrates>");
   }

   /**
    * Saves a recipe's categories to file.
    * 
    * @param recipe
    *           The recipe to get categories from
    * @param out
    *           PrintWriter connected to the output file
    */
   private void saveCategories(Recipe recipe, PrintWriter out) {
      ArrayList<String> categories = recipe.getCategories();
      out.println("<categoryList>");
      for (int index = 0; index < categories.size(); index++) {
         out.print("<category>");
         out.print(categories.get(index));
         out.println("</category>");
      }
      out.println("</categoryList>");
   }

   /**
    * Writes the cholestrol tag from a recipe to file
    * 
    * @param recipe
    *           The recipe to save.
    * @param out
    *           The printwriter to print to.
    */
   private void saveCholestrol(Recipe recipe, PrintWriter out) {
      out.print("<cholesterol>");
      out.print(recipe.getCholesterol());
      out.println("</cholesterol>");
   }

   /**
    * Writes the cost tag from a recipe to file
    * 
    * @param recipe
    *           The recipe to save.
    * @param out
    *           The printwriter to print to.
    */
   private void saveCost(Recipe recipe, PrintWriter out) {
      out.print("<cost>");
      out.print(recipe.getCost());
      out.println("</cost>");
   }

   /**
    * Writes the difficulty tag from a recipe to file
    * 
    * @param recipe
    *           The recipe to save.
    * @param out
    *           The printwriter to print to.
    */
   private void saveDifficulty(Recipe recipe, PrintWriter out) {
      out.print("<difficulty>");
      out.print(recipe.getDifficulty());
      out.println("</difficulty>");
   }

   /**
    * Writes the energy tag from a recipe to file
    * 
    * @param recipe
    *           The recipe to save.
    * @param out
    *           The printwriter to print to.
    */
   private void saveEnergyContent(Recipe recipe, PrintWriter out) {
      out.print("<energyContent>");
      out.print(recipe.getEnergy());
      out.println("</energyContent>");
   }

   /**
    * Writes the fat tag from a recipe to file
    * 
    * @param recipe
    *           The recipe to save.
    * @param out
    *           The printwriter to print to.
    */
   private void saveFat(Recipe recipe, PrintWriter out) {
      out.print("<fat>");
      out.print(recipe.getFat());
      out.println("</fat>");
   }

   /**
    * Saves a (generated) recipe to file. Intended for testing.
    * 
    * @param recipe
    *           The recipe to save.
    * @param out
    *           PrintWriter connected to the output file.
    */
   private void saveGeneratedRecipe(Recipe recipe, PrintWriter out) {
      out.println("<recipe>");
      out.print("<name>");
      out.print(recipe.getName());
      out.println("</name>");
      saveCategories(recipe, out);
      saveDifficulty(recipe, out);
      saveTime(recipe, out);
      saveCost(recipe, out);
      saveNutrition(recipe, out);
      saveIngredients(recipe, out);
      out.println("</recipe>");
   }

   /**
    * Saves a list of (generated) recipes to file. Intended for testing
    * 
    * @param recipes
    *           ArrayList of recipes to save.
    * @param fileName
    *           File name to save to (ending excluded).
    */
   public void saveGeneratedToFile(List<Recipe> recipes, String fileName) {
      try {
         System.out.println("Entering savegeneratedToFile.");

         PrintWriter out = new PrintWriter(new BufferedWriter(new FileWriter(
               "data/" + fileName + ".data"))); //$NON-NLS-2$
         out.println("<rl>");
         for (int index = 0; index < recipes.size(); index++) {
            saveGeneratedRecipe(recipes.get(index), out);
         }
         out.println("</rl>");
         out.close();
      } catch (IOException e) {
         e.printStackTrace();
      }
   }

   /**
    * Saves a recipe's ingredients to file.
    * 
    * @param recipe
    *           The recipe to get ingredients from
    * @param out
    *           PrintWriter connected to the output file
    */
   private void saveIngredients(Recipe recipe, PrintWriter out) {
      ArrayList<IngredientRequirement> ingredients = recipe.getIngredients();
      out.println("<ingredientList>");
      for (int index = 0; index < ingredients.size(); index++) {
         out.print("<ingredient>");
         out.print("<name>");
         out.print(ingredients.get(index).getName());
         out.print("</name>");
         out.print("<amount>");
         out.print(ingredients.get(index).getAmount().getQuantity());
         out.print("</amount>");
         out.print("<unit>");
         out.print(ingredients.get(index).getAmount().getUnit());
         out.print("</unit>");
         out.println("</ingredient>");
      }
      out.println("</ingredientList>");
   }

   /**
    * Writes the nutrition tag from a recipe to file
    * 
    * @param recipe
    *           The recipe to save.
    * @param out
    *           The printwriter to print to.
    */
   private void saveNutrition(Recipe recipe, PrintWriter out) {
      out.println("<nutrition>");
      saveCarbonHydrates(recipe, out);
      saveEnergyContent(recipe, out);
      saveFat(recipe, out);
      saveProtein(recipe, out);
      saveCholestrol(recipe, out);
      saveSodium(recipe, out);
      saveCalcium(recipe, out);
      out.println("</nutrition>");
   }

   /**
    * Writes the protein tag from a recipe to file
    * 
    * @param recipe
    *           The recipe to save.
    * @param out
    *           The printwriter to print to.
    */
   private void saveProtein(Recipe recipe, PrintWriter out) {
      out.print("<protein>");
      out.print(recipe.getProtein());
      out.println("</protein>");
   }

   /**
    * Writes the sodium tag from a recipe to file
    * 
    * @param recipe
    *           The recipe to save.
    * @param out
    *           The printwriter to print to.
    */
   private void saveSodium(Recipe recipe, PrintWriter out) {
      out.print("<natrium>");
      out.print(recipe.getSodium());
      out.println("</natrium>");
   }

   /**
    * Writes the time tag from a recipe to file
    * 
    * @param recipe
    *           The recipe to save.
    * @param out
    *           The printwriter to print to.
    */
   private void saveTime(Recipe recipe, PrintWriter out) {
      out.print("<time>");
      out.print(recipe.getTime());
      out.println("</time>");
   }

   /**
    * Sets a recipe in storage.
    * 
    * @param r
    *           Recipe to set.
    */
   public void set(Recipe r) {
      recipeMap.put(r.getName().toLowerCase(), r);
   }
}
