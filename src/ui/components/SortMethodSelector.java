package ui.components;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JComboBox;

import model.MealSuggestionListModel;
import model.SearchStringMealSuggestionFilter;
import model.sorters.AlphabeticSorter;
import model.sorters.ConstraintSorter;
import model.sorters.GradeSorter;
import model.sorters.IgnoreSorter;
import model.sorters.RecipeSorter;
import dataObjects.requirements.RequirementManager;

/**
 * Selects the sort method to use when filtering recipes.
 * 
 * @author jernlas
 */
public class SortMethodSelector extends JComboBox implements ActionListener {

   private static final long             serialVersionUID = 1L;

   /**
    * All available sorters.
    */
   private static final Object[]         sorters          = {
         new IgnoreSorter(), new AlphabeticSorter(), new GradeSorter(),
         new ConstraintSorter()                          };

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
      addActionListener(new ActionListener() {
         @Override
         public void actionPerformed(ActionEvent e) {
            SearchStringMealSuggestionFilter f = listModel
                  .copyRestrictingLastFilter();
            f.setSorter((RecipeSorter) getSelectedItem());
            listModel.applyFilter(f);
         }
      });
   }

}
