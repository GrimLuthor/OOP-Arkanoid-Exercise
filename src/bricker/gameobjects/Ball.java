package bricker.gameobjects;

import bricker.constants.GameConstants;
import bricker.main.BrickerGameManager;
import danogl.GameObject;
import danogl.collisions.Collision;
import danogl.gui.Sound;
import danogl.gui.rendering.Renderable;
import danogl.util.Vector2;

public class Ball extends GameObject {
    private int collisionCounter = 0;
    private final Sound collisionSound;
    private final BrickerGameManager gameManager;


    public Ball(Vector2 topLeftCorner, Vector2 dimensions, Renderable renderable, Sound collisionSound ,
                BrickerGameManager gameManager) {
        super(topLeftCorner, dimensions, renderable);
        this.gameManager = gameManager;
        this.collisionSound = collisionSound;
        setTag(GameConstants.BALL_TAG);
    }

    @Override
    public boolean shouldCollideWith(GameObject other) {
        return super.shouldCollideWith(other) || other.getTag().equals(GameConstants.PUCK_TAG);
    }

    @Override
    public void onCollisionEnter(GameObject other, Collision collision) {
        Vector2 newVel = getVelocity().flipped(collision.getNormal());
        setVelocity(newVel);
        collisionSound.play();
        collisionCounter++;
    }


    @Override
    public void update(float deltaTime) {
        super.update(deltaTime);
        if (getCenter().y() > gameManager.getWindowDimensions().y()) {
            gameManager.removeObjectFromRender(this,GameConstants.BALL_LAYER);
        }
    }

    public int getCollisionCounter() {
        return collisionCounter;
    }

}
