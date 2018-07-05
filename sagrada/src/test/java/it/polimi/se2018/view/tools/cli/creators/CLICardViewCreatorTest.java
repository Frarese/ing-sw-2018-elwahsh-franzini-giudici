package it.polimi.se2018.view.tools.cli.creators;

import it.polimi.se2018.util.SingleCardView;
import it.polimi.se2018.view.tools.CardViewCreator;
import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.*;

/**
 * Tests for CLICardViewCreator class
 *
 * @author Mathyas Giudici
 */

public class CLICardViewCreatorTest {

    private CardViewCreator cliCardViewCreator;

    private List<SingleCardView> publicCards, toolCards;

    /**
     * Creates public cards, tool cards and new CLICardViewCreator
     */
    @Before
    public void testInit() {
        this.publicCards = new ArrayList<>();
        publicCards.add(new SingleCardView(10, 1));
        publicCards.add(new SingleCardView(11, 1));

        this.toolCards = new ArrayList<>();
        toolCards.add(new SingleCardView(20, 1));
        toolCards.add(new SingleCardView(26, 1));
        cliCardViewCreator = new CLICardViewCreator(new SingleCardView(3, 1), publicCards, toolCards);
    }

    /**
     * Checks correct CLI' cards object creation
     */
    @Test
    public void testGetCardInfo() {
        assertEquals("Sfumature Gialle", cliCardViewCreator.makeCard(3));
        assertEquals("Colori Diversi - Riga (6 punti)", cliCardViewCreator.makeCard(10));
        assertEquals("Martelleto", cliCardViewCreator.makeCard(26));
        assertNull(cliCardViewCreator.makeCard(32));
    }

    /**
     * Tests getter method of the privateObjectiveCard
     */
    @Test
    public void testGetPrivateObjectiveCard() {
        assertEquals(3, cliCardViewCreator.getPrivateObjectiveCard().cardID);
    }

    /**
     * Tests getter method of the publicObjectiveCards
     */
    @Test
    public void testGetPublicObjectiveCards() {
        assertArrayEquals(this.publicCards.toArray(), cliCardViewCreator.getPublicObjectiveCards().toArray());
    }

    /**
     * Tests getter method of the toolCards
     */
    @Test
    public void testGetToolCards() {
        assertArrayEquals(this.toolCards.toArray(), cliCardViewCreator.getToolCards().toArray());
    }

}
