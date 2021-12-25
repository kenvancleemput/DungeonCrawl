public enum CommandWord {
    GO("go"), TAKE("take"), DROP("drop"), QUIT("quit"), HELP("help"), EAT("eat"), LOOK("look"), UNKNOWN("");

    private String word;

    CommandWord(String word){this.word=word;}

    public String getWord() {
        return word;
    }
}
