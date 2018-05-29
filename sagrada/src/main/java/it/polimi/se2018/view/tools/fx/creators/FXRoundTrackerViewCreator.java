package it.polimi.se2018.view.tools.fx.creators;

import it.polimi.se2018.model.ColorModel;
import it.polimi.se2018.util.Pair;
import it.polimi.se2018.view.tools.RoundTrackerViewCreator;
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
     * Class constructor
     *
     * @param round        contains the round
     * @param roundTracker contains the round tracker
     */
    public FXRoundTrackerViewCreator(int round, Pair<Integer, ColorModel>[][] roundTracker) {
        super(round, roundTracker);
        this.dieViewCreator = new FXDieViewCreator(FXConstants.ROUNDT_IMG_DIM_VALUE);
    }

    @Override
    public VBox display() {
        //Initialize container
        VBox container = new VBox(0);

        //Initialize grid
        FXConstants.createEmptyGrid(container, "WHITE", 2, 5,
                FXConstants.ROUNDT_INSETS_SPACING, FXConstants.ROUNDT_ROW_SPACING, FXConstants.ROUNDT_CELL_DIM_VALUE);

        for (int i = 0; i < roundTracker.length; i++) {
            //Create round tracker elements
            Label label = new Label("Round: " + i);
            ComboBox<ImageView> comboBox = new ComboBox<>();

            //Set comboBox properties
            comboBox.setPrefSize(FXConstants.ROUNDT_CELL_DIM_VALUE, FXConstants.ROUNDT_CELL_DIM_VALUE);

            //Create die in comboBox
            if (i < round && roundTracker[i] != null) {
                for (int j = 0; j < roundTracker[i].length; j++) {
                    this.makeCellDie(roundTracker[i][j],comboBox);
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

                    public void initialize() {
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

    private void makeCellDie(Pair<Integer,ColorModel> die, ComboBox<ImageView> comboBox){
        if (die != null) {
            Image image = (Image) dieViewCreator.makeDie(die);
            ImageView imageView = new ImageView(image);
            imageView.setFitHeight(FXConstants.ROUNDT_IMG_DIM_VALUE);
            imageView.setFitWidth(FXConstants.ROUNDT_IMG_DIM_VALUE);
            comboBox.getItems().add(imageView);
            comboBox.setValue(imageView);
        }
    }
}
