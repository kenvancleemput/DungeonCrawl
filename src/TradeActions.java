import java.util.HashMap;
/**
 * This class is part of the "Dungeon Crawl" application.
 * "Dungeon Crawl" is a complicated, text based adventure game.
 *
 * This class holds an enumeration of all action words known to the game.
 * It is used to recognise actions as they are typed in.
 *
 * @author  Ken Van Cleemput, based on code by Michael Kölling and David J. Barnes
 * @version 2022.01.05
 */

public class TradeActions {
    private HashMap<String, TradeAction> validActions;

    /**
     * Constructor to initiate all possible actions.
     */
    public TradeActions(){
        validActions=new HashMap<>();
        for(TradeAction action: TradeAction.values()){
            if(action != TradeAction.UNKNOWN){
                validActions.put(action.getWord(),action);
            }
        }
    }

    /**
     * Checks to see if action given is valid.
     * @param aString action the player wants to undertake
     * @return if this is valid or not.
     */
    public boolean isAction(String aString){
        return validActions.containsKey(aString);
    }

    /**
     * Checks to see whether an action is valid, and if so, returns that action.
     * @param aString input by player
     * @return the action.
     */
    public TradeAction getAction(String aString){
        if(isAction(aString)){
            return validActions.get(aString);
        } return TradeAction.UNKNOWN;
    }
    /**
     * Shows all possible actions.
     * @return a string with all allowed actions.
     */
    public String showAll() {
        String show = "";
        for (String action : validActions.keySet()) {
            show += "\n" + action + " is used to " + validActions.get(action).getExplanation();
        }
        return show;
    }
}
