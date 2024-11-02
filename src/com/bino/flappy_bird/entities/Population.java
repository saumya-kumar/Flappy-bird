package com.bino.flappy_bird.entities;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.MathUtils;

import java.util.Arrays;

public class Population {

    private static final int POPULATION_SIZE = 100;

    public float firstBirdX;
    private int generation;

    private SmartBird[] birds;

    public Population() {
        birds = new SmartBird[POPULATION_SIZE];
        for (int i = 0; i < POPULATION_SIZE; i++) {
            birds[i] = new SmartBird();
        }
        generation = 1;
    }

    public void update(float delta, Pipes pipes) {
        boolean allDead = true;
        for (SmartBird bird : birds) {
            bird.update(delta, pipes.nearest);
            if (bird.alive) {
                allDead = false;
                firstBirdX = Math.max(bird.getX(), firstBirdX);
            }
        }
        if (allDead) {
            firstBirdX = 0;
            createNewGeneration();
            if (pipes.score > 100) {
                System.out.println(generation);
            }
            pipes.init();
        }
    }

    private void createNewGeneration() {
        generation++;
        //Sorts birds array in ascending order of distance reached before death
        Arrays.sort(birds, (b1, b2) -> Float.compare(b1.getX(), b2.getX()));
        SmartBird[] newGen = new SmartBird[POPULATION_SIZE];

        /*
        The lower 50% of the population is replaced by offspring from the top 50% of birds
        Higher the position of a bird in the array, higher is its chances of reproducing
         */
        for (int i = 0; i < POPULATION_SIZE/2; ++i) {
            SmartBird b1 = birds[(int) MathUtils.randomTriangular(POPULATION_SIZE/2f, POPULATION_SIZE,
                POPULATION_SIZE)];
            SmartBird b2 = birds[(int) MathUtils.randomTriangular(POPULATION_SIZE/2f, POPULATION_SIZE,
                POPULATION_SIZE)];
            newGen[i] = new SmartBird(b1, b2);
        }

        //The top 50% are copied to the next generation unchanged
        for (int i = POPULATION_SIZE/2; i < POPULATION_SIZE; ++i) {
            newGen[i] = birds[i];
            birds[i].init();
        }
        birds = newGen;
    }

    public void draw(SpriteBatch batch) {
        for (Bird bird : birds) {
            bird.draw(batch);
        }
    }
}
