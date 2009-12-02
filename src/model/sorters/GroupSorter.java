package model.sorters;

import java.util.Comparator;

import dataObjects.Group;
import dataObjects.Recipe;

/**
 * Sort by selected group
 * @author Mange
 *
 */
public class GroupSorter implements RecipeSorter {

	private Group group;

	public Comparator<Recipe> getComparator() {
		System.out.println(group.getName());
		return null;
	}

	public void setGroup(Group group) {
		this.group = group;
	}

	public Group getGroup() {
		return group;
	}

	@Override
	public String toString() {
		return "Sortera efter grupp";
	}

}
