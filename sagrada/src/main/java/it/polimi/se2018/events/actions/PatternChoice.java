package it.polimi.se2018.events.actions;

/**
 * Player choice for pattern
 */
public class PatternChoice extends PlayerMove {

    private final String patterName;

    /**
     * PatternChoice's constructor
     * @param username player's username
     * @param patternName chosen pattern's name
     */
    public PatternChoice(String username, String patternName)
    {
        this.playerName = username;
        this.patterName = patternName;
        this.description = "Pattern selected";
    }

    /**
     * Getter for the selected pattern's
     * @return selected pattern's name
     */
    public String getPatterName() {
        return patterName;
    }
}
