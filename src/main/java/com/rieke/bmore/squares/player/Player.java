package com.rieke.bmore.squares.player;

import com.rieke.bmore.squares.connection.Connection;
import com.rieke.bmore.squares.gameboard.Square;

import java.util.HashSet;
import java.util.Set;

public class Player {
    private Connection connection;
    private String name;
    private String display;
    private Set<Square> squares;
    private double balance;
    private boolean active;

    public Player(Connection connection, String name, String display) {
        this.connection = connection;
        this.name = name;
        this.display = display;
        squares = new HashSet<>();
        balance = 0;
        active = false;
    }

    public Connection getConnection() {
        return connection;
    }

    public void setConnection(Connection connection) {
        this.connection = connection;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDisplay() {
        return display;
    }

    public void setDisplay(String display) {
        this.display = display;
    }

    public Set<Square> squares() {
        return squares;
    }

//    public void setSquares(Set<Square> squares) {
//        this.squares = squares;
//    }

    public void addSquare(Square square) {
        squares.add(square);
    }

    public void removeSquare(Square square) {
        squares.remove(square);
    }

    public double getBalance() {
        return balance;
    }

    public void setBalance(double balance) {
        this.balance = balance;
    }

    public boolean isActive() {
        return active;
    }

    public void setActive(boolean active) {
        this.active = active;
    }
}
