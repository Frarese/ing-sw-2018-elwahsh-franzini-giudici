package it.polimi.se2018.util;

import java.io.Serializable;

/**
 * Helper class used to wrap an object's id
 * @author Francesco Franzini
 */
public class SafeSocketACK implements Serializable {

    int id;

    /**
     *Initializes the id to be stored
     * @param id the id to store
     */
    public SafeSocketACK(int id) {
        this.id=id;
    }


}