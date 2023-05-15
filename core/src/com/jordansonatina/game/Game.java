package com.jordansonatina.game;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.freetype.FreeTypeFontGenerator;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.ScreenUtils;

public class Game extends ApplicationAdapter {

	ShapeRenderer renderer;
	Snake s;
	Food f;

	FreeTypeFontGenerator generator;

	FreeTypeFontGenerator.FreeTypeFontParameter parameter;
	BitmapFont font;

	SpriteBatch batch;

	private int tick = 0;
	private Vector2 direction = new Vector2(0, 0);
	
	@Override
	public void create () {
		renderer = new ShapeRenderer();
		s = new Snake();
		f = new Food();

		batch = new SpriteBatch();

		generator = new FreeTypeFontGenerator(Gdx.files.internal("/Users/johnearnest/Desktop/Snake/assets/ARCADECLASSIC.ttf"));
		parameter = new FreeTypeFontGenerator.FreeTypeFontParameter();
		parameter.size = 50;
		font = generator.generateFont(parameter); // font size 12 pixels
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

	private void drawGrid()
	{
		for (int row = 0; row < Constants.MAP_SIZE; row++)
		{
			for (int col = 0; col < Constants.MAP_SIZE; col++)
			{
				renderer.setColor(Color.DARK_GRAY);
				renderer.rect(col*Constants.CELL_SIZE, row * Constants.CELL_SIZE, Constants.CELL_SIZE, Constants.CELL_SIZE);

			}
		}
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

	public void AI()
	{


		if (s.getPos().y > f.getPos().y && direction.y != 1) {
			direction = new Vector2(0, -1);
		}

		if (s.getPos().y < f.getPos().y && direction.y != -1) {
			direction = new Vector2(0, 1);
		}

		if (s.getPos().x < f.getPos().x && direction.x != -1) {
			direction = new Vector2(1, 0);
		}

		if (s.getPos().x > f.getPos().x && direction.x != 1) {
			direction = new Vector2(-1, 0);
		}

		if (s.hitTail(direction))
		{
			direction = s.getSafeDirection();
		}



	}

	@Override
	public void render () {
		ScreenUtils.clear(0, 0, 0, 1);

		getUserInput();

		//AI();

		renderer.begin(ShapeRenderer.ShapeType.Line);
		drawGrid();
		renderer.end();

		renderer.begin(ShapeRenderer.ShapeType.Filled);
		drawFood();
		drawSnake();
		renderer.end();

		batch.begin();
		font.draw(batch, "Score    " + (s.getLength() - 1), 0, Constants.HEIGHT-10);
		batch.end();

		tick++;
		if (tick > Constants.GAME_SPEED)
			tick = 0;

		s.findSafeDirection(); // find the direction in which you won't hit a tail
		s.loop();
		s.eat(f);
		// update snake one time per second
		if (tick == Constants.GAME_SPEED)
			s.move(direction);



		if (s.hitTail())
		{
			s.clearTail();
			s.setLength(1);
			s.setPos(new Vector2(0, 0));
		}






	}
	
	@Override
	public void dispose () {
		renderer.dispose();
		generator.dispose();
	}
}
