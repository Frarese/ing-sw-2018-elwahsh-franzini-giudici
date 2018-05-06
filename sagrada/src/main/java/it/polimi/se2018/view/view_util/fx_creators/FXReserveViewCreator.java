package it.polimi.se2018.view.view_util.fx_creators;

import it.polimi.se2018.util.Pair;
import it.polimi.se2018.view.view_util.ReserveViewCreator;
import javafx.scene.Group;

/**
 * Class to create and to handle reserve in GUI
 *
 * @author Mathyas Giudici
 */

public class FXReserveViewCreator implements ReserveViewCreator<Group> {

    private FXDieViewCreator fxDieViewCreator;

    private Pair reserve;


    @Override
    public Group display() {
        //TODO
        return null;
    }

    @Override
    public Group pickDie(int index) {
        //TODO
        return null;
    }

    public Pair getReserve() {
        return reserve;
    }

    public void setReserve(Pair reserve) {
        this.reserve = reserve;
    }
}
