package ui.panels.profile;

import java.awt.CardLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.GridLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.BorderFactory;
import javax.swing.BoxLayout;
import javax.swing.ButtonGroup;
import javax.swing.JComponent;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JToggleButton;
import javax.swing.border.EtchedBorder;
import javax.swing.border.TitledBorder;

import ui.components.AddRemoveComponent;

public class ProfilePanel extends JPanel implements ActionListener {

	private static final long serialVersionUID = -3037636351809756514L;

	private final static String OVERVIEW = "Översikt";
	private final static String DISLIKES = "Allergier/Ogillar";
	private final static String GROUPS = "Grupphantering";
	private final static String PREFERENCES = "Preferenser";
	private final static String NUTRITION = "Näringsvärden";
	private final static String WISHLIST = "Önskelista";

	@SuppressWarnings("unused")
	private JFrame mainFrame;
	private JPanel cards; //a panel that uses CardLayout
	private JScrollPane cardsScrollPane;

	public ProfilePanel(JLabel loggedInUserLabel) {
		mainFrame = (JFrame)getParent();
		
        //Put the buttons in a JPanel to get a nicer look
        JPanel menuPane = new JPanel(new GridLayout(0,1));
        String buttons[] = { OVERVIEW, DISLIKES, GROUPS, PREFERENCES, 
        		NUTRITION, WISHLIST};
		//JList menuList = new JList(buttons);
        menuPane.add(loggedInUserLabel);
        //menuPane.add(menuList);
        
        ButtonGroup bg = new ButtonGroup();
        for(int i = 0; i < buttons.length; i++) {
        	JToggleButton button;
        	if(buttons[i].equals(OVERVIEW))
        		button = new JToggleButton(buttons[i], true);
        	else
        		button = new JToggleButton(buttons[i]);
            button.addActionListener(this);
            bg.add(button);
            menuPane.add(button);
        }
                
        //Create the "cards" and the panel that contains the "cards"
        cards = new JPanel(new CardLayout());
        cardsScrollPane = new JScrollPane(cards);
        cards.add(createOverviewTab(), OVERVIEW);
        cards.add(createDislikesTab(), DISLIKES);
        cards.add(createGroupsTab(), GROUPS);
        cards.add(createPreferencesTab(), PREFERENCES);
        cards.add(createNutritionTab(), NUTRITION);
        cards.add(createWishlistTab(), WISHLIST);

        this.setLayout(new BoxLayout(this, BoxLayout.X_AXIS));

        cards.setBorder(BorderFactory.createEtchedBorder(EtchedBorder.LOWERED));
        menuPane.setBorder(BorderFactory.createEtchedBorder(EtchedBorder.LOWERED));
        
        menuPane.setAlignmentX(LEFT_ALIGNMENT);
        menuPane.setAlignmentY(TOP_ALIGNMENT);
        cardsScrollPane.setAlignmentX(LEFT_ALIGNMENT);
        cardsScrollPane.setAlignmentY(TOP_ALIGNMENT);
        add(menuPane);
        add(cardsScrollPane);
	}

	public void actionPerformed(ActionEvent e) {
        AddRemoveComponent.hideCompletionWindows();

        CardLayout cl = (CardLayout)(cards.getLayout());
        cl.show(cards, (String)e.getActionCommand());
	}
	
	private JComponent createOverviewTab(){
        setLayout(new GridBagLayout());
        
        JPanel testPanel = new JPanel();
        testPanel.setLayout(new GridBagLayout());
        
        GridBagConstraints c = new GridBagConstraints();
        c.gridx = 0; c.gridy = 0;
        c.weightx = 0.5; c.weighty = 0.06;
        c.fill = GridBagConstraints.BOTH;
        c.insets = new Insets(5, 5, 5, 5);

        c.weightx = 1.0;
        c.weighty = 1.0;
        add(testPanel, c);

        c = new GridBagConstraints();
        c.gridx = 0; c.gridy = 1;
        c.weightx = 1.0; c.weighty = 1.0;
        c.insets = new Insets(5, 5, 5, 5);
        c.anchor = GridBagConstraints.FIRST_LINE_START;
        c.fill = GridBagConstraints.BOTH;
        testPanel.add(new HtmlOverviewPanel(), c);
        
		return testPanel;
	}

	private JComponent createDislikesTab(){
		return new DislikePanel();
	}

	private JComponent createGroupsTab(){
		return new GroupPanel();
	}

	private JComponent createPreferencesTab(){
		return new PreferencesPanel();
	}

	private JComponent createNutritionTab(){
		JPanel panel = new JPanel();
		panel.add(new JScrollPane(new NutritionPanel()));
		return panel;
	}

	private JComponent createWishlistTab(){
		return new WishlistPanel();
	}
}
