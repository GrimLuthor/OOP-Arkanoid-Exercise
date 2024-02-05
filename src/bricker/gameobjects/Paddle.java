package bricker.gameobjects;

import bricker.constants.GameConstants;
import danogl.GameObject;
import danogl.collisions.Collision;
import danogl.gui.UserInputListener;
import danogl.gui.rendering.Renderable;
import danogl.util.Vector2;

import java.awt.event.KeyEvent;

public class Paddle extends GameObject {

    private static final String TAG = GameConstants.PADDLE_TAG;

    private static final float MOVEMENT_SPEED = 250;
    private UserInputListener inputListener;
    private Vector2 rangeOfMovement;

    public Paddle(Vector2 topLeftCorner, Vector2 dimensions, Renderable renderable, UserInputListener inputListener, Vector2 rangeOfMovement) {
        super(topLeftCorner, dimensions, renderable);
        this.inputListener = inputListener;
        this.rangeOfMovement = rangeOfMovement;
    }

    @Override
    public void onCollisionEnter(GameObject other, Collision collision) {
        super.onCollisionEnter(other, collision);
    }

    @Override
    public void update(float deltaTime) {
        super.update(deltaTime);

        Vector2 movementDirection = Vector2.ZERO;

        if (inputListener.isKeyPressed(KeyEvent.VK_LEFT)) {
            movementDirection = movementDirection.add(Vector2.LEFT.mult(MOVEMENT_SPEED));
        }
        if (inputListener.isKeyPressed(KeyEvent.VK_RIGHT)) {
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

    public String getTag() {
        return TAG;
    }
}
