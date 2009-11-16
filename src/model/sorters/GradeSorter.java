package model.sorters;

import java.util.Comparator;
import java.util.Map;

import dataAccess.SubjectDA;
import dataObjects.Preference;
import dataObjects.Recipe;
import dataObjects.Subject;
/**
 * Sorts the recipes in order of how they are graded..
 * @author jernlas
 *
 */
public class GradeSorter implements RecipeSorter {

   /**
    * Compares grades for two recipes.
    * @author jernlas
    *
    */
   private class ValuationComparator implements Comparator<Recipe> {

      Map<String, Preference> m;

      public ValuationComparator() {
         SubjectDA s = new SubjectDA();
         // Always user 3 for testing
         Subject subject = s.getAllSubjects().get(3);
         m = subject.getPreferences();
      }

      /*
       * (non-Javadoc)
       * @see java.util.Comparator#compare(java.lang.Object, java.lang.Object)
       */
      @Override
      public int compare(Recipe r1, Recipe r2) {
         Preference p1 = m.get(r1.getName());
         Preference p2 = m.get(r2.getName());
         int v1 = (p1 == null ? 0 : p1.getRating());
         int v2 = (p2 == null ? 0 : p2.getRating());
         if (v1 < v2) {
            return 1;
         } else if (v1 > v2) {
            return -1;
         } else {
            return 0;
         }
      }

   }

   /*
    * (non-Javadoc)
    * @see model.sorters.RecipeSorter#getComparator()
    */
   @Override
   public Comparator<Recipe> getComparator() {
      return new ValuationComparator();
   }

   /*
    * (non-Javadoc)
    * @see java.lang.Object#toString()
    */
   @Override
   public String toString() {
      return "Bästa betyg först";
   }

}
