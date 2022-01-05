import java.util.ArrayList;
import java.util.Iterator;
import java.util.Random;

/**
 * Superclass that contains all the character stats that are shared
 * between player character and non player character (NPC).
 */

abstract public class Character {
    private String name;
    protected Room currentRoom;
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

    /**
     * Constructor
     * @param name The name of the character
     * @param health The current health of the character.
     *               Equal to max_health, which is used in leveling.
     * @param armourClass The current defense rating of the character
     *                    Equal to base_defense, which is used in leveling.
     * @param toHit The current attack bonus of the character
     *              Equal to base_attack, which is used in leveling.
     * @param damageCode The current random number the character uses when attacking.
     *                   Equal to base_damage, which is used in leveling.
     * @param movable Whether the character can move or not due to random methods.
     * @param gold The amount of gold the character has with them.
     */
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

    /**
     *
     * @return the name given to the character.
     */
    public String getName() {
        return name;
    }

    /**
     *
     * @return the current room the character is in
     */
    public Room getCurrentRoom() {
        return currentRoom;
    }

    /**
     *
     * @param currentRoom set character to that room.
     */
    public void setCurrentRoom(Room currentRoom) {
        this.currentRoom = currentRoom;
    }

    /**
     *
     * @return the current health the character has.
     */
    public int getHealth() {
        return health;
    }

    /**
     *
     * @param health sets the current health the character has.
     */
    public void setHealth(int health) {
        this.health = health;
    }

    /**
     *
     * @return the character's defenses.
     */
    public int getArmourClass() {
        return armourClass;
    }

    /**
     *
     * @param armourClass sets the character's defenses.
     */
    public void setArmourClass(int armourClass) {
        this.armourClass = armourClass;
    }

    /**
     *
     * @return the character's attack bonus.
     */
    public int getToHit() {
        return toHit;
    }

    /**
     *
     * @param toHit adjusts the character's attack bonus.
     */
    public void setToHit(int toHit) {
        this.toHit = toHit;
    }

    /**
     *
     * @return the bound for the random damage a character does.
     */
    public int getDamageCode() {
        return damageCode;
    }

    /**
     *
     * @return the base damage for a character at character creation
     */
    public int getBase_damage() {
        return base_damage;
    }

    /**
     *
     * @return the max health a character has.
     */
    public int getMax_health() {
        return max_health;
    }

    /**
     *
     * @return the base attack a character has
     */
    public int getBase_attack() {
        return base_attack;
    }

    /**
     *
     * @return the base defence a character has
     */
    public int getBase_defence() {
        return base_defence;
    }

    /**
     *
     * @param damageCode sets a character's damage to a new number.
     */
    public void setDamageCode(int damageCode) {
        this.damageCode = damageCode;
    }

    /**
     *
     * @return the character's current location description
     */
    public String getInfo() {
        String info = currentRoom.getLongDescription();
        return info;
    }

    /**
     *
     * @param gold sets the character's gold.
     */
    public void setGold(int gold){ this.gold=gold;}

    /**
     *
     * @return the character's current gold.
     */
    public int getGold(){ return gold;}

    /**
     *
     * @param gold adds this amount of gold to the current gold
     *             a character has.
     */
    public void addGold(int gold){ this.gold += gold;}

    /**
     *
     * @return if a character can move through the dungeon
     */
    public Boolean getMovable() {
        return movable;
    }

    /**
     * moves a character from one room to the next
     * @param direction the exit the character wants to take
     * @return the current room the character is in.
     */
    public boolean go(String direction) {
        Room nextRoom = getCurrentRoom().getExit(direction);
        if (nextRoom == null) {
            return false;
        }
        currentRoom = nextRoom;
        return true;
    }

    /**
     *
     * @return a string with everything the character
     *         has in their bag
     *
     */
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

    /**
     * super method for players class
     */
    public Boolean take(String name) {
        return false;    }

    /**
     * super method for player class
     * @param itemName
     * @return
     */
    public Boolean drop(String itemName) {
     return false;
    }

    /**
     * Adds item to bag.
     * @param item the item that needs to be added to bag.
     */
    public void addItem(Item item) {
        inventory.add(item);
    }

    /**
     * removes an item from player's bag. Used when selling and buying.
     * @param item the item to be removed from bag
     */
    public void removeItem(Item item) {inventory.remove(item);}

    public Item bagGetItem(String itemName) {
        for (Item item : inventory) {
            if (item.getName().equals(itemName)) return item;
        }
        return null;
    }

    abstract public String getDescription();

    abstract public boolean getFriendly();

    /**
     * Attack method for characters. Make an attack with a
     * 20-sided die, adds current attack modifier.
     * @return the number rolled
     */
    public int attack() {
        Random attackRoll = new Random();
        int i = attackRoll.nextInt(19) + 1;
        int toHit = i + getToHit();
        return toHit;
    }

    /**
     * Rolls the character's current damage rating
     * @return the number rolled.
     */
    public int damage() {
        Random damageRoll = new Random();
        int damage = damageRoll.nextInt(damageCode) + 1;
        if (damage > damageCode) {
            damage = damage - 1;
        }
        return damage;
    }

    /**
     * Check to see the character is alive.
     * @return true or false.
     */
    public boolean alive() {
        if (health <= 0) {
            return false;
        }
        return true;
    }

    /**
     * checks is inventory contains a healthpotion and uses it
     * @param aString healthpotion
     */
    public void useHealth(String aString) {
        if (inventory.contains(aString)){
            health=max_health;
            inventory.remove(bagGetItem("healthpotion"));
          } else {
        }
    }

    /**
     * Checks to see if inventory has holy water and uses it.
     * @param aString
     */
    public void useHoly(String aString) {
        if(inventory.contains(aString)){
            inventory.remove(bagGetItem("holywater"));
        }
    }

    /**
     * super method for player class to be able to equip item to hands
     * @param hands
     * @return
     */
    public void setHands(Item hands) {}

    /**
     * super method for player class
     * @param body
     * @return
     */
    public void setBody(Item body) {}


    public boolean eat(String name) {
        Iterator<Item> it = inventory.iterator();
        while (it.hasNext()) {
            Item food = it.next();
            if (food.isEdible()&& food.getName().contains(name)) {
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

    /**
     * super method for player class
     */
    public void increaseMonsters_Defeated() {
    }

    /**
     * super method for player class
     * @return
     */
    public boolean increaseLevel() {
        return false;}

    /**
     * super method for player class
     * @return
     */
    public int getLevel(){return 0;}

    /**
     * super method for player class
     * @return
     */
    public String checkEquipment() { return null;}

    /**
     * super method for player class
     * @return
     */
    public String showStats(){ return null;}

    public double getCurrentWeight(){return 0.0;}

    public double getMaxWeightInBag(){return 0.0;}

}
