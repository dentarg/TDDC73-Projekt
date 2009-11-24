package dataObjects;

import java.util.ArrayList;

/**
 * The group class contains its name and a list
 * of all the members of this group. Groups are
 * created and managed by users.
 * 
 * @author Fredrik Ericsson
 *
 */

public class Group 
{
	private String name;
	private ArrayList<User> members;
	
	public Group(String name)
	{
		this.name = name;
		this.members = new ArrayList<User>();		
	}

	public void removeUser(User user)
	{
		int length = this.members.size();
		for(int i = 0; i<length; i++)
		{
			if(user == this.members.get(i))
			{
				this.members.remove(i);
			}
		}
	}
	
	public int getNumberOfMembers()
	{
		return this.members.size();
	}
	
	public ArrayList<User> getMembers()
	{
		return this.members;
	}
	
	public void addUser(User user)
	{
		this.members.add(user);
	}
	
	public void changeName(String name)
	{
		this.name = name;
	}
	
	public String getName()
	{
		return this.name;
	}
	
	
}
