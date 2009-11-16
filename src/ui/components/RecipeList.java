package ui.components;

import java.util.ArrayList;

import javax.swing.DropMode;
import javax.swing.JList;
import javax.swing.ListSelectionModel;
import javax.swing.event.EventListenerList;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;

import model.MealSuggestionListModel;
import model.RecipeSelectionListener;
import dataObjects.Recipe;
import dataObjects.requirements.IngredientRequirement;

/**
 * List component for displaying recipes.
 * @author jernlas
 *
 */
public class RecipeList extends JList implements RecipePicker, ListSelectionListener {

   private static final long serialVersionUID = 1L;
   
   /**
    * The recipe picker listeners
    */
   private EventListenerList      listeners        = new EventListenerList();

   
   /**
    * Create a new Recipe list.
    * @param listModel The list model to use
    */
   public RecipeList(MealSuggestionListModel listModel) {
      super(listModel);
      setCellRenderer(new MealSuggestionRenderer());
      setPrototypeCellValue(new Recipe("New Great recipe, weeeeee",
            new ArrayList<String>(), new ArrayList<IngredientRequirement>(), 0,
            0f, 0, ""));
      setDragEnabled(true);
      setDropMode(DropMode.ON_OR_INSERT);
      setTransferHandler(listModel);
      setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
      getSelectionModel().addListSelectionListener(this);
   }

   /*
    * (non-Javadoc)
    * @see javax.swing.event.ListSelectionListener#valueChanged(javax.swing.event.ListSelectionEvent)
    */
   @Override
   public void valueChanged(ListSelectionEvent e) {
      RecipeSelectionListener[] tmp = listeners.getListeners(RecipeSelectionListener.class);
      for( int idx = tmp.length-1; idx >= 0; idx--){
         tmp[idx].valueChanged(this);
      }
      
   }
   
   /*
    * (non-Javadoc)
    * @see ui.components.RecipePicker#getSelectedRecipe()
    */
   @Override
   public Recipe getSelectedRecipe() {
      return (Recipe) this.getSelectedValue();
   }

   /*
    * (non-Javadoc)
    * @see ui.components.RecipePicker#registerNewRecipeListener(model.RecipeSelectionListener)
    */
   @Override
   public void registerNewRecipeListener(RecipeSelectionListener l) {
      listeners.add(RecipeSelectionListener.class, l);
   }

}
