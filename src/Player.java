import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;

/**
 * This is the player in the "Dungeon Crawl" application.
 * This holds all player data, like name, inventory, health, armour_class and to_hit.
 * Methods include all those necessary to manipulate these fields, as methods for item manipulation on the player level.
 */
public class Player extends Character {
    private double maxWeightInBag;
    private Item hands;
    private Item body;
    private int level;
    private int monsters_defeated;
    private HashMap<Integer, Integer> level_chart;

    public Player(String name, int health, int armourClass, int toHit, int damageCode, boolean movable) {
        super(name, health, armourClass, toHit, damageCode, movable);
        maxWeightInBag = 25;
        hands=null;
        body=null;
    }

    @Override
    public String getDescription() {
        return null;
    }

    @Override
    public boolean getFriendly() {
        return false;
    }

    public void setMaxWeightInBag(double maxWeightInBag) {
        this.maxWeightInBag = maxWeightInBag;
    }

    public double getMaxWeightInBag() {
        return maxWeightInBag;
    }

    public Item getHands() {
        return hands;
    }

    public void setHands(Item hands) {
        this.hands = hands;
        setToHit(getToHit()+hands.getAttackBonus());
    }

    public Item getBody() {
        return body;
    }

    public void setBody(Item body) {
        this.body = body;
        setArmourClass(getArmourClass()+ body.getDefenseBonus());
    }
}


