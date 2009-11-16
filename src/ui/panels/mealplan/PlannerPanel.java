package ui.panels.mealplan;

import java.awt.BorderLayout;

import javax.swing.JPanel;
import javax.swing.JSplitPane;

import model.MealSuggestionListModel;
import model.RecipeSelectionListener;

/**
 * The main panel for the planner part of the application.
 * @author jernlas
 *
 */
public class PlannerPanel extends JPanel {

   private static final long serialVersionUID = 1L;

   public PlannerPanel(MealSuggestionListModel serachModel,
         MealSuggestionListModel planModel) {

      setLayout(new BorderLayout());

      FilterSearchSortPanel filterPanel = new FilterSearchSortPanel(serachModel);
      PlanViewPanel planPanel = new PlanViewPanel(planModel);
      
      RecipeSelectionListener l = planPanel.getDetailsPanel();
      filterPanel.getRecipeList().registerNewRecipeListener(l);

      JSplitPane splitPane = new JSplitPane(JSplitPane.HORIZONTAL_SPLIT, true,
            filterPanel, planPanel);

      splitPane.setOneTouchExpandable(true);
      add(splitPane, BorderLayout.CENTER);
   }

}
