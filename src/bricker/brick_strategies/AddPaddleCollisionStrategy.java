package bricker.brick_strategies;

import bricker.constants.GameConstants;
import bricker.gameobjects.ExtraPaddle;
import bricker.main.BrickerGameManager;
import danogl.GameObject;
import danogl.gui.rendering.ImageRenderable;
import danogl.util.Vector2;

public class AddPaddleCollisionStrategy implements CollisionStrategy {

    private final BrickerGameManager gameManager;

    public AddPaddleCollisionStrategy(BrickerGameManager gameManager) {
        this.gameManager = gameManager;
    }

    @Override
    public void onCollision(GameObject gameObject1, GameObject gameObject2) {

        if (gameManager.getExtraPaddle() == null) {
            System.out.println("add disk");

            Vector2 exPaddlePos = new Vector2(gameManager.getWindowDimensions().mult(0.5f).subtract(
                    GameConstants.PADDLE_SIZE.mult(0.5f)));
            ImageRenderable exPaddleImage = gameManager.getImageReader().readImage(
                    GameConstants.PADDLE_IMAGE_PATH, true);

            ExtraPaddle extraPaddle = new ExtraPaddle(exPaddlePos, GameConstants.PADDLE_SIZE, exPaddleImage,
                    gameManager, Vector2.DOWN.mult(gameManager.getWindowDimensions().x()));

            gameManager.addObjectToRender(extraPaddle,GameConstants.EXTRA_PADDLE_LAYER);
            gameManager.setExtraPaddle(extraPaddle);
        }
        gameManager.getBricksCounter().decrement();
        gameManager.removeObjectFromRender(gameObject1, GameConstants.BRICK_LAYER);
    }
}
