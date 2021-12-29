import java.util.HashMap;

public class CombatWords {
    private HashMap <String, CombatWord> validActions;

    /**
     * Constructor for actions in combat
     */
    public CombatWords(){
        validActions=new HashMap<>();
            for (CombatWord word: CombatWord.values()){
                if(word != CombatWord.UNKNOWN) {
                    validActions.put(word.getWord(), word);
                }
            }
    }

    /**
     *Check whether a given string is a valid action in combat.
     */
    public boolean isAction(String aString) {
        return validActions.containsKey(aString);
    }
    public String showAll() {
        String show = "";
        for (String command : validActions.keySet()) {
            show += command + " ";
        }
        return show;
    }

    public CombatWord getCommand(String aString) {
        if (isAction(aString)) {
            return validActions.get(aString);
        }
        return CombatWord.UNKNOWN;
    }
}
