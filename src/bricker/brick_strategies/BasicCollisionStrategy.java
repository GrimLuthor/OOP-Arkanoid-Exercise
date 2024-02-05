
package bricker.brick_strategies;

import bricker.constants.GameConstants;
import bricker.main.BrickerGameManager;
import danogl.GameObject;
import danogl.util.Counter;

public class BasicCollisionStrategy implements CollisionStrategy {
    private BrickerGameManager gameManager;
    private Counter brickCounter;

    public BasicCollisionStrategy(BrickerGameManager gameManager, Counter brickCounter) {
        this.gameManager = gameManager;
        this.brickCounter = brickCounter;
    }

    @Override
    public void onCollision(GameObject gameObject1, GameObject gameObject2) {
//        System.out.println("collision with brick detected");
        brickCounter.decrement();
        gameManager.removeObjectFromRender(gameObject1, GameConstants.BRICK_LAYER);

    }
}
