package it.polimi.se2018.view.app;

import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.assertNull;

public class AppTest {

    private CLIApp cliApp;

    @Before
    public void testInit(){
        cliApp = new CLIApp(null,null,null);
    }

    @Test
    public void testSearchPlayerViewByName() {
        assertNull(this.cliApp.searchPlayerViewByName(null,"Test"));
    }

    @Test
    public void testSearchPlayerViewById() {
        assertNull(this.cliApp.searchPlayerViewById(null,0));
    }

    @Test
    public void testGetGridViewCreator() {
    }

    @Test
    public void testGetInvites() {
    }

    @Test
    public void testGetLeaderBoard() {

    }
}
