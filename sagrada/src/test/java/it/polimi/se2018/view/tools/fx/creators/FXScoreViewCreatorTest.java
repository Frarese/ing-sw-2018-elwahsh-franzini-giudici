package it.polimi.se2018.view.tools.fx.creators;

import it.polimi.se2018.util.Pair;
import it.polimi.se2018.view.tools.ScoreViewCreator;
import org.junit.Test;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.List;

import static org.junit.Assert.assertNotNull;

public class FXScoreViewCreatorTest {

    /**
     * Tests correct FX's score creation
     * @throws Exception if an error occurs
     */
    @SuppressWarnings("unchecked")
    @Test
    public void testScores() throws Exception {
        FXScoreViewCreator uut = new FXScoreViewCreator();
        Method method = FXScoreViewCreator.class.getDeclaredMethod("getScores");
        method.setAccessible(true);

        Field scoreF = ScoreViewCreator.class.getDeclaredField("scores");
        scoreF.setAccessible(true);
        List<Pair<String, Integer>> list = (List) scoreF.get(uut);
        list.add(new Pair<>("test", 1));
        assertNotNull(method.invoke(uut));
    }
}