package it.polimi.se2018.view.tools.fx.controller;

import it.polimi.se2018.util.PatternView;
import it.polimi.se2018.util.SingleCardView;
import it.polimi.se2018.view.app.JavaFXStageProducer;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Pane;

import java.util.logging.Level;
import java.util.logging.Logger;

public class ShowCardsController implements FXController {

    @FXML
    ImageView privateObjectiveCard;

    @FXML
    ImageView publicObjectiveCard0;
    @FXML
    ImageView publicObjectiveCard1;
    @FXML
    ImageView publicObjectiveCard2;

    private PatternView pattern1, pattern2, pattern3, pattern4;

    public void saveState(PatternView pattern1, PatternView pattern2, PatternView pattern3, PatternView pattern4) {
        this.pattern1 = pattern1;
        this.pattern2 = pattern2;
        this.pattern3 = pattern3;
        this.pattern4 = pattern4;
    }

    public void display() {
        privateObjectiveCard.setImage((Image) JavaFXStageProducer.getApp().getCardViewCreator().makeCard(JavaFXStageProducer.getApp().getCardViewCreator().getPrivateObjectiveCard().cardID));

        publicObjectiveCard0.setImage((Image) JavaFXStageProducer.getApp().getCardViewCreator().makeCard(((SingleCardView) JavaFXStageProducer.getApp().getCardViewCreator().getPublicObjectiveCards().get(0)).cardID));
        publicObjectiveCard1.setImage((Image) JavaFXStageProducer.getApp().getCardViewCreator().makeCard(((SingleCardView) JavaFXStageProducer.getApp().getCardViewCreator().getPublicObjectiveCards().get(1)).cardID));
        publicObjectiveCard2.setImage((Image) JavaFXStageProducer.getApp().getCardViewCreator().makeCard(((SingleCardView) JavaFXStageProducer.getApp().getCardViewCreator().getPublicObjectiveCards().get(2)).cardID));
    }

    public void next() {
        try {
            FXMLLoader loader = new FXMLLoader(JavaFXStageProducer.class.getResource("fxmlFiles/patternSelection.fxml"));
            Pane root = loader.load();

            Platform.runLater(() -> {
                JavaFXStageProducer.getStage().setScene(new Scene(root));
                JavaFXStageProducer.setController(loader.getController());
                JavaFXStageProducer.getStage().centerOnScreen();
                PatternSelectionController patternSelectionController = (PatternSelectionController) JavaFXStageProducer.getController();
                patternSelectionController.showPattern(pattern1, pattern2, pattern3, pattern4);
            });
        } catch (Exception e) {
            Logger.getGlobal().log(Level.WARNING, "Non sono riuscito a caricare FXML");
        }
    }
}
