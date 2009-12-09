/*
 * Created using IntelliJ IDEA. User: x04nicsu Date: 2005-jan-13 Time: 15:45:24
 */
package dataObjects;

import constraints.CalciumConstraint;
import constraints.CarbonHydratesConstraint;
import constraints.CholestrolConstraint;
import constraints.EnergyConstraint;
import constraints.FatConstraint;
import constraints.IngredientConstraint;
import constraints.ProteinConstraint;
import constraints.SingleRecipeConstraint;
import constraints.SodiumConstraint;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Observable;
import java.util.Observer;

import dataObjects.units.GramMeasurement;
import dataObjects.units.Measurement;
import dataObjects.units.MilliGramMeasurement;
import java.util.List;
import ui.panels.profile.OverviewPanel;

/**
 * Class representing a subject.
 */
public class Subject extends Observable {

    private int desiredAvailability;
    private int desiredDifficulty;
    private int desiredCost;
    private int desiredTime;
    private int desiredVariation;
    private Map<String, Preference> generatedPreferenceMap;
    private int importance;
    private Measurement maxCalcium;
    private Measurement maxCarbohydrates;
    private Measurement maxCholesterol;
    private Measurement maxEnergyContent;
    private Measurement maxFat;
    private Measurement maxProtein;
    private Measurement maxSodium;
    private Measurement minCalcium;
    private Measurement minCarbohydrates;
    private Measurement minCholesterol;
    private Measurement minEnergyContent;
    private Measurement minFat;
    private Measurement minProtein;
    private Measurement minSodium;
    private String name;
    private Map<String, Preference> preferenceMap;
    private ArrayList<String> refusedCategoriesList;
    private ArrayList<String> allergyIngredientList;
    private ArrayList<String> refusedIngredientsList;
    private ArrayList<String> favoriteRecipeList;
    private ArrayList<Group> groups;

    /**
     * Create a new subject.
     *
     * @param name
     *           Name of the subject.
     */
    public Subject(String name) {
        this.name = name;
        desiredDifficulty = -1;
        desiredAvailability = 2;
        desiredVariation = 2;
        minCarbohydrates = new GramMeasurement(0f);
        maxCarbohydrates = new GramMeasurement(130f);
        minEnergyContent = Measurement.createMeasurement("kcal", 1800f);
        maxEnergyContent = Measurement.createMeasurement("kcal", 2400f);
        minFat = new GramMeasurement(20f);
        maxFat = new GramMeasurement(35f);
        minProtein = new GramMeasurement(45f);
        maxProtein = new GramMeasurement(63f);
        minCholesterol = new MilliGramMeasurement(0);
        maxCholesterol = new MilliGramMeasurement(200);
        minSodium = new MilliGramMeasurement(0);
        maxSodium = new MilliGramMeasurement(120);
        minCalcium = new MilliGramMeasurement(800);
        maxCalcium = new MilliGramMeasurement(1200);
        importance = 1;
        preferenceMap = new HashMap<String, Preference>();
        generatedPreferenceMap = new HashMap<String, Preference>();
        refusedIngredientsList = new ArrayList<String>();
        refusedCategoriesList = new ArrayList<String>();
        favoriteRecipeList = new ArrayList<String>();
        allergyIngredientList = new ArrayList<String>();
        groups = new ArrayList<Group>();
    }

    /**
     * Adds an allergy ingredient
     * @param ingredientName  Name of the ingredient
     */
    public void addAllergyIngredient(String ingredientName) {
        if (allergyIngredientList.contains(ingredientName)) {
            return;
        }
        allergyIngredientList.add(ingredientName);
        changed();
    }

    /**
     * Adds a generated preference to the subject.
     *
     * @param preference
     *           Preference to add.
     */
    public void addGeneratedPreference(Preference preference) {
        generatedPreferenceMap.put(preference.getRecipeName(), preference);
    }

    /**
     * Adds a preference to the subject.
     *
     * @param preference
     *           Preference to add.
     */
    public void addPreference(Preference preference) {
        preferenceMap.put(preference.getRecipeName(), preference);
        changed();
    }

    /**
     * Adds a refused category to the subject.
     *
     * @param category
     *           Name of the category to add.
     */
    public void addRefusedCategory(String category) {
        if (refusedCategoriesList.contains(category)) {
            return;
        }
        refusedCategoriesList.add(category);
        changed();
    }

    /**
     * Adds a refused ingredient to the subject.
     *
     * @param ingredientName
     *           Name of ingredient to add.
     */
    public void addRefusedIngredient(String ingredientName) {
        if (refusedIngredientsList.contains(ingredientName)) {
            return;
        }
        refusedIngredientsList.add(ingredientName);
        changed();
    }

    /**
     * Adds a favorite recipe to the subject.
     *
     * @param recipeName
     *           Name of recipe to add.
     */
    public void addFavoriteRecipe(String recipeName) {
        favoriteRecipeList.add(recipeName);
    }

    /**
     * Creates a new group and adds it to the list of groups.
     *
     *  @param name
     *  			Name of the group.
     */
    public Group createGroup(String name) {
        Group g = new Group(name);
        this.groups.add(g);
        changed();
        return g;
    }

    /**
     * Removes a group from the user's list of groups.
     *
     * @param name
     * 			Name of the group.
     */
    public void removeGroup(String name) {
        for (int i = 0; i < groups.size(); i++) {
			if (name.equals(groups.get(i).getName())) {
				groups.remove(i);
			}
		}
        changed();
    }

    public Group getGroup(String name) {
        int length = this.groups.size();
        for (int i = 0; i < length; i++) {
            if (name == this.groups.get(i).getName()) {
                return this.groups.get(i);
            }
        }
        return null;
    }

    public ArrayList<Group> getGroups() {
        return groups;
    }

    public ArrayList<String> getGroupNames() {
        int length = this.groups.size();
        ArrayList<String> names = new ArrayList<String>();

        for (int i = 0; i < length; i++) {
            names.add(this.groups.get(i).getName());
        }

        return names;
    }

    /**
     * Checks if a user is a member of the specified group.
     *
     * @param g
     * 			The group to search in.
     */
    public Boolean isMemberOf(Group g) {
        int numberOfGroups = this.groups.size();

        for (int i = 0; i < numberOfGroups; i++) {
            Group group = this.groups.get(i);
            ArrayList<Subject> userList = group.getMembers();
            int numberOfMembers = group.getNumberOfMembers();

            for (int j = 0; i < numberOfMembers; j++) {
                if (this == userList.get(j)) {
                    return true;
                }
            }
        }

        return false;
    }

    public List<SingleRecipeConstraint> getAllConstraints() {

        ArrayList<SingleRecipeConstraint> c = new ArrayList<SingleRecipeConstraint>();

        ArrayList<String> tempList = refusedCategoriesList;

        for (String s : allergyIngredientList) {
            tempList.add(s);
        }


        c.add(new IngredientConstraint(tempList, false));

        c.add(new CalciumConstraint(minCalcium, maxCalcium));
        c.add(new SodiumConstraint(minSodium, maxSodium));
        c.add(new EnergyConstraint(minEnergyContent, maxEnergyContent));
        c.add(new CarbonHydratesConstraint(maxCarbohydrates, maxCarbohydrates));
        c.add(new FatConstraint(minFat, maxFat));
        c.add(new CholestrolConstraint(minCholesterol, maxCholesterol));
        c.add(new ProteinConstraint(minProtein, maxProtein));

        return c;
    }

    public int getDesiredAvailability() {
        return desiredAvailability;
    }

    public int getDesiredDifficulty() {
        return desiredDifficulty;
    }

    public int getDesiredVariation() {
        return desiredVariation;
    }

    /**
     * Gets the generated preferences of a subject.
     *
     * @return Map of <String, Integer>.
     */
    public Map<String, Preference> getGeneratedPreferences() {
        return generatedPreferenceMap;
    }

    public int getImportance() {
        return importance;
    }

    public Measurement getMaxCalcium() {
        return maxCalcium;
    }

    public Measurement getMaxCarbohydrates() {
        return maxCarbohydrates;
    }

    public Measurement getMaxCholesterol() {
        return maxCholesterol;
    }

    public Measurement getMaxEnergyContent() {
        return maxEnergyContent;
    }

    public Measurement getMaxFat() {
        return maxFat;
    }

    public Measurement getMaxProtein() {
        return maxProtein;
    }

    public Measurement getMaxSodium() {
        return maxSodium;
    }

    public Measurement getMinCalcium() {
        return minCalcium;
    }

    public Measurement getMinCarbohydrates() {
        return minCarbohydrates;
    }

    public Measurement getMinCholesterol() {
        return minCholesterol;
    }

    public Measurement getMinEnergyContent() {
        return minEnergyContent;
    }

    public Measurement getMinFat() {
        return minFat;
    }

    public Measurement getMinProtein() {
        return minProtein;
    }

    public Measurement getMinSodium() {
        return minSodium;
    }

    public int getDesiredTime() {
        return this.desiredTime;
    }

    public int getDesiredCost() {
        return this.desiredCost;
    }

    /**
     * Name of the subject.
     *
     * @return Name, String.
     */
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
        changed();
    }

    /**
     * Gets the preferences of a subject.
     *
     * @return Map of <String, Integer>.
     */
    public Map<String, Preference> getPreferences() {
        return preferenceMap;
    }

    /**
     * Gets the refused categories of a subjects.
     *
     * @return ArrayList of String.
     */
    public ArrayList<String> getRefusedCategoriesList() {
        return refusedCategoriesList;
    }

    /**
     * Gets the refused ingredients of a subject.
     *
     * @return ArrayList of String.
     */
    public ArrayList<String> getRefusedIngredientsList() {
        return refusedIngredientsList;
    }

    /**
     * Gets the favorite recipies ingredients of a subject.
     *
     * @return ArrayList of String.
     */
    public ArrayList<String> getFavoriteRecipeList() {
        return favoriteRecipeList;
    }

    public void removeRefusedCategory(String category) {
        refusedCategoriesList.remove(category);
        changed();
    }

    public void removeRefusedIngredient(String ingredient) {
        refusedIngredientsList.remove(ingredient);
        changed();
    }

    public void removeAllergyIngredient(String ingredient) {
        allergyIngredientList.remove(ingredient);
        changed();
    }

    public void removeFavoriteRecipe(String recipe) {
        favoriteRecipeList.remove(recipe);
    }

    public void setDesiredAvailability(int desiredAvailability) {
        this.desiredAvailability = desiredAvailability;
    }

    public void setDesiredAvailability(String desiredAvailability) {
        if (desiredAvailability.equalsIgnoreCase("low")) {
            this.desiredAvailability = 1;
        } else if (desiredAvailability.equalsIgnoreCase("medium")) {
            this.desiredAvailability = 2;
        } else {
            this.desiredAvailability = 3;
        }
    }

    public void setDesiredCost(int desiredCost) {
        this.desiredCost = desiredCost;
        changed();
    }

    public void setDesiredTime(int desiredTime) {
        this.desiredTime = desiredTime;
        changed();
    }

    public void setDesiredDifficulty(int desiredDifficulty) {
        this.desiredDifficulty = desiredDifficulty;
        changed();
    }

    public void setDesiredDifficulty(String difficulty) {
        if (difficulty.equalsIgnoreCase("easy")) {
            this.desiredDifficulty = 1;
        } else if (difficulty.equalsIgnoreCase("medium")) {
            this.desiredDifficulty = 2;
        } else if (difficulty.equalsIgnoreCase("hard")) {
            this.desiredDifficulty = 3;
        } else {
            this.desiredDifficulty = -1;
        }
        changed();
    }

    public void setDesiredVariation(int desiredVariation) {
        this.desiredVariation = desiredVariation;
    }

    public void setDesiredVariation(String desiredVariation) {
        if (desiredVariation.equalsIgnoreCase("low")) {
            this.desiredVariation = 1;
        } else if (desiredVariation.equalsIgnoreCase("medium")) {
            this.desiredVariation = 2;
        } else {
            this.desiredVariation = 3;
        }
    }

    public void setImportance(int importance) {
        this.importance = importance;
    }

    public void setMaxCalcium(Measurement maxCalcium) {
        this.maxCalcium = maxCalcium;
        changed();
    }

    public void setMaxCarbohydrates(Measurement maxCarbohydrates) {
        this.maxCarbohydrates = maxCarbohydrates;
        changed();
    }

    public void setMaxCholesterol(Measurement maxCholesterol) {
        this.maxCholesterol = maxCholesterol;
        changed();
    }

    public void setMaxEnergyContent(Measurement maxEnergyContent) {
        this.maxEnergyContent = maxEnergyContent;
        changed();
    }

    public void setMaxFat(Measurement maxFat) {
        this.maxFat = maxFat;
        changed();
    }

    public void setMaxProtein(Measurement maxProtein) {
        this.maxProtein = maxProtein;
        changed();
    }

    public void setMaxSodium(Measurement maxSodium) {
        this.maxSodium = maxSodium;
        changed();
    }

    public void setMinCalcium(Measurement minCalcium) {
        this.minCalcium = minCalcium;
        changed();
    }

    public void setMinCarbohydrates(Measurement minCarbohydrates) {
        this.minCarbohydrates = minCarbohydrates;
        changed();
    }

    public void setMinCholesterol(Measurement minCholesterol) {
        this.minCholesterol = minCholesterol;
        changed();
    }

    public void setMinEnergyContent(Measurement minEnergyContent) {
        this.minEnergyContent = minEnergyContent;
        changed();
    }

    public void setMinFat(Measurement minFat) {
        this.minFat = minFat;
        changed();
    }

    public void setMinProtein(Measurement minProtein) {
        this.minProtein = minProtein;
        changed();
    }

    public void setMinSodium(Measurement minSodium) {
        this.minSodium = minSodium;
        changed();
    }

    @Override
    public String toString() {
        return getName();
    }

    public void changed() {
        this.setChanged();
        this.notifyObservers();
    }
}
