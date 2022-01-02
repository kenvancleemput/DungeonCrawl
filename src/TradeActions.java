import java.util.HashMap;

public class TradeActions {
    private HashMap<String, TradeAction> validActions;

    public TradeActions(){
        validActions=new HashMap<>();
        for(TradeAction action: TradeAction.values()){
            if(action != TradeAction.UNKNOWN){
                validActions.put(action.getWord(),action);
            }
        }
    }

    public boolean isAction(String aString){
        return validActions.containsKey(aString);
    }

    public String showAll(){
        String show="";
        for(String action: validActions.keySet()){
            show+=action;
        } return show;
    }

    public TradeAction getAction(String aString){
        if(isAction(aString)){
            return validActions.get(aString);
        } return TradeAction.UNKNOWN;
    }
}
