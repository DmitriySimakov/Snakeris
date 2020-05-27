package com.dmitrysimakov.snakeris.screens;

import android.graphics.Color;
import android.view.MotionEvent;

import com.dmitrysimakov.snakeris.Assets;
import com.dmitrysimakov.snakeris.Game;
import com.dmitrysimakov.snakeris.framework.Graphics;
import com.dmitrysimakov.snakeris.framework.Settings;
import com.dmitrysimakov.snakeris.framework.TextButton;
import com.dmitrysimakov.snakeris.framework.TouchHandler.TouchEvent;

public class SettingsScreen extends Screen {

    private TextButton min2;
    private TextButton min3;
    private TextButton min4;
    private TextButton max3;
    private TextButton max4;
    private TextButton max5;

    public SettingsScreen(Game game, Settings settings) {
        super(game, settings);
        min2 = new TextButton("2", width * 1/4, 6*16, 16, 16);
        min3 = new TextButton("3", width * 2/4, 6*16, 16, 16);
        min4 = new TextButton("4", width * 3/4, 6*16, 16, 16);
        max3 = new TextButton("3", width * 1/4, 9*16, 16, 16);
        max4 = new TextButton("4", width * 2/4, 9*16, 16, 16);
        max5 = new TextButton("5", width * 3/4, 9*16, 16, 16);
    }

    @Override
    public void update(float deltaTime) {
        TouchEvent event = game.getTouchHandler().getTouchEvent();
        if (event != null) {
            if (event.action == MotionEvent.ACTION_UP) {
                if (min2.pressed(event)) {
                    settings.setMinSnakeLen(2);
                } else if (min3.pressed(event)) {
                    settings.setMinSnakeLen(3);
                } else if (min4.pressed(event)) {
                    settings.setMinSnakeLen(4);
                } else if (max3.pressed(event)) {
                    settings.setMaxSnakeLen(3);
                } else if (max4.pressed(event)) {
                    settings.setMaxSnakeLen(4);
                } else if (max5.pressed(event)) {
                    settings.setMaxSnakeLen(5);
                } else if (event.x >= width - 40 && event.y >= height - 48) {
                    game.setScreen(new MainMenuScreen(game, settings));
                }
            }
        }
    }

    @Override
    public void present(float deltaTime) {
        graphics.drawBitmap(Assets.background, 0, 0);
        graphics.drawText("SETTINGS", width / 2, 2*16, 16, Assets.plainTF, Color.WHITE);

        graphics.drawText("Snake min length", width / 2, 5*16 - 8, 16, Assets.plainTF, Color.WHITE);
        switch (settings.getMinSnakeLen()) {
            case 2:
                graphics.drawTextButton(min2, Color.YELLOW);
                graphics.drawTextButton(min3, Color.WHITE);
                graphics.drawTextButton(min4, Color.WHITE);
                break;
            case 3:
                graphics.drawTextButton(min2, Color.WHITE);
                graphics.drawTextButton(min3, Color.YELLOW);
                graphics.drawTextButton(min4, Color.WHITE);
                break;
            case 4:
                graphics.drawTextButton(min2, Color.WHITE);
                graphics.drawTextButton(min3, Color.WHITE);
                graphics.drawTextButton(min4, Color.YELLOW);
                break;
        }

        graphics.drawText("Snake max length", width / 2, 8*16 - 8, 16, Assets.plainTF, Color.WHITE);
        switch (settings.getMaxSnakeLen()) {
            case 3:
                graphics.drawTextButton(max3, Color.YELLOW);
                graphics.drawTextButton(max4, Color.WHITE);
                graphics.drawTextButton(max5, Color.WHITE);
                break;
            case 4:
                graphics.drawTextButton(max3, Color.WHITE);
                graphics.drawTextButton(max4, Color.YELLOW);
                graphics.drawTextButton(max5, Color.WHITE);
                break;
            case 5:
                graphics.drawTextButton(max3, Color.WHITE);
                graphics.drawTextButton(max4, Color.WHITE);
                graphics.drawTextButton(max5, Color.YELLOW);
                break;
        }

        graphics.drawBitmap(Assets.back, width - 40, height - 48);
    }

    @Override
    public void pause() {
        settings.save();
    }
}
