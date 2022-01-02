import java.util.ArrayList;
import java.util.HashMap;


/**
 * This is the player in the "Dungeon Crawl" application.
 * This holds all player data, like name, inventory, health, armour_class and to_hit.
 * Methods include all those necessary to manipulate these fields, as methods for item manipulation on the player level.
 */
public class Player extends Character {
    private double maxWeightInBag;
    private Item hands;
    private Item body;
    private int level;
    private int monsters_defeated;
    private HashMap<Integer, Integer> level_chart;

    public Player(String name, int health, int armourClass, int toHit, int damageCode, boolean movable) {
        super(name, health, armourClass, toHit, damageCode, movable);
        maxWeightInBag = 25;
        hands=null;
        body=null;
        monsters_defeated=0;
        level_chart=new HashMap<>();
        setLevel_chart();
    }

    @Override
    public String getDescription() {
        return null;
    }

    @Override
    public boolean getFriendly() {
        return false;
    }

    public void setMaxWeightInBag(double maxWeightInBag) {
        this.maxWeightInBag = maxWeightInBag;
    }

    public double getMaxWeightInBag() {
        return maxWeightInBag;
    }

    public Item getHands() {
        return hands;
    }

    public Item setHands(Item hands) {
        unequipHands();
        this.hands = hands;
        inventory.remove(hands);
        setToHit(getBase_attack()+hands.getAttackBonus()+level);
        setDamageCode(getBase_damage()+hands.getDamageBonus()+level);
        setArmourClass(getArmourClass()+hands.getDefenseBonus()+level);
        return hands;
    }

    public Item getBody() {
        return body;
    }


    public Item setBody(Item body) {
        unequipBody();
        this.body = body;
        inventory.remove(body);
        setArmourClass(getBase_defence()+ body.getDefenseBonus());
        return body;
    }

    public void setLevel_chart(){
        level_chart.put(3,2);
        level_chart.put(6,3);
        level_chart.put(10,4);
        level_chart.put(15,5);
    }

    public void increaseMonsters_Defeated(){
        monsters_defeated++;
    }

    public void increaseLevel(){
        for (int i:level_chart.keySet()){
            if(monsters_defeated == i){
                level=level_chart.get(i);
                int attack= getBase_attack();
                int damage = getDamageCode();
                int defense = getBase_defence();
                if(hands==null){
                    attack+=level;
                    damage+=level;
                } else {
                    attack+=level + hands.getAttackBonus();
                    damage += level + hands.getDamageBonus();
                    defense += level + hands.getDefenseBonus();
                }
                if (body==null){
                    defense+=level;
                } else {
                    defense+=level+body.getDefenseBonus();
                }
                setToHit(attack);
                setDamageCode(damage);
                setArmourClass(defense);
            }
        }
    }

    private void unequipHands(){
        if(!(hands==null)){
            inventory.add(hands);
            hands=null;
        }
    }

    private void unequipBody(){
        if(!(body==null)){
            inventory.add(body);
            body=null;
        }
    }
}


