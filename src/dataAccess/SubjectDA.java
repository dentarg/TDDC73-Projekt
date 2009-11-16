/*
 * Created using IntelliJ IDEA. User: x04nicsu Date: 2005-jan-13 Time: 14:51:31
 */
package dataAccess;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import dataObjects.Preference;
import dataObjects.Recipe;
import dataObjects.Subject;
import dataObjects.units.GramMeasurement;
import dataObjects.units.Measurement;
import dataObjects.units.MilliGramMeasurement;
import dataStorage.SubjectDS;
import exceptions.SubjectNotFoundException;

/**
 * Class used for subject data access.
 */
public class SubjectDA {
   /**
    * Generates a number of random subjects for testing purposes. <b>Intendet
    * for testing</b>
    * 
    * @param numberOfSubjects
    *           Number of Subjects to generate.
    * @param maximumImportance
    *           Maximum importance of a subject
    * @param numberOfPreferencesMean
    *           The mean number of preferences to generate for each Subject.
    * @param numberOfPreferencesDev
    *           The standard deviation in number of preferences per Subject.
    * @param numberOfRefusedCategories
    *           Number of refused categories to generate per Subject.
    * @param numberOfRefusedIngredients
    *           Number of refused ingredients to generate per Subject.
    * @param nutritionRequirements
    *           Nutrition requirements per subject.
    * @param mealParameters
    *           Meal parameters per subject.
    * @param recipes
    *           List of Recipes to choose from.
    * @return List containing the random Subjects.
    */
   public static List<Subject> generateRandomSubjects(int numberOfSubjects,
         int maximumImportance, int numberOfPreferencesMean,
         int numberOfPreferencesDev, int numberOfRefusedCategories,
         int numberOfRefusedIngredients, int nutritionRequirements,
         int mealParameters, List<Recipe> recipes) {
      Random RNG = new Random();
      int numberOfRecipes = recipes.size();
      List<Subject> subjects = new ArrayList<Subject>();
      Subject subject;
      String recipeName;
      Recipe randomRecipe;
      int value1;
      int value2;
      for (int index = 0; index < numberOfSubjects; index++) {
         subject = new Subject("RandomPerson" + index);
         value1 = Math.abs(numberOfPreferencesMean
               + (int) (RNG.nextGaussian() * numberOfPreferencesDev));
         for (int index2 = 0; index2 < value1; index2++) {
            recipeName = recipes.get(Math.abs(RNG.nextInt() % numberOfRecipes))
                  .getName();
            subject.addPreference(new Preference(recipeName, Math.abs(RNG
                  .nextInt() % 5) + 1));
         }
         for (int index2 = 0; index2 < numberOfRefusedIngredients; index2++) {
            randomRecipe = recipes.get(Math
                  .abs(RNG.nextInt() % numberOfRecipes));
            subject.addRefusedIngredient(randomRecipe.getIngredients().get(0)
                  .getName());
         }
         for (int index2 = 0; index2 < numberOfRefusedCategories; index2++) {
            randomRecipe = recipes.get(Math
                  .abs(RNG.nextInt() % numberOfRecipes));
            subject.addRefusedCategory(randomRecipe.getCategories().get(0));
         }

         boolean carbsSelected = false;
         boolean energySelected = false;
         boolean fatSelected = false;
         boolean proteinSelected = false;
         boolean cholesterolSelected = false;
         boolean natriumSelected = false;
         boolean calciumSelected = false;
         for (int index2 = 0; index2 < nutritionRequirements; index2++) {
            value1 = Math.abs(RNG.nextInt() % 7);
            switch (value1) {
            case 0:
               if (carbsSelected) {
                  index2--;
                  break;
               }
               value1 = Math.abs((int) (65 + RNG.nextGaussian() * 65));
               value2 = Math.abs((int) (65 + RNG.nextGaussian() * 65));
               subject.setMinCarbohydrates(new GramMeasurement(Math.min(value1,
                     value2)));
               subject.setMaxCarbohydrates(new GramMeasurement(Math.max(value1,
                     value2)));
               carbsSelected = true;
               break;

            case 1:
               if (energySelected) {
                  index2--;
                  break;
               }
               value1 = Math.abs((int) (2100 + RNG.nextGaussian() * 300));
               value2 = Math.abs((int) (2100 + RNG.nextGaussian() * 300));
               subject.setMinEnergyContent(Measurement.createMeasurement(
                     "kcal", (float) Math.min(value1, value2)));
               subject.setMaxEnergyContent(Measurement.createMeasurement(
                     "kcal", (float) Math.max(value1, value2)));
               energySelected = true;
               break;

            case 2:
               if (fatSelected) {
                  index2--;
                  break;
               }
               value1 = Math.abs((int) (27 + RNG.nextGaussian() * 7));
               value2 = Math.abs((int) (27 + RNG.nextGaussian() * 7));
               subject.setMinFat(new GramMeasurement(Math.min(value1, value2)));
               subject.setMaxFat(new GramMeasurement(Math.max(value1, value2)));
               fatSelected = true;
               break;

            case 3:
               if (proteinSelected) {
                  index2--;
                  break;
               }
               value1 = Math.abs((int) (55 + RNG.nextGaussian() * 8));
               value2 = Math.abs((int) (55 + RNG.nextGaussian() * 8));
               subject.setMinProtein(new GramMeasurement(Math.min(value1,
                     value2)));
               subject.setMaxProtein(new GramMeasurement(Math.max(value1,
                     value2)));
               proteinSelected = true;
               break;

            case 4:
               if (cholesterolSelected) {
                  index2--;
                  break;
               }
               value1 = Math.abs((int) (100 + RNG.nextGaussian() * 100));
               value2 = Math.abs((int) (100 + RNG.nextGaussian() * 100));
               subject.setMinCholesterol(new MilliGramMeasurement(Math.min(
                     value1, value2)));
               subject.setMaxCholesterol(new MilliGramMeasurement(Math.max(
                     value1, value2)));
               cholesterolSelected = true;
               break;

            case 5:
               if (natriumSelected) {
                  index2--;
                  break;
               }
               value1 = Math.abs((int) (60 + RNG.nextGaussian() * 60));
               value2 = Math.abs((int) (60 + RNG.nextGaussian() * 60));
               subject.setMinSodium(new MilliGramMeasurement(Math.min(value1,
                     value2)));
               subject.setMaxSodium(new MilliGramMeasurement(Math.max(value1,
                     value2)));
               natriumSelected = true;
               break;

            case 6:
               if (calciumSelected) {
                  index2--;
                  break;
               }
               value1 = Math.abs((int) (1000 + RNG.nextGaussian() * 200));
               value2 = Math.abs((int) (1000 + RNG.nextGaussian() * 200));
               subject.setMinCalcium(new MilliGramMeasurement(Math.min(value1,
                     value2)));
               subject.setMaxCalcium(new MilliGramMeasurement(Math.max(value1,
                     value2)));
               calciumSelected = true;
               break;
            }
         }

         for (int index2 = 0; index2 < mealParameters; index2++) {
            value1 = Math.abs(RNG.nextInt() % 3);
            boolean timeSelected = false;
            boolean costSelected = false;
            boolean difficultySelected = false;
            switch (value1) {
            case 0:
               if (timeSelected) {
                  index2--;
                  break;
               }
               value1 = Math.abs(RNG.nextInt() % 3) + 1;
               subject.setDesiredTime(value1);
               timeSelected = true;
               break;

            case 1:
               if (costSelected) {
                  index2--;
                  break;
               }
               value1 = Math.abs(RNG.nextInt() % 3) + 1;
               subject.setDesiredCost(value1);
               costSelected = true;
               break;

            case 2:
               if (difficultySelected) {
                  index2--;
                  break;
               }
               value1 = Math.abs(RNG.nextInt() % 3) + 1;
               subject.setDesiredDifficulty(value1);
               difficultySelected = true;
               break;
            }
         }

         value1 = Math.abs(RNG.nextInt() % maximumImportance) + 1;
         subject.setImportance(value1);

         subjects.add(subject);
      }
      return subjects;
   }

   /**
    * The datastorage object to use.
    */
   private SubjectDS subjectDS;

   /**
    * Create a new subject data acess object.
    */
   public SubjectDA() {
      subjectDS = new SubjectDS();
   }

   /**
    * Adds a subject to underlying storage.
    * 
    * @param s
    *           Subject to add.
    */
   public void addSubject(Subject s) {
      subjectDS.add(s);
   }

   /**
    * Returns all subjects in underlying storage.
    * 
    * @return ArrayList of Subject.
    */
   public ArrayList<Subject> getAllSubjects() {
      return subjectDS.getAllSubjects();
   }

   /**
    * Gets a subject from underlying storage.
    * 
    * @param name
    *           Name of subject to get.
    * @return Subject or null.
    * @throws SubjectNotFoundException
    */
   public Subject getSubject(String name) throws SubjectNotFoundException {
      return subjectDS.get(name);
   }

   /**
    * Loads subjects to underlying storage from a file in internal format.
    * 
    * @param fileName
    *           name of file to load from.
    */
   public void loadGeneratedSubjects(String fileName) {
      subjectDS.readGeneratedFromFile(fileName);
   }

   /**
    * Modifies a subject in underlying storage.
    * 
    * @param s
    *           Modified Subject.
    */
   public void modifySubject(Subject s) {
      subjectDS.set(s);
   }

   /**
    * Removes a subject from underlying storage.
    * 
    * @param name
    *           Name of Subject to revmove.
    */
   public void removeSubject(String name) {
      subjectDS.remove(name);
   }

   /**
    * Saves subjects to a file in internal format.
    * 
    * @param subjects
    *           List of Subjects to save.
    * @param fileName
    *           Name of file to save to.
    */
   public void saveGeneratedSubjects(List<Subject> subjects, String fileName) {
      subjectDS.saveGeneratedToFile(subjects, fileName);
   }
}
