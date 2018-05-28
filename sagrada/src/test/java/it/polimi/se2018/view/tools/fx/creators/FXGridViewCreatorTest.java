package it.polimi.se2018.view.tools.fx.creators;

import it.polimi.se2018.model.ColorModel;
import it.polimi.se2018.util.Pair;
import it.polimi.se2018.view.tools.fx.FXConstants;
import javafx.embed.swing.JFXPanel;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.assertEquals;

/**
 * Tests for FXGridViewCreator class
 *
 * @author Mathyas Giudici
 */

public class FXGridViewCreatorTest {

    @Before
    public void initTest(){
        new JFXPanel();
    }

    @Test
    public void displayTest() {
        Pair<Integer, ColorModel>[][] pattern = new Pair[2][3];
        pattern[0][0] = new Pair<>(1, ColorModel.WHITE);
        pattern[0][1] = new Pair<>(0, ColorModel.RED);
        pattern[0][2] = new Pair<>(0, ColorModel.RED);

        Pair<Integer, ColorModel>[][] grid = new Pair[2][3];
        grid[1][0] = new Pair<>(1, ColorModel.BLUE);
        grid[1][1] = new Pair<>(6, ColorModel.VIOLET);

        FXGridViewCreator gridViewCreator = new FXGridViewCreator(grid,pattern,"BLACK");
        VBox viewGrid = gridViewCreator.display();

        HBox row = (HBox) viewGrid.getChildren().get(1);
        VBox cell = (VBox) row.getChildren().get(0);

        Image aspect = new Image("/it/polimi/se2018/view/images/die/value_color/val1cBLUE.png",FXConstants.GRID_CELL_DIM_VALUE,FXConstants.GRID_CELL_DIM_VALUE,true,false);
        int error = this.dieCheck(((ImageView) cell.getChildren().get(0)).getImage(),aspect);

        assertEquals(0,error);

        row = (HBox) viewGrid.getChildren().get(1);
        cell = (VBox) row.getChildren().get(1);

        aspect = new Image("/it/polimi/se2018/view/images/die/value_color/val6cVIOLET.png",FXConstants.GRID_CELL_DIM_VALUE,FXConstants.GRID_CELL_DIM_VALUE,true,false);
        error = this.dieCheck(((ImageView) cell.getChildren().get(0)).getImage(),aspect);

        assertEquals(0,error);

    }

    @Test
    public void addADieTest() {
        FXGridViewCreator gridViewCreator = new FXGridViewCreator(null,null,"BLACK");
        Image die = new Image("/it/polimi/se2018/view/images/die/value_color/val1cBLUE.png",FXConstants.GRID_CELL_DIM_VALUE,FXConstants.GRID_CELL_DIM_VALUE,true,false);
        gridViewCreator.addADie(die,1,1);

        //TODO
    }

    @Test
    public void pickDieTest() {
        Pair<Integer, ColorModel>[][] grid = new Pair[2][3];
        grid[1][0] = new Pair<>(1, ColorModel.BLUE);

        FXGridViewCreator gridViewCreator = new FXGridViewCreator(grid,null,"BLACK");

        Image aspect = new Image("/it/polimi/se2018/view/images/die/value_color/val1cBLUE.png",FXConstants.GRID_CELL_DIM_VALUE,FXConstants.GRID_CELL_DIM_VALUE,true,false);
        int error = this.dieCheck(gridViewCreator.pickDie(1,0),aspect);

        assertEquals(0,error);
    }

    private int dieCheck(Image image, Image aspect){
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