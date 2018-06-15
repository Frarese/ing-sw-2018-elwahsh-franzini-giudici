package it.polimi.se2018.view.tools.fx.creators;

import it.polimi.se2018.model.IntColorPair;
import it.polimi.se2018.model.dice.RoundTracker;
import it.polimi.se2018.view.app.JavaFXStageProducer;
import it.polimi.se2018.view.tools.RoundTrackerViewCreator;
import it.polimi.se2018.view.tools.fx.alert.ConfirmBox;
import javafx.scene.control.Label;
import javafx.scene.control.MenuButton;
import javafx.scene.control.MenuItem;
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
     * Basic Class constructor that initializes elements at default value
     */
    public FXRoundTrackerViewCreator() {
        super();
        this.dieViewCreator = new FXDieViewCreator(FXConstants.GRID_CELL_DIM_VALUE);
    }

    /**
     * Class constructor
     *
     * @param round        contains the round
     * @param roundTracker contains the round tracker
     */
    FXRoundTrackerViewCreator(int round, IntColorPair[][] roundTracker) {
        super(round, roundTracker);
        this.dieViewCreator = new FXDieViewCreator(FXConstants.ROUNDT_IMG_DIM_VALUE);
    }

    @Override
    public VBox display() {
        return roundTrackerCreator(false);
    }


    /**
     * Creates a FXRoundTracker with cells' click properties
     *
     * @return VBox objects that contains round tracker
     */
    public VBox displayWithClick() {
        return roundTrackerCreator(true);
    }

    /**
     * Create a FXRoundTracker using MenuButton
     *
     * @param hasClickProp contains boolean value for cells' click properties
     * @return VBox object that contains round tracker
     */
    private VBox roundTrackerCreator(boolean hasClickProp) {
        //Initialize container
        VBox container = new VBox(0);

        //Initialize grid
        FXConstants.createEmptyGrid(container, "transparent", 2, 5,
                FXConstants.ROUNDT_INSETS_SPACING, FXConstants.ROUNDT_ROW_SPACING, FXConstants.ROUNDT_CELL_DIM_VALUE);


        for (int i = 0; i < RoundTracker.ROUNDS; i++) {
            Label label = new Label("Round: " + (i + 1));
            MenuButton menuButton = new MenuButton();

            //Set comboBox properties
            menuButton.setPrefSize(FXConstants.ROUNDT_CELL_DIM_VALUE, FXConstants.ROUNDT_CELL_DIM_VALUE);

            if (i < round && roundTracker[0][i] != null) {
                for (int j = 0; j < MAX_DIE_IN_ROUNDS; j++) {
                    if (roundTracker[j][i] != null) {
                        this.makeMenuItem(roundTracker[j][i], menuButton, hasClickProp, i, j);
                    }
                }
            } else {
                menuButton.setDisable(true);
            }

            //Add elements in correct row
            if (i > 4) {
                HBox row = (HBox) container.getChildren().get(1);
                VBox cell = (VBox) row.getChildren().get(i - 5);
                cell.getChildren().addAll(label, menuButton);

            } else {
                HBox row = (HBox) container.getChildren().get(0);
                VBox cell = (VBox) row.getChildren().get(i);
                cell.getChildren().addAll(label, menuButton);
            }
        }

        //Return
        return container;
    }

    /**
     * Creates menu's item
     *
     * @param die          contains the die to add in menu (roundTracker[dieIndex][roundIndex])
     * @param menuButton   contains menu
     * @param hasClickProp contains boolean value for item' click properties
     * @param roundIndex   contains round index
     * @param dieIndex     contains die index
     */
    private void makeMenuItem(IntColorPair die, MenuButton menuButton, boolean hasClickProp, int roundIndex, int dieIndex) {
        if (die != null) {
            Image image = (Image) dieViewCreator.makeDie(die);
            ImageView imageView = new ImageView(image);
            imageView.setFitHeight(FXConstants.ROUNDT_IMG_DIM_VALUE);
            imageView.setFitWidth(FXConstants.ROUNDT_IMG_DIM_VALUE);

            MenuItem item = new MenuItem();
            item.setGraphic(imageView);

            ImageView menuImageView = new ImageView(image);
            menuImageView.setFitHeight(FXConstants.ROUNDT_IMG_DIM_VALUE);
            menuImageView.setFitWidth(FXConstants.ROUNDT_IMG_DIM_VALUE);

            if (hasClickProp) {
                item.setOnAction(event -> {
                    menuButton.setGraphic(menuImageView);
                    boolean answer = ConfirmBox.display("Selezione Dado", "Vuoi selezionare il dado cliccato?");
                    if (answer) {
                        JavaFXStageProducer.getApp().getViewToolCardActions().selectedDieFromRoundTracker(roundIndex, dieIndex);
                    }
                    event.consume();
                });
            } else {
                item.setOnAction(event -> {
                    menuButton.setGraphic(menuImageView);
                    event.consume();
                });
            }

            menuButton.getItems().add(item);
            menuButton.setGraphic(menuImageView);
        }
    }
}
