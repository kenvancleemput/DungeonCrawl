import java.util.ArrayList;
import java.util.Iterator;

/**
 * This is the player in the "Dungeon Crawl" application.
 * This holds all player data, like name, inventory, health, armour_class and to_hit.
 * Methods include all those necessary to manipulate these fields, as methods for item manipulation on the player level.
 */
public class Player {
    private String name;
    private double maxWeightInBag;
    private ArrayList<Item> bag;
    private Room currentRoom;
    private int health;
    private int armour_class;
    private int to_hit;
    private int damage_code;

    public Player(String name) {
        this.name = name;
        maxWeightInBag = 25;
        bag = new ArrayList<>();
        health=10;
        armour_class=10;
        to_hit=3;
        damage_code=1;
    }

    public String getName() {
        return name;
    }

    public void addItem(Item item) {
        bag.add(item);
    }

    public void setMaxWeightInBag(double maxWeightInBag) {
        this.maxWeightInBag = maxWeightInBag;
    }

    public double getMaxWeightInBag() {
        return maxWeightInBag;
    }

    public Room getCurrentRoom() {
        return currentRoom;
    }

    public void setCurrentRoom(Room currentRoom) {
        this.currentRoom = currentRoom;
    }

    public String getInfo() {
        String info = "My name is " + name;
        info += " and I am " + currentRoom.getLongDescription();
        if (!bag.isEmpty()) {
            info += "\n" + inventory();
        }
        return info;
    }

    public boolean go(String direction) {
        Room nextRoom = getCurrentRoom().getExit(direction);
        if (nextRoom == null) {
            return false;
        }
        currentRoom = nextRoom;
        return true;
    }

    public Boolean take(String name) {
        if (currentRoom.hasItem(name)) {
            Item item = currentRoom.getItem(name);
            if (item.isMovable())
                bag.add(item);
            currentRoom.removeItem(item);
            return true;
        } return false;
    }

    public String inventory() {
        String inv = "";
        if (bag.isEmpty()) {
            inv = "My bag is empty";
            return inv;
        } else {
            inv = "My bag contains";
            for (Item item : bag) {
                inv += "\n" + item.toString();
            }
        }
        return inv;

    }

    public Item bagGetItem(String itemName) {
        for (Item item : bag) {
            if (item.getName().equals(itemName)) return item;
        }
        return null;
    }


    public Boolean drop(String itemName) {
        Iterator<Item> it = bag.iterator();
        while (it.hasNext()) {
                {
                    Item item = it.next();
                    it.remove();
                    currentRoom.addItem(item);
                    return true;
                }
            } return false;
        }
    }


