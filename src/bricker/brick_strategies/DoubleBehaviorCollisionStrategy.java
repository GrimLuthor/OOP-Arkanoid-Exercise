package bricker.brick_strategies;

import bricker.constants.GameConstants;
import bricker.main.BrickerGameManager;
import danogl.GameObject;

public class DoubleBehaviorCollisionStrategy implements CollisionStrategy{

    private BrickerGameManager gameManager;

    public DoubleBehaviorCollisionStrategy(BrickerGameManager gameManager) {
        this.gameManager = gameManager;
    }

    @Override
    public void onCollision(GameObject gameObject1, GameObject gameObject2) {
        System.out.println("double behavior");
        gameManager.getBricksCounter().decrement();
        gameManager.removeObjectFromRender(gameObject1, GameConstants.BRICK_LAYER);
    }
}
