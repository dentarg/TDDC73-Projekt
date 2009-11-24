package dataObjects;

import java.util.ArrayList;

import dataObjects.units.Measurement;

/**
 * The UserData class stores user settings that 
 * is used when searching for appropriate food.
 * 
 * @author Fredrik Ericsson
 *
 */

public class UserData
{
	private int priceMin, priceMax;
	private int levelMin, levelMax;
	private int timeMin, timeMax;
	
	private ArrayList<Nutrition> nutritions;
	private ArrayList<Ingredient> allergies;
	
	/*
	 * The constructor for UserData. It initializes
	 * the variables to some random values.
	 */
	public UserData()
	{
		this.priceMin = 10;
		this.priceMax = 100;
		this.levelMin = 0;
		this.levelMax = 5;
		this.timeMin = 10;
		this.timeMax = 60;
		
		/*
		 * 0: cholesterol	3: natrium		6: fat
		 * 1: energy		4: calcium
		 * 2: protein		5: carbohydrate
		 * 
		 */
		this.nutritions = new ArrayList<Nutrition>();
		this.allergies = new ArrayList<Ingredient>();
		init();
	}
	
	public void init()
	{
		//Measurement fatMax = new Measurement("75 g");
		
		Nutrition fat = new Nutrition();
		Nutrition carbohydrate = new Nutrition();
		Nutrition calcium = new Nutrition();
		Nutrition natrium = new Nutrition();
		Nutrition protein = new Nutrition();
		Nutrition energy = new Nutrition();
		Nutrition cholesterol = new Nutrition();
		
		this.nutritions.add(fat);
		this.nutritions.add(carbohydrate);
		this.nutritions.add(calcium);
		this.nutritions.add(natrium);
		this.nutritions.add(protein);
		this.nutritions.add(energy);
		this.nutritions.add(cholesterol);
	}
	
	public void addAllergie(Ingredient ingredient)
	{
		this.allergies.add(ingredient);
	}
	
	public void removeAllergie(Ingredient ingredient)
	{
		int length = this.allergies.size();
		for(int i = 0; i<length; i++)
		{
			if(ingredient == this.allergies.get(i))
			{
				this.allergies.remove(i);
			}
		}
	}
	
	public ArrayList<Ingredient> getAllergies()
	{
		return this.allergies;
	}
	
	/*
	 * Here are the get methods for the UserData.
	 */
	
	public Nutrition getFat()
	{
		return this.nutritions.get(6);
	}
	
	public Nutrition getCarb()
	{
		return this.nutritions.get(5);
	}
	
	public Nutrition getCalcium()
	{
		return this.nutritions.get(4);
	}
	
	public Nutrition getNatrium()
	{
		return this.nutritions.get(3);
	}
	
	public Nutrition getProtein()
	{
		return this.nutritions.get(2);
	}
	
	public Nutrition getEnergy()
	{
		return this.nutritions.get(1);
	}
	
	public Nutrition getCholesterol()
	{
		return this.nutritions.get(0);
	}
	
	public int getPriceMin()
	{
		return this.priceMin;
	}
	
	public int getPriceMax()
	{
		return this.priceMax;
	}
	
	public int getLevelMin()
	{
		return this.levelMin;
	}
	
	public int getLevelMax()
	{
		return this.levelMax;
	}
	
	public int getTimeMin()
	{
		return this.timeMin;
	}
	
	public int getTimeMax()
	{
		return this.timeMax;
	}
	
	/*
	 * Here are the set methods for the UserData.
	 */
	public void setPriceMin(int value)
	{
		this.priceMin = value;
	}
	
	public void setPriceMax(int value)
	{
		this.priceMax = value;
	}
	
	public void setLevelMin(int value)
	{
		this.levelMin = value;
	}
	
	public void setLevelMax(int value)
	{
		this.levelMax = value;
	}
	
	public void setTimeMin(int value)
	{
		this.timeMin = value;
	}
	
	public void setTimeMax(int value)
	{
		this.timeMax = value;
	}
	
	
	
}
