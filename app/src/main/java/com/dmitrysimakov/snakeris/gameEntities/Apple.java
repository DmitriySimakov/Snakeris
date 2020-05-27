package com.dmitrysimakov.snakeris.gameEntities;

public class Apple extends Tile {
    public static final int GREEN = 0;
    public static final int YELLOW = 1;
    public static final int RED = 2;

    public int type;

    public Apple(int x, int y, int type) {
        super(x, y);
        this.type = type;
    }
}
