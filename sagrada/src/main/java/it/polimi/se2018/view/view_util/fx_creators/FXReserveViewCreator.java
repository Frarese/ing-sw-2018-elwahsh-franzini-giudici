package it.polimi.se2018.view.view_util.fx_creators;

import it.polimi.se2018.model.ColorModel;
import it.polimi.se2018.util.Pair;
import it.polimi.se2018.view.view_util.ReserveViewCreator;
import javafx.scene.image.Image;

/**
 * Class to create and to handle reserve in GUI
 *
 * @author Mathyas Giudici
 */

public class FXReserveViewCreator extends ReserveViewCreator<Image> {

    /**
     * Class constructor
     * <p>
     * {@link ReserveViewCreator}
     */
    public FXReserveViewCreator(Pair<Integer, ColorModel>[] reserve) {
        super(reserve);
        this.dieViewCreator = new FXDieViewCreator();
    }

    @Override
    public Image display() {
        //TODO
        return null;
    }

    @Override
    public Image pickDie(int index) {
        //TODO
        return null;
    }
}