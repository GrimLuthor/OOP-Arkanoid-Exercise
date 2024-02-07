package bricker.brick_strategies;

import bricker.constants.GameConstants;
import bricker.main.BrickerGameManager;
import danogl.GameObject;

import java.util.Random;

public class DoubleBehaviorCollisionStrategy implements CollisionStrategy {

    private BrickerGameManager gameManager;

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

        System.out.println("double behavior: " + numberOfBehaviors + " behaviors");

        for (int i = 0; i < numberOfBehaviors; i++) {
            gameManager.getBricksCounter().increment(); // negating the decrement in other behavior

            System.out.print(" - ");
            collisionStrategyFactory.generateStrategy(gameManager,
                    random.nextInt(4)).onCollision(gameObject1,gameObject2);
        }


        gameManager.getBricksCounter().decrement();
        gameManager.removeObjectFromRender(gameObject1, GameConstants.BRICK_LAYER);
    }
}
