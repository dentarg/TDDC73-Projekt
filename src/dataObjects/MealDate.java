package dataObjects;

import java.sql.Date;
import java.util.Calendar;
import java.util.List;

import mappings.InitMappings;

/**
 * Class for representing dates in Mealplanner. Internally represented as
 * Strings on the form "yyyy-mm-dd". Also includes information about what meal
 * is referred to.
 */
public class MealDate {

   /**
    * Adds delta days to the date given in dateString.
    * 
    * @param dateString
    *           The original date.
    * @param delta
    *           The number of days to add.
    * @return The new date string.
    */
   private static String addStringDate(final String dateString, final int delta) {
      Calendar c = Calendar.getInstance();

      int year = Integer.parseInt(dateString.substring(0, 4));
      int month = Integer.parseInt(dateString.substring(5, 7));
      int date = Integer.parseInt(dateString.substring(8, 10));

      c.clear();
      c.set(year, month - 1, date);
      c.add(Calendar.DATE, delta);

      year = c.get(Calendar.YEAR);
      month = c.get(Calendar.MONTH) + 1;
      date = c.get(Calendar.DATE);

      String result = String.format("%04d-%02d-%02d", year, month, date);

      return result;
   }

   /**
    * Check if a given date exists in a list of dates
    * 
    * @param testDate
    *           MealDate with mealNumber set to the current meal beeing tested
    * @param validDates
    *           List of MealDate
    * @return true if date exists, false otherwise
    */
   public static boolean dateIsInList(final MealDate testDate,
         final List<MealDate> validDates) {
      MealDate currentDate;
      for (int index = 0; index < validDates.size(); index++) {
         currentDate = validDates.get(index);
         if (testDate.toString().equalsIgnoreCase(currentDate.toString())) {
            return testDate.getMealNumber() == currentDate.getMealNumber();
         }
      }
      return false;
   }

   /**
    * Checks if date1 is bigger (later) than date2.
    * 
    * @param date1
    *           the fist date
    * @param date2
    *           the second date
    * @return True if date1 occurs after date2.
    */
   public static boolean greaterThan(String date1, String date2) {
      String[] split1 = date1.split("-");
      int year1 = Integer.parseInt(split1[0]);

      String[] split2 = date2.split("-");
      int year2 = Integer.parseInt(split2[0]);

      if (year1 > year2) {
         return true;
      } else if (year1 == year2) {
         int month1 = Integer.parseInt(split1[1]);
         int month2 = Integer.parseInt(split2[1]);
         if (month1 > month2) {
            return true;
         } else if (month1 == month2) {
            int day1 = Integer.parseInt(split1[2]);
            int day2 = Integer.parseInt(split2[2]);
            return day1 > day2;
         }
      }
      return false;
   }

   /**
    * Checks if date1 is bigger (later) than or equal to date2.
    * 
    * @param date1
    *           the fist date
    * @param date2
    *           the second date
    * @return True if date1 occurs after or on date2.
    */
   public static boolean greaterThanOrEqual(String date1, String date2) {
      String[] split1 = date1.split("-");
      int year1 = Integer.parseInt(split1[0]);

      String[] split2 = date2.split("-");
      int year2 = Integer.parseInt(split2[0]);

      if (year1 > year2) {
         return true;
      } else if (year1 == year2) {
         int month1 = Integer.parseInt(split1[1]);
         int month2 = Integer.parseInt(split2[1]);
         if (month1 > month2) {
            return true;
         } else if (month1 == month2) {
            int day1 = Integer.parseInt(split1[2]);
            int day2 = Integer.parseInt(split2[2]);
            return day1 >= day2;
         }
      }
      return false;
   }

   /**
    * Checks if date1 is smaller than (before) date2.
    * 
    * @param date1
    *           the fist date
    * @param date2
    *           the second date
    * @return True if date1 occurs before date2.
    */
   public static boolean lessThan(MealDate date1, MealDate date2) {
      return lessThan(date1.toString(), date2.toString());
   }

   /**
    * Checks if date1 is smaller than (before) date2.
    * 
    * @param date1
    *           the fist date
    * @param date2
    *           the second date
    * @return True if date1 occurs before date2.
    */
   public static boolean lessThan(String date1, String date2) {
      String[] split1 = date1.split("-");
      int year1 = Integer.parseInt(split1[0]);

      String[] split2 = date2.split("-");
      int year2 = Integer.parseInt(split2[0]);

      if (year1 < year2) {
         return true;
      } else if (year1 == year2) {
         int month1 = Integer.parseInt(split1[1]);
         int month2 = Integer.parseInt(split2[1]);
         if (month1 < month2) {
            return true;
         } else if (month1 == month2) {
            int day1 = Integer.parseInt(split1[2]);
            int day2 = Integer.parseInt(split2[2]);
            return day1 < day2;
         }
      }
      return false;
   }

   /**
    * Adds one day to the date in currentDate.
    * 
    * @param currentDate
    *           The date to start from
    * @return The date representing one day after currentDate.
    */
   public static MealDate nextDate(final MealDate currentDate) {
      return new MealDate(addStringDate(currentDate.toString(), 1));
   }

   /**
    * Adds one day to the date in currentDate.
    * 
    * @param currentDate
    *           The date to start from
    * @return The date representing one day after currentDate.
    */
   public static String nextDate(final String currentDate) {
      return addStringDate(currentDate.toString(), 1);
   }

   /**
    * The date this instance represents.
    */
   private String date;

   /**
    * The meal on the day this date is associated with.
    */
   private int    mealNumber;

   
   /**
    * Creates a new MealDate. The date is set to today. Meal is set to 1.
    * 
    */
   public MealDate() {
      Calendar c = Calendar.getInstance();
      c.setTime(new java.util.Date());
      int day = c.get(Calendar.DAY_OF_MONTH);
      int month = c.get(Calendar.MONTH) + 1;
      int year = c.get(Calendar.YEAR);
      this.date = String.format("%04d-%02d-%02d", year, month, day);
      mealNumber = 1;
   }

   /**
    * Creates a new MealDate for the given date.  Meal is set to 1.
    * 
    * @param date The date.
    */
   public MealDate(final Date date) {
      this.date = date.toString();
      mealNumber = 1;
   }

   /**
    * Creates a new MealDate for the given date and meal.
    * @param date The date.
    * @param mealNumber The meal.
    */
   public MealDate(final Date date, final int mealNumber) {
      this.date = date.toString();
      this.mealNumber = mealNumber;
   }

   /**
    * Creates a new MealDate for the given date.  Meal is set to 1.
    * 
    * @param date The date.
    */
   public MealDate(final MealDate date) {
      this.date = date.toString();
      mealNumber = 1;
   }

   /**
    * Creates a new MealDate.   Meal is set to 1.
    * 
    * @param dateString
    *           "YYYY-MM-DD"
    */
   public MealDate(final String dateString) {
      this.date = dateString;
      mealNumber = 1;
   }

   /**
    * Creates a new MealDate for the given date and meal.
    * @param date The date.
    * @param mealNumber The meal.
    */
   public MealDate(final String dateString, final int mealNumber) {
      this.date = dateString;
      this.mealNumber = mealNumber;
   }

   /**
    * Returns a date delta days after the current date.
    * @param delta The number of days to add to the current date.
    * @return The new date.
    */
   public MealDate add(int delta) {
      return new MealDate(addStringDate(date, delta));
   }

   /**
    * Compares the year, month and day of two dates.
    * 
    * @param otherDate
    *           The date to compare this one with.
    * @return True if the dates are the same, false otherwise.
    */
   @Override
   public boolean equals(Object o) {
      if (!(o instanceof MealDate)) {
         return false;
      }
      MealDate other = (MealDate) o;
      return (date.equals(other.date));
   }

   /**
    * Gets the string representing the current date.
    * @return The date string.
    */
   public String getDate() {
      return date;
   }

   
   /**
    * Gets the number of the meal.
    * @return The number.
    */
   public int getMealNumber() {
      return mealNumber;
   }

   /*
    * Delegates hashCode calls to the date string.
    * (non-Javadoc)
    * @see java.lang.Object#hashCode()
    */
   @Override
   public int hashCode() {
      return date.hashCode();
   }

   /**
    * Modifies the current date by adding one day to the old value.
    */
   public void nextDate() {
      date = addStringDate(date, 1);
   }

   /**
    * Changes the date indicated by this object.
    * @param date The new date string.
    */
   public void setDate(final String date) {
      this.date = date;
   }

   /**
    * Sets the meal number.
    * @param num The number.
    */
   public void setMealNumber(int num) {
      mealNumber = num;
   }

   /**
    * Gets a nice human readable string representation of the object.
    * @return the string.
    */
   public String toNiceString() {
      Calendar c = Calendar.getInstance();

      int yearVal = Integer.parseInt(date.substring(0, 4));
      int monthVal = Integer.parseInt(date.substring(5, 7));
      int dateVal = Integer.parseInt(date.substring(8, 10));

      c.clear();
      c.set(yearVal, monthVal - 1, dateVal);

      String day = InitMappings.getWeekDayName(c.get(Calendar.DAY_OF_WEEK));

      StringBuilder sb = new StringBuilder();
      sb.append(day).append(" ");
      sb.append(c.get(Calendar.DATE)).append("/");
      sb.append(c.get(Calendar.MONTH));

      return sb.toString();
   }

   @Override
   public String toString() {
      return date;
   }
}
