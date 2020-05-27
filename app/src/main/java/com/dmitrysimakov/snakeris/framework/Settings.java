package com.dmitrysimakov.snakeris.framework;

import android.content.SharedPreferences;
import android.os.Environment;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;

public class Settings {

    private static final String HIGHSCORE = "highscore";
    private static final String MIN_SNAKE_LEN = "min_snake_len";
    private static final String MAX_SNAKE_LEN = "max_snake_len";
    private SharedPreferences prefs;

    private int highScore;
    private int minSnakeLen;
    private int maxSnakeLen;

    public Settings(SharedPreferences preferences) {
        prefs = preferences;
    }

    public void load() {
        highScore = prefs.getInt(HIGHSCORE, 0);
        minSnakeLen = prefs.getInt(MIN_SNAKE_LEN, 2);
        maxSnakeLen = prefs.getInt(MAX_SNAKE_LEN, 4);
    }

    public void save() {
        prefs.edit()
                .putInt(HIGHSCORE, highScore)
                .putInt(MIN_SNAKE_LEN, minSnakeLen)
                .putInt(MAX_SNAKE_LEN, maxSnakeLen)
                .apply();
    }

    public boolean addScore(int score) {
        if (score > highScore) {
            highScore = score;
            return true;
        }
        return false;
    }

    public int getHighScore() {
        return highScore;
    }

    public int getMinSnakeLen() {
        return minSnakeLen;
    }

    public void setMinSnakeLen(int len) {
        if (len <= maxSnakeLen) minSnakeLen = len;
    }

    public int getMaxSnakeLen() {
        return maxSnakeLen;
    }

    public void setMaxSnakeLen(int len) {
        if (len >= minSnakeLen) maxSnakeLen = len;
    }
}
