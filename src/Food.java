public class Food extends Item{
    private boolean isEdible;
    private int healingValue;

    public Food(String description, double weight, String name, Boolean isEdible, boolean isEquippable, boolean isConsumable, int attackBonus, int defenseBonus, int damageBonus, int healingValue) {
        super(description, weight, name, isEquippable, isConsumable, attackBonus, defenseBonus, damageBonus);
        this.isEdible=isEdible;
        this.healingValue=healingValue;
    }

    @Override
    public boolean isEdible() {
        return isEdible;
    }

    @Override
    public int getHealingValue() {
        return healingValue;
    }
}