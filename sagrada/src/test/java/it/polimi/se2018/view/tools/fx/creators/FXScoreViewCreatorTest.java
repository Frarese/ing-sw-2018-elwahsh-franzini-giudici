package it.polimi.se2018.view.tools.fx.creators;

import it.polimi.se2018.util.Pair;
import it.polimi.se2018.view.tools.ScoreViewCreator;
import org.junit.Test;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.List;

import static org.junit.Assert.*;

public class FXScoreViewCreatorTest {

    @SuppressWarnings("unchecked")
    @Test
    public void testScores() throws Exception {
        FXScoreViewCreator uut=new FXScoreViewCreator();
        Method m=FXScoreViewCreator.class.getDeclaredMethod("getScores");
        m.setAccessible(true);

        Field scoreF=ScoreViewCreator.class.getDeclaredField("scores");
        scoreF.setAccessible(true);
        List<Pair<String,Integer>> list=(List)scoreF.get(uut);
        list.add(new Pair<>("test",1));
        assertNotNull(m.invoke(uut));

    }
}