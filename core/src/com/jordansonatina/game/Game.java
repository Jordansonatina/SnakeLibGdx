package com.jordansonatina.game;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.ScreenUtils;

public class Game extends ApplicationAdapter {

	ShapeRenderer renderer;
	Snake s;
	Food f;

	private int tick = 0;
	private Vector2 direction = new Vector2(0, 0);
	
	@Override
	public void create () {
		renderer = new ShapeRenderer();
		s = new Snake();
		f = new Food();
	}

	private void drawSnake()
	{
		renderer.setColor(Color.LIGHT_GRAY);
		renderer.rect(s.getPos().x * Constants.CELL_SIZE, s.getPos().y * Constants.CELL_SIZE, Constants.CELL_SIZE, Constants.CELL_SIZE);
		renderer.setColor(Color.WHITE);
		renderer.rect(s.getPos().x * Constants.CELL_SIZE + 4, s.getPos().y * Constants.CELL_SIZE + 4, Constants.CELL_SIZE - 8, Constants.CELL_SIZE - 8);
		for (int i = 0; i < s.getLength(); i++)
		{
			if (s.getTail()[i] != null)
			{
				renderer.setColor(Color.LIGHT_GRAY);
				renderer.rect(s.getTail()[i].x * Constants.CELL_SIZE, s.getTail()[i].y * Constants.CELL_SIZE, Constants.CELL_SIZE, Constants.CELL_SIZE);
				renderer.setColor(Color.WHITE);
				renderer.rect(s.getTail()[i].x * Constants.CELL_SIZE + 4, s.getTail()[i].y * Constants.CELL_SIZE + 4, Constants.CELL_SIZE - 8, Constants.CELL_SIZE - 8);

			}
		}
	}
	private void drawFood()
	{
		renderer.setColor(new Color(0.8f, 0f, 0f, 1f));
		renderer.rect(f.getPos().x * Constants.CELL_SIZE, f.getPos().y * Constants.CELL_SIZE, Constants.CELL_SIZE, Constants.CELL_SIZE);
		renderer.setColor(new Color(1f, 0f, 0f, 1f));
		renderer.rect(f.getPos().x * Constants.CELL_SIZE + 4, f.getPos().y * Constants.CELL_SIZE + 4, Constants.CELL_SIZE - 8, Constants.CELL_SIZE - 8);
	}

	private void getUserInput()
	{
		if (Gdx.input.isKeyJustPressed(Input.Keys.RIGHT))
		{
			direction = new Vector2(1, 0);
		}
		if (Gdx.input.isKeyJustPressed(Input.Keys.LEFT))
		{
			direction = new Vector2(-1, 0);
		}
		if (Gdx.input.isKeyJustPressed(Input.Keys.UP))
		{
			direction = new Vector2(0, 1);
		}
		if (Gdx.input.isKeyJustPressed(Input.Keys.DOWN))
		{
			direction = new Vector2(0, -1);
		}
	}

	@Override
	public void render () {
		ScreenUtils.clear(0, 0, 0, 1);

		getUserInput();

		renderer.begin(ShapeRenderer.ShapeType.Filled);
		drawFood();
		drawSnake();
		renderer.end();

		tick++;
		if (tick > Constants.FRAME_RATE)
			tick = 0;

		s.loop();
		s.eat(f);
		// update snake one time per second
		if (tick == Constants.FRAME_RATE)
			s.move(direction);


	}
	
	@Override
	public void dispose () {
		renderer.dispose();
	}
}
