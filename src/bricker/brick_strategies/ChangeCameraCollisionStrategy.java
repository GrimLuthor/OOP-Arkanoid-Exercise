/**
 * Och aye, laddies and lassies! Welcome tae the ChangeCameraCollisionStrategy class. This strategy here's
 * as bonnie as the Highlands, changin' the camera's focus when there's a collision with a ball. It's all
 * aboot givin' the players a view worth a wee dram o' whisky, ya ken?
 */
package bricker.brick_strategies;

import bricker.constants.GameConstants;
import bricker.gameobjects.Ball;
import bricker.gameobjects.CameraReverter;
import bricker.main.BrickerGameManager;
import danogl.GameObject;
import danogl.util.Vector2;

public class ChangeCameraCollisionStrategy implements CollisionStrategy {

    private final BrickerGameManager gameManager;

    /**
     * Och aye, mateys! This here constructor sets up the ChangeCameraCollisionStrategy, makin' sure we're
     * ready tae switch the camera focus when there's a collision in the game.
     *
     * @param gameManager The game manager, keepin' things in order and runnin' smoothly.
     */
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
