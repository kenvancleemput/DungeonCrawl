import java.util.Scanner;

public class TradeParser {

/**
 * This class is part of the "Dungeon Crawl" application.
 * "Dungeon Crawl" is a complicated, text based adventure game.
 *
 * This parser reads user input and tries to interpret it as a "trade action"
 * command. Every time it is called it reads a line from the terminal and
 * tries to interpret the line as a two word command. It returns the command
 * as an object of class Action.
 *
 * The parser has a set of known action words. It checks user input against
 * the known actions, and if the input is not one of the known actions, it
 * returns an action object that is marked as an unknown action.
 *
 * @author  Ken Van Cleemput, based on code by Michael Kölling and David J. Barnes
 * @version 2022.01.05
 */


    private TradeActions actions;  // holds all valid action words
    private Scanner reader; // source of command input


    /**
     * Create a parser to read from the terminal window.
     */
    public TradeParser()
    {
        actions = new TradeActions();
        reader = new Scanner(System.in);
    }

    /**
     * @return The next action from the user.
     */
    public Action getAction()
    {
        String inputLine;   // will hold the full input line
        String word1 = null;
        String word2 = null;

        System.out.print("> ");     // print prompt

        inputLine = reader.nextLine();

        // Find up to two words on the line.
        Scanner tokenizer = new Scanner(inputLine);
        if(tokenizer.hasNext()) {
            word1 = tokenizer.next();      // get first word
            if(tokenizer.hasNext()) {
                word2 = tokenizer.next();      // get second word
                // note: we just ignore the rest of the input line.
            }
        }

        // Now check whether this word is known. If so, create an action
        // with it. If not, create a "null" action (for unknown action).

            return new Action(actions.getAction(word1), word2);

    }


    /**
     * Lists all the possible actions
     * @return a string with all the possible actions while trading.
     */
    public String showActions() {
        return actions.showAll();
    }
}
