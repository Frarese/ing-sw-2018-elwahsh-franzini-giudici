package it.polimi.se2018.view.view_util.fx_creators;

import it.polimi.se2018.model.ColorModel;
import it.polimi.se2018.util.Pair;
import it.polimi.se2018.view.view_util.DieViewCreator;
import javafx.scene.image.Image;

/**
 * Class to create dice in GUI
 *
 * @author Mathyas Giudici
 */

public class FXDieViewCreator implements DieViewCreator<Image> {

    @Override
    public Image makeDie(Pair<Integer, ColorModel> die) {
        return new Image("/it/polimi/se2018/view/view_img/die/value_color/val"+die.getFirst()+"c"+die.getSecond().toString()+".png");
    }
}
