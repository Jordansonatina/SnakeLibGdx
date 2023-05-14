package com.jordansonatina.game;

import com.badlogic.gdx.math.Vector2;

import java.util.ArrayList;

public class Snake
{
    private Vector2 position;
    private int length;
    private Vector2[] tail;

    private Vector2 safeDirection;

    public Snake()
    {
        this.position = new Vector2(0, 0);
        length = 1;
        tail = new Vector2[Constants.MAP_SIZE * Constants.MAP_SIZE + 1000];
    }
    public Snake(Vector2 position)
    {
        this.position = position;
        length = 1;
        tail = new Vector2[Constants.MAP_SIZE * Constants.MAP_SIZE];
    }
    public Vector2[] getTail()
    {
        return tail;
    }

    public Vector2 getSafeDirection()
    {
        return safeDirection;
    }

    public void clearTail()
    {
        for (int i = 0; i < length; i++)
        {
            tail[i] = null;
        }
    }

    public Vector2 getPos()
    {
        return position;
    }

    public void setPos(Vector2 position)
    {
        this.position = position;
    }

    public int getLength()
    {
        return length;
    }

    public void setLength(int n)
    {
        length = n;
    }
    public void move(Vector2 direction)
    {
        if (hitTail(direction)) // prevents from going backwards, if we are going to hit our tail in the current direction, reverse dir
        {
            direction.x *= -1;
            direction.y *= -1;
        }
        if (length != 0) {
            for (int i = 0; i < length - 1; i++) {
                tail[i] = tail[i + 1];
            }
            tail[length - 1] = new Vector2(position.x, position.y);
        }
        position.add(direction);

    }
    public void loop()
    {
        if (position.x >= Constants.MAP_SIZE) {
            position.x = 0;
        }
        if (position.x < 0)
        {
            position.x = Constants.MAP_SIZE - 1;
        }
        if (position.y >= Constants.MAP_SIZE) {
            position.y = 0;
        }
        if (position.y < 0)
        {
            position.y = Constants.MAP_SIZE - 1;
        }
    }

    public boolean hitTail()
    {
        for (int i = 0; i < length; i++)
        {
            Vector2 segment = tail[i];
            if (segment != null && segment.x == position.x && segment.y == position.y)
            {
                return true;
            }
        }
        return false;
    }

    public boolean hitTail(Vector2 direction)
    {
        Vector2 projectedPosition = new Vector2(position.x + direction.x, position.y + direction.y);
        for (int i = 0; i < length; i++)
        {
            Vector2 segment = tail[i];
            if (segment != null && segment.x == projectedPosition.x && segment.y == projectedPosition.y)
            {
                return true;
            }
        }
        return false;
    }

    public void eat(Food f)
    {
        if (f.getPos().x == position.x && f.getPos().y == position.y)
        {
            f.pickLocation(this);
            length++;
        }
    }

    public void findSafeDirection()
    {

        if (!hitTail(new Vector2(0, 1)))
            safeDirection = new Vector2(0, 1);

        if (!hitTail(new Vector2(0, -1)))
            safeDirection = new Vector2(0, -1);

        if (!hitTail(new Vector2(1, 0)))
            safeDirection = new Vector2(1, 0);

        if (!hitTail(new Vector2(-1, 0)))
            safeDirection = new Vector2(-1, 0);



    }



}
