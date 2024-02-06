package bricker.gameobjects;

import bricker.constants.GameConstants;
import bricker.main.BrickerGameManager;
import danogl.GameObject;
import danogl.collisions.Collision;
import danogl.gui.rendering.Renderable;
import danogl.util.Vector2;

import java.awt.event.KeyEvent;

public class Paddle extends GameObject {

    private static final float MOVEMENT_SPEED = GameConstants.PADDLE_SPEED;

    private final BrickerGameManager gameManager;
    private final Vector2 rangeOfMovement;

    public Paddle(Vector2 topLeftCorner, Vector2 dimensions, Renderable renderable,
                  BrickerGameManager gameManager, Vector2 rangeOfMovement) {
        super(topLeftCorner, dimensions, renderable);
        this.gameManager = gameManager;
        this.rangeOfMovement = rangeOfMovement;
        setTag(GameConstants.PADDLE_TAG);
    }

    @Override
    public void onCollisionEnter(GameObject other, Collision collision) {
        if (other.getTag().equals(GameConstants.HEART_TOKEN_TAG)) {
            gameManager.removeObjectFromRender(other, GameConstants.HEART_TOKEN_LAYER);
            gameManager.increaseLives();

        }
        super.onCollisionEnter(other, collision);
    }

    @Override
    public void update(float deltaTime) {
        super.update(deltaTime);

        Vector2 movementDirection = Vector2.ZERO;

        if (gameManager.getInputListener().isKeyPressed(KeyEvent.VK_LEFT)) {
            movementDirection = movementDirection.add(Vector2.LEFT.mult(MOVEMENT_SPEED));
        }
        if (gameManager.getInputListener().isKeyPressed(KeyEvent.VK_RIGHT)) {
            movementDirection = movementDirection.add(Vector2.RIGHT.mult(MOVEMENT_SPEED));
        }
        setVelocity(movementDirection);

        if (getTopLeftCorner().x() < rangeOfMovement.x()) {
            setTopLeftCorner(new Vector2(rangeOfMovement.x(), getTopLeftCorner().y()));
        }
        if (getTopLeftCorner().x() > rangeOfMovement.y() - getDimensions().x()) {
            setTopLeftCorner(new Vector2(rangeOfMovement.y() - getDimensions().x(), getTopLeftCorner().y()));
        }
    }

    public BrickerGameManager getGameManager() {
        return gameManager;
    }
}
