package ui.components;

import java.awt.Point;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JComboBox;
import javax.swing.JList;
import javax.swing.event.AncestorEvent;
import javax.swing.event.AncestorListener;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;

import ui.MainFrame;

import model.MealSuggestionListModel;
import model.SearchStringMealSuggestionFilter;
import model.sorters.AlphabeticSorter;
import model.sorters.ConstraintSorter;
import model.sorters.GradeSorter;
import model.sorters.GroupSorter;
import model.sorters.IgnoreSorter;
import model.sorters.ProfileSorter;
import model.sorters.RecipeSorter;
import dataObjects.Group;
import dataObjects.requirements.RequirementManager;

/**
 * Selects the sort method to use when filtering recipes.
 * 
 * @author jernlas
 */
public class SortMethodSelector extends JComboBox implements ActionListener{

	private static final long             serialVersionUID = 1L;

	private EditWindow groupSelectionWindow;
	/**
	 * All available sorters.
	 */
	private static final Object[]         sorters          = {
		new IgnoreSorter(), new AlphabeticSorter(), new GradeSorter(),
		new ConstraintSorter(), new ProfileSorter(), new GroupSorter()};

	/**
	 * The list model to sort.
	 */
	private final MealSuggestionListModel listModel;


	/**
	 * Create a new sort model selector.
	 * @param model The model to sort.
	 */
	public SortMethodSelector(MealSuggestionListModel model) {
		super(sorters);
		setSelectedIndex(3);
		listModel = model;
		RequirementManager.setModel(model);
		addActionListener(this);
	}

	
	private void createGroupSelectionWindow() {
		groupSelectionWindow = new EditWindow(MainFrame.mainFrame);
		addAncestorListener(new AncestorListener() {
			public void ancestorRemoved(AncestorEvent event) {
			}
			public void ancestorMoved(AncestorEvent event) {
				Point p = getLocationOnScreen();
				p.x = p.x + getWidth();
				groupSelectionWindow.setLocation(p);
			}
			public void ancestorAdded(AncestorEvent event) {
			}
		});
		groupSelectionWindow.addListSelectionListener(new ListSelectionListener() {

			public void valueChanged(ListSelectionEvent e) {
				JList list = (JList)e.getSource();

				if(!list.getValueIsAdjusting()) {
					SearchStringMealSuggestionFilter f = listModel
					.copyRestrictingLastFilter();
					GroupSorter gs = (GroupSorter)getSelectedItem();

					gs.setGroup((Group)list.getSelectedValue());
					f.setSorter(gs);
					listModel.applyFilter(f);
				}
			}
		});
		Point p = getLocationOnScreen();
		p.x += this.getWidth() + 5;
		p.y -= 20;
		groupSelectionWindow.setLocation(p);
		groupSelectionWindow.toFront();
		groupSelectionWindow.requestFocusInWindow();
	}
	
	public void actionPerformed(ActionEvent e) {
		if(getSelectedIndex() == 5) {
			if(groupSelectionWindow == null) {
				createGroupSelectionWindow();
			}
			if(!groupSelectionWindow.isVisible()) {
				groupSelectionWindow.setVisible(true);
			}
		} else {
			SearchStringMealSuggestionFilter f = listModel
				.copyRestrictingLastFilter();
			f.setSorter((RecipeSorter) getSelectedItem());
			listModel.applyFilter(f);
		}
	}

}
