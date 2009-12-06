package ui.components;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Frame;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.event.WindowEvent;
import java.awt.event.WindowFocusListener;
import java.util.ArrayList;
import java.util.List;

import javax.swing.BorderFactory;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.JSeparator;
import javax.swing.JWindow;

import javax.swing.border.TitledBorder;
import javax.swing.event.ListSelectionListener;

import dataAccess.SubjectDA;
import dataObjects.Group;
import dataObjects.Session;
import dataObjects.Subject;


public class EditWindow extends JWindow implements WindowFocusListener {

	/**
	 * 
	 */
	private static final long serialVersionUID = -1515580483915058715L;
	private AddRemoveComponent subjectList;
	private JList groupList;
	
	public EditWindow(Frame owner) {
		super(owner);
		createGUI();
		setAlwaysOnTop(true);
		addWindowFocusListener(this);
		//com.sun.awt.AWTUtilities.setWindowOpacity(this, 0.8f);
		pack();
	}

	private JPanel createHeader(String title) {
		JPanel panel = new JPanel();
		
		JLabel p = new JLabel(title);
		p.setFont(new Font("Arial", Font.BOLD, 15));
		p.setForeground(Color.BLUE);
		panel.add(p);
		
		return panel;
	}

	private void createGUI() {

	
		JPanel subjectPanel = new JPanel();
		subjectPanel.setLayout(new GridBagLayout());
		GridBagConstraints c = new GridBagConstraints();
		c.gridx = 1;
		c.gridy = 1;
		subjectPanel.add(createHeader("Dina grupper"), c);
	
		groupList = createGroupList();
		c.gridy++;
		subjectPanel.add(groupList, c);

		c.gridy++;
		subjectPanel.add(new JSeparator(), c);
		c.gridy++;
		subjectPanel.add(createHeader("Sök efter personer"), c);

		subjectList = new AddRemoveComponent();
		subjectList.addAddRemoveListener(new AddRemoveListener() {
			
			public void objectSelected(Object o) {
				
			}
			
			public void objectRemoved(Object o, boolean wasSelected) {
				Dimension d = getSize();
				d.height -= 32;
				setSize(d);
			}
			
			public void objectAdded(Object o) {
				Dimension d = getSize();
				d.height += 32;
				setSize(d);
			}
		});
		SubjectDA sda = new SubjectDA();
		List subjects = sda.getAllSubjects();
		subjectList.setContents(subjects);
		c.gridy++;
		subjectPanel.add(subjectList, c); 
		subjectPanel.setBorder(BorderFactory.createTitledBorder(
				BorderFactory.createLineBorder(Color.BLACK, 2),
				"Välj sökgrupp",
				TitledBorder.CENTER,
				TitledBorder.CENTER,
				new Font("Arial", Font.BOLD, 15),
				Color.BLUE));
				
				
		getContentPane().add(subjectPanel);
	}

	private JList createGroupList() {
		Subject s = Session.getInstance().getUser();
		ArrayList<Group> group = s.getGroups();
		
		JList list = new JList(group.toArray());
		list.setMinimumSize(new Dimension(250, group.size()*20));
		list.setPreferredSize(new Dimension(250, group.size()*20));
		list.setFocusable(false);
		return list;
	}
	
	public void updateGroupList() {
		Subject s = Session.getInstance().getUser();
		ArrayList<Group> group = s.getGroups();
		groupList.setListData(group.toArray());
		groupList.repaint();
	}

	
	public void addListSelectionListener(ListSelectionListener listener) {
		groupList.addListSelectionListener(listener);
	}

	public void addAddRemoveListener(AddRemoveListener listener) {
		subjectList.addAddRemoveListener(listener);
	}
	public void windowGainedFocus(WindowEvent e) {

	}
	

	public void windowLostFocus(WindowEvent e) {
		setVisible(false);

	}

}
