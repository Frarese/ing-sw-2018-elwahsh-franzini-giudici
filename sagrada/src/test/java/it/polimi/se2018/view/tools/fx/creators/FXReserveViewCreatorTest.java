package it.polimi.se2018.view.tools.fx.creators;

import it.polimi.se2018.model.ColorModel;
import it.polimi.se2018.util.Pair;
import javafx.embed.swing.JFXPanel;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.assertEquals;

/**
 * Tests for FXReserveViewCreator class
 *
 * @author Mathyas Giudici
 */

public class FXReserveViewCreatorTest {

    @Before
    public void initTest() {
        new JFXPanel();
    }

    @Test
    public void displayTest() {
        Pair<Integer, ColorModel>[] reserve = new Pair[9];
        reserve[0] = new Pair<>(1, ColorModel.RED);
        reserve[1] = new Pair<>(2, ColorModel.RED);
        reserve[2] = new Pair<>(3, ColorModel.RED);
        reserve[3] = new Pair<>(4, ColorModel.RED);
        reserve[4] = new Pair<>(5, ColorModel.RED);
        reserve[5] = new Pair<>(6, ColorModel.RED);
        reserve[6] = new Pair<>(1, ColorModel.BLUE);
        reserve[7] = new Pair<>(3, ColorModel.BLUE);
        reserve[8] = new Pair<>(6, ColorModel.BLUE);

        FXReserveViewCreator reserveViewCreator = new FXReserveViewCreator(reserve);
        VBox reserveView = reserveViewCreator.display();

        //First die
        HBox row = (HBox) reserveView.getChildren().get(0);
        VBox cell = (VBox) row.getChildren().get(0);

        Image aspect = new Image("/it/polimi/se2018/view/images/die/value_color/val1cRED.png", FXConstants.GRID_CELL_DIM_VALUE, FXConstants.GRID_CELL_DIM_VALUE, true, false);
        int error = this.dieCheck(((ImageView) cell.getChildren().get(0)).getImage(), aspect);

        assertEquals(0, error);

        //Second die
        row = (HBox) reserveView.getChildren().get(0);
        cell = (VBox) row.getChildren().get(1);

        aspect = new Image("/it/polimi/se2018/view/images/die/value_color/val2cRED.png", FXConstants.GRID_CELL_DIM_VALUE, FXConstants.GRID_CELL_DIM_VALUE, true, false);
        error = this.dieCheck(((ImageView) cell.getChildren().get(0)).getImage(), aspect);

        assertEquals(0, error);

        //Third die
        row = (HBox) reserveView.getChildren().get(0);
        cell = (VBox) row.getChildren().get(2);

        aspect = new Image("/it/polimi/se2018/view/images/die/value_color/val3cRED.png", FXConstants.GRID_CELL_DIM_VALUE, FXConstants.GRID_CELL_DIM_VALUE, true, false);
        error = this.dieCheck(((ImageView) cell.getChildren().get(0)).getImage(), aspect);

        assertEquals(0, error);

        //Fourth die
        row = (HBox) reserveView.getChildren().get(1);
        cell = (VBox) row.getChildren().get(0);

        aspect = new Image("/it/polimi/se2018/view/images/die/value_color/val4cRED.png", FXConstants.GRID_CELL_DIM_VALUE, FXConstants.GRID_CELL_DIM_VALUE, true, false);
        error = this.dieCheck(((ImageView) cell.getChildren().get(0)).getImage(), aspect);

        assertEquals(0, error);

        //Fifth die
        row = (HBox) reserveView.getChildren().get(1);
        cell = (VBox) row.getChildren().get(1);

        aspect = new Image("/it/polimi/se2018/view/images/die/value_color/val5cRED.png", FXConstants.GRID_CELL_DIM_VALUE, FXConstants.GRID_CELL_DIM_VALUE, true, false);
        error = this.dieCheck(((ImageView) cell.getChildren().get(0)).getImage(), aspect);

        assertEquals(0, error);

        //Sixth die
        row = (HBox) reserveView.getChildren().get(1);
        cell = (VBox) row.getChildren().get(2);

        aspect = new Image("/it/polimi/se2018/view/images/die/value_color/val6cRED.png", FXConstants.GRID_CELL_DIM_VALUE, FXConstants.GRID_CELL_DIM_VALUE, true, false);
        error = this.dieCheck(((ImageView) cell.getChildren().get(0)).getImage(), aspect);

        assertEquals(0, error);

        //Seventh die
        row = (HBox) reserveView.getChildren().get(2);
        cell = (VBox) row.getChildren().get(0);

        aspect = new Image("/it/polimi/se2018/view/images/die/value_color/val1cBLUE.png", FXConstants.GRID_CELL_DIM_VALUE, FXConstants.GRID_CELL_DIM_VALUE, true, false);
        error = this.dieCheck(((ImageView) cell.getChildren().get(0)).getImage(), aspect);

        assertEquals(0, error);

        //Eighth die
        row = (HBox) reserveView.getChildren().get(2);
        cell = (VBox) row.getChildren().get(1);

        aspect = new Image("/it/polimi/se2018/view/images/die/value_color/val3cBLUE.png", FXConstants.GRID_CELL_DIM_VALUE, FXConstants.GRID_CELL_DIM_VALUE, true, false);
        error = this.dieCheck(((ImageView) cell.getChildren().get(0)).getImage(), aspect);

        assertEquals(0, error);

        //Ninth die
        row = (HBox) reserveView.getChildren().get(2);
        cell = (VBox) row.getChildren().get(2);

        aspect = new Image("/it/polimi/se2018/view/images/die/value_color/val6cBLUE.png", FXConstants.GRID_CELL_DIM_VALUE, FXConstants.GRID_CELL_DIM_VALUE, true, false);
        error = this.dieCheck(((ImageView) cell.getChildren().get(0)).getImage(), aspect);

        assertEquals(0, error);
    }

    @Test
    public void pickDieTest() {
        Pair<Integer, ColorModel>[] reserve = new Pair[1];
        reserve[0] = new Pair<>(1, ColorModel.RED);

        FXReserveViewCreator reserveViewCreator = new FXReserveViewCreator(reserve);

        Image aspect = new Image("/it/polimi/se2018/view/images/die/value_color/val1cRED.png", FXConstants.GRID_CELL_DIM_VALUE, FXConstants.GRID_CELL_DIM_VALUE, true, false);
        int error = this.dieCheck(reserveViewCreator.pickDie(0), aspect);

        assertEquals(0, error);
    }

    private int dieCheck(Image image, Image aspect) {
        int error = 0;

        for (int x = 0; x < (int) image.getWidth(); x++) {
            for (int y = 0; y < (int) image.getHeight(); y++) {
                if (image.getPixelReader().getArgb(x, y) != aspect.getPixelReader().getArgb(x, y)) {
                    error++;
                }
            }
        }

        return error;
    }
}