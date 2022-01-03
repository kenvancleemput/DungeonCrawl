import java.util.*;

/**
 * This class is the main class of the "Dungeon Crawl" application.
 * "Dungeon Crawl" is a quite complex, text based adventure game.  Users
 * can walk around a dungeon, looking for their ancestral sword.
 * The dungeon contains monsters, loot and gold.
 * <p>
 * To play this game, create an instance of this class and call the "play"
 * method.
 * <p>
 * This main class creates and initialises all the others: it creates all
 * rooms, creates the parser and starts the game.  It also evaluates and
 * executes the commands that the parser returns.
 *
 * @author Michael Kölling and David J. Barnes
 * @version 2011.07.31
 */

public class Game {
    private Parser parser;
    private TradeParser tradeParser;
    private Character player;
    private HashMap<Integer, Room> collection;
    private NPC combatant;
    private NPC merchant;
    private ArrayList<NPC> spawnList;
    private boolean inCombat;
    private boolean winner = false;
    private boolean alive = true;
    private boolean trading = false;

    /**
     * Create the game and initialise its internal map.
     */
    public Game() {
        player = new Player("Albrecht", 20, 10, 5, 4, true,0);
        collection = new HashMap<>();
        spawnList = new ArrayList<>();
        merchant = null;
        createRooms();
        roamNPC();
        parser = new Parser();
        tradeParser = new TradeParser();
        inCombat = false;
        combatant = null;


    }

    /**
     * Create all the rooms and link their exits together.
     */
    private void createRooms() {
        Room entrance, torture_room, dining_room, armoury, hallway, library, descending_path, lair, next_level, dug_path;
        Item knife, spear, axe, mace, sword, leather_vest, chainmail, breastplate, healthpotion, holywater, apple, cake, steak;
        NPC rat, goblin, orc, trader, boss;


        // create the rooms
        entrance = new Room("entrance", "A single torch lights this simple hallway");
        torture_room = new Room("torture room", "This room was obviously used as a torture room. There's a skeleton chained to the wall, and the smell of death hangs heavily in the air.");
        dining_room = new Room("dining room", "There are signs of violence here, but also a shabby table with some food left over.");
        armoury = new Room("armoury", "Once, this must have been an armoury, holding the weapons and armour of whoever lived here before the monsters.");
        hallway = new Room("hallway", "This hallway still has faded banners on the walls.");
        library = new Room("library", "Old shelves line the wall here, in what was once obviously a library.");
        descending_path = new Room("descending path", "The floor slopes down, and the tiles on the floor gradually give over to the rough rock of a cave.");
        lair = new Room("lair", "As you enter the cave, your eyes are drawn to the sculpted stone throne adorning the center of the cave. Seated in it, you can see a skeletal warrior, their brow graced with an ornate golden crown. It stirs as you enter, and two blue lights flare up before the head turns towards you. In its hands, you can see your ancestral sword.");
        next_level = new Room("stairs", "Roughly made stairs greet you here, the darkness from below impenetrable to your human eyes. This must be the place where the various monsters you've encountered so far enter the complex which you've explored.");
        dug_path = new Room("dug path", "This tunnel was burrowed from beneath at some point, and from ahead you can hear the dripping of water.");


        // add rooms to collection ArrayList
        collection.put(0, entrance);
        collection.put(1, torture_room);
        collection.put(2, dining_room);
        collection.put(3, armoury);
        collection.put(4, hallway);
        collection.put(5, library);
        collection.put(6, descending_path);
        collection.put(7, lair);
        collection.put(8, next_level);
        collection.put(9, dug_path);


        // initialise room exits
        entrance.setExit("east", torture_room);
        entrance.setExit("north", dining_room);
        entrance.setExit("west", armoury);
        torture_room.setExit("north", dining_room);
        torture_room.setExit("west", entrance);
        dining_room.setExit("southwest", entrance);
        dining_room.setExit("southeast", torture_room);
        dining_room.setExit("east", dug_path);
        dining_room.setExit("west", armoury);
        armoury.setExit("northeast", dining_room);
        armoury.setExit("southeast", entrance);
        armoury.setExit("west", hallway);
        hallway.setExit("east", armoury);
        hallway.setExit("north", library);
        library.setExit("south", hallway);
        library.setExit("east", descending_path);
        descending_path.setExit("west", library);
        descending_path.setExit("east", lair);
        lair.setExit("west", descending_path);
        lair.setExit("north", next_level);
        lair.setExit("south", dug_path);
        dug_path.setExit("north", lair);
        dug_path.setExit("west", dining_room);
        next_level.setExit("south", lair);

        //Create items
        knife = new Weapon("A sharp knife", 0.45, "knife", true, false, 1, 0, 1,5,1);
        spear = new Weapon("A spear with an ornate head", 1.36, "spear", true, false, 2, 1, 2,10,2);
        axe = new Weapon("A sturdy axe ", 1.81, "axe", true, false, 3, 0, 3,15,3);
        mace = new Weapon("A mace", 1.81, "mace", true, false, 4, 0, 4,20,4);
        sword = new Weapon("your family sword", 1.36, "sword", true, false, 5, 1, 5,100,1);
        leather_vest = new Armour("leather armour", 4.53, "leather", true, false, 0, 2, 0,10,2);
        chainmail = new Armour("A chain shirt", 9.06, "chain", true, false, 0, 4, 0,15,3);
        breastplate = new Armour("A sturdy plate", 18.12, "breastplate", true, false, 0, 6, 0,20,4);
        healthpotion = new Consumable("A magical drink replenishing all your health", 0.2, "healthpotion", false, true, 0, 0, 0,10,2);
        holywater = new Consumable("a flask filled with holy water. Very effective", 0.2, "holywater", false, true, 0, 0, 0,10,2);
        apple = new Food("a green apple", 0.05, "apple", true, false, false, 0, 0, 0, 2,5,1);
        cake = new Food("a delicious cake", 0.1, "cake", true, false, false, 0, 0, 0, 4,7,3);
        steak = new Food("A juicy steak", 0.2, "steak", true, false, false, 0, 0, 0, 6,10,4);


        // add items to collections per room

        for (Integer i : collection.keySet()) {
            collection.get(i).addToCollection(mace);
            collection.get(i).addToCollection(spear);
            collection.get(i).addToCollection(axe);
            collection.get(i).addToCollection(chainmail);
            collection.get(i).addToCollection(leather_vest);
            collection.get(i).addToCollection(knife);
            collection.get(i).addToCollection(breastplate);
            collection.get(i).addToCollection(healthpotion);
            collection.get(i).addToCollection(holywater);
            collection.get(i).addToCollection(cake);
            collection.get(i).addToCollection(apple);
            collection.get(i).addToCollection(steak);
            collection.get(i).getRandomItems();
        }

        // Create Characters
        rat = new NPC("Rat", 5, 10, 1, 4, true, false, "A giant rat",0);
        goblin = new NPC("Goblin", 10, 12, 2, 5, true, false, "A small green humanoid",0);
        orc = new NPC("Orc", 15, 14, 3, 6, true, false, "A towering green giant",0);
        trader = new NPC("Khajit", 200, 20, 10, 10, false, true, "A friendly gnome selling some wares",200);
        merchant = trader;
        boss = new NPC("Skeleton King", 30, 5, 6, 8, false, false, "A skeleton with a shining crown and a familiar sword",50);

        // add NPC's to spawnList
        Collections.addAll(spawnList, rat, goblin, orc, boss);



        //Assign trader to library and goods to trader
        merchant.setCurrentRoom(library);
        merchant.addItem(healthpotion);
        merchant.addItem(healthpotion);
        merchant.addItem(holywater);
        merchant.addItem(mace);
        merchant.addItem(breastplate);


        //add boss and win condition to lair
        boss.setCurrentRoom(lair);
        lair.addItem(sword);

        // start game outside
        player.setCurrentRoom(entrance);
        player.setHands(knife);


    }

    private void roamNPC() {
        for (NPC npc : spawnList) {
            if (!npc.getName().contains("Skeleton King")) {
                Random r = new Random();
                int i = r.nextInt(collection.size() - 1);
                if (i <= 0) {
                    npc.setCurrentRoom(collection.get(1));
                }
                npc.setCurrentRoom(collection.get(i));

            }
        }
    }

    /**
     * Main play routine.  Loops until end of play.
     */
    public void play() {
        printWelcome();

        // Enter the main command loop.  Here we repeatedly read commands and
        // execute them until the game is over.

        boolean finished = false;
        while (!finished && !winner) {
            while (!finished && !inCombat && alive && !trading) {
                Command command = parser.getCommand();
                finished = processCommand(command);
            }
            while(!finished && !inCombat && alive && trading) {
                Action action = tradeParser.getAction();
                trading = processAction(action);
            }
            while (!finished && inCombat && alive) {
                Command command = parser.getCommand();
                finished = processCommand(command);
            }
        }
        if (winner) {
            System.out.println("You have retrieved the ancestral blade. Congratulations!");
        } else {
            if (!alive) {
                System.out.println("You fall on the ground, felled by " + combatant.getName() + ".");
            } else {
                if (finished) {
                    System.out.println("Thank you for playing. Goodbye!");
                }
            }
        }
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
        if (!inCombat) {
            switch (commandWord) {
                case HELP:
                    printHelp();
                    break;
                case GO:
                    goRoom(command);
                    checkNPC();
                    checkMerchant();
                    if (inCombat) {
                        printCombatInfo();
                    }
                    break;
                case LOOK:
                    printLocationInfo();
                    System.out.println(player.inventory());
                    System.out.println(playerGold());
                    System.out.println(player.checkEquipment());
                    break;
                case EAT:
                    eatItem(command);
                    break;
                case DROP:
                    dropItem(command);
                    break;
                case TAKE:
                    takeItem(command);
                    break;
                case EQUIP:
                    equip(command);
                    break;
                case QUIT:
                    wantToQuit = true;
                    break;
                case RUN:
                case INFO:
                case USE:
                case ATTACK:
                    System.out.println(isInCombat());
                    break;
                default:
                    System.out.println("I don't know what you mean");
            }
        } else {
            switch (commandWord) {
                case HELP:
                case GO:
                case LOOK:
                case EAT:
                case DROP:
                case TAKE:
                case QUIT:
                case EQUIP:
                    System.out.println(isInCombat());
                    break;
                case RUN:
                    player.go(player.getCurrentRoom().getRandomExit());
                    wantToQuit = false;
                    inCombat = false;
                    printLocationInfo();
                    break;
                case INFO:
                    printCombatInfo();
                    break;
                case USE:
                    useItem(command);
                    break;
                case ATTACK:
                    fight();
                    checkNPC();
                    break;
                case UNKNOWN:
                    System.out.println("I don't know what you mean");
                    break;
                default:
                    System.out.println("I don't know what you mean");

            }
        }
        if (!player.alive()) {
            alive = false;
            wantToQuit = true;
        }

        if (player.inventory().contains("sword")) {
            winner = true;
            wantToQuit = true;
        }
        return wantToQuit;
    }

    private boolean processAction(Action action){
        boolean wanttoTrade=true;
        if(merchant.inventory.contains(action.getSecondWord()) || player.inventory.contains(action.getSecondWord())){
        Item item= merchant.bagGetItem(action.getSecondWord());
        Item item2= player.bagGetItem(action.getSecondWord());

        TradeAction tradeAction=action.getTradeAction();
        switch (tradeAction){
            case BUY:
                buy(item);
                System.out.println(player.inventory());
                System.out.println(playerGold());
                break;
            case SELL:
                sell(item2);
                System.out.println(player.inventory());
                System.out.println(playerGold());
                break;
            case LEAVE:
                wanttoTrade=false;
                System.out.println("You leave the trader behind");
                Random r=new Random();
                int i=r.nextInt(collection.size());
                merchant.setCurrentRoom(collection.get(i));
                printLocationInfo();
            case UNKNOWN:
                System.out.println("The trader looks at you wearily");
                break;
            default:
                System.out.println("I don't know what you mean");
        }} else {
            TradeAction tradeAction=action.getTradeAction();
            switch (tradeAction){
                case SELL:
                case BUY:
                    System.out.println("buy or sell a valid item");
                    break;
                case LEAVE:
                    wanttoTrade=false;
                    System.out.println("You leave the trader behind");
                    Random r=new Random();
                    int i=r.nextInt(collection.size());
                    merchant.setCurrentRoom(collection.get(i));
                    printLocationInfo();
                    break;
                case UNKNOWN:
                    System.out.println("The trader looks at you wearily");
                    break;
                default:
                    System.out.println("I don't know what you mean.");
            }

        } return wanttoTrade;
    }

    private void sell(Item item2) {
        if(player.inventory().contains(item2.getName())){
            player.removeItem(item2);
            merchant.addItem(item2);
            player.setGold(player.getGold()+ item2.getSellCost());
        } else {
            System.out.println("The merchant doesn't have that for sale.");
        }
    }

    private void buy(Item item) {
        if (player.getGold() >= item.getBuyCost()) {
            if (merchant.inventory().contains(item.getName())) {
                merchant.removeItem(item);
                player.addItem(item);
                player.setGold(player.getGold() - item.getBuyCost());
            }
        } else {
            System.out.println("You don't have enough gold to buy that!");
        }
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
            for (NPC npc : spawnList) {
                if (npc.getMovable()) {
                    String exit = npc.getCurrentRoom().getRandomExit();
                    npc.go(exit);
                }
            }
        }
    }

    private void takeItem(Command command) {
        if (!command.hasSecondWord()) {
            // if there is no second word, we don't know where to go...
            System.out.println("Take what?");
        } else {
            if (player.take(command.getSecondWord())) ;
            System.out.println(player.getInfo());
        }
    }

    private void dropItem(Command command) {
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

    public void useItem(Command command){
        if(!command.hasSecondWord()){
            // if there is no second word, we don't know what to use
            System.out.println("Use what?");
        } else {
            if(player.inventory().contains(command.getSecondWord())){
                if(command.getSecondWord().contains("healthpotion")){
                player.useHealth(command.getSecondWord());
            } else {
                if (command.getSecondWord().contains("holywater") && combatant.getName().contains("Skeleton King")){
                    System.out.println("It's super effective");
                    combatant.setHealth(combatant.getHealth()-20);
                } else {
                    System.out.println("You can't use that, sorry.");
                }
                }
        }}
    }

    public String isInCombat() {
        String message = "";
        if (inCombat = true) {
            message += "You are in combat, exploration is forbidden";
        } else {
            message += "You are not in combat, combat actions are forbidden";
        }
        return message;
    }

    private String playerGold(){
        return "You have " + player.getGold() + " gold in your coinpurse.";
    }

    private void printInfo() {
        System.out.println(player.getCurrentRoom().getLongDescription());
        System.out.println();
    }

    private void printLocationInfo() {
        System.out.println(player.getInfo());
        System.out.println();
    }

    private void eatItem(Command command) {
        if (!command.hasSecondWord()) {
            System.out.println("eat what?");
        } else {
            if (player.eat(command.getSecondWord())) {
                System.out.println("You eat a " + command.getSecondWord() + ".");
                System.out.println("You are now at " + player.getHealth() + " health.");
            } else {
                System.out.println("My bag does not contain " + command.getSecondWord() + ".");
            }
        }
    }

    private void checkNPC() {
        if (combatant == null) {
            for (NPC npc : spawnList) {
                if (npc.getCurrentRoom() == player.getCurrentRoom() && !npc.getFriendly() && npc.alive()) {
                    combatant = npc;
                    inCombat = true;
                }
            }
        } else {
            if (combatant.getHealth() <= 0) {
                for (NPC npc : spawnList) {
                    if (npc.getCurrentRoom() == player.getCurrentRoom() && !npc.getFriendly() && npc.alive()) {
                        combatant = npc;
                        inCombat = true;
                    } else {
                        inCombat= false;
                    }
                }
            }
        }
    }

    private void checkMerchant(){
        if(player.getCurrentRoom()==merchant.getCurrentRoom()){
            System.out.println("You meet " + merchant.getName() + ", a friendly merchant.");
            System.out.println("He has " + merchant.inventory() + "\nfor sale");
            trading=true;
        }
    }

    private boolean fight() {
        int playerAttack = player.attack();
        int monsterAttack = combatant.attack();
        Room heaven = new Room("bla", "bla");
        if (playerAttack >= combatant.getArmourClass()) {
            int damage = player.damage();
            System.out.println("You attack, rolling " + playerAttack + ",hit and do " + damage + " damage!");
            combatant.setHealth(combatant.getHealth() - damage);
        } else {
            System.out.println("You attack, rolling " + playerAttack + " but miss");
        }
        if (combatant.alive()) {
            if (monsterAttack >= player.getArmourClass()) {
                int damage = combatant.damage();
                System.out.println("The " + combatant.getName() + " hits you and deals " + damage + " damage!");
                player.setHealth(player.getHealth() - damage);
            } else {
                System.out.println("The " + combatant.getName() + " misses.");
            }

        } else {
            spawnList.remove(combatant);
            combatant.setCurrentRoom(heaven);
            inCombat = false;
            player.setGold(player.getGold()+combatant.setRandomGold());
            player.increaseMonsters_Defeated();
            player.increaseLevel();
            System.out.println("You are victorious over " + combatant.getName() + ".");
            printLocationInfo();

        }
        return inCombat;
    }


    private void printCombatInfo() {
        System.out.println("There's a " + combatant.getName() + " standing in front of you. Prepare for combat");
    }

    private void equip(Command command) {
        if (!command.hasSecondWord()) {
            System.out.println("Equip what?");
        } else {
            if (player.inventory().contains(command.getSecondWord()) && player.bagGetItem(command.getSecondWord()).isEquippable()) {
                Item equip = player.bagGetItem(command.getSecondWord());
                if (player.bagGetItem(command.getSecondWord()) instanceof Weapon) {
                    player.setHands(equip);
                } else player.setBody(equip);
                System.out.println("You equip the " + equip);
            } else {
                System.out.println("You can't equip that");
            }

        }
    }

    public static void main(String[] args) {
        Game game = new Game();
        game.play();
    }
}

