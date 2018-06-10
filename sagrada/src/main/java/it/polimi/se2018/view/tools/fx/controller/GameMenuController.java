package it.polimi.se2018.view.tools.fx.controller;


import it.polimi.se2018.view.app.JavaFXStageProducer;
import it.polimi.se2018.view.tools.fx.alert.ChangeLayerBox;
import it.polimi.se2018.view.tools.fx.alert.ConfirmBox;
import it.polimi.se2018.view.tools.fx.alert.GameMenuBox;

public class GameMenuController {

    public void close() {
        GameMenuBox.close();
    }

    public void changeLayer() {
        ChangeLayerBox.display();
    }

    public void lobby() {
        boolean answer = ConfirmBox.display("Lobby", "Sei sicuro di voler tornare alla lobby?");
        if (answer) {
            JavaFXStageProducer.getApp().getViewActions().leaveMatch();
        }
    }

    public void logout() {
        boolean answer = ConfirmBox.displaySafeExit();
        if (answer) {
            JavaFXStageProducer.getApp().getViewActions().logout();
        }
    }
}
