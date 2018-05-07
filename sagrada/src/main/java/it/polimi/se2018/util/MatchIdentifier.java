package it.polimi.se2018.util;

import java.io.Serializable;
import java.util.TreeSet;

/**
 * Wrapper class to identify a match
 * @author Francesco Franzini
 * */
public class MatchIdentifier implements Serializable {
    public static final String DEFAULT_SEPARATOR=":";
    public final String player0;
    public final String player1;
    public final String player2;
    public final String player3;

    /**
     * Initializes player names in alphabetic(asc) order.
     * Player names must be different
     *
     * @param player0 the first name
     * @param player1 the second name
     * @param player2 the third name
     * @param player3 the fourth name
     */
    public MatchIdentifier(String player0, String player1, String player2, String player3) {
        TreeSet<String> s =new TreeSet<>();
        s.add(player0);
        s.add(player1);
        s.add(player2);
        s.add(player3);
        this.player0 = s.pollFirst();
        this.player1 = s.pollFirst();
        this.player2 = s.pollFirst();
        this.player3 = s.pollFirst();
    }

    @Override
    public String toString(){
        return player0+DEFAULT_SEPARATOR
                +player1+DEFAULT_SEPARATOR
                +player2+DEFAULT_SEPARATOR
                +player3;
    }
}