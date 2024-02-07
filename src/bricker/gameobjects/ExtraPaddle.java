package bricker.gameobjects;

import bricker.constants.GameConstants;
import bricker.main.BrickerGameManager;
import danogl.GameObject;
import danogl.collisions.Collision;
import danogl.gui.rendering.Renderable;
import danogl.util.Counter;
import danogl.util.Vector2;

public class ExtraPaddle extends Paddle {

    private final Counter collisionCounter = new Counter();

    public ExtraPaddle(Vector2 topLeftCorner, Vector2 dimensions, Renderable renderable,
                       BrickerGameManager gameManager, Vector2 rangeOfMovement) {
        super(topLeftCorner, dimensions, renderable, gameManager, rangeOfMovement);
        setTag(GameConstants.EXTRA_PADDLE_TAG);
    }

    @Override
    public void onCollisionEnter(GameObject other, Collision collision) {
        super.onCollisionEnter(other, collision);
        collisionCounter.increment();
        if (collisionCounter.value() > GameConstants.COLLISIONS_TO_REMOVE_EXTRA_PADDLE) {
            getGameManager().removeObjectFromRender(this, GameConstants.EXTRA_PADDLE_LAYER);
            getGameManager().setExtraPaddle(null);
        }
    }
}
