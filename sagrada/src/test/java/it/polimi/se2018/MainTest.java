package it.polimi.se2018;

import org.junit.Test;


public class MainTest {

    @Test(expected = IllegalArgumentException.class)
    public void testClientInit() throws Exception{
        String[] args=new String[1];
        args[0]="true";
        Main.main(args);
    }

    @Test(expected = NumberFormatException.class)
    public void testServerInit() throws Exception{
        String[] args=new String[5];
        args[0]="false";
        args[1]="localhost";
        args[2]="10";
        args[3]="10";
        args[4]=null;
        Main.main(args);
    }
}