package it.polimi.se2018.view.tools.fx.creators;

import it.polimi.se2018.util.ScoreEntry;
import it.polimi.se2018.view.tools.ScoreViewCreator;
import javafx.scene.Group;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

import java.util.List;

/**
 * Class to create score visualizer in GUI
 *
 * @author Mathyas Giudici
 */

public class FXScoreViewCreator implements ScoreViewCreator<Group> {

    /**
     * Class constructor
     */
    public FXScoreViewCreator() {
        super();
    }

    @Override
    public Group display(List<ScoreEntry> scores) {
        Image bottom = new Image("/it/polimi/se2018/view/images/others/scoreBoard.png");
        ImageView bottomIw = new ImageView(bottom);
        Group finalized = new Group();
        finalized.getChildren().add(bottomIw);

        //TODO
        return finalized;
    }
}
