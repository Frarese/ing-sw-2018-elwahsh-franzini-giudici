package it.polimi.se2018.view.tools.cli.creators;

import it.polimi.se2018.model.IntColorPair;
import it.polimi.se2018.view.tools.ReserveViewCreator;

/**
 * Class to create and to handle reserve in CLI
 *
 * @author Mathyas Giudici
 */

public class CLIReserveViewCreator extends ReserveViewCreator<String, String> {


    /**
     * Basic Class constructor that initializes elements at default value
     */
    public CLIReserveViewCreator() {
        super();
        this.dieViewCreator = new CLIDieViewCreator();
    }

    /**
     * Class constructor
     *
     * @param reserve contains the reserve
     */
    public CLIReserveViewCreator(IntColorPair[] reserve) {
        super(reserve);
        this.dieViewCreator = new CLIDieViewCreator();
    }

    @Override
    public String display() {
        //Create StringBuilder
        StringBuilder stringBuilder = new StringBuilder();

        //Iterate on reserve
        for (int i = 0; i < this.reserve.length; i++) {
            if(this.reserve[i]!= null) {
                stringBuilder.append(i).append(") ").append(this.dieViewCreator.makeDie(this.reserve[i]));
                stringBuilder.append("\n");
            }
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
