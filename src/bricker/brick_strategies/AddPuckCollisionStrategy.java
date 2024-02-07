package bricker.brick_strategies;

import bricker.constants.GameConstants;
import bricker.gameobjects.Puck;
import bricker.main.BrickerGameManager;
import danogl.GameObject;
import danogl.gui.Sound;
import danogl.gui.rendering.ImageRenderable;
import danogl.util.Vector2;

import java.util.Random;

public class AddPuckCollisionStrategy implements CollisionStrategy {

    private final BrickerGameManager gameManager;

    public AddPuckCollisionStrategy(BrickerGameManager gameManager) {
        this.gameManager = gameManager;
    }

    @Override
    public void onCollision(GameObject gameObject1, GameObject gameObject2) {
        Vector2 puckDims = new Vector2(GameConstants.BALL_SIZE, GameConstants.BALL_SIZE)
                .mult(GameConstants.BALL_PUCK_RATIO);
        Vector2 puckPos = new Vector2(gameObject1.getCenter().subtract(puckDims.mult(0.5f)));
        ImageRenderable puckImage = gameManager.getImageReader().readImage(
                GameConstants.PUCK_IMAGE_PATH, true);
        Sound collisionSound = gameManager.getSoundReader().readSound(GameConstants.BALL_IMPACT_SOUND_PATH);

        Random random = new Random();
        for (int i = 0; i < GameConstants.PUCK_AMOUNT; i++) {
            Vector2 angle = randomizeAngle(random);
            Puck puck = new Puck(puckPos, puckDims, puckImage, collisionSound, gameManager);
            puck.setVelocity(new Vector2(angle.x(), angle.y()).mult(GameConstants.BALL_SPEED));
            gameManager.addObjectToRender(puck, GameConstants.BALL_LAYER);
        }
        removeBrick(gameManager, gameObject1);
    }

    private Vector2 randomizeAngle(Random random) {
        double randomAngle = random.nextDouble() * Math.PI;
        float VelX = (float) Math.cos(randomAngle);
        float VelY = (float) Math.sin(randomAngle);
        return new Vector2(VelX, VelY);
    }
}
