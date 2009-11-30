package ui.panels.profile;

import java.awt.BorderLayout;

import javax.swing.JComponent;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTabbedPane;

public class ProfilePanel extends JPanel {

   private static final long serialVersionUID = -3037636351809756514L;

   private final static String OVERVIEW = "Översikt";
   private final static String DISLIKES = "Jag gillar inte";
   private final static String GROUPS = "Grupphantering";
   private final static String ALLERGIES = "Allergier";
   private final static String PREFERENCES = "Preferenser";
   private final static String NUTRITION = "Näringsvärden";
   private final static String WISHLIST = "Önskelista";

   public ProfilePanel(JLabel loggedInUserLabel) {
      setLayout(new BorderLayout());

      JTabbedPane tabPane = new JTabbedPane();
      add(tabPane);
           
      tabPane.add(OVERVIEW, createOverviewTab());
      tabPane.add(DISLIKES, createDislikesTab());
      tabPane.add(GROUPS, createGroupsTab());
      tabPane.add(ALLERGIES, createAllergiesTab());
      tabPane.add(PREFERENCES, createPreferencesTab());
      tabPane.add(NUTRITION, createNutritionTab());
      tabPane.add(WISHLIST, createWishlistTab());
      
      add(loggedInUserLabel, BorderLayout.NORTH);
   }

   private JComponent createOverviewTab(){
	   JPanel panel = new JPanel();
	   return panel;
   }

   private JComponent createDislikesTab(){
	   JPanel panel = new JPanel();
	   return panel;
   }

   private JComponent createAllergiesTab(){
	   JPanel panel = new JPanel();
	   return panel;
   }

   private JComponent createGroupsTab(){
	   JPanel panel = new JPanel();
	   return panel;
   }

   private JComponent createPreferencesTab(){
	   PreferencesPanel panel = new PreferencesPanel();
	   return panel;
   }

   private JComponent createNutritionTab(){
	   JPanel panel = new NutritionPanel();
	   
	   return panel;
   }
   
   private JComponent createWishlistTab(){
	   JPanel panel = new JPanel();
	   return panel;
   }
}
