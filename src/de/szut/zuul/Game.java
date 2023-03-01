package de.szut.zuul;

/**
 *  This class is the main class of the "World of Zuul" application. 
 *  "World of Zuul" is a very simple, text based adventure game.  Users 
 *  can walk around some scenery. That's all. It should really be extended 
 *  to make it more interesting!
 * 
 *  To play this game, create an instance of this class and call the "play"
 *  method.
 * 
 *  This main class creates and initialises all the others: it creates all
 *  rooms, creates the parser and starts the game.  It also evaluates and
 *  executes the commands that the parser returns.
 * 
 * @author  Michael Kölling and David J. Barnes
 * @version 2016.02.29
 */

public class Game 
{
    private Parser parser;
    private Room currentRoom;
        
    /**
     * Create the game and initialise its internal map.
     */
    public Game() 
    {
        createRooms();
        parser = new Parser();
    }

    /**
     * Create all the rooms and link their exits together.
     */
    private void createRooms()
    {
        Room marketsquare, templePyramid, tavern, sacrificialSite, hut, jungle, secretPassage, cave, beach,basement,magician_room;
      
        // create the rooms
        marketsquare = new Room("on the market square");
        templePyramid = new Room("in a temple pyramid");
        tavern = new Room("in the tavern at the market square");
        sacrificialSite = new Room("at a sacrificial site");
        hut = new Room("in a hut");
        jungle = new Room("in the jungle");
        secretPassage = new Room("in a secret passage");
        cave = new Room("in a cave");
        beach = new Room("on the beach");
        basement = new Room("in the basement");
        magician_room = new Room("in the magician's room");

        // initialise room exits
        marketsquare.setExits("north",tavern);
        marketsquare.setExits("east", templePyramid);
        marketsquare.setExits("west", sacrificialSite);
        templePyramid.setExits("north", hut);
        templePyramid.setExits("west", marketsquare);
        templePyramid.setExits("up", magician_room);
        templePyramid.setExits("down", basement);
        tavern.setExits("south", marketsquare);
        tavern.setExits("east", hut);
        sacrificialSite.setExits("down", cave);
        sacrificialSite.setExits("east", marketsquare);
        hut.setExits("east", jungle);
        hut.setExits("south", templePyramid);
        hut.setExits("west", tavern);
        jungle.setExits("west", hut);
        secretPassage.setExits("east", basement);
        secretPassage.setExits("west", cave);
        cave.setExits("east", secretPassage);
        cave.setExits("south", beach);
        beach.setExits("north", cave);
        basement.setExits("up", templePyramid);
        basement.setExits("west", secretPassage);
        magician_room.setExits("down", templePyramid);

        marketsquare.setItem(new Item("Bow", "a wooden bow", 0.5));
        cave.setItem(new Item("Treasure", "a small treasure chest filled with coins", 7.5));
        magician_room.setItem(new Item("Arrows", "a quiver with various  arrows", 1));
        jungle.setItem(new Item("Plant", "a healing plant", 0.5));
        jungle.setItem(new Item("Cocoa", "a small cocoa tree", 5));
        sacrificialSite.setItem(new Item("Knife", "a very sharp, large knife", 1));
        hut.setItem(new Item("Spear", "a spear with a matching sling", 5.0));
        tavern.setItem(new Item("Food", "a plate of hearty meat and cornmeal", 0.5));
        basement.setItem(new Item("Jewelry", "a very pretty headdress", 1));

        currentRoom = marketsquare;  // start game on marketsquare
    }

    /**
     *  Main play routine.  Loops until end of play.
     */
    public void play() 
    {            
        printWelcome();

        // Enter the main command loop.  Here we repeatedly read commands and
        // execute them until the game is over.
                
        boolean finished = false;
        while (! finished) {
            Command command = parser.getCommand();
            finished = processCommand(command);
        }
        System.out.println("Thank you for playing.  Good bye.");
    }

    /**
     * Print out the opening message for the player.
     */
    private void printWelcome()
    {
        System.out.println();
        System.out.println("Welcome to the World of Zuul!");
        System.out.println("World of Zuul is a new, incredibly boring adventure game.");
        System.out.println("Type 'help' if you need help.");
        System.out.println();
        System.out.println(currentRoom.getLongDescription());
        System.out.println();
    }

    /**
     * Given a command, process (that is: execute) the command.
     * @param command The command to be processed.
     * @return true If the command ends the game, false otherwise.
     */
    private boolean processCommand(Command command) 
    {
        boolean wantToQuit = false;

        if(command.isUnknown()) {
            System.out.println("I don't know what you mean...");
            return false;
        }

        String commandWord = command.getCommandWord();
        if (commandWord.equals("help")) {
            printHelp();
        }
        else if (commandWord.equals("go")) {
            goRoom(command);
        }
        else if (commandWord.equals("quit")) {
            wantToQuit = quit(command);
        }
        else if (commandWord.equals("look")) {
            look();
        }

        return wantToQuit;
    }
    private void look(){
        System.out.println(currentRoom.getLongDescription());
    }

    // implementations of user commands:

    /**
     * Print out some help information.
     * Here we print some stupid, cryptic message and a list of the 
     * command words.
     */
    private void printHelp() 
    {
        System.out.println("You are lost. You are alone. You wander");
        System.out.println("through the jungle. At once there is a glade. On it there a buildings...");
        System.out.println();
        System.out.println("Your command words are:");
        System.out.println("   " + parser.showCommands());
    }

    /** 
     * Try to go in one direction. If there is an exit, enter
     * the new room, otherwise print an error message.
     */
    private void goRoom(Command command) 
    {
        if(!command.hasSecondWord()) {
            // if there is no second word, we don't know where to go...
            System.out.println("Go where?");
            return;
        }

        String direction = command.getSecondWord();

        // Try to leave current room.
        Room nextRoom = null;
        switch (direction) {
            case "north":
                nextRoom = currentRoom.getExit("north");
                break;
            case "east":
                nextRoom = currentRoom.getExit("east");
                break;
            case "south":
                nextRoom = currentRoom.getExit("south");
                break;
            case "west":
                nextRoom = currentRoom.getExit("west");
                break;
            case "up":
                nextRoom = currentRoom.getExit("up");
                break;
            case "down":
                nextRoom = currentRoom.getExit("down");
                break;
        }

        if (nextRoom == null) {
            System.out.println("There is no door!");
        }
        else {
            currentRoom = nextRoom;
            System.out.println(currentRoom.getLongDescription());
            System.out.println();
        }
    }

    /** 
     * "Quit" was entered. Check the rest of the command to see
     * whether we really quit the game.
     * @return true, if this command quits the game, false otherwise.
     */
    private boolean quit(Command command) 
    {
        if(command.hasSecondWord()) {
            System.out.println("Quit what?");
            return false;
        }
        else {
            return true;  // signal that we want to quit
        }
    }
}
