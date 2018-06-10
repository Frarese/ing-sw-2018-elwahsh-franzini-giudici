package it.polimi.se2018.events.actions;

public class ReadyView extends PlayerMove {

    public ReadyView(String username)
    {
        this.playerName = username;
        this.description = "ReadyView";
    }
}
