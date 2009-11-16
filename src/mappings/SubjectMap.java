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
 * Class for mapping users to dates. Used as avariable for the CS solver.
 * 
 */
public class SubjectMap extends TreeMap<String, ArrayList<MealDate>> {

   private static final long   serialVersionUID = 5882241589621378325L;
   /** Mapping between special meals and their settings. */
   HashMap<MealDate, Settings> mealSettingsMap;

   public SubjectMap() {
   }

   /**
    * Creates a new SubjectMap-object.
    * 
    * @param selectedSubjects The selected users
    * @param selectedDates
    *           The selected dates.
    * @param special
    *           True if this is for special meals
    * @param mealSettingsMap
    *           Mapping between dates and settings
    */
   public SubjectMap(ArrayList<String> selectedSubjects,
         ArrayList<MealDate> selectedDates, boolean special,
         HashMap<MealDate, Settings> mealSettingsMap) {

      this.mealSettingsMap = mealSettingsMap;

      for (ListIterator<String> iterPersons = selectedSubjects.listIterator(); iterPersons
            .hasNext();) {
         String subject = iterPersons.next();
         ArrayList<MealDate> dateList = new ArrayList<MealDate>();

         for (ListIterator<MealDate> iterDays = selectedDates.listIterator(); iterDays
               .hasNext();) {
            MealDate oneDate = iterDays.next();
            if (!isDateAdded(oneDate, dateList)) {
               dateList.add(oneDate);
            }
         }
         put(subject, dateList);
      }

      if (special) {
         addSpecialSubjects();
         checkRemove();
      }

   }

   /**
    * Adds special subjects to this mapping
    */
   public void addSpecialSubjects() {
      Set<Map.Entry<MealDate, Settings>> mealSettingsSet = mealSettingsMap
            .entrySet();

      for (Iterator<Map.Entry<MealDate, Settings>> iter = mealSettingsSet
            .iterator(); iter.hasNext();) {
         Map.Entry<MealDate, Settings> settingsMap = iter.next();
         MealDate date = settingsMap.getKey();
         Settings mealSettings = settingsMap.getValue();

         ArrayList<String> usersArrayList = mealSettings.getPersons();

         for (ListIterator<String> subjectsIter = usersArrayList.listIterator(); subjectsIter
               .hasNext();) {
            String subject = subjectsIter.next();

            if (this.containsKey(subject)) {
               ArrayList<MealDate> dateList = this.get(subject);
               if (!isDateAdded(date, dateList)) {
                  dateList.add(date);
               } else {
               }
            } else {
               ArrayList<MealDate> dateList = new ArrayList<MealDate>();
               dateList.add(date);
               this.put(subject, dateList);
            }
         }
      }
   }

   /**
    * Removes all NON-special subjects from all special meals
    */
   public void checkRemove() {
      Set<Map.Entry<MealDate, Settings>> mealSet = mealSettingsMap.entrySet();

      for (Iterator<Map.Entry<MealDate, Settings>> iter = mealSet.iterator(); iter
            .hasNext();) {
         Map.Entry<MealDate, Settings> mealEntry = iter.next();
         MealDate date = mealEntry.getKey();

         Settings settings = mealEntry.getValue();

         ArrayList<String> subjects = settings.getPersons();

         removeSubjects(date, subjects);
      }
   }

   /**
    * Check if a date is in a list.
    * 
    * @param date
    *           - The sought date.
    * @param dateList
    *           - The list to search.
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
    * Check if a user is in a list of special users.
    * 
    * @param subject
    *           The sought user
    * @param specialSubjects
    *           The searched list of users
    * @return True if the user is in the list.
    */
   public boolean isSpecial(String subject, ArrayList<String> specialSubjects) {
      for (ListIterator<String> subjectsIter = specialSubjects.listIterator(); subjectsIter
            .hasNext();) {
         String specialSubject = subjectsIter.next();
         if (subject.equals(specialSubject)) {
            return true;
         }
      }
      return false;
   }

   /**
    * Removes all non special users from a meal, ie the users not participating.
    * 
    * @param specialDate
    *           The special meal
    * @param specialSubjects
    *           The users that are participating.
    */
   public void removeSubjects(MealDate specialDate,
         ArrayList<String> specialSubjects) {

      Set<Map.Entry<String, ArrayList<MealDate>>> subjectsSet = this.entrySet();

      for (Iterator<Map.Entry<String, ArrayList<MealDate>>> iter = subjectsSet
            .iterator(); iter.hasNext();) {
         Map.Entry<String, ArrayList<MealDate>> subjectEntry = iter.next();
         String subject = subjectEntry.getKey();
         ArrayList<MealDate> dateList = subjectEntry.getValue();

         for (ListIterator<MealDate> datesIter = dateList.listIterator(); datesIter
               .hasNext();) {
            MealDate date = datesIter.next();
            if (specialDate.toString().equals(date.toString())) {
               if (!isSpecial(subject, specialSubjects)) {
                  dateList.remove(date);
               }
               break;
            }
         }
         put(subject, dateList);
      }
   }

}
