
package bricker.brick_strategies;

import bricker.constants.GameConstants;
import bricker.main.BrickerGameManager;
import danogl.GameObject;

public class BasicCollisionStrategy implements CollisionStrategy {
    private final BrickerGameManager gameManager;

    public BasicCollisionStrategy(BrickerGameManager gameManager) {
        this.gameManager = gameManager;
    }

    @Override
    public void onCollision(GameObject gameObject1, GameObject gameObject2) {
        System.out.println("basic");
        gameManager.getBricksCounter().decrement();
        gameManager.removeObjectFromRender(gameObject1, GameConstants.BRICK_LAYER);
    }
}
