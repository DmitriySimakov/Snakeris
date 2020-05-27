package com.dmitrysimakov.snakeris.screens;

import com.dmitrysimakov.snakeris.Game;
import com.dmitrysimakov.snakeris.framework.Graphics;
import com.dmitrysimakov.snakeris.framework.Settings;

public abstract class Screen {

    protected final Settings settings;
    protected final Game game;
    protected final Graphics graphics;
    protected final int width;
    protected final int height;

    public Screen(Game game, Settings settings) {
        this.game = game;
        this.settings = settings;
        graphics = game.getGraphics();
        width = graphics.getWidth();
        height = graphics.getHeight();
    }

    public abstract void update(float deltaTime);

    public abstract void present(float deltaTime);

    public abstract void pause();
}
