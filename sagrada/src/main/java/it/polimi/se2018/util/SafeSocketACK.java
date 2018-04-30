package it.polimi.se2018.util;

/**
 * Helper class used to wrap an object's id
 * @author Francesco Franzini
 */
class SafeSocketACK {

    public final int id;

    /**
     *Initializes the id to be stored
     * @param id the id to store
     */
    public SafeSocketACK(int id) {
        this.id=id;
    }


}