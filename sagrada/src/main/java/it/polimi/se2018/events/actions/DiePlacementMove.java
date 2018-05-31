package it.polimi.se2018.events.actions;


/**
 * Player's attempt to insert a die inside the grid
 * @author Alì El Wahsh
 */
public class DiePlacementMove extends PlayerMove
{

    private int height;
    private int width;
    private int diePosition;
    private boolean colorRestriction;
    private boolean valueRestriction;
    private boolean adjacentRestriction;

    /**
     * DiePlacementMove's constructor
     * @param h height of the placement
     * @param w width of the placement
     * @param diePosition position of the die inside teh reserve
     */
    public DiePlacementMove(int h, int w, int diePosition, String name, String description, boolean color, boolean value, boolean adjacent)
    {
        this.playerName = name;
        this.description = description;
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
