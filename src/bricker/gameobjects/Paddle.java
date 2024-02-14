package bricker.gameobjects;

import bricker.main.GameConstants;
import bricker.main.BrickerGameManager;
import danogl.GameObject;
import danogl.collisions.Collision;
import danogl.gui.rendering.Renderable;
import danogl.util.Vector2;

import java.awt.event.KeyEvent;

/**
 * Exquisite, lads 'n' lasses! Welcome tae the Paddle class. This wee class represents a paddle object
 * in the Bricker game. It's aw aboot keepin' yersel' in control o' the game, ken?
 */

public class Paddle extends GameObject {

    private static final float MOVEMENT_SPEED = GameConstants.PADDLE_SPEED;
    private final BrickerGameManager gameManager;
    private final Vector2 rangeOfMovement;

    /**
     * A'richt, listen up! This here constructor sets up the Paddle, givin' it the tools it needs tae
     * keep things in check in the game.
     *
     * @param topLeftCorner    The startin' point o' this paddle, guidin' it along its path.
     * @param dimensions       The size 'n' shape o' the paddle, keepin' it in line.
     * @param renderable       The visual representation, makin' sure the paddle looks the part.
     * @param gameManager      The game manager, keepin' tabs on everything in the game.
     * @param rangeOfMovement  The range o' movement for the paddle, keepin' it from wanderin' too far.
     */
    public Paddle(Vector2 topLeftCorner, Vector2 dimensions, Renderable renderable,
                  BrickerGameManager gameManager, Vector2 rangeOfMovement) {
        super(topLeftCorner, dimensions, renderable);
        this.gameManager = gameManager;
        this.rangeOfMovement = rangeOfMovement;
        setTag(GameConstants.PADDLE_TAG);
    }

    @Override
    public void onCollisionEnter(GameObject other, Collision collision) {
        // If the paddle collides with a heart token, remove the heart token from the game and increase
        // the player's lives.
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
        // If the left or right arrow keys are pressed, move the paddle in the corresponding direction.
        if (gameManager.getInputListener().isKeyPressed(KeyEvent.VK_LEFT)) {
            movementDirection = movementDirection.add(Vector2.LEFT.mult(MOVEMENT_SPEED));
        }
        if (gameManager.getInputListener().isKeyPressed(KeyEvent.VK_RIGHT)) {
            movementDirection = movementDirection.add(Vector2.RIGHT.mult(MOVEMENT_SPEED));
        }
        setVelocity(movementDirection);
        // If the paddle is at the edge of its range of movement, stop it from moving further.
        if (getTopLeftCorner().x() < rangeOfMovement.x()) {
            setTopLeftCorner(new Vector2(rangeOfMovement.x(), getTopLeftCorner().y()));
        }
        if (getTopLeftCorner().x() > rangeOfMovement.y() - getDimensions().x()) {
            setTopLeftCorner(
                    new Vector2(rangeOfMovement.y() - getDimensions().x(), getTopLeftCorner().y()));
        }
    }

    /**
     * Splendid, pal! This method gets the game manager, helpin' keep things runnin' smoothly in the game.
     *
     * @return The game manager for this paddle.
     */
    public BrickerGameManager getGameManager() {
        return gameManager;
    }
}
