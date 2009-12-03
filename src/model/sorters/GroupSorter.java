package model.sorters;

import constraints.SingleRecipeConstraint;
import java.util.Comparator;

import dataObjects.Group;
import dataObjects.Recipe;
import dataObjects.Session;
import dataObjects.Subject;

/**
 * Sort by selected group
 * @author Mange
 *
 */
public class GroupSorter implements RecipeSorter {
//vars
    private Group group;
    private Comparator<Recipe> comparaor = new ValueComparator();
    private Subject user = Session.getInstance().getUser();
/**
 * Gets the comparator
 * @return  Comparator<Recipie> 
 */
    public Comparator<Recipe> getComparator() {
        return comparaor;
    }
/**
 * Comparator calculates the average in the group
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
            for (Subject subject: group.getMembers())  {
                for (SingleRecipeConstraint c : subject.getAllConstraints()) {
                    if (c != null) {
                        val1 += c.valuation(r1);
                        val2 += c.valuation(r2);
                    }
                }
            }
            val1 = val1/group.getNumberOfMembers();
            val2 = val2/group.getNumberOfMembers();

            return Math.round((val2 - val1) * -10);
        }
    }
/**
 * Sets the group of subject to be searched,
 * and adds the logged on user
 * @param group     Group the group to be calculated from
 */
    public void setGroup(Group group) {
        this.group = group;
        this.group.addUser(user);
    }

    /**
     * Returns the group
     * @return
     */
    public Group getGroup() {
        return group;
    }

    public GroupSorter() {
    }
    

    @Override
    public String toString() {
        return "Sortera efter grupp";
    }
}
