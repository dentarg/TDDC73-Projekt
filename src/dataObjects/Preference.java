/*
 * Created using IntelliJ IDEA. User: x04nicsu Date: 2005-jan-17 Time: 11:41:53
 */
package dataObjects;

/**
 * Class representing a recipe preference.
 */
public class Preference {
   private int    rating;
   private String recipeName;

   /**
    * Create a new preference.
    * 
    * @param recipe
    *           Name of recipe.
    * @param rating
    *           Recipe rating, int 0-5
    */
   public Preference(String recipe, int rating) {
      recipeName = recipe;
      this.rating = rating;
   }

   /**
    * Rating.
    * 
    * @return int, 0-5
    */
   public int getRating() {
      return rating;
   }

   /**
    * Recipe name.
    * 
    * @return Name of the recipe.
    */
   public String getRecipeName() {
      return recipeName;
   }
}
