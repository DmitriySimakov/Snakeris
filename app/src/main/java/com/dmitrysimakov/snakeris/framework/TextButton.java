package com.dmitrysimakov.snakeris.framework;

import android.graphics.Typeface;

import com.dmitrysimakov.snakeris.Assets;
import com.dmitrysimakov.snakeris.framework.TouchHandler.TouchEvent;

public class TextButton {

    public String text;
    public int x;
    public int y;
    public int width;
    public int height;
    public Typeface typeface = Assets.plainTF;

    public TextButton(String text, int x, int y, int width, int height) {
        this.text = text;
        this.x = x;
        this.y = y;
        this.width = width;
        this.height = height;
    }

    public boolean pressed(TouchEvent event) {
        return event.x > x && event.x < x + width - 1 && event.y > y && event.y < y + height - 1;
    }
}
