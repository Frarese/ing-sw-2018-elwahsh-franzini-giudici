package it.polimi.se2018.view.tools.fx.controller;

import it.polimi.se2018.view.app.JavaFXStageProducer;
import it.polimi.se2018.view.tools.fx.alert.ConfirmBox;
import it.polimi.se2018.view.tools.fx.alert.GameMenuBox;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.image.ImageView;
import javafx.scene.layout.VBox;

public class GameController implements FXController {

    @FXML
    ImageView toolCard0, toolCard1, toolCard2, publicObjectiveCard0, publicObjectiveCard1, publicObjectiveCard2, privateObjectiveCard;

    @FXML
    Label toolCardFV0, toolCardFV1, toolCardFV2;

    @FXML
    TextField myFavours;

    @FXML
    VBox roundTrackerContainer, reserveContainer, myGridContainer;

    public void menu() {
        GameMenuBox.display();
    }

    public void passTurn() {
        boolean answer = ConfirmBox.display("Passaggio turno", "Sicuro di voler passare il turno?");
        if (answer) {
            JavaFXStageProducer.getApp().getViewActions().passTurn();
        }
    }

}
