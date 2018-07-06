package it.polimi.se2018.controller.network.server;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.io.*;

import static org.junit.Assert.*;

/**
 * Tester class for the FileUserBase class
 * @author Francesco Franzini
 */
@SuppressWarnings("ResultOfMethodCallIgnored")
public class FileUserBaseTest {
    private FileUserBase uut;

    /**
     * Ensures that the tests are independent
     */
    @Before
    public void setUp(){
        File f=new File("test.csv");
        f.delete();
    }

    /**
     * Cleans the user file
     */
    @After
    public void tearDown() {
        new File("test.csv").delete();
    }

    /**
     * Tests that the users are correctly read
     * @throws IOException if an error occurs
     */
    @Test
    public void testRead() throws IOException {
        File f=new File("test.csv");
        f.createNewFile();
        PrintWriter pw=new PrintWriter(f);
        pw.write("user1,p1,100,100\n");
        pw.write("user2,p2,200,100\n");
        pw.write("user3,p3,100,600\n");
        pw.write("user4,p4,500,100\n");
        pw.close();
        uut=new FileUserBase("test.csv");
        assertTrue(new File("test.csv").exists());
        assertTrue(uut.containsUser("user1"));
        assertTrue(uut.containsUser("user2"));
        assertTrue(uut.containsUser("user3"));
        assertTrue(uut.containsUser("user4"));

        assertEquals("p1",uut.getPw("user1"));
        assertEquals(100,uut.getUserScore("user1").tot);
        assertEquals(600,uut.getUserScore("user3").wins);
    }

    /**
     * Tests that the file is created if not existing
     * @throws Exception if an error occurs
     */
    @Test
    public void testCreation() throws Exception{
        uut=new FileUserBase("test.csv");
        assertTrue(new File("test.csv").exists());
    }

    /**
     * Tests that invalid tuples are discarded
     * @throws Exception if an error occurs
     */
    @Test
    public void testInvalid() throws Exception{
        File f=new File("test.csv");
        f.createNewFile();
        PrintWriter pw=new PrintWriter(f);
        pw.write("user1,p1,100,100\n");
        pw.write("user2,p2,hello,100\n");
        pw.write("user3,p2,0,100\n");
        pw.write("user4,p2,0,100,field\n");
        pw.write("user5,p2,0\n");
        pw.close();
        uut=new FileUserBase("test.csv");
        assertTrue(new File("test.csv").exists());
        assertTrue(uut.containsUser("user1"));
        assertFalse(uut.containsUser("user2"));
        assertTrue(uut.containsUser("user3"));
        assertFalse(uut.containsUser("user4"));
        assertFalse(uut.containsUser("user5"));
    }

    /**
     * Checks that null values are correctly handled
     * @throws Exception if an error occurs
     */
    @Test
    public void testNull() throws Exception{
        uut=new FileUserBase("test.csv");
        assertNull(uut.getPw(null));
        assertFalse(uut.addUser(null,null));
        assertFalse(uut.removeUser(null));
        assertNull(uut.getUserScore(null));
        uut.alterUserScore(null,0,0);
        uut.alterUserScore("user",0,0);
    }

    /**
     * Tests the add method
     * @throws Exception if an error occurs
     */
    @Test
    public void testAdd() throws Exception{
        uut=new FileUserBase("test.csv");

        assertTrue(uut.addUser("user1","pw"));
        assertFalse(uut.addUser("user1","pw"));
        assertTrue(uut.containsUser("user1"));

        BufferedReader bf=new BufferedReader(new FileReader("test.csv"));
        assertEquals("user1,pw,0,0",bf.readLine());

        assertEquals("user1",uut.getLeaderBoard().get(0).usn);
        bf.close();
    }

    /**
     * Tests the remove method
     * @throws Exception if an error occurs
     */
    @Test
    public void testRemove() throws Exception{
        uut=new FileUserBase("test.csv");
        uut.addUser("user1","pw");
        assertTrue(uut.containsUser("user1"));
        uut.removeUser("user1");
        assertFalse(uut.containsUser("user1"));
    }

    /**
     * Tests that the file is created again if it is deleted during execution
     * @throws Exception if an error occurs
     */
    @Test
    public void testRemovedFile() throws Exception{
        uut=new FileUserBase("test.csv");
        new File("test.csv").delete();
        uut.addUser("user1","pw");
        assertTrue(uut.containsUser("user1"));
    }

    /**
     * Tests the alter method
     * @throws Exception if an error occurs
     */
    @Test
    public void testAlter() throws Exception{
        uut=new FileUserBase("test.csv");
        assertTrue(uut.addUser("user1","pw"));
        uut.alterUserScore("user1",200,100);
        assertEquals(200,uut.getUserScore("user1").tot);
        assertEquals(100,uut.getUserScore("user1").wins);
    }
}