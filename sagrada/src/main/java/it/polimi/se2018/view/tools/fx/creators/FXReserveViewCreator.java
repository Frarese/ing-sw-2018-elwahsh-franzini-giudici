package it.polimi.se2018.view.tools.fx.creators;

import it.polimi.se2018.model.ColorModel;
import it.polimi.se2018.util.Pair;
import it.polimi.se2018.view.tools.ReserveViewCreator;
import javafx.scene.image.Image;

/**
 * Class to create and to handle reserve in GUI
 *
 * @author Mathyas Giudici
 */

public class FXReserveViewCreator extends ReserveViewCreator<Image> {

    /**
     * Class constructor
     *
     * @param reserve contains the reserve
     */
    public FXReserveViewCreator(Pair<Integer, ColorModel>[] reserve) {
        super(reserve);
        this.dieViewCreator = new FXDieViewCreator();
    }

    @Override
    public Image display() {
        throw new UnsupportedOperationException();
    }

    @Override
    public Image pickDie(int index) {
        throw new UnsupportedOperationException();
    }
}
