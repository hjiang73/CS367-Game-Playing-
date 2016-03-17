///////////////////////////////////////////////////////////////////////////////
//                   ALL STUDENTS COMPLETE THESE SECTIONS
// Main Class File:  TheGame.java
// File:             Item.java
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

/**
 * Create a Item object that players can interact (pick up, use, drop) with
 * <p>Bugs: None known
 * @author Han Jiang & You Wu
 */
public class Item {
	// name of item
	private String	name;
	// description of item
	private String	description;
	//If activated is true, the item is active and has been used.
	private boolean activated;
	//message of item
	private String message;
	//If oneTimeUse is true, the item can be used only once. 
	private boolean oneTimeUse;
	//usedString of item
	private String usedString;


	public Item(String name, String description, boolean activated, 
			String message,boolean oneTimeUse, String usedString){
		if(name==null||description==null|message==null||usedString==null){
			throw new IllegalArgumentException();
		}
		//Constructor
		this.name= name;
		this.description = description;
		this.activated = activated;
		this.message = message;
		this.oneTimeUse = oneTimeUse;
		this.usedString = usedString;
	}

	/**
	 * Getter method for the Name of the Item.
	 * @return String name
	 */
	public String getName(){
		return this.name;
	}

	/**
	 * Getter method for the Description of the Item
	 * @return String description
	 */
	public String getDescription(){
		return this.description;
	}

	/**
	 * Checks if the item is activated. 
	 * Returns true if it is, else return false.
	 * @return boolean activated
	 */
	public boolean activated(){

		return this.activated;
	}

	/**
	 * Returns the "message" that the item wants to send to the room.
	 * @return String message
	 */
	public String on_use(){
		return this.message;
	}

	/**
	 * Activates the object. This changes the activation status to true.
	 */
	public void activate(){
		this.activated = true;
	}

	/**
	 * Returns the "on_useString" for the Item. 
	 * This is printed in the notifyRoom() function in 
	 * TheGame class after an item has been used and is active.
	 * @return String usedString
	 */
	public String on_useString(){
		return this.usedString;
	}

	/**
	 * Returns true if the item can only be used once. 
	 * Else returns false. 
	 * This is used in TheGame to remove single-time use items after they are used.
	 * @return boolean oneTimeUse
	 */
	public boolean isOneTimeUse(){
		return this.oneTimeUse;
	}

	@Override
	//This returns a String consisting of the name and description of the Item
	//This has been done for you.
	//DO NOT MODIFY
	public String toString(){
		StringBuilder sb = new StringBuilder();
		sb.append("Item Name: " + this.name);
		sb.append(System.getProperty("line.separator"));
		sb.append("Description: " + this.description);
		return sb.toString();
	}
}
