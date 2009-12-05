package ui.panels.profile;

import java.awt.BorderLayout;

import javax.swing.JComponent;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTabbedPane;

public class ProfilePanel extends JPanel {

	private static final long serialVersionUID = -3037636351809756514L;

	private final static String OVERVIEW = "Översikt";
	private final static String DISLIKES = "Allergier/Ogillar";
	private final static String GROUPS = "Grupphantering";
	private final static String PREFERENCES = "Preferenser";
	private final static String NUTRITION = "Näringsvärden";
	private final static String WISHLIST = "Önskelista";

	private JFrame mainFrame;

	public ProfilePanel(JLabel loggedInUserLabel) {
		setLayout(new BorderLayout());
		mainFrame = (JFrame)getParent();
		JTabbedPane tabPane = new JTabbedPane();
		add(tabPane);
		tabPane.add(OVERVIEW, createOverviewTab());
		tabPane.add(DISLIKES, createDislikesTab());
		tabPane.add(GROUPS, createGroupsTab());
		tabPane.add(PREFERENCES, createPreferencesTab());
		tabPane.add(NUTRITION, createNutritionTab());
		tabPane.add(WISHLIST, createWishlistTab());

		add(loggedInUserLabel, BorderLayout.SOUTH);
	}

	private JComponent createOverviewTab(){
		JPanel panel = new OverviewPanel();
		return panel;
	}

	private JComponent createDislikesTab(){
		JPanel panel = new DislikePanel();
		return panel;
	}

	private JComponent createGroupsTab(){
		JPanel panel = new GroupPanel();
		return panel;
	}

	private JComponent createPreferencesTab(){
		PreferencesPanel panel = new PreferencesPanel();
		return panel;
	}

	private JComponent createNutritionTab(){
		return new JScrollPane(new NutritionPanel());
	}

	private JComponent createWishlistTab(){
                return new WishlistPanel();
	}
}
