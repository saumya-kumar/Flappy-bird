package com.bino.flappy_bird.entities;

import com.badlogic.gdx.math.MathUtils;

public class Brain {

    private static final int N_INPUTS = 4;
    private final float[] weights;

    private float bias;

    Brain() {
        bias = MathUtils.random(-100f, 100f);
        weights = new float[N_INPUTS];
        for (int i = 0; i < N_INPUTS; i++) {
            weights[i] = MathUtils.random(-100f, 100f);
        }
    }

    /*
    Constructs a Brain that is the offspring of b1 and b2 by crossing over of bias and weights.
    Each bias or weight has a 90% chance to be changed slightly and a 10% chance to be replaced with a random value
     */
    Brain(Brain b1, Brain b2) {
        if (MathUtils.randomBoolean()) {
            bias = b1.bias;
        } else {
            bias = b2.bias;
        }
        if (MathUtils.random(1, 100) <= 10) {
            bias = MathUtils.random(-100f, 100f);
        } else {
            bias += MathUtils.random(-1f, 1f);
        }
        weights = new float[N_INPUTS];
        for (int i = 0; i < N_INPUTS; i++) {
            if (MathUtils.randomBoolean()) {
                weights[i] = b1.weights[i] + MathUtils.random(-1f, 1f);
            } else {
                weights[i] = b2.weights[i] + MathUtils.random(-1f, 1f);
            }
            if (MathUtils.random(1, 100) <= 10) {
                weights[i] = MathUtils.random(-100f, 100f);
            }
        }
    }

    boolean shouldJump(float... params) {
        float sum = bias;
        for (int i = 0; i < N_INPUTS; i++) {
            sum += weights[i]*params[i];
        }
        return sum > 0;
    }
}
