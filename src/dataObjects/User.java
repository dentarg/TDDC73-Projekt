package dataObjects;

import java.awt.Image;
import java.util.ArrayList;

/**
 * The User class is used to create a new user. It contains
 * the user's name and information about settings the user 
 * has done, which is stored in a UserData object.
 * 
 * @author Fredrik Ericsson
 *
 */

public class User
{
	private UserData data;
	private String name;
	private Image picture;
	private ArrayList<Group> groups;
	
	public User(String name)
	{
		this.name = name;
		this.data = new UserData();
		this.groups = new ArrayList<Group>();
	}
	
	public void createGroup(String name)
	{
		Group g = new Group(name);
		g.addUser(this);
		this.groups.add(g);		
	}
	
	public void removeGroup(String name)
	{
		int length = this.groups.size();
		for(int i = 0; i<length; i++)
		{
			if(name == this.groups.get(i).getName())
			{
				this.groups.remove(i);
			}
		}
	}
	
	public Boolean isMemberOf(Group g)
	{
		int numberOfGroups = this.groups.size();
			
		for(int i = 0; i<numberOfGroups; i++)
		{
			Group group = this.groups.get(i);
			ArrayList<User> userList = group.getMembers();
			int numberOfMembers = group.getNumberOfMembers();
			
			for(int j = 0; i<numberOfMembers; j++)
			{
				if(this == userList.get(j))
				{
					return true;
				}
			}
		}
		
		return false;
	}
	
	public boolean isAllergicTo(Ingredient ingredient)
	{
		ArrayList<Ingredient> allergies = this.data.getAllergies(); 
		int length = allergies.size();
				
		for(int i = 0; i<length; i++)
		{
			if(ingredient == allergies.get(i))
			{
				return true;
			}
		}
		
		return false;
	}
	
	/*
	 * Some set and get methods for the User.
	 */
	public String getName()
	{
		return this.name;
	}
	
	public void setName(String name)
	{
		this.name = name;
	}
	
	public UserData getUserData()
	{
		return this.data;
	}
	
	public void setUserData(UserData data)
	{
		this.data = data;
	}
	
	public void setPicture(Image i)
	{
		this.picture = i;
	}
	
	public Image getPicture()
	{
		return this.picture;
	}
	
	public ArrayList<Group> getGroups()
	{
		return this.groups;
	}
	
}
