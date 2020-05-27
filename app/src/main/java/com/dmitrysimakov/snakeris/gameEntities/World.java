package com.dmitrysimakov.snakeris.gameEntities;

import android.util.Log;

import com.dmitrysimakov.snakeris.framework.Settings;

import java.util.List;
import java.util.Random;

public class World {

    public static final int WIDTH = 10;
    public static final int HEIGHT = 14;

    private Settings settings;

    private Snake snake;
    private Tetris tetris = new Tetris();
    private Apple apple;

    private boolean gameOver = false;
    private int score = 0;
    private int speedScore = 0;

    private Random random = new Random();

    private static final float TICK_DECREMENT = 0.0125f;
    private float tickTime = 0;
    private static float tick = 0.125f;
    private int ticks = 0;

    public World(Settings settings) {
        this.settings = settings;
        snake = new Snake(settings.getMaxSnakeLen());
        placeApple();
    }

    private void placeApple() {
        boolean occupiedPlaces[][] = new boolean[WIDTH][HEIGHT / 2];
        int[] arr = new int[WIDTH];

        for (Tile part : snake.parts) {
            occupiedPlaces[part.x][part.y] = true;
        }
        Tile head = snake.head;
        switch (snake.getDirection()) {
            case UP:
                if (head.y > 0) occupiedPlaces[head.x][head.y - 1] = true;
                break;
            case RIGHT:
                if (head.x < WIDTH - 1) occupiedPlaces[head.x + 1][head.y] = true;
                break;
            case DOWN:
                if (head.y < HEIGHT / 2 - 1) occupiedPlaces[head.x][head.y + 1] = true;
                break;
            case LEFT:
                if (head.x > 0) occupiedPlaces[head.x - 1][head.y] = true;
                break;
        }

        int len = 0;
        for (int x = 0; x < WIDTH; x++) {
            for (int y = HEIGHT / 2; y < HEIGHT; y++) {
                if (getTiles()[x][y] == null) {
                    arr[x]++;
                    len++;
                } else {
                    break;
                }
            }
        }

        int r = random.nextInt(len);
        Log.d("TAG", "len = "+ len +" r = "+ r +"tick = "+ tick);
        int appleX = 0;
        while (r>=0) {
            if (arr[appleX] > 0) {
                arr[appleX]--;
                r--;
            } else {
                appleX++;
            }
        }

        int appleY = random.nextInt(HEIGHT / 2);
        while (occupiedPlaces[appleX][appleY]) {
            appleY++;
            if (appleY >= HEIGHT/2) {
                appleY = 0;
            }
        }

        int appleType;
        if (settings.getMinSnakeLen() == settings.getMaxSnakeLen()) {
            appleType = 1;
        } else if (snake.parts.size() == settings.getMaxSnakeLen()) {
            appleType = random.nextInt(2);
        } else if(snake.parts.size() == settings.getMinSnakeLen()) {
            appleType = random.nextInt(2) + 1;
        } else {
            appleType = random.nextInt(3);
        }
        apple = new Apple(appleX, appleY, appleType);
    }

    public void update(float deltaTime) {
        if (gameOver) return;

        tickTime += deltaTime;

        while (tickTime > tick) {
            tickTime -= tick;
            ticks++;

            if (apple == null && getFigure().size() == 0) {
                placeApple();
            }

            if (ticks % 4 == 0) {
                snake.move();
                if (snake.checkBitten()) {
                    gameOver = true;
                    return;
                }

                Tile head = snake.head;
                if (apple != null && head.x == apple.x && head.y == apple.y) {
                    score += snake.parts.size();
                    speedScore += snake.parts.size();

                    tetris.createFigure(snake.parts);
                    snake.eat(apple);
                    apple = null;

                    if (speedScore > 100  && tick > TICK_DECREMENT) {
                        tick -= TICK_DECREMENT;
                        speedScore = 0;
                    }
                }
            }

            if (tetris.update()) {
                int removedLines = tetris.removeFilledLines();
                for (int x = 0; x < World.WIDTH; x++) {
                    if (getTiles()[x][World.HEIGHT/2 - 1] != null) {
                        gameOver = true;
                        return;
                    }
                }

                for (int i = 1; i <= removedLines; i++) {
                    score += i*10;
                    speedScore += i*10;
                }
            }
        }
    }

    public Snake getSnake() {
        return snake;
    }

    public List<Tile> getFigure() {
        return tetris.getFigure();
    }

    public Tile[][] getTiles() {
        return tetris.getTiles();
    }

    public Apple getApple() {
        return apple;
    }

    public boolean isGameOver() {
        return gameOver;
    }

    public int getScore() {
        return score;
    }
}
