package it.polimi.se2018.controller.game.turns;

import it.polimi.se2018.model.Player;

import java.util.Observer;

public abstract class AbstractTurn implements Runnable, Observer {

    protected Player activePlayer;


}
