package com.rieke.bmore.squares.connection;

import java.util.Collection;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class ConnectionFactory {

    private final Map<String,Connection> ipToConnectionMap;

    public ConnectionFactory() {
        ipToConnectionMap = new ConcurrentHashMap<>();
    }

    public synchronized Connection getConnection(String ip) {
        Connection connection = ipToConnectionMap.get(ip);
        if(connection==null) {
            connection = new Connection(ip);
            ipToConnectionMap.put(ip,connection);
        }
        return connection;
    }

    public Collection<Connection> getAllConnections() {
        return ipToConnectionMap.values();
    }
}
