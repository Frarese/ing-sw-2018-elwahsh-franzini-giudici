package it.polimi.se2018.view.tools.fx.creators;

import it.polimi.se2018.model.ColorModel;
import it.polimi.se2018.util.Pair;
import it.polimi.se2018.view.tools.DieViewCreator;
import it.polimi.se2018.view.tools.ReserveViewCreator;
import org.junit.Before;
import org.junit.Test;

import java.lang.reflect.Field;
import java.util.*;

import static org.junit.Assert.assertEquals;

/**
 * Tests for FXReserveViewCreator class
 *
 * @author Mathyas Giudici
 */

public class FXReserveViewCreatorTest {
    private ArrayList<Pair> hitList;
    private Field dieCreatorF;

    @Before
    public void initTest() throws Exception{
        hitList=new ArrayList<>();
        dieCreatorF=ReserveViewCreator.class.getDeclaredField("dieViewCreator");
        dieCreatorF.setAccessible(true);
    }

    @SuppressWarnings("unchecked")
    @Test
    public void testDisplay() throws Exception{
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

        List<Pair> pairList = Arrays.asList(reserve);

        FXReserveViewCreator reserveViewCreator = new FXReserveViewCreator(reserve);

        dieCreatorF.set(reserveViewCreator,new ReserveViewCreatorMock());

        reserveViewCreator.display();

        for(int i=0;i<9;i++){
            assertEquals(hitList.get(i),pairList.get(i));
        }
    }

    @SuppressWarnings("unchecked")
    @Test
    public void testPickDie() throws Exception{
        Pair<Integer, ColorModel>[] reserve = new Pair[1];
        reserve[0] = new Pair<>(1, ColorModel.RED);

        FXReserveViewCreator reserveViewCreator = new FXReserveViewCreator(reserve);

        dieCreatorF.set(reserveViewCreator,new ReserveViewCreatorMock());

        reserveViewCreator.pickDie(0);
        assertEquals(hitList.get(0),reserve[0]);

    }


    private class ReserveViewCreatorMock implements DieViewCreator {


        @Override
        public Object makeDie(Pair die) {
            hitList.add(die);
            return null;
        }
    }
}