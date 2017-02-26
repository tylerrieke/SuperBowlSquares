package com.rieke.bmore.squares.gameboard;

import com.rieke.bmore.squares.player.Player;

public class Square {
    private final int x;
    private final int y;
    private Player owner;

    public Square(int x, int y) {
        this.x = x;
        this.y = y;
    }

    public Player getOwner() {
        return owner;
    }

    public void setOwner(Player owner) {
        this.owner = owner;
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }
}
