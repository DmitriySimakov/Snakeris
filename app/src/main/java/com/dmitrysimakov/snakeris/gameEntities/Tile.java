package com.dmitrysimakov.snakeris.gameEntities;

public class Tile {
    public int x, y;
    
    public Tile(int x, int y) {
        this.x = x;
        this.y = y;
    }

    public Tile(Tile src) {
        x = src.x;
        y = src.y;
    }
}       

