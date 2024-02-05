package bricker.brick_strategies;

import bricker.constants.GameConstants;
import bricker.gameobjects.Puck;
import bricker.main.BrickerGameManager;
import danogl.GameManager;
import danogl.GameObject;
import danogl.gui.ImageReader;
import danogl.gui.Sound;
import danogl.gui.rendering.ImageRenderable;
import danogl.util.Counter;
import danogl.util.Vector2;

import java.util.Random;

public class AddPuckCollisionStrategy implements CollisionStrategy {

    private BrickerGameManager gameManager;

    public AddPuckCollisionStrategy (BrickerGameManager gameManager) {
        this.gameManager = gameManager;
    }

    @Override
    public void onCollision(GameObject gameObject1, GameObject gameObject2) {

        System.out.println("add puck");

        Vector2 puckDims = new Vector2(GameConstants.BALL_SIZE,GameConstants.BALL_SIZE).mult(GameConstants.BALL_PUCK_RATIO);
        Vector2 puckPos = new Vector2(gameObject1.getCenter().subtract(puckDims.mult(0.5f)));
        ImageRenderable puckImage = gameManager.getImageReader().readImage(GameConstants.PUCK_IMAGE_PATH,true);
        Sound collisionSound = gameManager.getSoundReader().readSound(GameConstants.BALL_IMPACT_SOUND_PATH);
        Random random = new Random();
        for (int i = 0; i < GameConstants.PUCK_AMOUNT; i++) {
            double angle = random.nextDouble() * Math.PI;
            float VelX = (float) Math.cos(angle);
            float VelY = (float) Math.sin(angle);
            Puck puck = new Puck(puckPos,puckDims,puckImage,collisionSound,gameManager);
            puck.setVelocity(new Vector2(VelX,VelY).mult(GameConstants.BALL_SPEED));
            gameManager.addObjectToRender(puck,GameConstants.BALL_LAYER);
        }
        gameManager.getBricksCounter().decrement();
        gameManager.removeObjectFromRender(gameObject1,GameConstants.BRICK_LAYER);
    }
}
