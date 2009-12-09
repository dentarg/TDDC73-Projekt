package ui.panels.profile;

import java.awt.Color;

import java.awt.Dimension;
import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;

import javax.swing.BorderFactory;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.border.EtchedBorder;
import javax.swing.border.LineBorder;
import javax.swing.border.TitledBorder;

import dataObjects.Session;

import ui.components.AddRemoveComponent;
import ui.components.AddRemoveListener;

public class GroupPanel extends JPanel {
	
	private AddRemoveComponent groupList;
	private AddRemoveComponent subjectList;
	
	private JPanel groupPanel;
	private JPanel subjectPanel;
	
	private AddRemoveListener groupListener = new groupListener();
	private AddRemoveListener subjectListener = new subjectListener();
	
	private dataObjects.Session session = Session.getInstance();
	private dataObjects.Subject user = session.getUser();
	
	private String currentGroup = "Ingen grupp vald";
	
    class groupListener implements AddRemoveListener {

		public void objectAdded(Object o) {
			currentGroup = o.toString();
			user.createGroup(currentGroup);
			subjectList.removeAddRemoveListener(subjectListener);
			subjectList.clearSelected();
			for (dataObjects.Subject subject : user.getGroup(currentGroup).getMembers()) {
				subjectList.setSelected(subject);
			}
	        TitledBorder tb = BorderFactory.createTitledBorder(
	        		BorderFactory.createLineBorder(Color.GRAY),
	        		currentGroup,
	        		TitledBorder.LEFT,
	        		TitledBorder.CENTER,
	        		new Font("Arial", Font.BOLD, 15)
	        );
	        subjectPanel.setBorder(tb);
			subjectList.setVisible(true);
			subjectPanel.revalidate();
			subjectList.addAddRemoveListener(subjectListener);
		}

		public void objectRemoved(Object o, boolean wasSelected) {
			user.removeGroup(o.toString());
			if (wasSelected) {
				subjectList.removeAddRemoveListener(subjectListener);
				subjectList.clearSelected();
		        TitledBorder tb = BorderFactory.createTitledBorder(
		        		BorderFactory.createLineBorder(Color.GRAY),
		        		"Ingen grupp vald",
		        		TitledBorder.LEFT,
		        		TitledBorder.CENTER,
		        		new Font("Arial", Font.BOLD, 15)
		        );
		        subjectPanel.setBorder(tb);
				subjectList.setVisible(false);
				subjectPanel.revalidate();
				subjectList.addAddRemoveListener(subjectListener);
			}
		}

		public void objectSelected(Object o) {
			currentGroup = o.toString();
			subjectList.removeAddRemoveListener(subjectListener);
			subjectList.clearSelected();
			for (dataObjects.Subject subject : user.getGroup(currentGroup).getMembers()) {
				subjectList.setSelected(subject);
			}
	        TitledBorder tb = BorderFactory.createTitledBorder(
	        		BorderFactory.createLineBorder(Color.GRAY),
	        		currentGroup,
	        		TitledBorder.LEFT,
	        		TitledBorder.CENTER,
	        		new Font("Arial", Font.BOLD, 15)
	        );
	        subjectPanel.setBorder(tb);
	        subjectList.setVisible(true);
			subjectPanel.revalidate();
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
    
	public GroupPanel() {
		setPreferredSize(new Dimension(800, 600));
		setLayout(new GridBagLayout());
		
		groupList = new AddRemoveComponent(false, true);
		subjectList = new AddRemoveComponent(false, false);
		
		groupPanel = createGroupPanel();
		subjectPanel = createSubjectPanel();
		
	    GridBagConstraints c = new GridBagConstraints();
	    c.fill = GridBagConstraints.BOTH;
	    c.weightx = 0.5;
	    c.weighty = 0.5;
	    c.gridx = 0;
	    c.gridy = 0;
	    c.insets = new Insets(5, 5, 5, 5);
	    add(groupPanel, c);
	    
	    c.gridx = 1;
	    add(subjectPanel, c);
	    
		subjectList.setContents(session.getSubjects());
		
	    groupList.addAddRemoveListener(groupListener);
	    subjectList.addAddRemoveListener(subjectListener);
	}
	
	public JPanel createGroupPanel() {
		JPanel groupPanel = new JPanel();		
		groupPanel.setLayout(new GridBagLayout());
		groupPanel.setPreferredSize(new Dimension(300, 600));
		
		JLabel groupLabel = new JLabel("<html>Här kan du lägga till " +
				"grupper igenom att skriva in ett namn, eller ändra " +
				"existerande grupper och ta bort dem.</html>");
        groupLabel.setVerticalAlignment(JLabel.CENTER);
        groupLabel.setHorizontalAlignment(JLabel.CENTER);
        LineBorder lb = (LineBorder) BorderFactory.createLineBorder(Color.gray);
        groupLabel.setBorder(lb);
        groupLabel.setPreferredSize(new Dimension(300, 50));
		
        GridBagConstraints c = new GridBagConstraints();
        c.gridx = 0;
        c.gridy = 0;
        c.weightx = 1.0;
        c.weighty = 0;
        c.fill = GridBagConstraints.BOTH;
        c.insets = new Insets(5, 5, 5, 5);
        groupPanel.add(groupLabel, c);

        c = new GridBagConstraints();
        c.gridx = 0;
        c.gridy = 1;
        c.weightx = 1.0;
        c.weighty = 1.0;
        c.insets = new Insets(5, 5, 5, 5);
        c.anchor = GridBagConstraints.FIRST_LINE_START;
        c.gridheight = 3;
        groupPanel.add(groupList, c);
		
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
	
	public JPanel createSubjectPanel() {
		JPanel newPanel = new JPanel();
		newPanel.setLayout(new GridBagLayout());
		newPanel.setPreferredSize(new Dimension(300, 600));
		
		JLabel subjectLabel = new JLabel("<html>Här kan du lägga till och " +
				"ta bort användare ur gruppen.</html>");
		subjectLabel.setVerticalAlignment(JLabel.CENTER);
		subjectLabel.setHorizontalAlignment(JLabel.CENTER);
        LineBorder lb = (LineBorder) BorderFactory.createLineBorder(Color.gray);
        subjectLabel.setBorder(lb);
        subjectLabel.setPreferredSize(new Dimension(300, 50));
		
        GridBagConstraints c = new GridBagConstraints();
        c.gridx = 0;
        c.gridy = 0;
        c.weightx = 1.0;
        c.weighty = 0;
        c.fill = GridBagConstraints.BOTH;
        c.insets = new Insets(5, 5, 5, 5);
        newPanel.add(subjectLabel, c);

        c = new GridBagConstraints();
        c.gridx = 0;
        c.insets = new Insets(5, 5, 5, 5);
        c.anchor = GridBagConstraints.FIRST_LINE_START;
        c.gridy = 1;
        c.weightx = 1;
        c.weighty = 1;
        c.gridheight = 3;
        newPanel.add(subjectList, c);
        subjectList.setVisible(false);
		
        TitledBorder tb = BorderFactory.createTitledBorder(
        		BorderFactory.createLineBorder(Color.GRAY),
        		currentGroup,
        		TitledBorder.LEFT,
        		TitledBorder.CENTER,
        		new Font("Arial", Font.BOLD, 15)
        );
        newPanel.setBorder(tb);
        
        return newPanel;
	}
}