package com.bino.flappy_bird.screens;

import com.badlogic.gdx.InputAdapter;
import com.badlogic.gdx.ScreenAdapter;
import com.badlogic.gdx.graphics.Camera;
import com.badlogic.gdx.graphics.g2d.GlyphLayout;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.ScreenUtils;
import com.badlogic.gdx.utils.viewport.ExtendViewport;
import com.bino.flappy_bird.FlappyBirdGame;
import com.bino.flappy_bird.entities.Pipes;
import com.bino.flappy_bird.utils.Assets;

import static com.bino.flappy_bird.utils.GlobalConstants.BG_COLOR;
import static com.bino.flappy_bird.utils.GlobalConstants.WORLD_HEIGHT;

abstract class MainScreen extends ScreenAdapter {

    private static final int MIN_WORLD_WIDTH = 200;
    private static final int TEXT_VIEWPORT_SIZE = 100;

    protected final FlappyBirdGame game;
    protected Pipes pipes;

    private SpriteBatch batch;
    private ExtendViewport spritesViewport;
    private Camera spritesCamera;
    private ExtendViewport textViewport;

    protected MainScreen(FlappyBirdGame game) {
        this.game = game;
    }

    @Override
    public void show() {
        batch = new SpriteBatch();

        spritesViewport = new ExtendViewport(MIN_WORLD_WIDTH, WORLD_HEIGHT, Float.POSITIVE_INFINITY, WORLD_HEIGHT);
        spritesCamera = spritesViewport.getCamera();

        textViewport = new ExtendViewport(TEXT_VIEWPORT_SIZE, TEXT_VIEWPORT_SIZE);

        pipes = new Pipes();
    }

    @Override
    public void resize(int width, int height) {
        spritesViewport.update(width, height);
        spritesViewport.getCamera().position.y = spritesViewport.getWorldHeight()/2;
        textViewport.update(width, height, true);

        if (height > width) {
            spritesViewport.setScreenY(height - spritesViewport.getScreenHeight());
            textViewport.setScreenY(height - textViewport.getScreenHeight());
        }
    }

    protected void update(float delta) {
        pipes.update(spritesViewport.getWorldWidth(), spritesCamera.position.x);
    }

    protected void drawSprites(SpriteBatch batch) {
        pipes.draw(batch);
    }

    private void drawText(SpriteBatch batch, float width, float height) {
        GlyphLayout score = new GlyphLayout(Assets.font, "" + pipes.score);
        Assets.font.draw(batch, score, (width - score.width)/2, (height - score.height)/2);
    }

    @Override
    public void render(float delta) {
        ScreenUtils.clear(BG_COLOR);
        if (delta > 1/30f)
            delta = 1/30f;
        update(delta);

        spritesViewport.apply();
        batch.setProjectionMatrix(spritesCamera.combined);

        batch.begin();
        drawSprites(batch);
        batch.end();

        textViewport.apply();
        batch.setProjectionMatrix(textViewport.getCamera().combined);

        batch.begin();
        drawText(batch, textViewport.getWorldWidth(), textViewport.getWorldHeight());
        batch.draw(Assets.backTexture,
            textViewport.getWorldWidth() - Assets.backTexture.getWidth(),
            textViewport.getWorldHeight() - Assets.backTexture.getHeight());
        batch.end();
    }

    @Override
    public void dispose() {
        batch.dispose();
    }

    protected void updateCameraPosition(float birdX) {
        spritesCamera.position.x = birdX + spritesViewport.getWorldWidth()/4;
    }

    class MainScreenInputProcessor extends InputAdapter {

        @Override
        public boolean touchDown(int screenX, int screenY, int pointer, int button) {
            Vector2 touchPos = textViewport.unproject(new Vector2(screenX, screenY));
            Rectangle rect = new Rectangle(
                textViewport.getWorldWidth() - Assets.backTexture.getWidth(),
                textViewport.getWorldHeight() - Assets.backTexture.getHeight(),
                Assets.backTexture.getWidth(), Assets.backTexture.getHeight());
            if (rect.contains(touchPos)) {
                game.setScreen(new StartScreen(game));
            }

            return true;
        }
    }
}
