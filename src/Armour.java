/**
 * class to determine if item is armour.
 */
public class Armour extends Item{
    public Armour(String description, double weight, String name, boolean isEquippable, boolean isConsumable, int attackBonus, int defenseBonus, int damageBonus, int buyCost, int sellCost) {
        super(description, weight, name, isEquippable, isConsumable, attackBonus, defenseBonus, damageBonus, buyCost, sellCost);
    }
}
