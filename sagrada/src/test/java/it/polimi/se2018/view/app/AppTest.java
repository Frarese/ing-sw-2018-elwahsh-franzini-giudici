package it.polimi.se2018.view.app;

import it.polimi.se2018.util.MatchIdentifier;
import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.assertArrayEquals;
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
    public void testGetInvites() {
        List<MatchIdentifier> invites = new ArrayList<>();
        invites.add(new MatchIdentifier("test1","test2",null,null));
        invites.add(new MatchIdentifier("test4","test3",null,null));


        cliApp.pullInvitate(new MatchIdentifier("test1","test2",null,null));
        cliApp.pullInvitate(new MatchIdentifier("test4","test3",null,null));

        assertArrayEquals(invites.toArray(), cliApp.getInvites().toArray());
    }
}
