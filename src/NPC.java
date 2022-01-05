import java.util.Random;

/**
 * This class contains all the monster basics for "Dungeon Crawl" and the merchant.
 * This will include the room it is currently in, and all the required methods for moving and combat.
 * Version 0.1: monsters will not have loot, as that is handled by the room class.
 */

public class NPC extends Character{
    private String description;
    private Boolean isFriendly;

    /**
     *
     * @param name name of NPC
     * @param health health of NPC
     * @param armourClass defense of NPC
     * @param toHit attack of NPC
     * @param damageCode damage of NPC
     * @param movable whether NPC can move
     * @param isFriendly whether NPC is friendly
     * @param description despription of NPC
     * @param gold amount of gold carried.
     */
    public NPC(String name, int health, int armourClass, int toHit, int damageCode,boolean movable, boolean isFriendly, String description, int gold) {
        super(name, health,armourClass, toHit,damageCode, movable, gold);
        this.description = description;
        this.isFriendly = isFriendly;

    }

    /**
     *
     * @return description
     */
    public String getDescription() {
        return description;
    }

    /**
     *
     * @return whether NPC is friendly or hostile.
     */
    public boolean getFriendly() {
        return isFriendly;
    }

    /**
     * adds random gold to NPC. Used in combat resolution
     * @return random amount of gold
     */
    public int setRandomGold(){
        Random r=new Random();
        int bling=r.nextInt(10);
        if(bling<1){
            bling=1;
        }
        return bling;
    }

    /**
     * Returns the merchant's wares as monsters don't have items
     * @return a string of possible things to buy.
     */
    public String inventory(){
        String inv="";
        if(inventory.isEmpty()){
            inv+="The merchant is all sold out!";
        } else {
            for (Item item:inventory){
                inv+="\n" + item.getName() + " for a cost of " + item.getBuyCost() + "gold";
            }
        } return inv;
    }
}
