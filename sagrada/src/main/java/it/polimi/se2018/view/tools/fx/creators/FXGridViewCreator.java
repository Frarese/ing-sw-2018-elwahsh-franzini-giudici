package it.polimi.se2018.view.tools.fx.creators;

import it.polimi.se2018.model.ColorModel;
import it.polimi.se2018.model.IntColorPair;
import it.polimi.se2018.view.app.JavaFXStageProducer;
import it.polimi.se2018.view.tools.GridViewCreator;
import it.polimi.se2018.view.tools.fx.alert.AlertBox;
import it.polimi.se2018.view.tools.fx.alert.ConfirmBox;
import it.polimi.se2018.view.tools.fx.controller.GameController;
import javafx.application.Platform;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.TransferMode;
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
     * Basic Class constructor that initializes elements at default value
     */
    public FXGridViewCreator(String gridColor) {
        this.gridColor = gridColor;
        this.dieViewCreator = new FXDieViewCreator(FXConstants.GRID_DIE_DIM_VALUE);
    }

    /**
     * Class constructor
     *
     * @param grid        the grid to use
     * @param gridPattern the pattern to use
     */
    public FXGridViewCreator(IntColorPair[][] grid, IntColorPair[][] gridPattern, String color) {
        super(grid, gridPattern);
        this.dieViewCreator = new FXDieViewCreator(FXConstants.GRID_DIE_DIM_VALUE);
        this.gridColor = color;
    }

    @Override
    public VBox display() {
        //Initialize container
        VBox container = new VBox(0);

        //Initialize grid
        FXConstants.createEmptyGrid(container, gridColor, 4, 5,
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

    public VBox displayWithDeD() {
        //Initialize container
        VBox container = display();

        //Iterate on grid pattern
        for (int i = 0; i < gridPattern.length; i++) {
            for (int j = 0; j < gridPattern[i].length; j++) {
                //Get equivalent view  cell
                HBox row = (HBox) container.getChildren().get(i);
                VBox cell = (VBox) row.getChildren().get(j);

                cell.setOnDragOver(doEvent -> {
                    if (doEvent.getDragboard().hasString()) {
                        doEvent.acceptTransferModes(TransferMode.COPY);
                    }
                });

                int finalI = i;
                int finalJ = j;
                cell.setOnDragDropped(ddEvent -> {
                    String reserveIndex = ddEvent.getDragboard().getString();
                    JavaFXStageProducer.getApp().getViewActions().setDie(Integer.parseInt(reserveIndex), finalI, finalJ);
                    ((GameController) JavaFXStageProducer.getController()).disablePlacement();
                });
            }
        }

        //Return
        return container;
    }

    public VBox displayWithClick(boolean isDieSelection, boolean isColorSelection) {
        //Initialize container
        VBox container = display();

        //Iterate on grid pattern
        for (int i = 0; i < gridPattern.length; i++) {
            for (int j = 0; j < gridPattern[i].length; j++) {
                //Get equivalent view  cell
                HBox row = (HBox) container.getChildren().get(i);
                VBox cell = (VBox) row.getChildren().get(j);

                handleClick(cell, i, j, isDieSelection, isColorSelection);
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
    private void setCellBackgroundColor(IntColorPair patternCell, VBox cell) {
        if (patternCell != null) {
            if (patternCell.getSecond() != ColorModel.WHITE) {
                //Set color restriction
                cell.setStyle(FXConstants.makeBgColorString(patternCell.getSecond().toJavaFXColor()));
            } else {
                if (patternCell.getFirst() != 0) {
                    //Set value restriction
                    String url = MessageFormat.format("/it/polimi/se2018/view/images/pattern/value{0}.png", patternCell.getFirst());
                    String css = MessageFormat.format("-fx-background-size: COVER;-fx-background-image: url({0});", url);
                    cell.setStyle(css);
                } else {
                    //Set no restriction (white bg)
                    cell.setStyle(FXConstants.makeBgColorString(ColorModel.WHITE.toJavaFXColor()));
                }
            }
        } else {
            //Set no restriction (white bg)
            cell.setStyle(FXConstants.makeBgColorString(ColorModel.WHITE.toJavaFXColor()));
        }
    }

    /**
     * Handles grid cell's click
     *
     * @param cell             contains VBox's cell
     * @param i                contains row index
     * @param j                contains column index
     * @param isDieSelection   contains boolean value for die selection
     * @param isColorSelection contains boolean value for color die selection
     */
    private void handleClick(VBox cell, int i, int j, boolean isDieSelection, boolean isColorSelection) {
        if (isDieSelection) {
            cell.setOnMouseClicked(clickEvent -> {
                boolean answer = ConfirmBox.display("Selezione Dado", "Vuoi usare il dado cliccato?");
                if (answer) {
                    if (isColorSelection) {
                        JavaFXStageProducer.getApp().getViewToolCardActions().selectedDieFromGridByColor(j, i);
                    } else {
                        JavaFXStageProducer.getApp().getViewToolCardActions().selectedDieFromGrid(j, i);
                    }
                }
                clickEvent.consume();
            });
        } else {
            cell.setOnMouseClicked(clickEvent -> {
                boolean answer = ConfirmBox.display("Selezione Cella", "Vuoi selezionare la cella cliccata?");
                if (answer) {
                    JavaFXStageProducer.getApp().getViewToolCardActions().selectedDieToGrid(j, i);
                }
                clickEvent.consume();
            });
        }
    }
}
