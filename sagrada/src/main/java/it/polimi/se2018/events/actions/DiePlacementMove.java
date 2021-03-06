package it.polimi.se2018.events.actions;


/**
 * Player's attempt to insert a die inside the grid
 * @author Alì El Wahsh
 */
public class DiePlacementMove extends PlayerMove
{

    private final int height;
    private final int width;
    private final int diePosition;
    private final boolean colorRestriction;
    private final boolean valueRestriction;
    private final boolean adjacentRestriction;

    /**
     * DiePlacementMove's constructor
     * @param h height of the placement
     * @param w width of the placement
     * @param diePosition position of the die inside teh reserve
     * @param name player name
     * @param color true if color restriction is to be used
     * @param value true if value restriction is to be used
     * @param adjacent true if adjacent restriction is to be used
     */
    public DiePlacementMove(int h, int w, int diePosition, String name, boolean color, boolean value, boolean adjacent)
    {
        this.playerName = name;
        this.description = "Placement";
        this.height = h;
        this.width = w;
        this.diePosition = diePosition;
        this.colorRestriction = color;
        this.valueRestriction = value;
        this.adjacentRestriction = adjacent;

    }


    /**
     * Getter for height
     * @return height
     */
    public int getHeight() {
        return height;
    }

    /**
     * Getter for width
     * @return width
     */
    public int getWidth() {
        return width;
    }

    /**
     * Getter for die
     * @return die
     */
    public int getDiePosition() {
        return diePosition;
    }

    /**
     * Checks if the adjacency restriction is enabled
     * @return true if the restriction is enabled, false otherwise
     */
    public boolean isAdjacentRestriction() {
        return adjacentRestriction;
    }

    /**
     * Checks if the color restriction is enabled
     * @return true if the restriction is enabled, false otherwise
     */
    public boolean isColorRestriction() {
        return colorRestriction;
    }

    /**
     * Checks if the value restriction is enabled
     * @return true if the restriction is enabled, false otherwise
     */
    public boolean isValueRestriction() {
        return valueRestriction;
    }
}
