package com.bino.flappy_bird.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.bino.flappy_bird.FlappyBirdGame;
import com.bino.flappy_bird.entities.Population;

public class LearnScreen extends MainScreen {

    private Population population;
    private boolean paused;

    public LearnScreen(FlappyBirdGame game) {
        super(game);
    }

    @Override
    public void show() {
        super.show();
        population = new Population();
        paused = false;

        Gdx.input.setInputProcessor(new MainScreenInputProcessor() {

            @Override
            public boolean keyDown(int keycode) {
                super.keyDown(keycode);
                if (keycode == Input.Keys.SPACE) {
                    paused = !paused;
                }
                return true;
            }

            @Override
            public boolean touchDown(int screenX, int screenY, int pointer, int button) {
                super.touchDown(screenX, screenY, pointer, button);
                paused = !paused;
                return true;
            }
        });
    }

    @Override
    protected void update(float delta) {
        if (paused) {
            return;
        }
        super.update(delta);
        population.update(delta, pipes);
        updateCameraPosition(population.firstBirdX);
    }

    @Override
    protected void drawSprites(SpriteBatch batch) {
        super.drawSprites(batch);
        population.draw(batch);
    }
}