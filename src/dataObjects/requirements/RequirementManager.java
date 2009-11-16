package dataObjects.requirements;

import java.util.ArrayList;
import java.util.List;

import model.MealSuggestionListModel;
import model.SearchStringMealSuggestionFilter;
import model.sorters.ConstraintSorter;
import constraints.CalciumConstraint;
import constraints.CarbonHydratesConstraint;
import constraints.CholestrolConstraint;
import constraints.EnergyConstraint;
import constraints.FatConstraint;
import constraints.IngredientConstraint;
import constraints.ProteinConstraint;
import constraints.SodiumConstraint;
import constraints.SingleRecipeConstraint;

/**
 * Keeps track of requirements for the new GUI
 */
public class RequirementManager {

   private static CalciumConstraint        calciumConstraint    = null;
   private static SodiumConstraint         sodiumConstraint     = null;
   private static EnergyConstraint         energyConstraint     = null;
   private static CarbonHydratesConstraint carbConstraint       = null;
   private static FatConstraint            fatConstraint        = null;
   private static CholestrolConstraint     cholestrolConstraint = null;
   private static ProteinConstraint        proteinConstraint    = null;
   private static MealSuggestionListModel  model                = null;

   private static IngredientConstraint     includeIngredients   = null;

   private static IngredientConstraint     excludeIngredients   = null;

   /**
    * Gets a list of all constraints.
    * 
    * @return The list of constraints
    */
   public static List<SingleRecipeConstraint> getAllConstraints() {

      ArrayList<SingleRecipeConstraint> c = new ArrayList<SingleRecipeConstraint>();
      c.add(includeIngredients);
      c.add(excludeIngredients);

      c.add(calciumConstraint);
      c.add(sodiumConstraint);
      c.add(energyConstraint);
      c.add(carbConstraint);
      c.add(fatConstraint);
      c.add(cholestrolConstraint);
      c.add(proteinConstraint);

      return c;
   }

   /**
    * Gets the calcium constraint
    * 
    * @return the constraint
    */
   public static synchronized CalciumConstraint getCalciumRequirement() {
      return calciumConstraint;
   }

   /**
    * Gets the carbon hydrate constraint
    * 
    * @return the constraint
    */
   public static synchronized CarbonHydratesConstraint getCarbRequirement() {
      return carbConstraint;
   }

   /**
    * Gets the cholesterol constraint
    * @return the constraint
    */
   public static synchronized CholestrolConstraint getCholestrolRequirement() {
      return cholestrolConstraint;
   }

   /**
    * Gets the energy constraint
    * @return the constraint
    */
   public static synchronized EnergyConstraint getEnergyRequirement() {
      return energyConstraint;
   }

   /**
    * Gets the exclude ingredients constraint
    * @return the constraint
    */
   public static synchronized IngredientConstraint getExcludeIngredients() {
      return excludeIngredients;
   }

   /**
    * Gets the fat constraint
    * @return the constraint
    */
   public static synchronized FatConstraint getFatRequirement() {
      return fatConstraint;
   }
   
   /**
    * Gets the include ingredeints constraint
    * @return the constraint
    */
   public static synchronized IngredientConstraint getIncludeIngredients() {
      return includeIngredients;
   }

   /**
    * Gets the list model 
    * @return the constraint
    */
   public static MealSuggestionListModel getModel() {
      return model;
   }

   /**
    * Gets the protein constraint
    * @return the constraint
    */
   public static synchronized ProteinConstraint getProteinRequirement() {
      return proteinConstraint;
   }

   /**
    * Gets the sodium constraint
    * @return the constraint
    */
   public static synchronized SodiumConstraint getSodiumRequirement() {
      return sodiumConstraint;
   }

   /**
    * Sets the calcium constraint
    * @param calciumRequirement the constraint
    */
   public static synchronized void setCalciumRequirement(
         CalciumConstraint calciumRequirement) {
      calciumConstraint = calciumRequirement;
      update();
   }

   /**
    * Sets the carbon hydrates constraint
    * @param carbRequirement the constraint
    */
   public static synchronized void setCarbRequirement(
         CarbonHydratesConstraint carbRequirement) {
      carbConstraint = carbRequirement;
      update();
   }

   /**
    * Sets the cholesterol constraint
    * @param cholestrolRequirement the constraint
    */
   public static synchronized void setCholestrolRequirement(
         CholestrolConstraint cholestrolRequirement) {
      cholestrolConstraint = cholestrolRequirement;
      update();
   }

   /**
    * Sets the energy constraint
    * @param energyRequirement the constraint
    */
   public static synchronized void setEnergyRequirement(
         EnergyConstraint energyRequirement) {
      energyConstraint = energyRequirement;
      update();
   }

   
   /**
    * Sets the excluded ingredients list constraint
    * @param ingredients the constraint
    */
   public static synchronized void setExcludeIngredients(
         IngredientConstraint ingredients) {
      excludeIngredients = ingredients;
      update();
   }

   /**
    * Sets the fat constraint
    * @param fatRequirement the constraint
    */
   public static synchronized void setFatRequirement(
         FatConstraint fatRequirement) {
      fatConstraint = fatRequirement;
      update();
   }

   /**
    * Sets the included ingredients list constraint
    * @param ingredients the constraint
    */
   public static synchronized void setIncludeIngredients(
         IngredientConstraint ingredients) {
      includeIngredients = ingredients;
      update();
   }

   
   /**
    * Sets the list model
    * @param model the model
    */
   public static void setModel(MealSuggestionListModel model) {
      RequirementManager.model = model;
   }

   /**
    * Sets the protein constraint
    * @param proteinRequirement the constraint
    */
   public static synchronized void setProteinRequirement(
         ProteinConstraint proteinRequirement) {
      proteinConstraint = proteinRequirement;
      update();
   }

   /**
    * Sets the sodium constraint
    * @param sodiumRequirement the constraint
    */
   public static synchronized void setSodiumRequirement(
         SodiumConstraint sodiumRequirement) {
      sodiumConstraint = sodiumRequirement;
      update();
   }

   /**
    * Updates the manager and the model.
    */
   private static void update() {
      SearchStringMealSuggestionFilter f = model.copyRestrictingLastFilter();
      if (f.getSorter() instanceof ConstraintSorter) {
         model.applyFilter(f);
      }
   }

}
