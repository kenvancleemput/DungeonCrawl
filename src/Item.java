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
    private int attackBonus;
    private int defenseBonus;

    public Item() {

    }

    public int getAttackBonus() {
        return attackBonus;
    }

    public void setAttackBonus(int attackBonus) {
        this.attackBonus = attackBonus;
    }

    public int getDefenseBonus() {
        return defenseBonus;
    }

    public void setDefenseBonus(int defenseBonus) {
        this.defenseBonus = defenseBonus;
    }

    public Item(String description, double weight, String name, Boolean isMovable, boolean isEquippable, boolean isConsumable, int attackBonus, int defenseBonus) {
        this.description = description;
        this.weight = weight;
        this.name = name;
        this.isMovable = isMovable;
        this.isEquippable= isEquippable;
        this.isConsumable = isConsumable;
        this.attackBonus=attackBonus;
        this.defenseBonus=defenseBonus;
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
