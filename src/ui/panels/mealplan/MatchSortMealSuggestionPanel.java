package ui.panels.mealplan;

import java.awt.BorderLayout;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;

import javax.swing.JLabel;
import javax.swing.JPanel;

import model.FilteringSearchField;
import model.MealSuggestionListModel;
import ui.components.SortMethodSelector;

/**
 * Panel for setting seort order and search string.
 * @author jernlas
 *
 */
public class MatchSortMealSuggestionPanel extends JPanel {

   private static final String SÖK               = "Sök:";
   private static final String SORTERINGSORDNING = "Sorteringsordning:";
   private static final long   serialVersionUID  = 1L;

   MatchSortMealSuggestionPanel(MealSuggestionListModel model) {
      JPanel searchPanel = createSortSearchPanel(model);
      setLayout(new BorderLayout());

      add(searchPanel, BorderLayout.CENTER);
   }

   private JPanel createSortSearchPanel(MealSuggestionListModel model) {
      JPanel p = new JPanel();
      p.setLayout(new GridBagLayout());
      GridBagConstraints c = new GridBagConstraints();

      c.fill = GridBagConstraints.HORIZONTAL;
      c.weightx = 0.5;
      c.gridx = 0;
      c.gridy = 0;
      c.insets = new Insets(5, 5, 1, 5);
      p.add(new JLabel(SORTERINGSORDNING), c);

      c.fill = GridBagConstraints.HORIZONTAL;
      c.weightx = 0.5;
      c.gridx = 0;
      c.gridy = 1;
      c.insets = new Insets(1, 5, 2, 5);
      p.add(new SortMethodSelector(model), c);

      c.fill = GridBagConstraints.HORIZONTAL;
      c.weightx = 0.5;
      c.gridx = 0;
      c.gridy = 2;
      c.insets = new Insets(2, 5, 1, 5);
      p.add(new JLabel(SÖK), c);

      c.fill = GridBagConstraints.HORIZONTAL;
      c.weightx = 0.5;
      c.gridx = 0;
      c.gridy = 3;
      c.insets = new Insets(1, 5, 5, 5);
      p.add(new FilteringSearchField(model), c);

      return p;
   }


}
