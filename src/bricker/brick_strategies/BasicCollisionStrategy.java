/**
 * Aye, laddies and lassies! Welcome tae the BasicCollisionStrategy class. This wee strategy is as simple as
 * a wee dram o' whisky, removin' bricks from the game when there's a collision. It's all aboot keepin' things
 * straightforward and tidy in the Bricker game, ye ken?
 */
package bricker.brick_strategies;

import bricker.main.BrickerGameManager;
import danogl.GameObject;

public class BasicCollisionStrategy implements CollisionStrategy {
    private final BrickerGameManager gameManager;

    /**
     * Aye, mateys! This here constructor sets up the BasicCollisionStrategy, makin' sure we're ready
     * tae take care o' business when there's a collision in the game.
     *
     * @param gameManager The game manager, keepin' things in order and runnin' smoothly.
     */
    public BasicCollisionStrategy(BrickerGameManager gameManager) {
        this.gameManager = gameManager;
    }

    @Override
    public void onCollision(GameObject gameObject1, GameObject gameObject2) {
        removeBrick(gameManager, gameObject1);
    }
}
