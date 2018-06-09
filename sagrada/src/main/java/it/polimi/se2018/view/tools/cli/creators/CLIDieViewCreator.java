package it.polimi.se2018.view.tools.cli.creators;

import it.polimi.se2018.model.IntColorPair;
import it.polimi.se2018.view.tools.DieViewCreator;

/**
 * Class to create dice in CLI
 *
 * @author Mathyas Giudici
 */

public class CLIDieViewCreator implements DieViewCreator<String> {

    @Override
    public String makeDie(IntColorPair die) {
        return die.getFirst().toString() + "-" + die.getSecond().toString();
    }
}
