///////////////////////////////////////////////////////////////////////////////
//                   ALL STUDENTS COMPLETE THESE SECTIONS
// Main Class File:  TheGame.java
// File:             Player.java
// Semester:         CS 367 Fall 2015
//
// Author:           Han Jiang
// Email:            hjiang73@wisc.edu
// CS Login:         hjiang
// Lecturer's Name:  James Skretney
// Lab Section:      002
//
//////////////////// PAIR PROGRAMMERS COMPLETE THIS SECTION ////////////////////
//
// Pair Partner:     You Wu
// Email:            wu278@wisc.edu
// CS Login:         ywu
// Lecturer's Name:  James Skretney
// Lab Section:      001
//////////////////////////// 80 columns wide //////////////////////////////////
import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;

/**
 * Player class creates a player object 
 * Each Player needs a name, and a magic sack to store all his items. 
 * Once a player picks up an item, it goes in the sack, and he can use it whenever he wants.
 * <p>Bugs: None known
 * @author Han Jiang & You Wu
 */
public class Player {
	// player name
	private String name;
	// the magic sack held by the player that contains all his/her items
	private Set<Item> magicSack;
	//Do not add anymore private data members

	public Player(String name, Set<Item> startingItems){
		if(name==null||startingItems==null)
			throw new IllegalArgumentException();
		//Constructor
		this.name = name;
		this.magicSack = startingItems;
	}

	/**
	 * Getter method for the Name of the player
	 * @return String player name
	 */
	public String getName(){
		return this.name;
	}

	//Returns a String consisting of the items in the sack
	//DO NOT MODIFY THIS METHOD
	public String printSack(){
		//neatly printed items in sack
		StringBuilder sb = new StringBuilder();
		sb.append("Scanning contents of your magic sack");
		sb.append(System.getProperty("line.separator"));
		for(Item itm : magicSack){
			sb.append(itm.getName());
			sb.append(System.getProperty("line.separator"));
		}
		return sb.toString();
	}

	/**
	 * Iterate through the sack, and find the items whose status is activated. 
	 * This is used in TheGame class when a user enters a new room, 
	 * so that all active items work in the new room.
	 * @return Set of all active items
	 */
	public Set<Item> getActiveItems(){
		Set<Item> actitems = new HashSet<Item>() ;
		Iterator<Item> itr = magicSack.iterator();
		while(itr.hasNext()){
			Item tmp = itr.next();
			if(tmp.activated()){
				actitems.add(tmp);
			}	
		}
		return actitems;
	}

	/**
	 * Find the Item in the sack whose name is "itemName". 
	 * Return the item if you find it, otherwise return null.
	 * @param string item name
	 * @return Item found by name
	 */
	public Item findItem(String item){
		Item found = null;
		Iterator<Item> itr = magicSack.iterator();
		while(itr.hasNext()){
			Item tmp = itr.next();
			if(tmp.getName().equals(item)){
				found = tmp;
			}
		}
		return found;
	}

	/**
	 * Checks if the player has the "item" in his sack. 
	 * Returns true if he does, otherwise returns false.
	 * @param Item item
	 * @return boolean
	 */
	public boolean hasItem(Item item){
			
		return magicSack.contains(item);
	}

	/**
	 * Adds the "item" to the Player's sack. Duplicate items are not allowed. 
	 * Returns true if item successfully added, else returns false.
	 * @param Item item
	 * @return boolean
	 */
	public boolean addItem(Item item){
		if(item==null)
			return false;
		return magicSack.add(item);
	}

	/**
	 * Removes the "item" from the sack. 
	 * Returns true if removal is successful, else returns false.
	 * @param Item item
	 * @return boolean
	 */
	public boolean removeItem(Item item){
		return magicSack.remove(item);
	}
}
