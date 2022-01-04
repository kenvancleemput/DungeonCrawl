import java.util.ArrayList;
import java.util.Iterator;
import java.util.Random;

abstract public class Character {
    private String name;
    private Room currentRoom;
    private int health;
    private int armourClass;
    private int max_health;
    private int base_attack;
    private int base_defence;
    private int toHit;
    private int damageCode;
    private int base_damage;
    private Boolean movable;
    protected ArrayList<Item> inventory;
    private int gold;

    public Character(String name, int health, int armourClass, int toHit, int damageCode, boolean movable, int gold) {
        this.name = name;
        this.health = health;
        max_health = health;
        this.armourClass = armourClass;
        base_defence = armourClass;
        base_attack = toHit;
        this.toHit = toHit;
        this.damageCode = damageCode;
        base_damage = damageCode;
        this.movable = movable;
        inventory = new ArrayList<>();
        this.gold=gold;
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

    public int getBase_damage() {
        return base_damage;
    }

    public int getMax_health() {
        return max_health;
    }

    public void setMax_health(int max_health) {
        this.max_health = max_health;
    }

    public int getBase_attack() {
        return base_attack;
    }

    public void setBase_attack(int base_attack) {
        this.base_attack = base_attack;
    }

    public int getBase_defence() {
        return base_defence;
    }

    public void setBase_defence(int base_defence) {
        this.base_defence = base_defence;
    }

    public void setBase_damage(int base_damage) {
        this.base_damage = base_damage;
    }

    public void setDamageCode(int damageCode) {
        this.damageCode = damageCode;
    }

    public String getInfo() {
        String info = currentRoom.getLongDescription();
        return info;
    }

    public void setGold(int gold){ this.gold=gold;}

    public int getGold(){ return gold;}

    public void addGold(int gold){ this.gold += gold;}

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
            inventory.add(item);
            currentRoom.removeItem(item);
            return true;
        }
        return false;
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
        }
        return false;
    }

    public void addItem(Item item) {
        inventory.add(item);
    }

    public void removeItem(Item item) {inventory.remove(item);}

    public Item bagGetItem(String itemName) {
        for (Item item : inventory) {
            if (item.getName().equals(itemName)) return item;
        }
        return null;
    }

    abstract public String getDescription();

    abstract public boolean getFriendly();

    public int attack() {
        Random attackRoll = new Random();
        int i = attackRoll.nextInt(19) + 1;
        int toHit = i + getToHit();
        return toHit;
    }

    public int damage() {
        Random damageRoll = new Random();
        int damage = damageRoll.nextInt(damageCode) + 1;
        if (damage > damageCode) {
            damage = damage - 1;
        }
        return damage;
    }

    public boolean alive() {
        if (health <= 0) {
            return false;
        }
        return true;
    }

    public void useHealth(String aString) {
        if (inventory.contains(aString)){
            health=max_health;
            inventory.remove(bagGetItem("healthpotion"));
          }
    }



    public void useHoly(String aString) {
        if(inventory.contains(aString)){
            inventory.remove(bagGetItem("holywater"));
        }
    }

    public Item setHands(Item hands) { return null;}

    public Item setBody(Item body) {return null;}

    public boolean eat(String name) {
        Iterator<Item> it = inventory.iterator();
        while (it.hasNext()) {
            Item food = it.next();
            if (food.isEdible()) {
                inventory.remove(food);
                if (health + food.getHealingValue() <= max_health) {
                    health += food.getHealingValue();
                } else {
                    health = max_health;
                }
                return true;
            }
        }
        return false;
    }

    public void increaseMonsters_Defeated() {
    }

    ;

    public boolean increaseLevel() {
        boolean b = false;
        return b;}

    public int getLevel(){return 0;}

    public String checkEquipment() { return null;}

    public String showStats(){ return null;}

}
