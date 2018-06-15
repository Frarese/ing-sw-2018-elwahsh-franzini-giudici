package it.polimi.se2018.view.tools.fx.controller;

import org.junit.Test;

import java.lang.reflect.Method;

import static org.junit.Assert.*;

public class ChangeLayerControllerTest {


    @Test
    public void testChecks() throws Exception{
        ChangeLayerController uut=new ChangeLayerController();

        Method reqM=ChangeLayerController.class.getDeclaredMethod("checkRequestPort", String.class, StringBuilder.class);
        reqM.setAccessible(true);

        StringBuilder sB=new StringBuilder();
        reqM.invoke(uut,"-1",sB);
        assertNotEquals("",sB.toString());

        sB=new StringBuilder();
        reqM.invoke(uut,"test",sB);
        assertNotEquals("",sB.toString());

        sB=new StringBuilder();
        reqM.invoke(uut,"",sB);
        assertNotEquals("",sB.toString());

        Method objM=ChangeLayerController.class.getDeclaredMethod("checkObjectPort", String.class, StringBuilder.class);
        objM.setAccessible(true);

        sB=new StringBuilder();
        objM.invoke(uut,"-1",sB);
        assertNotEquals("",sB.toString());

        sB=new StringBuilder();
        objM.invoke(uut,"test",sB);
        assertNotEquals("",sB.toString());

        sB=new StringBuilder();
        objM.invoke(uut,"",sB);
        assertNotEquals("",sB.toString());
    }
}