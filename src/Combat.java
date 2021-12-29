public class Combat {
    private CombatWord combatWord;
    private String secondWord;

    public Combat(CombatWord combatWord, String secondWord) {
        this.combatWord = combatWord;
        this.secondWord = secondWord;
    }

    public CombatWord getCombatWord() {
        return combatWord;
    }

    public String getSecondWord() {
        return secondWord;
    }

    public boolean isunknown(){return combatWord==null;}

    public boolean hasSecondWord(){ return secondWord != null;}
}
