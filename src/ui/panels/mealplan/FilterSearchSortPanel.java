package ui.panels.mealplan;

import java.awt.BorderLayout;

import javax.swing.JComponent;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JSplitPane;

import model.MealSuggestionListModel;
import ui.components.RecipeList;

/**
 * Panel for controlling the Left half of the mealplan.
 * @author jernlas
 *
 */
public class FilterSearchSortPanel extends JPanel {

   private final RecipeList recipeList;
   
   public RecipeList getRecipeList() {
      return recipeList;
   }

   private static final long serialVersionUID = 1L;

   /**
    * Creates the whole left half.
    * @param model The model to use for searching and displaying.
    */
   public FilterSearchSortPanel(MealSuggestionListModel model) {
      setLayout(new BorderLayout());
      recipeList = new RecipeList(model);
      add(new JSplitPane(JSplitPane.VERTICAL_SPLIT, createPanel(model),
            new JScrollPane(recipeList)), BorderLayout.CENTER);
   }

   /**
    * Creates the upper half of above, the search / filter parts.
    * @param model The model to use
    * @return The created panel
    */
   private JComponent createPanel(MealSuggestionListModel model) {
      JPanel panel = new JPanel();
      JPanel sortPanel = new MatchSortMealSuggestionPanel(model);
      JPanel filterPanel = new FilterMealSuggestionPanel();
      panel.setLayout(new BorderLayout());
      panel.add(sortPanel, BorderLayout.NORTH);
      panel.add(filterPanel, BorderLayout.CENTER);
      return panel;
   }

}
