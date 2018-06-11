package it.polimi.se2018.view.tools.fx.controller;

import it.polimi.se2018.model.ColorModel;
import it.polimi.se2018.util.CardIdentifier;
import it.polimi.se2018.util.SingleCardView;
import it.polimi.se2018.view.app.JavaFXStageProducer;
import it.polimi.se2018.view.observer.PlayerState;
import it.polimi.se2018.view.tools.fx.alert.CardZoomBox;
import it.polimi.se2018.view.tools.fx.alert.ConfirmBox;
import it.polimi.se2018.view.tools.fx.alert.GameMenuBox;
import it.polimi.se2018.view.tools.fx.creators.FXGridViewCreator;
import it.polimi.se2018.view.tools.fx.creators.FXReserveViewCreator;
import it.polimi.se2018.view.tools.fx.creators.FXRoundTrackerViewCreator;
import javafx.event.Event;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.VBox;

import java.util.ArrayList;
import java.util.List;

/**
 * Manages game page
 *
 * @author Mathyas Giudici
 */

public class GameController implements FXController {

    @FXML
    ImageView toolCard0;
    @FXML
    ImageView toolCard1;
    @FXML
    ImageView toolCard2;
    @FXML
    ImageView publicObjectiveCard0;
    @FXML
    ImageView publicObjectiveCard1;
    @FXML
    ImageView publicObjectiveCard2;
    @FXML
    ImageView privateObjectiveCard;

    @FXML
    Label toolCardFV0;
    @FXML
    Label toolCardFV1;
    @FXML
    Label toolCardFV2;

    @FXML
    TextField myFavours;

    @FXML
    VBox roundTrackerContainer;
    @FXML
    VBox reserveContainer;

    @FXML
    VBox myGridContainer;
    @FXML
    VBox firstOtherPlayer;
    @FXML
    VBox secondOtherPlayer;
    @FXML
    VBox thirdOtherPlayer;


    public void display() {

        //Sets cards and zoom on its
        privateObjectiveCard.setImage((Image) JavaFXStageProducer.getApp().getCardViewCreator().makeCard(JavaFXStageProducer.getApp().getCardViewCreator().getPrivateObjectiveCard().cardID));
        privateObjectiveCard.setOnMouseEntered(e -> {
            CardZoomBox.display(privateObjectiveCard.getImage());
            e.consume();
        });
        privateObjectiveCard.setOnMouseExited(e -> {
            CardZoomBox.close();
            e.consume();
        });

        publicObjectiveCard0.setImage((Image) JavaFXStageProducer.getApp().getCardViewCreator().makeCard(((SingleCardView) JavaFXStageProducer.getApp().getCardViewCreator().getPublicObjectiveCards().get(0)).cardID));
        publicObjectiveCard0.setOnMouseEntered(e -> {
            CardZoomBox.display(publicObjectiveCard0.getImage());
            e.consume();
        });
        publicObjectiveCard0.setOnMouseExited(e -> {
            CardZoomBox.close();
            e.consume();
        });

        publicObjectiveCard1.setImage((Image) JavaFXStageProducer.getApp().getCardViewCreator().makeCard(((SingleCardView) JavaFXStageProducer.getApp().getCardViewCreator().getPublicObjectiveCards().get(1)).cardID));
        publicObjectiveCard1.setOnMouseEntered(e -> {
            CardZoomBox.display(publicObjectiveCard1.getImage());
            e.consume();
        });
        publicObjectiveCard1.setOnMouseExited(e -> {
            CardZoomBox.close();
            e.consume();
        });

        publicObjectiveCard2.setImage((Image) JavaFXStageProducer.getApp().getCardViewCreator().makeCard(((SingleCardView) JavaFXStageProducer.getApp().getCardViewCreator().getPublicObjectiveCards().get(2)).cardID));
        publicObjectiveCard2.setOnMouseEntered(e -> {
            CardZoomBox.display(publicObjectiveCard2.getImage());
            e.consume();
        });
        publicObjectiveCard2.setOnMouseExited(e -> {
            CardZoomBox.close();
            e.consume();
        });

        String preToolFavours = "FV: ";

        toolCard0.setImage((Image) JavaFXStageProducer.getApp().getCardViewCreator().makeCard(((SingleCardView) JavaFXStageProducer.getApp().getCardViewCreator().getToolCards().get(0)).cardID));
        toolCard0.setOnMouseEntered(e -> {
            CardZoomBox.display(toolCard0.getImage());
            e.consume();
        });
        toolCard0.setOnMouseExited(e -> {
            CardZoomBox.close();
            e.consume();
        });
        toolCardFV0.setText(preToolFavours + ((SingleCardView) JavaFXStageProducer.getApp().getCardViewCreator().getToolCards().get(0)).cardCost);


        toolCard1.setImage((Image) JavaFXStageProducer.getApp().getCardViewCreator().makeCard(((SingleCardView) JavaFXStageProducer.getApp().getCardViewCreator().getToolCards().get(1)).cardID));
        toolCard1.setOnMouseEntered(e -> {
            CardZoomBox.display(toolCard1.getImage());
            e.consume();
        });
        toolCard1.setOnMouseExited(e -> {
            CardZoomBox.close();
            e.consume();
        });
        toolCardFV1.setText(preToolFavours + ((SingleCardView) JavaFXStageProducer.getApp().getCardViewCreator().getToolCards().get(1)).cardCost);

        toolCard2.setImage((Image) JavaFXStageProducer.getApp().getCardViewCreator().makeCard(((SingleCardView) JavaFXStageProducer.getApp().getCardViewCreator().getToolCards().get(2)).cardID));
        toolCard2.setOnMouseEntered(e -> {
            CardZoomBox.display(toolCard2.getImage());
            e.consume();
        });
        toolCard2.setOnMouseExited(e -> {
            CardZoomBox.close();
            e.consume();
        });
        toolCardFV2.setText(preToolFavours + ((SingleCardView) JavaFXStageProducer.getApp().getCardViewCreator().getToolCards().get(2)).cardCost);

        //Sets current player favours points
        PlayerState me = JavaFXStageProducer.getApp().searchPlayerViewByName(JavaFXStageProducer.getApp().getPlayers(), JavaFXStageProducer.getApp().getOwnerPlayerName());
        myFavours.setText(String.valueOf(me.getPlayerFavours()));

        //Sets reserve and round tracker
        reserveContainer.getChildren().clear();
        reserveContainer.getChildren().add((VBox) JavaFXStageProducer.getApp().getReserveViewCreator().display());
        roundTrackerContainer.getChildren().clear();
        roundTrackerContainer.getChildren().add((VBox) JavaFXStageProducer.getApp().getRoundTrackerViewCreator().display());

        //Sets grids
        makeGrid();
    }

    public void enablePlacement() {
        //Sets reserve drag and drop
        reserveContainer.getChildren().clear();
        FXReserveViewCreator reserveViewCreator = (FXReserveViewCreator) JavaFXStageProducer.getApp().getReserveViewCreator();
        reserveContainer.getChildren().add(reserveViewCreator.displayWithDeD());

        //Sets grid drag and drop
        myGridContainer.getChildren().clear();
        FXGridViewCreator gridViewCreator = (FXGridViewCreator) JavaFXStageProducer.getApp().getGridViewCreator();
        myGridContainer.getChildren().add(gridViewCreator.displayWithDeD());
    }

    public void disablePlacement() {
        reserveContainer.getChildren().clear();
        FXReserveViewCreator reserveViewCreator = (FXReserveViewCreator) JavaFXStageProducer.getApp().getReserveViewCreator();
        reserveContainer.getChildren().add(reserveViewCreator.display());

        //Sets grid drag and drop
        myGridContainer.getChildren().clear();
        FXGridViewCreator gridViewCreator = (FXGridViewCreator) JavaFXStageProducer.getApp().getGridViewCreator();
        myGridContainer.getChildren().add(gridViewCreator.display());
    }


    public void enableToolCardsUse() {
        String title = "Uso Tool Card";
        String comMessage = "Sei sicuro di voler utilizzare la carta tool: ";

        toolCard0.setOnMouseClicked(e -> {
            CardZoomBox.close();
            boolean answer = ConfirmBox.display(title, comMessage + new CardIdentifier().getCardInfo(((SingleCardView) JavaFXStageProducer.getApp().getCardViewCreator().getToolCards().get(0)).cardID));
            if (answer) {
                JavaFXStageProducer.getApp().getViewActions().useToolCard(((SingleCardView) JavaFXStageProducer.getApp().getCardViewCreator().getToolCards().get(0)).cardID);
                disableToolCardsUse();
                e.consume();
            }
        });

        toolCard1.setOnMouseClicked(e -> {
            CardZoomBox.close();
            boolean answer = ConfirmBox.display(title, comMessage + new CardIdentifier().getCardInfo(((SingleCardView) JavaFXStageProducer.getApp().getCardViewCreator().getToolCards().get(1)).cardID));
            if (answer) {
                JavaFXStageProducer.getApp().getViewActions().useToolCard(((SingleCardView) JavaFXStageProducer.getApp().getCardViewCreator().getToolCards().get(1)).cardID);
                disableToolCardsUse();
                e.consume();
            }
        });

        toolCard2.setOnMouseClicked(e -> {
            CardZoomBox.close();
            boolean answer = ConfirmBox.display(title, comMessage + new CardIdentifier().getCardInfo(((SingleCardView) JavaFXStageProducer.getApp().getCardViewCreator().getToolCards().get(2)).cardID));
            if (answer) {
                JavaFXStageProducer.getApp().getViewActions().useToolCard(((SingleCardView) JavaFXStageProducer.getApp().getCardViewCreator().getToolCards().get(2)).cardID);
                disableToolCardsUse();
                e.consume();
            }
        });
    }

    public void passTurn() {
        boolean answer = ConfirmBox.display("Passaggio turno", "Sicuro di voler passare il turno?");
        if (answer) {
            JavaFXStageProducer.getApp().getViewActions().passTurn();
        }
    }

    public void menu() {
        GameMenuBox.display();
    }

    private void makeGrid() {
        List<String> listColor = new ArrayList<>();
        listColor.add(ColorModel.BLUE.toJavaFXColor());
        listColor.add(ColorModel.GREEN.toJavaFXColor());
        listColor.add(ColorModel.YELLOW.toJavaFXColor());

        for (PlayerState playerState : JavaFXStageProducer.getApp().getPlayers()) {
            if (playerState.getPlayerName().equals(JavaFXStageProducer.getApp().getOwnerPlayerName())) {
                myGridContainer.getChildren().clear();
                myGridContainer.getChildren().add((VBox) JavaFXStageProducer.getApp().getGridViewCreator().display());
            } else {
                FXGridViewCreator gridViewCreator = new FXGridViewCreator(listColor.get(0));
                gridViewCreator.setGridPattern(playerState.getPlayerTemplate());
                gridViewCreator.setGrid(playerState.getPlayerGrid());
                if (listColor.size() == 3) {
                    firstOtherPlayer.getChildren().clear();
                    firstOtherPlayer.getChildren().add(gridViewCreator.display());
                } else if (listColor.size() == 2) {
                    secondOtherPlayer.getChildren().clear();
                    secondOtherPlayer.getChildren().add(gridViewCreator.display());
                } else {
                    thirdOtherPlayer.getChildren().clear();
                    thirdOtherPlayer.getChildren().add(gridViewCreator.display());
                }
                listColor.remove(0);
            }
        }
    }

    private void disableToolCardsUse() {
        toolCard0.setOnMouseClicked(Event::consume);
        toolCard1.setOnMouseClicked(Event::consume);
        toolCard2.setOnMouseClicked(Event::consume);
    }


    public void selectDieFromReserve() {
        //Sets reserve click
        reserveContainer.getChildren().clear();
        FXReserveViewCreator reserveViewCreator = (FXReserveViewCreator) JavaFXStageProducer.getApp().getReserveViewCreator();
        reserveContainer.getChildren().add(reserveViewCreator.displayWithClick());
    }

    public void refreshReserve() {
        //Refreshes reserve
        reserveContainer.getChildren().clear();
        reserveContainer.getChildren().add((VBox) JavaFXStageProducer.getApp().getReserveViewCreator().display());
    }

    public void selectDieFromGrid() {
        //Sets grid click
        myGridContainer.getChildren().clear();
        FXGridViewCreator gridViewCreator = (FXGridViewCreator) JavaFXStageProducer.getApp().getGridViewCreator();
        myGridContainer.getChildren().add(gridViewCreator.displayWithClick(true, false));
    }

    public void setDieOnGrid() {
        //Sets grid click
        myGridContainer.getChildren().clear();
        FXGridViewCreator gridViewCreator = (FXGridViewCreator) JavaFXStageProducer.getApp().getGridViewCreator();
        myGridContainer.getChildren().add(gridViewCreator.displayWithClick(false, false));
    }

    public void selectDieFromRoundTracker() {
        //Sets RoundTracker click
        roundTrackerContainer.getChildren().clear();
        FXRoundTrackerViewCreator roundTrackerViewCreator = (FXRoundTrackerViewCreator) JavaFXStageProducer.getApp().getRoundTrackerViewCreator();
        roundTrackerContainer.getChildren().add(roundTrackerViewCreator.displayWithClick());
    }

    public void selectDieFromGridByColor() {
        //Sets grid click
        myGridContainer.getChildren().clear();
        FXGridViewCreator gridViewCreator = (FXGridViewCreator) JavaFXStageProducer.getApp().getGridViewCreator();
        myGridContainer.getChildren().add(gridViewCreator.displayWithClick(true, true));
    }
}
