package bricker.brick_strategies;

import bricker.constants.GameConstants;
import bricker.gameobjects.HeartToken;
import bricker.main.BrickerGameManager;
import danogl.GameObject;
import danogl.gui.rendering.ImageRenderable;
import danogl.util.Vector2;

public class AddHeartCollisionStrategy implements CollisionStrategy {

    private final BrickerGameManager gameManager;

    public AddHeartCollisionStrategy (BrickerGameManager gameManager) {
        this.gameManager = gameManager;
    }

    @Override
    public void onCollision(GameObject gameObject1, GameObject gameObject2) {

        System.out.println("add heart");

        Vector2 heartDims = new Vector2(GameConstants.HEART_SIZE,GameConstants.HEART_SIZE);
        Vector2 heartPos = new Vector2(gameObject1.getCenter().subtract(heartDims.mult(0.5f)));
        ImageRenderable heartImage = gameManager.getImageReader().readImage(
                GameConstants.HEART_IMAGE_PATH, true);

        HeartToken heartToken = new HeartToken(heartPos, heartDims, heartImage, gameManager);
        heartToken.setVelocity(Vector2.DOWN.mult(GameConstants.HEART_SPEED));
        gameManager.addObjectToRender(heartToken, GameConstants.HEART_TOKEN_LAYER);

        gameManager.getBricksCounter().decrement();
        gameManager.removeObjectFromRender(gameObject1, GameConstants.BRICK_LAYER);
    }
}
