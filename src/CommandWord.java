public enum CommandWord {
    GO("go"), TAKE("take"), DROP("drop"), QUIT("quit"), HELP("help"), EAT("eat"), LOOK("look"), UNKNOWN(""),ATTACK("attack"), USE("use"),RUN("run"),INFO("info"), EQUIP("equip");

    private String word;

    CommandWord(String word){this.word=word;}

    public String getWord() {
        return word;
    }
}
