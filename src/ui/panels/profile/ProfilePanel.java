package ui.panels.profile;

import java.awt.BorderLayout;
import java.awt.CardLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JComponent;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;

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

	public ProfilePanel(JLabel loggedInUserLabel) {
		mainFrame = (JFrame)getParent();
		
        //Put the buttons in a JPanel to get a nicer look
        JPanel buttonsPane = new JPanel(new GridLayout(0,1));
        String buttons[] = { OVERVIEW, DISLIKES, GROUPS, PREFERENCES, 
        		NUTRITION, WISHLIST};
        for(int i = 0; i < buttons.length; i++) {
            JButton button = new JButton(buttons[i]);
            button.addActionListener(this);
            buttonsPane.add(button);
        }
        
        //Create the "cards" and the panel that contains the "cards"
        cards = new JPanel(new CardLayout());
        cards.add(createOverviewTab(), OVERVIEW);
        cards.add(createDislikesTab(), DISLIKES);
        cards.add(createGroupsTab(), GROUPS);
        cards.add(createPreferencesTab(), PREFERENCES);
        cards.add(createNutritionTab(), NUTRITION);
        cards.add(createWishlistTab(), WISHLIST);
        
        add(buttonsPane, BorderLayout.PAGE_START);
        add(cards, BorderLayout.CENTER);
		add(loggedInUserLabel, BorderLayout.SOUTH);
	}

	public void actionPerformed(ActionEvent e) {
        CardLayout cl = (CardLayout)(cards.getLayout());
        cl.show(cards, (String)e.getActionCommand());
	}
	
	private JComponent createOverviewTab(){
		return new OverviewPanel();
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
		return new JScrollPane(new NutritionPanel());
	}

	private JComponent createWishlistTab(){
		return new WishlistPanel();
	}
}
