package com.dmitrysimakov.snakeris.gameEntities;

import java.util.ArrayList;
import java.util.List;

public class Tetris {

    private List<Tile> figure = new ArrayList<>();
    private Tile[][] tiles = new Tile[World.WIDTH][World.HEIGHT];

    public void createFigure(List<Tile> snake) {
        for (Tile part : snake) {
            figure.add(new Tile(part));
        }
    }

    public boolean update() {
        if (figure.size() != 0) {
            if (!canFall()) {
                joinFigure();
                figure.clear();
                return true;
            } else {
                for (Tile part : figure) part.y++;
            }
        }
        return false;
    }

    private boolean canFall() {
        for (Tile part : figure) {
            if (part.y + 1 == World.HEIGHT || tiles[part.x][part.y + 1] != null) return false;
        }
        return true;
    }

    private void joinFigure() {
        for (Tile part : figure) {
            tiles[part.x][part.y] = part;
        }
    }

    public int removeFilledLines() {
        int removeCnt = 0;
        for (int y = World.HEIGHT - 1; y > 0; ) {
            boolean filled = true;
            for (int x = 0; x < World.WIDTH; x++) {
                if (tiles[x][y] == null) {
                    filled = false;
                    break;
                }
            }

            if (filled) {
                removeCnt++;
                for (int x = 0; x < World.WIDTH; x++) {
                    tiles[x][y] = null;
                    System.arraycopy(tiles[x], 0, tiles[x], 1, y);
                }
            } else {
                y--;
            }
        }
        return removeCnt;
    }

    public List<Tile> getFigure() {
        return figure;
    }

    public Tile[][] getTiles() {
        return tiles;
    }
}
