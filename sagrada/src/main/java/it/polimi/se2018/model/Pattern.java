package it.polimi.se2018.model;
import it.polimi.se2018.util.Pair;

/**
 * Model representation of a window pattern
 * @author Alì El Wahsh
 */
public class Pattern {
    public static final int HEIGHT = 4;
    public static final int WIDTH = 5;

    private Pair[][] schema;
    private int favourPoints;
    private String name;


    /**
     * Pattern's constructor
     * @param schema pattern's grid
     * @param name pattern's name
     * @param favourPoints pattern's favour points
     */
    public Pattern(Pair[][] schema, String name, int favourPoints)
    {
        this.schema = schema;
        this.name = name;
        this.favourPoints = favourPoints;
        for(int h=0; h<Pattern.HEIGHT;h++)
            for(int w = 0; w<Pattern.WIDTH;w++)
                if(schema[h][w] == null)
                    schema[h][w] = new Pair<>(ColorModel.WHITE,0);
    }

    /**
     * Getter for pattern's favour points (or difficulty)
     * @return the pattern's difficulty
     */
    public int getFavourPoints() {
        return favourPoints;
    }

    /**
     * Getter for pattern's name
     * @return pattern's name
     */
    public String getName() {
        return name;
    }

    /**
     * Get the color of a cell inside the pattern
     * @param h cell's height
     * @param w cell's width
     * @return cell's color
     */
    public ColorModel getColor(int h, int w )
    {
        return (ColorModel) schema[h][w].getFirst();
    }

    /**
     * Get the numeric value printed on the cell
     * @param h cell's height
     * @param w cell's width
     * @return the cell's value (0 if no value was printed)
     */
    public int getValue(int h, int w)
    {
        return (Integer) schema[h][w].getSecond();
    }

    /**
     * toString method for ClI and log
     * @return a matrix of strings representing
     */
    public String toString()
    {
        StringBuilder strb = new StringBuilder();
        strb.append(name).append(" ").append(favourPoints).append("\n");
        for (int i = 0; i <HEIGHT ; i++) {
            for (int j = 0; j <WIDTH ; j++) {
                strb.append(schema[i][j].getFirst()).append(" ").append(schema[i][j].getSecond()).append(" ");
            }
           strb.append("\n");
        }
        return strb.toString();
    }

}
