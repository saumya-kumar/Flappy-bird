package com.bino.flappy_bird;

import com.badlogic.gdx.backends.lwjgl.LwjglApplication;
import com.badlogic.gdx.backends.lwjgl.LwjglApplicationConfiguration;

public class Main {

    public static void main(String[] args) {
        LwjglApplicationConfiguration config = new LwjglApplicationConfiguration();
        config.title = "Flappy Bird AI";
        config.vSyncEnabled = true;
        config.width = 960;
        config.height = 540;
        new LwjglApplication(new FlappyBirdGame(), config);
    }
}
