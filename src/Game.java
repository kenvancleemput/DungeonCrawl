import java.util.*;

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
    private Character player;
    private HashMap<Integer, Room> collection;
    private Character NPC;
    private ArrayList<Character> spawnList;
    private HashMap<Integer, Character> charsInRoom;

    /**
     * Create the game and initialise its internal map.
     */
    public Game() {
        player = new Player("Albrecht", 10, 10, 2, 4, true);
        collection = new HashMap<>();
        spawnList = new ArrayList<Character>();
        charsInRoom = new HashMap<>();
        createRooms();
        parser = new Parser();


    }

    /**
     * Create all the rooms and link their exits together.
     */
    private void createRooms() {
        Room entrance, theater, pub, lab, office, cellar, armoury, waitingroom;
        Item mace, spear, elvenchainmail, healingpotion, sword, shield;
        Character rat, goblin, orc, trader, boss;


        // create the rooms
        waitingroom = new Room("room for all the monsters before the game starts");
        entrance = new Room("While your eyes are adjusting to the flickering torchlight of the single torch which lights the hallway at the end of the stairs, you can't help but notice the brown smears on the floor, leading to the room east of you.\nFrom the room to the west, there's an unpleasant odour wafting your way.\nTo the north of you, a steel door stands closed, uninviting and with scratch marks all over it.\nWhich way will you go?");
        theater = new Room("in a lecture theater");
        pub = new Room("in the campus pub");
        lab = new Room("in a computing lab");
        office = new Room("in the computing admin office");
        cellar = new Room("In the cellar with all the provisions for the pub");

        // add rooms to collection ArrayList
        collection.put(0, entrance);
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

        for (Integer i : collection.keySet()) {
            collection.get(i).addItem(mace);
            collection.get(i).addItem(spear);
            collection.get(i).addItem(elvenchainmail);
            collection.get(i).addItem(healingpotion);
            collection.get(i).addItem(sword);
            collection.get(i).addItem(shield);
            collection.get(i).getRandomItems();
        }

        // Create Characters
        rat = new NPC("Rat", 5, 10, 1, 4, true, false, "A giant rat");
        goblin = new NPC("Goblin", 10, 12, 2, 5, true, false, "A small green humanoid");
        orc = new NPC("Orc", 15, 14, 3, 6, true, false, "A towering green giant");
        trader = new NPC("Khajit", 200, 20, 10, 10, false, true, "A friendly gnome selling some wares");
        boss = new NPC("The Skeleton King", 30, 18, 6, 8, false, false, "A skeleton with a shining crown and a familiar sword");

        // add NPC's to spawnList
        Collections.addAll(spawnList, rat, goblin, orc, trader, boss);

        //set rooms for NPC's
        for (Character character : spawnList) {
            if (character.getName().equals("The Skeleton King")) {
                boss.setCurrentRoom(cellar);
            } else {
                Random r = new Random();
                int i = r.nextInt(collection.size());
                if (i == 0) {
                    character.setCurrentRoom(collection.get(i + 1));
                }
                character.setCurrentRoom(collection.get(i));
            }
        }
        // start game outside
        player.setCurrentRoom(entrance);


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

        CommandWord commandWord = command.getCommandWord();
        switch (commandWord) {
            case UNKNOWN:
                System.out.println("I don't know what you mean...");
                break;
            case HELP:
                printHelp();
                break;
            case GO:
                goRoom(command);
                break;
            case LOOK:
                printLocationInfo();
                break;
            case EAT:
                eat();
                break;
            case DROP:
                dropItem(command);
                break;
            case TAKE:
                takeItem(command);
                break;
            case QUIT:
                wantToQuit = true;
                break;
            default:
                System.out.println("I don't know what you mean");
        }
        checkNPC();
        if (charsInRoom.size() > 1) {


        }
        return wantToQuit;
    }

        // implementations of user commands:

        /**
         * Print out some help information.
         * Here we print some stupid, cryptic message and a list of the
         * command words.
         */
        private void printHelp () {
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
        private void goRoom (Command command){
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
                for (Character character : spawnList) {
                    if (character.getMovable()) {
                        String exit = character.getCurrentRoom().getRandomExit();
                        character.go(exit);
                    }
                }
            }
        }

        private void takeItem (Command command){
            if (!command.hasSecondWord()) {
                // if there is no second word, we don't know where to go...
                System.out.println("Take what?");
            } else {
                if (player.take(command.getSecondWord())) ;
                System.out.println(player.getInfo());
            }
        }

        private void dropItem (Command command){
            if (!command.hasSecondWord()) {
                // if there is no second word, we don't know where to go...
                System.out.println("Drop what?");
            } else {
                if (player.drop(command.getSecondWord())) {
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
        private boolean quit (Command command){
            if (command.hasSecondWord()) {
                System.out.println("Quit what?");
                return false;
            } else {
                return true;  // signal that we want to quit
            }
        }
        private void printInfo () {
            System.out.println(player.getCurrentRoom().getLongDescription());
            System.out.println();
        }

        private void printLocationInfo () {
            System.out.println(player.getInfo());
            System.out.println();
        }

        private void eat () {
            System.out.println("I have eaten and I am not hungry anymore");
            System.out.println();
        }

        private void checkNPC() {
            int i = 1;
            for (Character character : spawnList) {
                if (character.getCurrentRoom() == player.getCurrentRoom()) {
                    charsInRoom.put(i, character);
                    i++;
                }
            }
        }

        private void fight(){
            boolean playerAlive=true;
            boolean monsterAlive=true;
            while(playerAlive && monsterAlive){
                Combat combat= parser.getCombat();

            }

        }

        public static void main (String[]args){
            Game game = new Game();
            game.play();
        }
    }

