package it.polimi.se2018.view.tools.fx.creators;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;

/**
 * Abstract class that contains FX constants used to setup Panel
 *
 * @author Mathyas Giudici
 */

abstract class FXConstants {

    private FXConstants() {

    }

    /**
     * Constants for grid and dice
     */
    static final int GRID_CELL_DIM_VALUE = 50;
    static final int GRID_DIE_DIM_VALUE = 40;
    static final int GRID_INSETS_SPACING = 5;
    static final int GRID_ROW_SPACING = 10;

    /**
     * Constants for reserve
     */
    static final int RESERVE_INSETS_SPACING = 7;
    static final int RESERVE_ROW_SPACING = 7;

    /**
     * Constants for roundTracker
     */
    static final int ROUNDT_CELL_DIM_VALUE = 70;
    static final int ROUNDT_IMG_DIM_VALUE = 35;
    static final int ROUNDT_INSETS_SPACING = 15;
    static final int ROUNDT_ROW_SPACING = 5;

    /**
     * Creates a string to set background color
     *
     * @param color contains the color to set
     * @return the setting string
     */
    static String makeBgColorString(String color) {
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
        container.setStyle(makeBgColorString(bgColor));
        container.setFillWidth(true);
        container.setAlignment(Pos.CENTER);

        for (int i = 0; i < row; i++) {
            //Creates row
            HBox hBox = new HBox(rowSpacing);
            hBox.setPadding(new Insets(insetsSpacing, insetsSpacing, insetsSpacing, insetsSpacing));
            hBox.setStyle(makeBgColorString(bgColor));
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
