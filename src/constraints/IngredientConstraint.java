package constraints;

import java.util.List;

import dataObjects.Recipe;
import dataObjects.requirements.IngredientRequirement;

/**
 * A constraint on what ingredients should or should not be included in a
 * certain recipe.
 */
public class IngredientConstraint implements SingleRecipeConstraint {

   /**
    * Is this a list of ingredients that should or should not be included in the
    * recipe.
    */
   private boolean      inclusive;

   /**
    * The list of ingredients the constraint cares about.
    */
   private List<String> ingredients;

   /**
    * The weight of the constraint. 1 implies hard, 1+ implies soft.
    */
   private int          weight;

   /**
    * Creates a new constraint on recipe ingredients. Weight is set to 1 (hard).
    * 
    * @param ingredients
    *           What ingredients are affected
    * @param inclusive
    *           Should these ingredients be included in the recipe (true) or
    *           excluded (false).
    */
   public IngredientConstraint(final List<String> ingredients,
         final boolean inclusive) {
      this.ingredients = ingredients;
      this.inclusive = inclusive;
      this.weight = 1;
   }

   /**
    * Creates a new constraint on recipe ingredients.
    * 
    * @param ingredients
    *           What ingredients are affected
    * @param inclusive
    *           Should these ingredients be included in the recipe (true) or
    *           excluded (false).
    * @param weight
    *           The weight of the constraint. 1 implies hard, 1+ soft.
    */
   public IngredientConstraint(List<String> ingredients, boolean inclusive,
         int weight) {
      this.ingredients = ingredients;
      this.inclusive = inclusive;
      this.weight = weight;
   }

   /*
    * (non-Javadoc)
    * 
    * @see constraints.SingleRecipeConstraint#getWeight()
    */
   public int getWeight() {
      return weight;
   }

   /*
    * (non-Javadoc)
    * 
    * @see constraints.SingleRecipeConstraint#valuation(dataObjects.Recipe)
    */
   public float valuation(final Recipe matchedRecipe) {
      List<IngredientRequirement> ingredients = matchedRecipe.getIngredients();
      int penalty = 0;

      if (inclusive) {
         for (int index2 = 0; index2 < ingredients.size(); index2++) {
            if (this.ingredients.contains((ingredients.get(index2)).getName()
                  .toLowerCase())) {
            } else {
               penalty++;
            }
         }
      } else {
         for (int index2 = 0; index2 < ingredients.size(); index2++) {
            if (this.ingredients.contains((ingredients.get(index2)).getName()
                  .toLowerCase())) {
               penalty++;
            }
         }
      }

      return penalty / (ingredients.size() + 0.0001f);
   }
}
