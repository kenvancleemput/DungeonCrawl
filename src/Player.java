import java.util.ArrayList;
import java.util.Iterator;

/**
 * This is the player in the "Dungeon Crawl" application.
 * This holds all player data, like name, inventory, health, armour_class and to_hit.
 * Methods include all those necessary to manipulate these fields, as methods for item manipulation on the player level.
 */
public class Player extends Character {
    private double maxWeightInBag;

    public Player(String name, int health, int armourClass, int toHit, int damageCode, boolean movable) {
        super(name, health, armourClass, toHit, damageCode, movable);
        maxWeightInBag = 25;
    }

    public void setMaxWeightInBag(double maxWeightInBag) {
        this.maxWeightInBag = maxWeightInBag;
    }

    public double getMaxWeightInBag() {
        return maxWeightInBag;
    }

   }


