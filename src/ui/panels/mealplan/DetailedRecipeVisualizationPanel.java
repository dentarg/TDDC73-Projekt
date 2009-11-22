package ui.panels.mealplan;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.util.Map;
import java.util.Vector;

import javax.swing.JComponent;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTabbedPane;
import javax.swing.JTextArea;
import javax.swing.ScrollPaneConstants;
import javax.swing.SwingConstants;

import model.RecipeSelectionListener;
import ui.components.RecipePicker;
import ui.components.StarGrader;
import dataAccess.SubjectDA;
import dataObjects.Preference;
import dataObjects.Recipe;
import dataObjects.Subject;
import dataObjects.requirements.IngredientRequirement;

/**
 * Panel used for displaying detailed information about a Recipe.
 * 
 * @author jernlas
 */
public class DetailedRecipeVisualizationPanel extends JPanel implements
      RecipeSelectionListener {

   private static final long serialVersionUID    = 1L;

   /**
    * The list of ingredients
    */
   private JList             ingredients         = new JList();

   /**
    * Name of the Recipe
    */
   private JLabel            nameLabel           = new JLabel();

   /**
    * Image of the recipe
    */
   private JLabel            imageLabel          = new JLabel();

   /**
    * The cooking intructions.
    */
   private JTextArea         cookingInstructions = new JTextArea();

   /**
    * The nutrition information
    */
   private JTextArea         nutritionInfo       = new JTextArea();

   /**
    * The cost
    */
   private JLabel            costLabel           = new JLabel();

   /**
    * The cooking time
    */
   private JLabel            timeLabel           = new JLabel();

   /**
    * The difficulty
    */
   private JLabel            difficultyLabel     = new JLabel();

   /**
    * The grade
    */
   private StarGrader        gradeSlider         = new StarGrader();

   /**
    * Create a new Recipe visualization panel connected to the recipe picker
    * list.
    * 
    * @param list
    *           The RecipePicker to listen to.
    */
   public DetailedRecipeVisualizationPanel(RecipePicker list) {
      setLayout(new BorderLayout());
      add(createRecipeNorthPanel(), BorderLayout.NORTH);
      add(createRecipeIngredientPanel(), BorderLayout.CENTER);
      list.registerNewRecipeListener(this);
   }

   /**
    * Creates the ingredient panel
    * @return The panel
    */
   private JComponent createRecipeIngredientPanel() {
      JTabbedPane p = new JTabbedPane(SwingConstants.TOP,
            JTabbedPane.SCROLL_TAB_LAYOUT);

      cookingInstructions.setEditable(false);
      nutritionInfo.setEditable(false);

      p.add("Ingredienter", new JScrollPane(ingredients));
      p.add("Tillagning", new JScrollPane(cookingInstructions,
            ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS,
            ScrollPaneConstants.HORIZONTAL_SCROLLBAR_AS_NEEDED));
      p.add("N√§ringsv√§rden", new JScrollPane(nutritionInfo,
            ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS,
            ScrollPaneConstants.HORIZONTAL_SCROLLBAR_AS_NEEDED));

      p.setMinimumSize(new Dimension(300, 100));

      return p;
   }

   /**
    * Create main panel
    * @return The panel
    */
   private JComponent createRecipeMainPanel() {
      JPanel p = new JPanel();
      p.setLayout(new BorderLayout());

      p.add(imageLabel, BorderLayout.WEST);
      p.add(nameLabel, BorderLayout.CENTER);
      p.add(gradeSlider, BorderLayout.SOUTH);

      return p;
   }

   /**
    * Creates the miscellaneous information panel
    * @return The panel
    */
   private JComponent createRecipeMiscPanel() {
      JPanel p = new JPanel();
      p.setLayout(new GridLayout(0, 2, 5, 5));

      p.add(new JLabel("Kostnad:"));
      p.add(costLabel);

      p.add(new JLabel("Tillagningstid:"));
      p.add(timeLabel);

      p.add(new JLabel("Sv√•righetsgrad:"));
      p.add(difficultyLabel);

      return p;
   }

   
   /**
    * Creates the North panel (contains the main and misc panels)
    * @return The created panel.
    */
   private JComponent createRecipeNorthPanel() {
      JPanel p = new JPanel();
      p.setLayout(new BorderLayout());
      p.add(createRecipeMainPanel(), BorderLayout.CENTER);
      p.add(createRecipeMiscPanel(), BorderLayout.EAST);
      return p;
   }

   /**
    * Sets the displayed recipe.
    * @param r The recipe to display.
    */
   private void setDisplayedRecipe(Recipe r) {
      if (r != null) {
         imageLabel.setIcon(r.getImg());
         nameLabel.setText(r.getName());
         SubjectDA s = new SubjectDA();
         Subject subject = s.getAllSubjects().get(3);
         Map<String, Preference> m = subject.getPreferences();
         Preference p = m.get(r.getName());
         int g = (p == null ? 0 : p.getRating());
         gradeSlider.setGrade(g);

         Vector<String> ingredientStrings = new Vector<String>();

         for (IngredientRequirement ing : r.getIngredients()) {
            ingredientStrings.add(ing.toString());
         }

         ingredients.setListData(ingredientStrings);

         StringBuilder nutr = new StringBuilder();
         nutr.append("Energy:\t").append(r.getEnergy()).append("\n");
         nutr.append("Kolhydrater:\t").append(r.getCarbohydrates())
               .append("\n");
         nutr.append("Fat:\t").append(r.getFat()).append("\n");
         nutr.append("Protein:\t").append(r.getProtein()).append("\n");
         nutr.append("Cholesterol:\t").append(r.getCholesterol()).append("\n");
         nutr.append("Kalcium:\t").append(r.getCalcium()).append("\n");
         nutr.append("Kalium:\t").append(r.getSodium()).append("\n");
         nutritionInfo.setText(nutr.toString());

         cookingInstructions.setText(r.getDescription());

         costLabel.setText(String.format("%.2f", r.getCost()));
         timeLabel.setText(String.format("%d", r.getTime()));
         difficultyLabel.setText(String.format("%d", r.getDifficulty()));
      }
   }

   /*
    * (non-Javadoc)
    * @see model.RecipeSelectionListener#valueChanged(ui.components.RecipePicker)
    */
   public void valueChanged(RecipePicker e) {
      setDisplayedRecipe(e.getSelectedRecipe());
   }

}
