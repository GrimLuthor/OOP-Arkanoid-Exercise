package bricker.brick_strategies;

import bricker.constants.GameConstants;
import bricker.main.BrickerGameManager;
import danogl.GameObject;

public interface CollisionStrategy {
    void onCollision(GameObject gameObject1, GameObject gameObject2);

    default void removeBrick(BrickerGameManager gameManager, GameObject gameObject1) {
        gameManager.getBricksCounter().decrement();
        gameManager.removeObjectFromRender(gameObject1, GameConstants.BRICK_LAYER);
    }

}

