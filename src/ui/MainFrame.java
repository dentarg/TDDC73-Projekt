package ui;

import java.awt.BorderLayout;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.Toolkit;
import java.util.ArrayList;

import javax.swing.JComponent;
import javax.swing.JFrame;
import javax.swing.JTabbedPane;
import javax.swing.SwingUtilities;

import model.MealSuggestionListModel;
import ui.panels.forum.ForumPanel;
import ui.panels.mealplan.PlannerPanel;
import ui.panels.profile.ProfilePanel;
import dataAccess.RecipeDA;
import dataObjects.Recipe;

/**
 * The main frame of the application.
 * 
 * @author jernlas
 */
public class MainFrame extends JFrame {

   private static final long serialVersionUID = 1L;

   /**
    * @param args
    *           all arguments are ignored
    */
   public static void main(String[] args) {
      SwingUtilities.invokeLater(new Runnable() {
         public void run() {
            new MainFrame();
         }
      });

   }

   /**
    * The list model for tsearch results
    */
   private final MealSuggestionListModel searchMealModel = new MealSuggestionListModel();

   /**
    * The list model for the plan.
    */
   private final MealSuggestionListModel planMealModel   = new MealSuggestionListModel();

   /**
    * Creates a new interface.
    */
   public MainFrame() {
      super("Concept UI for mealplanner");
      setDefaultCloseOperation(DISPOSE_ON_CLOSE);
      createComponents();
   }

   /**
    * Creates the main part of the GUI, the tabbed pane that contains the
    * different parts of the application.
    */
   private void createComponents() {

      setLayout(new BorderLayout());
      setMinimumSize(new Dimension(800, 600));

      Container contentPane = getContentPane();
      JTabbedPane tabbedPane = new JTabbedPane();
      tabbedPane.setMaximumSize(Toolkit.getDefaultToolkit().getScreenSize());
      // tabbedPane.setPreferredSize(new Dimension(790, 570));
      tabbedPane.add("Måltidsplan", createMealPlanPanel());
      tabbedPane.add("Min profil", new ProfilePanel());
      tabbedPane.add("Forum", new ForumPanel());
      contentPane.add(tabbedPane, BorderLayout.CENTER);
      pack();

      setVisible(true);
   }

   /**
    * Create the panel for the Meal plan tab.
    * @return The resulting panel.
    */
   private JComponent createMealPlanPanel() {
      createFood();
      return new PlannerPanel(searchMealModel, planMealModel);
   }

   /**
    * Sets the recipes that are available for the search model.
    */
   private void createFood() {
      RecipeDA recDA = new RecipeDA();
      ArrayList<Recipe> recipes = recDA.getAllRecipes();
      for (Recipe recipe : recipes) {
         searchMealModel.addRecipe(recipe);
      }
   }

}
