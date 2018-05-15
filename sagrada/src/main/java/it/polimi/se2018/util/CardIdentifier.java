package it.polimi.se2018.util;

import java.util.HashMap;

/**
 * Class to manage cards' ID
 *
 * @author Mathyas Giudici
 */

public class CardIdentifier {

    private HashMap<Integer, String> cards;
    private HashMap<Integer, String> helper;


    /**
     * Class constructor
     */
    public CardIdentifier() {
        this.cards = new HashMap<>();
        this.initCards();
    }

    /**
     * To ask card's name
     *
     * @param card contains cardID
     * @return card's name
     */
    public String getCardInfo(int card) {
        return this.cards.get(card);
    }

    /**
     * To initialize HashMap of cards
     */
    private void initCards() {
        //Start with private objective card
        this.cards.put(0, "Sfumature Rosse");
        this.cards.put(1, "Sfumature Blu");
        this.cards.put(2, "Sfumature Verdi");
        this.cards.put(3, "Sfumature Gialle");
        this.cards.put(4, "Sfumature Viola");

        //Start with public objective card
        this.cards.put(10, "Colori Diversi - Riga (6 punti)");
        this.cards.put(11, "Colori Diversi - Colonna (5 punti)");
        this.cards.put(12, "Sfumature Diverse - Riga (5 punti)");
        this.cards.put(13, "Sfumature Diverse - Colonna (4 punti)");
        this.cards.put(14, "Sfumature Chiare - 1 e 2 (2 punti)");
        this.cards.put(15, "Sfumature Medi - 3 e 4 (2 punti)");
        this.cards.put(16, "Sfumature Scure - 5 e 6 (2 punti)");
        this.cards.put(17, "Sfumature Diverse (5 punti)");
        this.cards.put(18, "Diagonali colorate");
        this.cards.put(19, "Varieta\' di colore (5 punti)");

        //Start with
        this.cards.put(20, "Pinza sgrossatrice");
        this.cards.put(21, "Pennello per Eglomise");
        this.cards.put(22, "Alesatore per lamina di rame");
        this.cards.put(23, "Lathekin");
        this.cards.put(24, "Taglierina circolare");
        this.cards.put(25, "Pennello per Pasta Salda");
        this.cards.put(26, "Martelleto");
        this.cards.put(27, "Tenaglia a Rotelle");
        this.cards.put(28, "Riga in sughero");
        this.cards.put(29, "Tampone Diamantato");
        this.cards.put(30, "Diluente per Pasta Salda");
        this.cards.put(31, "Taglierina Manuale");
//        this.cards.put(32, "Strip Cutter");
    }

}
