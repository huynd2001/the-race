package com.releasemedaddy.game;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.InputProcessor;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.utils.TimeUtils;

public class TheRace extends ApplicationAdapter {
	public SpriteBatch batch;
	public Assets assets;
	public long lastTimePlayer;
	public int imageUsed;
	public int lastFornite;
	public long lastForniteTime;
	public long startTime;
	public int pasSec;
	public boolean timerStart = false;
	public BitmapFont font;

	public static long processSeed(String s)
	{
		long ans = 0;
		for(int i = 0; i < s.length(); i++)
		{
			ans += (int) s.charAt(i);
		}
		return ans;
	}

	@Override
	public void create () {
		batch = new SpriteBatch();
		font = new BitmapFont();
		Gdx.gl.glClearColor(1.0F, 1.0F, 1.0F, 1.0F);
		this.assets = new Assets();
		this.assets.load();
		Gdx.input.getTextInput(new Input.TextInputListener() {
			@Override
			public void input(String text) {
				assets.maze = new Maze(5, 5, TheRace.processSeed(text));
				timerStart = true;
				startTime = TimeUtils.nanoTime();
			}

			@Override
			public void canceled() {
				assets.maze = new Maze(5, 5, 69);
				timerStart = true;
				startTime = TimeUtils.nanoTime();
			}
		}, "Please Enter Seed!", "", "Enter what special to you");
		lastForniteTime = startTime = lastTimePlayer = TimeUtils.nanoTime();
		pasSec = 0;
		imageUsed = 1;
		lastFornite = 10;
	}

	private void renderNormal()
	{
		int[][] matrix = this.assets.maze.getMaze();
		for (int i = 0; i < this.assets.maze.getArrayRow(); i++) {
			for (int j = 0; j < this.assets.maze.getArrayCol(); j++) {
				switch (matrix[i][j]) {
					case 0:
						batch.draw(this.assets.path[assets.maze.getCode(i, j)], j * 20, (this.assets.maze.getArrayRow() - i - 1) * 20);
						break;
					case 1:
						batch.draw(this.assets.wall, j * 20, (this.assets.maze.getArrayRow() - i - 1) * 20);
						break;
					case 2:
						batch.draw(this.assets.path[assets.maze.getCode(i, j)], j * 20, (this.assets.maze.getArrayRow() - i - 1) * 20);
						if(TimeUtils.nanoTime() - lastTimePlayer > 500000000) {
							imageUsed = 1 - imageUsed;
							lastTimePlayer = TimeUtils.nanoTime();
						}
						batch.draw(this.assets.player[imageUsed], j * 20, (this.assets.maze.getArrayRow() - i - 1) * 20);
						break;
					case 3:
						batch.draw(this.assets.wall, j * 20, (this.assets.maze.getArrayRow() - i - 1) * 20);
						batch.draw(this.assets.target, j * 20, (this.assets.maze.getArrayRow() - i - 1) * 20);
						break;
					default:
						;
				}
			}
		}
		String s ;
		if(TimeUtils.nanoTime() - startTime > 1000000000 && timerStart) {
			startTime = TimeUtils.nanoTime();
			pasSec++;
		}
		s = Integer.toString(30 - pasSec);
		if(timerStart) {
			if(Gdx.input.isTouched())
				batch.draw(assets.fog, Gdx.input.getX() - 420, - Gdx.input.getY());
			else
				batch.draw(assets.black, 0, 0);
		}
		batch.draw(assets.path[assets.maze.getCode(assets.maze.getCurRow(), assets.maze.getCurCol())], assets.maze.getCurCol() * 20, (assets.maze.getArrayRow() - assets.maze.getCurRow() - 1) * 20);
		batch.draw(assets.player[imageUsed], assets.maze.getCurCol() * 20, (assets.maze.getArrayRow() - assets.maze.getCurRow() - 1) * 20);
		batch.draw(assets.wall, assets.maze.getTargetCol() * 20, (assets.maze.getArrayRow() - assets.maze.getTargetRow() - 1) * 20);
		batch.draw(assets.target, assets.maze.getTargetCol() * 20, (assets.maze.getArrayRow() - assets.maze.getTargetRow() - 1) * 20);
		font.draw(batch, s, 0, 420);
	}

	private void renderWon()
	{
		if(TimeUtils.nanoTime() - lastForniteTime > 250000000)
		{
			lastFornite = (lastFornite + 1) % 11;
			lastForniteTime = TimeUtils.nanoTime();
		}
		batch.draw(this.assets.fornite[lastFornite], 0, 0);
	}

	private void renderLose()
	{
		batch.draw(assets.doge, 0, 0);
	}

	@Override
	public void render () {
		batch.begin();
		if(pasSec == 30)
		{
			assets.maze.setEnded();
		}
		if(!assets.maze.isEnded())
			this.renderNormal();
		else if(assets.maze.isWon())
			this.renderWon();
		else
			this.renderLose();
		batch.end();
		Gdx.input.setInputProcessor(new InputProcessor() {
			@Override
			public boolean keyDown(int keycode) {
				switch(keycode)
				{
					case Input.Keys.A:
					case Input.Keys.LEFT:
						assets.maze.move(0);
						break;
					case Input.Keys.D:
					case Input.Keys.RIGHT:
						assets.maze.move(1);
						break;
					case Input.Keys.S:
					case Input.Keys.DOWN:
						assets.maze.move(2);
						break;
					case Input.Keys.W:
					case Input.Keys.UP:
						assets.maze.move(3);
						break;
					default:
						;
				}
				return true;
			}

			@Override
			public boolean keyUp(int keycode) {
				return false;
			}

			@Override
			public boolean keyTyped(char character) {
				return false;
			}

			@Override
			public boolean touchDown(int screenX, int screenY, int pointer, int button) {
				return false;
			}

			@Override
			public boolean touchUp(int screenX, int screenY, int pointer, int button) {
				return false;
			}

			@Override
			public boolean touchDragged(int screenX, int screenY, int pointer) {
				return false;
			}

			@Override
			public boolean mouseMoved(int screenX, int screenY) {
				return false;
			}

			@Override
			public boolean scrolled(int amount) {
				return false;
			}
		});
	}
	
	@Override
	public void dispose () {
		batch.dispose();
		assets.dispose();
	}
}
