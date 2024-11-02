package com.bino.flappy_bird.entities;

class SmartBird extends Bird {

    private static final long WAITING_TIME = 132004300;

    Brain brain;
    private long lastJumpTime;

    SmartBird() {
        super();
        brain = new Brain();
        lastJumpTime = System.nanoTime();
    }

    SmartBird(SmartBird b1, SmartBird b2) {
        super();
        brain = new Brain(b1.brain, b2.brain);
    }

    @Override
    public void update(float delta, PipePair nearest) {
        super.update(delta, nearest);
        long thisTime = System.nanoTime();
        //Jumps at most once every WAITING_TIME nanoseconds
        if (thisTime - lastJumpTime >= WAITING_TIME) {

            final float x = getX();
            final float y = getY();
            final float width = getWidth();
            final float height = getHeight();
            final float horDist = x + width - (nearest.x);
            final float vertDist1 = y - (nearest.rect1.y + nearest.rect1.height);
            final float vertDist2 = y + height - nearest.rect2.y;
            if (brain.shouldJump(vertDist1, vertDist2, horDist, y)) {
                jump();
                lastJumpTime = thisTime;
            }
        }
    }
}
