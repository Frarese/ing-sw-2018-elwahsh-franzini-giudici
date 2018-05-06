package it.polimi.se2018.view.view_util.cli_creators;

import it.polimi.se2018.util.Pair;
import it.polimi.se2018.view.view_util.ReserveViewCreator;

import java.util.ArrayList;

/**
 * Class to create and to handle reserve in CLI
 *
 * @author Mathyas Giudici
 */

public class CLIReserveViewCreator implements ReserveViewCreator<String> {

    private CLIDieViewCreator cliDieViewCreator;

    private Pair reserve;

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

    public Pair getReserve() {
        return reserve;
    }

    public void setReserve(Pair reserve) {
        this.reserve = reserve;
    }
}
