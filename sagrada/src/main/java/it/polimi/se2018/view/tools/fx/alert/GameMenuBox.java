package it.polimi.se2018.view.tools.fx.alert;

/**
 * Class to create game menu box to show to user
 *
 * @author Mathyas Giudici
 */

public class GameMenuBox extends GeneralBox {

    /**
     * Class constructor
     */
    private GameMenuBox() {
        super();
    }

    /**
     * Shows the game's menu
     */
    public static void display() {
        initStage("Menu",true , "fxmlFiles/gameMenu.fxml" );
    }
}

