/**
 * This holds all the items for the "Dungeon Crawl" application.
 * Items include both equippable and consumable items, as their methods if necessary.
 */

public class Item {
    private String description;
    private String name;
    private double weight;
    private boolean isEquippable;
    private boolean isConsumable;
    private int attackBonus;
    private int defenseBonus;
    private int damageBonus;
    private int buyCost;
    private int sellCost;

    /**
     * constructor
     *
     * @param description  a description of the item
     * @param weight       what the item weighs
     * @param name         used in manipulation of items
     * @param isEquippable whether the item can be equipped
     * @param isConsumable whether the item can be consumed
     * @param attackBonus  the amount the item adds to attack when equipped
     * @param defenseBonus the amount the item adds to defense when equipped
     * @param damageBonus  the amount the item adds to damage when equipped
     * @param buyCost      what the item costs to buy
     * @param sellCost     what you earn by selling it
     */
    public Item(String description, double weight, String name, boolean isEquippable, boolean isConsumable, int attackBonus, int defenseBonus, int damageBonus, int buyCost, int sellCost) {
        this.description = description;
        this.weight = weight;
        this.name = name;
        this.isEquippable = isEquippable;
        this.isConsumable = isConsumable;
        this.attackBonus = attackBonus;
        this.defenseBonus = defenseBonus;
        this.damageBonus = damageBonus;
        this.buyCost = buyCost;
        this.sellCost = sellCost;

    }

    /**
     * @return attack bonus of item
     */
    public int getAttackBonus() {
        return attackBonus;
    }

    /**
     * @return defense bonus of item
     */
    public int getDefenseBonus() {
        return defenseBonus;
    }

    /**
     * @return the name used in manipulation
     */
    public String getName() {
        return name;
    }

    public double getWeight() {
        return weight;
    }

    /**
     * @return whether you can eat the item
     */
    public boolean isEdible() {
        return false;
    }

    /**
     *
     * @return the description of the item
     */
    public String toString(){
        return this.name + " ("+ this.description + ") with a weight of " + this.weight + "kg.";
    }

    /**
     *
     * @return damage bonus of the item
     */
    public int getDamageBonus() {
        return damageBonus;
    }

    /**
     * super method for food.
     * @return
     */
    public int getHealingValue() {
        return 0;
    }

    /**
     *
     * @return whether item can be equipped
     */
    public boolean isEquippable() {
        return isEquippable;
    }

    /**
     *
     * @return whether item can be used
     */
    public boolean isConsumable() {
        return isConsumable;
    }

    /**
     *
     * @return cost of item
     */
    public int getBuyCost() {
        return buyCost;
    }

    /**
     *
     * @return value of item when sold
     */
    public int getSellCost() {
        return sellCost;
    }
}
