package ui.panels.mealplan;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JComponent;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTabbedPane;
import javax.swing.SwingConstants;

import ui.components.IngredientTree;

/**
 * Panel containing filtering controls.
 * 
 * @author jernlas
 */
public class FilterMealSuggestionPanel extends JPanel {

   private static final String SKA_INNEHÅLLA      = "Ska innehålla:";
   private static final String SKA_INTE_INNEHÅLLA = "<html>Ska <i>inte</i> innehålla:<html>";
   private static final long   serialVersionUID   = 1L;

   /**
    * Creates a new filtering control panel
    */
   public FilterMealSuggestionPanel() {
      setLayout(new BorderLayout());
      JTabbedPane tabPane = new JTabbedPane(SwingConstants.TOP,
            JTabbedPane.SCROLL_TAB_LAYOUT);
      tabPane.add(createIngredientsPanel(), "Ingredienser");
      tabPane.add(createSpecialFoodPanel(), "Specialkost");
      tabPane.add(createAttributesPanel(), "Attribut");
      tabPane.add(createMealTypesPanel(), "Måltidstyp");
      add(tabPane, BorderLayout.CENTER);
      Dimension size = tabPane.getMinimumSize();
      if (size.width < 450) {
         size.width = 450;
      }
      setMinimumSize(size);

   }

   /**
    * Creates a panel with min-max attribute restrictions to filter by.
    * 
    * @return The created panel
    */
   private JComponent createAttributesPanel() {
      JPanel p = new JPanel();
      p.setLayout(new GridLayout(0, 3));
      p.add(new JLabel("Näringsvärde:"));
      p.add(new JLabel("Min:"));
      p.add(new JLabel("Max:"));

      List<MinMaxFields> fields = new ArrayList<MinMaxFields>();
      fields.add(new FatField("Fett:"));
      fields.add(new CarbohydratesField("Kolhydrater:"));
      fields.add(new EnergyField("Energi:"));
      fields.add(new ProteinField("Protein:"));
      fields.add(new CholestrolField("Kolesterol:"));
      fields.add(new SodiumField("Natrium:"));
      fields.add(new CalciumField("Kalcium:"));

      for (MinMaxFields field : fields) {
         p.add(field.title);
         p.add(field.min);
         p.add(field.max);
      }

      p.setPreferredSize(new Dimension(370, 200));
      JScrollPane sp = new JScrollPane(p);
      sp.setPreferredSize(new Dimension(400, 80));
      return sp;
   }

   /**
    * Create a panel with three ingredient trees to select what ingredients and
    * or categories should and should not be included in the suggested recipes.
    * 
    * @return The created panel.
    */
   private JPanel createIngredientsPanel() {
      JPanel whole = new JPanel();
      whole.setLayout(new BorderLayout());
      JPanel p = new JPanel();
      p.setLayout(new GridLayout(1, 3));
      p.add(new JLabel("Allt"));
      p.add(new JLabel(SKA_INNEHÅLLA));
      p.add(new JLabel(SKA_INTE_INNEHÅLLA));

      JPanel q = new JPanel();
      q.setLayout(new GridLayout(1, 3));
      IngredientTree allTree = new IngredientTree(true, true);
      IngredientTree mustHaveTree = new IngredientTree(false, false);
      IngredientTree mustNotHaveTree = new IngredientTree(false, true);

      q.add(allTree);
      q.add(mustHaveTree);
      q.add(mustNotHaveTree);

      whole.add(p, BorderLayout.NORTH);
      whole.add(q, BorderLayout.CENTER);
      return whole;
   }

   /**
    * Panel for setting meal types, not implemented.
    * @return The created panel
    */
   private JPanel createMealTypesPanel() {
      JPanel p = new JPanel();
      p.add(new JLabel("<html><h1>Inte implementerad</h1></html>"));
      return p;
   }

   /**
    * Panel for setting special food needs, not implemented.
    * @return The created panel
    */
   private JPanel createSpecialFoodPanel() {
      JPanel p = new JPanel();
      p.add(new JLabel("<html><h1>Inte implementerad</h1></html>"));
      return p;
   }

}
