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
    private String description;
    private HashMap<String, Room> exits;
    private ArrayList<Item> items;
    private ArrayList<Item> collection;


    /**
     * Create a room described "description". Initially, it has
     * no exits. "description" is something like "a kitchen" or
     * "an open court yard".
     * @param description The room's description.
     */
    public Room(String description)
    {
        this.description = description;
        exits= new HashMap<>();
        items= new ArrayList<>();
        collection= new ArrayList<>();

    }

    /**
     * Define the exits of this room.  Every direction either leads
     * to another room or is null (no exit there).
     * @param north The north exit.
     * @param east The east east.
     * @param south The south exit.
     * @param west The west exit.
     */
    public void setExits(Room north, Room east, Room south, Room west)
    {
        if(north != null)
            exits.put("north", north);
        if(east != null)
            exits.put("east", east);
        if(south != null)
            exits.put("south", south);
        if(west != null)
            exits.put("west", west);
    }

    /**
     * @return The description of the room.
     */
    public String getDescription()
    {
        return description;
    }

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

    public void setExit(String direction, Room room){
        exits.put(direction, room);
    }

    public String getLongDescription() {
        String info = description + ".\n" + getExitString();
        if(!items.isEmpty()) {
            info += "\ncontains items";
        }
        for (Item item : items) {
            info += "\n" + item.toString();
        }             return info;
    }

    public void addItem(Item item){
        this.collection.add(item);    }

    public void getRandomItems(){
        Random r = new Random();
        int i=r.nextInt(4);
        for(int j=0;j<i;j++){
            Item random =collection.get(r.nextInt(collection.size()));
            items.add(random);
        }

    }

    public boolean hasItem(String name){
        for (Item item : items){
            if (item.getName().equals(name)) return true;
        } return false;
    }

    public Item getItem(String name){
        for (Item item : items){
            if(item.getName().equals(name)) return item;
        } return null;
    }

    public void removeItem(Item item){
        items.remove(item);
    }
}







