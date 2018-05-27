package it.polimi.se2018.view.tools.fx.creators;

import it.polimi.se2018.model.ColorModel;
import it.polimi.se2018.util.Pair;
import it.polimi.se2018.view.tools.GridViewCreator;
import it.polimi.se2018.view.tools.fx.FXConstants;
import it.polimi.se2018.view.tools.fx.alert.AlertBox;
import javafx.application.Platform;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;

import java.text.MessageFormat;

/**
 * Class to create and to handle grid in GUI
 *
 * @author Mathyas Giudici
 */

public class FXGridViewCreator extends GridViewCreator<VBox, Image> {

    private final String gridColor;

    /**
     * Class constructor
     *
     * @param grid        the grid to use
     * @param gridPattern the pattern to use
     */
    public FXGridViewCreator(Pair<Integer, ColorModel>[][] grid, Pair<Integer, ColorModel>[][] gridPattern, String color) {
        super(grid, gridPattern);
        this.dieViewCreator = new FXDieViewCreator(FXConstants.GRID_CELL_DIM_VALUE);
        this.gridColor = color;
    }

    @Override
    public VBox display() {
        //Initialize container
        VBox container = new VBox(0);

        //Initialize grid
        createEmptyGrid(container, gridColor, 4, 5,
                FXConstants.GRID_INSETS_SPACING, FXConstants.GRID_ROW_SPACING, FXConstants.GRID_CELL_DIM_VALUE);

        //Iterate on grid pattern
        for (int i = 0; i < gridPattern.length; i++) {
            for (int j = 0; j < gridPattern[i].length; j++) {
                //Get equivalent view  cell
                HBox row = (HBox) container.getChildren().get(i);
                VBox cell = (VBox) row.getChildren().get(j);

                //Set cell bg color
                this.setCellBackgroundColor(gridPattern[i][j], cell);

                cell.setStyle((new StringBuilder().append(cell.getStyle()).append("-fx-border-radius: 1; -fx-border-color: BLACK;")).toString());

                if (grid != null && grid[i][j] != null) {
                    //Create die and insert in current cell
                    ImageView imageView = new ImageView((Image) dieViewCreator.makeDie(grid[i][j]));
                    imageView.setPreserveRatio(true);
                    imageView.setFitWidth(cell.getWidth());
                    imageView.setFitHeight(cell.getHeight());
                    cell.getChildren().add(0, imageView);
                }
            }
        }

        //Return
        return container;
    }

    @Override
    public void addADie(Image die, int height, int width) {
        String message = "Aggiunto alla posizione " + height + "," + width + ".";
        Platform.runLater(() ->
                AlertBox.display("Mossa", message, die)
        );
    }

    @Override
    public Image pickDie(int height, int width) {
        return (Image) dieViewCreator.makeDie(grid[height][width]);
    }

    /**
     * Sets cell's background color in player's grid
     *
     * @param patternCell contains the cell restriction
     * @param cell        contains the cell in the view grid
     */
    private void setCellBackgroundColor(Pair<Integer, ColorModel> patternCell, VBox cell) {
        if (patternCell != null) {
            if (patternCell.getSecond() != ColorModel.WHITE) {
                //Set color restriction
                cell.setStyle(makeBackgroundColor(patternCell.getSecond().toJavaFXColor()));
            } else {
                //Set value restriction
                String url = MessageFormat.format("/it/polimi/se2018/view/images/pattern/value{0}.png", patternCell.getFirst());
                String css = MessageFormat.format("-fx-background-size: COVER;-fx-background-image: url({0});", url);
                cell.setStyle(css);
            }
        } else {
            //Set no restriction (white bg)
            cell.setStyle(makeBackgroundColor(ColorModel.WHITE.toJavaFXColor()));
        }
    }

    /**
     * Creates a string to set background color
     *
     * @param color contains the color to set
     * @return the setting string
     */
    private static String makeBackgroundColor(String color) {
        return "-fx-background-color: " + color + ";";
    }

    /**
     * Creates a generic grid using HBox and VBox in container
     *
     * @param container     is where grid must be put
     * @param bgColor       contains the background color of the grid
     * @param row           contains the grid's row
     * @param column        contains the grid's column
     * @param insetsSpacing contains the Insets's value
     * @param rowSpacing    contains the row's spacing
     * @param cellDim       contains the cell's dimension
     */
    static void createEmptyGrid(VBox container, String bgColor, int row, int column,
                                int insetsSpacing, int rowSpacing, int cellDim) {
        //Container properties
        container.setPadding(new Insets(insetsSpacing, insetsSpacing, insetsSpacing, insetsSpacing));
        container.setStyle(makeBackgroundColor(bgColor));
        container.setFillWidth(true);
        container.setAlignment(Pos.CENTER);

        for (int i = 0; i < row; i++) {
            //Creates row
            HBox hBox = new HBox(rowSpacing);
            hBox.setPadding(new Insets(insetsSpacing, insetsSpacing, insetsSpacing, insetsSpacing));
            hBox.setStyle(makeBackgroundColor(bgColor));
            hBox.setFillHeight(true);
            hBox.setAlignment(Pos.CENTER);
            container.getChildren().add(i, hBox);

            for (int j = 0; j < column; j++) {
                //Create cell
                VBox cell = new VBox(0);
                cell.setAlignment(Pos.CENTER);
                cell.setPrefWidth(cellDim);
                cell.setPrefHeight(cellDim);
                hBox.getChildren().add(j, cell);
            }
        }
    }
}
