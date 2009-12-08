package ui;

import java.awt.BorderLayout;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.util.ArrayList;

import javax.swing.JComponent;
import javax.swing.JFrame;
import javax.swing.JTabbedPane;
import javax.swing.SwingUtilities;
import javax.swing.UIManager;
import model.MealSuggestionListModel;
import ui.components.StatusPanel;
import ui.panels.forum.ForumPanel;
import ui.panels.mealplan.PlannerPanel;
import ui.panels.profile.ProfilePanel;
import ui.panels.profile.loggedInUserLabel;
import dataAccess.RecipeDA;
import dataObjects.Recipe;
import dataObjects.Session;
import dataObjects.Subject;
import java.awt.event.ActionListener;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.event.ChangeListener;

/**
 * The main frame of the application.
 * 
 * @author jernlas
 */
public class MainFrame extends JFrame {

    public static MainFrame mainFrame;
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
    private final MealSuggestionListModel planMealModel = new MealSuggestionListModel();

    /**
     * Creates a new interface.
     */
    public MainFrame() {
        super("Concept UI for mealplanner");
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        createLoginFrame();
        //Session.getInstance().setUser(new Subject("blah")); createComponents();
    }

    /**
     * Creates a login window
     */
    public void createLoginFrame() {
        final JFrame loginFrame = new JFrame("Logga in");
        final JTextField nameField = new JTextField();
        final JButton loginButton = new JButton("Logga in");
        final JLabel loginLabel = new JLabel("Användarnamn:");
        final JLabel statusLabel = new JLabel("Loggar in...");
        final JPanel container = (JPanel) loginFrame.getContentPane();
        container.setLayout(new BorderLayout(5, 5));
        loginButton.addActionListener(new ActionListener() {

            public void actionPerformed(ActionEvent ae) {
                if (nameField.getText().compareTo("") != 0) {
                    Session session = Session.getInstance();
                    session.setUser(new Subject(nameField.getText()));
                    
                    createComponents();
                    loginFrame.dispose();
                } else {
                    JOptionPane.showMessageDialog(loginFrame, "Ej giltigt inlog!");
                }

            }
        });
        container.add(loginButton, BorderLayout.SOUTH);

        container.add(loginLabel, BorderLayout.NORTH);
        nameField.setPreferredSize(
                new Dimension(100, 20));
        nameField.addActionListener(
                new ActionListener() {

                    public void actionPerformed(ActionEvent ae) {
                        if (nameField.getText().compareTo("") != 0) {
                            Session session = Session.getInstance();
                            session.setUser(new Subject(nameField.getText()));
                            
                            createComponents();
                            loginFrame.dispose();
                        } else {
                            JOptionPane.showMessageDialog(loginFrame, "Ej giltigt inlog!");
                        }
                    }
                });
        container.add(nameField, BorderLayout.CENTER);
        loginFrame.pack();
        loginFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        loginFrame.setVisible(true);
        loginFrame.requestFocus();
        loginFrame.setAlwaysOnTop(true);
        loginFrame.setLocationRelativeTo(this);
    }

    /**
     * Creates the main part of the GUI, the tabbed pane that contains the
     * different parts of the application.
     */
    private void createComponents() {
        try {
            UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());


        } catch (Exception e) {
        }
        setLayout(new BorderLayout());
        setMinimumSize(new Dimension(800, 600));

        Container contentPane = getContentPane();
        JTabbedPane tabbedPane = new JTabbedPane();
        tabbedPane.setMaximumSize(Toolkit.getDefaultToolkit().getScreenSize());
        // tabbedPane.setPreferredSize(new Dimension(790, 570));
        tabbedPane.add("Måltidsplan", createMealPlanPanel());
        loggedInUserLabel loggedInUserLabel = new loggedInUserLabel();
        tabbedPane.addChangeListener(loggedInUserLabel);
        tabbedPane.add("Min profil", new ProfilePanel(loggedInUserLabel));
        tabbedPane.add("Forum", new ForumPanel());
        contentPane.add(tabbedPane, BorderLayout.CENTER);
        pack();
        setVisible(true);
        mainFrame = this;


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
