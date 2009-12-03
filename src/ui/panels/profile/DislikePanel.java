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
import javax.swing.JPanel;
import javax.swing.border.TitledBorder;
import ui.components.AddRemoveComponent;
import ui.components.AddRemoveListener;

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
                
            }

            public void objectRemoved(Object o) {
                user.removeRefusedCategory(o.toString());
            }

            public void objectSelected(Object o) {
            }

            public void selectedObjectRemoved(Object o) {
            }

            public void objectRemoved(Object o, boolean wasSelected) {
            }
        });

        dislikeList.addAddRemoveListener(new AddRemoveListener() {

            public void objectAdded(Object o) {
                user.addRefusedIngredient(o.toString());
            }

            public void objectRemoved(Object o) {
                user.removeRefusedIngredient(o.toString());

            }

            public void objectSelected(Object o) {
            }

            public void selectedObjectRemoved(Object o) {
            }

            public void objectRemoved(Object o, boolean wasSelected) {
            }
        });

        setPreferredSize(new Dimension(800, 600));
        setLayout(
                new GridBagLayout());
        GridBagConstraints c = new GridBagConstraints();

        c.fill = GridBagConstraints.BOTH;
        c.weightx = 0.5;
        c.weighty = 0.5;
        c.gridx = 0;
        c.gridy = 0;
        c.insets = new Insets(5, 5, 5, 5);

        add(createAllergyPanel(), c);

        c.gridx = 1;

        add(createDislikePanel(), c);


    }

    public JPanel createAllergyPanel() {
        JPanel groupPanel = new JPanel();
        groupPanel.setLayout(new BorderLayout());
        groupPanel.add(allergyList, BorderLayout.NORTH);

        TitledBorder tb = BorderFactory.createTitledBorder(
                BorderFactory.createLineBorder(Color.GRAY),
                "Dina Allergier:",
                TitledBorder.LEFT,
                TitledBorder.CENTER,
                new Font("Arial", Font.BOLD, 15));
        groupPanel.setBorder(tb);

        return groupPanel;
    }
    // Create subjectpanel containing an AddRemoveComponent subjectList.

    public JPanel createDislikePanel() {
        JPanel subjectPanel = new JPanel();
        subjectPanel.setLayout(new BorderLayout());
        subjectPanel.add(dislikeList, BorderLayout.NORTH);

        TitledBorder tb = BorderFactory.createTitledBorder(
                BorderFactory.createLineBorder(Color.GRAY),
                "Ingredienser du ogillar: ",
                TitledBorder.LEFT,
                TitledBorder.CENTER,
                new Font("Arial", Font.BOLD, 15));
        subjectPanel.setBorder(tb);

        return subjectPanel;
    }
}
