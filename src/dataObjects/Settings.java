/*
 * Settings.java
 */

package dataObjects;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Iterator;
import java.util.ListIterator;
import java.util.Map;
import java.util.Scanner;
import java.util.Set;
import java.util.TimeZone;
import java.util.TreeMap;

import mappings.InitMappings;

import data.Messages;
import dataObjects.units.Measurement;

/**
 * All settings a user makes.
 */
public class Settings {

   /**
    * Possible value for nutrition type
    */
   public static int                         NUTRITION_CALC     = 3;
   /**
    * Possible value for nutrition type
    */
   public static int                         NUTRITION_CARB     = 1;
   /**
    * Possible value for nutrition type
    */
   public static int                         NUTRITION_CHOL     = 6;
   /**
    * Possible value for nutrition type
    */
   public static int                         NUTRITION_ENER     = 0;
   /**
    * Possible value for nutrition type
    */
   public static int                         NUTRITION_FAT      = 2;
   /**
    * Possible value for nutrition type
    */
   public static int                         NUTRITION_NATR     = 5;
   /**
    * Possible value for nutrition type
    */
   public static int                         NUTRITION_PROT     = 4;

   /**
    * Possible value for restriction type
    */
   public static int                         RESTRICTION_AVAIL  = 3;
   /**
    * Possible value for restriction type
    */
   public static int                         RESTRICTION_COST   = 0;
   /**
    * Possible value for restriction type
    */
   public static int                         RESTRICTION_DIFF   = 1;
   /**
    * Possible value for restriction type
    */
   public static int                         RESTRICTION_TIME   = 2;
   /**
    * Possible value for restriction type
    */
   public static int                         RESTRICTION_VAR    = 4;

   /**
    * Possible value for user setting type
    */
   public static int                         USER_SETTING_AVOID = 2;
   /**
    * Possible value for user setting type
    */
   public static int                         USER_SETTING_MEALS = 0;
   /**
    * Possible value for user setting type
    */
   public static int                         USER_SETTING_NUTR  = 3;
   /**
    * Possible value for user setting type
    */
   public static int                         USER_SETTING_PERS  = 1;
   /**
    * Possible value for user setting type
    */
   public static int                         USER_SETTING_RESTR = 4;

   /** Current user */
   Subject                                   activeSubject;

   /** Available ingredient categories */
   private ArrayList<String>                 categories;
   /** Names of ingredeints at home */
   private ArrayList<String>                 ingredientNames;
   /** Ingredients at home */
   private ArrayList<IngredientAvailability> ingredients;
   /** Are these settings specific for one meal */
   private boolean                           mealSettings;
   /** All users */
   private ArrayList<Subject>                subjects;
   /** The dates to plan for */
   private ArrayList<MyDate>                 timePeriod;
   /** The user settings */
   private ArrayList<UserSetting>            userSettings;

   /**
    * Creates a new Settings-object.
    * 
    * @param mealSettings
    *           The settings made.
    * @param subjects
    *           All users.
    * @param activeSubject
    *           The current user.
    */
   public Settings(boolean mealSettings, ArrayList<Subject> subjects,
         Subject activeSubject) {
      this.subjects = subjects;
      this.activeSubject = activeSubject;
      this.setMealSettings(mealSettings);

      if (!mealSettings) {
         createInitTime();
         createInitIngredients();
         createInitCategories();
         createInitIngredientNames();
      } else {
         timePeriod = new ArrayList<MyDate>();
         ingredients = new ArrayList<IngredientAvailability>();
         ingredientNames = new ArrayList<String>();
         categories = new ArrayList<String>();
      }

      userSettings = new ArrayList<UserSetting>(6);
   }

   /**
    * Creates the user setting "Ingredients to avoid". Also contains categories.
    * 
    * @param refIngrs
    *           The avoided ingredeints.
    * @param refCats
    *           The avoided categories.
    */
   public void createAvoidedSetting(ArrayList<String> refIngrs,
         ArrayList<String> refCats) {
      UserSetting avoidSetting = new UserSetting(Messages
            .getString(Messages.INGREDIENTS_TO_AVOID), "avoid");
      ArrayList<ArrayList<String>> ingrsCats = new ArrayList<ArrayList<String>>();
      ingrsCats.add(new ArrayList<String>(refIngrs));
      ingrsCats.add(new ArrayList<String>(refCats));

      avoidSetting.setSettingList(ingrsCats);
      userSettings.add(Settings.USER_SETTING_AVOID, avoidSetting);
   }

   /** Create initial categories */
   private void createInitCategories() {
      ArrayList<String> categoriesInit = new ArrayList<String>();
      categories = categoriesInit;
   }

   /** Creates initial ingredeint names */
   private void createInitIngredientNames() {
      ingredientNames = new ArrayList<String>();
      for (ListIterator<IngredientAvailability> iter = ingredients
            .listIterator(); iter.hasNext();) {
         IngredientAvailability ingr = iter.next();
         ingredientNames.add(ingr.getName());
      }
   }

   /** Cerates initial Ingredients */
   private void createInitIngredients() {
      ArrayList<IngredientAvailability> ingredientsInit = new ArrayList<IngredientAvailability>();
      ingredients = ingredientsInit;
   }

   /**
    * Creates a list of nutritional settings.
    * 
    * @param init
    *           A mapping of initial nutritional settings.
    * @return A list of nutritional values with initial settings.
    */
   private ArrayList<Nutrition> createInitNutritions(
         TreeMap<Integer, ArrayList<Measurement>> init) {

      ArrayList<Nutrition> nutritions = new ArrayList<Nutrition>();

      if (!init.keySet().isEmpty()) {
         int index = 0;
         TreeMap<Integer, String> nutrNameMap = InitMappings
               .getNutritionNameMap();
         TreeMap<String, String> sweNameMap = InitMappings.getNutritionEngMap();

         Set<Map.Entry<Integer, ArrayList<Measurement>>> nutrSet = init
               .entrySet();
         for (Iterator<Map.Entry<Integer, ArrayList<Measurement>>> iter = nutrSet
               .iterator(); iter.hasNext();) {
            Map.Entry<Integer, ArrayList<Measurement>> settingsEntry = iter
                  .next();
            Integer nutrNr = settingsEntry.getKey();
            ArrayList<Measurement> nutrValues = settingsEntry.getValue();

            Nutrition nutr = new Nutrition();
            String name = nutrNameMap.get(nutrNr);

            nutr.setName(sweNameMap.get(name));

            Measurement min = nutrValues.get(0);
            Measurement max = nutrValues.get(1);
            nutr.setMinimum(min);
            nutr.setMaximum(max);

            nutritions.add(nutr);
            index++;
         }
      }
      return nutritions;
   }

   /**
    * Create initial restrictions
    * 
    * @return A list of restrictions with initial settings
    */
   private ArrayList<Restriction> createInitRestrictions() throws IOException {

      ArrayList<Restriction> restrArrayList = new ArrayList<Restriction>();
      File restrFile = new File("text/restrictions.txt");
      Scanner sc = new Scanner(restrFile, "UTF-8");
      Restriction restriction;
      ArrayList<String> valueNames = new ArrayList<String>();
      ArrayList<Integer> values = new ArrayList<Integer>();
      String initValue = new String("" + 0);

      int index = 0;
      while (sc.hasNextLine()) {
         restriction = new Restriction();
         restriction.setName(sc.nextLine());
         restriction.setNr(index);
         index++;
         if (sc.hasNextLine()) {
            Integer nrOfValues = new Integer(sc.nextLine());
            restriction.setnrOfValues(nrOfValues.intValue());
         }
         valueNames = restriction.getValueNames();
         values = restriction.getValues();
         valueNames.clear();
         values.clear();
         String valueName;
         int value;
         int i = 0;
         while (i < restriction.getnrOfValues() && sc.hasNextLine()) {
            valueName = sc.nextLine();
            valueNames.add(valueName);
            value = i;
            values.add(new Integer(value));
            i++;
         }
         if (sc.hasNextLine())
            initValue = sc.nextLine();
         int initValueNr = new Integer(initValue);
         restriction.setInitValue(initValueNr);
         restriction.setValueNames(valueNames);
         restrArrayList.add(restriction);
      }
      return restrArrayList;
   }

   /** Creates the initial dates */
   private void createInitTime() {
      ArrayList<MyDate> timeInit = new ArrayList<MyDate>();

      Calendar cal = Calendar.getInstance(TimeZone.getTimeZone("CET"));
      cal.setFirstDayOfWeek(Calendar.MONDAY);
      int currentYear;
      int currentMonth;
      int currentDay;
      String currentDayS;
      int currentWeekday;

      int dayAdvance = 0;
      while (dayAdvance < getStartPlannedPeriod()) {
         rollCalendarOneDay(cal);
         dayAdvance++;
      }

      int dayNr = 0;
      MyDate dateToAdd;
      while (dayNr < getTimePeriodLength()) {
         currentYear = cal.get(Calendar.YEAR);
         currentMonth = cal.get(Calendar.MONTH);
         currentDay = cal.get(Calendar.DAY_OF_MONTH);
         currentDayS = "" + currentDay;
         currentWeekday = cal.get(Calendar.DAY_OF_WEEK);
         dateToAdd = new MyDate(currentYear, currentMonth, currentDayS,
               currentWeekday);
         timeInit.add(dateToAdd);
         dayNr++;
         rollCalendarOneDay(cal);
      }

      setTimePeriod(timeInit);
   }

   /**
    * Create user setting "Meals"
    * 
    * @param meals
    *           The initial meals
    */
   public void createMealsSetting(ArrayList<String> meals) {
      UserSetting mealsSetting = new UserSetting(Messages
            .getString(Messages.MEALS), "meals");
      mealsSetting.setSettingList(meals);
      userSettings.add(Settings.USER_SETTING_MEALS, mealsSetting);
   }

   /**
    * Create user setting "Nutrition"
    * 
    * @param init
    *           The initial settings
    */
   public void createNutritionSetting(
         TreeMap<Integer, ArrayList<Measurement>> init) {

      UserSetting nutrsSetting = new UserSetting(Messages
            .getString(Messages.NUTRITION), "nutr");

      ArrayList<Nutrition> nutritions = createInitNutritions(init);
      nutrsSetting.setSettingList(nutritions);
      userSettings.add(Settings.USER_SETTING_NUTR, nutrsSetting);
   }

   /**
    * Create user setting persons
    * 
    * @param persons
    *           The initially selected persons
    */
   public void createPersonsSetting(ArrayList<String> persons) {
      UserSetting personsSetting = new UserSetting(Messages
            .getString(Messages.PERSONS), "pers");
      // ArrayList<String> newPersons = new ArrayList<String>();
      personsSetting.setSettingList(persons);
      userSettings.add(Settings.USER_SETTING_PERS, personsSetting);
   }

   /**
    * Create initial restriction values
    * 
    * @param init
    *           The initial restrictions
    */
   public void createRestrictionSetting(ArrayList<String> init) {
      UserSetting restrsSetting = new UserSetting(Messages
            .getString(Messages.RESTRICTIONS), "restr");
      ArrayList<Restriction> restrictions = null;
      try {
         restrictions = createInitRestrictions();
      } catch (IOException e) {
         System.out.println("IOException: text/restrictions.txt");
      }
      restrsSetting.setSettingList(restrictions);
      userSettings.add(Settings.USER_SETTING_RESTR, restrsSetting);
   }

   /**
    * Creates a copy of the settings object. This is then thrown away if the
    * user aborts, if not it is saved in a meal settings map. 
    * 
    * @param settingsToCopy
    *           Det settings-object to copy.
    * @param mealSettings
    *           True if the settings are for just one meal
    * @return The copy
    */
   public Settings createSettingsCopy(Settings settingsToCopy,
         boolean mealSettings) {
      Settings settingsCopy = new Settings(false, subjects, activeSubject);

      ArrayList<MyDate> oldTimePeriod = settingsToCopy.getTimePeriod();
      ArrayList<MyDate> newTimePeriod = new ArrayList<MyDate>();
      for (ListIterator<MyDate> iter = oldTimePeriod.listIterator(); iter
            .hasNext();) {
         MyDate date = iter.next();
         String day = date.getDay();
         byte[] dayBytes = day.getBytes();
         String dayCopy = new String(dayBytes);
         int year = date.getYear();
         int month = date.getMonth();
         int weekday = date.getWeekday();
         MyDate dateCopy = new MyDate(year, month, dayCopy, weekday);
         newTimePeriod.add(dateCopy);
      }
      settingsCopy.setTimePeriod(newTimePeriod);

      ArrayList<IngredientAvailability> oldIngredients = settingsToCopy
            .getIngredients();
      ArrayList<IngredientAvailability> newIngredients = new ArrayList<IngredientAvailability>();
      for (ListIterator<IngredientAvailability> iter = oldIngredients
            .listIterator(); iter.hasNext();) {
         IngredientAvailability ingredient = iter.next();
         String name = ingredient.getName();
         Measurement amount = ingredient.getAmount();
         String expirationDate = ingredient.getExpirationDate();
         String nameCopy = new String(name.getBytes());
         Measurement amountCopy = amount;
         String expirationDateCopy = new String(expirationDate.getBytes());
         IngredientAvailability ingredientCopy = new IngredientAvailability(
               nameCopy, amountCopy, new MealDate(expirationDateCopy));
         newIngredients.add(ingredientCopy);
      }
      settingsCopy.setIngredients(newIngredients);

      ArrayList<String> oldIngredientNames = settingsToCopy
            .getIngredientNames();
      ArrayList<String> newIngredientNames = new ArrayList<String>();
      for (ListIterator<String> iter = oldIngredientNames.listIterator(); iter
            .hasNext();) {
         String ingredient = iter.next();
         byte[] ingredientBytes = ingredient.getBytes();
         String ingredientCopy = new String(ingredientBytes);
         newIngredientNames.add(ingredientCopy);
      }
      settingsCopy.setIngredientNames(newIngredientNames);


      ArrayList<String> oldMeals = settingsToCopy.getMeals();
      ArrayList<String> newMeals = new ArrayList<String>();
      for (ListIterator<String> iter = oldMeals.listIterator(); iter.hasNext();) {
         String meal = iter.next();
         byte[] mealBytes = meal.getBytes();
         String mealCopy = new String(mealBytes);
         newMeals.add(mealCopy);
      }
      UserSetting newMealSetting = new UserSetting(Messages
            .getString(Messages.MEALS), "meals");
      newMealSetting.setSettingList(newMeals);
      settingsCopy.setUserSetting(Settings.USER_SETTING_MEALS, newMealSetting);


      ArrayList<String> oldPersons = settingsToCopy.getPersons();
      ArrayList<String> newPersons = new ArrayList<String>();
      for (ListIterator<String> iter = oldPersons.listIterator(); iter
            .hasNext();) {
         String person = iter.next();
         byte[] personBytes = person.getBytes();
         String personCopy = new String(personBytes);
         newPersons.add(personCopy);
      }
      UserSetting newPersonsSetting = new UserSetting(Messages
            .getString(Messages.PERSONS), "pers");
      newPersonsSetting.setSettingList(newPersons);
      settingsCopy
            .setUserSetting(Settings.USER_SETTING_PERS, newPersonsSetting);


      ArrayList<ArrayList<String>> avoid = settingsToCopy
            .getAvoidedIngredients();

      ArrayList<String> oldRefusedIngredients = avoid.get(0);
      ArrayList<String> oldRefusedCategories = avoid.get(1);

      ArrayList<String> newRefusedIngredients = new ArrayList<String>();
      ArrayList<String> newRefusedCategories = new ArrayList<String>();

      for (ListIterator<String> iter = oldRefusedIngredients.listIterator(); iter
            .hasNext();) {
         String refIngr = iter.next();
         byte[] refIngrBytes = refIngr.getBytes();
         String refIngrCopy = new String(refIngrBytes);
         newRefusedIngredients.add(refIngrCopy);
      }

      for (ListIterator<String> iter = oldRefusedCategories.listIterator(); iter
            .hasNext();) {
         String refCat = iter.next();
         byte[] refCatBytes = refCat.getBytes();
         String refCatCopy = new String(refCatBytes);
         newRefusedCategories.add(refCatCopy);
      }

      ArrayList<ArrayList<String>> newIngrCats = new ArrayList<ArrayList<String>>();
      newIngrCats.add(0, newRefusedIngredients);
      newIngrCats.add(1, newRefusedCategories);
      UserSetting newIngrsCatsSetting = new UserSetting(Messages
            .getString(Messages.INGREDIENTS_TO_AVOID), "avoid");
      newIngrsCatsSetting.setSettingList(newIngrCats);
      settingsCopy.setUserSetting(Settings.USER_SETTING_AVOID,
            newIngrsCatsSetting);

      ArrayList<Nutrition> oldNutritions = settingsToCopy.getNutrition();
      ArrayList<Nutrition> newNutritions = new ArrayList<Nutrition>();
      for (ListIterator<Nutrition> iter = oldNutritions.listIterator(); iter
            .hasNext();) {
         Nutrition nutrition = iter.next();
         String nameCopy = new String(nutrition.getName());
         Measurement maxCopy = nutrition.getMaximum().Copy();
         Measurement minCopy = nutrition.getMinimum().Copy();

         Nutrition nutritionCopy = new Nutrition(nameCopy, maxCopy, minCopy);

         newNutritions.add(nutritionCopy);
      }
      UserSetting newNutrSetting = new UserSetting(Messages
            .getString(Messages.NUTRITION), "nutr");
      newNutrSetting.setSettingList(newNutritions);
      settingsCopy.setUserSetting(Settings.USER_SETTING_NUTR, newNutrSetting);

      ArrayList<Restriction> oldRestrictions = settingsToCopy.getRestrictions();
      ArrayList<Restriction> newRestrictions = new ArrayList<Restriction>();
      for (ListIterator<Restriction> iter = oldRestrictions.listIterator(); iter
            .hasNext();) {
         Restriction restriction = iter.next();
         Restriction restrictionCopy = new Restriction();
         // InitValue
         Integer restrictionValue = restriction.getInitValue();
         byte restrBytes = restrictionValue.byteValue();
         Integer restrictionValueCopy = new Integer(restrBytes);
         restrictionCopy.setInitValue(restrictionValueCopy);
         // Name
         String name = restriction.getName();
         String nameCopy = new String(name.getBytes());
         restrictionCopy.setName(nameCopy);
         // values
         ArrayList<Integer> values = restriction.getValues();
         ArrayList<Integer> valuesCopy = new ArrayList<Integer>();
         for (ListIterator<Integer> iterValues = values.listIterator(); iterValues
               .hasNext();) {
            Integer value = iterValues.next();
            Integer valueCopy = new Integer(value.byteValue());
            valuesCopy.add(valueCopy);
         }
         restrictionCopy.setValues(valuesCopy);
         // nrOfValues
         int nrOfValuesCopy = restriction.getnrOfValues();
         restrictionCopy.setnrOfValues(nrOfValuesCopy);
         // valueNames
         ArrayList<String> valueNames = restriction.getValueNames();
         ArrayList<String> valueNamesCopy = new ArrayList<String>();
         for (ListIterator<String> iterValueNames = valueNames.listIterator(); iterValueNames
               .hasNext();) {
            String valueName = iterValueNames.next();
            String valueNameCopy = new String(valueName.getBytes());
            valueNamesCopy.add(valueNameCopy);
         }
         restrictionCopy.setValueNames(valueNamesCopy);

         newRestrictions.add(restrictionCopy);
      }
      UserSetting newRestrSetting = new UserSetting(Messages
            .getString(Messages.RESTRICTIONS), "restr");
      newRestrSetting.setSettingList(newRestrictions);
      settingsCopy.setUserSetting(Settings.USER_SETTING_RESTR, newRestrSetting);

      return settingsCopy;

   }

   /**
    * Gets avoided ingrediets
    * @return The ingredeints
    */
   @SuppressWarnings("unchecked")
   public ArrayList<ArrayList<String>> getAvoidedIngredients() {
      UserSetting setting = userSettings.get(Settings.USER_SETTING_AVOID);
      ArrayList<ArrayList<String>> avoided = (ArrayList<ArrayList<String>>) setting
            .getSettingList();
      return avoided;
   }

   /**
    * Returns a list of ingredient categories that the user picked.
    * 
    * @return The list
    */
   public ArrayList<String> getCategories() {
      return categories;
   }


   /**
    * Gets a list of available ingredients' names
    * 
    * @return  The list of names
    */
   public ArrayList<String> getIngredientNames() {
      return ingredientNames;
   }

   /**
    * Gets a list of available ingredients.
    * 
    * @return The list of ingredeints
    */
   public ArrayList<IngredientAvailability> getIngredients() {
      return ingredients;
   }


   @SuppressWarnings("unchecked")
   public ArrayList<String> getMeals() {
      UserSetting setting = userSettings.get(Settings.USER_SETTING_MEALS);
      ArrayList<String> meals = (ArrayList<String>) setting.getSettingList();
      return meals;
   }

   @SuppressWarnings("unchecked")
   public ArrayList<Nutrition> getNutrition() {
      UserSetting setting = userSettings.get(Settings.USER_SETTING_NUTR);
      ArrayList<Nutrition> nutrition = (ArrayList<Nutrition>) setting
            .getSettingList();
      return nutrition;
   }

   @SuppressWarnings("unchecked")
   public ArrayList<String> getPersons() {
      UserSetting setting = userSettings.get(Settings.USER_SETTING_PERS);
      ArrayList<String> persons = (ArrayList<String>) setting.getSettingList();
      return persons;
   }

   @SuppressWarnings("unchecked")
   public ArrayList<Restriction> getRestrictions() {
      UserSetting setting = userSettings.get(Settings.USER_SETTING_RESTR);
      ArrayList<Restriction> restriction = (ArrayList<Restriction>) setting
            .getSettingList();
      return restriction;
   }

   /**
    * The plan starts this many days from today.
    * 
    * @return Number of days until plan starts, 0 is today.
    */
   private int getStartPlannedPeriod() {
      return 1;
   }


   public ArrayList<MyDate> getTimePeriod() {
      return timePeriod;
   }


   private int getTimePeriodLength() {
      return 2;
   }

   public UserSetting getUserSetting(int userSettingNr) {
      return userSettings.get(userSettingNr);
   }


   public ArrayList<UserSetting> getUserSettings() {
      return userSettings;
   }

   public boolean isMealSettings() {
      return mealSettings;
   }


   /**
    * Advance the calendar one day.
    * @param cal The calendar to advance
    */
   private void rollCalendarOneDay(Calendar cal) {
      int month = cal.get(Calendar.MONTH);
      Integer date = cal.get(Calendar.DATE);

      int numOfDaysInMonth = InitMappings.getNumOfDaysInMonth(month);

      if (date == numOfDaysInMonth) {
         if (month == 12) {
            cal.roll(Calendar.YEAR, true);
         }
         cal.roll(Calendar.MONTH, true);
         int count = 0;
         while (count < numOfDaysInMonth) {
            cal.roll(Calendar.DATE, false);
            count++;
         }
      }
      cal.roll(Calendar.DATE, true);
   }


   /**
    * Sets the list of availible ingredeints' names
    * @param ingredientNames the list
    */
   public void setIngredientNames(ArrayList<String> ingredientNames) {
      this.ingredientNames = ingredientNames;
   }

   /**
    * Sets the list of availible ingredeints
    * @param ingredientNames the list
    */
   public void setIngredients(ArrayList<IngredientAvailability> ingredients) {
      this.ingredients = ingredients;
   }

   public void setMealSettings(boolean mealSettings) {
      this.mealSettings = mealSettings;
   }


   public void setTimePeriod(ArrayList<MyDate> timePeriod) {
      this.timePeriod = timePeriod;
   }


   public void setUserSetting(int userSettingNr, UserSetting userSetting) {
      userSettings.add(userSettingNr, userSetting);
   }

   public void setUserSettings(ArrayList<UserSetting> someUserSettings) {
      userSettings = someUserSettings;
   }


   @Override
   public String toString() {
      String settingsString = new String();
      String general = "SETTINGS: Time Period: " + timePeriod
            + " Ingredients: " + ingredients;
      String userSettingsString = new String();
      for (ListIterator<UserSetting> iter = userSettings.listIterator(); iter
            .hasNext();) {
         UserSetting userSetting = iter.next();
         userSettingsString = userSetting.toString();
         settingsString = settingsString + userSettingsString;
      }
      settingsString = general + settingsString;
      return settingsString;
   }

}
