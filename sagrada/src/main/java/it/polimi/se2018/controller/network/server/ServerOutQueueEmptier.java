package it.polimi.se2018.controller.network.server;


import it.polimi.se2018.controller.network.client.ThreadHandler;

/**
 * Object that empties the outbound queue of the server
 * @author Francesco Franzini
 */
class ServerOutQueueEmptier extends ThreadHandler {
    private final Client client;

    /**
     * Builds this handler with the given parameters
     * @param client the client to call
     * @param isReq flag to choose request or object stream
     */
    public ServerOutQueueEmptier(Client client, boolean isReq) {
        this.client=client;
        throw new UnsupportedOperationException();
    }

    @Override
    public void methodToCall() {
        throw new UnsupportedOperationException();
    }


}
