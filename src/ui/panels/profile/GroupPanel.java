package ui.panels.profile;

import java.awt.Color;

import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.util.List;

import javax.swing.BorderFactory;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.border.LineBorder;
import javax.swing.border.TitledBorder;

import dataAccess.SubjectDA;
import dataObjects.Session;

import ui.components.AddRemoveComponent;
import ui.components.AddRemoveListener;



public class GroupPanel extends JPanel {
	
	private AddRemoveComponent groupList;
	private AddRemoveComponent subjectList;
	
	private AddRemoveListener groupListener = new groupListener();
	private AddRemoveListener subjectListener = new subjectListener();
	
	private SubjectDA sda = new SubjectDA();
	private dataObjects.Session session = Session.getInstance();
	private dataObjects.Subject user = session.getUser();
	
	private String currentGroup = null;
	
    class groupListener implements AddRemoveListener {

		public void objectAdded(Object o) {
			currentGroup = o.toString();
			user.createGroup(currentGroup);
			subjectList.removeAddRemoveListener(subjectListener);
			subjectList.clearSelected();
			subjectList.setSelected(user.getGroup(currentGroup).getMembers());
			subjectList.addAddRemoveListener(subjectListener);
		}

		public void objectRemoved(Object o, boolean wasSelected) {
			user.removeGroup(o.toString());
			if (wasSelected) {
				subjectList.removeAddRemoveListener(subjectListener);
				subjectList.clearSelected();
				subjectList.addAddRemoveListener(subjectListener);
			}
		}

		public void objectSelected(Object o) {
			currentGroup = o.toString();
			subjectList.removeAddRemoveListener(subjectListener);
			subjectList.clearSelected();
			subjectList.setSelected(user.getGroup(currentGroup).getMembers());
			subjectList.addAddRemoveListener(subjectListener);
		}
    }
    
    class subjectListener implements AddRemoveListener {

		public void objectAdded(Object o) {
			user.getGroup(currentGroup).addUser((dataObjects.Subject) o);
		}

		public void objectRemoved(Object o, boolean wasSelected) {
			user.getGroup(currentGroup).removeUser((dataObjects.Subject) o);
		}

		public void objectSelected(Object o) {
			// Ignore.
		}
    }
	
	// Create grouppanel containing an AddRemoveComponent groupList.
	public JPanel createGroupPanel() {
		JPanel groupPanel = new JPanel();		
		groupPanel.setLayout(new FlowLayout());
		groupPanel.setPreferredSize(new Dimension(300, 600));
		
		JLabel groupLabel = new JLabel("Lägg till och ta bort grupper, välj vilken grupp du vill redigera.");
        groupLabel.setVerticalAlignment(JLabel.CENTER);
        groupLabel.setHorizontalAlignment(JLabel.CENTER);
        LineBorder lb = (LineBorder) BorderFactory.createLineBorder(Color.gray);
        groupLabel.setBorder(lb);
        groupLabel.setPreferredSize(new Dimension(300, 50));
        
		groupList = new AddRemoveComponent(false, true);
		
		groupPanel.add(groupLabel);
		groupPanel.add(groupList);
		
        TitledBorder tb = BorderFactory.createTitledBorder(
        		BorderFactory.createLineBorder(Color.GRAY),
        		"Grupper",
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
		subjectPanel.setLayout(new FlowLayout());
		subjectPanel.setPreferredSize(new Dimension(300, 600));
		
		JLabel subjectLabel = new JLabel("Lägg till och ta bort medlemmar ur gruppen.");
		subjectLabel.setVerticalAlignment(JLabel.CENTER);
		subjectLabel.setHorizontalAlignment(JLabel.CENTER);
        LineBorder lb = (LineBorder) BorderFactory.createLineBorder(Color.gray);
        subjectLabel.setBorder(lb);
        subjectLabel.setPreferredSize(new Dimension(300, 50));
		
		subjectList = new AddRemoveComponent(false, false);
		
		subjectPanel.add(subjectLabel);
		subjectPanel.add(subjectList);
		
        TitledBorder tb = BorderFactory.createTitledBorder(
        		BorderFactory.createLineBorder(Color.GRAY),
        		"Medlemmar",
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
	    
		List subjects = sda.getAllSubjects();
		subjectList.setContents(subjects);
		
	    groupList.addAddRemoveListener(groupListener);
	    subjectList.addAddRemoveListener(subjectListener);
	}
}