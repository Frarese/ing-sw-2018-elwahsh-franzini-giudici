package it.polimi.se2018.view.tools.fx.creators;

import it.polimi.se2018.util.ScoreEntry;
import it.polimi.se2018.view.tools.ScoreViewCreator;
import javafx.scene.Group;

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
        throw new UnsupportedOperationException();
    }
}
