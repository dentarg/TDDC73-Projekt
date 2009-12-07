package ui.components;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Frame;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowEvent;
import java.awt.event.WindowFocusListener;
import java.util.ArrayList;
import java.util.List;
import java.util.Observable;
import java.util.Observer;

import javax.swing.BorderFactory;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.JSeparator;
import javax.swing.JWindow;
import javax.swing.ListSelectionModel;
import javax.swing.SwingConstants;

import javax.swing.border.EtchedBorder;
import javax.swing.border.TitledBorder;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;

import dataAccess.SubjectDA;
import dataObjects.Group;
import dataObjects.Session;
import dataObjects.Subject;


public class EditWindow extends JWindow implements WindowFocusListener, Observer {

	/**
	 * 
	 */
	private static final long serialVersionUID = -1515580483915058715L;
	private AddRemoveComponent subjectList;
	private JList groupList;
	private ArrayList<ListSelectionListener> listSelectionListeners = new ArrayList<ListSelectionListener>();

	private Group tempGroup;
	private Group searchGroup;
	
	public EditWindow(Frame owner) {
		super(owner);
		createGUI();
		setAlwaysOnTop(true);
		addWindowFocusListener(this);
		//com.sun.awt.AWTUtilities.setWindowOpacity(this, 0.8f);
		Subject s = Session.getInstance().getUser();
		s.addObserver(this);
		pack();
	}

	private JPanel createHeader(String title) {
		JPanel panel = new JPanel();

		JLabel p = new JLabel(title);
		p.setFont(new Font("Arial", Font.BOLD, 15));
		p.setForeground(Color.DARK_GRAY);
		//panel.setAlignmentX(SwingConstants.LEFT);
		panel.add(p);

		return panel;
	}

	private JPanel createGroupList() {
		JPanel p = new JPanel();
		p.setLayout(new BorderLayout());
		p.setBorder(BorderFactory.createEtchedBorder(EtchedBorder.LOWERED));

		p.add(new JLabel("<html><h3>Sortera efter dina grupper</h3></html>"), BorderLayout.PAGE_START);

		Subject s = Session.getInstance().getUser();
		ArrayList<Group> group = s.getGroups();
		s.createGroup("Familj");
		s.createGroup("Vänner");
		groupList = new JList(group.toArray());
		groupList.setFocusable(false);
		groupList.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		groupList.addListSelectionListener(new ListSelectionListener() {
			
			public void valueChanged(ListSelectionEvent arg0) {
				if(!groupList.getValueIsAdjusting())
				searchGroup = (Group)groupList.getSelectedValue();
			}
		});
		p.add(groupList, BorderLayout.CENTER);

		return p;
	}

	private JPanel createUserList() {
		JPanel p = new JPanel();
		p.setLayout(new BorderLayout());
		p.setBorder(BorderFactory.createEtchedBorder(EtchedBorder.LOWERED));

		JLabel info = new JLabel("<html><h3>Skapa ny sökgrupp</h3><i>En sökgrupp är en temporär grupp som inte sparas, den kan du använda för att sortera recepten utifrån en ny grupp med människor</i></html>");
		info.setPreferredSize(new Dimension(300, 100));
		p.add(info, BorderLayout.PAGE_START);

		subjectList = new AddRemoveComponent();
		subjectList.addAddRemoveListener(new AddRemoveListener() {
			public void objectSelected(Object o) {
				groupList.removeSelectionInterval(0, groupList.getComponentCount());
			}
			public void objectRemoved(Object o, boolean wasSelected) {
				Dimension d = getSize();
				d.height -= 32;
				setSize(d);
				if(tempGroup != null) {
					tempGroup.removeUser((Subject)o);
					searchGroup = tempGroup;
					groupList.removeSelectionInterval(0, groupList.getComponentCount());
				}
			}
			public void objectAdded(Object o) {
				Dimension d = getSize();
				d.height += 32;
				setSize(d);
				if(tempGroup == null) {
					Subject s = Session.getInstance().getUser();
					tempGroup = s.createGroup("tempSearchgroup");
				}
				tempGroup.addUser((Subject)o);
				searchGroup = tempGroup;
				groupList.removeSelectionInterval(0, groupList.getComponentCount());
			}
		});
		SubjectDA sda = new SubjectDA();
		List subjects = sda.getAllSubjects();
		subjectList.setContents(subjects);

		p.add(subjectList, BorderLayout.CENTER);
		return p;
	}

	private JPanel createButtonPanel() {
		JPanel buttonPane = new JPanel();

		JButton cancelButton = new JButton("Avbryt");
		cancelButton.addActionListener(new ActionListener() {
			
			public void actionPerformed(ActionEvent arg0) {
				setVisible(false);
			}
		});
		
		final JButton setButton = new JButton("Sortera");
		setButton.requestFocusInWindow();
		setButton.addActionListener(new ActionListener() {
			
			public void actionPerformed(ActionEvent e) {
				System.out.println("tmpgroup: " + tempGroup);
				System.out.println("Grupp att sortera efter: " + searchGroup);
				if(searchGroup != null) {
					for (ListSelectionListener listener : listSelectionListeners) {
						listener.valueChanged(new ListSelectionEvent(
							searchGroup,
							groupList.getSelectedIndex(),
							groupList.getSelectedIndex(),
							false
						));
					}
				}
			}
		});
		getRootPane().setDefaultButton(setButton);
		
		buttonPane.setLayout(new BoxLayout(buttonPane, BoxLayout.LINE_AXIS));
		buttonPane.setBorder(BorderFactory.createEmptyBorder(0, 5, 5, 5));
		buttonPane.add(Box.createHorizontalGlue());
		buttonPane.add(cancelButton);
		buttonPane.add(Box.createRigidArea(new Dimension(5, 0)));
		buttonPane.add(setButton);
		
		return buttonPane;

	}
	private void createGUI() {
		JPanel p = new JPanel();
		p.setLayout(new BorderLayout(0, 5));
		p.setBorder(BorderFactory.createLineBorder(Color.DARK_GRAY, 2));

		p.add(createGroupList(), BorderLayout.PAGE_START);
		p.add(createUserList(), BorderLayout.LINE_START); 

		p.add(createButtonPanel(), BorderLayout.PAGE_END);

		getContentPane().add(p);
	}

	public void updateGroupList() {
		Subject s = Session.getInstance().getUser();
		ArrayList<Group> group = s.getGroups();
		groupList.setListData(group.toArray());
		groupList.repaint();
		groupList.revalidate();
		System.out.println("EditWindow.updateGroupList: Updated grouplist, user is now in groups: " + group);
	}

	public void addListSelectionListener(ListSelectionListener listener) {
		listSelectionListeners.add(listener);
	}

	public void windowGainedFocus(WindowEvent e) {
	}
	public void windowLostFocus(WindowEvent e) {
		setVisible(false);
	}

	public void update(Observable arg0, Object arg1) {
		if(!isVisible()) {
			updateGroupList();
		}
	}

}
