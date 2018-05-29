package it.polimi.se2018.view.tools.fx.creators;

import it.polimi.se2018.model.ColorModel;
import it.polimi.se2018.util.Pair;
import javafx.embed.swing.JFXPanel;
import javafx.scene.control.ComboBox;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.assertEquals;

/**
 * Tests for FXRoundTrackerViewCreator class
 *
 * @author Mathyas Giudici
 */

public class FXRoundTrackerViewCreatorTest {

    @Before
    public void initTest() {
        new JFXPanel();
    }

    @Test
    public void testDisplay() {
        Pair<Integer, ColorModel>[][] roundT = new Pair[10][2];
        roundT[0][0] = new Pair<>(1,ColorModel.RED);
        roundT[0][1] = new Pair<>(1,ColorModel.BLUE);
        roundT[1][0] = new Pair<>(5,ColorModel.RED);

        FXRoundTrackerViewCreator roundTrackerViewCreator = new FXRoundTrackerViewCreator(3,roundT);

        HBox row = (HBox) roundTrackerViewCreator.display().getChildren().get(0);
        VBox cell = (VBox) row.getChildren().get(0);

        ComboBox<ImageView> comboBox = (ComboBox<ImageView>) cell.getChildren().get(1);

        Image aspect = new Image("/it/polimi/se2018/view/images/die/value_color/val1cRED.png", FXConstants.ROUNDT_IMG_DIM_VALUE, FXConstants.ROUNDT_IMG_DIM_VALUE, true, false);
        int error = this.dieCheck(comboBox.getItems().get(0).getImage(), aspect);

        assertEquals(0, error);


        aspect = new Image("/it/polimi/se2018/view/images/die/value_color/val1cBLUE.png", FXConstants.ROUNDT_IMG_DIM_VALUE, FXConstants.ROUNDT_IMG_DIM_VALUE, true, false);
        error = this.dieCheck(comboBox.getItems().get(1).getImage(), aspect);

        assertEquals(0, error);

        row = (HBox) roundTrackerViewCreator.display().getChildren().get(0);
        cell = (VBox) row.getChildren().get(1);

        comboBox = (ComboBox<ImageView>) cell.getChildren().get(1);

        aspect = new Image("/it/polimi/se2018/view/images/die/value_color/val5cRED.png", FXConstants.ROUNDT_IMG_DIM_VALUE, FXConstants.ROUNDT_IMG_DIM_VALUE, true, false);
        error = this.dieCheck(comboBox.getItems().get(0).getImage(), aspect);

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