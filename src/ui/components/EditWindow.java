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
import javax.swing.JSeparator;
import javax.swing.JWindow;

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
		setBackground(Color.GRAY);

		com.sun.awt.AWTUtilities.setWindowOpacity(this, 0.8f);
		pack();
	}

	private JPanel createHeader(String title) {
		JPanel panel = new JPanel();
		panel.setBackground(Color.BLACK);
		
		JLabel p = new JLabel(title);
		p.setFont(new Font("Arial", Font.BOLD, 15));
		p.setForeground(Color.BLUE);
		panel.add(p);
		
		return panel;
	}

	private void createGUI() {

		getContentPane().add(createHeader("Välj grupp"), BorderLayout.NORTH);

		JPanel subjectPanel = new JPanel();
		subjectPanel.setLayout(new BorderLayout());
		
		groupList = createGroupList();
		subjectPanel.add(groupList, BorderLayout.NORTH);
		
		subjectPanel.add(new JSeparator());
		subjectPanel.add(createHeader("Sök efter personer"));

		subjectList = new AddRemoveComponent();
		subjectList.addAddRemoveListener(new AddRemoveListener() {
			
			@Override
			public void objectSelected(Object o) {
				
			}
			
			@Override
			public void objectRemoved(Object o, boolean wasSelected) {
				Dimension d = getSize();
				d.height -= 34;
				setSize(d);
			}
			
			@Override
			public void objectAdded(Object o) {
				Dimension d = getSize();
				d.height += 34;
				setSize(d);
			}
		});
		SubjectDA sda = new SubjectDA();
		List subjects = sda.getAllSubjects();
		subjectList.setContents(subjects);
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

		ArrayList<Group> group = s.getGroups();
		JList list = new JList(group.toArray());
		
		list.setPreferredSize(new Dimension(150, group.size()*20));
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

	public void addAddRemoveListener(AddRemoveListener listener) {
		subjectList.addAddRemoveListener(listener);
	}
	public void windowGainedFocus(WindowEvent e) {

	}
	

	public void windowLostFocus(WindowEvent e) {
		setVisible(false);

	}

}
