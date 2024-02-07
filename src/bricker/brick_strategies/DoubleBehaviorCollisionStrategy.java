package bricker.brick_strategies;

import bricker.main.BrickerGameManager;
import danogl.GameObject;

import java.util.Random;

public class DoubleBehaviorCollisionStrategy implements CollisionStrategy {

    private final BrickerGameManager gameManager;

    public DoubleBehaviorCollisionStrategy(BrickerGameManager gameManager) {
        this.gameManager = gameManager;
    }

    @Override
    public void onCollision(GameObject gameObject1, GameObject gameObject2) {

        CollisionStrategyFactory collisionStrategyFactory = new CollisionStrategyFactory();
        Random random = new Random();

        int numberOfBehaviors = random.nextInt(5) == 4 ? 3 : 2;

        if (numberOfBehaviors == 2) {
            numberOfBehaviors = random.nextInt(5) == 4 ? 3 : 2;
        }

        for (int i = 0; i < numberOfBehaviors; i++) {
            gameManager.getBricksCounter().increment(); // negating the decrement in other behavior
            collisionStrategyFactory.generateStrategy(gameManager,
                    random.nextInt(4)).onCollision(gameObject1, gameObject2);
        }
        removeBrick(gameManager, gameObject1);
    }
}
