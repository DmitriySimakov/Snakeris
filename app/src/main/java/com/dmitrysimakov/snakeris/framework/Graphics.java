package com.dmitrysimakov.snakeris.framework;

import android.content.res.AssetManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.graphics.Typeface;

import java.io.IOException;
import java.io.InputStream;

public class Graphics {

    private AssetManager assetManager;
    private Bitmap frameBuffer;
    private Canvas canvas;
    private Rect srcRect = new Rect();
    private Rect dstRect = new Rect();

    public Graphics(AssetManager assetManager, Bitmap frameBuffer) {
        this.assetManager = assetManager;
        this.frameBuffer = frameBuffer;
        this.canvas = new Canvas(frameBuffer);
    }

    public Bitmap loadBitmap(String fileName) {
        Bitmap bitmap;
        try (InputStream in = assetManager.open(fileName)) {
            bitmap = BitmapFactory.decodeStream(in);
            if (bitmap == null) throw new RuntimeException("Couldn't load bitmap from asset: " + fileName);
        } catch (IOException e) {
            throw new RuntimeException("Couldn't load bitmap from asset: "+ fileName);
        }
        return bitmap;
    }

    public Typeface loadTypeface(String fileName) {
        return Typeface.createFromAsset(assetManager, fileName);
    }

    public void drawBitmap(Bitmap bitmap, int x, int y, int srcX, int srcY, int srcWidth, int srcHeight) {
        srcRect.left = srcX;
        srcRect.top = srcY;
        srcRect.right = srcX + srcWidth;
        srcRect.bottom = srcY + srcHeight;

        dstRect.left = x;
        dstRect.top = y;
        dstRect.right = x + srcWidth;
        dstRect.bottom = y + srcHeight;

        canvas.drawBitmap(bitmap, srcRect, dstRect, null);
    }

    public void drawBitmap(Bitmap bitmap, int x, int y) {
        canvas.drawBitmap(bitmap, x, y, null);
    }

    public void drawText(String text, int x, int y, int maxHeight, Typeface tf, int color) {
        y += maxHeight;

        Paint paint = new Paint();
        paint.setTextAlign(Paint.Align.CENTER);
        paint.setTypeface(tf);
        float size = getTextSizeByHeight(paint, maxHeight - 1, text);
        paint.setTextSize(size);

        paint.setColor(0xFF686868);
        canvas.drawText(text, x, y, paint);

        paint.setColor(color);
        canvas.drawText(text, x - 1, y - 1, paint);
    }

    public void drawTextButton(TextButton button, int color) {
        drawText(button.text, button.x + button.width/2, button.y, button.height, button.typeface, color);
    }

    private static float getTextSizeByHeight(Paint paint, int maxHeight, String text) {
        Rect bounds = new Rect();
        paint.getTextBounds(text, 0, text.length(), bounds);
        return paint.getTextSize() * maxHeight / bounds.height();
    }

    public int getWidth() {
        return frameBuffer.getWidth();
    }

    public int getHeight() {
        return frameBuffer.getHeight();
    }
}
