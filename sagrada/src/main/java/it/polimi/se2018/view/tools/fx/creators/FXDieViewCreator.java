package it.polimi.se2018.view.tools.fx.creators;

import it.polimi.se2018.model.IntColorPair;
import it.polimi.se2018.view.tools.DieViewCreator;
import javafx.scene.image.Image;

/**
 * Class to create dice in GUI
 *
 * @author Mathyas Giudici
 */

public class FXDieViewCreator implements DieViewCreator<Image> {

    private final int imageSize;

    public FXDieViewCreator(int imageSize) {
        this.imageSize = imageSize;
    }

    @Override
    public Image makeDie(IntColorPair die) {
        String url = "/it/polimi/se2018/view/images/die/val" + die.getFirst().toString() + "c" + die.getSecond().toString() + ".png";
        return new Image(url, imageSize, imageSize, true, false);
    }
}
