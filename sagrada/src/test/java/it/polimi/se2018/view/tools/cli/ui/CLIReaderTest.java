package it.polimi.se2018.view.tools.cli.ui;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.io.ByteArrayInputStream;

import static org.junit.Assert.*;

/**
 * Tests for CLIReader class
 *
 * @author Mathyas Giudici
 */

public class CLIReaderTest {

    private final String enter = System.lineSeparator();

    private CLIReader cliReader;

    private CLIPrinter cliPrinter;

    @Before
    public void testInit(){
        cliPrinter = new CLIPrinter();
    }

    @Test
    public void testRead() {
        String message = "Test";
        System.setIn(new ByteArrayInputStream((message + enter).getBytes()));

        this.cliReader = new CLIReader(this.cliPrinter);
        String returnString = this.cliReader.read();

        assertEquals(message, returnString);
    }

    @Test
    public void testReadInt() {
        int message = 1;
        System.setIn(new ByteArrayInputStream((message + enter).getBytes()));

        this.cliReader = new CLIReader(this.cliPrinter);
        int returnNumber = this.cliReader.readInt();

        assertEquals(message, returnNumber);
    }

    @Test
    public void testReadIntFail() {
        String message = "fail" + enter + "11";
        System.setIn(new ByteArrayInputStream((message + enter).getBytes()));

        this.cliReader = new CLIReader(this.cliPrinter);
        int returnNumber = this.cliReader.readInt();

        assertEquals(11, returnNumber);
    }

    @Test
    public void testChooseYes() {
        String message = "a" + enter + "y" + enter;
        System.setIn(new ByteArrayInputStream(message.getBytes()));

        this.cliReader = new CLIReader(this.cliPrinter);
        boolean returnValue = this.cliReader.chooseYes();

        assertTrue(returnValue);
    }

    @Test
    public void testChooseNo() {
        String message = "n";
        System.setIn(new ByteArrayInputStream((message + enter).getBytes()));

        this.cliReader = new CLIReader(this.cliPrinter);
        boolean returnValue = this.cliReader.chooseYes();

        assertFalse(returnValue);
    }


    @Test
    public void testChooseInRangeFirst() {
        String numberString = "0";
        System.setIn(new ByteArrayInputStream((numberString + enter).getBytes()));

        this.cliReader = new CLIReader(this.cliPrinter);
        int returnValue = this.cliReader.chooseInRange(0,3);

        assertEquals(0, returnValue);
    }

    @Test
    public void testChooseInRangeSecond() {
        String numberString = "0" + enter + "4" + enter + "2";
        System.setIn(new ByteArrayInputStream((numberString + enter).getBytes()));

        this.cliReader = new CLIReader(this.cliPrinter);
        int returnValue = this.cliReader.chooseInRange(1,3);

        assertEquals(2, returnValue);
    }

    @Test
    public void testChooseBetweenFirstSecond() {
        String numberString = "1";
        System.setIn(new ByteArrayInputStream((numberString + enter).getBytes()));

        this.cliReader = new CLIReader(this.cliPrinter);
        int returnValue = this.cliReader.chooseBetweenTwo(1,2);

        assertEquals(1, returnValue);
    }

    @Test
    public void testChooseBetweenTwoSecond() {
        String numberString = "0" + enter + "2";
        System.setIn(new ByteArrayInputStream((numberString + enter).getBytes()));

        this.cliReader = new CLIReader(this.cliPrinter);
        int returnValue = this.cliReader.chooseBetweenTwo(1,2);

        assertEquals(2, returnValue);
    }

    @After
    public void testCloseOperation() {
        System.setIn(System.in);
        cliReader.close();
    }
}