package it.polimi.se2018.view.tools.fx.creators;

import it.polimi.se2018.model.ColorModel;
import it.polimi.se2018.util.Pair;
import it.polimi.se2018.view.tools.RoundTrackerViewCreator;
import it.polimi.se2018.view.tools.fx.FXConstants;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;

/**
 * Class to create round tracker in GUI
 *
 * @author Mathyas Giudici
 */

public class FXRoundTrackerViewCreator extends RoundTrackerViewCreator<VBox> {

    /**
     * Class constructor
     *
     * @param round        contains the round
     * @param roundTracker contains the round tracker
     */
    public FXRoundTrackerViewCreator(int round, Pair<Integer, ColorModel>[][] roundTracker) {
        super(round, roundTracker);
        this.dieViewCreator = new FXDieViewCreator(FXConstants.ROUNDT_CELL_DIM_VALUE);
    }

    @Override
    public VBox display() {
        //Initialize container
        VBox container = new VBox(0);

        //Initialize grid
        FXGridViewCreator.createEmptyGrid(container, "WHITE", 2, 5,
                FXConstants.ROUNDT_INSETS_SPACING, FXConstants.ROUNDT_ROW_SPACING, FXConstants.ROUNDT_CELL_DIM_VALUE);

        for (int i = 0; i < roundTracker.length; i++) {
            //Create round tracker elements
            Label label = new Label("Round: " + i);
            ChoiceBox<ImageView> choiceBox = new ChoiceBox<>();

            //Set comboBox properties
            choiceBox.setPrefHeight(FXConstants.ROUNDT_CELL_DIM_VALUE);
            choiceBox.setPrefWidth(FXConstants.ROUNDT_CELL_DIM_VALUE);
            choiceBox.setMaxHeight(FXConstants.ROUNDT_CELL_DIM_VALUE);
            choiceBox.setMaxWidth(FXConstants.ROUNDT_CELL_DIM_VALUE);

            //Create die in comboBox
            if (i < round && roundTracker[i] != null) {
                for (int j = 0; j < roundTracker[i].length; j++) {
                    if (roundTracker[i][j] != null) {
                        Image die = (Image) dieViewCreator.makeDie(roundTracker[i][j]);
                        ImageView imageView = new ImageView(die);
                        imageView.setFitHeight(choiceBox.getHeight());
                        imageView.setFitWidth(choiceBox.getWidth());
                        choiceBox.getItems().add(imageView);
                        choiceBox.setValue(imageView);
                    }
                }
            }


            //Add elements in correct row
            if (i > 4) {
                HBox row = (HBox) container.getChildren().get(1);
                VBox cell = (VBox) row.getChildren().get(i - 5);
                cell.getChildren().addAll(label, choiceBox);

            } else {
                HBox row = (HBox) container.getChildren().get(0);
                VBox cell = (VBox) row.getChildren().get(i);
                cell.getChildren().addAll(label, choiceBox);
            }
        }

        //Return
        return container;
    }
}
