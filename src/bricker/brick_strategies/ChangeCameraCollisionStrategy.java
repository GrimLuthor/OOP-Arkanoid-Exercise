package bricker.brick_strategies;

import bricker.constants.GameConstants;
import bricker.gameobjects.Ball;
import bricker.gameobjects.CameraReverter;
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
            gameManager.setCameraToFollowBall();
            CameraReverter cameraReverter = new CameraReverter(Vector2.ZERO, Vector2.ZERO,
                    null, gameManager, (Ball) gameObject2);

            gameManager.addObjectToRender(cameraReverter);
        }
        removeBrick(gameManager, gameObject1);
    }
}
