package it.polimi.se2018.view.view_util.cli_creators;

import it.polimi.se2018.model.ColorModel;
import it.polimi.se2018.util.Pair;
import it.polimi.se2018.view.view_util.ReserveViewCreator;

/**
 * Class to create and to handle reserve in CLI
 *
 * @author Mathyas Giudici
 */

public class CLIReserveViewCreator extends ReserveViewCreator<String> {

    /**
     * Class constructor
     *
     * {@link ReserveViewCreator}
     */
    public CLIReserveViewCreator(Pair<Integer, ColorModel>[] reserve) {
        super(reserve);
        this.dieViewCreator = new CLIDieViewCreator();
    }

    @Override
    public String display() {
        //TODO
        return null;
    }

    @Override
    public String pickDie(int index) {
        //TODO
        return null;
    }
}
