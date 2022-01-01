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


    public Item(String description, double weight, String name, boolean isEquippable, boolean isConsumable, int attackBonus, int defenseBonus, int damageBonus) {
        this.description = description;
        this.weight = weight;
        this.name = name;
        this.isEquippable= isEquippable;
        this.isConsumable = isConsumable;
        this.attackBonus=attackBonus;
        this.defenseBonus=defenseBonus;
        this.damageBonus=damageBonus;

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

    public String getName() {
        return name;
    }

    public boolean isEdible() {
        return false;}

        public String toString(){
        return this.name + " ("+ this.description + ") with a weight of " + this.weight + "kg.";
    }

    public int getDamageBonus() {
        return damageBonus;
    }

    public void setDamageBonus(int damageBonus) {
        this.damageBonus = damageBonus;
    }

    public int getHealingValue() {
        return 0;
    }
}
