package com.rieke.bmore.squares.gameboard;

public class  Team {
    private String name;
    private String color;
    private String font;

    public Team(String name, String color, String font) {
        this.name = name;
        this.color = color;
        this.font = font;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getColor() {
        return color;
    }

    public void setColor(String color) {
        this.color = color;
    }

    public String getFont() {
        return font;
    }

    public void setFont(String font) {
        this.font = font;
    }
}
