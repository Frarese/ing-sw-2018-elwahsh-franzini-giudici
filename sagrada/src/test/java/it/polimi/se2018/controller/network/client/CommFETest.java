package it.polimi.se2018.controller.network.client;

import it.polimi.se2018.controller.network.*;
import it.polimi.se2018.util.MatchIdentifier;
import it.polimi.se2018.util.MessageTypes;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.io.Serializable;
import java.lang.reflect.Field;

import static org.junit.Assert.*;

public class CommFETest {
    private CommFE uut;
    private String obj;

    @Before
    public void setUp() throws Exception {
        uut=new CommFE();
        Field field = CommFE.class.getDeclaredField("comm");
        field.setAccessible(true);
        field.set(uut, new CommMockup());
        obj=null;
    }

    @After
    public void tearDown(){
        obj=null;
    }

    @Test
    public void testLogin() {
        assertEquals("test"
                ,uut.login(null,0,0,false,null,null,false,false,null));
    }

    @Test
    public void testLogout() {
        assertTrue(uut.logout());
    }

    @Test
    public void testSendObj() {
        uut.sendObj("test");
        assertEquals("test",obj);
    }

    @Test
    public void testSendReq() {
        uut.sendReq("test");
        assertEquals("test",obj);
    }

    @Test
    public void testChangeLayer() {
        uut.changeLayer(false,0,0);

        assertEquals("false:0:0",obj);

        uut.changeLayer(true,1,-1);
    }

    @Test
    public void testAnswerInvite() {
        uut.answerInvite(new MatchIdentifier("a","c","d","e"),false);
        assertEquals("a:c:d:e:false",obj);
    }

    @Test
    public void testInviteToMatch() {
        uut.inviteToMatch(new MatchIdentifier("a","c","d","e"));
        assertEquals("a:c:d:e",obj);
    }



    @Test
    public void testRequestLeaderboard() {
        uut.requestLeaderboard();
        assertEquals("Leaderboard",obj);
    }

    @Test
    public void testRequestUserList() {
        uut.requestUserList();
        assertEquals("List",obj);
    }

    @Test
    public void testSendChatMessage() {
        uut.sendChatMessage("testmsg",MessageTypes.BROADCAST,"testdst");
        assertEquals("testmsg:BROADCAST:testdst",obj);
    }

    @Test
    public void testLeaveMatch() {
        uut.leaveMatch();
        assertEquals("test",obj);
    }

    @Test
    public void testJoinMatchMakingQueue() {
        uut.joinMatchMakingQueue();
        assertEquals("true",obj);
    }

    @Test
    public void testLeaveMatchMakingQueue() {
        uut.leaveMatchMakingQueue();
        assertEquals("false",obj);
    }

    private class CommMockup extends Comm{
        @Override
        public String login(String host, int requestPort, int objectPort, String usn, String pw, boolean newUser, boolean useRMI, CommUtilizer utilizer) {
            return "test";
        }

        @Override
        public boolean logout(){
            return true;
        }

        @Override
        public void pushOutObj(Serializable o){
            obj=(String)o;
        }

        @Override
        public void pushOutReq(AbsReq r){
            if(r.getClass().equals(ClientRequest.class)){
                ClientRequest cr=(ClientRequest)r;
                obj= (cr.serializedReq)+"";
            }
            else if(r.getClass().equals(MatchInviteRequest.class)){
                MatchInviteRequest ir=(MatchInviteRequest)r;
                obj=ir.matchId.toString();
            }
            else if(r.getClass().equals(MatchResponseRequest.class)){
                MatchResponseRequest ir=(MatchResponseRequest) r;
                obj=ir.matchId.toString()+":"+ir.accept;
            }
            else if(r.getClass().equals(GetLeaderBoardRequest.class)){
                obj="Leaderboard";
            }
            else if(r.getClass().equals(ListUsersRequest.class)){
                obj="List";
            }
            else if(r.getClass().equals(MatchmakingRequest.class)){
                obj=((MatchmakingRequest)r).join+"";
            }
            else if(r.getClass().equals(MatchAbortedRequest.class)){
                obj=((MatchAbortedRequest)r).matchId.toString();
            }
            else if(r.getClass().equals(MatchEndedRequest.class)){
                MatchEndedRequest ri=(MatchEndedRequest)r;
                obj=ri.playerScore0+":"
                        +ri.playerScore1+":"
                        +ri.playerScore2+":"
                        +ri.playerScore3;
            }
            else if(r.getClass().equals(LeaveMatchRequest.class)){
                obj=((LeaveMatchRequest)r).usn;
            }
            else if(r.getClass().equals(ChatMessageRequest.class)){
                ChatMessageRequest ri=(ChatMessageRequest) r;
                obj=ri.msg+":"
                        +ri.type+":"
                        +ri.destination;
            }
        }

        @Override
        public void changeLayer(boolean toRMI, int reqPort,int objPort){
            if(toRMI&& objPort<0)throw new IllegalArgumentException();
            obj=toRMI+":"+reqPort+":"+objPort;
        }

        @Override
        public MatchIdentifier getMatchInfo(){
            return new MatchIdentifier("a","c","d","e");
        }

        @Override
        public String getUsername(){
            return "test";
        }
    }
}