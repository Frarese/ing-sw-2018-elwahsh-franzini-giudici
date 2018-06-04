package it.polimi.se2018.view.tools.fx.creators;

import it.polimi.se2018.util.MatchIdentifier;
import it.polimi.se2018.view.tools.ScoreViewCreator;
import javafx.scene.Group;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.VBox;

/**
 * Class to create score visualizer in GUI
 *
 * @author Mathyas Giudici
 */

public class FXScoreViewCreator implements ScoreViewCreator<VBox> {

    /**
     * Class constructor
     */
    public FXScoreViewCreator() {
        super();
    }

    @Override
    public VBox display(MatchIdentifier matchIdentifier, int player0, int player1, int player2, int player3) {
        Image bottom = new Image("/it/polimi/se2018/view/images/others/scoreBoard.png");
        ImageView bottomIw = new ImageView(bottom);
        Group finalized = new Group();
        finalized.getChildren().add(bottomIw);

        //TODO FIX
        return new VBox();
    }
}
