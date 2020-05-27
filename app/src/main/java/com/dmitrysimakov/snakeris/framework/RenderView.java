package com.dmitrysimakov.snakeris.framework;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Rect;
import android.view.SurfaceHolder;
import android.view.SurfaceView;

import com.dmitrysimakov.snakeris.Game;

public class RenderView extends SurfaceView implements Runnable {

    private Game game;
    private Bitmap framebuffer;
    private Thread renderThread;
    private SurfaceHolder holder;
    volatile boolean running = false;
    
    public RenderView(Game game, Bitmap framebuffer) {
        super(game);
        this.game = game;
        this.framebuffer = framebuffer;
        this.holder = getHolder();
    }

    public void resume() { 
        running = true;
        renderThread = new Thread(this);
        renderThread.start();         
    }      
    
    public void run() {
        Rect rect = new Rect();
        long startTime = System.nanoTime();
        while(running) {  
            if(!holder.getSurface().isValid()) continue;
            
            float deltaTime = (System.nanoTime() - startTime) / 1000000000.0f;
            startTime = System.nanoTime();

            game.getCurrentScreen().update(deltaTime);
            game.getCurrentScreen().present(deltaTime);
            
            Canvas canvas = holder.lockCanvas();
            canvas.getClipBounds(rect);
            canvas.drawBitmap(framebuffer, null, rect, null);
            holder.unlockCanvasAndPost(canvas);
        }
    }

    public void pause() {                        
        running = false;                        
        while(true) {
            try {
                renderThread.join();
                break;
            } catch (InterruptedException e) {
                // retry
            }
        }
    }        
}