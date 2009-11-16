package dataObjects;

import dataObjects.units.GramMeasurement;
import dataObjects.units.Measurement;

/**
 * Keeps track of nutritional data for one or more ingredients.
 *
 */
public class NutritionData {

   private Measurement calcium       = Measurement.createMeasurement("mg", 0f);
   private Measurement carbohydrates = Measurement.createMeasurement("g", 0f);
   private Measurement cholesterol   = Measurement.createMeasurement("mg", 0f);
   private Measurement energy        = Measurement
                                           .createMeasurement("kcal", 0f);
   private Measurement fat           = Measurement.createMeasurement("g", 0f);
   private Measurement protein       = Measurement.createMeasurement("g", 0f);
   private Measurement sodium        = Measurement.createMeasurement("mg", 0f);
   private Measurement weight        = new GramMeasurement();

   
   /**
    * Adds nutrition data objects together. Used to sum nutritional data for several ingredients.
    * @param n The nutritional data to add.
    * @param amount The amount of the ingredient.
    */
   public void add(NutritionData n, Measurement amount) {
      if (amount != null && this.weight.isWeightUnit() && amount.isWeightUnit()) {
         float ratio = amount.getGrams() / n.getAmount().getGrams();
         this.weight.setQuantity(this.weight.getQuantity()
               + n.getAmount().getQuantity() * ratio);
         energy = energy.add(n.energy.multiply(ratio));
         carbohydrates = carbohydrates.add(n.carbohydrates.multiply(ratio));
         fat = fat.add(n.fat.multiply(ratio));
         sodium = sodium.add(n.sodium.multiply(ratio));
         calcium = calcium.add(n.calcium.multiply(ratio));
         protein = protein.add(n.protein.multiply(ratio));
         cholesterol = cholesterol.add(n.cholesterol.multiply(ratio));
      }
   }

   /**
    * @return the weight
    */
   public Measurement getAmount() {
      return weight;
   }

   /**
    * @return the calcium
    */
   public Measurement getCalcium() {
      return calcium;
   }

   /**
    * @return the carbohydrates
    */
   public Measurement getCarbohydrates() {
      return carbohydrates;
   }

   /**
    * @return the cholesterol
    */
   public Measurement getCholesterol() {
      return cholesterol;
   }

   /**
    * @return the energy
    */
   public Measurement getEnergy() {
      return energy;
   }

   /**
    * @return the fat
    */
   public Measurement getFat() {
      return fat;
   }

   /**
    * @return the protein
    */
   public Measurement getProtein() {
      return protein;
   }

   /**
    * @return the sodium
    */
   public Measurement getSodium() {
      return sodium;
   }

   /**
    * @return the weight
    */
   public void setAmount(Measurement amount) {
      this.weight = amount;
   }

   /**
    * @param calcium
    *           the calcium to set
    */
   public void setCalcium(Measurement calcium) {
      this.calcium = calcium;
   }

   /**
    * @param carbohydrates
    *           the carbohydrates to set
    */
   public void setCarbohydrates(Measurement carbohydrates) {
      this.carbohydrates = carbohydrates;
   }

   /**
    * @param cholesterol
    *           the cholesterol to set
    */
   public void setCholesterol(Measurement cholesterol) {
      this.cholesterol = cholesterol;
   }

   /**
    * @param energy
    *           the energy to set
    */
   public void setEnergy(Measurement energy) {
      this.energy = energy;
   }

   /**
    * @param fat
    *           the fat to set
    */
   public void setFat(Measurement fat) {
      this.fat = fat;
   }

   /**
    * @param protein
    *           the protein to set
    */
   public void setProtein(Measurement protein) {
      this.protein = protein;
   }

   /**
    * @param sodium
    *           the sodium to set
    */
   public void setSodium(Measurement sodium) {
      this.sodium = sodium;
   }

}
