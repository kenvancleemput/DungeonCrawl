public enum CombatWord {
    ATTACK("attack"),DRINK("drink"),RUN("run"), UNKNOWN(""),INFO("info");

    private String word;

    CombatWord(String word){
        this.word=word;
    }

    public String getWord() {
        return word;
    }
}
