package ui.panels.profile;

import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import javax.swing.JLabel;
import javax.swing.JPanel;

import ui.components.AddRemoveComponent;

public class WishlistPanel extends JPanel {
    private static final String helpText =
        "Välj rätter som du extra gärna vill få som förslag i framtiden nedan:";

    private AddRemoveComponent wishlistList;

    public WishlistPanel() {
        wishlistList = new AddRemoveComponent(false, false);

        setLayout(new GridBagLayout());

        GridBagConstraints c = new GridBagConstraints();
        c.gridx = 0; c.gridy = 0;
        c.weightx = 1.0; c.weighty = 0;
        c.fill = GridBagConstraints.BOTH;
        c.insets = new Insets(5, 5, 5, 5);
        add(new JLabel(helpText), c);

        c = new GridBagConstraints();
        c.gridx = 0; c.gridy = 1;
        c.weightx = 1.0; c.weighty = 1.0;
        c.insets = new Insets(5, 5, 5, 5);
        c.anchor = GridBagConstraints.FIRST_LINE_START;
        add(wishlistList, c);
    }
}
