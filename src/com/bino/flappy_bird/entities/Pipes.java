package com.bino.flappy_bird.entities;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;

import java.util.ArrayList;

import static com.bino.flappy_bird.utils.GlobalConstants.PIPE_WIDTH;

public class Pipes {

    private static final float GAP = 150;

    public PipePair nearest;
    public int score;

    private int nearIndex;
    private ArrayList<PipePair> pipes;

    public Pipes() {
        init();
    }

    void init() {
        pipes = new ArrayList<>();
        nearest = new PipePair(GAP*2);
        pipes.add(nearest);
        score = 0;
        nearIndex = 0;
    }

    public void update(float worldWidth, float cameraX) {
        final float screenLeft = cameraX - worldWidth/2;
        final float screenRight = screenLeft + worldWidth;

        PipePair last = pipes.get(pipes.size() - 1);
        while (screenRight - (last.x + PIPE_WIDTH) > GAP) {
            last = new PipePair(last.x + PIPE_WIDTH + GAP);
            pipes.add(last);
        }

        if (pipes.get(0).x + PIPE_WIDTH < screenLeft) {
            pipes.remove(0);
            nearIndex--;
        }

        final float birdX = cameraX - worldWidth/4;
        if (nearest.x + PIPE_WIDTH < birdX) {
            nearest = pipes.get(++nearIndex);
            score++;
        }
    }

    public void draw(SpriteBatch batch) {
        for (PipePair pipePair : pipes) {
            pipePair.draw(batch);
        }
    }
}
