package ui.components;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;
import java.util.ArrayList;

import javax.swing.BorderFactory;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.JWindow;
import javax.swing.border.TitledBorder;
import javax.swing.event.ListSelectionListener;

import dataObjects.Group;
import dataObjects.Session;
import dataObjects.Subject;


public class EditWindow extends JWindow implements FocusListener {

	private AddRemoveComponent subjectList;
	private JList groupList;
	
	private static EditWindow instance;
	public static EditWindow getInstance() {
		if(EditWindow.instance == null) {
			EditWindow.instance = new EditWindow();
		}
		return EditWindow.instance;
	}
	
	private EditWindow() {
		super();
		createGUI();
		setBackground(Color.BLACK);
		setAlwaysOnTop(true);
		addFocusListener(this);
		com.sun.awt.AWTUtilities.setWindowOpacity(this, 0.99f); //since Java 6 Update 10
		pack();
	}
	
	private void createGUI() {
		JPanel subjectPanel = new JPanel();
		subjectPanel.setLayout(new BorderLayout());
		groupList = createGroupList();
		subjectPanel.add(groupList, BorderLayout.NORTH);
		subjectList = new AddRemoveComponent();
		subjectPanel.add(subjectList, BorderLayout.SOUTH);
		TitledBorder tb = BorderFactory.createTitledBorder(
				BorderFactory.createLineBorder(Color.BLACK, 3),
				"Grupper", 
				TitledBorder.LEFT, 
				TitledBorder.CENTER, 
				new Font("Arial", Font.BOLD, 15));
		subjectPanel.setBorder(tb);
		this.getContentPane().add(subjectPanel);
	}
	
	private JList createGroupList() {
		Subject s = Session.getInstance().getUser();
		
		s.createGroup("Familj");
		s.createGroup("Vänner");
		s.createGroup("Fest");
		
		ArrayList<Group> groupNames = s.getGroups();
		JList list = new JList(groupNames.toArray());
		list.setPreferredSize(new Dimension(150, s.getGroups().size()*20));
		
		return list;
	}

	public void focusGained(FocusEvent arg0) {
		// TODO Auto-generated method stub
		
	}

	public void focusLost(FocusEvent arg0) {
		setVisible(false);
	}
	
	public void addListSelectionListener(ListSelectionListener listener) {
		groupList.addListSelectionListener(listener);
	}
	
}
