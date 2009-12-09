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

public class HtmlOverviewPanel extends JPanel implements Observer {

	/**
	 * This class is used so the user can get a summary of
	 * all the preferences made to his/her account.
	 */
	
	private static final long serialVersionUID = -2840460382044622882L;
	private Subject user;
	
	private JLabel overview;
	private String groups, refusedIngredients, refusedCategories; 
	
	public HtmlOverviewPanel()
	{
		this.user = Session.getInstance().getUser();
		this.user.addObserver(this);
		this.overview = new JLabel();
		setBorder(this, "Din användare:");
		init();		
	}
	
	public HtmlOverviewPanel(Subject user) {
		this.user = user;
		this.user.addObserver(this);
		this.overview = new JLabel();
		setBorder(this, "Din användare:");
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
	 * This method creates the JLabel with html code.
	 * The labels contains general information about the user.
	 */
	private void init()
	{
		setLayout(new BorderLayout());
		this.refusedCategories = listToString(this.user.getRefusedCategoriesList(), "Inga mat kategorier listade.");
		this.refusedIngredients = listToString(this.user.getRefusedIngredientsList(), "Inga ingredienser listade.");
		this.groups = listToString(this.user.getGroupNames(), "Du har inte skapat några grupper.");
		String difficulty = calcVal(this.user.getDesiredDifficulty());
		String time = calcVal(this.user.getDesiredTime());
		String cost = calcVal(this.user.getDesiredCost());		
		
		this.overview.setText(
				"<html>" +
				"<h2>Namn: </h2><b>" + this.user.getName() + "</b><br>" +
				
				"<h2>Allergier / ogillar: </h2>" + 
					"<h3>Kategorier: </h3>" + this.refusedCategories + 
					"<h3>Ingredienser: </h3>" + this.refusedIngredients + 
				
				"<h2>Dina grupper: </h2>" + this.groups +
				
				"<h2>Dina preferenser: </h2>" +
				"<b>Kalcium: </b>" + this.user.getMinCalcium() + "-" + this.user.getMaxCalcium() + "<br>" +
				"<b>Kolhydrater: </b>" + this.user.getMinCarbohydrates() + "-" + this.user.getMaxCarbohydrates() + "<br>" +
				"<b>Kolesterol: </b>" + this.user.getMinCholesterol() + "-" + this.user.getMaxCholesterol() + "<br>" +
				"<b>Energi: </b>" + this.user.getMinEnergyContent() + "-" + this.user.getMaxEnergyContent() + "<br>" +
				"<b>Fett: </b>" + this.user.getMinFat() + "-" + this.user.getMaxFat() + "<br>" +
				"<b>Protein: </b>" + this.user.getMinProtein() + "-" + this.user.getMaxProtein() + "<br>" +
				"<b>Salt: </b>" + this.user.getMinSodium() + "-" + this.user.getMaxSodium() + "<br><br>" +
					
				"<b>Kostnad: </b>" + cost + "<br>" +
				"<b>Svårighetsgrad: </b>" + difficulty + "<br>" +
				"<b>Tilllagningstid: </b>" + time + "<br>" +
					
				"</html>");
		
		add(this.overview, BorderLayout.PAGE_START);
	}
	
	/**
	 * This method returns a string that associated with 
	 * an integer. This is used to e.g. transform the users
	 * difficulty setting.
	 * @param val
	 * @return
	 */
	public String calcVal(int val)
	{
		String res = "";
		if(val == -1 | val == 0)
		{
			res = "<i>Inte inställt.</i>";			
		}
		else if(val == 1)
		{
			res = "Låg";
		}
		else if(val == 2)
		{
			res = "Medium";
		}
		else if(val == 3)
		{ 
			res = "Hög";
		}

		return res;
	}
	
	/**
	 * This method takes a list of strings and turn it
	 * into a string. If the string is empty the string
	 * will contain a message that there's no info in it.  
	 * @param list
	 * @return
	 */
	public String listToString(ArrayList<String> list, String msg)
	{
		String text = "";
		if(list.isEmpty())
		{
			text = "<i>"+msg+"</i><br>";
		}
		else
		{
			for(int i=0; i<list.size(); i++)
			{
				if(i==list.size()-1)
				{
					text = text + list.get(i) + ".";
				}
				else
				{
					text = text + list.get(i) + ", ";
				}
			}
		}
		
		return text;
	}

	/**
	 * Update method for when the user changes its settings.
	 */
	public void update(Observable arg0, Object arg1) {
		this.user = Session.getInstance().getUser();
		init();
	}
}
