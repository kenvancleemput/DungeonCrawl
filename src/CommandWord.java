/**
 * enum class containing all the possible commands, their situational
 * use and their explanations.
 */
public enum CommandWord {
    GO("go","out of combat","makes you move in the specified direction"), TAKE("take","out of combat", "puts item in bag"), DROP("drop","out of combat","removes item from bag"), QUIT("quit","out of combat","quits the game"), HELP("help","out of combat","shows all possible commands"), EAT("eat","out of combat","eats food from your bag"), LOOK("look","out of combat","looks around your current room"), UNKNOWN("","",""),ATTACK("attack","in combat", "attacks the monster in front of you"), USE("use","in combat", "uses the specified consumable"),RUN("run", "in combat", "you run to a random exit"),INFO("info", "in combat", "shows combat info"), EQUIP("equip","out of combat", "equips a weapon or armour from your bag"), STATS("stats", "out of combat", "shows you your statistics");

    private String word;
    private String situation;
    private String explanation;

    /**
     * constructor
     * @param word the input
     * @param situation when the word can be used
     * @param explanation descriptor of use
     */
    CommandWord(String word, String situation, String explanation){this.word=word;this.situation=situation;this.explanation=explanation;}

    /**
     *
     * @return string associated with enum
     */
    public String getWord() {
        return word;
    }

    /**
     *
     * @return where it is used
     */
    public String getSituation() {
        return situation;
    }

    /**
     *
     * @return the explanation of that word.
     */
    public String getExplanation() {
        return explanation;
    }
}
