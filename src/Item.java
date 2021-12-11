/**
 * This holds all the items for the "Dungeon Crawl" application.
 * Items include both equippable and consumable items, as their methods if necessary.
 */

public class Item {
    private String description;
    private String name;
    private double weight;
    private boolean isMovable;
    private boolean isEquippable;
    private boolean isConsumable;

    public Item() {

    }

    public Item(String description, double weight, String name, Boolean isMovable, boolean isEquippable, boolean isConsumable) {
        this.description = description;
        this.weight = weight;
        this.name = name;
        this.isMovable = isMovable;
        this.isEquippable= isEquippable;
        this.isConsumable = isConsumable;
    }

    public String getName() {
        return name;
    }

    public boolean isMovable() {
        return isMovable;
    }

    public String getDescription() {
        return description;
    }

    public double getWeight() {
        return weight;
    }

    public String toString(){
        return this.name + " ("+ this.description + ") with a weight of " + this.weight + "kg.";
    }

}
