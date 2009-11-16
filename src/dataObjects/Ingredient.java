package dataObjects;

/**
 * Class representing ingredients with costs.
 */
public class Ingredient {
   private float  cost;
   private String name;

   /**
    * Create a new ingredient with cost.
    * 
    * @param name
    *           Name of ingredient.
    * @param cost
    *           Cost of ingredient.
    */
   public Ingredient(String name, float cost) {
      this.name = name;
      this.cost = cost;
   }

   /**
    * Cost of ingredient.
    * 
    * @return Cost of ingredient, float.
    */
   public float getCost() {
      return cost;
   }

   /**
    * Name of ingredient.
    * 
    * @return Name of ingredient, String.
    */
   public String getName() {
      return name;
   }
}
