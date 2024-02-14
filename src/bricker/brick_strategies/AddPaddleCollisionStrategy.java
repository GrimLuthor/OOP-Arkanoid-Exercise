/**
 * Ay up, mates! Welcome to the AddPaddleCollisionStrategy class. This 'ere strategy is a right treat,
 * addin' an extra paddle to the game when there's a collision. It's all about givin' the players a bit
 * more oomph on the ol' paddle, ain't it?
 */
package bricker.brick_strategies;

import bricker.constants.GameConstants;
import bricker.gameobjects.ExtraPaddle;
import bricker.main.BrickerGameManager;
import danogl.GameObject;
import danogl.gui.rendering.ImageRenderable;
import danogl.util.Vector2;

public class AddPaddleCollisionStrategy implements CollisionStrategy {

    private final BrickerGameManager gameManager;

    /**
     * Alright, guv'nor! This 'ere constructor sets up the AddPaddleCollisionStrategy, makin' sure we're
     * ready to give 'em an extra paddle in the game.
     *
     * @param gameManager The game manager, keepin' things in check on the streets.
     */
    public AddPaddleCollisionStrategy(BrickerGameManager gameManager) {
        this.gameManager = gameManager;
    }

    @Override
    public void onCollision(GameObject gameObject1, GameObject gameObject2) {

        if (gameManager.getExtraPaddle() == null) {
            Vector2 exPaddlePos = new Vector2(gameManager.getWindowDimensions().mult(0.5f).subtract(
                    GameConstants.PADDLE_SIZE.mult(0.5f)));
            ImageRenderable exPaddleImage = gameManager.getImageReader().readImage(
                    GameConstants.PADDLE_IMAGE_PATH, true);

            ExtraPaddle extraPaddle = new ExtraPaddle(exPaddlePos, GameConstants.PADDLE_SIZE, exPaddleImage,
                    gameManager, Vector2.DOWN.mult(gameManager.getWindowDimensions().x()));

            gameManager.addObjectToRender(extraPaddle, GameConstants.EXTRA_PADDLE_LAYER);
            gameManager.setExtraPaddle(extraPaddle);
        }
        removeBrick(gameManager, gameObject1);
    }
}
