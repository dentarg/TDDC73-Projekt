package ui.panels.profile;

import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.TextArea;
import java.util.ArrayList;
import javax.swing.JLabel;
import javax.swing.JPanel;
import dataObjects.Session;
import dataObjects.Subject;

public class OverviewPanel extends JPanel {

	/**
	 * 
	 */
	private static final long serialVersionUID = -2840460382044622882L;
	private Subject user;
	
	public OverviewPanel()
	{
		this.user = Session.getInstance().getUser();
		/*
		//Test data
		this.user = new Subject("John Doe");
		this.user.addRefusedIngredient("Vatten");
		this.user.addRefusedIngredient("Allt annat");
		this.user.addRefusedIngredient("Cider");
		this.user.createGroup("Vänner");
		this.user.createGroup("Familj");
		this.user.createGroup("Fest");
		*/
		init();
	}
	
	public OverviewPanel(Subject user) {
		this.user = user;
		init();
	}
	
	private void init()
	{
		setLayout(new GridLayout(5,1));
		JPanel top = new JPanel();
		JPanel user = new JPanel();
		JPanel preferences = new JPanel();
		JPanel groups = new JPanel();
		JPanel personal = new JPanel();		

		//Set the layout of the panels.
		top.setLayout(new FlowLayout(FlowLayout.LEFT, 0, 10));
		user.setLayout(new FlowLayout(FlowLayout.LEFT, 0, 10));
		preferences.setLayout(new FlowLayout(FlowLayout.LEFT, 0, 10));
		groups.setLayout(new FlowLayout(FlowLayout.LEFT, 0, 10));
		personal.setLayout(new FlowLayout(FlowLayout.LEFT, 0, 10));
		
		//Some predefined fonts.
		Font title = new Font("Arial", Font.BOLD, 20);
		Font label = new Font("Arial", Font.BOLD, 14);
		
		/*
		 * The title of the pane.
		 */
		JLabel overview = new JLabel("Overview");
		overview.setFont(title);
		top.add(overview);

		/*
		 * Settings for the visual representation of the user's name and picture.
		 */
		JLabel nameLabel = new JLabel("Name: ");		
		JLabel name = new JLabel(this.user.getName());
		nameLabel.setFont(label);
		user.add(nameLabel);
		user.add(name);
		
		/*
		 * Settings for the visual representation of the user's food settings.
		 */
		JLabel preferencesLabel = new JLabel("Preferenser:   ");
		preferencesLabel.setFont(label);
		preferences.add(preferencesLabel);		
		TextArea prefArea = new TextArea("", 3, 50, TextArea.SCROLLBARS_NONE);
		prefArea.setEditable(false);
		prefArea.setText("Gillar inte/allergier:");
		ArrayList<String> refList = this.user.getRefusedIngredientsList();
		printList(prefArea, refList);
		preferences.add(prefArea);
		
		/*
		 * Settings for the visual representation of the user's groups.
		 */
		JLabel groupLabel = new JLabel("Dina grupper: ");
		groupLabel.setFont(label);
		TextArea groupArea = new TextArea("", 3, 50, TextArea.SCROLLBARS_NONE);
		groupArea.setEditable(false);
		ArrayList<String> groupList = this.user.getGroupNames();
		printList(groupArea, groupList);
		groups.add(groupLabel);
		groups.add(groupArea);
		
		/*
		 * Settings for the visual representation of the user's personal settings.
		 */
		JLabel personalLabel = new JLabel("Personligt:      ");
		personalLabel.setFont(label);
		TextArea personalArea = new TextArea("", 2, 50, TextArea.SCROLLBARS_NONE);
		personalArea.setEditable(false);
		personalArea.setText("Kostnad: " + + this.user.getDesiredCost());
		personalArea.append("\nTid: " + this.user.getDesiredTime());
		personalArea.append("\nSvårighetsgrad: " + this.user.getDesiredDifficulty());
		personal.add(personalLabel);
		personal.add(personalArea);
		
		//Add all the panels to the view.
		add(top);
		add(user);
		add(preferences);
		add(groups);
		add(personal);
	
	}
	
	/**
	 * Prints an array of strings into a TextArea.
	 * @param area
	 * 		the specified textarea
	 * @param list
	 * 		the list to be printed
	 */
	private void printList(TextArea area, ArrayList<String> list)
	{
		for(int i = 0; i < list.size(); i++)
		{
			if(i == list.size()-1)
			{
				area.append(" " + list.get(i) + ".");
			}
			else
			{
				area.append(" " + list.get(i) + ",");
			}
			
		}
	}
}
