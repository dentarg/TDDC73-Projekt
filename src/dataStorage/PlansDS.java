package dataStorage;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.URL;
import java.util.HashMap;
import java.util.List;

import data.Settings;
import dataObjects.IngredientCategory;
import dataObjects.MealDate;
import dataObjects.MealPlan;
import dataObjects.MealSuggestion;
import exceptions.PlanNotFoundException;

/**
 * Data storage class for plans.
 */
public class PlansDS {
   HashMap<String, MealPlan> planMap;

   /**
    * Create a new plan data storage object.
    */
   public PlansDS() {
      planMap = new HashMap<String, MealPlan>();
      readFromFile();
   }

   /**
    * Add a plan to storage.
    * 
    * @param p
    *           MealPlan to add
    */
   public void add(MealPlan p) {
      planMap.put(p.getName(), p);
      saveToFile();
   }

   /**
    * Get a plan from storage.
    * 
    * @param name
    *           Name of plan to get.
    * @return MealPlan or null.
    * @throws PlanNotFoundException
    */
   public MealPlan get(String name) throws PlanNotFoundException {
      MealPlan tempPlan = planMap.get(name);
      if (tempPlan == null) {
         throw new PlanNotFoundException();
      }
      return tempPlan;
   }

   /**
    * Reads meal plans from file in a XML-like format.
    */
   public void readFromFile() {
      String s;
      planMap.clear();
      try {
         InputStreamReader ir = new InputStreamReader(ClassLoader
               .getSystemResourceAsStream(Settings
                     .getFilePath("PlansDS.PlanDataFile")));
         BufferedReader in = new BufferedReader(ir);
         if ((s = in.readLine()) == null) {
            return;
         } else if (!s.equalsIgnoreCase("<planList>")) { 
            throw new IOException("<planList> expected"); 
         }
         readPlans(in);
         in.close();
      } catch (FileNotFoundException e) {
         e.printStackTrace();
      } catch (IOException e) {
         e.printStackTrace();
      }
   }

   private void readMeals(MealPlan plan, BufferedReader in) {
      String s = null;
      MealDate d = null;
      MealSuggestion mealSuggestion = null;
      try {
         while (!(s = in.readLine()).equalsIgnoreCase("</meals>")) { 
            if (!s.equalsIgnoreCase("<mealSuggestion>")) { 
               throw new IOException("<mealSuggestion> expected"); 
            }
            if (!in.readLine().equalsIgnoreCase("<date>")) { 
               throw new IOException("<date> expected"); 
            }
            d = new MealDate(in.readLine());
            if (!in.readLine().equalsIgnoreCase("</date>")) { 
               throw new IOException("</date> expected"); 
            }
            if (!in.readLine().equalsIgnoreCase("<recipe>")) { 
               throw new IOException("<recipe> expected"); 
            }
            mealSuggestion = new MealSuggestion(d, in.readLine());
            if (!in.readLine().equalsIgnoreCase("</recipe>")) { 
               throw new IOException("</recipe> expected"); 
            }
            if (!in.readLine().equalsIgnoreCase("</mealSuggestion>")) { 
               throw new IOException("</mealSuggestion> expected"); 
            }
            plan.addMeal(mealSuggestion);
         }
      } catch (IOException e) {
         e.printStackTrace();
      }
   }

   private void readPlans(BufferedReader in) {
      String s = null;
      MealPlan mealPlan = null;
      try {
         while (!(s = in.readLine()).equalsIgnoreCase("</planList>")) { 
            if (!s.equalsIgnoreCase("<mealPlan>")) { 
               throw new IOException("<mealPlan> expected, found " + s); 
            }
            if (!in.readLine().equalsIgnoreCase("<name>")) { 
               throw new IOException("<name> expected"); 
            }
            mealPlan = new MealPlan(in.readLine());
            if (!in.readLine().equalsIgnoreCase("</name>")) { 
               throw new IOException("</name> expected"); 
            }
            if (!in.readLine().equalsIgnoreCase("<meals>")) { 
               throw new IOException("<meals> expected"); 
            }
            readMeals(mealPlan, in);
            if (!in.readLine().equalsIgnoreCase("</meals>")) { 
               throw new IOException("</meals> expected"); 
            }
            planMap.put(mealPlan.getName(), mealPlan);
         }
      } catch (IOException e) {
         e.printStackTrace();
      }

   }

   /**
    * Remove a plan from storage.
    * 
    * @param name
    *           Name of plan.
    */
   public void remove(String name) {
      planMap.remove(name);
      saveToFile();
   }

   private void saveMeals(MealPlan plan, PrintWriter out) {
      out.println("<meals>"); 
      List<MealSuggestion> mealList = plan.getMealList();
      for (int index = 0; index < mealList.size(); index++) {
         out.println("<meal>"); 
         out.println("<date>"); 
         out.println(mealList.get(index).getDate());
         out.println("</date>"); 
         out.println("<recipe>"); 
         saveRecipes(plan.getMealList().get(index).getRecipes(), out);
         out.println("</recipe>"); 
         out.println("</meal>"); 
      }
      out.println("</meals>"); 

   }

   private void savePlan(MealPlan plan, PrintWriter out) {
      out.println("<mealPlan>"); 
      out.println("<name>"); 
      out.println(plan.getName());
      out.println("</name>"); 
      saveMeals(plan, out);
      out.println("</mealPlan>"); 
   }

   private void saveRecipes(List<String> recipeList, PrintWriter out) {
      for (int index = 0; index < recipeList.size(); index++) {
         out.println(recipeList.get(index));
      }
   }

   /**
    * Saves meal plans to file in a XML-like format
    */
   public void saveToFile() {
      try {
         URL url = IngredientCategory.class.getClassLoader().getResource(
               Settings.getFilePath("PlansDS.PlanDataFile"));
         PrintWriter out = new PrintWriter(new BufferedWriter(new FileWriter(
               url.toExternalForm())));
         out.println("<planList>"); 
         for (int index = 0; index < planMap.size(); index++) {
            savePlan((MealPlan) planMap.values().toArray()[index], out);
         }
         out.println("</planList>"); 
         out.close();
      } catch (IOException e) {
         e.printStackTrace();
      }
   }

   /**
    * Set a Plan in storage.
    * 
    * @param p
    */
   public void set(MealPlan p) {
      planMap.put(p.getName(), p);
      saveToFile();
   }
}
