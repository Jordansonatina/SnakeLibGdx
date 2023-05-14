package com.jordansonatina.game;

import com.badlogic.gdx.math.Vector2;

public class Food {
    private Vector2 position;

    public Food()
    {
        position = new Vector2((int)(Math.random() * Constants.MAP_SIZE), (int)(Math.random() * Constants.MAP_SIZE));
    }
    public Vector2 getPos()
    {
        return position;
    }
    public void pickLocation(Snake s)
    {
        position = new Vector2((int)(Math.random() * Constants.MAP_SIZE), (int)(Math.random() * Constants.MAP_SIZE));

    }
}
