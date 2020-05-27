package com.dmitrysimakov.snakeris.screens;

import android.graphics.Color;
import android.view.MotionEvent;

import com.dmitrysimakov.snakeris.Assets;
import com.dmitrysimakov.snakeris.framework.Settings;
import com.dmitrysimakov.snakeris.Game;
import com.dmitrysimakov.snakeris.framework.Graphics;
import com.dmitrysimakov.snakeris.framework.TextButton;
import com.dmitrysimakov.snakeris.framework.TouchHandler.TouchEvent;

public class MainMenuScreen extends Screen {

    private TextButton playBtn;
    private TextButton settingsBtn;

    MainMenuScreen(Game game, Settings settings) {
        super(game, settings);

        playBtn = new TextButton("PLAY", 0, 8 * 16, width, 16);
        settingsBtn = new TextButton("SETTINGS", 0, 10 * 16, width, 16);
    }

    @Override
    public void update(float deltaTime) {
        TouchEvent event = game.getTouchHandler().getTouchEvent();
        if (event != null) {
            if(event.action == MotionEvent.ACTION_UP) {
                if(playBtn.pressed(event)) {
                    game.setScreen(new GameScreen(game, settings));
                } else if(settingsBtn.pressed(event)) {
                    game.setScreen(new SettingsScreen(game, settings));
                }
            }
        }
    }

    @Override
    public void present(float deltaTime) {
        graphics.drawBitmap(Assets.background, 0, 0);
        graphics.drawText("SNAKERIS", width/2, 3*16, 18, Assets.titleTF, Color.WHITE);
        graphics.drawTextButton(playBtn, Color.WHITE);
        graphics.drawTextButton(settingsBtn, Color.WHITE);

        for (int i = 0; i < 5; i++) {
            int score = settings.getHighScore();
            if (score > 0) {
                graphics.drawText("HIGH SCORE "+ score, width/2, height - 2*16, 16, Assets.plainTF, Color.WHITE);
            }
        }
    }

    @Override
    public void pause() {}
}
