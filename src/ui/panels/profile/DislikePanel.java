/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package ui.panels.profile;

import dataObjects.IngredientAvailability;
import dataObjects.IngredientCategory;
import dataObjects.Session;
import dataObjects.Subject;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;
import javax.swing.BorderFactory;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.border.EtchedBorder;
import javax.swing.border.LineBorder;
import javax.swing.border.TitledBorder;
import ui.components.AddRemoveComponent;
import ui.components.AddRemoveListener;
import ui.components.StatusPanel;

/**
 *
 * @author jonas
 */
public class DislikePanel extends JPanel {

    private javax.swing.JLabel allergyLabel;
    private javax.swing.JLabel dislikeLabel;
    private AddRemoveComponent allergyList;
    private AddRemoveComponent dislikeList;
    private javax.swing.JSeparator jSeparator1;
    private List dislikeContent = new ArrayList();
    private List allergyContent = new ArrayList();
    private Session session = Session.getInstance();
    private Subject user = session.getUser();

    public DislikePanel() {
        //   initComponents();
        allergyList = new AddRemoveComponent();
        dislikeList = new AddRemoveComponent();


        allergyContent = IngredientCategory.getIngredientCategoriesFromFile();


        allergyList.setContents(allergyContent);


        for (Object x : allergyContent) {
            IngredientCategory category = (IngredientCategory) x;
            ArrayList<IngredientAvailability> ingredients = category.getIngredients();
            for (int i = 0; i < ingredients.size(); i++) {
                dislikeContent.add(ingredients.get(i));
            }
        }
        dislikeList.setContents(dislikeContent);

        allergyList.addAddRemoveListener(new AddRemoveListener() {

            public void objectAdded(Object o) {
                user.addRefusedCategory(o.toString());
                IngredientCategory category = (IngredientCategory) o;
                ArrayList<IngredientAvailability> ingredients = category.getIngredients();
                for (int i = 0; i < ingredients.size(); i++) {
                    user.addAllergyIngredient(ingredients.get(i).toString());
                }
                StatusPanel.getInstance().flash("Mat som innehåller " + o.toString() + " kommer nu prioriteras lägre", StatusPanel.INFO);
            }

            public void objectRemoved(Object o) {
            }

            public void objectSelected(Object o) {
            }

            public void selectedObjectRemoved(Object o) {
            }

            public void objectRemoved(Object o, boolean wasSelected) {
                user.removeRefusedCategory(o.toString());
                IngredientCategory category = (IngredientCategory) o;
                ArrayList<IngredientAvailability> ingredients = category.getIngredients();
                for (int i = 0; i < ingredients.size(); i++) {
                    user.removeAllergyIngredient(ingredients.get(i).toString());
                }
                StatusPanel.getInstance().flash("Mat som innehåller " + o.toString() + " kommer prioriteras normalt", StatusPanel.INFO);
            }
        });

        dislikeList.addAddRemoveListener(new AddRemoveListener() {

            public void objectAdded(Object o) {
                user.addRefusedIngredient(o.toString());
                StatusPanel.getInstance().flash("Mat som innehåller " + o.toString() + " kommer nu prioriteras lägre", StatusPanel.INFO);

            }

            public void objectRemoved(Object o) {
            }

            public void objectSelected(Object o) {
            }

            public void selectedObjectRemoved(Object o) {
            }

            public void objectRemoved(Object o, boolean wasSelected) {
                user.removeRefusedIngredient(o.toString());
                StatusPanel.getInstance().flash("Mat som innehåller " + o.toString() + " kommer prioriteras normalt", StatusPanel.INFO);

            }
        });

        setPreferredSize(new Dimension(800, 600));
        setLayout(new GridBagLayout());
        GridBagConstraints c = new GridBagConstraints();

        c.fill = GridBagConstraints.BOTH;
        c.weightx = 0.5;
        c.weighty = 0.5;
        c.gridx = 0;
        c.gridy = 0;
        c.insets = new Insets(5, 5, 5, 5);
        c.anchor = GridBagConstraints.NORTHWEST;
        add(createAllergyPanel(), c);

        c.gridx = 1;

        add(createDislikePanel(), c);


    }

    public JPanel createAllergyPanel() {

        JPanel allergyPanel = new JPanel();
        //GridBagConstraints c = new GridBagConstraints();
        allergyPanel.setPreferredSize(new Dimension(300, 600));
        allergyLabel = new JLabel("Här kan du ställa in dina allergier.");
        allergyLabel.setVerticalAlignment(JLabel.CENTER);
        allergyLabel.setHorizontalAlignment(JLabel.CENTER);
        LineBorder lb = (LineBorder) BorderFactory.createLineBorder(Color.gray);
        allergyLabel.setBorder(lb);
        allergyPanel.setLayout(new GridBagLayout());
        allergyLabel.setPreferredSize(new Dimension(300, 50));

        GridBagConstraints c = new GridBagConstraints();
        c.gridx = 0;
        c.gridy = 0;
        c.weightx = 1.0;
        c.weighty = 0;
        c.fill = GridBagConstraints.BOTH;
        c.insets = new Insets(5, 5, 5, 5);
        allergyPanel.add(allergyLabel, c);

        c = new GridBagConstraints();
        c.gridx = 0;
        c.gridy = 1;
        c.weightx = 1.0;
        c.weighty = 1.0;
        c.insets = new Insets(5, 5, 5, 5);
        c.anchor = GridBagConstraints.FIRST_LINE_START;
        c.gridy = 1;
        c.weightx = 1;
        c.weighty = 1;
        c.gridheight = 3;
        //  c.anchor = GridBagConstraints.NORTH
        allergyPanel.add(allergyList, c);


        TitledBorder tb = BorderFactory.createTitledBorder(
                BorderFactory.createLineBorder(Color.GRAY),
                "Dina Allergier",
                TitledBorder.LEFT,
                TitledBorder.CENTER,
                new Font("Arial", Font.BOLD, 15));
        allergyPanel.setBorder(tb);

        return allergyPanel;
    }

    public JPanel createDislikePanel() {
        JPanel dislikePanel = new JPanel();
        dislikeLabel = new JLabel("<html>Här kan du ställa in ingredienser du inte tycker om.</html>");
        dislikePanel.setPreferredSize(new Dimension(300, 600));
        dislikePanel.setLayout(new GridBagLayout());
        dislikeLabel.setVerticalAlignment(JLabel.CENTER);
        dislikeLabel.setHorizontalAlignment(JLabel.CENTER);
        LineBorder lb = (LineBorder) BorderFactory.createLineBorder(Color.gray);
        dislikeLabel.setBorder(lb);
        dislikeLabel.setPreferredSize(new Dimension(300, 50));

        GridBagConstraints c = new GridBagConstraints();
        c.gridx = 0;
        c.gridy = 0;
        c.weightx = 1.0;
        c.weighty = 0;
        c.fill = GridBagConstraints.BOTH;
        c.insets = new Insets(5, 5, 5, 5);
        dislikePanel.add(dislikeLabel, c);

        c = new GridBagConstraints();
        c.gridx = 0;
        c.gridy = 1;
        c.weightx = 1.0;
        c.weighty = 1.0;
        c.insets = new Insets(5, 5, 5, 5);
        c.anchor = GridBagConstraints.FIRST_LINE_START;
        c.gridy = 1;
        c.weightx = 1;
        c.weighty = 1;
        c.gridheight = 3;
        //  c.anchor = GridBagConstraints.NORTH
        dislikePanel.add(dislikeList, c);

        TitledBorder tb = BorderFactory.createTitledBorder(
                BorderFactory.createLineBorder(Color.GRAY),
                "Ingredienser du ogillar",
                TitledBorder.LEFT,
                TitledBorder.CENTER,
                new Font("Arial", Font.BOLD, 15));
        dislikePanel.setBorder(tb);

        return dislikePanel;
    }
}
