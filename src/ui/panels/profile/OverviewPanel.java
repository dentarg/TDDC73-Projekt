package ui.panels.profile;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.GridLayout;
import java.awt.TextArea;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.Observable;
import java.util.Observer;

import javax.swing.BorderFactory;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.border.TitledBorder;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

import dataObjects.Session;
import dataObjects.Subject;

public class OverviewPanel extends JPanel implements Observer {

	/**
	 * This class is used so the user can get a summary of
	 * all the preferences made to his/her account.
	 */
	
	private static final long serialVersionUID = -2840460382044622882L;
	private Subject user;
	
	private TextArea nutritions, personalArea, prefArea, groupArea;
	private JLabel name;
	
	public OverviewPanel()
	{
		this.user = Session.getInstance().getUser();
		this.user.addObserver(this);
		this.nutritions = new TextArea("", 6, 35, TextArea.SCROLLBARS_NONE);
		this.personalArea = new TextArea("", 2, 20, TextArea.SCROLLBARS_NONE);
		this.prefArea = new TextArea("", 5, 100, TextArea.SCROLLBARS_NONE);
		this.groupArea = new TextArea("", 5, 100, TextArea.SCROLLBARS_NONE);
		this.name = new JLabel(this.user.getName());
		init();		
	}
	
	public OverviewPanel(Subject user) {
		this.user = user;
		this.user.addObserver(this);
		init();
	}
	
	/**
	 * This method sets a border and a title to a JPanel.
	 * @param panel
	 * 		The panel you want to add the border to.
	 * @param text
	 * 		The title to the border.
	 */
	private void setBorder(JPanel panel, String text)
	{
		TitledBorder tb = BorderFactory.createTitledBorder(
                BorderFactory.createLineBorder(Color.GRAY),
                text,
                TitledBorder.LEFT,
                TitledBorder.CENTER,
                new Font("Arial", Font.BOLD, 15));	
		panel.setBorder(tb);
	}
	
	/**
	 * This method initiates the window with all the panels in a gridLayout.
	 * It also collects data from the user and represents it visually.
	 */
	private void init()
	{
		setLayout(new GridLayout(4,1));
		setBorder(this, "Din användare:");
		JPanel user = new JPanel();
		JPanel preferences = new JPanel();
		JPanel groups = new JPanel();
		JPanel personal = new JPanel();		

		//Add cool borders to the to separate panels.
		setBorder(preferences, "Gillar inte / allergier:");
		setBorder(groups, "Dina grupper:");
		setBorder(personal, "Personliga inställningar:");		
		
		//Set the layout of the panels.
		user.setLayout(new BorderLayout());
		preferences.setLayout(new FlowLayout(FlowLayout.LEFT, 0, 10));
		groups.setLayout(new FlowLayout(FlowLayout.LEFT, 0, 10));
		personal.setLayout(new FlowLayout(FlowLayout.LEFT, 0, 10));
		
		//Some predefined fonts.
		Font infoText = new Font("Arial", Font.ITALIC, 12);
		Font label = new Font("Arial", Font.BOLD, 14);
		
		JLabel info = new JLabel("Här kan du se inställningar som är gjorda för din användare.");
		info.setFont(infoText);
		user.add(info, BorderLayout.PAGE_START);
		this.name.setFont(label);	
		user.add(this.name, BorderLayout.LINE_START);
	
		this.prefArea.setEditable(false);
		ArrayList<String> refList = this.user.getRefusedIngredientsList();
		this.printList(this.prefArea, refList);
		preferences.add(this.prefArea);
		
		this.groupArea.setEditable(false);
		ArrayList<String> groupList = this.user.getGroupNames();
		printList(this.groupArea, groupList);
		groups.add(this.groupArea);
		
		this.nutritions.setEditable(false);
		printNutritions();
		personal.add(this.nutritions);
		
		this.personalArea.setEditable(false);
		printPersonalInfo();
		personal.add(this.personalArea);
				
		//Add all the panels to the view.
		add(user);
		add(preferences);
		add(groups);
		add(personal);
	
	}
	
	private void printPersonalInfo()
	{
		this.personalArea.setText("Kostnad: " + + this.user.getDesiredCost());
		this.personalArea.append("\nTid: " + this.user.getDesiredTime());
		this.personalArea.append("\nSvårighetsgrad: " + this.user.getDesiredDifficulty());
		
	}
	
	private void printNutritions()
	{
		this.nutritions.setText("Kalcium: " + this.user.getMinCalcium() + "-" + this.user.getMaxCalcium());
		this.nutritions.append("\nKolhydrater: " + this.user.getMinCarbohydrates() + "-" + this.user.getMaxCarbohydrates());
		this.nutritions.append("\nKolesterol: " + this.user.getMinCholesterol() + "-" + this.user.getMaxCholesterol());
		this.nutritions.append("\nEnergi: " + this.user.getMinEnergyContent() + "-" + this.user.getMaxEnergyContent());
		this.nutritions.append("\nFett: " + this.user.getMinFat() + "-" + this.user.getMaxFat());
		this.nutritions.append("\nProtein: " + this.user.getMinProtein() + "-" + this.user.getMaxProtein());
		this.nutritions.append("\nSalt: " + this.user.getMinSodium() + "-" + this.user.getMaxSodium());
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
		area.setText("");
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

	public void update(Observable arg0, Object arg1) {
		this.user = Session.getInstance().getUser();
		printNutritions();
		printPersonalInfo();
		printList(this.groupArea, this.user.getGroupNames());
		printList(this.prefArea, this.user.getRefusedIngredientsList());
		this.name.setText(this.user.getName());
	}
}
