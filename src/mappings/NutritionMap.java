/*
 * VariationMap.java
 * 
 * Created on den 6 juni 2005, 22:25
 */

package mappings;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.ListIterator;
import java.util.Map;
import java.util.Set;
import java.util.TreeMap;

import dataObjects.MealDate;
import dataObjects.MyDate;
import dataObjects.requirements.NutritionRequirement;

/**
 * Mapping between nutrition requirements and different dates.
 */
public class NutritionMap extends
      TreeMap<NutritionRequirement, ArrayList<MealDate>> {

   private static final long serialVersionUID = 415340064487524094L;

   public NutritionMap() {
   }

   /**
    * Check if a date is in a list.
    * 
    * @param date
    *           - The date to look for.
    * @param dateList
    *           - The list to search.
    * @return boolean - Sant om listan inneh�ller datumet.
    */
   public boolean isDateAdded(MealDate date, ArrayList<MealDate> dateList) {
      for (ListIterator<MealDate> datesIter = dateList.listIterator(); datesIter
            .hasNext();) {
         MealDate mealDate = datesIter.next();

         MyDate inDate = new MyDate(date.toString());
         MyDate listDate = new MyDate(mealDate.toString());

         if (inDate.equals(listDate)) {
            return true;
         }
      }
      return false;
   }

   /**
    * Removes a date from every mapping exept the special one.
    * 
    * @param date
    *           The date to remove
    * @param specVariation
    *           The mapping to keep
    * @param variationMap
    *           The mappings to purge.
    */
   public void removeDate(MealDate date, int specVariation,
         Map<Integer, ArrayList<MealDate>> variationMap) {
      Set<Map.Entry<Integer, ArrayList<MealDate>>> variationSet = variationMap
            .entrySet();
      for (Iterator<Map.Entry<Integer, ArrayList<MealDate>>> iter = variationSet
            .iterator(); iter.hasNext();) {
         Map.Entry<Integer, ArrayList<MealDate>> variationEntry = iter.next();
         int variation = variationEntry.getKey();
         ArrayList<MealDate> dateList = variationEntry.getValue();
         if (isDateAdded(date, dateList) && (specVariation != variation)) {
            dateList.remove(date);
         }
      }
   }
}
