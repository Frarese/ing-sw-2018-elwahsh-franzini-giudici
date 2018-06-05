package it.polimi.se2018.controller.network.server;

import it.polimi.se2018.controller.network.*;

import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

class ServerVisitorImpl implements ServerVisitor {
    private final Client client;
    private final ServerMain server;

    ServerVisitorImpl(Client c,ServerMain s){
        this.client=c;
        this.server=s;
    }

    @Override
    public void handle(ChangeCLayerRequest c) {
        if(!c.checkValid())return;
        client.zombiefy(false,c);
    }

    @Override
    public void handle(ChatMessageRequest c) {
        if(!c.checkValid())return;
        if(!c.sender.equals(client.usn)){
            Logger.getGlobal().log(Level.FINE,"Client attempted to cheat on username {0}",client.usn);
        }
        switch (c.type){
            case BROADCAST:
                throw new UnsupportedOperationException();
            case PM:
                Client d=server.getClient(c.destination);
                if(d!=null)d.pushOutReq(c);
                break;
            case MATCH:
                if(client.getMatch()==null)break;
                List<Client> dst=client.getMatch().getClients();
                dst.stream().filter(cl->(cl!=client)).forEach(cl->cl.pushOutReq(c));
        }
    }

    @Override
    public void handle(ClientRequest c) {
        if(!c.checkValid())return;
        if(client!=null && client.getMatch()!=null){
            client.getMatch().handleReq(client.usn,c.serializedReq);
        }
    }

    @Override
    public void handle(GetLeaderBoardRequest c) {
        c.setLeaderboard(server.getRegisteredUsers());
        client.pushOutReq(c);
    }

    @Override
    public void handle(KeepAliveRequest c) {
        if(!c.hasBounced()){
            c.setBounced();
            client.sendReq(c);
        }
    }

    @Override
    public void handle(LeaveMatchRequest c) {
        if(!c.checkValid())return;
        Match m=client.getMatch();
        if(m==null)return;
        m.playerLeft(c.usn,false);
    }

    @Override
    public void handle(ListUsersRequest c) {
        c.setList(server.getUserListCopy());
        client.pushOutReq(c);
    }

    @Override
    public void handle(LogoutRequest c) {
        client.purge(true);
    }

    @Override
    public void handle(MatchAbortedRequest c) {
        //This request means nothing if received by server
    }

    @Override
    public void handle(MatchBeginRequest c) {
        //This request means nothing if received by server
    }

    @Override
    public void handle(MatchEndedRequest c) {
        //This request means nothing if received by server
    }

    @Override
    public void handle(MatchInviteRequest c) {
        if(!c.checkValid())return;
        server.addPendingMatch(c.matchId,client);
    }

    @Override
    public void handle(MatchmakingRequest c) {
        if(c.join)server.addToMatchMaking(client);
        else server.removeFromMatchMaking(client);
    }

    @Override
    public void handle(MatchResponseRequest c) {
        if(!c.checkValid())return;
        PendingApprovalMatch m=server.getPendingMatch(c.matchId);
        if(m==null)return;
        if(c.accept)m.clientAccepted(client);
        else m.abort();
    }

    @Override
    public void handle(UserReconnectedRequest c) {
        //This request means nothing if received by server
    }
}
