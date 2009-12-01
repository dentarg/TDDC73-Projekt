package mappings;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.ListIterator;
import java.util.Map;
import java.util.TreeMap;

import dataObjects.MealDate;
import dataObjects.MyDate;
import dataObjects.Restriction;
import dataObjects.Settings;

/**
 * Mapps cooking times to dates.
 */
public class TimeMap extends TreeMap<String, List<MealDate>> {

   private static final long serialVersionUID = 8045385068410836347L;

   /** maps values to strings */
   TreeMap<Integer, String>  timeMinuteMap;

   public String getValue(int k) {
	   return timeMinuteMap.get(k);
   }

   /**
    * Creates an empty map.
    */
   public TimeMap() {
	   timeMinuteMap = new TreeMap<Integer, String>();
	   timeMinuteMap.put(1, "short");
	   timeMinuteMap.put(2, "medium");
	   timeMinuteMap.put(3, "high");
   }

   /**
    * Creates a new TimeMap-object.
    * 
    * @param mealSettingsMap
    *           Mapping between meals and their settings.
    */
   public TimeMap(HashMap<MealDate, Settings> mealSettingsMap,
         String subjectTime) {

      timeMinuteMap = new TreeMap<Integer, String>();
      timeMinuteMap.put(1, "short");
      timeMinuteMap.put(2, "medium");
      timeMinuteMap.put(3, "high");

      for (Map.Entry<MealDate, Settings> settingsEntry : mealSettingsMap
            .entrySet()) {
         MealDate date = settingsEntry.getKey();
         Settings mealSettings = settingsEntry.getValue();

         ArrayList<Restriction> restrictions = mealSettings.getRestrictions();
         int timeInt = restrictions.get(Settings.RESTRICTION_TIME)
               .getInitValue();
         String timeName = InitMappings.getRestrictionEngName(timeInt,
               Settings.RESTRICTION_TIME);

         if (!subjectTime.equals(timeName)) {
            String time = timeMinuteMap.get(timeInt);
            if (this.containsKey(time)) {

               ArrayList<MealDate> timeDates = (ArrayList<MealDate>) this
                     .get(time);
               if (!isDateAdded(date, timeDates)) {
                  timeDates.add(date);
                  removeDate(date, time, this);
               }
            } else {
               ArrayList<MealDate> newDateList = new ArrayList<MealDate>();
               newDateList.add(date);
               this.put(time, newDateList);
               removeDate(date, time, this);
            }
         }
      }
   }

   /**
    * Check if date is in a list.
    * 
    * @param date
    *           - The sought date.
    * @param dateList
    *           - The list to search
    * @return boolean - True if the list contains the date.
    */
   public boolean isDateAdded(MealDate date, List<MealDate> dateList) {
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
    * Removes all entries for a date except the special one.
    * 
    * @param date
    *           The date to remove
    * @param specTime
    *           The special time to keep
    * @param timeMap
    *           The mappings to remove from.
    */
   public void removeDate(MealDate date, String specTime, TimeMap timeMap) {
      for (Map.Entry<String, List<MealDate>> timeEntry : timeMap.entrySet()) {
         String time = timeEntry.getKey();
         List<MealDate> dateList = timeEntry.getValue();
         if (isDateAdded(date, dateList) && (!specTime.equals(time))) {
            dateList.remove(date);
         }
      }
   }
}
