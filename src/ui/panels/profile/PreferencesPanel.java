package ui.panels.profile;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.BorderFactory;
import javax.swing.ButtonGroup;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import javax.swing.JScrollPane;
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
		init();
	}

	public PreferencesPanel(Subject user) {
		this.user = user;
		init();
	}

	public JPanel createButtonGroup(String title) {
		JPanel groupPanel = new JPanel();
		ButtonGroup group = new ButtonGroup();
		// a column in a panel
		JPanel radioPanel = new JPanel(new GridLayout(0, 1));

		// value is always in the range 1 to 3
		for(int i=1; i<4; i++) {
			JRadioButton b = new JRadioButton();
			PlanVariables pv = new PlanVariables();

			// use mappings to get button labels from value
			if(title.equals("Kostnad")) {
				CostMap map = pv.getCostMap();
				b.setText(map.getValue(i));
				b.setActionCommand("cost" + i);
			}
			else if(title.equals("Svårighetsgrad")) {
				DifficultyMap map = pv.getDifficultyMap();
				b.setText(map.getValue(i));
				b.setActionCommand("difficulty" + i);
			}
			else if(title.equals("Tillagningstid")) {
				TimeMap map = pv.getTimeMap();
				b.setText(map.getValue(i));
				b.setActionCommand("time" + i);
			}

			group.add(b);
			b.addActionListener(this);
			radioPanel.add(b);
		}
		groupPanel.add(radioPanel, BorderLayout.LINE_START);
        TitledBorder tb = BorderFactory.createTitledBorder(
        		BorderFactory.createLineBorder(Color.GRAY),
        		title,
        		TitledBorder.CENTER,
        		TitledBorder.CENTER,
        		new Font("Arial", Font.BOLD, 15)
        );
        groupPanel.setBorder(tb);
		return groupPanel;
	}

	private void init() {
		setLayout(new GridBagLayout());
		GridBagConstraints c = new GridBagConstraints();

		c.anchor 	= GridBagConstraints.FIRST_LINE_START;
		c.fill 		= GridBagConstraints.HORIZONTAL;
		c.gridx		= 0;
		c.gridy		= 0;
		add(createButtonGroup("Kostnad"), c);

		c.gridx		= 1;
		c.anchor 	= GridBagConstraints.PAGE_START;
		add(createButtonGroup("Svårighetsgrad"), c);

		c.gridx		= 2;
		c.anchor 	= GridBagConstraints.FIRST_LINE_END;
		add(createButtonGroup("Tillagningstid"), c);

		c.gridx 	= 0;
		c.gridy		= 1;
		add(new LifeStylePanel(), c);
	}

	// update the user model with chosen value
	public void actionPerformed(ActionEvent e) {
		String s = e.getActionCommand();
		if(s.startsWith("cost")) {
			int desiredCost = Integer.parseInt(s.substring(s.length()-1));	
			//user.setDesiredCost(desiredCost);
		}
		else if(s.startsWith("difficulty")){
			int desiredDifficulty = Integer.parseInt(s.substring(s.length()-1));
			//user.setDesiredDifficulty(desiredDifficulty);
		}
		else if(s.startsWith("time")){
			int desiredTime = Integer.parseInt(s.substring(s.length()-1));
			//user.setDesiredTime(desiredTime);
		}
	}
}
