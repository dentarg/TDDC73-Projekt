package ui.components;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Frame;
import java.awt.event.WindowEvent;
import java.awt.event.WindowFocusListener;
import java.util.ArrayList;
import java.util.List;

import javax.swing.BorderFactory;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.JWindow;

import javax.swing.event.ListSelectionListener;

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
		setBackground(Color.GRAY);

		com.sun.awt.AWTUtilities.setWindowOpacity(this, 0.8f);
		pack();
	}

	private JPanel createHeader() {
		JPanel panel = new JPanel();
		panel.setBackground(Color.BLACK);
		
		
		JLabel p = new JLabel("Välj grupp");
		p.setFont(new Font("Arial", Font.BOLD, 15));
		p.setForeground(Color.BLUE);
		panel.add(p);
		
		return panel;
	}

	private void createGUI() {

		getContentPane().add(createHeader(), BorderLayout.NORTH);

		JPanel subjectPanel = new JPanel();
		subjectPanel.setLayout(new BorderLayout());
		groupList = createGroupList();
		
		subjectPanel.add(groupList, BorderLayout.CENTER);
		subjectList = new AddRemoveComponent();
		Subject s = Session.getInstance().getUser();
		List<Group> groups = s.getGroups();
		for (Group group : groups) {
			subjectList.add(group.getName());
		}
		subjectPanel.add(subjectList, BorderLayout.SOUTH); 

		subjectPanel.setBorder(BorderFactory.createLineBorder(Color.BLACK, 5));

		subjectPanel.setBackground(Color.BLACK);
		getContentPane().add(subjectPanel);
	}

	private JList createGroupList() {
		Subject s = Session.getInstance().getUser();

		s.createGroup("Familj");
		s.createGroup("Vänner");
		s.createGroup("Fest");

		ArrayList<Group> groupNames = s.getGroups();
		JList list = new JList(groupNames.toArray());
		list.setPreferredSize(new Dimension(150, s.getGroups().size()*20));
		list.setBackground(Color.BLACK);
		list.setForeground(Color.GRAY);
		list.setSelectionBackground(Color.BLACK);
		list.setSelectionForeground(Color.WHITE);
		list.setFocusable(false);
		return list;
	}

	public void addListSelectionListener(ListSelectionListener listener) {
		groupList.addListSelectionListener(listener);
	}

	public void windowGainedFocus(WindowEvent e) {

	}

	public void windowLostFocus(WindowEvent e) {
		setVisible(false);

	}

}
