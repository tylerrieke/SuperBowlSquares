package com.rieke.bmore.squares.admin;

import com.rieke.bmore.squares.gameboard.Board;
import com.rieke.bmore.squares.player.InvalidPlayerException;
import com.rieke.bmore.squares.player.Player;
import com.rieke.bmore.squares.player.PlayerService;

import java.util.ArrayList;
import java.util.List;

public class AdminService {

    private Board gameboard;
    private PlayerService playerService;
    private List<String> playerOrder = new ArrayList<>();

    public AdminService(PlayerService playerService) {
        this.playerService = playerService;
    }

    public void setGameboard(Board gameboard) {
        this.gameboard = gameboard;
    }

    public Board getGameboard() {
        return gameboard;
    }

    public List<String> getPlayerOrder() {
        return playerOrder;
    }

    public void setPlayerOrder(List<String> playerOrder) {
        this.playerOrder = playerOrder;
    }

    public void setBlackListed(String ip) {
        Player player = playerService.getPlayer(ip);
        player.getConnection().setBlackListed(true);
    }

    public void activatePlayer(String ip) {
        Player player = playerService.getPlayer(ip);
        gameboard.setActivePlayer(player);
    }

    public List<Player> getOrderedPlayers() {
        return playerService.getPlayersByOrder(playerOrder);
    }

    public void setState(String state) {
        Board.State value;
        try {
            value = Board.State.valueOf(state);
        } catch (IllegalArgumentException e) {
            value = Board.State.REGISTRATION;
        }
        switch(value) {
            case PICKING:
                gameboard.setPlayers(getOrderedPlayers());
                break;
            case CLOSED:
                gameboard.setActivePlayer(null);
                break;
        }
        gameboard.setState(value);
    }

    public void setSquareOwner(int x, int y, String ip) throws InvalidPlayerException {
        gameboard.markSquare(x,y,(ip!=null?playerService.getPlayer(ip):null),true);
    }
}
