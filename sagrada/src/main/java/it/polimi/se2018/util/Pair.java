package it.polimi.se2018.util;

/**
 * Utility class, added to support mostly information exchanges,
 * in particular to avoid exposing the Die class outside of the model.
 * It represent a tuple of items
 */

public class Pair<E,T> {

    private final E first;
    private final T second;

    /**
     * Constructor for Pair
     * @param first fist item of the pair (ex. color for dice)
     * @param second second item of the pair (ex. value for dice)
     */

    public Pair(E first, T second)
    {
        this.first = first;
        this.second = second;
    }

    /**
     * Getter for the first item
     * @return the first item of the pair
     */
    public E getFirst() {
        return first;
    }

    /**
     * Getter for the second item
     * @return the second value of the pair
     */
    public T getSecond() {
        return second;
    }

    /**
     * Override of the equals() method needed for possible comparisons in
     * the game logic
     * @param o Object equals() must compare the pair with
     * @return true if o and the pair are equal, false otherwise
     */
    @Override
    public boolean equals(Object o)
    {
        if(this == o)
        {
            return true;
        }

        if(o == null || o.getClass() != getClass())
        {
            return false;
        }

        Pair pair = (Pair) o;
       return first.equals(pair.first) && second.equals(pair.second);

    }

    /**
     * Ovveride of the hashCode() method, it makes the class compatible with hash tables
     * For now it uses an "arbitrary" hash generation, may change if needed
     * @return the hash code of the pair
     */
    @Override
    public int hashCode()
    {
        return first.hashCode() ^ second.hashCode();
    }

}
