package com.dmitrysimakov.snakeris.screens;

import com.dmitrysimakov.snakeris.Assets;
import com.dmitrysimakov.snakeris.Game;
import com.dmitrysimakov.snakeris.framework.Graphics;
import com.dmitrysimakov.snakeris.framework.Settings;

public class LoadingScreen extends Screen {

    public LoadingScreen(Game game, Settings settings) {
        super(game, settings);
    }

    @Override
    public void update(float deltaTime) {
        Assets.background = graphics.loadBitmap("background1.png");
        Assets.pause = graphics.loadBitmap("pause.png");
        Assets.back = graphics.loadBitmap("back.png");
        Assets.arrows = graphics.loadBitmap("arrows.png");
        Assets.snake = graphics.loadBitmap("snake.png");
        Assets.tetris = graphics.loadBitmap("tetris.png");
        Assets.appleGreen = graphics.loadBitmap("apple_green.png");
        Assets.appleYellow = graphics.loadBitmap("apple_yellow.png");
        Assets.appleRed = graphics.loadBitmap("apple_red.png");

        Assets.titleTF = graphics.loadTypeface("title.ttf");
        Assets.plainTF = graphics.loadTypeface("plain.ttf");

        settings.load();
        game.setScreen(new MainMenuScreen(game, settings));
    }

    @Override
    public void present(float deltaTime) {}

    @Override
    public void pause() {}
}