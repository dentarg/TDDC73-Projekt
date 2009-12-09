package ui.panels.profile;

import dataAccess.RecipeDA;

import dataObjects.Session;

import java.util.ArrayList;

import java.awt.Color;
import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;

import javax.swing.BorderFactory;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.border.EtchedBorder;
import javax.swing.border.TitledBorder;

import ui.components.AddRemoveComponent;
import ui.components.AddRemoveListener;

public class WishlistPanel extends JPanel {
    private static final String helpText =
        "<html>Välj rätter som du extra gärna vill få som förslag i framtiden nedan.</html>";

    private AddRemoveComponent wishlistList;

    public WishlistPanel() {
        wishlistList = new AddRemoveComponent(false, false);

        // Goes out to disk and reloads all recipe data, plus makes an extra
        // copy of the list just to please the type system (AddRemoveComponent
        // might have been better off using generics and a proper data model).
        // Hrrrrrrrrrr.......
        wishlistList.setContents(new ArrayList<Object>(new RecipeDA().getAllRecipes()));

        wishlistList.addAddRemoveListener(new AddRemoveListener() {
            public void objectAdded(Object o) {
                Session.getInstance().getUser().addFavoriteRecipe(o.toString());
            }

            public void objectRemoved(Object o, boolean wasSelected) {
                Session.getInstance().getUser().removeFavoriteRecipe(o.toString());
            }

            public void objectSelected(Object o) {}
        });
        
        setBorder(BorderFactory.createEtchedBorder(EtchedBorder.LOWERED));
        setLayout(new GridBagLayout());
        
        JPanel testPanel = new JPanel();
        testPanel.setLayout(new GridBagLayout());
        testPanel.setBorder(BorderFactory.createTitledBorder(
        		BorderFactory.createLineBorder(Color.GRAY),
        		"Önskelista",
        		TitledBorder.LEFT,
        		TitledBorder.CENTER,
        		new Font("Arial", Font.BOLD, 15)
        ));

        JLabel text = new JLabel(helpText);
        text.setBorder(BorderFactory.createLineBorder(Color.gray));
        text.setVerticalAlignment(JLabel.CENTER);
        text.setHorizontalAlignment(JLabel.CENTER);
        
        GridBagConstraints c = new GridBagConstraints();
        c.gridx = 0; c.gridy = 0;
        c.weightx = 0.5; c.weighty = 0.06;
        c.fill = GridBagConstraints.BOTH;
        c.insets = new Insets(5, 5, 5, 5);
        testPanel.add(text, c);
        c.weightx = 1.0;
        c.weighty = 1.0;
        add(testPanel, c);

        c = new GridBagConstraints();
        c.gridx = 0; c.gridy = 1;
        c.weightx = 1.0; c.weighty = 1.0;
        c.insets = new Insets(5, 5, 5, 5);
        c.anchor = GridBagConstraints.FIRST_LINE_START;
        testPanel.add(wishlistList, c);
    }
}
