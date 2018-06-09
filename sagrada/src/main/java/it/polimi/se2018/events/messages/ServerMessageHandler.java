package it.polimi.se2018.events.messages;

import it.polimi.se2018.controller.game.client.ClientController;
import it.polimi.se2018.events.Event;

import it.polimi.se2018.observable.CardView;
import it.polimi.se2018.observable.PlayerView;
import it.polimi.se2018.observable.ReserveView;
import it.polimi.se2018.observable.RoundTrackerView;
import it.polimi.se2018.util.SingleCardView;
import it.polimi.se2018.view.app.App;

import java.util.List;



public interface ServerMessageHandler {

    static void handle(ClientController controller, Event message, App app, List<PlayerView> players, ReserveView reserve, RoundTrackerView roundTrack,CardView cards) {

        switch (message.toString()) {
            case "Player":
                PlayerStatus playerStatus = (PlayerStatus) message;
                for(PlayerView p: players)
                    if(p.getPlayerName().equals(playerStatus.getName()))
                    {
                        p.setPlayerGrid(playerStatus.getGrid());
                        p.setFirstCardRights(playerStatus.isFirstTurnCard());
                        p.setFirstPlacementRights(playerStatus.isFirstTurnPlacement());
                        p.setSecondCardRights(playerStatus.isSecondTurnCard());
                        p.setSecondPlacementRights(playerStatus.isSecondTurnPlacement());
                        p.setPlayerFavours(playerStatus.getFavourPoints());
                        p.uniqueNotify();
                    }

                break;
            case "CardInfo":
                CardInfo cardInfo = (CardInfo) message;
                cards.setCardView(cardInfo);
                break;
            case "PrivateObjective":
                PrivateObjectiveStatus privateObjectiveStatus = (PrivateObjectiveStatus) message;
                cards.setPrivateObjectiveCard(new SingleCardView(privateObjectiveStatus.getCardId(),0));
                cards.uniqueNotify();
                break;
            case "RoundTrack":
                RoundTrackStatus roundTrackStatus = (RoundTrackStatus) message;
                roundTrack.setRound(roundTrackStatus.round());
                roundTrack.setRoundTracker(roundTrackStatus.getDice());
                roundTrack.uniqueNotify();
                break;

            case "Reserve":
                ReserveStatus reserveStatus = (ReserveStatus) message;
                reserve.setReserve(reserveStatus.getDice());
                reserve.uniqueNotify();
                break;

            case "Pattern":
                controller.addPatternView((PatternSelect) message);
                break;

            case "MatchStart":
                app.initGame(players,reserve,roundTrack);
                break;
            default:

        }
    }
}
