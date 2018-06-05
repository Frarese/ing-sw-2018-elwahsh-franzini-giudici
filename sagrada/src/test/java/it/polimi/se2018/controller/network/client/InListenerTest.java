package it.polimi.se2018.controller.network.client;

import it.polimi.se2018.controller.network.AbsReq;
import it.polimi.se2018.controller.network.server.ServerVisitor;
import it.polimi.se2018.util.MatchIdentifier;
import it.polimi.se2018.util.MessageTypes;
import org.junit.Before;
import org.junit.Test;

import java.io.Serializable;
import java.util.List;

import static org.junit.Assert.*;

public class InListenerTest {
    private String receivedObj;
    private CommUtilizer testUt;
    private Comm testComm;
    private boolean dc;
    private boolean fail;
    @Before
    public void setUp() {
        testUt=new TestUtilizer();
        testComm=new TestCComm();
        receivedObj=null;
        dc=false;
        fail=false;
    }

    @Test
    public void testObjectHandle() throws Exception{

        InListener inL=new InListener(testComm,testUt,false);
        inL.methodToCall();
        assertEquals("Test",receivedObj);
    }

    @Test
    public void testReqHandle() throws Exception{

        InListener inL=new InListener(testComm,testUt,true);
        inL.methodToCall();
        assertEquals("TestReq",receivedObj);
    }

    @Test
    public void testExceptionHandle() throws Exception{
        fail=true;
        InListener inL=new InListener(testComm,testUt,false);
        inL.methodToCall();
    }

    @Test
    public void testDCHandle() {

        InListener inL=new InListener(testComm,testUt,true);
        inL.notifyDisconnect();
        assertTrue(dc);
    }

    @Test
    public void testSetter() throws Exception{

        InListener inL=new InListener(testComm,null,true);
        inL.setUtilizer(testUt);
        inL.methodToCall();
        assertEquals("TestReq",receivedObj);
    }

    private class TestUtilizer implements CommUtilizer{

        @Override
        public void receiveObject(Serializable obj) {
            if(fail)throw new IllegalArgumentException("Test");
            receivedObj=(String)obj;
        }

        @Override
        public void receiveRequest(Serializable req) {

        }

        @Override
        public void abortMatch() {

        }

        @Override
        public void notifyInvite(MatchIdentifier match) {

        }

        @Override
        public void notifyMatchEnd(int playerScore0, int playerScore1, int playerScore2, int playerScore3) {

        }

        @Override
        public void notifyMatchStart() {

        }

        @Override
        public void notifyUserLeft(String usn) {

        }

        @Override
        public void pushLeaderboard(List list) {

        }

        @Override
        public void pushUserList(List list) {

        }

        @Override
        public void notifyCommDropped() {
            dc=true;
        }

        @Override
        public void pushChatMessage(String msg, MessageTypes type, String source) {

        }

        @Override
        public void notifyReconnect() {

        }

        @Override
        public void notifyUserReconnected(String usn) {

        }
    }
    private class TestCComm extends Comm{
        @Override
        public Serializable popInPendingObj(){
            return "Test";
        }
        @Override
        public AbsReq popInPendingReq(){
            return new TestAbsReq();
        }
    }

    private class TestAbsReq implements AbsReq{
        @Override
        public void serverVisit(ServerVisitor sV) {

        }

        @Override
        public void clientHandle(Comm clientComm, CommUtilizer commUtilizer){
            commUtilizer.receiveObject("TestReq");
        }

        @Override
        public boolean checkValid() {
            return false;
        }
    }
}