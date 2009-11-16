package ui.panels.mealplan;

import java.awt.BorderLayout;

import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JSplitPane;
import javax.swing.ScrollPaneConstants;

import model.MealSuggestionListModel;

/**
 * Panel for viewing the current plan, and a detailed view of the currently selected Recipe.
 * @author jernlas
 *
 */
public class PlanViewPanel extends JPanel {

   private static final long serialVersionUID = 1L;

   private final DetailedRecipeVisualizationPanel detailsPanel;
   
   public PlanViewPanel(MealSuggestionListModel listModel) {
      setLayout(new BorderLayout());
      MealOrganizerPanel lst = new MealOrganizerPanel();
      detailsPanel = new DetailedRecipeVisualizationPanel(lst);
      // add(new DetailedRecipeVisualizationPanel(lst), BorderLayout.NORTH);
      // add(new JScrollPane(lst), BorderLayout.CENTER);
      JSplitPane splitPane = new JSplitPane(JSplitPane.VERTICAL_SPLIT, true,
            detailsPanel , new JScrollPane(lst,
                  ScrollPaneConstants.VERTICAL_SCROLLBAR_NEVER,
                  ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER));
      splitPane.setOneTouchExpandable(true);
      add(splitPane);
   }

   /**
    * Gets the details panel
    * @return The panel
    */
   public DetailedRecipeVisualizationPanel getDetailsPanel() {
      return detailsPanel;
   }

}
