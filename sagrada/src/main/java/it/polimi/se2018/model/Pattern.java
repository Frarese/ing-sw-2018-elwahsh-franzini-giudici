package it.polimi.se2018.model;
import it.polimi.se2018.util.Pair;

/**
 * Model representation of a window pattern
 * @author Alì El Wahsh
 */
public class Pattern {
    public static final int HEIGHT = 4;
    public static final int WIDTH = 5;

    private Pair<ColorModel,Integer>[][] schema = new Pair[HEIGHT][WIDTH];
    private int favourPoints;


    /**
     * Build the pattern from a file
     * @param path file's path
     */
    private void loadFromFile(String path)
    {
       throw new UnsupportedOperationException();
    }

    /**
     * Pattern constructor
     * @param path file's path
     */
    public Pattern(String path)
    {
        loadFromFile(path);
    }

    /**
     * Getter for pattern's favour points (or difficulty)
     * @return the pattern's difficulty
     */
    public int getFavourPoints() {
        return favourPoints;
    }

    /**
     * Get the color of a cell inside the pattern
     * @param h cell's height
     * @param w cell's width
     * @return cell's color
     */
    public ColorModel getColor(int h, int w )
    {
        return schema[h][w].getFirst();
    }

    /**
     * Get the numeric value printed on the cell
     * @param h cell's height
     * @param w cell's width
     * @return the cell's value (0 if no value was printed)
     */
    public int getValue(int h, int w)
    {
        return schema[h][w].getSecond();
    }

    /**
     * toString method for ClI and log
     * @return a matrix of strings representing
     */
    public String toString()
    {
        StringBuilder strb = new StringBuilder();
        for (int i = 0; i <HEIGHT ; i++) {
            for (int j = 0; j <WIDTH ; j++) {
                strb.append(schema[i][j].getFirst()).append(" ").append(schema[i][j].getSecond()).append(" ");
            }
           strb.append("\n");
        }
        strb.append(favourPoints);

        return strb.toString();
    }

}
