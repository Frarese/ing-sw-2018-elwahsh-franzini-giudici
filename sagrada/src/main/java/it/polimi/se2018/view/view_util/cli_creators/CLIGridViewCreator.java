package it.polimi.se2018.view.view_util.cli_creators;

import it.polimi.se2018.model.ColorModel;
import it.polimi.se2018.util.Pair;
import it.polimi.se2018.view.view_util.GridViewCreator;
import it.polimi.se2018.view.view_util.cli_interface.CLIPrinter;

import java.util.ArrayList;

/**
 * Class to create and to handle grid in CLI
 *
 * @author Mathyas Giudici
 */

public class CLIGridViewCreator extends GridViewCreator<ArrayList<String>, String> {

    private CLIPrinter printer;

    /**
     * Class constructor
     * <p>
     * {@link GridViewCreator}
     */
    public CLIGridViewCreator(Pair<Integer, ColorModel>[][] grid, Pair<Integer, ColorModel>[][] gridPattern) {
        super(grid, gridPattern);
        this.dieViewCreator = new CLIDieViewCreator();
    }

    @Override
    public ArrayList<String> display() {
        ArrayList<String> strings = new ArrayList<>();

        //Start to create grid
        for (int i = 0; i < gridPattern.length; i++) {
            StringBuilder currentSring = new StringBuilder();
            for (int j = 0; i < gridPattern[i].length; j++) {
                currentSring.append("|");
                currentSring.append(fixPrintSpace((String) dieViewCreator.makeDie(gridPattern[i][j])));
                if (grid != null && grid[i][j] != null) {
                    currentSring.append("|");
                    currentSring.append(fixPrintSpace((String) dieViewCreator.makeDie(grid[i][j])));
                }
            }
            strings.add(i, currentSring.toString());
        }

        //Fix line separator on grid
        for (int i = 0; i < strings.size(); i = i + 2) {
            strings.add(i + 1, lineCreator(strings.get(i).length()));
        }

        strings.add(0, lineCreator(strings.get(0).length()));

        return strings;
    }

    @Override
    public void addADie(String die, int height, int width) {
        printer.print("Aggiunto dado " + die + " in posizione (" + height + "," + width + ")");
    }

    @Override
    public String pickDie(int height, int width) {
        return (String) dieViewCreator.makeDie(this.grid[height][width]);
    }

    private String fixPrintSpace(String toFix) {
        //Check length
        if (toFix.length() < 9) {
            //Create String Builder
            StringBuilder stringBuilder = new StringBuilder();
            stringBuilder.append(toFix);

            //Fix String to 9 characters
            while (stringBuilder.toString().length() < 10) {
                stringBuilder.append(" ");
            }
            return stringBuilder.toString();
        }
        //Return
        return toFix;
    }

    private String lineCreator(int value) {
        //Create a String Builder
        StringBuilder string = new StringBuilder();

        //Make line
        for (int i = 0; i < value; i++) {
            string.append("-");
        }
        return string.toString();
    }
}