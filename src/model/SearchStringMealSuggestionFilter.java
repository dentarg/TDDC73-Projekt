package model;

import java.util.ArrayList;
import java.util.Collections;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.swing.SwingWorker;

import model.sorters.ConstraintSorter;
import model.sorters.RecipeSorter;
import dataObjects.Recipe;

/**
 * Recipe filter that filters all recipes not matching a search string.
 * @author jernlas
 *
 */
public class SearchStringMealSuggestionFilter extends
      SwingWorker<ArrayList<Recipe>, Void> {

   /**
    * The pattern to search for.
    */
   private Pattern                            searchPattern = Pattern
                                                                  .compile(".*");
   
   /**
    * The original data
    */
   private final ArrayList<Recipe>            originalData;
   
   /**
    * The data that passed through the filter.
    */
   private final ArrayList<Recipe>            filteredData  = new ArrayList<Recipe>();
   
   /**
    * How to sort the result
    */
   private RecipeSorter                       sorter        = new ConstraintSorter();
   
   /**
    * Who listens to the result.
    */
   private final MealSuggestionFilterListener listener;

   /**
    * Creates a new Filter
    * @param originalList The original list to filter.
    * @param listener The listener
    */
   public SearchStringMealSuggestionFilter(
         final ArrayList<Recipe> originalList,
         final MealSuggestionFilterListener listener) {
      this.originalData = originalList;
      this.listener = listener;
   }

   /**
    * Cerate a new Filter from this one.
    * @param filterData The new search pattern.
    * @return The new filter.
    */
   public SearchStringMealSuggestionFilter copy(ArrayList<Recipe> filterData) {
      SearchStringMealSuggestionFilter filter = new SearchStringMealSuggestionFilter(
            filterData, listener);
      filter.setSearchPattern(searchPattern);
      filter.setSorter(sorter);
      return filter;
   }

   /*
    * (non-Javadoc)
    * @see javax.swing.SwingWorker#doInBackground()
    */
   @Override
   protected ArrayList<Recipe> doInBackground() throws Exception {
      for (Recipe ms : originalData) {
         if (!isItemFiltered(ms)) {
            filteredData.add(ms);
         }
      }
      if (getSorter().getComparator() != null) {
         Collections.sort(filteredData, getSorter().getComparator());
      }
      return filteredData;
   }

   /*
    * (non-Javadoc)
    * @see javax.swing.SwingWorker#done()
    */
   @Override
   protected void done() {
      super.done();
      listener.updateFilteredData(filteredData);
   }

   /**
    * Gets the sorter.
    * @return The sorter
    */
   public RecipeSorter getSorter() {
      return sorter;
   }

   /**
    * Shold this item be removed from the result.
    * @param ms The recipe to check
    * @return True if the recipe should not appear in the result.
    */
   public boolean isItemFiltered(final Recipe ms) {
      Matcher m = searchPattern.matcher(ms.getName());
      return !m.matches();
   }

   
   /**
    * Sets the search pattern to a new pattern.
    * @param searchPattern The new pattern.
    * @return The current filter after it has been modified.
    */
   public SearchStringMealSuggestionFilter setSearchPattern(
         Pattern searchPattern) {
      this.searchPattern = searchPattern;
      return this;
   }

   /**
    * Sets the search pattern to a new string. This results in a pattern *string*.
    * @param searchString The new string.
    * @return The current filter after it has been modified.
    */
   public SearchStringMealSuggestionFilter setSerachString(String searchString) {
      StringBuilder sb = new StringBuilder();
      sb.append(".*").append(searchString).append(".*");
      return setSearchPattern(Pattern.compile(sb.toString()));

   }

   /**
    * Sets the sorter for this filter.
    * @param sorter The sorter to set.
    */
   public void setSorter(RecipeSorter sorter) {
      this.sorter = sorter;
   }

}
