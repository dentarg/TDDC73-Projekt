/*
 * DifficultyMap.java
 * 
 * Created on den 6 juni 2005, 22:05
 */

package mappings;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.ListIterator;
import java.util.Map;
import java.util.Set;
import java.util.TreeMap;

import dataObjects.MealDate;
import dataObjects.MyDate;
import dataObjects.Settings;

/**
 * Mapping of ingredients to avoid for special meals, and a list of these meals
 */
public class SpecIngredientsMap extends TreeMap<String, ArrayList<MealDate>> {

   private static final long   serialVersionUID = 7794467816639008695L;
   /** Mapping between special meals anf their settings. */
   HashMap<MealDate, Settings> mealSettingsMap;

   public SpecIngredientsMap() {
   }

   /**
    * Create a new SpecIngredientsMap-object.
    * 
    * @param ingr
    *           True if this mapping is for ingredients, false for categories.
    * @param mealSettingsMap
    *           Mapping between special dates and theis settings.
    */
   public SpecIngredientsMap(boolean ingr,
         HashMap<MealDate, Settings> mealSettingsMap) {
      this.mealSettingsMap = mealSettingsMap;
      Set<Map.Entry<MealDate, Settings>> mealSettingsSet = mealSettingsMap
            .entrySet();

      for (Iterator<Map.Entry<MealDate, Settings>> iter = mealSettingsSet
            .iterator(); iter.hasNext();) {
         Map.Entry<MealDate, Settings> settingsEntry = iter.next();
         MealDate date = settingsEntry.getKey();
         Settings mealSettings = settingsEntry.getValue();

         ArrayList<ArrayList<String>> ingrscats = mealSettings
               .getAvoidedIngredients();
         ArrayList<String> ingrs;
         if (ingr) {
            ingrs = ingrscats.get(0);
         } else {
            ingrs = ingrscats.get(1);
         }

         for (Iterator<String> iterIngrs = ingrs.iterator(); iterIngrs
               .hasNext();) {
            String ingrS = iterIngrs.next();

            if (this.containsKey(ingrS)) {
               ArrayList<MealDate> ingrDates = this.get(ingrS);
               if (!isDateAdded(date, ingrDates)) {
                  ingrDates.add(date);
               }
            } else {
               ArrayList<MealDate> newDateList = new ArrayList<MealDate>();
               newDateList.add(date);
               this.put(ingrS, newDateList);
            }
         }
      }
   }

   /**
    * Cheska for a special date in a list.
    * 
    * @param date
    *           - The date to look for.
    * @param dateList
    *           - The list to search
    * @return True if the date is in the list.
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
}
