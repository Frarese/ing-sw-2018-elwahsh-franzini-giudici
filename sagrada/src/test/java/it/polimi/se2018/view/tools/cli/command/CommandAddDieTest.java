package it.polimi.se2018.view.tools.cli.command;

import it.polimi.se2018.model.ColorModel;
import it.polimi.se2018.util.Pair;
import it.polimi.se2018.view.ViewActions;
import it.polimi.se2018.view.app.CLIApp;
import org.junit.Test;

import java.io.ByteArrayInputStream;

import static org.junit.Assert.assertEquals;

/**
 * Tests for CommandAddDie class
 *
 * @author Mathyas Giudici
 */

public class CommandAddDieTest extends AbsCommandTest {

    private class FakeViewAction extends ViewActions {
        private FakeViewAction(String ownerName) {
            super(ownerName);
        }

        @Override
        public void setDie(int reserveIndex, int height, int width) {
            assertEquals(0, reserveIndex);
            assertEquals(2, width);
            assertEquals(3, height);
        }
    }

    private class FakeApp extends CLIApp {
        private FakeApp() {
            super(new FakeViewAction(null), null, null);
        }
    }


    @Test
    public void testDoAction() {
        String input = "0" + enter + "2" + enter + "3" + enter;
        System.setIn(new ByteArrayInputStream(input.getBytes()));
        app = new FakeApp();

        Pair<Integer, ColorModel>[] fakeReserve = new Pair[1];
        fakeReserve[0] = new Pair<>(1, ColorModel.RED);
        app.getReserveViewCreator().setReserve(fakeReserve);

        Pair<Integer, ColorModel>[][] fakePattern = new Pair[1][2];
        Pair<Integer, ColorModel>[][] fakeGrid = new Pair[1][2];
        fakePattern = new Pair[1][2];
        fakePattern[0][0] = new Pair<>(1, ColorModel.RED);
        fakePattern[0][1] = new Pair<>(1, ColorModel.RED);
        fakeGrid[0][0] = new Pair<>(1, ColorModel.RED);
        fakeGrid[0][1] = new Pair<>(1, ColorModel.RED);
        app.getGridViewCreator().setGridPattern(fakePattern);
        app.getGridViewCreator().setGrid(fakeGrid);

        CommandAddDie commandAddDie = new CommandAddDie(app);
        commandAddDie.doAction();

        assertEquals("Seleziona un dado dalla riserva: ", savedStream.toString().split(enter)[0]);
        assertEquals("0) 1-RED", savedStream.toString().split(enter)[1]);
        // split(enter)[2] blank row
        assertEquals("Opzione: ", savedStream.toString().split(enter)[3]);
        assertEquals("Seleziona la posizione (la numerazione parte da 0)", savedStream.toString().split(enter)[4]);
    }
}