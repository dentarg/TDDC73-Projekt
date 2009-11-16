/*
 * DifficultyMap.java
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
import dataObjects.Restriction;
import dataObjects.Settings;

/**
 * Describs the mapping between difficulty and mealdates. Used as a parameter to the CS solver.
 */
public class DifficultyMap extends TreeMap<String, ArrayList<MealDate>> {

   private static final long serialVersionUID = -3513648442620737845L;

   private TreeMap<Integer, String>  diffStringMap;

   /**
    * Creates a new wmpty object
    */
   public DifficultyMap() {
   }

   /**
    * Creates a new DifficultyMap-object.
    * 
    * @param mealSettingsMap
    *           Mapping between meals and their settings.
    */
   public DifficultyMap(HashMap<MealDate, Settings> mealSettingsMap,
         String subjectDiff) {

      // XXX: Översättning
      diffStringMap = new TreeMap<Integer, String>();
      diffStringMap.put(1, "easy");
      diffStringMap.put(2, "medium");
      diffStringMap.put(3, "hard");

      Set<Map.Entry<MealDate, Settings>> mealSettingsSet = mealSettingsMap
            .entrySet();

      for (Iterator<Map.Entry<MealDate, Settings>> iter = mealSettingsSet
            .iterator(); iter.hasNext();) {
         Map.Entry<MealDate, Settings> settingsEntry = iter.next();
         MealDate date = settingsEntry.getKey();
         Settings mealSettings = settingsEntry.getValue();

         ArrayList<Restriction> restrictions = mealSettings.getRestrictions();
         int diffInt = restrictions.get(Settings.RESTRICTION_DIFF)
               .getInitValue();
         String diffName = InitMappings.getRestrictionEngName(diffInt,
               Settings.RESTRICTION_DIFF);

         if (!subjectDiff.equals(diffName)) {

            String diff = diffStringMap.get(diffInt);
            if (this.containsKey(diff)) {
               ArrayList<MealDate> diffDates = this.get(diff);
               if (!isDateAdded(date, diffDates)) {
                  diffDates.add(date);
                  removeDate(date, diff, this);
               }
            } else {
               ArrayList<MealDate> newDateList = new ArrayList<MealDate>();
               newDateList.add(date);
               this.put(diff, newDateList);
               removeDate(date, diff, this);
            }
         }
      }
   }

   /**
    * Check iof a date is in a list.
    * 
    * @param date
    *           - The date to look for.
    * @param dateList
    *           - The list to look in
    * @return boolean - True if the list contains the date.
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
    * Removes a date from all mappings except a special one.
    * 
    * @param date
    *           The date to remove
    * @param specDiff
    *           The mapping to keep
    * @param difficultyMap
    *           The whole mapping-list.
    */
   public void removeDate(MealDate date, String specDiff,
         Map<String, ArrayList<MealDate>> difficultyMap) {
      Set<Map.Entry<String, ArrayList<MealDate>>> difficultiesSet = difficultyMap
            .entrySet();
      for (Iterator<Map.Entry<String, ArrayList<MealDate>>> iter = difficultiesSet
            .iterator(); iter.hasNext();) {
         Map.Entry<String, ArrayList<MealDate>> diffEntry = iter.next();
         String diff = diffEntry.getKey();
         ArrayList<MealDate> dateList = diffEntry.getValue();
         if (dateList.contains(date) && !specDiff.equals(diff)) {
            dateList.remove(date);
         }
      }
   }
}
