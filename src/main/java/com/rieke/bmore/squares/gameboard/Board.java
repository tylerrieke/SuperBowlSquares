package com.rieke.bmore.squares.gameboard;

import com.rieke.bmore.squares.player.InvalidPlayerException;
import com.rieke.bmore.squares.player.Player;

import java.util.List;

public class Board {

    public enum State {
        REGISTRATION,PICKING,PAUSED,CLOSED
    }

    private Square[][] squares;
    private Team teamX;
    private Team teamY;
    private List<Player> players;
    private Player activePlayer;
    private State state;

    public Board(Team teamX, Team teamY) {
        this.teamX = teamX;
        this.teamY = teamY;
        initSquares();
        state = State.REGISTRATION;
    }

    private void initSquares() {
        squares = new Square[10][10];
        for(int x=0;x<squares.length;x++) {
            Square[] column = squares[x];
            for(int y=0;y<column.length;y++) {
                column[y]=new Square(x,y);
            }
        }
    }

    public void setPlayers(List<Player> players) {
        this.players = players;
        if(activePlayer==null || !players.contains(activePlayer)) {
            rotatePlayer();
        }
    }

    public void rotatePlayer(String ip) {
        if(activePlayer!=null && !activePlayer.getConnection().getIp().equals(ip)) {
            return;
        }
        rotatePlayer();
    }

    public void rotatePlayer() {
        Player player;
        if(activePlayer==null) {
            player = players.get(0);
        } else {
            try {
                player = players.get(players.indexOf(activePlayer) + 1);
            } catch (IndexOutOfBoundsException e) {
                player = players.get(0);
            }
        }
        setActivePlayer(player);
    }

    public void markSquare(int x, int y, Player player, boolean force) throws InvalidPlayerException {
        if(!force) {
            validatePlayer(player);
            if(squares[x][y].getOwner()!=null) {
                throw new InvalidPlayerException();
            }
        }
        Square square = squares[x][y];
        Player owner = square.getOwner();
        if(owner!=null) {
            owner.removeSquare(square);
        }
        squares[x][y].setOwner(player);
        if(player!=null) {
            player.addSquare(square);
        }
        if(!force) {
            rotatePlayer();
        }
    }

    private void validatePlayer(Player player) throws InvalidPlayerException {
        switch (state) {
            case PICKING:
                if(activePlayer!=player) {
                    throw new InvalidPlayerException();
                }
                break;
            case CLOSED:
            case REGISTRATION:
                if(!players.contains(player)) {
                    throw new InvalidPlayerException();
                }
        }


    }

    public Square[][] getSquares() {
        return squares;
    }

    public Team getTeamX() {
        return teamX;
    }

    public Team getTeamY() {
        return teamY;
    }

    public State getState() {
        return state;
    }

    public List<Player> getPlayers() {
        return players;
    }

    public Player getActivePlayer() {
        return activePlayer;
    }

    public void setActivePlayer(Player activePlayer) {
        if(this.activePlayer!=null) {
            this.activePlayer.setActive(false);
        }
        if(activePlayer!=null) {
            activePlayer.setActive(true);
        }
        this.activePlayer = activePlayer;
    }

    public void setState(State state) {
        this.state = state;
    }
}
