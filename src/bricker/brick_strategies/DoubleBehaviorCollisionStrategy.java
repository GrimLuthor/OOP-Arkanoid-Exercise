package bricker.brick_strategies;

import bricker.main.BrickerGameManager;
import danogl.GameObject;

import java.util.Random;

/**
 * Alright, mates! Welcome to the DoubleBehaviorCollisionStrategy class. This strategy is a bit of a
 * cheeky one, mixin' up multiple behaviors when there's a collision in the Bricker game. It's like
 * havin' a proper cuppa with a slice of Victoria sponge cake - double the pleasure, innit?
 */

public class DoubleBehaviorCollisionStrategy implements CollisionStrategy {

    private final BrickerGameManager gameManager;

    /**
     * Oi, guv'nor! This 'ere constructor sets up the DoubleBehaviorCollisionStrategy, makin' sure
     * we're ready to shake things up when there's a collision in the game.
     *
     * @param gameManager The game manager, keepin' things in order and runnin' smoothly.
     */
    public DoubleBehaviorCollisionStrategy(BrickerGameManager gameManager) {
        this.gameManager = gameManager;
    }

    @Override
    public void onCollision(GameObject gameObject1, GameObject gameObject2) {
        // Let's get a bit cheeky and mix things up a bit, shall we?
        CollisionStrategyFactory collisionStrategyFactory = new CollisionStrategyFactory();
        Random random = new Random();

        int numberOfBehaviors = random.nextInt(5) == 4 ? 3 : 2;

        // If there are only two behaviors, there's a 20% chance of adding a third behavior
        if (numberOfBehaviors == 2) {
            numberOfBehaviors = random.nextInt(5) == 4 ? 3 : 2;
        }

        for (int i = 0; i < numberOfBehaviors; i++) {
            gameManager.getBricksCounter().increment(); // negating the decrement in other behavior
            collisionStrategyFactory.generateStrategy(gameManager,
                    random.nextInt(4)).onCollision(gameObject1, gameObject2);
        }
        gameManager.removeBrick(gameObject1);
    }
}
