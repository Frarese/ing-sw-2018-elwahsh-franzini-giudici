package it.polimi.se2018.controller.network.server;

import it.polimi.se2018.util.ScoreEntry;

import java.io.IOException;
import java.util.List;

/**
 * A DB based implementation of the User Base
 * @author Francesco Franzini
 */
class DBUserBase implements UserBase {


    /**
     * Loads the User Base from the database
     */
    public DBUserBase() throws IOException {
        throw new UnsupportedOperationException();
    }

    @Override
    public boolean containsUser(String usn) {
        throw new UnsupportedOperationException();
    }

    @Override
    public String getPw(String usn) {
        throw new UnsupportedOperationException();
    }

    @Override
    public boolean addUser(String usn, String pw) {
        throw new UnsupportedOperationException();
    }

    @Override
    public boolean removeUser(String usn) {
        throw new UnsupportedOperationException();
    }

    @Override
    public ScoreEntry getUserScore(String usn) {
        throw new UnsupportedOperationException();
    }

    @Override
    public void alterUserScore(String usn, int dTot, int dWins) {
        throw new UnsupportedOperationException();
    }

    @Override
    public List<ScoreEntry> getLeaderBoard() {
        throw new UnsupportedOperationException();
    }
}
