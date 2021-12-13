import java.util.HashMap;
import java.util.HashSet;
import java.util.Random;

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
    private HashMap<Integer, Room> collection;
    private NPC NPC;
    private HashSet<NPC> spawnList;

    /**
     * Create the game and initialise its internal map.
     */
    public Game() {
        player = new Player("Matthew");
        collection= new HashMap<>();
        createRooms();
        parser = new Parser();
        spawnList = new HashSet<>();

    }

    /**
     * Create all the rooms and link their exits together.
     */
    private void createRooms() {
        Room entrance, theater, pub, lab, office, cellar, armoury;
        Item mace, spear, elvenchainmail, healingpotion, sword, shield;
        NPC rat, goblin, orc, trader, boss;


        // create the rooms
        entrance = new Room("While your eyes are adjusting to the flickering torchlight of the single torch which lights the hallway at the end of the stairs, you can't help but notice the brown smears on the floor, leading to the room east of you.\nFrom the room to the west, there's an unpleasant odour wafting your way.\nTo the north of you, a steel door stands closed, uninviting and with scratch marks all over it.\nWhich way will you go?");
        theater = new Room("in a lecture theater");
        pub = new Room("in the campus pub");
        lab = new Room("in a computing lab");
        office = new Room("in the computing admin office");
        cellar = new Room("In the cellar with all the provisions for the pub");

        // add rooms to collection HashSet
        collection.put (0 ,entrance);
        collection.put(1, theater);
        collection.put(2, pub);
        collection.put(3, lab);
        collection.put(4, office);
        collection.put(5, cellar);

        // initialise room exits
        entrance.setExit("east", theater);
        entrance.setExit("south", lab);
        entrance.setExit("west", pub);
        theater.setExit("west", entrance);
        pub.setExit("east", entrance);
        pub.setExit("down", cellar);
        cellar.setExit("up", pub);
        lab.setExit("north", entrance);
        lab.setExit("east", office);
        office.setExit("west", lab);

        //Create items
        mace = new Item("A sturdy mace", 5, "mace", true, true, false);
        spear = new Item("A spear made from bone", 4.5, "shield", true, true, false);
        elvenchainmail = new Item("An elven chain mail ", 0.3, "elven chain mail", true, true, false);
        healingpotion = new Item("a healing potion", 5.5, "healing potion", true, false, true);
        sword = new Item("a foam sword", 0.7, "sword", true, true, false);
        shield = new Item("a shield made of cardboard", 0.2, "shield", true, true, false);

        // add items to collections per room

        for(Room room: collection){
            room.addItem(mace);
            room.addItem(spear);
            room.addItem(elvenchainmail);
            room.addItem(healingpotion);
            room.addItem(sword);
            room.addItem(shield);
            room.getRandomItems();
        }

        // Create NPC's
        rat = new NPC("Rat", "A mutated rat", 5,10,0,false);
        goblin = new NPC("Goblin","A small green humanoid",10,12,2,false);
        orc = new NPC("Orc","A towering green giant",15,14,4,false);
        trader = new NPC("Khajit","A friendly gnome selling some wares",200,20,10,true);
        boss = new NPC("The Skeleton King","A skeleton with a shining crown and a familiar sword",30,18,6,false);

        // Add NPC's to Hashset
        spawnList.add(rat);
        spawnList.add(goblin);
        spawnList.add(orc);
        spawnList.add(trader);
        spawnList.add(boss);

        //Add NPC's to random rooms
        for(NPC npc:spawnList){
            if (!npc.getName().equals(boss)){
                Random r= new Random();
                npc.setCurrentRoom(collection.get(r.nextInt(collection.size())));
            }
        }

        player.setCurrentRoom(entrance);  // start game outside


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
        System.out.println("Welcome to Dungeon Crawl!");
        System.out.println("Dungeon Crawl tasks you with finding your family's lost weapon");
        System.out.println("Type 'help' if you need help.");
        System.out.println("It's with a decent amount of trepidation that you descend the stairs in to the dungeon.\nYou've heard the rumours of the wealth hoarded by its denizens.\nYou know for a fact that your brother descended into here, never to be seen again.\nWith him, he was carrying the ancestral blade of your family.\nCan you retrieve the blade and defeat the evil that lurks here?");
        printInfo();
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

    private void takeItem(Command command) {
        if (!command.hasSecondWord()) {
            // if there is no second word, we don't know where to go...
            System.out.println("Take what?");
        } else {
            if (player.take(command.getSecondWord()));
                System.out.println(player.getInfo());
            }
        }

    private void dropItem(Command command){
        if (!command.hasSecondWord()) {
            // if there is no second word, we don't know where to go...
            System.out.println("Drop what?");
        } else {
            if(player.drop(command.getSecondWord())) {
                System.out.println(player.getInfo());
            } else {
                System.out.println("My bag does not contain " + command.getSecondWord());
            }
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
    private void printInfo(){
        System.out.println(player.getCurrentRoom().getLongDescription());
        System.out.println();
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
