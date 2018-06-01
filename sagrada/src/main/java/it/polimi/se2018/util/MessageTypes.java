package it.polimi.se2018.util;

import java.io.Serializable;

/**
 * Represents the three different types of destination for a chat message
 *
 * @author Francesco Franzini
 */
public enum MessageTypes implements Serializable {
    PM, BROADCAST, MATCH
}
