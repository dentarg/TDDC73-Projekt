package dataStorage;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStreamReader;
import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;
import java.text.ParseException;
import java.util.HashMap;
import java.util.Locale;
import java.util.StringTokenizer;

import data.Settings;
import dataObjects.NutritionData;
import dataObjects.units.GramMeasurement;
import dataObjects.units.Measurement;
import dataObjects.units.MilliGramMeasurement;

public class IngredientNutritionDataDS {
   @SuppressWarnings("unused")
   private static final DecimalFormat           fmt = new DecimalFormat(
                                                          "",
                                                          new DecimalFormatSymbols(
                                                                new Locale("se")));
   private final HashMap<String, NutritionData> nutritionData;

   /**
    * Create a new information object.
    */
   public IngredientNutritionDataDS() {
      nutritionData = new HashMap<String, NutritionData>();
      readFromFile();
   }

   /**
    * Parses and adds data for one ingredeint.
    * @param s The string of data for the ingredeint.
    */
   private void addNutritionInfo(String s) {
      String name;
      NutritionData data = new NutritionData();
      StringTokenizer t = new StringTokenizer(s, "\t");
      DecimalFormat fmt = new DecimalFormat("", new DecimalFormatSymbols(
            new Locale("se")));

      try {
         name = t.nextToken().toLowerCase(); // Livsmedel

         data.setAmount(new GramMeasurement(fmt.parse(t.nextToken())
               .floatValue()));// Vikt (g)
         t.nextToken();// Fibrer (g)
         t.nextToken();// Energi kJ (kJ)
         data.setEnergy(Measurement.createMeasurement("kcal", fmt.parse(
               t.nextToken()).floatValue()));// Energi kcal
         // (kcal)
         data.setProtein(new GramMeasurement(fmt.parse(t.nextToken())
               .floatValue()));// Protein (g)
         data.setCarbohydrates(new GramMeasurement(fmt.parse(t.nextToken())
               .floatValue()));// Kolhydrat
         // (g)
         t.nextToken();// Alkohol (g)
         data
               .setFat(new GramMeasurement(fmt.parse(t.nextToken())
                     .floatValue()));// Fett (g)
         t.nextToken();// M�ttade fettsyror (g)
         t.nextToken();// Enkelom�ttade fettsyror (g)
         t.nextToken();// Flerom�ttade fettsyror (g)
         t.nextToken();// Fettsyra C4-C10 (g)
         t.nextToken();// Fettsyra C12 (g)
         t.nextToken();// Fettsyra C14 (g)
         t.nextToken();// Fettsyra C16 (g)
         t.nextToken();// Fettsyra C18 (g)
         t.nextToken();// Fettsyra C20 (g)
         t.nextToken();// Fettsyra C16:1 (g)
         t.nextToken();// Fettsyra C18:1 (g)
         t.nextToken();// Fettsyra C18:2 (g)
         t.nextToken();// Fettsyra C18:3 (g)
         t.nextToken();// Fettsyra C20:4 (g)
         t.nextToken();// Fettsyra C20:5 (g)
         t.nextToken();// Fettsyra C22:5 (g)
         t.nextToken();// Fettsyra C22:6 (g)
         t.nextToken();// Monosackarider (g)
         t.nextToken();// Disackarider (g)
         t.nextToken();// Sackaros (g)
         t.nextToken();// Retinolekvivalent (�g)
         t.nextToken();// Retinol (�g)
         t.nextToken();// D vitamin (�g)
         t.nextToken();// E vitamin (mg)
         t.nextToken();// ?-Tokoferol (mg)
         t.nextToken();// Karoten (�g)
         t.nextToken();// Tiamin (mg)
         t.nextToken();// Riboflavin (mg)
         t.nextToken();// Askorbinsyra (mg)
         t.nextToken();// Niacinekvivalent (mg)
         t.nextToken();// Niacin (mg)
         t.nextToken();// Vitamin B6 (mg)
         t.nextToken();// Vitamin B12 (�g)
         t.nextToken();// Folat (�g)
         t.nextToken();// Fosfor (mg)
         t.nextToken();// J�rn (mg)
         t.nextToken();// Kalium (mg)
         data.setCalcium(new MilliGramMeasurement(fmt.parse(t.nextToken())
               .floatValue()));// Kalcium (mg)
         t.nextToken();// Magnesium (mg)
         data.setSodium(new MilliGramMeasurement(fmt.parse(t.nextToken())
               .floatValue()));// Natrium (mg)
         t.nextToken();// Selen (�g)
         t.nextToken();// Zink (mg)
         data.setCholesterol(new MilliGramMeasurement(fmt.parse(t.nextToken())
               .floatValue()));// Kolesterol
         // (mg)
         t.nextToken();// Aska (g)
         t.nextToken();// Vatten (g)
         t.nextToken();// Avfall (%)

         nutritionData.put(name, data);
      } catch (ParseException e) {
         e.printStackTrace();
      }

   }

   /**
    * Gets nutrition data for an ingredient.
    * @param ingredient The ingredient to get data for,
    * @return The nutrition data.
    */
   public NutritionData get(String ingredient) {
      return nutritionData.get(ingredient.toLowerCase());
   }

   /**
    * Reads Ingredient info from Livsmedelsverket data-file.
    */
   public void readFromFile() {
      String s = null;
      nutritionData.clear();
      try {
         InputStreamReader ir = new InputStreamReader(ClassLoader
               .getSystemResourceAsStream(Settings
                     .getFilePath("NutritionData.DataFile")));
         BufferedReader in = new BufferedReader(ir);
         if (in.readLine() != null) {
            while ((s = in.readLine()) != null) {
               addNutritionInfo(s);
            }
         }
         in.close();
      } catch (FileNotFoundException e) {
         e.printStackTrace();
      } catch (IOException e) {
         e.printStackTrace();
      }
   }

}
