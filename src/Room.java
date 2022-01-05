import java.util.ArrayList;
import java.util.HashMap;
import java.util.Random;

/**
 * Class Room - a room in an adventure game.
 *
 * This class is part of the "Dungeon crawl" application.
 * "Dungeon crawl" is a very simple, text based adventure game.
 *
 * A "Room" represents one location in the scenery of the game.  It is
 * connected to other rooms via exits.  The exits are labelled north,
 * east, south, west.  For each direction, the room stores a reference
 * to the neighboring room, or null if there is no exit in that direction.
 *
 * @author  Michael Kölling and David J. Barnes
 * @version 2011.07.31
 */

public class Room
{
    private String name;
    private String description;
    private HashMap<String, Room> exits;
    private ArrayList<Item> items;
    private ArrayList<Item> collection;


    /**
     * Create a room described "description". Initially, it has
     * no exits. "description" is the text players will see on the console.
     * @param description The room's description.
     */
    public Room(String name, String description)
    {
        this.name = name;
        this.description = description;
        exits= new HashMap<>();
        items= new ArrayList<>();
        collection= new ArrayList<>();

    }

    /**
     *
     * @param direction
     * @return the exit in the direction specified.
     */
    public Room getExit(String direction) {
        return exits.get(direction);
    }

    /**
     * @return string met alle aanwezige uitgangen
    bvb. "Exits: north west".
     */
    public String getExitString(){
        String exit = "Exits: ";
        for (String key : exits.keySet()) {
            exit += key + " ";
        }
        return exit ;
    }

    /**
     *
     * @param direction one of the possible exits.
     * @param room the room that exit goes to.
     */
    public void setExit(String direction, Room room){
        exits.put(direction, room);
    }

    /**
     *
     * @return a description of the room with all possible exits
     * and the items in that room.
     */
    public String getLongDescription() {
        String info = description + ".\n" + getExitString();
        if(!items.isEmpty()) {
            info += "\ncontains items";
        }
        for (Item item : items) {
            info += "\n" + item.toString();
        }             return info;
    }

    /**
     * Adds items to the Arraylist of items in a certain room.
     * @param item an item that a player can find.
     */
    public void addItem(Item item){
        this.items.add(item);    }

    /**
     * Adds items to Arraylist that contains all items over the whole dungeon
     * Used for random distributing of items over the dungeon.
     * @param item
     */
    public void addToCollection(Item item){
        this.collection.add(item);
    }

    /**
     * Sets random item per room.
     */
    public void getRandomItems(){
        Random r = new Random();
        int i=r.nextInt(5);
        if(i>2){
            Item random =collection.get(r.nextInt(collection.size()));
            items.add(random);
        }

    }

    /**
     *
     * @param name the name of an item
     * @return whether the room contains that item.
     */
    public boolean hasItem(String name){
        for (Item item : items){
            if (item.getName().equals(name)) return true;
        } return false;
    }

    /**
     *
     * @param name the name of the item
     * @return an object of that item.
     */
    public Item getItem(String name){
        for (Item item : items){
            if(item.getName().equals(name)) return item;
        } return null;
    }

    /**
     * Removes an item from the arraylist of items in the room
     * after picking it up.
     * @param item the item being taken.
     */
    public void removeItem(Item item){
        items.remove(item);
    }

    /**
     * Get a random exit from the current room. Used for moving monsters
     * or when the player decides to run from combat.
     * @return a possible exit from the room
     */
    public String getRandomExit(){
        int index= 0;
        HashMap<Integer, String> getExit = new HashMap<>();
        for(String possibleExits: exits.keySet()){
            getExit.put(index, possibleExits);
            index++;
        }
        Random r= new Random();
        int i= r.nextInt(getExit.size());
        return getExit.get(i);

    }
}







