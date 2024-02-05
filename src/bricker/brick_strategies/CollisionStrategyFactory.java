package bricker.brick_strategies;

import bricker.main.BrickerGameManager;

public class CollisionStrategyFactory {
    public CollisionStrategyFactory () {

    }

    public CollisionStrategy generateStrategy(BrickerGameManager gameManager, int randomNum) {
        switch (randomNum) {
            case 0:
                return new AddDiskCollisionStrategy(gameManager);
            case 1:
                return new AddHeartCollisionStrategy(gameManager);
            case 2:
                return new AddPuckCollisionStrategy(gameManager);
            case 3:
                return new ChangeCameraCollisionStrategy(gameManager);
            case 4:
                return new DoubleBehaviorCollisionStrategy(gameManager);
            default:
                return new BasicCollisionStrategy(gameManager);
        }
    }
}
