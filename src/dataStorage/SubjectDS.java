package dataStorage;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import data.Settings;
import dataObjects.IngredientCategory;
import dataObjects.Preference;
import dataObjects.Subject;
import dataObjects.units.Measurement;
import exceptions.SubjectNotFoundException;

/**
 * Class for subject data storage.
 */
public class SubjectDS {
   HashMap<String, Subject> subjectMap;

   /**
    * Create a new subject data storage object.
    */
   public SubjectDS() {
      subjectMap = new HashMap<String, Subject>();
      readFromFile();
   }

   /**
    * Adds a subject to storage.
    * 
    * @param s
    *           Subject to add.
    */
   public void add(Subject s) {
      subjectMap.put(s.getName().toLowerCase(), s);
      saveToFile();
   }

   /**
    * Gets a subject from storage.
    * 
    * @param name
    *           Name of Subject.
    * @return Subject or null.
    * @throws SubjectNotFoundException
    */
   public Subject get(String name) throws SubjectNotFoundException {
      Subject tempSubject = subjectMap.get(name.toLowerCase());
      if (tempSubject == null) {
         throw new SubjectNotFoundException();
      }
      return tempSubject;
   }

   /**
    * Gets all subjects from storage.
    * 
    * @return ArrayList of Subject.
    */
   public ArrayList<Subject> getAllSubjects() {
      return new ArrayList<Subject>(subjectMap.values());
   }

   private void readCalcium(Subject subject, BufferedReader in)
         throws IOException {
      if (!readNextTag(in).equalsIgnoreCase("<minCalcium>")) { //$NON-NLS-1$
         throw new IOException("<minCalcium> expected"); //$NON-NLS-1$
      }
      subject.setMinCalcium(Measurement.createFromString(readNextValue(in)));
      if (!readNextTag(in).equalsIgnoreCase("</minCalcium>")) { //$NON-NLS-1$
         throw new IOException("</minCalcium> expected"); //$NON-NLS-1$
      }
      if (!readNextTag(in).equalsIgnoreCase("<maxCalcium>")) { //$NON-NLS-1$
         throw new IOException("<maxCalcium> expected"); //$NON-NLS-1$
      }
      subject.setMaxCalcium(Measurement.createFromString(readNextValue(in)));
      if (!readNextTag(in).equalsIgnoreCase("</maxCalcium>")) { //$NON-NLS-1$
         throw new IOException("</maxCalcium> expected"); //$NON-NLS-1$
      }
   }

   private void readCarbohydrates(Subject subject, BufferedReader in)
         throws IOException {
      if (!readNextTag(in).equalsIgnoreCase("<minCarbohydrates>")) { //$NON-NLS-1$
         throw new IOException("<minCarbohydrates> expected"); //$NON-NLS-1$
      }
      subject.setMinCarbohydrates(Measurement
            .createFromString(readNextValue(in)));
      if (!readNextTag(in).equalsIgnoreCase("</minCarbohydrates>")) { //$NON-NLS-1$
         throw new IOException("</minCarbohydrates> expected"); //$NON-NLS-1$
      }
      if (!readNextTag(in).equalsIgnoreCase("<maxCarbohydrates>")) { //$NON-NLS-1$
         throw new IOException("<maxCarbohydrates> expected"); //$NON-NLS-1$
      }
      subject.setMaxCarbohydrates(Measurement
            .createFromString(readNextValue(in)));
      if (!readNextTag(in).equalsIgnoreCase("</maxCarbohydrates>")) { //$NON-NLS-1$
         throw new IOException("</maxCarbohydrates> expected"); //$NON-NLS-1$
      }
   }

   private void readCholesterol(Subject subject, BufferedReader in)
         throws IOException {
      if (!readNextTag(in).equalsIgnoreCase("<minCholesterol>")) { //$NON-NLS-1$
         throw new IOException("<minCholesterol> expected"); //$NON-NLS-1$
      }
      subject
            .setMinCholesterol(Measurement.createFromString(readNextValue(in)));
      if (!readNextTag(in).equalsIgnoreCase("</minCholesterol>")) { //$NON-NLS-1$
         throw new IOException("</minCholesterol> expected"); //$NON-NLS-1$
      }
      if (!readNextTag(in).equalsIgnoreCase("<maxCholesterol>")) { //$NON-NLS-1$
         throw new IOException("<maxCholesterol> expected"); //$NON-NLS-1$
      }
      subject
            .setMaxCholesterol(Measurement.createFromString(readNextValue(in)));
      if (!readNextTag(in).equalsIgnoreCase("</maxCholesterol>")) { //$NON-NLS-1$
         throw new IOException("</maxCholesterol> expected"); //$NON-NLS-1$
      }
   }

   private void readEnergyContent(Subject subject, BufferedReader in)
         throws IOException {
      if (!readNextTag(in).equalsIgnoreCase("<minEnergyContent>")) { //$NON-NLS-1$
         throw new IOException("<minEnergyContent> expected"); //$NON-NLS-1$
      }
      subject.setMinEnergyContent(Measurement
            .createFromString(readNextValue(in)));
      if (!readNextTag(in).equalsIgnoreCase("</minEnergyContent>")) { //$NON-NLS-1$
         throw new IOException("</minEnergyContent> expected"); //$NON-NLS-1$
      }
      if (!readNextTag(in).equalsIgnoreCase("<maxEnergyContent>")) { //$NON-NLS-1$
         throw new IOException("<maxEnergyContent> expected"); //$NON-NLS-1$
      }
      subject.setMaxEnergyContent(Measurement
            .createFromString(readNextValue(in)));
      if (!readNextTag(in).equalsIgnoreCase("</maxEnergyContent>")) { //$NON-NLS-1$
         throw new IOException("</maxEnergyContent> expected"); //$NON-NLS-1$
      }
   }

   private void readFat(Subject subject, BufferedReader in) throws IOException {
      if (!readNextTag(in).equalsIgnoreCase("<minFat>")) { //$NON-NLS-1$
         throw new IOException("<minFat> expected"); //$NON-NLS-1$
      }
      subject.setMinFat(Measurement.createFromString(readNextValue(in)));
      if (!readNextTag(in).equalsIgnoreCase("</minFat>")) { //$NON-NLS-1$
         throw new IOException("</minFat> expected"); //$NON-NLS-1$
      }
      if (!readNextTag(in).equalsIgnoreCase("<maxFat>")) { //$NON-NLS-1$
         throw new IOException("<maxFat> expected"); //$NON-NLS-1$
      }
      subject.setMaxFat(Measurement.createFromString(readNextValue(in)));
      if (!readNextTag(in).equalsIgnoreCase("</maxFat>")) { //$NON-NLS-1$
         throw new IOException("</maxFat> expected"); //$NON-NLS-1$
      }
   }

   /**
    * Reads subjects from file in a XML-like format.
    */
   public void readFromFile() {
      String s;
      subjectMap.clear();
      try {
         InputStreamReader ir = new InputStreamReader(ClassLoader
               .getSystemResourceAsStream(Settings
                     .getFilePath("SubjectDS.SubjectsDataFile")));
         BufferedReader in = new BufferedReader(ir);
         if ((s = readNextTag(in)) == null) {
            return;
         } else if (!s.equalsIgnoreCase("<subjectList>")) { //$NON-NLS-1$
            throw new IOException("<subjectList> expected"); //$NON-NLS-1$
         }
         readSubjects(in);
         in.close();
      } catch (FileNotFoundException e) {
         e.printStackTrace();
      } catch (IOException e) {
         e.printStackTrace();
      }
   }

   /**
    * Reads subjects from file in a XML-like format.
    * 
    * @param fileName
    *           The file name to load from, ending exluded.
    */
   public void readGeneratedFromFile(String fileName) {
      subjectMap.clear();
      try {
         BufferedReader in = new BufferedReader(new FileReader(
               "data/" + fileName + ".data")); //$NON-NLS-1$ //$NON-NLS-2$
         if (!readNextTag(in).equalsIgnoreCase("<subjectList>")) { //$NON-NLS-1$
            throw new IOException("<subjectList> expected"); //$NON-NLS-1$
         }
         readSubjects(in);
         in.close();
      } catch (FileNotFoundException e) {
         e.printStackTrace();
      } catch (IOException e) {
         e.printStackTrace();
      }
   }

   private void readNatrium(Subject subject, BufferedReader in)
         throws IOException {
      if (!readNextTag(in).equalsIgnoreCase("<minNatrium>")) { //$NON-NLS-1$
         throw new IOException("<minNatrium> expected"); //$NON-NLS-1$
      }
      subject.setMinSodium(Measurement.createFromString(readNextValue(in)));
      if (!readNextTag(in).equalsIgnoreCase("</minNatrium>")) { //$NON-NLS-1$
         throw new IOException("</minNatrium> expected"); //$NON-NLS-1$
      }
      if (!readNextTag(in).equalsIgnoreCase("<maxNatrium>")) { //$NON-NLS-1$
         throw new IOException("<maxNatrium> expected"); //$NON-NLS-1$
      }
      subject.setMaxSodium(Measurement.createFromString(readNextValue(in)));
      if (!readNextTag(in).equalsIgnoreCase("</maxNatrium>")) { //$NON-NLS-1$
         throw new IOException("</maxNatrium> expected"); //$NON-NLS-1$
      }
   }

   private String readNextTag(BufferedReader in) {
      char[] tag = new char[100];
      tag[0] = '<';
      int pos = 1;
      try {
         do {
            tag[pos] = (char) in.read();
         } while (tag[pos] == '\n' || tag[pos] == '\r');

         if (tag[pos] == '<') {
            pos--;
         }
         do {
            pos++;
            tag[pos] = (char) in.read();
         } while (tag[pos] != '>');
      } catch (IOException e) {
         e.printStackTrace(); // To change body of catch statement use File |
                              // Settings | File Templates.
      }
      return new String(tag, 0, pos + 1);
   }

   private String readNextValue(BufferedReader in) {
      char[] value = new char[100];
      int pos = 0;
      try {
         do {
            value[pos] = (char) in.read();
         } while (value[pos] == '\n' || value[pos] == '\r');

         do {
            if (value[pos] != '\n' && value[pos] != '\r') {
               pos++;
            }
            value[pos] = (char) in.read();
         } while (value[pos] != '<');
      } catch (IOException e) {
         e.printStackTrace(); // To change body of catch statement use File |
                              // Settings | File Templates.
      }
      return new String(value, 0, pos);
   }

   private void readNutrition(Subject subject, BufferedReader in) {
      try {
         readCarbohydrates(subject, in);
         readEnergyContent(subject, in);
         readFat(subject, in);
         readProtein(subject, in);
         readCholesterol(subject, in);
         readNatrium(subject, in);
         readCalcium(subject, in);
      } catch (IOException e) {
         e.printStackTrace(); // To change body of catch statement use File |
                              // Settings | File Templates.
      }
   }

   /**
    * Reads a Subject's preferences from file in a XML-like format
    * 
    * @param subject
    *           The Subject whos preferences to read
    * @param in
    *           BufferedReader connected to the input file
    */
   private void readPreferences(Subject subject, BufferedReader in) {
      String s;
      Preference pref;
      try {
         while (!(s = readNextTag(in)).equalsIgnoreCase("</preferences>")) { //$NON-NLS-1$
            if (!s.equalsIgnoreCase("<preference>")) { //$NON-NLS-1$
               throw new IOException("<preference> expected"); //$NON-NLS-1$
            }
            if (!readNextTag(in).equalsIgnoreCase("<recipe>")) { //$NON-NLS-1$
               throw new IOException("<recipe> expected"); //$NON-NLS-1$
            }
            s = readNextValue(in);
            if (!readNextTag(in).equalsIgnoreCase("</recipe>")) { //$NON-NLS-1$
               throw new IOException("</recipe> expected"); //$NON-NLS-1$
            }
            if (!readNextTag(in).equalsIgnoreCase("<rating>")) { //$NON-NLS-1$
               throw new IOException("<rating> expected"); //$NON-NLS-1$
            }
            pref = new Preference(s, Integer.parseInt(readNextValue(in)));
            if (!readNextTag(in).equalsIgnoreCase("</rating>")) { //$NON-NLS-1$
               throw new IOException("</rating> expected"); //$NON-NLS-1$
            }
            if (!readNextTag(in).equalsIgnoreCase("</preference>")) { //$NON-NLS-1$
               throw new IOException("</preference> expected"); //$NON-NLS-1$
            }
            subject.addPreference(pref);
         }
      } catch (IOException e) {
         e.printStackTrace();
      }
   }

   private void readProtein(Subject subject, BufferedReader in)
         throws IOException {
      if (!readNextTag(in).equalsIgnoreCase("<minProtein>")) { //$NON-NLS-1$
         throw new IOException("<minProtein> expected"); //$NON-NLS-1$
      }
      subject.setMinProtein(Measurement.createFromString(readNextValue(in)));
      if (!readNextTag(in).equalsIgnoreCase("</minProtein>")) { //$NON-NLS-1$
         throw new IOException("</minProtein> expected"); //$NON-NLS-1$
      }
      if (!readNextTag(in).equalsIgnoreCase("<maxProtein>")) { //$NON-NLS-1$
         throw new IOException("<maxProtein> expected"); //$NON-NLS-1$
      }
      subject.setMaxProtein(Measurement.createFromString(readNextValue(in)));
      if (!readNextTag(in).equalsIgnoreCase("</maxProtein>")) { //$NON-NLS-1$
         throw new IOException("</maxProtein> expected"); //$NON-NLS-1$
      }
   }

   private void readRefusedCategories(Subject subject, BufferedReader in) {
      String s;
      String category;
      try {
         while (!(s = readNextTag(in)).equalsIgnoreCase("</refusedCategories>")) { //$NON-NLS-1$
            if (!s.equalsIgnoreCase("<refusedCategory>")) { //$NON-NLS-1$
               throw new IOException("<refusedCategory> expected"); //$NON-NLS-1$
            }
            category = readNextValue(in);
            if (!readNextTag(in).equalsIgnoreCase("</refusedCategory>")) { //$NON-NLS-1$
               throw new IOException("</refusedCategory> expected"); //$NON-NLS-1$
            }
            subject.addRefusedCategory(category);
         }
      } catch (IOException e) {
         e.printStackTrace();
      }
   }

   private void readRefusedIngredients(Subject subject, BufferedReader in) {
      String s;
      String ingredient;
      try {
         while (!(s = readNextTag(in))
               .equalsIgnoreCase("</refusedIngredients>")) { //$NON-NLS-1$
            if (!s.equalsIgnoreCase("<refusedIngredient>")) { //$NON-NLS-1$
               throw new IOException("<refusedIngredient> expected"); //$NON-NLS-1$
            }
            ingredient = readNextValue(in);
            if (!readNextTag(in).equalsIgnoreCase("</refusedIngredient>")) { //$NON-NLS-1$
               throw new IOException("</refusedIngredient> expected"); //$NON-NLS-1$
            }
            subject.addRefusedIngredient(ingredient);
         }
      } catch (IOException e) {
         e.printStackTrace();
      }
   }

   /**
    * Reads a list ofSubjects from file in a XML-like format
    * 
    * @param in
    *           BufferedReader connected to the input file
    */
   private void readSubjects(BufferedReader in) {
      String s;
      Subject subject;
      try {
         while (!(s = readNextTag(in)).equalsIgnoreCase("</subjectList>")) { //$NON-NLS-1$
            if (!s.equalsIgnoreCase("<subject>")) { //$NON-NLS-1$
               throw new IOException("<subject> expected, found " + s); //$NON-NLS-1$
            }
            if (!readNextTag(in).equalsIgnoreCase("<name>")) { //$NON-NLS-1$
               throw new IOException("<name> expected"); //$NON-NLS-1$
            }
            subject = new Subject(readNextValue(in));
            if (!readNextTag(in).equalsIgnoreCase("</name>")) { //$NON-NLS-1$
               throw new IOException("</name> expected"); //$NON-NLS-1$
            }
            if (!readNextTag(in).equalsIgnoreCase("<importance>")) { //$NON-NLS-1$
               throw new IOException("<importance> expected"); //$NON-NLS-1$
            }
            subject.setImportance(Integer.parseInt(readNextValue(in)));
            if (!readNextTag(in).equalsIgnoreCase("</importance>")) { //$NON-NLS-1$
               throw new IOException("</importance> expected"); //$NON-NLS-1$
            }
            if (!readNextTag(in).equalsIgnoreCase("<desiredAvailability>")) { //$NON-NLS-1$
               throw new IOException("<desiredAvailability> expected"); //$NON-NLS-1$
            }
            subject.setDesiredAvailability(Integer.parseInt(readNextValue(in)));
            if (!readNextTag(in).equalsIgnoreCase("</desiredAvailability>")) { //$NON-NLS-1$
               throw new IOException("</desiredAvailability> expected"); //$NON-NLS-1$
            }
            if (!readNextTag(in).equalsIgnoreCase("<desiredVariation>")) { //$NON-NLS-1$
               throw new IOException("<desiredVariation> expected"); //$NON-NLS-1$
            }
            subject.setDesiredVariation(Integer.parseInt(readNextValue(in)));
            if (!readNextTag(in).equalsIgnoreCase("</desiredVariation>")) { //$NON-NLS-1$
               throw new IOException("</desiredVariation> expected"); //$NON-NLS-1$
            }
            if (!readNextTag(in).equalsIgnoreCase("<mealParameters>")) { //$NON-NLS-1$
               throw new IOException("<mealParameters> expected"); //$NON-NLS-1$
            }
            if (!readNextTag(in).equalsIgnoreCase("<mealTime>")) { //$NON-NLS-1$
               throw new IOException("<mealTime> expected"); //$NON-NLS-1$
            }
            subject.setDesiredTime(Integer.parseInt(readNextValue(in)));
            if (!readNextTag(in).equalsIgnoreCase("</mealTime>")) { //$NON-NLS-1$
               throw new IOException("</mealTime> expected"); //$NON-NLS-1$
            }
            if (!readNextTag(in).equalsIgnoreCase("<mealCost>")) { //$NON-NLS-1$
               throw new IOException("<mealCost> expected"); //$NON-NLS-1$
            }
            subject.setDesiredCost(Integer.parseInt(readNextValue(in)));
            if (!readNextTag(in).equalsIgnoreCase("</mealCost>")) { //$NON-NLS-1$
               throw new IOException("</mealCost> expected"); //$NON-NLS-1$
            }
            if (!readNextTag(in).equalsIgnoreCase("<mealDifficulty>")) { //$NON-NLS-1$
               throw new IOException("<mealDifficulty> expected"); //$NON-NLS-1$
            }
            subject.setDesiredDifficulty(Integer.parseInt(readNextValue(in)));
            if (!readNextTag(in).equalsIgnoreCase("</mealDifficulty>")) { //$NON-NLS-1$
               throw new IOException("</mealDifficulty> expected"); //$NON-NLS-1$
            }
            if (!readNextTag(in).equalsIgnoreCase("</mealParameters>")) { //$NON-NLS-1$
               throw new IOException("</mealParameters> expected"); //$NON-NLS-1$
            }
            if (!readNextTag(in).equalsIgnoreCase("<nutrition>")) { //$NON-NLS-1$
               throw new IOException("<nutrition> expected"); //$NON-NLS-1$
            }
            readNutrition(subject, in);
            if (!readNextTag(in).equalsIgnoreCase("</nutrition>")) { //$NON-NLS-1$
               throw new IOException("</nutrition> expected"); //$NON-NLS-1$
            }
            if (!readNextTag(in).equalsIgnoreCase("<preferences>")) { //$NON-NLS-1$
               throw new IOException("<preferences> expected"); //$NON-NLS-1$
            }
            readPreferences(subject, in);
            if (!readNextTag(in).equalsIgnoreCase("<refusedCategories>")) { //$NON-NLS-1$
               throw new IOException("<refusedCategories> expected"); //$NON-NLS-1$
            }
            readRefusedCategories(subject, in);
            if (!readNextTag(in).equalsIgnoreCase("<refusedIngredients>")) { //$NON-NLS-1$
               throw new IOException("<refusedIngredients> expected"); //$NON-NLS-1$
            }
            readRefusedIngredients(subject, in);
            if (!readNextTag(in).equalsIgnoreCase("</subject>")) { //$NON-NLS-1$
               throw new IOException("</subject> expected"); //$NON-NLS-1$
            }
            subjectMap.put(subject.getName().toLowerCase(), subject);
         }
      } catch (IOException e) {
         e.printStackTrace();
      }
   }

   /**
    * Removes a subject from storage.
    * 
    * @param name
    *           Name of Subject.
    */
   public void remove(String name) {
      subjectMap.remove(name.toLowerCase());
      saveToFile();
   }

   private void saveCalcium(Subject s, PrintWriter out) {
      out.print("<minCalcium>"); //$NON-NLS-1$
      out.print(s.getMinCalcium());
      out.println("</minCalcium>"); //$NON-NLS-1$
      out.print("<maxCalcium>"); //$NON-NLS-1$
      out.print(s.getMaxCalcium());
      out.println("</maxCalcium>"); //$NON-NLS-1$
   }

   private void saveCarbohydrates(Subject s, PrintWriter out) {
      out.print("<minCarbohydrates>"); //$NON-NLS-1$
      out.print(s.getMinCarbohydrates());
      out.println("</minCarbohydrates>"); //$NON-NLS-1$
      out.print("<maxCarbohydrates>"); //$NON-NLS-1$
      out.print(s.getMaxCarbohydrates());
      out.println("</maxCarbohydrates>"); //$NON-NLS-1$
   }

   private void saveCholesterol(Subject s, PrintWriter out) {
      out.print("<minCholesterol>"); //$NON-NLS-1$
      out.print(s.getMinCholesterol());
      out.println("</minCholesterol>"); //$NON-NLS-1$
      out.print("<maxCholesterol>"); //$NON-NLS-1$
      out.print(s.getMaxCholesterol());
      out.println("</maxCholesterol>"); //$NON-NLS-1$
   }

   private void saveEnergyContent(Subject s, PrintWriter out) {
      out.print("<minEnergyContent>"); //$NON-NLS-1$
      out.print(s.getMinEnergyContent());
      out.println("</minEnergyContent>"); //$NON-NLS-1$
      out.print("<maxEnergyContent>"); //$NON-NLS-1$
      out.print(s.getMaxEnergyContent());
      out.println("</maxEnergyContent>"); //$NON-NLS-1$
   }

   private void saveFat(Subject s, PrintWriter out) {
      out.print("<minFat>"); //$NON-NLS-1$
      out.print(s.getMinFat());
      out.println("</minFat>"); //$NON-NLS-1$
      out.print("<maxFat>"); //$NON-NLS-1$
      out.print(s.getMaxFat());
      out.println("</maxFat>"); //$NON-NLS-1$
   }

   /**
    * Saves subjects to file in a XML-like format.
    * 
    * @param fileName
    *           The file name to save to, ending excluded.
    */
   public void saveGeneratedToFile(List<Subject> subjects, String fileName) {
      try {

         // System.out.println("Filename: " + fileName);
         PrintWriter out = new PrintWriter(new BufferedWriter(new FileWriter(
               "data/" + fileName + ".data"))); //$NON-NLS-1$ //$NON-NLS-2$
         out.println("<subjectList>"); //$NON-NLS-1$
         for (int index = 0; index < subjects.size(); index++) {
            saveSubject(subjects.get(index), out);
         }
         out.println("</subjectList>"); //$NON-NLS-1$
         out.close();
      } catch (IOException e) {
         e.printStackTrace();
      }
   }

   private void saveMealParameters(Subject s, PrintWriter out) {
      out.print("<mealTime>"); //$NON-NLS-1$
      out.print(s.getDesiredTime());
      out.println("</mealTime>"); //$NON-NLS-1$
      out.print("<mealCost>"); //$NON-NLS-1$
      out.print(s.getDesiredCost());
      out.println("</mealCost>"); //$NON-NLS-1$
      out.print("<mealDifficulty>"); //$NON-NLS-1$
      out.print(s.getDesiredDifficulty());
      out.println("</mealDifficulty>"); //$NON-NLS-1$

   }

   private void saveNatrium(Subject s, PrintWriter out) {
      out.print("<minNatrium>"); //$NON-NLS-1$
      out.print(s.getMinSodium());
      out.println("</minNatrium>"); //$NON-NLS-1$
      out.print("<maxNatrium>"); //$NON-NLS-1$
      out.print(s.getMaxSodium());
      out.println("</maxNatrium>"); //$NON-NLS-1$
   }

   private void saveNutrition(Subject s, PrintWriter out) {
      saveCarbohydrates(s, out);
      saveEnergyContent(s, out);
      saveFat(s, out);
      saveProtein(s, out);
      saveCholesterol(s, out);
      saveNatrium(s, out);
      saveCalcium(s, out);
   }

   /**
    * Saves a subject's preferences to file in a XML-like format
    * 
    * @param s
    *           The Subject whos Preferences will be saved
    * @param out
    *           PrintWrite connected to the output file
    */
   private void savePreferences(Subject s, PrintWriter out) {
      out.println("<preferences>"); //$NON-NLS-1$
      Map<String, Preference> subjectPrefs = s.getPreferences();
      List<String> recipeNames = new ArrayList<String>(subjectPrefs.keySet());
      String currentRecipe;
      for (int index = 0; index < subjectPrefs.size(); index++) {
         currentRecipe = recipeNames.get(index);
         out.print("<preference>"); //$NON-NLS-1$
         out.print("<recipe>"); //$NON-NLS-1$
         out.print(currentRecipe);
         out.print("</recipe>"); //$NON-NLS-1$
         out.print("<rating>"); //$NON-NLS-1$
         out.print(subjectPrefs.get(currentRecipe).getRating());
         out.print("</rating>"); //$NON-NLS-1$
         out.println("</preference>"); //$NON-NLS-1$
      }
      out.println("</preferences>"); //$NON-NLS-1$
   }

   private void saveProtein(Subject s, PrintWriter out) {
      out.print("<minProtein>"); //$NON-NLS-1$
      out.print(s.getMinProtein());
      out.println("</minProtein>"); //$NON-NLS-1$
      out.print("<maxProtein>"); //$NON-NLS-1$
      out.print(s.getMaxProtein());
      out.println("</maxProtein>"); //$NON-NLS-1$
   }

   private void saveRefusedCategories(Subject s, PrintWriter out) {
      out.println("<refusedCategories>"); //$NON-NLS-1$
      ArrayList<String> refusedCats = s.getRefusedCategoriesList();
      for (int index = 0; index < refusedCats.size(); index++) {
         out.print("<refusedCategory>"); //$NON-NLS-1$
         out.print(refusedCats.get(index));
         out.println("</refusedCategory>"); //$NON-NLS-1$
      }
      out.println("</refusedCategories>"); //$NON-NLS-1$
   }

   private void saveRefusedIngredients(Subject s, PrintWriter out) {
      out.println("<refusedIngredients>"); //$NON-NLS-1$
      ArrayList<String> refusedIngs = s.getRefusedIngredientsList();
      for (int index = 0; index < refusedIngs.size(); index++) {
         out.print("<refusedIngredient>"); //$NON-NLS-1$
         out.print(refusedIngs.get(index));
         out.println("</refusedIngredient>"); //$NON-NLS-1$
      }
      out.println("</refusedIngredients>"); //$NON-NLS-1$
   }

   /**
    * Saves a subject to file in a XML-like format
    * 
    * @param s
    *           The Subject to be saved
    * @param out
    *           PrintWriter connected to output file
    */
   private void saveSubject(Subject s, PrintWriter out) {
      out.println("<subject>"); //$NON-NLS-1$
      out.print("<name>"); //$NON-NLS-1$
      out.print(s.getName());
      out.println("</name>"); //$NON-NLS-1$
      out.print("<importance>"); //$NON-NLS-1$
      out.print(s.getImportance());
      out.println("</importance>"); //$NON-NLS-1$
      out.print("<desiredAvailability>"); //$NON-NLS-1$
      out.print(s.getDesiredAvailability());
      out.println("</desiredAvailability>"); //$NON-NLS-1$
      out.print("<desiredVariation>"); //$NON-NLS-1$
      out.print(s.getDesiredVariation());
      out.println("</desiredVariation>"); //$NON-NLS-1$
      out.println("<mealParameters>"); //$NON-NLS-1$
      saveMealParameters(s, out);
      out.println("</mealParameters>"); //$NON-NLS-1$
      out.println("<nutrition>"); //$NON-NLS-1$
      saveNutrition(s, out);
      out.println("</nutrition>"); //$NON-NLS-1$
      savePreferences(s, out);
      saveRefusedCategories(s, out);
      saveRefusedIngredients(s, out);
      out.println("</subject>"); //$NON-NLS-1$
   }

   /**
    * Saves subjects to file in a XML-like format.
    */
   public void saveToFile() {
      try {
         URL url = IngredientCategory.class.getClassLoader().getResource(
               Settings.getFilePath("SubjectDS.SubjectsDataFile"));
         PrintWriter out = new PrintWriter(new BufferedWriter(new FileWriter(
               url.toExternalForm())));
         out.println("<subjectList>"); //$NON-NLS-1$
         for (int index = 0; index < subjectMap.size(); index++) {
            saveSubject((Subject) subjectMap.values().toArray()[index], out);
         }
         out.println("</subjectList>"); //$NON-NLS-1$
         out.close();
      } catch (IOException e) {
         e.printStackTrace();
      }
   }

   /**
    * Sets a subject in storage.
    * 
    * @param s
    *           Subject to set.
    */
   public void set(Subject s) {
      subjectMap.put(s.getName().toLowerCase(), s);
      saveToFile();
   }
}
