package it.polimi.se2018.view.tools.cli.ui;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.io.ByteArrayInputStream;

import static org.junit.Assert.assertEquals;

/**
 * Tests for CLIReader class
 *
 * @author Mathyas Giudici
 */

public class CLIReaderTest {

    private String enter = "\r\n";

    private CLIReader cliReader;

    private CLIPrinter cliPrinter;

    @Before
    public void testInit(){
        cliPrinter = new CLIPrinter();
    }

    @Test
    public void readTest() {
        String message = "Test";
        System.setIn(new ByteArrayInputStream((message + enter).getBytes()));

        this.cliReader = new CLIReader(this.cliPrinter);
        String returnString = this.cliReader.read();

        assertEquals(message, returnString);
    }

    @Test
    public void readIntTest() {
        int message = 1;
        System.setIn(new ByteArrayInputStream((message + enter).getBytes()));

        this.cliReader = new CLIReader(this.cliPrinter);
        int returnNumber = this.cliReader.readInt();

        assertEquals(message, returnNumber);
    }

    @Test
    public void readIntFailTest() {
        String message = "asssd" + enter + "11";
        System.setIn(new ByteArrayInputStream((message + enter).getBytes()));

        this.cliReader = new CLIReader(this.cliPrinter);
        int returnNumber = this.cliReader.readInt();

        assertEquals(11, returnNumber);
    }

    @Test
    public void chooseYesTest() {
        String message = "a" + enter + "y" + enter;
        System.setIn(new ByteArrayInputStream(message.getBytes()));

        this.cliReader = new CLIReader(this.cliPrinter);
        boolean returnValue = this.cliReader.chooseYes();

        assertEquals(true, returnValue);
    }

    @Test
    public void chooseNoTest() {
        String message = "n";
        System.setIn(new ByteArrayInputStream((message + enter).getBytes()));

        this.cliReader = new CLIReader(this.cliPrinter);
        boolean returnValue = this.cliReader.chooseYes();

        assertEquals(false, returnValue);
    }


    @Test
    public void chooseInRangeFirstTest() {
        String numberString = "0";
        System.setIn(new ByteArrayInputStream((numberString + enter).getBytes()));

        this.cliReader = new CLIReader(this.cliPrinter);
        int returnValue = this.cliReader.chooseInRange(0,3);

        assertEquals(0, returnValue);
    }

    @Test
    public void chooseInRangeSecondTest() {
        String numberString = "0" + enter + "4" + enter + "2";
        System.setIn(new ByteArrayInputStream((numberString + enter).getBytes()));

        this.cliReader = new CLIReader(this.cliPrinter);
        int returnValue = this.cliReader.chooseInRange(1,3);

        assertEquals(2, returnValue);
    }

    @Test
    public void chooseBetweenFirstSecondTest() {
        String numberString = "1";
        System.setIn(new ByteArrayInputStream((numberString + enter).getBytes()));

        this.cliReader = new CLIReader(this.cliPrinter);
        int returnValue = this.cliReader.chooseBetweenTwo(1,2);

        assertEquals(1, returnValue);
    }

    @Test
    public void chooseBetweenTwoSecondTest() {
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