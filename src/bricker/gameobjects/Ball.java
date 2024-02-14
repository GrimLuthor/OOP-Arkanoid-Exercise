/**
 * Dis be de Ball class, representing a ball object inside de Bricker game.
 * De Ball be movin' an' collidin' within de game world.
 */
package bricker.gameobjects;

import bricker.constants.GameConstants;
import bricker.main.BrickerGameManager;
import danogl.GameObject;
import danogl.collisions.Collision;
import danogl.gui.Sound;
import danogl.gui.rendering.Renderable;
import danogl.util.Counter;
import danogl.util.Vector2;

public class Ball extends GameObject {
    private final Counter collisionCounter = new Counter();
    private final Sound collisionSound;
    private final BrickerGameManager gameManager;

    /**
     * Creates a new Ball object wit' de specified parameters.
     *
     * @param topLeftCorner   De position of de top-left corner of de ball.
     * @param dimensions      De dimensions (size) of de ball.
     * @param renderable      De renderable object representin' de ball's appearance.
     * @param collisionSound  De sound to play when de ball collides.
     * @param gameManager     De game manager for managing de ball within de game.
     */
    public Ball(Vector2 topLeftCorner, Vector2 dimensions, Renderable renderable, Sound collisionSound,
                BrickerGameManager gameManager) {
        super(topLeftCorner, dimensions, renderable);
        this.gameManager = gameManager;
        this.collisionSound = collisionSound;
        setTag(GameConstants.BALL_TAG);
    }

    @Override
    public boolean shouldCollideWith(GameObject other) {
        return super.shouldCollideWith(other);
    }

    @Override
    public void onCollisionEnter(GameObject other, Collision collision) {
        Vector2 newVel = getVelocity().flipped(collision.getNormal());
        setVelocity(newVel);
        collisionSound.play();
        collisionCounter.increment();
    }

    @Override
    public void update(float deltaTime) {
        super.update(deltaTime);
        if (getCenter().y() > gameManager.getWindowDimensions().y()) {
            gameManager.removeObjectFromRender(this, GameConstants.BALL_LAYER);
        }
    }

    /**
     * Gets de number of collisions dat de ball has undergone.
     *
     * @return De number of collisions.
     */
    public int getCollisionCounter() {
        return collisionCounter.value();
    }
}
