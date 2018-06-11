package it.polimi.se2018.events;


import it.polimi.se2018.events.messages.*;

public interface ServerMessageHandler {

    void handle(CardInfo move);
    void handle(InvalidMove move);
    void handle(MatchStart move);
    void handle(PatternSelect move);
    void handle(PlayerStatus move);
    void handle(PrivateObjectiveStatus move);
    void handle(ReserveStatus move);
    void handle(RoundTrackStatus move);
    void handle(TurnStart move);

    void handle(ReadyView move);
}
