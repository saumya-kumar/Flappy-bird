package com.bino.flappy_bird.entities;

import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Vector2;
import com.bino.flappy_bird.utils.Assets;
import com.bino.flappy_bird.utils.GlobalConstants;

public class Bird extends Sprite {

    private static final float X_VEL = 100;
    private static final float GRAVITY = 980;
    private static final float JUMP_VEL = 200;

    public boolean alive;
    protected Vector2 velocity;

    public Bird() {
        super(Assets.birdTexture);
        setOriginCenter();
        init();
    }

    void init() {
        setX(0);
        setY((GlobalConstants.WORLD_HEIGHT - getHeight())/2f);
        velocity = new Vector2(X_VEL, 0);
        alive = true;
    }

    public void update(float delta, PipePair nearest) {
        float x = getX();
        float y = getY();

        velocity.y -= GRAVITY*delta;

        x += velocity.x*delta;
        y += velocity.y*delta;

        if (y > GlobalConstants.WORLD_HEIGHT - getHeight()) {
            y = GlobalConstants.WORLD_HEIGHT - getHeight();
        } else if (y < 0) {
            y = 0;
            velocity.x = 0;
            alive = false;
        }

        if (nearest.overlaps(this.getBoundingRectangle())) {
            velocity.x = 0;
        }

        float angle = MathUtils.atan2(velocity.y, velocity.x)*MathUtils.radiansToDegrees;
        setRotation(angle);

        setX(x);
        setY(y);
    }

    public void jump() {
        if (velocity.x != 0) {
            velocity.y = JUMP_VEL;
        }
    }
}
