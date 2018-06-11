package it.polimi.se2018.view.tools.fx.creators;

import it.polimi.se2018.model.IntColorPair;
import it.polimi.se2018.view.app.JavaFXStageProducer;
import it.polimi.se2018.view.tools.ReserveViewCreator;
import it.polimi.se2018.view.tools.fx.alert.ConfirmBox;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.ClipboardContent;
import javafx.scene.input.Dragboard;
import javafx.scene.input.TransferMode;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;

/**
 * Class to create and to handle reserve in GUI
 *
 * @author Mathyas Giudici
 */

public class FXReserveViewCreator extends ReserveViewCreator<VBox, Image> {

    /**
     * Basic Class constructor that initializes elements at default value
     */
    public FXReserveViewCreator() {
        super();
        this.dieViewCreator = new FXDieViewCreator(FXConstants.GRID_CELL_DIM_VALUE);
    }

    /**
     * Class constructor
     *
     * @param reserve contains the reserve
     */
    public FXReserveViewCreator(IntColorPair[] reserve) {
        super(reserve);
        this.dieViewCreator = new FXDieViewCreator(FXConstants.GRID_CELL_DIM_VALUE);
    }

    @Override
    public VBox display() {
        //Initialize container
        VBox container = new VBox(0);

        //Initialize grid
        FXConstants.createEmptyGrid(container, "GREY", 3, 3,
                FXConstants.RESERVE_INSETS_SPACING, FXConstants.RESERVE_ROW_SPACING, FXConstants.GRID_CELL_DIM_VALUE);


        //Iterate on reserve
        for (int i = 0; i < reserve.length; i++) {
            if (reserve[i] != null) {
                //Check iterator's index to set die in correct position on grid
                if (i >= 3) {
                    if (i >= 6) {
                        HBox row = (HBox) container.getChildren().get(2);
                        VBox cell = (VBox) row.getChildren().get(i - 6);
                        ImageView die = new ImageView((Image) dieViewCreator.makeDie(reserve[i]));
                        die.setPreserveRatio(true);
                        die.setFitWidth(cell.getWidth());
                        die.setFitHeight(cell.getHeight());
                        cell.getChildren().add(0, die);
                    } else {
                        HBox row = (HBox) container.getChildren().get(1);
                        VBox cell = (VBox) row.getChildren().get(i - 3);
                        ImageView die = new ImageView((Image) dieViewCreator.makeDie(reserve[i]));
                        die.setPreserveRatio(true);
                        die.setFitWidth(cell.getWidth());
                        die.setFitHeight(cell.getHeight());
                        cell.getChildren().add(0, die);
                    }
                } else {
                    HBox row = (HBox) container.getChildren().get(0);
                    VBox cell = (VBox) row.getChildren().get(i);
                    ImageView die = new ImageView((Image) dieViewCreator.makeDie(reserve[i]));
                    die.setPreserveRatio(true);
                    die.setFitWidth(cell.getWidth());
                    die.setFitHeight(cell.getHeight());
                    cell.getChildren().add(0, die);
                }
            }
        }

        //Return
        return container;
    }

    public VBox displayWithDeD() {
        //Initialize container
        VBox container = display();

        //Iterate on reserve
        for (int i = 0; i < reserve.length; i++) {
            if (reserve[i] != null) {
                ImageView die = searchDie(i, container);

                int finalI = i;
                die.setOnDragDetected(ddEvent -> {
                    String reserveIndex = Integer.toString(finalI);
                    Dragboard dragboard = die.startDragAndDrop(TransferMode.COPY);
                    dragboard.setDragView(die.snapshot(null, null));
                    ClipboardContent clipboardContent = new ClipboardContent();
                    clipboardContent.putString(reserveIndex);
                    dragboard.setContent(clipboardContent);
                    ddEvent.consume();
                });
            }
        }

        //Return
        return container;
    }

    public VBox displayWithClick() {
        //Initialize container
        VBox container = display();

        //Iterate on reserve
        for (int i = 0; i < reserve.length; i++) {
            if (reserve[i] != null) {
                ImageView die = searchDie(i, container);

                int finalI = i;
                die.setOnMouseClicked(clickEvent -> {
                    boolean answer = ConfirmBox.display("Selezione Dado", "Vuoi usare il dado cliccato?");
                    if (answer) {
                        JavaFXStageProducer.getApp().getViewToolCardActions().selectedDieFromReserve(finalI);
                    }
                    clickEvent.consume();
                });
            }
        }

        //Return
        return container;
    }

    @Override
    public Image pickDie(int index) {
        return (Image) dieViewCreator.makeDie(reserve[index]);
    }

    /**
     * Searches a die in the reserve
     *
     * @param i         reserve index
     * @param container reserve container
     * @return the ImageView of the wanted die
     */
    private ImageView searchDie(int i, VBox container) {
        ImageView die;
        if (i >= 3) {
            if (i >= 6) {
                VBox cell = (VBox) ((HBox) container.getChildren().get(2)).getChildren().get(i - 6);
                die = (ImageView) cell.getChildren().get(0);
            } else {
                VBox cell = (VBox) ((HBox) container.getChildren().get(1)).getChildren().get(i - 3);
                die = (ImageView) cell.getChildren().get(0);

            }
        } else {
            VBox cell = (VBox) ((HBox) container.getChildren().get(0)).getChildren().get(i);
            die = (ImageView) cell.getChildren().get(0);
        }

        return die;
    }
}
