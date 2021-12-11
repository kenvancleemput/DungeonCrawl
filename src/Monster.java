/**
 * This class contains all the monster basics for "Dungeon Crawl".
 * This will include the room it is currently in, and all the required methods for moving and combat.
 * Version 0.1: monsters will not have loot, as that is handled by the room class.
 */

public class Monster {
    private String name;
    private String description;
    private int health;
    private int armour_class;
    private int attack_bonus;

    public Monster(String name, String description, int health, int armour_class, int attack_bonus) {
        this.name = name;
        this.description = description;
        this.health = health;
        this.armour_class = armour_class;
        this.attack_bonus = attack_bonus;
    }

    public void setHealth(int health) {
        this.health = health;
    }

    public String getName() {
        return name;
    }

    public String getDescription() {
        return description;
    }

    public int getHealth() {
        return health;
    }
}
