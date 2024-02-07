package bricker.brick_strategies;

import bricker.main.BrickerGameManager;

public class CollisionStrategyFactory {
    public CollisionStrategyFactory() {
    }

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
