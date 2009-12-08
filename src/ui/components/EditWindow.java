package ui.components;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Frame;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
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
import javax.swing.JComponent;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.JWindow;
import javax.swing.ListSelectionModel;
import javax.swing.border.EtchedBorder;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;

import dataAccess.SubjectDA;
import dataObjects.Group;
import dataObjects.Session;
import dataObjects.Subject;


public class EditWindow extends JWindow implements WindowFocusListener{

	private static final long serialVersionUID = -1515580483915058715L;
	private ArrayList<ListSelectionListener> listSelectionListeners = new ArrayList<ListSelectionListener>();
	private Dimension minSize = new Dimension(190, 140);
	private Dimension maxSize = new Dimension(310, 280);

	public EditWindow(Frame owner) {
		super(owner);
		createGUI();
		setAlwaysOnTop(true);
		addWindowFocusListener(this);
		pack();
		minSize = getSize();
	}
	
	
	private void createGUI() {
		JPanel p = new JPanel();
		p.setLayout(new BorderLayout(0, 5));
		p.setBorder(BorderFactory.createLineBorder(Color.DARK_GRAY, 2));

		JPanel groupList = new GroupList();
		JPanel userList = new UserList();

		p.add(groupList, BorderLayout.PAGE_START);
		p.add(userList, BorderLayout.CENTER); 

		getContentPane().add(p);
	}


	public void addListSelectionListener(ListSelectionListener listener) {
		listSelectionListeners.add(listener);
	}

	public void windowGainedFocus(WindowEvent e) {
	}
	public void windowLostFocus(WindowEvent e) {
		setVisible(false);
	}



	private class GroupList extends JPanel implements Observer {

		private JList groupList;

		public GroupList() {
			setLayout(new BorderLayout());
			setBorder(BorderFactory.createEtchedBorder(EtchedBorder.LOWERED));

			Subject s = Session.getInstance().getUser();
			
			add(new JLabel("<html><h3>Sortera efter dina grupper</h3></html>"), BorderLayout.PAGE_START);

			ArrayList<Group> group = s.getGroups();
			/*s.createGroup("Familj");
			s.createGroup("Vänner");*/
			groupList = new JList(group.toArray());
			groupList.setFocusable(false);
			groupList.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
			add(groupList, BorderLayout.CENTER);
			add(createButtonPanel(), BorderLayout.PAGE_END);
			s.addObserver(this);

		}

		private JPanel createButtonPanel() {
			JPanel buttonPane = new JPanel();
			JButton cancelButton = new JButton("Avbryt");
			cancelButton.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent arg0) {
					EditWindow.this.setVisible(false);
				}
			});

			JButton sortButton = new JButton("Sortera");

			sortButton.addActionListener(new ActionListener() {

				public void actionPerformed(ActionEvent e) {
					Group searchGroup = (Group)groupList.getSelectedValue();
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
			buttonPane.setLayout(new BoxLayout(buttonPane, BoxLayout.LINE_AXIS));
			buttonPane.setBorder(BorderFactory.createEmptyBorder(0, 5, 5, 5));
			buttonPane.add(Box.createHorizontalGlue());
			buttonPane.add(cancelButton);
			buttonPane.add(Box.createRigidArea(new Dimension(5, 0)));
			buttonPane.add(sortButton);
			return buttonPane;

		}

		public void update(Observable arg0, Object arg1) {
			Subject s = Session.getInstance().getUser();
			ArrayList<Group> groups = s.getGroups();
			if(groups != null) {
				boolean added = groupList.getComponentCount() < groups.size(); 
				groupList.setListData(groups.toArray());
				groupList.repaint();
				groupList.revalidate();
				int size = 18;
				EditWindow.this.setSize(EditWindow.this.getWidth(), EditWindow.this.getHeight()+size);
				EditWindow.this.maxSize.height += size;
				EditWindow.this.minSize.height += size;

				
			}
		}
	}

	private class UserList extends JPanel {

		private Group tempGroup;
		private AddRemoveComponent subjectList;

		public UserList() {
			setLayout(new BorderLayout());
			setBorder(BorderFactory.createEtchedBorder(EtchedBorder.LOWERED));

			add(createToggleLink(), BorderLayout.PAGE_START);
			add(createList(), BorderLayout.CENTER);
			
			setPreferredSize(new Dimension(getWidth(), 20));
		}

		private JLabel createToggleLink() {
			JLabel title = new JLabel("Visa avancerat");
			title.setForeground(Color.GRAY);
			title.addMouseListener(new MouseListener() {
				public void mouseReleased(MouseEvent e) {
					Dimension d = EditWindow.this.getSize();
					JLabel field = (JLabel)e.getSource();
					if(field.getText().equals("Visa avancerat")) {
						field.setText("Göm avancerat");
						while(d.height < maxSize.height || d.width < maxSize.width) {
							if(d.height < maxSize.height) d.height += 2;
							if(d.width < maxSize.width)  d.width  += 2;
							EditWindow.this.setSize(d);
							try {
								Thread.sleep(2);
							} catch (InterruptedException e1) {
							}	
						}
					} else {
						field.setText("Visa avancerat");
						while(d.height > minSize.height || d.width > minSize.width) {
							if(d.height > minSize.height) d.height -= 2;
							if(d.width > minSize.width)  d.width  -= 2;
							EditWindow.this.setSize(d);
							try {
								Thread.sleep(2);
							} catch (InterruptedException e1) {
							}	
						}
					}
				}
				public void mousePressed(MouseEvent e) {
					((JComponent)e.getSource()).setForeground(Color.RED);
				}
				public void mouseExited(MouseEvent e) {
					((JComponent)e.getSource()).setForeground(Color.GRAY);
				}
				public void mouseEntered(MouseEvent e) {
					((JComponent)e.getSource()).setForeground(Color.BLACK);
				}
				public void mouseClicked(MouseEvent e) {
				}
			});
			return title;
		}

		private JPanel createList() {
			JPanel content = new JPanel();
			content.setLayout(new BorderLayout());
			JLabel desc = new JLabel("<html><h3>Skapa ny sökgrupp</h3><i>En sökgrupp är en temporär grupp som inte sparas, den kan du använda för att sortera recepten utifrån en ny grupp med människor</i></html>");
			desc.setPreferredSize(new Dimension(300, 100));
			content.add(desc, BorderLayout.PAGE_START);

			subjectList = new AddRemoveComponent();
			subjectList.addAddRemoveListener(new AddRemoveListener() {
				public void objectSelected(Object o) {
				}
				public void objectRemoved(Object o, boolean wasSelected) {
					int size = -32;
					EditWindow.this.setSize(EditWindow.this.getWidth(), EditWindow.this.getHeight()+size);
					EditWindow.this.maxSize.height += size;
					


					if(tempGroup != null) {
						tempGroup.removeUser((Subject)o);
					}
				}
				public void objectAdded(Object o) {
					int size = 32;
					
					EditWindow.this.setSize(EditWindow.this.getWidth(), EditWindow.this.getHeight()+size);
					EditWindow.this.maxSize.height += size;
					

					if(tempGroup == null) {
						Subject s = Session.getInstance().getUser();
						tempGroup = s.createGroup("Temporär sökgrupp");
					}
					tempGroup.addUser((Subject)o);
				}
			});

			SubjectDA sda = new SubjectDA();
			List subjects = sda.getAllSubjects();
			subjectList.setContents(subjects);
			content.add(subjectList, BorderLayout.CENTER);
			content.add(createButtonPanel(), BorderLayout.PAGE_END);
			return content;
		}

		private JPanel createButtonPanel() {
			JPanel buttonPane = new JPanel();
			JButton cancelButton = new JButton("Avbryt");
			cancelButton.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent arg0) {
					EditWindow.this.setVisible(false);
				}
			});

			JButton sortButton = new JButton("Sortera");
			sortButton.addActionListener(new ActionListener() {

				public void actionPerformed(ActionEvent e) {
					if(tempGroup != null) {
						for (ListSelectionListener listener : listSelectionListeners) {
							listener.valueChanged(new ListSelectionEvent(
									tempGroup,
									0,
									0,
									false
							));
						}
					}
				}
			});
			
			buttonPane.setLayout(new BoxLayout(buttonPane, BoxLayout.LINE_AXIS));
			buttonPane.setBorder(BorderFactory.createEmptyBorder(0, 5, 5, 5));
			buttonPane.add(Box.createHorizontalGlue());
			buttonPane.add(cancelButton);
			buttonPane.add(Box.createRigidArea(new Dimension(5, 0)));
			buttonPane.add(sortButton);
			return buttonPane;

		}
	}

}