/**
 * class used for validating is an item is a consumable.
 */
public class Consumable extends Item{
    public Consumable(String description, double weight, String name, boolean isEquippable, boolean isConsumable, int attackBonus, int defenseBonus, int damageBonus, int buyCost, int sellCost) {
        super(description, weight, name, isEquippable, isConsumable, attackBonus, defenseBonus, damageBonus, buyCost, sellCost);
    }
}
