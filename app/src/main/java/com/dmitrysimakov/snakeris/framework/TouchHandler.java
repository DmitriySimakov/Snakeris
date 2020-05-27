package com.dmitrysimakov.snakeris.framework;

import android.view.MotionEvent;
import android.view.View;

import com.dmitrysimakov.snakeris.Direction;

public class TouchHandler implements View.OnTouchListener {

    public static class TouchEvent {
        public float x, y;
        public int action;
        public Direction direction;

        TouchEvent(float x, float y, int action) {
            this.x = x;
            this.y = y;
            this.action = action;
        }
    }

    private TouchEvent touchEvent;

    private float prevX;
    private float prevY;

    private float scaleX;
    private float scaleY;

    public TouchHandler(View view, float scaleX, float scaleY) {
        view.setOnTouchListener(this);
        this.scaleX = scaleX;
        this.scaleY = scaleY;
    }

    @Override
    synchronized public boolean onTouch(View v, MotionEvent event) {
        float x = event.getX() * scaleX;
        float y = event.getY() * scaleY;
        touchEvent = new TouchEvent(x, y, event.getAction());

        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN:
                prevX = x;
                prevY = y;
                break;
            case MotionEvent.ACTION_UP:
                if (Math.abs(x - prevX) > Math.abs(y - prevY)) {
                    if (x > prevX) {
                        touchEvent.direction = Direction.RIGHT;
                    } else {
                        touchEvent.direction = Direction.LEFT;
                    }
                } else {
                    if (y > prevY) {
                        touchEvent.direction = Direction.DOWN;
                    } else {
                        touchEvent.direction = Direction.UP;
                    }
                }
                break;
        }
        return true;
    }

    synchronized public TouchEvent getTouchEvent() {
        TouchEvent event = touchEvent;
        touchEvent = null;
        return event;
    }
}
