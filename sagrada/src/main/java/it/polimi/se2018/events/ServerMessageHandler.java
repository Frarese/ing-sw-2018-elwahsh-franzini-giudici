package it.polimi.se2018.events;


import it.polimi.se2018.events.messages.*;

/**Interface used to implement the visitor pattern on messages coming from the server
 * @author Francesco Franzini
 */
public interface ServerMessageHandler {


    void handle(CardInfo move);
    void handle(InvalidMove move);
    void handle(ConfirmMove move);
    void handle(MatchStart move);
    void handle(PatternSelect move);
    void handle(PlayerStatus move);
    void handle(PrivateObjectiveStatus move);
    void handle(ReserveStatus move);
    void handle(RoundTrackStatus move);
    void handle(TurnStart move);

    void handle(ReadyView move);
}
