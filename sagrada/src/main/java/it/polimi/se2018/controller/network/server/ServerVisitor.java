package it.polimi.se2018.controller.network.server;

import it.polimi.se2018.controller.network.*;

@SuppressWarnings({"unused", "EmptyMethod"})
public interface ServerVisitor {

    void handle(ChangeCLayerRequest c);

    void handle(ChatMessageRequest c);

    void handle(ClientRequest c);

    void handle(GetLeaderBoardRequest c);

    void handle(KeepAliveRequest c);

    void handle(LeaveMatchRequest c);

    void handle(ListUsersRequest c);

    void handle(LogoutRequest c);

    void handle(MatchAbortedRequest c);

    void handle(MatchBeginRequest c);

    void handle(MatchEndedRequest c);

    void handle(MatchInviteRequest c);

    void handle(MatchmakingRequest c);

    void handle(MatchResponseRequest c);

    void handle(UserReconnectedRequest c);
}
