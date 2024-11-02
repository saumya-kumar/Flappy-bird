package com.bino.flappy_bird.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.bino.flappy_bird.FlappyBirdGame;
import com.bino.flappy_bird.entities.Bird;

public class GameScreen extends MainScreen {

    private Bird bird;

    public GameScreen(FlappyBirdGame game) {
        super(game);
    }

    @Override
    public void show() {
        super.show();

        bird = new Bird();

        Gdx.input.setInputProcessor(new MainScreenInputProcessor() {
            @Override
            public boolean touchDown(int screenX, int screenY, int pointer, int button) {
                super.touchDown(screenX, screenY, pointer, button);
                bird.jump();

                return true;
            }

            @Override
            public boolean keyDown(int keycode) {
                super.keyDown(keycode);
                if (keycode == Input.Keys.SPACE) {
                    bird.jump();
                }

                return true;
            }
        });
    }

    @Override
    protected void update(float delta) {
        super.update(delta);
        bird.update(delta, pipes.nearest);
        if (!bird.alive) {
            game.setScreen(new StartScreen(game));
        }
        float x = bird.getX();
        updateCameraPosition(x);
    }

    @Override
    protected void drawSprites(SpriteBatch batch) {
        super.drawSprites(batch);
        bird.draw(batch);
    }
}
