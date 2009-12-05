package ui.panels.profile;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
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
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import javax.swing.SwingConstants;
import javax.swing.border.LineBorder;
import javax.swing.border.TitledBorder;

import mappings.CostMap;
import mappings.DifficultyMap;
import mappings.TimeMap;

import dataObjects.PlanVariables;
import dataObjects.Session;
import dataObjects.Subject;

public class PreferencesPanel extends JPanel implements ActionListener {

	private static final long serialVersionUID = -9011866376505097065L;
	private Subject user;

	public PreferencesPanel() {
		this.user = Session.getInstance().getUser();
        setPreferredSize(new Dimension(800, 600));
        setLayout(new GridBagLayout());
        GridBagConstraints c = new GridBagConstraints();
        c.fill = GridBagConstraints.BOTH;
        c.weightx = 0.5;
        c.weighty = 0.5;
        c.gridx = 0;
        c.gridy = 0;
        c.insets = new Insets(5, 5, 5, 5);
        add(createPrefPanel(), c);
        c.gridx = 1;
        add(createDietPanel(), c);
	}
	
    public JPanel createPrefPanel() {
        JPanel prefPanel = new JPanel();
        prefPanel.setPreferredSize(new Dimension(300, 600));      
        prefPanel.setLayout(new BoxLayout(prefPanel, BoxLayout.PAGE_AXIS));
        prefPanel.add(createButtonGroup("Kostnad"));
        prefPanel.add(createButtonGroup("Svårighetsgrad"));
        prefPanel.add(createButtonGroup("Tillagningstid"));
        TitledBorder tb = BorderFactory.createTitledBorder(
                BorderFactory.createLineBorder(Color.GRAY),
                "Preferenser",
                TitledBorder.LEFT,
                TitledBorder.CENTER,
                new Font("Arial", Font.BOLD, 15));
        prefPanel.setBorder(tb);
        return prefPanel;
    }
    
    public JPanel createDietPanel() {
        JPanel dietPanel = new JPanel();
        JLabel dietLabel = new JLabel("Här kan du ställa in specialkost.");
        dietPanel.setPreferredSize(new Dimension(300, 600));
        dietPanel.setLayout(new BoxLayout(dietPanel, BoxLayout.PAGE_AXIS));
        dietLabel.setVerticalAlignment(JLabel.CENTER);
        dietLabel.setHorizontalAlignment(JLabel.CENTER);
        LineBorder lb = (LineBorder) BorderFactory.createLineBorder(Color.gray);
        dietLabel.setBorder(lb);
        dietLabel.setPreferredSize(new Dimension(300, 50));
        dietPanel.add(dietLabel);
        
        dietPanel.add(new DietPanel());

        TitledBorder tb = BorderFactory.createTitledBorder(
                BorderFactory.createLineBorder(Color.GRAY),
                "Specialkost",
                TitledBorder.LEFT,
                TitledBorder.CENTER,
                new Font("Arial", Font.BOLD, 15));
        dietPanel.setBorder(tb);

        return dietPanel;
    }    

    public String translate(String str) {
    	if(str.equals("low"))
    		return new String("låg");
    	if(str.equals("medium"))
    		return new String("mellan");
    	if(str.equals("high"))
    		return new String("hög");
    	if(str.equals("easy"))
    		return new String("lätt");
    	if(str.equals("hard"))
    		return new String("svår");
    	if(str.equals("short"))
    		return new String("kort");    	
    	
    	System.out.println("u: " + str);
    	return new String("unknown word");
    }
    
	public JPanel createButtonGroup(String title) {
		// a column in a panel
		JPanel radioPanel = new JPanel(new GridLayout(0,1));
		ButtonGroup group = new ButtonGroup();
		
		// value is always in the range 1 to 3
		for(int i=1; i<4; i++) {
			JRadioButton button = new JRadioButton();
			PlanVariables pv = new PlanVariables();

			// use mappings to get button labels from value
			if(title.equals("Kostnad")) {
				CostMap map = pv.getCostMap();
				button.setText(translate(map.getValue(i)));
				button.setActionCommand("cost" + i);
			}
			else if(title.equals("Svårighetsgrad")) {
				DifficultyMap map = pv.getDifficultyMap();
				button.setText(translate(map.getValue(i)));
				button.setActionCommand("difficulty" + i);
			}
			else if(title.equals("Tillagningstid")) {
				TimeMap map = pv.getTimeMap();
				button.setText(translate(map.getValue(i)));
				button.setActionCommand("time" + i);
			}
			group.add(button);
			button.addActionListener(this);
			radioPanel.add(button);
		}
        TitledBorder tb = BorderFactory.createTitledBorder(
        		BorderFactory.createEtchedBorder(),
        		title,
        		TitledBorder.CENTER,
        		TitledBorder.CENTER,
        		new Font("Arial", Font.BOLD, 13)
        );
        radioPanel.setBorder(tb);
        return radioPanel;
	}

	private void init() {
		setLayout(new GridBagLayout());
		GridBagConstraints c = new GridBagConstraints();
		
		Font headlineFont = new Font("Arial", Font.BOLD, 25);
				
		c.anchor 	= GridBagConstraints.FIRST_LINE_START;
		c.gridx		= 0;
		c.gridy		= 0;
		c.weightx	= 0.5;
		c.weighty	= 0;
		JLabel prefHeadline = new JLabel("Preferenser");
		prefHeadline.setFont(headlineFont);
		add(prefHeadline, c);

		c.anchor 	= GridBagConstraints.FIRST_LINE_START;
		c.fill 		= GridBagConstraints.BOTH;
		c.gridx		= 0;
		c.gridy		= 1;
		add(createButtonGroup("Kostnad"), c);

		c.gridx		= 1;
		c.gridy		= 1;
		c.anchor 	= GridBagConstraints.PAGE_START;
		add(createButtonGroup("Svårighetsgrad"), c);

		c.gridx		= 2;
		c.gridy		= 1;
		c.anchor 	= GridBagConstraints.FIRST_LINE_END;
		add(createButtonGroup("Tillagningstid"), c);

		c.gridx		= 0;
		c.gridy		= 2;
		c.weighty	= 0;
		c.fill		= GridBagConstraints.NONE;
		c.anchor 	= GridBagConstraints.FIRST_LINE_START;
		JLabel dietHeadline = new JLabel("Specialkost");
		dietHeadline.setFont(headlineFont);
		add(dietHeadline, c);
		
		c.gridx 	= 0;
		c.gridy		= 3;
		c.gridwidth = 2;
		c.weighty	= 0.5;
		c.fill		= GridBagConstraints.BOTH;
		add(new DietPanel(), c);
	}

	// update the user model with chosen value
	public void actionPerformed(ActionEvent e) {
		String s = e.getActionCommand();
		if(s.startsWith("cost")) {
			int desiredCost = Integer.parseInt(s.substring(s.length()-1));	
			user.setDesiredCost(desiredCost);
		}
		else if(s.startsWith("difficulty")){
			int desiredDifficulty = Integer.parseInt(s.substring(s.length()-1));
			user.setDesiredDifficulty(desiredDifficulty);
		}
		else if(s.startsWith("time")){
			int desiredTime = Integer.parseInt(s.substring(s.length()-1));
			user.setDesiredTime(desiredTime);
		}
	}
}
