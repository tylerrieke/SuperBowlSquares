package com.rieke.bmore.squares.player;

import com.rieke.bmore.squares.connection.Connection;
import com.rieke.bmore.squares.connection.ConnectionFactory;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class PlayerFactory {
    private final ConnectionFactory connectionFactory;
    private final Map<Connection,Player> connectionPlayerMap;
    private int playerCount=0;

    public PlayerFactory(ConnectionFactory connectionFactory) {
        this.connectionFactory = connectionFactory;
        connectionPlayerMap = new ConcurrentHashMap<>();
    }

    public synchronized Player getPlayer(String ip) {
        Connection connection = connectionFactory.getConnection(ip);
        Player player = connectionPlayerMap.get(connection);
        if(player==null) {
            player = new Player(connection, "NewPlayer"+(++playerCount),"P"+playerCount);
            connectionPlayerMap.put(connection,player);
        }
        return player;
    }

    public Collection<Player> getAllPlayers() {
        return connectionPlayerMap.values();
    }
}
