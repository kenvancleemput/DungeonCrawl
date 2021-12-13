import java.security.PrivateKey;
import java.util.HashSet;

/**
 *  This class is the main class of the "Dungeon Crawl" application.
 *  "Dungeon Crawl" is a very simple, text based adventure game.  Users
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
 * @version 2011.07.31
 */

public class Game {
    private Parser parser;
    private Room currentRoom;
    private Player player;
    private HashSet<Room> collection;
    private HashSet<Monster> monsters;

    /**
     * Create the game and initialise its internal map.
     */
    public Game() {
        player = new Player("Matthew");
        collection= new HashSet<>();
        createRooms();
        parser = new Parser();

    }

    /**
     * Create all the rooms and link their exits together.
     */
    private void createRooms() {
        Room outside, theater, pub, lab, office, cellar, armoury;
        Item mace, spear, elven_chain_mail, healing_potion, sword, shield;


        // create the rooms
        outside = new Room("outside the main entrance of the university");
        theater = new Room("in a lecture theater");
        pub = new Room("in the campus pub");
        lab = new Room("in a computing lab");
        office = new Room("in the computing admin office");
        cellar = new Room("In the cellar with all the provisions for the pub");

        // add rooms to collection HashSet
        collection.add(outside);
        collection.add(theater);
        collection.add(pub);
        collection.add(lab);
        collection.add(office);
        collection.add(cellar);

        // initialise room exits
        outside.setExit("east", theater);
        outside.setExit("south", lab);
        outside.setExit("west", pub);
        theater.setExit("west", outside);
        pub.setExit("east", outside);
        pub.setExit("down", cellar);
        cellar.setExit("up", pub);
        lab.setExit("north", outside);
        lab.setExit("east", office);
        office.setExit("west", lab);

        //Create items
        mace = new Item("A sturdy mace", 5, "mace", true, true, false);
        spear = new Item("A spear made from bone", 4.5, "shield", true, true, false);
        elven_chain_mail = new Item("An elven chain mail ", 0.3, "elven chain mail", true, true, false);
        healing_potion = new Item("a healing potion", 5.5, "healing potion", true, false, true);
        sword = new Item("a foam sword", 0.7, "sword", true, true, false);
        shield = new Item("a shield made of cardboard", 0.2, "shield", true, true, false);

        // add items to collections per room

        for(Room room: collection){
            room.addItem(mace);
            room.addItem(spear);
            room.addItem(elven_chain_mail);
            room.addItem(healing_potion);
            room.addItem(sword);
            room.addItem(shield);
            room.getRandomItems();
        }

        player.setCurrentRoom(outside);  // start game outside


    }

    /**
     * Main play routine.  Loops until end of play.
     */
    public void play() {
        printWelcome();

        // Enter the main command loop.  Here we repeatedly read commands and
        // execute them until the game is over.

        boolean finished = false;
        while (!finished) {
            Command command = parser.getCommand();
            finished = processCommand(command);
        }
        System.out.println("Thank you for playing.  Good bye.");
    }

    /**
     * Print out the opening message for the player.
     */
    private void printWelcome() {
        System.out.println();
        System.out.println("Welcome to the World of Zuul!");
        System.out.println("World of Zuul is a new, incredibly boring adventure game.");
        System.out.println("Type 'help' if you need help.");
        System.out.println();
        printLocationInfo();
    }

    /**
     * Given a command, process (that is: execute) the command.
     *
     * @param command The command to be processed.
     * @return true If the command ends the game, false otherwise.
     */
    private boolean processCommand(Command command) {
        boolean wantToQuit = false;

        if (command.isUnknown()) {
            System.out.println("I don't know what you mean...");
            return false;
        }

        String commandWord = command.getCommandWord();
        if (commandWord.equals("help")) {
            printHelp();
        } else if (commandWord.equals("go")) {
            goRoom(command);
        } else if (commandWord.equals("look")) {
            printLocationInfo();
        } else if (commandWord.equals("eat")) {
            System.out.println("You have already eaten and are no longer hungry.");
            System.out.println(" ");
        } else if (commandWord.equals("take")) {
            takeItem(command);
        } else if (commandWord.equals("drop")) {
            dropItem(command);
        } else if (commandWord.equals("quit")) {
            wantToQuit = quit(command);
        }


        return wantToQuit;
    }

    // implementations of user commands:

    /**
     * Print out some help information.
     * Here we print some stupid, cryptic message and a list of the
     * command words.
     */
    private void printHelp() {
        System.out.println("Player " + player.getName() + " is lost and alone, and wanders");
        System.out.println("around at the university.");
        System.out.println();
        System.out.println("Possible command words are:");
        System.out.println(parser.showCommands());
        System.out.println();
    }

    /**
     * Try to go in one direction. If there is an exit, enter
     * the new room, otherwise print an error message.
     */
    private void goRoom(Command command) {
        if (!command.hasSecondWord()) {
            // if there is no second word, we don't know where to go...
            System.out.println("Go where?");
            return;
        }

        String direction = command.getSecondWord();

        // Try to leave current room.
        if (!player.go(direction)) {
            System.out.println("There is no door!");
        } else {
            printLocationInfo();
        }
    }

    private boolean takeItem(Command command) {
        if (!command.hasSecondWord()) {
            // if there is no second word, we don't know where to go...
            System.out.println("Take what?");
            return false;
        } else {
            if (player.getCurrentRoom().hasItem(command.getSecondWord())) {
                player.take(command.getSecondWord());
                System.out.println(player.getInfo());
            }
        } return true;
    }

    private void dropItem(Command command){
        if (!command.hasSecondWord()) {
            // if there is no second word, we don't know where to go...
            System.out.println("Drop what?");
        } else {
            if(player.inventory().contains(command.getSecondWord()))
                player.drop(command.getSecondWord());
            System.out.println(player.getInfo());
        }
    }

    /**
     * "Quit" was entered. Check the rest of the command to see
     * whether we really quit the game.
     *
     * @return true, if this command quits the game, false otherwise.
     */
    private boolean quit(Command command) {
        if (command.hasSecondWord()) {
            System.out.println("Quit what?");
            return false;
        } else {
            return true;  // signal that we want to quit
        }
    }

    private void printLocationInfo() {
        System.out.println(player.getInfo());
        System.out.println();
    }



    public static void main(String[] args) {
        Game game = new Game();
        game.play();
    }
}
