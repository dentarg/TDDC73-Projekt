/*
 * Created using IntelliJ IDEA. User: x04nicsu Date: 2005-feb-24 Time: 14:13:57
 */
package dataObjects;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Scanner;

import data.Settings;
import dataObjects.units.Measurement;

/**
 * Class representing an available ingredient. 
 */
public class IngredientAvailability {

   public static ArrayList<IngredientAvailability> getIngredientMap() {
      ArrayList<IngredientAvailability> ingredients = new ArrayList<IngredientAvailability>();
      InputStreamReader ir = new InputStreamReader(ClassLoader
            .getSystemResourceAsStream(Settings
                  .getFilePath("IngredientAvailability.IngredientListFile")));
      BufferedReader ingrFile = new BufferedReader(ir);
      Scanner scanner = null;

      scanner = new Scanner(ingrFile);
      int nr = 0;
      String ingrcat[];
      while (scanner.hasNextLine()) {
         String ingrName = scanner.nextLine();
         ingrcat = ingrName.split(" "); 
         ingrName = ingrcat[0]; 
         for (int index = 1; index < ingrcat.length - 1; ++index) {
            ingrName += " " + ingrcat[index]; 
         } 
         float amount = new Float(1);
         MealDate expDate = new MealDate("2005-12-12"); 
         IngredientAvailability ingrAvailability = new IngredientAvailability(
               ingrName, amount, expDate);
         ingredients.add(ingrAvailability);
         nr++;
      }

      return ingredients;
   }

   private Measurement amount;
   private String      expirationDate;

   private String      name;

   /**
    * Create an available ingredient, with amount and expiration date.
    * 
    * @param name
    *           Name of ingredient.
    * @param amount
    *           Amount available.
    * @param expirationDate
    *           Expiration date of ingredient.
    */
   public IngredientAvailability(String name, float amount,
         MealDate expirationDate) {
      this.name = name;
      this.amount = Measurement.createMeasurement("BogusUnit", amount);
      this.expirationDate = expirationDate.toString();
   }

   /**
    * Create an available ingredient, with amount and expiration date.
    * 
    * @param name
    *           Name of ingredient.
    * @param amount
    *           Amount available.
    * @param unit
    *           Unit of amount.
    * @param expirationDate
    *           Expiration date of ingredient.
    */
   public IngredientAvailability(String name, Measurement amount,
         MealDate expirationDate) {
      this.name = name;
      this.amount = amount;
      this.expirationDate = expirationDate.toString();
   }

   /**
    * Get amount available.
    * 
    * @return Amount available, float.
    */
   public Measurement getAmount() {
      return amount;
   }

   /**
    * Gets expiration date.
    * 
    * @return Expiration date, java.sql.Date
    */
   public String getExpirationDate() {
      return expirationDate;
   }

   /**
    * Get name of ingredient.
    * 
    * @return Name of ingredient, String.
    */
   public String getName() {
      return name;
   }

   /**
    * Sets amount available.
    * 
    * @param amount
    *           Amount to set, float.
    */
   public void setAmount(Measurement amount) {
      this.amount = amount;
   }

   @Override
   public String toString() {
      return getName();
   }

}
