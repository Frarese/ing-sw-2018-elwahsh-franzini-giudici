package it.polimi.se2018.model;

import it.polimi.se2018.util.Pair;

import java.io.Serializable;

public class IntColorPair implements Serializable {
    private Pair<Integer,ColorModel> pair;

    public IntColorPair(Integer first,ColorModel second){
        this.pair=new Pair<>(first,second);
    }


    /**
     * Getter for the first item
     *
     * @return the first item of the pair
     */
    public Integer getFirst() {
        return pair.getFirst();
    }

    /**
     * Getter for the second item
     *
     * @return the second value of the pair
     */
    public ColorModel getSecond() {
        return pair.getSecond();
    }
}
