/**
 * Howdy, y'all! Welcome to the CameraReverter class. This here class is all 'bout keepin' an eye on the ball
 * in the Bricker game and revertin' the camera when things get wild.
 */
package bricker.gameobjects;

import bricker.constants.GameConstants;
import bricker.main.BrickerGameManager;
import danogl.GameObject;
import danogl.gui.rendering.Renderable;
import danogl.util.Vector2;

public class CameraReverter extends GameObject {
    private final BrickerGameManager gameManager;
    private final Ball ball;
    private final int baseCollisions;

    /**
     * Howdy, folks! This constructor sets up the CameraReverter, keepin' an eye on the ball and the number of
     * collisions it's seen.
     *
     * @param topLeftCorner The starting position of this camera reverter, ready to keep watch.
     * @param dimensions    The size 'n' shape of this object, makin' sure it covers the right area.
     * @param renderable    The visual representation, so y'all know what's goin' on.
     * @param gameManager   The game manager, keepin' everything in check out here.
     * @param ball          The ball object to keep track of.
     */
    public CameraReverter(Vector2 topLeftCorner, Vector2 dimensions, Renderable renderable,
                          BrickerGameManager gameManager, Ball ball) {
        super(topLeftCorner, dimensions, renderable);
        this.gameManager = gameManager;
        this.ball = ball;
        this.baseCollisions = ball.getCollisionCounter();
    }

    @Override
    public void update(float deltaTime) {
        super.update(deltaTime);

        if (ball.getCollisionCounter() - baseCollisions > GameConstants.COLLISIONS_TO_REVERT_CAMERA) {
            gameManager.revertCamera();
            gameManager.removeObjectFromRender(this);
        }
    }
}
