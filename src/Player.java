import java.util.HashMap;
import java.util.Iterator;


/**
 * This is the player in the "Dungeon Crawl" application.
 * This holds all player data, like name, inventory, health, armour_class and to_hit.
 * Methods include all those necessary to manipulate these fields, as methods for item manipulation on the player level.
 */
public class Player extends Character {
    private double maxWeightInBag;
    private double currentWeight;
    private Item hands;
    private Item body;
    private int level;
    private int monsters_defeated;
    private HashMap<Integer, Integer> level_chart;


    /**
     * Constructor for player
     *
     * @param name        player name
     * @param health      current health
     * @param armourClass defense
     * @param toHit       attack bonus
     * @param damageCode  damage roll
     * @param movable     can player move
     * @param gold        gold on player
     */
    public Player(String name, int health, int armourClass, int toHit, int damageCode, boolean movable, int gold) {
        super(name, health, armourClass, toHit, damageCode, movable, gold);
        maxWeightInBag = 30;
        hands = null;
        body = null;
        monsters_defeated = 0;
        level_chart = new HashMap<>();
        level = 1;
        setLevel_chart();
        currentWeight = 0.00;
    }

    @Override
    public String getDescription() {
        return null;
    }

    @Override
    public boolean getFriendly() {
        return false;
    }

    /**
     * method to equip a weapon to hands slot
     * @param hands item to be equipped.
     *
     */
    public void setHands(Item hands) {
        unequipHands();
        this.hands = hands;
        removeItem(hands);
        setToHit(getBase_attack() + hands.getAttackBonus() + level);
        setDamageCode(getBase_damage() + hands.getDamageBonus() + level);
        setArmourClass(getArmourClass() + hands.getDefenseBonus() + level);
        }

    /**
     * method to equip armour to body slot
     * @param body item to be equipped
     */
    public void setBody(Item body) {
        unequipBody();
        this.body = body;
        removeItem(body);
        setArmourClass(getBase_defence() + body.getDefenseBonus());
            }

    /**
     * method to check player level
      * @return level
     */
    public int getLevel() {
        return level;
    }

    /**
     * Method that sets the intervals of monsters killed for
     * player advancement.
     */
    public void setLevel_chart() {
        level_chart.put(3, 2);
        level_chart.put(6, 3);
        level_chart.put(10, 4);
        level_chart.put(15, 5);
    }

    /**
     * Increases monsters defeated every time you do so.
     */
    public void increaseMonsters_Defeated() {
        monsters_defeated++;
    }

    /**
     * Method that checks if you should level and returns
     * if you leveled or not. Also calculates your new stats.
     * @return
     */
    public boolean increaseLevel() {
        boolean levelup = false;
        for (int i : level_chart.keySet()) {
            if (monsters_defeated == i) {
                level = level_chart.get(i);
                int attack = getBase_attack();
                int damage = getBase_damage();
                int defense = getBase_defence();
                if (hands == null) {
                    attack += level;
                    damage += level;
                } else {
                    attack += level + hands.getAttackBonus();
                    damage += level + hands.getDamageBonus();
                    defense += level + hands.getDefenseBonus();
                }
                if (body == null) {
                    defense += level;
                } else {
                    defense += level + body.getDefenseBonus();
                }
                setToHit(attack);
                setDamageCode(damage);
                setArmourClass(defense);
                setHealth(level * getMax_health());
                levelup = true;
            }
        }
        return levelup;
    }

    /**
     * method to unequip weapons when you equip a new one.
     */
    private void unequipHands() {
        if (!(hands == null)) {
            addItem(hands);
            hands = null;
        }
    }

    /**
     * method to unequip armour when you equip a new one.
     */
    private void unequipBody() {
        if (!(body == null)) {
            addItem(body);
            body = null;
        }
    }

    /**
     * Method that shows what you have equipped
     * @return string with equipment worn
     */
    public String checkEquipment() {
        String eq = "";
        if (hands != null) {
            eq += "I am wielding a " + hands.getName() + ".";
        } else {
            eq += "I am wielding nothing.";
        }
        if (body != null) {
            eq += "\nI'm wearing " + body.getName() + " for protection.";
        } else {
            eq += "\nI'm not wearing protection.";
        }
        return eq;
    }

    /**
     * method that shows player stats
     * @return String with stats
     */
    public String showStats() {
        String stats = "I have ";
        stats += getHealth() + " health and " + getToHit() + " attack bonus, " + getArmourClass() + " armour class";
        stats += "\nam level " + getLevel() + " and I do from 1 to " + getDamageCode() + " damage";
        return stats;
    }

    /**
     * Method for picking up items from rooms
     * @param name of the item
     * @return true or false
     */
    public Boolean take(String name) {
        Item item = currentRoom.getItem(name);
        if
        (currentRoom.hasItem(name) && (currentWeight + item.getWeight() <= maxWeightInBag)) {
            addItem(item);
            currentRoom.removeItem(item);
            currentWeight += item.getWeight();
            return true;
        } return false;

    }

    public double getCurrentWeight() {
        return currentWeight;
    }

    /**
     * method for dropping items from bag to floor.
     * @param itemName item to drop
     * @return true or false
     */
    public Boolean drop(String itemName) {
        boolean drop=false;
        Iterator<Item> it = inventory.iterator();
        while (it.hasNext()) {
            {
                Item item = it.next();
                if (item.getName().contains(itemName)) {
                    it.remove();
                    currentRoom.addItem(item);
                    currentWeight -= item.getWeight();
                    drop = true;
                }
            }
        }return drop;
    }

    public double getMaxWeightInBag() {
        return maxWeightInBag;
    }
}

