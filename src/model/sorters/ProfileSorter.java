package model.sorters;

import constraints.SingleRecipeConstraint;
import java.util.Comparator;

import dataObjects.Recipe;
import dataObjects.Session;
import dataObjects.Subject;
import java.util.List;

/**
 * Does no sorting at all.
 * @author jernlas
 *
 */
public class ProfileSorter implements RecipeSorter {
//vars

    private ValueComparator comarator = new ValueComparator();
    private Subject user = Session.getInstance().getUser();

    /**
     * Gets the comparator
     * @return  Comparator<Recipie>
     */
    public Comparator<Recipe> getComparator() {
        return comarator;
    }

    /**
     * Comparator calculates which recipe matches the user best
     */
    private class ValueComparator implements Comparator<Recipe> {

        /**
         * Compares two recepies
         * @param r1    Recipie first recipie
         * @param r2    Recipie second recipie
         * @return      int result of comparation >0 if r1 is greatest
         *                  0 if equal and 0> if r2 greatest
         */
        public int compare(Recipe r1, Recipe r2) {
            float val1 = 0;
            float val2 = 0;

            for (SingleRecipeConstraint c : user.getAllConstraints()) {
                if (c != null) {
                    if (c.toString().contains("Ingredient")) {
                        boolean b = false;
                        if (c.valuation(r1) != 0) {
                            val1 = 1000;
                            b = true;
                        }
                        if (c.valuation(r2) != 0) {
                            val2 = 1000;
                            b= true;
                        }
                        //Should compare break?
                        if (b) {
                            break;
                        }
                    } else {
                        val1 += c.valuation(r1);
                        val2 += c.valuation(r2);
                    }
                }
            }
            return Math.round((val2 - val1) * -10);
        }
    }

    public ProfileSorter() {
    }

    public String toString() {
        return "Sortera efter min profil";


    }
}
