/**
 * Enum that contains all the actions you can do while trading.
 */
public enum TradeAction {
    BUY("buy","buy an item in the trader's stock"), SELL("sell", "sell an item in your bag"), LEAVE("leave", "leave the trader"),HELP("help","show actions while trading"),BALANCE("balance","show your money"), UNKNOWN("","");

    private String word;
    private String explanation;

    TradeAction (String word, String explanation){
        this.word=word;
        this.explanation=explanation;
    }

    public String getWord(){return word;}

    public String getExplanation() {
        return explanation;
    }
}
