public class Action {
    private TradeAction tradeAction;
    private String secondWord;

    /**
     * Creates actions while trading with merchant.
     * @param firstWord The first word of the action. Returns null if the action is not recognized.
     * @param secondWord The second word of the action.
     */

    public Action(TradeAction firstWord, String secondWord) {
        tradeAction = firstWord;
        this.secondWord = secondWord;
    }

    /**
     * Returns the action word. return null if word was not recognized
     * @return tradeAction
     */
    public TradeAction getTradeAction() {
        return tradeAction;
    }

    /**
     * Returns the second word. Returns null if there is no second word in command.
     * @return secondWord.
     */
    public String getSecondWord() {
        return secondWord;
    }

}
