import java.util.HashMap;

/**
 * This class is part of the "Dungeon Crawl" application.
 * "Dungeon Crawl" is a complicated, text based adventure game.
 *
 * This class holds an enumeration of all command words known to the game.
 * It is used to recognise commands as they are typed in.
 *
 * @author  Ken Van Cleemput, based on code by Michael Kölling and David J. Barnes
 * @version 2022.01.05
 */

public class CommandWords
{
    // a constant array that holds all valid command words
    private HashMap<String, CommandWord> validCommands;


    /**
     * Constructor - initialise the command words.
     */
    public CommandWords() {
        validCommands = new HashMap<>();
        for (CommandWord word : CommandWord.values()) {
            if (word != CommandWord.UNKNOWN) {
                    validCommands.put(word.getWord(), word);
            }
        }
    }

    /**
     * Check whether a given String is a valid command word.
     * @return true if a given string is a valid command,
     * false if it isn't.
     */
    public boolean isCommand(String aString)
    {
        return validCommands.containsKey(aString);
        }

    /**
     * A list of all commands
     * @return string containing all commands
     */
    public String showAll() {
        String show = "";
        for (String command : validCommands.keySet()) {
            show += "\n" + command + " is used " + validCommands.get(command).getSituation() +" and " + validCommands.get(command).getExplanation();
             }
        return show;
    }

    /**
     *
     * @param aString input by user
     * @return the associated command word, or unknown.
     */
    public CommandWord getCommand(String aString) {
        if (isCommand(aString)) return validCommands.get(aString);
        return CommandWord.UNKNOWN;
    }
}
