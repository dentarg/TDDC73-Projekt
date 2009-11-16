package ui.components;

import model.RecipeSelectionListener;
import dataObjects.Recipe;

/**
 * A component that can pick recipes.
 * @author jernlas
 *
 */
public interface RecipePicker {
   /**
    * Gets the picked recipe.
    * @return
    */
   public Recipe getSelectedRecipe();

   
   /**
    * Registers a new RecipeSelectionListener.
    * @param l The listener to register.
    */
   public void registerNewRecipeListener(RecipeSelectionListener l);
}
