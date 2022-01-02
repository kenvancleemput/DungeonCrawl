public enum TradeAction {
    BUY("buy"), SELL("sell"), LEAVE("leave"), UNKNOWN("");

    private String word;

    TradeAction (String word){this.word=word;}

    public String getWord(){return word;}
}
