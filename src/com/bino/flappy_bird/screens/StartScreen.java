package com.bino.flappy_bird.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.InputAdapter;
import com.badlogic.gdx.ScreenAdapter;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.ScreenUtils;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.badlogic.gdx.utils.viewport.Viewport;
import com.bino.flappy_bird.FlappyBirdGame;
import com.bino.flappy_bird.utils.Assets;

import static com.bino.flappy_bird.utils.GlobalConstants.BG_COLOR;

public class StartScreen extends ScreenAdapter {

    private static final int WORLD_WIDTH = 32;
    private static final int WORLD_HEIGHT = 96;

    private final FlappyBirdGame game;
    private SpriteBatch batch;
    private Viewport viewport;

    public StartScreen(FlappyBirdGame game) {
        this.game = game;
    }

    @Override
    public void show() {
        batch = new SpriteBatch();
        viewport = new FitViewport(WORLD_WIDTH, WORLD_HEIGHT);
        Gdx.input.setInputProcessor(new InputAdapter() {
            // Called when the mouse is pressed
            @Override
            public boolean touchDown(int screenX, int screenY, int pointer, int button) {
                Vector2 touchPos = new Vector2(screenX, screenY);
                touchPos = viewport.unproject(touchPos);

                if (Assets.playButton.getBoundingRectangle().contains(touchPos)) {
                    game.setScreen(new GameScreen(game));
                } else if (Assets.learnButton.getBoundingRectangle().contains(touchPos)) {
                    game.setScreen(new LearnScreen(game));
                }

                return true;
            }
        });
    }

    @Override
    public void resize(int width, int height) {
        viewport.update(width, height, true);
    }

    @Override
    public void render(float delta) {
        ScreenUtils.clear(BG_COLOR);

        viewport.apply();
        batch.setProjectionMatrix(viewport.getCamera().combined);

        batch.begin();
        Assets.playButton.draw(batch);
        Assets.learnButton.draw(batch);
        batch.end();
    }

    @Override
    public void dispose() {
        batch.dispose();
    }
}
