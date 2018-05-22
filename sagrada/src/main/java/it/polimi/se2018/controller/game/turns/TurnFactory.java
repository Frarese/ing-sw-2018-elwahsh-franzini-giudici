package it.polimi.se2018.controller.game.turns;

import it.polimi.se2018.model.Player;

import java.util.ArrayList;

public class TurnFactory {

    private Player local;
    private ArrayList<Player> deadPlayers = new ArrayList<>();
    public TurnFactory(Player local)
    {
        this.local =local;
    }

    public void killPlayer(Player player)
    {
        if(!deadPlayers.contains(player))
            deadPlayers.add(player);
    }

    public void revivePlayer(Player player)
    {
        deadPlayers.remove(player);
    }

    public AbstractTurn getTurn(Player player)
    {
        return null;
    }
}
