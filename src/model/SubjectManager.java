package model;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import dataAccess.SubjectDA;
import dataObjects.Preference;
import dataObjects.Recipe;
import dataObjects.Subject;
import dataObjects.units.Measurement;
import exceptions.SubjectNotFoundException;

/**
 * Class containing methods for managing subjects (people to plan meals for).
 * This should normally be instanciated by an UI.
 */
public class SubjectManager {
   private SubjectDA subjectDA;

   /**
    * Creates a new subject manager.
    */
   public SubjectManager() {
      subjectDA = new SubjectDA();
   }

   /**
    * Adds a preference to a subject in storage.
    * 
    * @param subjectName
    *           name of Subject to add preference to.
    * @param recipeName
    *           name of the recipe.
    * @param rating
    *           rating of the recipe, (0=refuses to eat...5=loves to eat)
    * @throws SubjectNotFoundException
    *            If no subject with that name is in the database.
    */
   public void addPreference(final String subjectName, final String recipeName,
         final int rating) throws SubjectNotFoundException {
      Subject subject = getSubject(subjectName);
      subject.addPreference(new Preference(recipeName, rating));
      subjectDA.modifySubject(subject);
   }

   /**
    * ** Mainly intended for testing, use addPreference(String, String, int)
    * instead** Adds a preference to a subject in storage.
    * 
    * @param subject
    *           Subject to add preference to.
    * @param preference
    *           Preference to add.
    */
   public void addPreference(final Subject subject, final Preference preference) {
      subject.addPreference(preference);
      subjectDA.modifySubject(subject);
   }

   /**
    * Add a refused category to a subject in storage.
    * 
    * @param subjectName
    *           name of Subject to add to.
    * @param category
    *           category to add, given as String. Note: valid categories can be
    *           found in data/Categories.data
    * @throws SubjectNotFoundException
    *            If no subject with that name is in the database.
    */
   public void addRefusedCategory(final String subjectName,
         final String category) throws SubjectNotFoundException {
      Subject subject = subjectDA.getSubject(subjectName);
      subject.addRefusedCategory(category);
      subjectDA.modifySubject(subject);
   }

   /**
    * Add a refused ingredient to a subject in storage.
    * 
    * @param subjectName
    *           name of Subject to add to.
    * @param ingredient
    *           ingredient to add, given as String. Note: valid ingredients can
    *           be found in data/Ingredients.data
    * @throws SubjectNotFoundException
    *            if no subject with that name is in the database.
    */
   public void addRefusedIngredient(final String subjectName,
         final String ingredient) throws SubjectNotFoundException {
      Subject subject = subjectDA.getSubject(subjectName);
      subject.addRefusedIngredient(ingredient);
      subjectDA.modifySubject(subject);
   }

   /**
    * Adds a new subject with given name to storage.
    * 
    * @param subjectName
    *           name of Subject to add.
    */
   public void addSubject(final String subjectName) {
      subjectDA.addSubject(new Subject(subjectName));
   }

   /**
    * Generates a number of random subjects to file.
    * 
    * @param numberOfSubjects
    *           Number of Subjects to generate.
    * @param maxImportance
    *           Maximum importance of a subject
    * @param numberOfPrefsMean
    *           The mean number of preferences to generate for each Subject.
    * @param numberOfPrefsDev
    *           The standard deviation in number of preferences per Subject.
    * @param numberOfCats
    *           Number of refused categories to generate per Subject.
    * @param numberOfIngs
    *           Number of refused ingredients to generate per Subject.
    * @param nutrReqs
    *           Number of nutrition requirements for a subject.
    * @param mealPs
    *           Number of desired meal parameters for a subject.
    * @param recipes
    *           List of Recipes to choose from.
    * @param subjectFile
    *           Name of file to save to.
    */
   public void generateSubjectsToFile(final int numberOfSubjects,
         final int maxImportance, final int numberOfPrefsMean,
         final int numberOfPrefsDev, final int numberOfCats,
         final int numberOfIngs, final int nutrReqs, final int mealPs,
         final List<Recipe> recipes, final String subjectFile) {
      // System.out.println("generating random subjects.");
      final List<Subject> subjects = SubjectDA.generateRandomSubjects(
            numberOfSubjects, maxImportance, numberOfPrefsMean,
            numberOfPrefsDev, numberOfCats, numberOfIngs, nutrReqs, mealPs,
            recipes);
      // System.out.println("Saving generated subjects...");
      subjectDA.saveGeneratedSubjects(subjects, subjectFile);
   }

   /**
    * **Used for testing** Returns all subjects in storage.
    * 
    * @return ArrayList of Subject.
    */
   public ArrayList<Subject> getAllSubjects() {
      return subjectDA.getAllSubjects();
   }

   public String getDesiredAvailability(String subjectName)
         throws SubjectNotFoundException {
      Subject subject = subjectDA.getSubject(subjectName);
      switch (subject.getDesiredAvailability()) {
      case 1:
         return "low";
      case 2:
         return "medium";
      case 3:
         return "high";
      }
      return "unknown";
   }

   public String getDesiredCost(String subjectName)
         throws SubjectNotFoundException {
      Subject subject = subjectDA.getSubject(subjectName);
      switch (subject.getDesiredCost()) {
      case 1:
         return "low";
      case 2:
         return "medium";
      case 3:
         return "high";
      }
      return "unknown";
   }

   public String getDesiredDifficulty(String subjectName)
         throws SubjectNotFoundException {
      Subject subject = subjectDA.getSubject(subjectName);
      switch (subject.getDesiredDifficulty()) {
      case 1:
         return "easy";
      case 2:
         return "medium";
      case 3:
         return "hard";
      }
      return "unknown";
   }

   public String getDesiredTime(String subjectName)
         throws SubjectNotFoundException {
      Subject subject = subjectDA.getSubject(subjectName);
      switch (subject.getDesiredTime()) {
      case 1:
         return "short";
      case 2:
         return "medium";
      case 3:
         return "long";
      }
      return "unknown";
   }

   public String getDesiredVariation(String subjectName)
         throws SubjectNotFoundException {
      Subject subject = subjectDA.getSubject(subjectName);
      switch (subject.getDesiredVariation()) {
      case 1:
         return "low";
      case 2:
         return "medium";
      case 3:
         return "high";
      }
      return "unknown";
   }

   /**
    * Retrieves a subject's generated preferences.
    * 
    * @param subjectName
    *           Name of subject.
    * @return Map of <String, Integer> or null.
    * @throws SubjectNotFoundException
    *            If the subject is not in the database.
    */
   public Map<String, Preference> getGeneratedPreferences(
         final String subjectName) throws SubjectNotFoundException {
      return subjectDA.getSubject(subjectName).getGeneratedPreferences();
   }

   /**
    * Retrieves a subject's maximum value for a specific nutrional value.
    * 
    * @param subjectName
    *           Name of subject.
    * @param nutritionType
    *           carbohydrates, energyContent, fat, protein, cholesterol, natrium
    *           or calcium
    * @return Desired maximum value (int) or null.
    * @throws SubjectNotFoundException
    *            If the subject is not in the database.
    */
   public Measurement getMaxNutrition(final String subjectName,
         final String nutritionType) throws SubjectNotFoundException {
      if (nutritionType.equalsIgnoreCase("carbohydrates")) {
         return subjectDA.getSubject(subjectName).getMaxCarbohydrates();
      } else if (nutritionType.equalsIgnoreCase("energycontent")) {
         return subjectDA.getSubject(subjectName).getMaxEnergyContent();
      } else if (nutritionType.equalsIgnoreCase("fat")) {
         return subjectDA.getSubject(subjectName).getMaxFat();
      } else if (nutritionType.equalsIgnoreCase("protein")) {
         return subjectDA.getSubject(subjectName).getMaxProtein();
      } else if (nutritionType.equalsIgnoreCase("cholesterol")) {
         return subjectDA.getSubject(subjectName).getMaxCholesterol();
      } else if (nutritionType.equalsIgnoreCase("natrium")) {
         return subjectDA.getSubject(subjectName).getMaxSodium();
      } else if (nutritionType.equalsIgnoreCase("calcium")) {
         return subjectDA.getSubject(subjectName).getMaxCalcium();
      } else {
         return null;
      }
   }

   /**
    * Retrieves a subject's minimum value for a specific nutrional value.
    * 
    * @param subjectName
    *           Name of subject.
    * @param nutritionType
    *           carbohydrates, energyContent, fat, protein, cholesterol, natrium
    *           or calcium
    * @return Desired minimum value (int) or null.
    * @throws SubjectNotFoundException
    *            If the subject is not in the database.
    */
   public Measurement getMinNutrition(final String subjectName,
         final String nutritionType) throws SubjectNotFoundException {
      if (nutritionType.equalsIgnoreCase("carbohydrates")) {
         return subjectDA.getSubject(subjectName).getMinCarbohydrates();
      } else if (nutritionType.equalsIgnoreCase("energycontent")) {
         return subjectDA.getSubject(subjectName).getMinEnergyContent();
      } else if (nutritionType.equalsIgnoreCase("fat")) {
         return subjectDA.getSubject(subjectName).getMinFat();
      } else if (nutritionType.equalsIgnoreCase("protein")) {
         return subjectDA.getSubject(subjectName).getMinProtein();
      } else if (nutritionType.equalsIgnoreCase("cholesterol")) {
         return subjectDA.getSubject(subjectName).getMinCholesterol();
      } else if (nutritionType.equalsIgnoreCase("natrium")) {
         return subjectDA.getSubject(subjectName).getMinSodium();
      } else if (nutritionType.equalsIgnoreCase("calcium")) {
         return subjectDA.getSubject(subjectName).getMinCalcium();
      } else {
         return null;
      }
   }

   /**
    * Retrieves a subject's preferences.
    * 
    * @param subjectName
    *           Name of subject.
    * @return Map of <String, Integer> or null.
    * @throws SubjectNotFoundException
    *            If the subject is not in the database.
    */
   public Map<String, Preference> getPreferences(final String subjectName)
         throws SubjectNotFoundException {
      return subjectDA.getSubject(subjectName).getPreferences();
   }

   /**
    * Retrieves a subject's refused categories.
    * 
    * @param subjectName
    *           Name of subject.
    * @return A List of String or null.
    * @throws SubjectNotFoundException
    *            If the subject is not in the database.
    */
   public List<String> getRefusedCategories(final String subjectName)
         throws SubjectNotFoundException {
      return subjectDA.getSubject(subjectName).getRefusedCategoriesList();
   }

   /**
    * Retrieves a subject's refused ingredients.
    * 
    * @param subjectName
    *           Name of subject.
    * @return A List of String or null.
    * @throws SubjectNotFoundException
    *            If the subject is not in the database.
    */
   public List<String> getRefusedIngredients(final String subjectName)
         throws SubjectNotFoundException {
      return subjectDA.getSubject(subjectName).getRefusedIngredientsList();
   }

   /**
    * Retrieves a subject with given name from storage.
    * 
    * @param subjectName
    *           Name of subject to retrieve.
    * @return A Subject or null.
    * @throws SubjectNotFoundException
    *            If the subject is not in the database.
    */
   public Subject getSubject(final String subjectName)
         throws SubjectNotFoundException {
      return subjectDA.getSubject(subjectName);
   }

   /**
    * Returns this managers subject data access object, needed by the Solver's
    * generatePlan method.
    * 
    * @return SubjectDA object.
    */
   public SubjectDA getSubjectDA() {
      return subjectDA;
   }

   /**
    * **Used for testing** Loads a list of subjects in internal (benchmark)
    * format from file.
    * 
    * @param subjectFile
    *           Name of file to load from.
    */
   public void loadGeneratedSubjects(final String subjectFile) {
      subjectDA.loadGeneratedSubjects(subjectFile);
   }

   /**
    * Removed a refused category from the specified subject.
    * 
    * @param subject
    *           Subject to remove category from
    * @param category
    *           IngredientCategory to remove
    */
   public void removeRefusedCategory(String subject, String category)
         throws SubjectNotFoundException {
      Subject targetSubject = subjectDA.getSubject(subject);
      targetSubject.removeRefusedCategory(category);
      subjectDA.modifySubject(targetSubject);
   }

   /**
    * Removed a refused ingredient from the specified subject.
    * 
    * @param subject
    *           Subject to remove ingredient from
    * @param ingredient
    *           Ingredient to remove
    */
   public void removeRefusedIngredient(String subject, String ingredient)
         throws SubjectNotFoundException {
      Subject targetSubject = subjectDA.getSubject(subject);
      targetSubject.removeRefusedIngredient(ingredient);
      subjectDA.modifySubject(targetSubject);
   }

   public void removeSubject(final String subjectName) {
      subjectDA.removeSubject(subjectName);
   }

   /**
    * ** Used for testing** Saves a list of subjects in internal (benchmark)
    * format to file.
    * 
    * @param subjects
    *           List of Subjects to save.
    * @param fileName
    *           Name of file to save to
    */
   public void saveGeneratedSubjects(final List<Subject> subjects,
         final String fileName) {
      subjectDA.saveGeneratedSubjects(subjects, fileName);
   }

   public void setDesiredAvailability(String subjectName, String level)
         throws SubjectNotFoundException {
      Subject subject = subjectDA.getSubject(subjectName);
      subject.setDesiredAvailability(level);
      subjectDA.modifySubject(subject);
   }

   public void setDesiredCost(String subjectName, String desiredCost)
         throws SubjectNotFoundException {
      Subject subject = subjectDA.getSubject(subjectName);
      if (desiredCost.equalsIgnoreCase("low")) {
         subject.setDesiredCost(1);
      } else if (desiredCost.equalsIgnoreCase("medium")) {
         subject.setDesiredCost(2);
      } else {
         subject.setDesiredCost(3);
      }
      subjectDA.modifySubject(subject);
   }

   public void setDesiredDifficulty(String subjectName, String desiredDifficulty)
         throws SubjectNotFoundException {
      Subject subject = subjectDA.getSubject(subjectName);
      if (desiredDifficulty.equalsIgnoreCase("easy")) {
         subject.setDesiredDifficulty(1);
      } else if (desiredDifficulty.equalsIgnoreCase("medium")) {
         subject.setDesiredDifficulty(2);
      } else {
         subject.setDesiredDifficulty(3);
      }
      subjectDA.modifySubject(subject);
   }

   public void setDesiredTime(String subjectName, String desiredTime)
         throws SubjectNotFoundException {
      Subject subject = subjectDA.getSubject(subjectName);
      if (desiredTime.equalsIgnoreCase("short")) {
         subject.setDesiredTime(1);
      } else if (desiredTime.equalsIgnoreCase("medium")) {
         subject.setDesiredTime(2);
      } else {
         subject.setDesiredTime(3);
      }
      subjectDA.modifySubject(subject);
   }

   public void setDesiredVariation(String subjectName, String level)
         throws SubjectNotFoundException {
      Subject subject = subjectDA.getSubject(subjectName);
      subject.setDesiredVariation(level);
      subjectDA.modifySubject(subject);
   }

   /**
    * Sets the desired maximum value for a nutritional value of a subject in
    * storage.
    * 
    * @param subjectName
    *           name of Subject.
    * @param nutritionType
    *           carbohydrates, energyContent, fat, protein, cholesterol, natrium
    *           or calcium
    * @param desiredValue
    *           desired maximum nutritional value of meals for this Subject.
    * @throws SubjectNotFoundException
    *            if a subject with that name is not in the database.
    */
   public void setMaxNutritionValue(final String subjectName,
         final String nutritionType, final Measurement desiredValue)
         throws SubjectNotFoundException {
      Subject subject = subjectDA.getSubject(subjectName);
      if (nutritionType.equalsIgnoreCase("carbohydrates")) {
         subject.setMaxCarbohydrates(desiredValue);
      } else if (nutritionType.equalsIgnoreCase("energycontent")) {
         subject.setMaxEnergyContent(desiredValue);
      } else if (nutritionType.equalsIgnoreCase("fat")) {
         subject.setMaxFat(desiredValue);
      } else if (nutritionType.equalsIgnoreCase("protein")) {
         subject.setMaxProtein(desiredValue);
      } else if (nutritionType.equalsIgnoreCase("cholesterol")) {
         subject.setMaxCholesterol(desiredValue);
      } else if (nutritionType.equalsIgnoreCase("natrium")) {
         subject.setMaxSodium(desiredValue);
      } else if (nutritionType.equalsIgnoreCase("calcium")) {
         subject.setMaxCalcium(desiredValue);
      }
      subjectDA.modifySubject(subject);
   }

   /**
    * Sets the desired minimum value for a nutritional value of a subject in
    * storage.
    * 
    * @param subjectName
    *           name of Subject.
    * @param nutritionType
    *           carbohydrates, energyContent, fat, protein, cholesterol, natrium
    *           or calcium
    * @param desiredValue
    *           desired minimum nutritional value of meals for this Subject.
    * @throws SubjectNotFoundException
    *            if a subject with that name is not in the database.
    */
   public void setMinNutritionValue(final String subjectName,
         final String nutritionType, final Measurement desiredValue)
         throws SubjectNotFoundException {
      Subject subject = subjectDA.getSubject(subjectName);
      if (nutritionType.equalsIgnoreCase("carbohydrates")) {
         subject.setMinCarbohydrates(desiredValue);
      } else if (nutritionType.equalsIgnoreCase("energycontent")) {
         subject.setMinEnergyContent(desiredValue);
      } else if (nutritionType.equalsIgnoreCase("fat")) {
         subject.setMinFat(desiredValue);
      } else if (nutritionType.equalsIgnoreCase("protein")) {
         subject.setMinProtein(desiredValue);
      } else if (nutritionType.equalsIgnoreCase("cholesterol")) {
         subject.setMinCholesterol(desiredValue);
      } else if (nutritionType.equalsIgnoreCase("natrium")) {
         subject.setMinSodium(desiredValue);
      } else if (nutritionType.equalsIgnoreCase("calcium")) {
         subject.setMinCalcium(desiredValue);
      }
      subjectDA.modifySubject(subject);
   }
}
