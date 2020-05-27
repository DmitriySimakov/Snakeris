package com.dmitrysimakov.snakeris.gameEntities;

import com.dmitrysimakov.snakeris.Direction;
import com.dmitrysimakov.snakeris.framework.Settings;

import java.util.ArrayList;
import java.util.List;

public class Snake {

    public Tile head;
    public List<Tile> parts = new ArrayList<>();
    private Direction direction;
    
    public Snake(int length) {
        direction = Direction.RIGHT;
        for (int x = 3 + length - 1; x >= 3; x--) {
            parts.add(new Tile(x, 3));
        }
        head = parts.get(0);
    }

    public boolean setDirection(Direction newDirection) {
        if (head.x == parts.get(1).x) { // Если змея движется вертикально
            // То она может повернуть налево или направо
            if (newDirection == Direction.LEFT || newDirection == Direction.RIGHT) {
                direction = newDirection;
                return true;
            }
        // Иначе может повернуть вверх или вниз
        } else if (newDirection == Direction.UP || newDirection == Direction.DOWN) {
            direction = newDirection;
            return true;
        }
        return false;
    }

    public Direction getDirection() { return direction; }
    
    public void eat(Apple apple) {
        switch (apple.type) {
            case Apple.GREEN:
                parts.remove(parts.size() - 1);
                break;
            case Apple.YELLOW:
                break;
            case Apple.RED:
                Tile end = parts.get(parts.size()-1);
                parts.add(new Tile(end.x, end.y));
                break;
        }
    }
    
    public void move() {
        for(int i = parts.size() - 1; i > 0; i--) {
            Tile before = parts.get(i-1);
            Tile part = parts.get(i);
            part.x = before.x;
            part.y = before.y;
        }

        switch (direction) {
            case UP: head.y -= 1; break;
            case RIGHT: head.x += 1; break;
            case DOWN: head.y += 1; break;
            case LEFT: head.x -= 1; break;
        }
    }
    
    public boolean checkBitten() {
        if(head.x < 0 || head.x > World.WIDTH - 1 || head.y < 0 || head.y > World.HEIGHT / 2 - 1)
            return true;

        for(int i = 4; i < parts.size(); i++) {
            Tile part = parts.get(i);
            if (part.x == head.x && part.y == head.y) return true;
        }        
        return false;
    }
}
