package dataAccess;

import dataObjects.MealPlan;
import dataStorage.PlansDS;
import exceptions.PlanNotFoundException;

/**
 * Data access class for plans.
 */
public class PlansDA {
   private PlansDS plansDS;

   /**
    * Create a new plan data access object.
    */
   public PlansDA() {
      plansDS = new PlansDS();
   }

   /**
    * Add a plan to underlying storage.
    * 
    * @param p
    *           MealPlan to add.
    */
   public void addPlan(MealPlan p) {
      plansDS.add(p);
   }

   /**
    * Get a plan from underlying storage.
    * 
    * @param name
    *           Name of plan to get.
    * @return MealPlan or null
    * @throws PlanNotFoundException
    */
   public MealPlan getPlan(String name) throws PlanNotFoundException {
      return plansDS.get(name);
   }

   /**
    * Remove a plan from underlying storage.
    * 
    * @param name
    *           Name of MealPlan to remove.
    */
   public void removePlan(String name) {
      plansDS.remove(name);
   }

}
