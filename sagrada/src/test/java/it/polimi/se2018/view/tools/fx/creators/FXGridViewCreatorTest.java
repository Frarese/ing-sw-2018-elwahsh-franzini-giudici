package it.polimi.se2018.view.tools.fx.creators;

import it.polimi.se2018.model.ColorModel;
import it.polimi.se2018.model.IntColorPair;
import it.polimi.se2018.view.tools.DieViewCreator;
import it.polimi.se2018.view.tools.GridViewCreator;
import org.junit.Before;
import org.junit.Test;

import java.lang.reflect.Field;
import java.util.ArrayList;

import static org.junit.Assert.assertEquals;

/**
 * Tests for FXGridViewCreator class
 *
 * @author Mathyas Giudici
 */

public class FXGridViewCreatorTest {
    private ArrayList<IntColorPair> hitList;
    private Field dieCreatorF;

    @Before
    public void initTest() throws Exception{
        hitList=new ArrayList<>();
        dieCreatorF=GridViewCreator.class.getDeclaredField("dieViewCreator");
        dieCreatorF.setAccessible(true);
    }

    @SuppressWarnings("unchecked")
    @Test
    public void testDisplay() throws Exception{
        IntColorPair[][] pattern = new IntColorPair[2][3];
        pattern[0][0] = new IntColorPair(1, ColorModel.WHITE);
        pattern[0][1] = new IntColorPair(0, ColorModel.RED);
        pattern[0][2] = new IntColorPair(0, ColorModel.RED);

        IntColorPair[][] grid = new IntColorPair[2][3];
        grid[1][0] = new IntColorPair(1, ColorModel.BLUE);
        grid[1][1] = new IntColorPair(6, ColorModel.VIOLET);

        FXGridViewCreator gridViewCreator = new FXGridViewCreator(grid,pattern,"BLACK");
        dieCreatorF.set(gridViewCreator,new DieViewCreatorMock());
        gridViewCreator.display();

        assertEquals(grid[1][0],hitList.get(0));

        assertEquals(grid[1][1],hitList.get(1));


    }

    @Test(expected = IllegalStateException.class)
    public void testAddADie() {
        FXGridViewCreator gridViewCreator = new FXGridViewCreator(null,null,"BLACK");
        gridViewCreator.addADie(null,1,1);
    }

    @SuppressWarnings("unchecked")
    @Test
    public void testPickDie() throws Exception{
        IntColorPair[][] grid = new IntColorPair[2][3];
        grid[1][0] = new IntColorPair(1, ColorModel.BLUE);

        FXGridViewCreator gridViewCreator = new FXGridViewCreator(grid,null,"BLACK");
        dieCreatorF.set(gridViewCreator,new DieViewCreatorMock());

        gridViewCreator.pickDie(1,0);
        assertEquals(grid[1][0],hitList.get(0));

    }

    private class DieViewCreatorMock implements DieViewCreator {


        @Override
        public Object makeDie(IntColorPair die) {
            hitList.add(die);
            return null;
        }
    }
}