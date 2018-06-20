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

/**
 * Tester class for the client inbound listener
 * @author Francesco Franzini
 */
public class InListenerTest {
    private String receivedObj;
    private CommUtilizer testUt;
    private Comm testComm;
    private boolean dc;
    private boolean fail;

    /**
     * Initializes the flags and Objects used
     */
    @Before
    public void testSetUp() {
        testUt=new TestUtilizer();
        testComm=new TestCComm();
        receivedObj=null;
        dc=false;
        fail=false;
    }

    /**
     * Tests if the handler correctly pushes an object
     * @throws Exception if an error occurs
     */
    @Test
    public void testObjectHandle() throws Exception{
        InListener inL=new InListener(testComm,testUt,false);
        inL.methodToCall();
        assertEquals("Test",receivedObj);
    }

    /**
     * Tests if the handler correctly pushes a request
     * @throws Exception if an error occurs
     */
    @Test
    public void testReqHandle() throws Exception{
        InListener inL=new InListener(testComm,testUt,true);
        inL.methodToCall();
        assertEquals("TestReq",receivedObj);
    }

    /**
     * Tests if the handler correctly handles an exception thrown from the utilizer
     * @throws Exception if an error occurs
     */
    @Test
    public void testExceptionHandle() throws Exception{
        fail=true;
        InListener inL=new InListener(testComm,testUt,false);
        inL.methodToCall();
    }

    /**
     * Tests if the handler correctly notifies a disconnection
     */
    @Test
    public void testDCHandle() {
        InListener inL=new InListener(testComm,testUt,true);
        inL.notifyDisconnect();
        assertTrue(dc);
    }

    /**
     * Tests if the utilizer setter correctly updates the internal object
     */
    @Test
    public void testSetter() throws Exception{

        InListener inL=new InListener(testComm,null,true);
        inL.setUtilizer(testUt);
        inL.methodToCall();
        assertEquals("TestReq",receivedObj);
    }

    /**
     * Mock utilizer used to catch method calls from the listener
     */
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
        public void notifyMatchStart(MatchIdentifier mId) {

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

    /**
     * Mock Comm used to catch method calls from the listener
     */
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

    /**
     * Mock request used in this test
     */
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