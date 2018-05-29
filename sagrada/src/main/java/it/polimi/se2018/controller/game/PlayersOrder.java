package it.polimi.se2018.controller.game;

import it.polimi.se2018.model.Player;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.NoSuchElementException;

/**
 * This class represents the order of the players during a round
 * @deprecated since we're moving the controller inside the server
 * @author Alì El Wahsh
 */
@Deprecated
public class PlayersOrder implements Iterable<Player> {

    private ArrayList<Player> actors = new ArrayList<>();

    /**
     * PlayersOrder's constructor
     * @param actors list of all players
     */
    public PlayersOrder(List<Player> actors)
    {
        this.actors.addAll(actors);
    }

    /**
     * This custom iterator returns the players following the order
     * given by Sagrada's rules
     * @return an iterator with the mentioned properties
     */
    @Override
    public Iterator<Player> iterator() {
        return new Iterator<Player>() {

            private int index = 0;
            private int cursor = 0;
            boolean secondTurn;

            @Override
            public boolean hasNext() {

                return (!secondTurn && actors.get(index) != null && index < actors.size()) || (secondTurn && actors.get(index) != null && index >0);
            }

            @Override
            public Player next() {
                if(index <0 && secondTurn)
                    throw new NoSuchElementException();

                if(index == actors.size() -1)
                    secondTurn = true;
                if(!secondTurn)
                {
                    index = cursor;
                    cursor++;
                }
                else
                {
                    cursor--;
                    index = cursor;

                }
                return actors.get(index);

            }

            @Override
            public void remove() {
                throw new UnsupportedOperationException();
            }
        };
    }

    /**
     * Change the order of the players according to Sagrada's rules
     */
    public void prepareNextRound()
    {
        actors.add(actors.remove(0));
    }
}
