/*
 * CostMap.java
 */

package mappings;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;
import java.util.TreeMap;

import dataObjects.MealDate;
import dataObjects.Restriction;
import dataObjects.Settings;

/**
 * Class for mapping between cost limitations and different dates. Used as a
 * variable to the CS solver.
 */
public class CostMap extends DifficultyMap {

   private static final long               serialVersionUID = -3510154127788445026L;

   private static TreeMap<Integer, String> costStringMap    = new TreeMap<Integer, String>();

   static {
      costStringMap.put(1, "low");
      costStringMap.put(2, "medium");
      costStringMap.put(3, "high");
   }

   public CostMap() {
   }

   public CostMap(HashMap<MealDate, Settings> mealSettingsMap,
         String subjectCost) {

      Set<Map.Entry<MealDate, Settings>> mealSettingsSet = mealSettingsMap
            .entrySet();

      for (Iterator<Map.Entry<MealDate, Settings>> iter = mealSettingsSet
            .iterator(); iter.hasNext();) {
         Map.Entry<MealDate, Settings> settingsEntry = iter.next();
         MealDate date = settingsEntry.getKey();
         Settings mealSettings = settingsEntry.getValue();

         ArrayList<Restriction> restrictions = mealSettings.getRestrictions();
         int costInt = restrictions.get(Settings.RESTRICTION_COST)
               .getInitValue();
         String costName = InitMappings.getRestrictionEngName(costInt,
               Settings.RESTRICTION_COST);

         if (!subjectCost.equals(costName)) {
            String cost = costStringMap.get(costInt);
            if (this.containsKey(cost)) {
               ArrayList<MealDate> costDates = this.get(cost);
               if (!isDateAdded(date, costDates)) {
                  costDates.add(date);
                  removeDate(date, cost, this);
               }
            } else {
               ArrayList<MealDate> newDateList = new ArrayList<MealDate>();
               newDateList.add(date);
               this.put(cost, newDateList);
               removeDate(date, cost, this);
            }
         }
      }
   }
}
