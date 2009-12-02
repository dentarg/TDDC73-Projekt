package ui.panels.profile;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;

import javax.swing.BorderFactory;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.TitledBorder;

import ui.MainFrame;
import ui.components.AddRemoveComponent;
import ui.components.AddRemoveListener;



public class GroupPanel extends JPanel {
	
	private AddRemoveComponent groupList;
	private AddRemoveComponent subjectList;
	
	// Listener that updates the subjectList depending on what group is chosen.
    class groupListener implements AddRemoveListener {

		public void objectAdded(Object o) {
			// Ignore.
		}

		public void objectRemoved(Object o) {
			// Ignore.
		}

		public void objectSelected(Object o) {
			// Show the selected groups members.
		}

		public void selectedObjectRemoved(Object o) {
			// Stop showing the removed groups members.
		}
    }
	
	// Create grouppanel containing an AddRemoveComponent groupList.
	public JPanel createGroupPanel() {
		JPanel groupPanel = new JPanel();		
		groupPanel.setLayout(new BorderLayout());
		
		groupList = new AddRemoveComponent();
		
		groupPanel.add(groupList, BorderLayout.NORTH);
		
        TitledBorder tb = BorderFactory.createTitledBorder(
        		BorderFactory.createLineBorder(Color.GRAY),
        		"Groups",
        		TitledBorder.LEFT,
        		TitledBorder.CENTER,
        		new Font("Arial", Font.BOLD, 15)
        );
        groupPanel.setBorder(tb);
        
        return groupPanel;
	}
	
	// Create subjectpanel containing an AddRemoveComponent subjectList.
	public JPanel createSubjectPanel() {
		JPanel subjectPanel = new JPanel();
		subjectPanel.setLayout(new BorderLayout());
		
		subjectList = new AddRemoveComponent();
		
		subjectPanel.add(subjectList, BorderLayout.NORTH);
		
        TitledBorder tb = BorderFactory.createTitledBorder(
        		BorderFactory.createLineBorder(Color.GRAY),
        		"Members",
        		TitledBorder.LEFT,
        		TitledBorder.CENTER,
        		new Font("Arial", Font.BOLD, 15)
        );
        subjectPanel.setBorder(tb);
        
        return subjectPanel;
	}

	// Create the main grouppanel, containing smaller group- and subjectpanels.
	public GroupPanel() {
		setPreferredSize(new Dimension(800, 600));
		setLayout(new GridBagLayout());
	    GridBagConstraints c = new GridBagConstraints();
	    
	    c.fill = GridBagConstraints.BOTH;
	    c.weightx = 0.5;
	    c.weighty = 0.5;
	    c.gridx = 0;
	    c.gridy = 0;
	    c.insets = new Insets(5, 5, 5, 5);
	    add(createGroupPanel(), c);
	    
	    c.gridx = 1;
	    add(createSubjectPanel(), c);
	    
	    groupList.addAddRemoveListener(new groupListener());
	}
}
