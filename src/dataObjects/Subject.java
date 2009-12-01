/*
 * Created using IntelliJ IDEA. User: x04nicsu Date: 2005-jan-13 Time: 15:45:24
 */
package dataObjects;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import dataObjects.units.GramMeasurement;
import dataObjects.units.Measurement;
import dataObjects.units.MilliGramMeasurement;

/**
 * Class representing a subject.
 */
public class Subject {
   private int                     desiredAvailability;
   private int                     desiredCost;
   private int                     desiredDifficulty;
   private int                     desiredTime;
   private int                     desiredVariation;
   private Map<String, Preference> generatedPreferenceMap;
   private int                     importance;
   private Measurement             maxCalcium;
   private Measurement             maxCarbohydrates;
   private Measurement             maxCholesterol;
   private Measurement             maxEnergyContent;
   private Measurement             maxFat;
   private Measurement             maxProtein;
   private Measurement             maxSodium;
   private Measurement             minCalcium;
   private Measurement             minCarbohydrates;
   private Measurement             minCholesterol;
   private Measurement             minEnergyContent;
   private Measurement             minFat;
   private Measurement             minProtein;
   private Measurement             minSodium;
   private String                  name;
   private Map<String, Preference> preferenceMap;
   private ArrayList<String>       refusedCategoriesList;
   private ArrayList<String>       refusedIngredientsList;
   private ArrayList<Group>		groups;

   /**
    * Create a new subject.
    * 
    * @param name
    *           Name of the subject.
    */
   public Subject(String name) {
      this.name = name;
      desiredDifficulty = -1;
      desiredTime = -1;
      desiredCost = -1;
      desiredAvailability = 2;
      desiredVariation = 2;
      minCarbohydrates = new GramMeasurement(0f);
      maxCarbohydrates = new GramMeasurement(130f);
      minEnergyContent = Measurement.createMeasurement("kcal", 1800f);
      maxEnergyContent = Measurement.createMeasurement("kcal", 2400f);
      minFat = new GramMeasurement(20f);
      maxFat = new GramMeasurement(35f);
      minProtein = new GramMeasurement(45f);
      maxProtein = new GramMeasurement(63f);
      minCholesterol = new MilliGramMeasurement(0);
      maxCholesterol = new MilliGramMeasurement(200);
      minSodium = new MilliGramMeasurement(0);
      maxSodium = new MilliGramMeasurement(120);
      minCalcium = new MilliGramMeasurement(800);
      maxCalcium = new MilliGramMeasurement(1200);
      importance = 1;
      preferenceMap = new HashMap<String, Preference>();
      generatedPreferenceMap = new HashMap<String, Preference>();
      refusedIngredientsList = new ArrayList<String>();
      refusedCategoriesList = new ArrayList<String>();
      groups = new ArrayList<Group>();
   }

   /**
    * Adds a generated preference to the subject.
    * 
    * @param preference
    *           Preference to add.
    */
   public void addGeneratedPreference(Preference preference) {
      generatedPreferenceMap.put(preference.getRecipeName(), preference);
   }

   /**
    * Adds a preference to the subject.
    * 
    * @param preference
    *           Preference to add.
    */
   public void addPreference(Preference preference) {
      preferenceMap.put(preference.getRecipeName(), preference);
   }

   /**
    * Adds a refused category to the subject.
    * 
    * @param category
    *           Name of the category to add.
    */
   public void addRefusedCategory(String category) {
      if (refusedCategoriesList.contains(category)) {
         return;
      }
      refusedCategoriesList.add(category);
   }

   /**
    * Adds a refused ingredient to the subject.
    * 
    * @param ingredientName
    *           Name of ingredient to add.
    */
   public void addRefusedIngredient(String ingredientName) {
      if (refusedIngredientsList.contains(ingredientName)) {
         return;
      }
      refusedIngredientsList.add(ingredientName);
   }

   /**
    * Creates a new group and adds it to the list of groups.
    * 
    *  @param name
    *  			Name of the group.
    */
   public void createGroup(String name)
	{
		Group g = new Group(name);
		g.addUser(this);
		this.groups.add(g);		
	}
	
   /**
    * Removes a group from the user's list of groups.
    * 
    * @param name
    * 			Name of the group.
    */
	public void removeGroup(String name)
	{
		int length = this.groups.size();
		for(int i = 0; i<length; i++)
		{
			if(name == this.groups.get(i).getName())
			{
				this.groups.remove(i);
			}
		}
	}
	
	/**
	    * Checks if a user is a member of the specified group.
	    * 
	    * @param g
	    * 			The group to search in.
	    */
	public Boolean isMemberOf(Group g)
	{
		int numberOfGroups = this.groups.size();
			
		for(int i = 0; i<numberOfGroups; i++)
		{
			Group group = this.groups.get(i);
			ArrayList<User> userList = group.getMembers();
			int numberOfMembers = group.getNumberOfMembers();
			
			for(int j = 0; i<numberOfMembers; j++)
			{
				if(this == userList.get(j))
				{
					return true;
				}
			}
		}
		
		return false;
	}
   
   public int getDesiredAvailability() {
      return desiredAvailability;
   }

   public int getDesiredCost() {
      return desiredCost;
   }

   public int getDesiredDifficulty() {
      return desiredDifficulty;
   }

   public int getDesiredTime() {
      return desiredTime;
   }

   public int getDesiredVariation() {
      return desiredVariation;
   }

   /**
    * Gets the generated preferences of a subject.
    * 
    * @return Map of <String, Integer>.
    */
   public Map<String, Preference> getGeneratedPreferences() {
      return generatedPreferenceMap;
   }

   public int getImportance() {
      return importance;
   }

   public Measurement getMaxCalcium() {
      return maxCalcium;
   }

   public Measurement getMaxCarbohydrates() {
      return maxCarbohydrates;
   }

   public Measurement getMaxCholesterol() {
      return maxCholesterol;
   }

   public Measurement getMaxEnergyContent() {
      return maxEnergyContent;
   }

   public Measurement getMaxFat() {
      return maxFat;
   }

   public Measurement getMaxProtein() {
      return maxProtein;
   }

   public Measurement getMaxSodium() {
      return maxSodium;
   }

   public Measurement getMinCalcium() {
      return minCalcium;
   }

   public Measurement getMinCarbohydrates() {
      return minCarbohydrates;
   }

   public Measurement getMinCholesterol() {
      return minCholesterol;
   }

   public Measurement getMinEnergyContent() {
      return minEnergyContent;
   }

   public Measurement getMinFat() {
      return minFat;
   }

   public Measurement getMinProtein() {
      return minProtein;
   }

   public Measurement getMinSodium() {
      return minSodium;
   }

   /**
    * Name of the subject.
    * 
    * @return Name, String.
    */
   public String getName() {
      return name;
   }
   public void setName(String name) {
	   this.name = name;
   }
   /**
    * Gets the preferences of a subject.
    * 
    * @return Map of <String, Integer>.
    */
   public Map<String, Preference> getPreferences() {
      return preferenceMap;
   }

   /**
    * Gets the refused categories of a subjects.
    * 
    * @return ArrayList of String.
    */
   public ArrayList<String> getRefusedCategoriesList() {
      return refusedCategoriesList;
   }

   /**
    * Gets the refused ingredients of a subject.
    * 
    * @return ArrayList of String.
    */
   public ArrayList<String> getRefusedIngredientsList() {
      return refusedIngredientsList;
   }

   public void removeRefusedCategory(String category) {
      refusedCategoriesList.remove(category);
   }

   public void removeRefusedIngredient(String ingredient) {
      refusedIngredientsList.remove(ingredient);
   }

   public void setDesiredAvailability(int desiredAvailability) {
      this.desiredAvailability = desiredAvailability;
   }

   public void setDesiredAvailability(String desiredAvailability) {
      if (desiredAvailability.equalsIgnoreCase("low")) {
         this.desiredAvailability = 1;
      } else if (desiredAvailability.equalsIgnoreCase("medium")) {
         this.desiredAvailability = 2;
      } else {
         this.desiredAvailability = 3;
      }
   }

   public void setDesiredCost(int desiredCost) {
      this.desiredCost = desiredCost;
   }

   public void setDesiredDifficulty(int desiredDifficulty) {
      this.desiredDifficulty = desiredDifficulty;
   }

   public void setDesiredDifficulty(String difficulty) {
      if (difficulty.equalsIgnoreCase("easy")) {
         this.desiredDifficulty = 1;
      } else if (difficulty.equalsIgnoreCase("medium")) {
         this.desiredDifficulty = 2;
      } else if (difficulty.equalsIgnoreCase("hard")) {
         this.desiredDifficulty = 3;
      } else {
         this.desiredDifficulty = -1;
      }
   }

   public void setDesiredTime(int desiredTime) {
      this.desiredTime = desiredTime;
   }

   public void setDesiredVariation(int desiredVariation) {
      this.desiredVariation = desiredVariation;
   }

   public void setDesiredVariation(String desiredVariation) {
      if (desiredVariation.equalsIgnoreCase("low")) {
         this.desiredVariation = 1;
      } else if (desiredVariation.equalsIgnoreCase("medium")) {
         this.desiredVariation = 2;
      } else {
         this.desiredVariation = 3;
      }
   }

   public void setImportance(int importance) {
      this.importance = importance;
   }

   public void setMaxCalcium(Measurement maxCalcium) {
      this.maxCalcium = maxCalcium;
   }

   public void setMaxCarbohydrates(Measurement maxCarbohydrates) {
      this.maxCarbohydrates = maxCarbohydrates;
   }

   public void setMaxCholesterol(Measurement maxCholesterol) {
      this.maxCholesterol = maxCholesterol;
   }

   public void setMaxEnergyContent(Measurement maxEnergyContent) {
      this.maxEnergyContent = maxEnergyContent;
   }

   public void setMaxFat(Measurement maxFat) {
      this.maxFat = maxFat;
   }

   public void setMaxProtein(Measurement maxProtein) {
      this.maxProtein = maxProtein;
   }

   public void setMaxSodium(Measurement maxSodium) {
      this.maxSodium = maxSodium;
   }

   public void setMinCalcium(Measurement minCalcium) {
      this.minCalcium = minCalcium;
   }

   public void setMinCarbohydrates(Measurement minCarbohydrates) {
      this.minCarbohydrates = minCarbohydrates;
   }

   public void setMinCholesterol(Measurement minCholesterol) {
      this.minCholesterol = minCholesterol;
   }

   public void setMinEnergyContent(Measurement minEnergyContent) {
      this.minEnergyContent = minEnergyContent;
   }

   public void setMinFat(Measurement minFat) {
      this.minFat = minFat;
   }

   public void setMinProtein(Measurement minProtein) {
      this.minProtein = minProtein;
   }

   public void setMinSodium(Measurement minSodium) {
      this.minSodium = minSodium;
   }

   @Override
   public String toString() {
      return getName();
   }
}
