package com.releasemedaddy.game.desktop;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.backends.lwjgl.LwjglApplication;
import com.badlogic.gdx.backends.lwjgl.LwjglApplicationConfiguration;
import com.releasemedaddy.game.Maze;
import com.releasemedaddy.game.TheRace;

public class DesktopLauncher {
	public static void main (String[] arg) {
		LwjglApplicationConfiguration config = new LwjglApplicationConfiguration();
		final TheRace game = new TheRace();
		config.height = 420;
		config.width = 420;
		new LwjglApplication(game, config);
	}
}