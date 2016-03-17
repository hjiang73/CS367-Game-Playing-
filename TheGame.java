///////////////////////////////////////////////////////////////////////////////
//                   ALL STUDENTS COMPLETE THESE SECTIONS
// Title:            TheGame.java
// Files:            DirectedGraph.java, GraphADT.java, Item.java,
//                   MessageHandler.java, Player.java, Room.java,
//                   Testing.java
// Semester:         CS367 Fall 2015
//
// Author:           Han Jiang
// Email:            hjiang73@wisc.edu
// CS Login:         hjiang
// Lecturer's Name:  James Skretney
// Lab Section:      Lecture 002
//
//////////////////// PAIR PROGRAMMERS COMPLETE THIS SECTION ////////////////////
//
//                   CHECK ASSIGNMENT PAGE TO see IF PAIR-PROGRAMMING IS ALLOWED
//                   If pair programming is allowed:
//                   1. Read PAIR-PROGRAMMING policy (in cs302 policy)
//                   2. choose a partner wisely
//                   3. REGISTER THE TEAM BEFORE YOU WORK TOGETHER
//                      a. one partner creates the team
//                      b. the other partner must join the team
//                   4. complete this section for each program file.
//
// Pair Partner:     You Wu
// Email:            wu278@wisc.edu
// CS Login:         ywu
// Lecturer's Name:  James Skretney
// Lab Section:      Lecture 001
//
////////////////////////////////////////////////////////////////////////////////

/**
 * the core engine that is responsible for reading the input file and processing 
 * player commands. TheGame is initialized by passing the input file as a 
 * Command Line argument.
 *
 * <p>Bugs: none known
 *
 * @author Han Jiang, You Wu
 */

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Scanner;
import java.util.Set;

public class TheGame {
	private static String gameIntro; // initial introduction to the game
	private static String winningMessage; //winning message of game
	private static String gameInfo; //additional game info
	private static boolean gameWon = false; //state of the game
	private static Scanner scanner = null; //for reading files
	private static Scanner ioscanner = null; //for reading standard input
	private static Player player; //object for player of the game
	private static Room location; //current room in which player is located
	private static Room winningRoom; //Room which player must reach to win
	private static Item winningItem; //Item which player must find
	private static DirectedGraph<Room> layout; //Graph structure of the Rooms
	private static Set<Item> winningroomitems = new HashSet<Item>();
	private static Set<Room> winningrooms = new HashSet<Room>();
	private static List<String> adjlist = new ArrayList<String>();

	public static void main(String[] args) {
		if (args.length != 1) {
			System.err.println("Bad invocation! Correct usage: "
					+ "java AppStore <gameFile>");
			System.exit(1);
		}

		boolean didInitialize = initializeGame(args[0]);

		if (!didInitialize) {
			System.err.println("Failed to initialize the application!");
			System.exit(1);
		}

		System.out.println(gameIntro); // game intro

		processUserCommands();
	}


	/**
	 * Reads the file named "gameFile" and initializes all variables for the
	 * game to run smoothly.
	 *
	 * @param String gameFile
	 * @return true or false
	 */
	private static boolean initializeGame(String gameFile) {

		try {
			//reads player name
			System.out.println("Welcome worthy squire! What might be your name?");
			ioscanner = new Scanner(System.in);
			String playerName = ioscanner.nextLine();
			Set<Item> magicsack = new HashSet<Item>();
			player = new Player(playerName, magicsack);
			layout = new DirectedGraph<Room>();
			File inFile = new File(gameFile);
			scanner = new Scanner(inFile);
			//reads the first three lines of the game. if it misses one line or more, return false;
			gameIntro = scanner.nextLine();
			winningMessage = scanner.nextLine();
			gameInfo = scanner.nextLine();
			if(gameIntro.contains("#player items:")||winningMessage.contains("#player items:")
					||gameInfo.contains("#player items:")){
				return false;
			}

			boolean checkpoint = true;
			String tmp =null;
			String line1 = null;
			boolean firstroom =false;
			while(scanner.hasNextLine()){


				if(checkpoint == true){
					line1 = scanner.nextLine();
				}
				else{
					line1 = tmp;
					checkpoint = true;
				}
				if(line1.contains("#")){
					//initialize player items
					if(line1.charAt(1) == 'p'){
						boolean endloop = false;
						while(!endloop){
							String line2 = scanner.nextLine().trim();
							if(line2.contains("#item")){
								String itemname = scanner.nextLine().trim();
								String description = scanner.nextLine().trim();
								String activatedst = scanner.nextLine().trim();
								if(!activatedst.equalsIgnoreCase("true")&&!activatedst.equalsIgnoreCase("false")){
									return false;
								}
								boolean activated = Boolean.parseBoolean(activatedst);
								String message = scanner.nextLine().trim();
								String ontimeusest = scanner.nextLine().trim();
								if(!ontimeusest.equalsIgnoreCase("true")&&!ontimeusest.equalsIgnoreCase("false")){
									return false;
								}
								boolean ontimeuse = Boolean.parseBoolean(ontimeusest);
								String usedstring = scanner.nextLine().trim();
								Item item = new Item(itemname,description,activated,message,ontimeuse,usedstring);
								player.addItem(item);
							}
							else{
								tmp = line2;
								checkpoint = false;
								endloop = true;
							}
						}
					}
					//initialize rooms
					if(line1.charAt(1) == 'r' && !line1.contains("#win")){
						String roomname = scanner.nextLine().trim();
						String roomdescription = scanner.nextLine().trim();
						String visibilityst = scanner.nextLine().trim();
						if(!visibilityst.equalsIgnoreCase("true")&&!visibilityst.equalsIgnoreCase("false")){
							return false;
						}
						boolean visibility = Boolean.parseBoolean(visibilityst);
						String habitabilityst = scanner.nextLine().trim();
						if(!habitabilityst.equalsIgnoreCase("true")&&!habitabilityst.equalsIgnoreCase("false")){
							return false;
						}
						boolean habitability = Boolean.parseBoolean(habitabilityst);
						String hsbMsg = null;
						if(!habitability){
							String line4 = scanner.nextLine().trim();
							if(!line4.contains("#")){
								hsbMsg=line4;
							}
						}
						List<MessageHandler> roommh = new ArrayList<MessageHandler>();
						Set<Item> roomitems = new HashSet<Item>();
						boolean endloop2 = false;
						while(!endloop2){
							String line3 = scanner.nextLine().trim();
							//initialize items in the room
							if(line3.charAt(1) == 'i' && !line3.contains("win")){
								String itemname = scanner.nextLine().trim();
								String description = scanner.nextLine().trim();
								String activatedst = scanner.nextLine().trim();
								if(!activatedst.equalsIgnoreCase("true")&&!activatedst.equalsIgnoreCase("false")){
									return false;
								}
								boolean activated = Boolean.parseBoolean(activatedst);
								String message = scanner.nextLine().trim();
								String ontimeusest = scanner.nextLine().trim();
								if(!ontimeusest.equalsIgnoreCase("true")&&!ontimeusest.equalsIgnoreCase("false")){
									return false;
								}
								boolean ontimeuse = Boolean.parseBoolean(ontimeusest);
								String usedstring = scanner.nextLine().trim();
								Item item = new Item(itemname,description,activated,message,ontimeuse,usedstring);
								roomitems.add(item);

							}
							//contruct the winning item
							else if(line3.contains("#item:#win")){
								String itemname = scanner.nextLine().trim();
								String description = scanner.nextLine().trim();
								String activatedst = scanner.nextLine().trim();
								if(!activatedst.equalsIgnoreCase("true")&&!activatedst.equalsIgnoreCase("false")){
									return false;
								}
								boolean activated = Boolean.parseBoolean(activatedst);
								String message = scanner.nextLine().trim();
								String ontimeusest = scanner.nextLine().trim();
								if(!ontimeusest.equalsIgnoreCase("true")&&!ontimeusest.equalsIgnoreCase("false")){
									return false;
								}
								boolean ontimeuse = Boolean.parseBoolean(ontimeusest);
								String usedstring = scanner.nextLine().trim();
								Item item = new Item(itemname,description,activated,message,ontimeuse,usedstring);
								roomitems.add(item);
								winningroomitems.add(item);
								winningItem = item;
							}
							//intialize the messageHandlers in the room
							else if(line3.contains("messageHandler")){
								String msg = scanner.nextLine().trim();
								String typest1 = scanner.nextLine().trim();
								if(typest1.contains("room")){
									String typest2 =scanner.nextLine().trim();
									MessageHandler mgh2 = new MessageHandler(msg, typest1, typest2);
									roommh.add(mgh2);
								}
								else{
									MessageHandler mgh1 = new MessageHandler(msg, typest1, roomname);
									roommh.add(mgh1);
								}


							}
							else{
								tmp = line3;
								checkpoint = false;
								endloop2 = true;
							}

						}
						Room room = new Room(roomname,roomdescription,visibility,habitability,hsbMsg,roomitems,roommh);
						if(!firstroom){
							location = room;
							firstroom=true;

						}
						layout.addVertex(room);


					}
					//contruct the winning room
					if(line1.contains("#room:#win")){
						String roomname = scanner.nextLine().trim();
						String roomdescription = scanner.nextLine().trim();
						String visibilityst = scanner.nextLine().trim();
						if(!visibilityst.equalsIgnoreCase("true")&&!visibilityst.equalsIgnoreCase("false")){
							return false;
						}
						boolean visibility = Boolean.parseBoolean(visibilityst);
						String habitabilityst = scanner.nextLine().trim();
						if(!habitabilityst.equalsIgnoreCase("true")&&!habitabilityst.equalsIgnoreCase("false")){
							return false;
						}
						boolean habitability = Boolean.parseBoolean(habitabilityst);
						String hsbMsg = null;
						if(!habitability){
							String line4 = scanner.nextLine().trim();
							if(!line4.contains("#")){
								hsbMsg=line4;
							}
						}
						Set<Item> roomitems = new HashSet<Item>();

						List<MessageHandler> roommh = new ArrayList<MessageHandler>();
						boolean endloop2 = false;
						while(!endloop2){
							String line3 = scanner.nextLine().trim();
							//initialize the items in the room
							if(line3.charAt(1) == 'i' && !line3.contains("win")){
								String itemname = scanner.nextLine().trim();
								String description = scanner.nextLine().trim();
								String activatedst = scanner.nextLine().trim();
								if(!activatedst.equalsIgnoreCase("true")&&!activatedst.equalsIgnoreCase("false")){
									return false;
								}
								boolean activated = Boolean.parseBoolean(activatedst);
								String message = scanner.nextLine().trim();
								String ontimeusest = scanner.nextLine().trim();
								if(!ontimeusest.equalsIgnoreCase("true")&&!ontimeusest.equalsIgnoreCase("false")){
									return false;
								}
								boolean ontimeuse = Boolean.parseBoolean(ontimeusest);
								String usedstring = scanner.nextLine().trim();
								Item item = new Item(itemname,description,activated,message,ontimeuse,usedstring);
								roomitems.add(item);
							}
							//construct the winning item
							else if(line3.contains("#item:#win")){
								String itemname = scanner.nextLine().trim();
								String description = scanner.nextLine().trim();
								String activatedst = scanner.nextLine().trim();
								if(!activatedst.equalsIgnoreCase("true")&&!activatedst.equalsIgnoreCase("false")){
									return false;
								}
								boolean activated = Boolean.parseBoolean(activatedst);
								String message = scanner.nextLine().trim();
								String ontimeusest = scanner.nextLine().trim();
								if(!ontimeusest.equalsIgnoreCase("true")&&!ontimeusest.equalsIgnoreCase("false")){
									return false;
								}
								boolean ontimeuse = Boolean.parseBoolean(ontimeusest);
								String usedstring = scanner.nextLine().trim();
								Item item = new Item(itemname,description,activated,message,ontimeuse,usedstring);
								roomitems.add(item);
								winningroomitems.add(item);
								winningItem = item;

							}
							//initialize the messageHandlers in the room
							else if(line3.charAt(1) == 'm'){
								String msg = scanner.nextLine().trim();
								String typest1 = scanner.nextLine().trim();
								if(typest1.contains("room")){
									String typest2 =scanner.nextLine().trim();
									MessageHandler mgh2 = new MessageHandler(msg, typest1, typest2);
									roommh.add(mgh2);
								}
								else{
									MessageHandler mgh1 = new MessageHandler(msg, typest1, roomname);
									roommh.add(mgh1);
								}
							}
							else{
								tmp = line3;
								checkpoint = false;
								endloop2 = true;
							}

						}
						winningRoom = new Room(roomname,roomdescription,visibility,habitability,hsbMsg,roomitems,roommh);
						winningrooms.add(winningRoom);
						layout.addVertex(winningRoom);
					}
					//initialize the locked passages
					if(line1.contains("#locked passages:")){
						boolean endloop3 = false;
						while(!endloop3){
							String line4 = scanner.nextLine();
							if(line4.contains("#")){
								tmp = line4;
								checkpoint = false;
								endloop3 = true;
							}
							else{
								//find room by roomn then use add method
								String[] tworooms = line4.split(" ");
								Set<Room> rooms = layout.getAllVertices();
								Iterator<Room> itr = rooms.iterator();
								Iterator<Room> itr1 = rooms.iterator();
								Room room1 = null;
								Room room2 =null;
								while(itr.hasNext()){
									Room tmp1 = itr.next();
									if(tmp1.getName().equals(tworooms[0])){
										room1=tmp1;
									}
								}
								while(itr1.hasNext()){
									Room tmp2 = itr1.next();
									if(tmp2.getName().equals(tworooms[1])){
										room2=tmp2;
									}
								}
								String whyLocked = scanner.nextLine().trim();
								room1.addLockedPassage(room2, whyLocked);
							}

						}
					}
					//initialize the adjacency list and add edge between vertexes in the graph
					if(line1.contains("Adjacency")){
						while(scanner.hasNextLine()){
							String adj = scanner.nextLine().trim();
							adjlist.add(adj);
							String[] vertexes = adj.split(" ");
							int size = vertexes.length;
							Set<Room> rooms = layout.getAllVertices();
							Iterator<Room> itr1 = rooms.iterator();
							Room organialroom = null;
							Room targetroom =null;
							while(itr1.hasNext()){
								Room tmp2 = itr1.next();
								if(tmp2.getName().equalsIgnoreCase((vertexes[0]))){
									organialroom = tmp2;
								}
							}
							for(int i = 1;i<size;i++){
								String targetroomst = vertexes[i];
								Iterator<Room> itr2 = rooms.iterator();

								while(itr2.hasNext()){
									Room tmp3 = itr2.next();
									if(tmp3.getName().equals(targetroomst)){
										targetroom = tmp3;
									}
								}
								layout.addEdge(organialroom, targetroom);


							}

						}
					}


				}
			}
			if(adjlist.isEmpty()){
				return false;
			}
			if(winningRoom ==null){
				return false;
			}
			if(winningroomitems.size()>1){
				return false;
			}
			if(winningrooms.size()>1){
				return false;
			}
			return true;
		}catch (FileNotFoundException e) {
			e.printStackTrace();
			return false;
		} catch (Exception e){
			e.printStackTrace();
			return false;
		}
	}

	private static void processUserCommands() {
		String command = null;
		do {

			System.out.print("\nPlease Enter a command ([H]elp):");
			command = ioscanner.next();
			switch (command.toLowerCase()) {
			case "p": // pick up
				processPickUp(ioscanner.nextLine().trim());
				goalStateReached();
				break;
			case "d": // put down item
				processPutDown(ioscanner.nextLine().trim());
				break;
			case "u": // use item
				processUse(ioscanner.nextLine().trim());
				break;
			case "lr":// look around
				processLookAround();
				break;
			case "lt":// look at
				processLookAt(ioscanner.nextLine().trim());
				break;
			case "ls":// look at sack
				System.out.println(player.printSack());
				break;
			case "g":// goto room
				processGoTo(ioscanner.nextLine().trim());
				goalStateReached();
				break;
			case "q":
				System.out.println("You Quit! You, " + player.getName() + ", are a loser!!");
				break;
			case "i":
				System.out.println(gameInfo);
				break;
			case "h":
				System.out
				.println("\nCommands are indicated in [], and may be followed by \n"+
						"any additional information which may be needed, indicated within <>.\n"
						+ "\t[p]  pick up item: <item name>\n"
						+ "\t[d]  put down item: <item name>\n"
						+ "\t[u]  use item: <item name>\n"
						+ "\t[lr] look around\n"
						+ "\t[lt] look at item: <item name>\n"
						+ "\t[ls] look in your magic sack\n"
						+ "\t[g]  go to: <destination name>\n"
						+ "\t[q]  quit\n"
						+ "\t[i]  game info\n");
				break;
			default:
				System.out.println("Unrecognized Command!");
				break;
			}
		} while (!command.equalsIgnoreCase("q") && !gameWon);
		ioscanner.close();
	}

	private static void processLookAround() {
		System.out.print(location.toString());
		for(Room rm : layout.getNeighbors(location)){
			System.out.println(rm.getName());
		}
	}

	private static void processLookAt(String item) {
		Item itm = player.findItem(item);
		if(itm == null){
			itm = location.findItem(item);
		}
		if(itm == null){
			System.out.println(item + " not found");
		}
		else
			System.out.println(itm.toString());
	}

	private static void processPickUp(String item) {
		if(player.findItem(item) != null){
			System.out.println(item + " already in sack");
			return;
		}
		Item newItem = location.findItem(item);
		if(newItem == null){
			System.out.println("Could not find " + item);
			return;
		}
		player.addItem(newItem);
		location.removeItem(newItem);
		System.out.println("You picked up ");
		System.out.println(newItem.toString());
	}

	private static void processPutDown(String item) {
		if(player.findItem(item) == null){
			System.out.println(item + " not in sack");
			return;
		}
		Item newItem = player.findItem(item);
		location.addItem(newItem);
		player.removeItem(newItem);
		System.out.println("You put down " + item);
	}

	private static void processUse(String item) {
		Item newItem = player.findItem(item);
		if(newItem == null){
			System.out.println("Your magic sack doesn't have a " + item);
			return;
		}
		if (newItem.activated()) {
			System.out.println(item + " already in use");
			return;
		}
		if(notifyRoom(newItem)){
			if (newItem.isOneTimeUse()) {
				player.removeItem(newItem);
			}
		}
	}

	private static void processGoTo(String destination) {
		Room dest = findRoomInNeighbours(destination);
		if(dest == null) {
			for(Room rm : location.getLockedPassages().keySet()){
				if(rm.getName().equalsIgnoreCase(destination)){
					System.out.println(location.getLockedPassages().get(rm));
					return;
				}
			}
			System.out.println("Cannot go to " + destination + " from here");
			return;
		}
		Room prevLoc = location;
		location = dest;
		if(!player.getActiveItems().isEmpty())
			System.out.println("The following items are active:");
		for(Item itm:player.getActiveItems()){
			notifyRoom(itm);
		}
		if(!dest.isHabitable()){
			System.out.println("Thou shall not pass because");
			System.out.println(dest.getHabitableMsg());
			location = prevLoc;
			return;
		}

		System.out.println();
		processLookAround();
	}

	private static boolean notifyRoom(Item item) {
		Room toUnlock = location.receiveMessage(item.on_use());
		if (toUnlock == null) {
			if(!item.activated())
				System.out.println("The " + item.getName() + " cannot be used here");
			return false;
		} else if (toUnlock == location) {
			System.out.println(item.getName() + ": " + item.on_useString());
			item.activate();
		} else {
			// add edge from location to to Unlock
			layout.addEdge(location, toUnlock);
			if(!item.activated())
				System.out.println(item.on_useString());
			item.activate();
		}
		return true;
	}

	private static Room findRoomInNeighbours(String room) {
		Set<Room> neighbours = layout.getNeighbors(location);
		for(Room rm : neighbours){
			if(rm.getName().equalsIgnoreCase(room)){
				return rm;
			}
		}
		return null;
	}

	private static void goalStateReached() {
		if ((location == winningRoom && player.hasItem(winningItem)) 
				|| (location == winningRoom && winningItem == null)){
			System.out.println("Congratulations, " + player.getName() + "!");
			System.out.println(winningMessage);
			System.out.println(gameInfo);
			gameWon = true;
		}
	}

}
