package it.polimi.se2018.view.view_util.cli_creators;

import it.polimi.se2018.model.ColorModel;
import it.polimi.se2018.util.Pair;
import it.polimi.se2018.view.view_util.DieViewCreator;

/**
 * Class to create dice in CLI
 *
 * @author Mathyas Giudici
 */

public class CLIDieViewCreator implements DieViewCreator<String> {

    @Override
    public String makeDie(Pair<Integer, ColorModel> die) {
        return die.getFirst().toString() + "-" + die.getSecond().toString();
    }
}
