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
 * Class for maping variation requirements to dates.
 */
public class VariationMap extends TreeMap<Integer, ArrayList<MealDate>> {

   private static final long serialVersionUID = 415340064487524094L;
   /**
    * Mapping between name and value. For now all values are named the same as
    * the name .
    */
   TreeMap<Integer, Integer> variationMap;

   /**
    * Creates a new VariationMap-object.
    */
   public VariationMap() {
   }

   /**
    * Creates a new VariationMap-object.
    * 
    * @param mealSettingsMap
    *           Mapping between dates and their settings
    */
   public VariationMap(HashMap<MealDate, Settings> mealSettingsMap,
         String subjectVar) {

      variationMap = new TreeMap<Integer, Integer>();
      variationMap.put(1, 1);
      variationMap.put(2, 2);
      variationMap.put(3, 3);

      Set<Map.Entry<MealDate, Settings>> mealSettingsSet = mealSettingsMap
            .entrySet();

      for (Iterator<Map.Entry<MealDate, Settings>> iter = mealSettingsSet
            .iterator(); iter.hasNext();) {
         Map.Entry<MealDate, Settings> settingsEntry = iter.next();
         MealDate date = settingsEntry.getKey();
         Settings mealSettings = settingsEntry.getValue();

         ArrayList<Restriction> restrictions = mealSettings.getRestrictions();
         int variationInt = restrictions.get(Settings.RESTRICTION_VAR)
               .getInitValue();
         String varName = InitMappings.getRestrictionEngName(variationInt,
               Settings.RESTRICTION_VAR);

         if (!subjectVar.equals(varName)) {
            int variation = variationMap.get(variationInt);
            if (this.containsKey(variation)) {

               ArrayList<MealDate> diffDates = this.get(variation);
               if (isDateAdded(date, diffDates)) {
                  diffDates.add(date);
                  removeDate(date, variation, this);
               }
            } else {
               ArrayList<MealDate> newDateList = new ArrayList<MealDate>();
               newDateList.add(date);
               this.put(variation, newDateList);
               removeDate(date, variation, this);
            }
         }
      }
   }

   /**
    * Checks if a date is in a list.
    * 
    * @param date
    *           - The date to search for.
    * @param dateList
    *           - The list to search.
    * @return boolean - True if the list contains tha date.
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
    * Removes all occurances of dates that are not special.
    * 
    * @param datThe date to remove
    * @param specVariation
    *           The special occurance to keep.
    * @param variationMap
    *           mapping between variation-constraints and their dates.
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
