import java.util.ArrayList;
import java.util.Iterator;
import java.util.Random;

abstract public class Character {
    private String name;
    private Room currentRoom;
    private int health;
    private int armourClass;
    private int toHit;
    private int damageCode;
    private Boolean movable;
    private ArrayList<Item> inventory;

    public Character(String name, int health, int armourClass, int toHit, int damageCode, boolean movable) {
        this.name = name;
        this.health = health;
        this.armourClass = armourClass;
        this.toHit = toHit;
        this.damageCode = damageCode;
        this.movable = movable;
        inventory = new ArrayList<>();
       }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Room getCurrentRoom() {
        return currentRoom;
    }

    public void setCurrentRoom(Room currentRoom) {
        this.currentRoom = currentRoom;
    }

    public int getHealth() {
        return health;
    }

    public void setHealth(int health) {
        this.health = health;
    }

    public int getArmourClass() {
        return armourClass;
    }

    public void setArmourClass(int armourClass) {
        this.armourClass = armourClass;
    }

    public int getToHit() {
        return toHit;
    }

    public void setToHit(int toHit) {
        this.toHit = toHit;
    }

    public int getDamageCode() {
        return damageCode;
    }

    public void setDamageCode(int damageCode) {
        this.damageCode = damageCode;
    }

    public String getInfo() {
        String info = "My name is " + name;
        info += " and I am " + currentRoom.getLongDescription();
        if (!inventory.isEmpty()) {
            info += "\n" + inventory();
        }
        return info;
    }

    public Boolean getMovable() {
        return movable;
    }

    public boolean go(String direction) {
        Room nextRoom = getCurrentRoom().getExit(direction);
        if (nextRoom == null) {
            return false;
        }
        currentRoom = nextRoom;
        return true;
    }

    public String inventory() {
        String inv = "";
        if (inventory.isEmpty()) {
            inv = "My bag is empty";
            return inv;
        } else {
            inv = "My bag contains";
            for (Item item : inventory) {
                inv += "\n" + item.toString();
            }
        }
        return inv;

    }

    public Boolean take(String name) {
        if (currentRoom.hasItem(name)) {
            Item item = currentRoom.getItem(name);
            if (item.isMovable())
                inventory.add(item);
            currentRoom.removeItem(item);
            return true;
        } return false;
    }

    public Boolean drop(String itemName) {
        Iterator<Item> it = inventory.iterator();
        while (it.hasNext()) {
            {
                Item item = it.next();
                it.remove();
                currentRoom.addItem(item);
                return true;
            }
        } return false;
    }

    public void addItem(Item item) {
        inventory.add(item);
    }

    public Item bagGetItem(String itemName) {
        for (Item item : inventory) {
            if (item.getName().equals(itemName)) return item;
        }
        return null;
    }

    abstract public String getDescription();

    abstract public boolean getFriendly();

    public int attack(){
        Random attackRoll=new Random();
        int i= attackRoll.nextInt(19)+1;
        int toHit=i+getToHit();
        return toHit;
    }

    public int damage(){
        Random damageRoll=new Random();
        int damage= damageRoll.nextInt(damageCode-1)+1;
        return damage;
    }

    public boolean alive(){
        if(health<=0){
            return false;
        } return true;
    }

    public void drink() {
    }

}
