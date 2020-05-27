package com.dmitrysimakov.snakeris;

import android.app.Activity;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Bitmap.Config;
import android.graphics.Point;
import android.os.Bundle;
import android.os.PowerManager;
import android.os.PowerManager.WakeLock;
import android.view.Window;
import android.view.WindowManager;

import com.dmitrysimakov.snakeris.framework.Graphics;
import com.dmitrysimakov.snakeris.framework.RenderView;
import com.dmitrysimakov.snakeris.framework.Settings;
import com.dmitrysimakov.snakeris.framework.TouchHandler;
import com.dmitrysimakov.snakeris.screens.LoadingScreen;
import com.dmitrysimakov.snakeris.screens.Screen;

public class Game extends Activity {

    public static final int FRAME_BUFFER_WIDTH = 160;
    public static final int FRAME_BUFFER_HEIGHT = 240;

    RenderView renderView;
    Graphics graphics;
    TouchHandler touchHandler;
    Screen screen;

    Settings settings;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        settings = new Settings(getPreferences(MODE_PRIVATE));

        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);

        Bitmap frameBuffer = Bitmap.createBitmap(FRAME_BUFFER_WIDTH, FRAME_BUFFER_HEIGHT, Config.RGB_565);

        Point size = new Point();
        getWindowManager().getDefaultDisplay().getSize(size);
        float scaleX = (float) FRAME_BUFFER_WIDTH / size.x;
        float scaleY = (float) FRAME_BUFFER_HEIGHT / size.y;

        renderView = new RenderView(this, frameBuffer);
        graphics = new Graphics(getAssets(), frameBuffer);
        touchHandler = new TouchHandler(renderView, scaleX, scaleY);
        screen = new LoadingScreen(this, settings);
        setContentView(renderView);
    }

    @Override
    public void onResume() {
        super.onResume();
        renderView.resume();
    }

    @Override
    public void onPause() {
        super.onPause();
        renderView.pause();
        screen.pause();
    }

    public TouchHandler getTouchHandler() { return touchHandler; }

    public Graphics getGraphics() { return graphics; }

    public void setScreen(Screen newScreen) {
        if (newScreen == null) throw new IllegalArgumentException("Screen must not be null");

        screen.pause();
        newScreen.update(0);
        screen = newScreen;
    }

    public Screen getCurrentScreen() { return screen; }
}