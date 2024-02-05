package bricker.gameobjects;

import bricker.main.BrickerGameManager;
import danogl.GameObject;
import danogl.gui.rendering.Renderable;
import danogl.util.Vector2;

public class BallCollisionCounter extends GameObject {
    private final BrickerGameManager gameManager;
    private final Ball ball;
    private int baseCollisions;

    public BallCollisionCounter(Vector2 topLeftCorner, Vector2 dimensions, Renderable renderable,
                                BrickerGameManager gameManager, Ball ball) {
        super(topLeftCorner, dimensions, renderable);

        this.gameManager = gameManager;
        this.ball = ball;
        this.baseCollisions = ball.getCollisionCounter();
    }

    @Override
    public void update(float deltaTime) {
        super.update(deltaTime);

        if (ball.getCollisionCounter() - baseCollisions > 4) {
            gameManager.revertCamera();
            gameManager.removeObjectFromRender(this);
        }
    }
}
