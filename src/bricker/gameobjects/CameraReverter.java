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
