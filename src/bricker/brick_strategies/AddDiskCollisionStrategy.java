package bricker.brick_strategies;

import bricker.constants.GameConstants;
import bricker.main.BrickerGameManager;
import danogl.GameObject;

public class AddDiskCollisionStrategy implements CollisionStrategy {

    private BrickerGameManager gameManager;

    public AddDiskCollisionStrategy(BrickerGameManager gameManager) {
        this.gameManager = gameManager;
    }

    @Override
    public void onCollision(GameObject gameObject1, GameObject gameObject2) {
        System.out.println("add disk");
        gameManager.getBricksCounter().decrement();
        gameManager.removeObjectFromRender(gameObject1, GameConstants.BRICK_LAYER);
    }
}
