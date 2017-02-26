package com.rieke.bmore.squares.player;

import com.rieke.bmore.squares.connection.ConnectionFactory;
import com.rieke.bmore.squares.gameboard.Board;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

public class PlayerService {
    private PlayerFactory playerFactory;
    private ConnectionFactory connectionFactory;
    private Board gameboard;

    public PlayerService(PlayerFactory playerFactory, ConnectionFactory connectionFactory) {
        this.playerFactory = playerFactory;
        this.connectionFactory = connectionFactory;
    }

    public void setGameboard(Board gameboard) {
        this.gameboard = gameboard;
    }

    public Board getGameboard() {
        return gameboard;
    }

    public Player getPlayer(String ip) {
        return playerFactory.getPlayer(ip);
    }

    public void makePick(String ip, int x, int y) throws InvalidPlayerException {
        gameboard.markSquare(x,y,getPlayer(ip),false);
    }

    public void update(String ip, String name, String display) {
        Player player = playerFactory.getPlayer(ip);
        player.setName(name);
        player.setDisplay(display);
    }

    public Collection<Player> getAllPlayers() {
        return playerFactory.getAllPlayers();
    }

    public List<Player> getPlayersByOrder(List<String> including) {
        List<Player> players = new ArrayList<>();
        for(String inc:including) {
            players.add(getPlayer(inc));
        }
        for(Player player:getAllPlayers()) {
            if(!player.getConnection().isBlackListed() && !players.contains(player)) {
                players.add(player);
            }
        }
        return players;
    }
}
