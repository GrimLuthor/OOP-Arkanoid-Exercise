package bricker.brick_strategies;

import bricker.constants.GameConstants;
import bricker.gameobjects.Ball;
import bricker.gameobjects.BallCollisionCounter;
import bricker.main.BrickerGameManager;
import danogl.GameObject;
import danogl.util.Counter;
import danogl.util.Vector2;

public class ChangeCameraCollisionStrategy implements CollisionStrategy {

    private BrickerGameManager gameManager;

    public ChangeCameraCollisionStrategy(BrickerGameManager gameManager) {
        this.gameManager = gameManager;
    }

    @Override
    public void onCollision(GameObject gameObject1, GameObject gameObject2) {
        System.out.println("basic");
        if (gameObject2.getTag().equals(GameConstants.BALL_TAG) && gameManager.camera() == null) {
            gameManager.setCameraToFollowBall();

            BallCollisionCounter ballCollisionCounter = new BallCollisionCounter(Vector2.ZERO,Vector2.ZERO,
                    null,gameManager, (Ball) gameObject2);
            gameManager.addObjectToRender(ballCollisionCounter);
        }
        gameManager.getBricksCounter().decrement();
        gameManager.removeObjectFromRender(gameObject1, GameConstants.BRICK_LAYER);
    }
}
