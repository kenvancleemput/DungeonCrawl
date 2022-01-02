public class Action {
    private TradeAction tradeAction;
    private String secondWord;

    public Action(TradeAction firstWord, String secondWord) {
        tradeAction = firstWord;
        this.secondWord = secondWord;
    }

    public TradeAction getTradeAction() {
        return tradeAction;
    }

    public String getSecondWord() {
        return secondWord;
    }

    public boolean hasSecondWord(){
        return (secondWord!=null);
    }
}
