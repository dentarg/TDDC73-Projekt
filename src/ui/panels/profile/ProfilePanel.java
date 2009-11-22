package ui.panels.profile;

import java.awt.BorderLayout;

import javax.swing.JComponent;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTabbedPane;

public class ProfilePanel extends JPanel {

   private static final long serialVersionUID = -3037636351809756514L;

   private final static String LIKES = "Jag gillar";
   private final static String DISLIKES = "Jag gillar inte";
   private final static String FAMILY = "Min familj";
   private final static String ALLERGIES = "Allergier";
   private final static String PREFERENCES = "Inställningar";
   private final static String NUTRITION = "Näringsvärden";

   public ProfilePanel() {
      setLayout(new BorderLayout());
      add(new JLabel("<html><h1>Inte implementerad</h1></html>"));

      JTabbedPane tabPane = new JTabbedPane();
      add(tabPane);
           
      tabPane.add(LIKES, createLikesTab());
      tabPane.add(DISLIKES, createDislikesTab());
      tabPane.add(FAMILY, createFamilyTab());
      tabPane.add(ALLERGIES, createAllergiesTab());
      tabPane.add(PREFERENCES, createPreferencesTab());
      tabPane.add(NUTRITION, createNutritionTab());

   }

   private JComponent createLikesTab(){
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

   private JComponent createFamilyTab(){
	   JPanel panel = new JPanel();
	   return panel;
   }

   private JComponent createPreferencesTab(){
	   JPanel panel = new JPanel();
	   return panel;
   }

   private JComponent createNutritionTab(){
	   JPanel panel = new JPanel();
	   return panel;
   }

}
