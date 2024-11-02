package com.bino.flappy_bird.utils;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.NinePatch;
import com.badlogic.gdx.graphics.g2d.Sprite;

public class Assets {

    public static Texture birdTexture;

    public static Texture backTexture;

    public static Sprite playButton;

    public static Sprite learnButton;

    public static NinePatch pipeNinePatch;

    public static BitmapFont font;

    public static void init() {
        birdTexture = new Texture("assets\\bird.png");

        backTexture = new Texture("assets\\back.png");

        playButton = new Sprite(new Texture("assets\\play-button.png"));
        playButton.setY(playButton.getWidth()*2);

        learnButton = new Sprite(new Texture("assets\\learn-button.png"));

        Texture pipeTexture = new Texture("assets\\pipe.png");
        pipeNinePatch = new NinePatch(pipeTexture, 0, 0, GlobalConstants.PIPE_EDGE, GlobalConstants.PIPE_EDGE);

        font = new BitmapFont(Gdx.files.internal("assets\\pixel-font.fnt"));
    }

    public static void dispose() {
        birdTexture.dispose();
        backTexture.dispose();
        playButton.getTexture().dispose();
        learnButton.getTexture().dispose();
        pipeNinePatch.getTexture().dispose();
        font.dispose();
    }
}
