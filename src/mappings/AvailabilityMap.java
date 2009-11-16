package mappings;

import java.util.HashMap;

import dataObjects.MealDate;
import dataObjects.Settings;

/**
 * Class for mapping between availibility constraints and dates. Used as
 * variable to the CS solver.
 */
public class AvailabilityMap extends VariationMap {

   private static final long serialVersionUID = -6863859110917079936L;

   /**
    * Creates an empty AvailabilityMap-object.
    */
   public AvailabilityMap() {
   }

   /**
    * Creates a new AvailabilityMap-object.
    * 
    * @param mealSettingsMap
    *           Mapping between special meals and their settings.
    */
   public AvailabilityMap(HashMap<MealDate, Settings> mealSettingsMap,
         String subjectAvail) {
      super(mealSettingsMap, subjectAvail);
   }
}
