package it.polimi.se2018.view.tools.fx.creators;

import it.polimi.se2018.model.IntColorPair;
import it.polimi.se2018.model.dice.RoundTracker;
import it.polimi.se2018.view.app.JavaFXStageProducer;
import it.polimi.se2018.view.tools.RoundTrackerViewCreator;
import it.polimi.se2018.view.tools.fx.alert.ConfirmBox;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.util.Callback;

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
    public FXRoundTrackerViewCreator(int round, IntColorPair[][] roundTracker) {
        super(round, roundTracker);
        this.dieViewCreator = new FXDieViewCreator(FXConstants.ROUNDT_IMG_DIM_VALUE);
    }

    @Override
    public VBox display() {
        return roundTrackerCreator(false);
    }


    public VBox displayWithClick() {
        return roundTrackerCreator(true);
    }

    private VBox roundTrackerCreator(boolean hasClickProp) {
        //Initialize container
        VBox container = new VBox(0);

        //Initialize grid
        FXConstants.createEmptyGrid(container, "transparent", 2, 5,
                FXConstants.ROUNDT_INSETS_SPACING, FXConstants.ROUNDT_ROW_SPACING, FXConstants.ROUNDT_CELL_DIM_VALUE);


        for (int i = 0; i < RoundTracker.ROUNDS; i++) {
            Label label = new Label("Round: " + (i + 1));
            ComboBox<ImageView> comboBox = new ComboBox<>();

            //Set comboBox properties
            comboBox.setPrefSize(FXConstants.ROUNDT_CELL_DIM_VALUE, FXConstants.ROUNDT_CELL_DIM_VALUE);

            if (i < round && roundTracker[0][i] != null) {
                for (int j = 0; j < MAX_DIE_IN_ROUNDS; j++) {
                    if (roundTracker[j][i] != null) {
                        this.makeCellDie(roundTracker[j][i], comboBox, hasClickProp, i, j);
                    }
                }
            } else {
                comboBox.setDisable(true);
            }

            this.fixComboBoxNodes(comboBox);

            //Add elements in correct row
            if (i > 4) {
                HBox row = (HBox) container.getChildren().get(1);
                VBox cell = (VBox) row.getChildren().get(i - 5);
                cell.getChildren().addAll(label, comboBox);

            } else {
                HBox row = (HBox) container.getChildren().get(0);
                VBox cell = (VBox) row.getChildren().get(i);
                cell.getChildren().addAll(label, comboBox);
            }
        }

        //Return
        return container;
    }


    /**
     * Inserting Nodes into the ComboBox items list and manage items disappearing
     * https://docs.oracle.com/javase/8/javafx/api/javafx/scene/control/ComboBox.html
     *
     * @param comboBox contains the ComboBox's class reference that must be fixed
     */
    private void fixComboBoxNodes(ComboBox<ImageView> comboBox) {
        comboBox.setCellFactory(new Callback<ListView<ImageView>, ListCell<ImageView>>() {
            @Override
            public ListCell<ImageView> call(ListView<ImageView> param) {
                return new ListCell<ImageView>() {
                    private ImageView imageView;

                    void initialize() {
                        setContentDisplay(ContentDisplay.GRAPHIC_ONLY);
                        imageView = new ImageView();
                    }

                    @Override
                    protected void updateItem(ImageView item, boolean empty) {
                        initialize();
                        super.updateItem(item, empty);

                        if (item == null || empty) {
                            setGraphic(null);
                        } else {
                            imageView.setImage(item.getImage());
                            imageView.setFitHeight(FXConstants.ROUNDT_IMG_DIM_VALUE);
                            imageView.setFitWidth(FXConstants.ROUNDT_IMG_DIM_VALUE);
                            setGraphic(imageView);
                        }
                    }
                };
            }
        });
    }

    private void makeCellDie(IntColorPair die, ComboBox<ImageView> comboBox, boolean hasClickProp, int roundIndex, int dieIndex) {
        if (die != null) {
            Image image = (Image) dieViewCreator.makeDie(die);
            ImageView imageView = new ImageView(image);
            imageView.setFitHeight(FXConstants.ROUNDT_IMG_DIM_VALUE);
            imageView.setFitWidth(FXConstants.ROUNDT_IMG_DIM_VALUE);

            if (hasClickProp) {
                imageView.setOnMouseClicked(clickEvent -> {
                    boolean answer = ConfirmBox.display("Selezione Dado", "Vuoi selezionare il dado cliccato?");
                    if (answer) {
                        JavaFXStageProducer.getApp().getViewToolCardActions().selectedDieFromRoundTracker(roundIndex, dieIndex);
                    }
                    clickEvent.consume();
                });
            }

            comboBox.getItems().add(imageView);
            comboBox.setValue(imageView);
        }
    }
}
