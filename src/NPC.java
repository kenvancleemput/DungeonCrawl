import java.util.Random;

/**
 * This class contains all the monster basics for "Dungeon Crawl".
 * This will include the room it is currently in, and all the required methods for moving and combat.
 * Version 0.1: monsters will not have loot, as that is handled by the room class.
 */

public class NPC extends Character{
    private String description;
    private Boolean isFriendly;

    public NPC(String name, int health, int armourClass, int toHit, int damageCode,boolean movable, boolean isFriendly, String description, int gold) {
        super(name, health,armourClass, toHit,damageCode, movable, gold);
        this.description = description;
        this.isFriendly = isFriendly;

    }

    public String getDescription() {
        return description;
    }

    public boolean getFriendly() {
        return isFriendly;
    }

    public int setRandomGold(){
        Random r=new Random();
        int bling=r.nextInt(10);
        return bling;
    }

    public String inventory(){
        String inv="";
        if(inventory.isEmpty()){
            inv+="The merchant is all sold out!";
        } else {
            inv+="The merchant sells ";
            for (Item item:inventory){
                inv+="\n" + item.getName() + " for a cost of " + item.getBuyCost() + "gold";
            }
        } return inv;
    }
}
