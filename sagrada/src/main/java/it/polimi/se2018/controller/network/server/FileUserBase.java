package it.polimi.se2018.controller.network.server;

import it.polimi.se2018.util.ScoreEntry;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;

/**
 * A file based implementation of the User Base
 *
 * @author Francesco Franzini
 */
class FileUserBase extends UserBase {
    private HashMap userMap;
    private final String userFileName = "users.csv";

    /**
     * Loads from file the User Base
     */
    public FileUserBase() throws IOException {
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
    public void alterUserScore(String usn, Integer dTot, Integer dWins) {
        throw new UnsupportedOperationException();
    }

    @Override
    public List<ScoreEntry> getLeaderBoard() {
        throw new UnsupportedOperationException();
    }
}
