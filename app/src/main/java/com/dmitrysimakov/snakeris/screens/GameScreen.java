package com.dmitrysimakov.snakeris.screens;

import android.graphics.Bitmap;
import android.graphics.Color;
import android.view.MotionEvent;

import com.dmitrysimakov.snakeris.Assets;
import com.dmitrysimakov.snakeris.Direction;
import com.dmitrysimakov.snakeris.Game;
import com.dmitrysimakov.snakeris.framework.Graphics;
import com.dmitrysimakov.snakeris.framework.Settings;
import com.dmitrysimakov.snakeris.framework.TextButton;
import com.dmitrysimakov.snakeris.framework.TouchHandler.TouchEvent;
import com.dmitrysimakov.snakeris.gameEntities.Apple;
import com.dmitrysimakov.snakeris.gameEntities.Snake;
import com.dmitrysimakov.snakeris.gameEntities.Tile;
import com.dmitrysimakov.snakeris.gameEntities.World;

public class GameScreen extends Screen {

    enum GameState { READY, RUNNING, PAUSED, GAME_OVER }

    private GameState state = GameState.READY;
    private World world;
    private Snake snake;
    private boolean newRecord = false;
    private int oldScore = 0;
    private String score = "0";

    private TextButton resumeBtn;
    private TextButton quitBtn;

    public GameScreen(Game game, Settings settings) {
        super(game, settings);

        resumeBtn = new TextButton("Resume", 0, 8 * 16, width, 16);
        quitBtn = new TextButton("Quit", 0, 11 * 16, width, 16);

        world = new World(settings);
        snake = world.getSnake();
    }

    @Override
    public void update(float deltaTime) {
        TouchEvent touchEvent = game.getTouchHandler().getTouchEvent();

        switch (state) {
            case READY: updateReady(touchEvent); break;
            case RUNNING: updateRunning(touchEvent, deltaTime); break;
            case PAUSED: updatePaused(touchEvent); break;
            case GAME_OVER: updateGameOver(touchEvent); break;
        }
    }
    
    private void updateReady(TouchEvent event) {
        if (event != null) state = GameState.RUNNING;
    }
    
    private void updateRunning(TouchEvent event, float deltaTime) {
        if (event != null) {
            if (event.action == MotionEvent.ACTION_UP) {
                if (event.x > 16*9 && event.y > 16*14) {
                    state = GameState.PAUSED;
                    return;
                } else if (event.y >= 16*7 && event.y <= 16*14) {
                    if (event.y < 0.7 * event.x + 16*7 && event.y < -0.7 * event.x + 16*14) {
                        snake.setDirection(Direction.UP);
                    } else if (event.y > 0.7 * event.x + 16*7 && event.y > -0.7 * event.x + 16*14) {
                        snake.setDirection(Direction.DOWN);
                    } else if ((event.y > 0.7 * event.x + 16*7 && event.y < -0.7 * event.x + 16*14)) {
                        snake.setDirection(Direction.LEFT);
                    } else if (event.y < 0.7 * event.x + 16*7 && event.y > -0.7 * event.x + 16*14) {
                        snake.setDirection(Direction.RIGHT);
                    }
                }
            }
        }
        
        world.update(deltaTime);
        if(world.isGameOver()) {
            state = GameState.GAME_OVER;
        }
        if(oldScore != world.getScore()) {
            oldScore = world.getScore();
            score = "" + oldScore;
        }
    }
    
    private void updatePaused(TouchEvent event) {
        if (event != null) {
            if (event.action == MotionEvent.ACTION_UP) {
                if (resumeBtn.pressed(event)) {
                    state = GameState.RUNNING;
                } else if (quitBtn.pressed(event)) {
                    game.setScreen(new MainMenuScreen(game, settings));
                }
            }
        }
    }
    
    private void updateGameOver(TouchEvent event) {
        if (event != null) {
            if (event.action == MotionEvent.ACTION_UP) {
                game.setScreen(new MainMenuScreen(game, settings));
            }
        }
    }
    

    @Override
    public void present(float deltaTime) {
        graphics.drawBitmap(Assets.background, 0, 0);
        drawWorld(world);
        graphics.drawBitmap(Assets.arrows, 0, 0);

        switch (state) {
            case READY:
                graphics.drawText("Tap to start", width / 2, 5*16, 20, Assets.plainTF, Color.WHITE);
                break;
            case RUNNING:
                graphics.drawBitmap(Assets.pause, width - 16, height - 16);
                break;
            case PAUSED:
                graphics.drawTextButton(resumeBtn, Color.WHITE);
                graphics.drawTextButton(quitBtn, Color.WHITE);
                break;
            case GAME_OVER:
                if (!newRecord) newRecord = settings.addScore(world.getScore());
                if (newRecord) {
                    graphics.drawText("NEW RECORD!", width/2, 5*16, 16, Assets.plainTF, Color.WHITE);
                } else {
                    graphics.drawText("GAME OVER", width / 2, 5*16, 16, Assets.plainTF, Color.WHITE);
                }
                break;
        }

        graphics.drawText(score, width / 2, height - 16 + 1,14, Assets.plainTF, Color.WHITE);
    }
    
    private void drawWorld(World world) {
        // Рисуем тетрис
        for (int x = 0; x < World.WIDTH; x++) {
            for (int y = 0; y < World.HEIGHT; y++) {
                if (world.getTiles()[x][y] != null) {
                    graphics.drawBitmap(Assets.tetris, x * 16, y * 16);
                }
            }
        }

        // Рисуем падающую фигуру
        for (Tile part : world.getFigure()) {
            graphics.drawBitmap(Assets.tetris, part.x * 16, part.y * 16);
        }

        // Рисуем яблоко
        Apple apple = world.getApple();
        if (apple != null) {
            Bitmap appleBitmap = null;
            switch (apple.type) {
                case Apple.GREEN:
                    appleBitmap = Assets.appleGreen;
                    break;
                case Apple.YELLOW:
                    appleBitmap = Assets.appleYellow;
                    break;
                case Apple.RED:
                    appleBitmap = Assets.appleRed;
                    break;
            }
            graphics.drawBitmap(appleBitmap, apple.x * 16, apple.y * 16);
        }

        // Рисуем змею
        graphics.drawBitmap(Assets.snake, snake.head.x * 16, snake.head.y * 16, 0, 16, 16, 16);

        for (int i = 1; i < snake.parts.size(); i++) {
            Tile part = snake.parts.get(i);
            graphics.drawBitmap(Assets.snake, part.x * 16, part.y * 16, 0, 0, 16, 16);
        }
    }
    
    @Override
    public void pause() {
        if(state == GameState.RUNNING) state = GameState.PAUSED;
        
        if (newRecord || settings.addScore(world.getScore())) settings.save();
    }
}