/*
 * UserSetting.java
 */

package dataObjects;

import java.util.ArrayList;

/**
 * Stores user settings. Used for settings that can be stored in the user profile.
 */
public class UserSetting {

   /**
    * The name of the help file for this setting.
    */
   private String       helpTextFile;

   /** Name for this setting. All settings have a unique name. */
   private String       name;

   /** Number for this setting. All settings have a unique name. */
   private int          nr;

   /**
    * Data for this setting. Could be a list of selected ingredeints.
    */
   private ArrayList<?> settingList;

   /**
    * Create a new UserSetting-object.
    * 
    * @param name
    *           The name of the setting
    * @param helpTextFile
    *           Helpfile for the setting
    */
   public UserSetting(String name, String helpTextFile) {
      this.name = name;
      this.helpTextFile = helpTextFile;
   }

   /**
    * Gets the help file for the setting.
    * 
    * @return The name of the helpfile.
    */
   public String getHelpTextFile() {
      return helpTextFile;
   }

   /**
    * Gets the name of this setting.
    * 
    * @return The name
    */
   public String getName() {
      return name;
   }

   /**
    * Gets the number of this setting.
    * 
    * @return The number
    */
   public int getNr() {
      return nr;
   }

   /**
    * Gets the list of data for this setting.
    * 
    * @return A list of data.
    */
   public ArrayList<?> getSettingList() {
      return settingList;
   }

   /**
    * Sets the name for this setting.
    * 
    * @param name
    *           The name
    */
   public void setName(String name) {
      this.name = name;
   }

   /**
    * Sets the number of this setting.
    * 
    * @param nr
    *           The number for this setting.
    */
   public void setNr(int nr) {
      this.nr = nr;
   }

   /**
    * Sets the list of data for this setting.
    * 
    * @param settingList
    *           The list of data.
    */
   public void setSettingList(ArrayList<?> settingList) {
      this.settingList = settingList;
   }

   /**
    * Returns a string with the data.
    * 
    * @return A string representing the data in this setting.
    */
   @Override
   public String toString() {
      return settingList.toString();
   }
}
