package bricker.brick_strategies;

import bricker.constants.GameConstants;
import bricker.gameobjects.HeartToken;
import bricker.main.BrickerGameManager;
import danogl.GameObject;
import danogl.gui.rendering.ImageRenderable;
import danogl.util.Vector2;

/**
 * Howdy, y'all! Welcome to the AddHeartCollisionStrategy class. This here strategy is as true as grits,
 * addin' a heart token to the game when a collision occurs. It's all 'bout spreadin' some lovin' on them
 * dusty old bricks in the Bricker game.
 */

public class AddHeartCollisionStrategy implements CollisionStrategy {

    private final BrickerGameManager gameManager;

    /**
     * Howdy, friends! This here constructor sets up the AddHeartCollisionStrategy, makin' sure we're
     * ready to spread some love in the game.
     *
     * @param gameManager The game manager, keepin' things in check on the ol' dusty trail.
     */
    public AddHeartCollisionStrategy(BrickerGameManager gameManager) {
        this.gameManager = gameManager;
    }

    @Override
    public void onCollision(GameObject gameObject1, GameObject gameObject2) {
        // calculates the position of the heart token
        Vector2 heartDims = new Vector2(GameConstants.HEART_SIZE, GameConstants.HEART_SIZE);
        Vector2 heartPos = new Vector2(gameObject1.getCenter().subtract(heartDims.mult(0.5f)));
        ImageRenderable heartImage = gameManager.getImageReader().readImage(
                GameConstants.HEART_IMAGE_PATH, true);
        // creates the heart token and adds it to the game
        HeartToken heartToken = new HeartToken(heartPos, heartDims, heartImage, gameManager);
        heartToken.setVelocity(Vector2.DOWN.mult(GameConstants.HEART_SPEED));
        gameManager.addObjectToRender(heartToken, GameConstants.HEART_TOKEN_LAYER);

        gameManager.removeBrick(gameObject1);
    }
}
