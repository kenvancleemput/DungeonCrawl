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

    public Player(String name, int health, int armourClass, int toHit, int damageCode, boolean movable, int gold) {
        super(name, health, armourClass, toHit, damageCode, movable, gold);
        maxWeightInBag = 50;
        hands=null;
        body=null;
        monsters_defeated=0;
        level_chart=new HashMap<>();
        level=1;
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
        removeItem(hands);
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
        removeItem(body);
        setArmourClass(getBase_defence()+ body.getDefenseBonus());
        return body;
    }

    public int getLevel() {
        return level;
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

    public boolean increaseLevel(){
        boolean levelup=false;
        for (int i:level_chart.keySet()){
            if(monsters_defeated == i){
                level=level_chart.get(i);
                int attack= getBase_attack();
                int damage = getBase_damage();
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
                setHealth(level*getMax_health());
                levelup=true;
            }
        } return levelup;
    }

    private void unequipHands(){
        if(!(hands==null)){
            addItem(hands);
            hands=null;
        }
    }

    private void unequipBody(){
        if(!(body==null)){
            addItem(body);
            body=null;
        }
    }

    public String checkEquipment() {
        String eq="";
        if(hands!=null){
            eq+="I am wielding a " + hands.getName() +".";
        } else {
            eq+="I am wielding nothing.";
        }
        if(body!=null){
            eq+="\nI'm wearing " + body.getName() + " for protection.";
        } else {
            eq+="\nI'm not wearing protection.";
        }
        return eq;
    }

    public String showStats(){
        String stats="I have ";
        stats+= getHealth()+ " health and " + getToHit() + " attack bonus, " + getArmourClass()+ " armour class";
        stats+= "\nam level " + getLevel() +" and I do from 1 to "+ getDamageCode() + " damage";
        return stats;
    }
}


