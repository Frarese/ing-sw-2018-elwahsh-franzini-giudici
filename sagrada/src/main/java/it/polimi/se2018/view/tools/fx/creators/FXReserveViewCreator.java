package it.polimi.se2018.view.tools.fx.creators;

import it.polimi.se2018.model.ColorModel;
import it.polimi.se2018.util.Pair;
import it.polimi.se2018.view.tools.ReserveViewCreator;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;

/**
 * Class to create and to handle reserve in GUI
 *
 * @author Mathyas Giudici
 */

public class FXReserveViewCreator extends ReserveViewCreator<VBox, Image> {

    /**
     * Class constructor
     *
     * @param reserve contains the reserve
     */
    public FXReserveViewCreator(Pair<Integer, ColorModel>[] reserve) {
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

        //Return
        return container;
    }

    @Override
    public Image pickDie(int index) {
        return (Image) dieViewCreator.makeDie(reserve[index]);
    }
}
