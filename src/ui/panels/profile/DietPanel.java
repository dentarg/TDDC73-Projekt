package ui.panels.profile;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;
import java.util.List;
import java.util.Vector;

import javax.swing.BorderFactory;
import javax.swing.ButtonGroup;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import javax.swing.border.TitledBorder;

import dataObjects.IngredientCategory;
import dataObjects.Session;
import dataObjects.Subject;

public class DietPanel extends JPanel implements ActionListener {

	/**
	 *
	 */
	private static final long serialVersionUID = -4204456733995810769L;

	/* getIngredientCategoriesFromFile(): 
     * Nötkött, Bröd, Ost, Ägg & mejeri, Fisk, Frukt, Vilt, Nötter & frön, 
     * Inälvsmat, Pasta & nudlar, Fläsk, Fågel, Soja & baljfrukter, 
     * Ris & korn, Skaldjur, Grönsaker, Smaksättning
     */	
	
	/** 
	 * Veganer undviker därmed animaliska produkter såsom kött, fågel, fisk, 
     * ägg, mjölkprodukter, skinn, ylle, dun och päls.
     * http://sv.wikipedia.org/wiki/Vegan - 20091205
     */
	String[] veganCategories = {"Nötkött", "Bröd", "Ost", "Ägg & mejeri", "Fisk", 
			"Vilt", "Inälvsmat", "Fläsk", "Fågel", "Skaldjur"};
    String[] emptyList = {};

    private static String NO_DIET_NAME = "Ingen";
	private JList contentList;
	private Vector<Diet> diets;
	private Subject user;
	private Diet oldDiet;
	private Diet currentDiet;
    
	public class Diet {
		private String name;
		private String[] refusedCategories;
		public Diet(String name, String[] refusedCategories) {
			this.name = name;
			this.refusedCategories = refusedCategories;;
		}
		public String[] getRefusedCategories() {
			return refusedCategories;
		}
		public String getName() {
			return name;
		}
	}

	public void newDiet(String name, String[] refusedCategories) {
		diets.add(new Diet(name, refusedCategories));
	}

    public DietPanel() {
    	super(new GridBagLayout());
    	this.user = Session.getInstance().getUser();
    	this.oldDiet = null;
    	
        diets = new Vector<Diet>();
        String[] nothing 	= {};
        contentList = new JList(nothing);
        contentList.setSelectionBackground(contentList.getBackground());
        contentList.setSelectionForeground(contentList.getForeground());
        
        /*
         * Diet's is saved in a global list that is used when
         * creating buttons and viewing the list that belongs to the Diet
         */
        
        newDiet("vegan", veganCategories);
        newDiet(NO_DIET_NAME, emptyList);

		GridBagConstraints c = new GridBagConstraints();      
        
		c.gridx 	= 0;
		c.gridy 	= 0;
		c.fill		= GridBagConstraints.BOTH;
		c.weightx	= 0.5;
        add(createButtons(), c);

		c.gridx 	= 1;
		c.gridy 	= 0;
        c.weightx	= 0.5;
        c.fill		= GridBagConstraints.BOTH;
        add(showList(contentList), c);
    }

    public JPanel createButtons() {
        JPanel radioPanel = new JPanel(new GridLayout(0, 1));
        ButtonGroup group = new ButtonGroup();
        for (Diet diet : diets) {
        	String name =  diet.getName();
        	JRadioButton b;
        	if(name.equals(NO_DIET_NAME))
        		b = new JRadioButton(name, true);
        	else
        		b = new JRadioButton(name);
        	b.setActionCommand(name);
        	b.addActionListener(this);
        	group.add(b);
        	radioPanel.add(b);
        }
		TitledBorder tb = BorderFactory.createTitledBorder(
				BorderFactory.createLineBorder(Color.GRAY),
				"Lifestyle",
				TitledBorder.CENTER,
				TitledBorder.CENTER,
				new Font("Arial", Font.BOLD, 15)
		);
		radioPanel.setBorder(tb);
		return radioPanel;
    }

    public JPanel showList(JList list) {
    	JPanel panel = new JPanel();
    	panel.add(list);
		TitledBorder tb = BorderFactory.createTitledBorder(
				BorderFactory.createLineBorder(Color.GRAY),
				"exkluderas",
				TitledBorder.CENTER,
				TitledBorder.CENTER,
				new Font("Arial", Font.BOLD, 15)
		);
		panel.setBorder(tb);
		return panel;
    }
    
    public void exclude(Diet diet) {
    	currentDiet = diet;
    	// reverse the last choose before adding new stuff
    	if(oldDiet != null) {
        	String[] oldRefusedCategories = oldDiet.getRefusedCategories();
        	for(int i = 0; i < oldRefusedCategories.length; i++) {
        		System.out.println("remove: " + oldRefusedCategories[i]);
        		user.removeRefusedCategory(oldRefusedCategories[i]);
        	}    		
    	}
    	String[] refusedCategories = diet.getRefusedCategories();
    	for(int i = 0; i < refusedCategories.length; i++) {
    		System.out.println("add: " + refusedCategories[i]);
    		user.addRefusedCategory(refusedCategories[i]);	
    	}
    	oldDiet = diet;
    }

    // View the LifeStyle list depending on which one is selected for the moment
    public void actionPerformed(ActionEvent e) {
    	for (Diet diet : diets) {
    		if(diet.getName().equals(e.getActionCommand())) {
    			// show which categories are excluded
    			contentList.setListData(diet.getRefusedCategories());
    			// exclude categories
    			exclude(diet);
    		}
		}
    }
}
