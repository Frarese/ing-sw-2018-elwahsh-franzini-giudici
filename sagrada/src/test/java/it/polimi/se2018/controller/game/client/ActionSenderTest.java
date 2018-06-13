package it.polimi.se2018.controller.game.client;

import it.polimi.se2018.controller.network.client.CommFE;
import it.polimi.se2018.controller.network.client.CommUtilizer;
import it.polimi.se2018.util.MatchIdentifier;
import org.junit.Before;
import org.junit.Test;

import java.io.Serializable;
import java.lang.reflect.Field;

import static org.junit.Assert.*;

public class ActionSenderTest {
    private ActionSender uut;
    private boolean fail;
    private boolean logout;
    private boolean changed;
    private boolean left;
    private String requested;
    private boolean sent;
    private boolean joined;

    @Before
    public void testSetUp() throws Exception{
        uut=new ActionSender();
        Field commF=ActionSender.class.getDeclaredField("network");
        commF.setAccessible(true);
        commF.set(uut,new CommMock());
        fail=false;
        logout=false;
        changed=false;
        left=false;
        requested=null;
        sent=false;
        joined=false;
        uut.setController(new ControllerMock());
    }

    @Test
    public void testInit() {
        assertNull(uut.login(null,0,0,null,null,false,false));
        fail=true;
        assertNotNull(uut.login(null,0,0,null,null,false,false));

    }

    @Test
    public void testBasics() {
        uut.logout();
        assertTrue(logout);

        uut.changeLayer(false,0,0);
        assertTrue(changed);

        uut.leaveMatch();
        assertTrue(left);

        uut.askLobby();
        assertEquals("user:lead",requested);

        changed=false;
        uut.pushInvite(null);
        assertTrue(changed);

        changed=false;
        uut.acceptInvite(null);
        assertTrue(changed);

        uut.selectedPattern(null);
        assertTrue(sent);

        sent=false;
        uut.endInitGame();
        assertTrue(sent);

        uut.joinMatchMaking();
        assertTrue(joined);
        uut.leaveMatchMaking();
        assertFalse(joined);

        sent=false;
        uut.passTurn();
        assertTrue(sent);

        sent=false;
        uut.userToolCard(0);
        assertTrue(sent);

        sent=false;
        uut.setDie(0,0,0);
        assertTrue(sent);

        sent=false;
        uut.selectedDieFromRoundTrack(0,0);
        assertTrue(sent);

        sent=false;
        uut.selectedDieToGrid(0,0);
        assertTrue(sent);

        sent=false;
        uut.selectedDieFromGrid(0,0);
        assertTrue(sent);

        sent=false;
        uut.selectedNewValueForDie(0);
        assertTrue(sent);

        sent=false;
        uut.selectedDieFromReserve(0);
        assertTrue(sent);
    }

    private class CommMock extends CommFE{
        @Override
        public String login(String host, int requestPort, int objectPort, boolean isRecovery, String usn, String pw, boolean newUser, boolean useRMI, CommUtilizer utilizer) {
            return fail?"hello":null;
        }

        @Override
        public boolean logout() {
            logout=true;
            return true;
        }

        @Override
        public void changeLayer(boolean toRMI, int reqPort, int objPort) {
            changed=true;
        }

        @Override
        public void leaveMatch() {
            left=true;
        }

        @Override
        public void requestLeaderboard() {
            requested=requested+":lead";
        }

        @Override
        public void requestUserList() {
            requested="user";
        }

        @Override
        public void inviteToMatch(MatchIdentifier match) {

        }

        @Override
        public void answerInvite(MatchIdentifier match, boolean accepted) {

        }

        @Override
        public void sendReq(Serializable req) {
            sent=true;
        }

        @Override
        public void sendObj(Serializable obj) {
            sent=true;
        }

        @Override
        public void joinMatchMakingQueue() {
            joined=true;
        }

        @Override
        public void leaveMatchMakingQueue() {
            joined=false;
        }
    }

    private class ControllerMock extends ClientController{

        ControllerMock() {
            super(null);
        }

        @Override
        public void setMId(MatchIdentifier id) {
            changed=true;
        }
    }
}