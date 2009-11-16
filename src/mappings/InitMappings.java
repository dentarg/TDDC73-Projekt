package mappings;

import java.util.ArrayList;
import java.util.Map;
import java.util.TreeMap;

import dataObjects.Settings;

/**
 * A utility class containing mappings for all sorts of things needed throughout
 * the application.
 */
public final class InitMappings {

   /**
    * Returns the name of a meal number.
    * 
    * @param mealNr
    *           The meal number
    * @return The name of the meal.
    */
   public static String getMealName(final int mealNr) {
      final Map<Integer, String> mealMap = new TreeMap<Integer, String>();
      mealMap.put(1, "Frukost");
      mealMap.put(2, "Lunch");
      mealMap.put(3, "Middag");

      return mealMap.get(mealNr);
   }

   /**
    * Gets the mappings between the meal numbers and names
    * 
    * @return The mapping
    */
   public static Map<Integer, String> getMealNameMap() {
      final Map<Integer, String> mealMap = new TreeMap<Integer, String>();
      mealMap.put(1, "Frukost");
      mealMap.put(2, "Lunch");
      mealMap.put(3, "Middag");
      return mealMap;
   }

   /**
    * Returns the number of a meal.
    * 
    * @param meal
    *           The name of the meal.
    * @return The number of the meal.
    */
   public static int getMealNr(final String meal) {
      final Map<String, Integer> mealMap = new TreeMap<String, Integer>();
      mealMap.put("Frukost", 1);
      mealMap.put("Lunch", 2);
      mealMap.put("Middag", 3);

      if (mealMap.containsKey(meal)) {
         return mealMap.get(meal);
      } else {
         throw new IllegalArgumentException(meal + " is not a valid meal-name");
      }

   }

   /**
    * Returns the list of allowed meals.
    * 
    * @return The list of meals.
    */
   public static ArrayList<String> getMeals() {
      final ArrayList<String> meals = new ArrayList<String>();
      meals.add("Frukost");
      meals.add("Lunch");
      meals.add("Middag");
      return meals;
   }

   /**
    * Gets the mappings between month numbers and names
    * 
    * @param monthNr
    *           The number of the month.
    * @return The name of the month.
    */
   public static String getMonthName(final int monthNr) {
      final Map<Integer, String> monthMap = new TreeMap<Integer, String>();
      monthMap.put(0, "Januari");
      monthMap.put(1, "Februari");
      monthMap.put(2, "Mars");
      monthMap.put(3, "April");
      monthMap.put(4, "Maj");
      monthMap.put(5, "Juni");
      monthMap.put(6, "Juli");
      monthMap.put(7, "Augusti");
      monthMap.put(8, "September");
      monthMap.put(9, "Oktober");
      monthMap.put(10, "November");
      monthMap.put(11, "December");

      return monthMap.get(monthNr);
   }

   /**
    * Mapping between the month number and the number of days that month.
    * 
    * @param monthNr
    *           The month's number
    * @return The number of days that month.
    */
   public static int getNumOfDaysInMonth(final int monthNr) {
      final Map<Integer, Integer> monthMap = new TreeMap<Integer, Integer>();
      monthMap.put(0, 31);
      monthMap.put(1, 28);
      monthMap.put(2, 31);
      monthMap.put(3, 30);
      monthMap.put(4, 31);
      monthMap.put(5, 30);
      monthMap.put(6, 31);
      monthMap.put(7, 31);
      monthMap.put(8, 30);
      monthMap.put(9, 31);
      monthMap.put(10, 30);
      monthMap.put(11, 31);

      return monthMap.get(monthNr);
   }

   /**
    * Gets a mapping between english and swedish nutirtional values.
    * 
    * @return The mapping
    */
   public static TreeMap<String, String> getNutritionEngMap() {
      final TreeMap<String, String> nutrMap = new TreeMap<String, String>();
      nutrMap.put("calcium", "kalcium (mg)");
      nutrMap.put("carbohydrates", "kolhydrater (g)");
      nutrMap.put("energyContent", "energi (kcal)");
      nutrMap.put("fat", "fett (g)");
      nutrMap.put("natrium", "sodium (mg)");
      nutrMap.put("protein", "protein (g)");
      nutrMap.put("cholesterol", "kolesterol (mg)");
      return nutrMap;
   }

   /**
    * Gets a mapping between nutritional value number and nutritional value
    * string.
    * 
    * @return The mapping.
    */
   public static TreeMap<Integer, String> getNutritionNameMap() {
      final TreeMap<Integer, String> nutrMap = new TreeMap<Integer, String>();
      nutrMap.put(Settings.NUTRITION_ENER, "energyContent");
      nutrMap.put(Settings.NUTRITION_CARB, "carbohydrates");
      nutrMap.put(Settings.NUTRITION_FAT, "fat");
      nutrMap.put(Settings.NUTRITION_NATR, "natrium");
      nutrMap.put(Settings.NUTRITION_PROT, "protein");
      nutrMap.put(Settings.NUTRITION_CALC, "calcium");
      nutrMap.put(Settings.NUTRITION_CHOL, "cholesterol");
      return nutrMap;
   }

   /**
    * Gets a mapping between nutritional value strings and their numbers.
    * 
    * @return The mapping.
    */
   public static TreeMap<String, Integer> getNutritionNrMap() {
      final TreeMap<String, Integer> nutrMap = new TreeMap<String, Integer>();
      nutrMap.put("energyContent", Settings.NUTRITION_ENER);
      nutrMap.put("carbohydrates", Settings.NUTRITION_CARB);
      nutrMap.put("fat", Settings.NUTRITION_FAT);
      nutrMap.put("natrium", Settings.NUTRITION_NATR);
      nutrMap.put("protein", Settings.NUTRITION_PROT);
      nutrMap.put("calcium", Settings.NUTRITION_CALC);
      nutrMap.put("cholesterol", Settings.NUTRITION_CHOL);
      return nutrMap;
   }

   /**
    * Gets a mapping between swedish nutritional value strings and their english counterparts.
    * 
    * @return The mapping.
    */
   public static TreeMap<String, String> getNutritionSweMap() {
      final TreeMap<String, String> nutrMap = new TreeMap<String, String>();
      nutrMap.put("energi (kcal)", "energyContent");
      nutrMap.put("fett (g)", "fat");
      nutrMap.put("kolhydrater (g)", "carbohydrates");
      nutrMap.put("protein (g)", "protein");
      nutrMap.put("sodium (mg)", "natrium");
      nutrMap.put("kalcium (mg)", "calcium");
      nutrMap.put("kolesterol (mg)", "cholesterol");
      return nutrMap;
   }

   /**
    * Mapping between restriction values and names. Returns a restriction's english name given it's value.
    * 
    * @param value
    *           The value of the restriction.
    * @param restrNr
    *           The restriction's numner
    * @return The name of the restriction.
    */
   public static String getRestrictionEngName(final int value, final int restrNr) {
      if (restrNr == Settings.RESTRICTION_AVAIL) {
         final TreeMap<Integer, String> availMap = new TreeMap<Integer, String>();
         availMap.put(1, "low");
         availMap.put(2, "medium");
         availMap.put(3, "high");
         return availMap.get(value);
      }
      if (restrNr == Settings.RESTRICTION_COST) {
         final TreeMap<Integer, String> costMap = new TreeMap<Integer, String>();
         costMap.put(1, "low");
         costMap.put(2, "medium");
         costMap.put(3, "high");
         return costMap.get(value);
      }
      if (restrNr == Settings.RESTRICTION_DIFF) {
         final TreeMap<Integer, String> costMap = new TreeMap<Integer, String>();
         costMap.put(1, "easy");
         costMap.put(2, "medium");
         costMap.put(3, "hard");
         return costMap.get(value);
      }
      if (restrNr == Settings.RESTRICTION_TIME) {
         final TreeMap<Integer, String> timeMap = new TreeMap<Integer, String>();
         timeMap.put(1, "short");
         timeMap.put(2, "medium");
         timeMap.put(3, "long");
         return timeMap.get(value);
      }
      if (restrNr == Settings.RESTRICTION_VAR) {
         final TreeMap<Integer, String> varMap = new TreeMap<Integer, String>();
         varMap.put(1, "low");
         varMap.put(2, "medium");
         varMap.put(3, "high");
         return varMap.get(value);
      } else
         return null;
   }

   /**
    * Mapping between restriction values and names. Returns a restriction's swedish name given it's value.
    * 
    * @deprecated Localized translation.
    * @param value
    *           The value of the restriction.
    * @param restrNr
    *           The restriction's numner
    * @return The name of the restriction.
    */
   public static String getRestrictionSweName(final int value, final int restrNr) {
      if (restrNr == Settings.RESTRICTION_AVAIL) {
         final TreeMap<Integer, String> availMap = new TreeMap<Integer, String>();
         availMap.put(1, "bra"); 
         availMap.put(2, "medelbra");
         availMap.put(3, "sämre");
         return availMap.get(value);
      }
      if (restrNr == Settings.RESTRICTION_COST) {
         final TreeMap<Integer, String> costMap = new TreeMap<Integer, String>();
         costMap.put(1, "billigt");
         costMap.put(2, "medeldyrt");
         costMap.put(3, "dyrt");
         return costMap.get(value);
      }
      if (restrNr == Settings.RESTRICTION_DIFF) {
         final TreeMap<Integer, String> costMap = new TreeMap<Integer, String>();
         costMap.put(1, "enkelt");
         costMap.put(2, "medelsvårt");
         costMap.put(3, "svårt");
         return costMap.get(value);
      }
      if (restrNr == Settings.RESTRICTION_TIME) {
         final TreeMap<Integer, String> timeMap = new TreeMap<Integer, String>();
         timeMap.put(1, "kort");
         timeMap.put(2, "medellång");
         timeMap.put(3, "lång");
         return timeMap.get(value);
      }
      if (restrNr == Settings.RESTRICTION_VAR) {
         final TreeMap<Integer, String> varMap = new TreeMap<Integer, String>();
         varMap.put(1, "god");
         varMap.put(2, "medelbra");
         varMap.put(3, "sämre"); 
         return varMap.get(value);
      } else
         return null;
   }

   /**
    * Gets a mapping between a restriction's name and value.
    * 
    * 
    * @param name
    *           The restriction's name.
    * @param restrNr
    *           The restriction's number.
    * @return The restriction's value.
    */
   public static Integer getRestrictionValue(final String name,
         final int restrNr) {
      if (restrNr == Settings.RESTRICTION_AVAIL) {
         final TreeMap<String, Integer> availMap = new TreeMap<String, Integer>();
         availMap.put("low", 1);
         availMap.put("medium", 2);
         availMap.put("high", 3);
         return availMap.get(name);
      }
      if (restrNr == Settings.RESTRICTION_COST) {
         final TreeMap<String, Integer> costMap = new TreeMap<String, Integer>();
         costMap.put("low", 1);
         costMap.put("medium", 2);
         costMap.put("high", 3);
         return costMap.get(name);
      }
      if (restrNr == Settings.RESTRICTION_DIFF) {
         final TreeMap<String, Integer> costMap = new TreeMap<String, Integer>();
         costMap.put("easy", 1);
         costMap.put("medium", 2);
         costMap.put("hard", 3);
         return costMap.get(name);
      }
      if (restrNr == Settings.RESTRICTION_TIME) {
         final TreeMap<String, Integer> timeMap = new TreeMap<String, Integer>();
         timeMap.put("short", 1);
         timeMap.put("medium", 2);
         timeMap.put("long", 3);
         return timeMap.get(name);
      }
      if (restrNr == Settings.RESTRICTION_VAR) {
         final TreeMap<String, Integer> varMap = new TreeMap<String, Integer>();
         varMap.put("low", 1);
         varMap.put("medium", 2);
         varMap.put("high", 3);
         return varMap.get(name);
      } else
         return null;
   }

   /**
    * Gets a weekday's name given it's number.
    * 
    * @param weekDayNr
    *           The number of a weekday.
    * @return The name of a weekdday
    */
   public static String getWeekDayName(final int weekDayNr) {
      final Map<Integer, String> weekMap = new TreeMap<Integer, String>();
      weekMap.put(1, "Söndag");
      weekMap.put(2, "Måndag");
      weekMap.put(3, "Tisdag");
      weekMap.put(4, "Onsdag");
      weekMap.put(5, "Torsdag");
      weekMap.put(6, "Fredag");
      weekMap.put(7, "Lördag");
      weekMap.put(8, "Söndag");
      weekMap.put(20, "Okänd");
      return weekMap.get(weekDayNr);
   }

   /** Should never be instantiated. */
   private InitMappings() {

   }
}
