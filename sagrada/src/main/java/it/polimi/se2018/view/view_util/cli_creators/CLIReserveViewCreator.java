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
     * @param reserve contains the reserve
     */
    public CLIReserveViewCreator(Pair<Integer, ColorModel>[] reserve) {
        super(reserve);
        this.dieViewCreator = new CLIDieViewCreator();
    }

    @Override
    public String display() {
        //Create StringBuilder
        StringBuilder stringBuilder = new StringBuilder();

        //Iterate on reserve
        for (int i = 0; i < this.reserve.length; i++) {
            stringBuilder.append(i + ")" + (String) this.dieViewCreator.makeDie(this.reserve[i]));
            stringBuilder.append("\n");
        }

        //Return
        return stringBuilder.toString();
    }

    @Override
    public String pickDie(int index) {
        //Return die
        return (String) this.dieViewCreator.makeDie(this.reserve[index]);
    }
}
