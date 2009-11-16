/*
 * MyDate.java
 */

package dataObjects;

import java.util.ArrayList;
import java.util.ListIterator;

/**
 * Old date class from CS.
 */
public class MyDate {

   /** The day */
   private String             day;

   /** Meals for this day */
   private ArrayList<Integer> meals;

   /** The month of this date */
   private int                month;

   /** The weekday of this month. */
   private int                weekday;

   /** The year of this date. */
   private int                year;

   /**
    * Creates an empty date (01/01 2000)
    */
   public MyDate() {
      year = 2000;
      month = 1;
      day = "10";
      weekday = 1;
      meals = new ArrayList<Integer>();
   }

   /**
    * Creates a date from the given year month and day.
    * 
    * @param year
    *           The year of the date.
    * @param month
    *           The month of the date.
    * @param day
    *           The day of the month of this date.
    * @param weekday
    *           The day of the week of this date.
    */
   public MyDate(int year, int month, String day, int weekday) {
      this.year = year;
      this.month = month;
      this.day = day;
      this.weekday = weekday;
      meals = new ArrayList<Integer>();
   }

   /**
    * Creates a new mydate from a mealdate and a list of MyDates used to search
    * for the correct weekday.
    * 
    * @param mealDate
    *           The MealDate to convert to MyDate.
    * @param selectedDates
    *           The dates used to search for the weekday value.
    */
   public MyDate(MealDate mealDate, ArrayList<MyDate> selectedDates) {
      String mealDateS = mealDate.toString();
      this.year = new Integer(mealDateS.substring(0, 4));
      this.month = new Integer(mealDateS.substring(5, 7)) - 1;
      if (this.month < 0)
         this.month = 11;
      this.day = mealDateS.substring(8, 10);
      if (this.day.substring(0, 1).equals("0")) {
         this.day = mealDateS.substring(9, 10);
      }
      this.weekday = 20;
      for (ListIterator<MyDate> iter = selectedDates.listIterator(); iter
            .hasNext();) {

         MyDate selDate = iter.next();
         if (this.equals(selDate)) {
            this.weekday = selDate.getWeekday();
         }
      }

   }

   /**
    * Create a new MyDate from a string yyyy-mm-dd.
    * 
    * @param dateS
    *           Date string on the form yyyy-mm-dd.
    */
   public MyDate(String dateS) {
      this.year = new Integer(dateS.substring(0, 4));
      this.month = new Integer(dateS.substring(5, 7));
      this.day = dateS.substring(8, 10);
      this.weekday = 1;
   }

   /**
    * Compares this and another date according to year month and day fields.
    * 
    * @param inDate
    *           The date to compare to.
    * @return true if the dates represent the same day.
    */
   public boolean equals(MyDate inDate) {
      if (inDate.getDay().equals(getDay())) {
         if (inDate.getMonth() == getMonth()) {
            if (inDate.getYear() == getYear()) {
               return true;
            } else
               return false;
         } else
            return false;
      } else
         return false;
   }

   /**
    * Gets the day of this MyDate object.
    * 
    * @return The day.
    */
   public String getDay() {
      return day;
   }

   /**
    * Gets a MealDate from the MyDate
    * 
    * @return A MealDate representing this date.
    */
   public MealDate getMealDate() {

      MealDate mealDate = new MealDate(this.toString());
      return mealDate;

   }

   /**
    * Gets the meals for this date.
    * 
    * @return The meals for this date.
    */
   public ArrayList<Integer> getMeals() {
      return meals;
   }

   /**
    * Gets the month for this date.
    * 
    * @return The month for this date.
    */
   public int getMonth() {
      return month;
   }

   /**
    * Gets the weekday for this date.
    * 
    * @return The weekday for this date, value between 0 and 6.
    */
   public int getWeekday() {
      return weekday;
   }

   /**
    * Gets the year of this date.
    * 
    * @return The year of this date.
    */
   public int getYear() {
      return year;
   }

   /**
    * Checks if this date is smaller than some other date.
    * 
    * @param otherDate
    *           The date to compare to.
    * @return true if this date is smaller (occurs before) the other date.
    */
   public boolean lessThan(MyDate otherDate) {
      Integer date1Day = new Integer(this.getDay());
      Integer date2Day = new Integer(otherDate.getDay());

      if (this.getYear() < otherDate.getYear()) {
         return true;
      } else if (otherDate.getYear() < this.getYear()) {
         return false;
      } else if (this.getMonth() < otherDate.getMonth()) {
         return true;
      } else if (otherDate.getMonth() < this.getMonth()) {
         return false;
      } else if (date1Day < date2Day) {
         return true;
      } else if (date2Day < date1Day) {
         return false;
      } else
         return false;
   }

   /**
    * Sets the day of this date.
    * 
    * @param day
    *           The day of this date.
    */
   public void setDay(String day) {
      this.day = day;
   }

   /**
    * Sets the meals for this date.
    * 
    * @param meals
    *           The meals for this date.
    */
   public void setMeals(ArrayList<Integer> meals) {
      this.meals = meals;
   }

   /**
    * Sets the month for this date.
    * 
    * @param month
    *           Month for this date.
    */
   public void setMonth(int month) {
      this.month = month;
   }

   /**
    * Sets the weekday for this date.
    * 
    * @param weekday
    *           The weekday for this date. Value between 0 and 6.
    */
   public void setWeekday(int weekday) {
      this.weekday = weekday;
   }

   /**
    * Sets the year of this date.
    * 
    * @param year
    *           The year of this date.
    */
   public void setYear(int year) {
      this.year = year;
   }

   /**
    * Gets this date as a string yyyy-mm-dd that can be used to create a
    * MealDate.
    * 
    * @return The date string.
    */
   public String toMealDateString() {
      String dayS = day;
      Integer dayInt = 0;
      if (day != null) {
         dayInt = new Integer(day);
      }
      if (dayInt < 10 && !day.substring(0, 1).equals("0")) {
         dayS = new String("" + 0 + day);
      }
      int monthToPrint = month + 1;
      if (monthToPrint == 13) {
         monthToPrint = 1;
      }

      String monthS;
      if (monthToPrint < 10) {
         monthS = new String("" + 0 + monthToPrint);
      } else {
         monthS = new String("" + monthToPrint);
      }

      return new String("" + year + "-" + monthS + "-" + dayS);
   }

   /**
    * Gets this date as a string yyyy-mm-dd.
    * 
    * @return The date string.
    */
   public String toProperString() {
      String dayS = day;
      Integer dayInt = 0;
      if (day != null) {
         dayInt = new Integer(day);
      }
      if (dayInt < 10 && !day.substring(0, 1).equals("0")) {
         dayS = new String("" + 0 + day);
      }
      int monthToPrint = month + 1;
      String monthS;
      if (monthToPrint < 10) {
         monthS = new String("" + 0 + monthToPrint);
      } else {
         monthS = new String("" + monthToPrint);
      }

      return new String("" + year + "-" + monthS + "-" + dayS);
   }

   /**
    * Returns this date in string format. Months are zero based, so first of
    * january 2000 would be "2000-00-01"
    * 
    * @return This date in string format.
    */
   @Override
   public String toString() {
      String dayS = day;
      Integer dayInt = 0;
      if (day != null) {
         dayInt = new Integer(day);
      }
      if (dayInt < 10 && !day.substring(0, 1).equals("0")) {
         dayS = new String("" + 0 + day);
      }
      int monthToPrint = month;
      String monthS;
      if (monthToPrint < 10) {
         monthS = new String("" + 0 + monthToPrint);
      } else {
         monthS = new String("" + monthToPrint);
      }

      return new String("" + year + "-" + monthS + "-" + dayS);
   }

}
