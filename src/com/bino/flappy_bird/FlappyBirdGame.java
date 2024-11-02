package com.bino.flappy_bird;

import com.badlogic.gdx.Game;
import com.bino.flappy_bird.screens.StartScreen;
import com.bino.flappy_bird.utils.Assets;

public class FlappyBirdGame extends Game {

    @Override
    public void create() {
        Assets.init();
        setScreen(new StartScreen(this));
    }

    @Override
    public void dispose() {
        Assets.dispose();
    }
}
