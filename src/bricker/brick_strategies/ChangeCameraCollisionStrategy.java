package bricker.brick_strategies;

import bricker.constants.GameConstants;
import bricker.gameobjects.Ball;
import bricker.gameobjects.BallCollisionCounter;
import bricker.main.BrickerGameManager;
import danogl.GameObject;
import danogl.util.Vector2;

public class ChangeCameraCollisionStrategy implements CollisionStrategy {

    private final BrickerGameManager gameManager;

    public ChangeCameraCollisionStrategy(BrickerGameManager gameManager) {
        this.gameManager = gameManager;
    }

    @Override
    public void onCollision(GameObject gameObject1, GameObject gameObject2) {

        if (gameObject2.getTag().equals(GameConstants.BALL_TAG) && gameManager.camera() == null) {
            System.out.println("camera");

            gameManager.setCameraToFollowBall();

            BallCollisionCounter ballCollisionCounter = new BallCollisionCounter(Vector2.ZERO,Vector2.ZERO,
                    null,gameManager, (Ball) gameObject2);
            gameManager.addObjectToRender(ballCollisionCounter);
        }
        else {
            System.out.println("basic");
        }
        gameManager.getBricksCounter().decrement();
        gameManager.removeObjectFromRender(gameObject1, GameConstants.BRICK_LAYER);
    }
}
