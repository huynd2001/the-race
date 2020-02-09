package com.releasemedaddy.game;

import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.Texture;

public class Assets{
    public Texture wall, target, doge;
    public Texture[] fornite, player, path;
    public Texture fog, black;
    public Maze maze;

    public void load(){
        doge = new Texture("lose.png");
        fog = new Texture("blacked.png");
        black = new Texture("black.png");
        wall = new Texture("wall.png");
        path = new Texture[16];
        for(int i = 1; i < 16; i++)
        {
            path[i] = new Texture("path" + Integer.toString(i) + ".png");
        }
        player = new Texture[2];
        player[0] = new Texture("player.png");
        player[1] = new Texture("player2.png");
        target = new Texture("target.png");
        maze = new Maze(5, 5, 69);
        fornite = new Texture[11];
        for(int i = 0; i < 11; i++)
        {
            fornite[i] = new Texture("fornite" + Integer.toString(i) + ".png");
        }
    }

    public void dispose()
    {
        doge.dispose();
        fog.dispose();
        black.dispose();
        wall.dispose();
        for(int i = 1; i < 16; i++)
        {
            path[i].dispose();
        }
        player[0].dispose();
        player[1].dispose();
        target.dispose();
        for(int i = 0; i < 11; i++)
        {
            fornite[i].dispose();
        }
    }
}
