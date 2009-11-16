package model;

import java.util.EventListener;

import ui.components.RecipePicker;

/**
 * A listener for changes in a recipe selection.
 * @author jernlas
 *
 */
public interface RecipeSelectionListener extends EventListener {
   
   /**
    * The selected value has changed.
    * @param source The source of the change.
    */
   public void valueChanged(RecipePicker source);
}
