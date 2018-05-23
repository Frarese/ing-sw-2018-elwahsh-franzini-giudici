package it.polimi.se2018.util;

import java.io.Serializable;

class SafeSocketDatagram implements Serializable{
    final Serializable payload;
    final Integer id;

    SafeSocketDatagram(Serializable payload) {
        this.payload = payload;
        this.id = SafeSocket.hashObj(payload);
    }
}
