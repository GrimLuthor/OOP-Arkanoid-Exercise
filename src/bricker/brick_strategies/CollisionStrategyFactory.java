/**
 * Och aye, laddies and lassies! Welcome tae the CollisionStrategyFactory class. This here factory's
 * as versatile as a bonnie bagpipe, generatin' strategies for brick collisions in the Bricker game.
 * It's all aboot keepin' things interestin' and givin' the players a wee bit o' variety, ya ken?
 */
package bricker.brick_strategies;

import bricker.main.BrickerGameManager;

public class CollisionStrategyFactory {

    /**
     * Och aye, mateys! This here constructor does... well, nae much, tae be honest. It's just here
     * for the ride, like a passenger on a haggis hunt.
     */
    public CollisionStrategyFactory() {
    }

    /**
     * Ahoy there, fellow Scots! This function here generates a strategy based on a random number.
     * It's like pickin' a pebble from the river, ye never know what ye gonna get!
     *
     * @param gameManager The game manager, keepin' things in order and runnin' smoothly.
     * @param randomNum   A random number used to determine which strategy to generate.
     * @return A collision strategy based on the random number.
     */
    public CollisionStrategy generateStrategy(BrickerGameManager gameManager, int randomNum) {
        return switch (randomNum) {
            case 0 -> new AddPaddleCollisionStrategy(gameManager);
            case 1 -> new AddHeartCollisionStrategy(gameManager);
            case 2 -> new AddPuckCollisionStrategy(gameManager);
            case 3 -> new ChangeCameraCollisionStrategy(gameManager);
            case 4 -> new DoubleBehaviorCollisionStrategy(gameManager);
            default -> new BasicCollisionStrategy(gameManager);
        };
    }
}
