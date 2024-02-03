package bricker.gameobjects;

import bricker.constants.GameConstants;
import danogl.GameObject;
import danogl.collisions.GameObjectCollection;
import danogl.gui.ImageReader;
import danogl.gui.rendering.ImageRenderable;
import danogl.gui.rendering.Renderable;
import danogl.util.Vector2;

public class LivesBar extends GameObject {

    private int livesCount = 0;
    private Heart[] hearts;

    private GameObjectCollection gameObjects;
    private ImageReader imageReader;

    public LivesBar(Vector2 topLeftCorner, Vector2 dimensions, Renderable renderable, int lives,
                    GameObjectCollection gameObjects, ImageReader imageReader) {
        super(topLeftCorner, dimensions, renderable);

        this.hearts = new Heart[GameConstants.MAX_LIVES];
        this.gameObjects = gameObjects;
        this.imageReader = imageReader;

        for (int i = 0; i < lives; i++) {
            addLife();
        }
    }

    public void addLife() {
        if (livesCount < hearts.length) {
            float heartPosX = GameConstants.GAP_BETWEEN_HEARTS+((livesCount)*(GameConstants.GAP_BETWEEN_HEARTS+Heart.HEART_SIZE));
            ImageRenderable heartImage = imageReader.readImage(GameConstants.HEART_IMAGE_PATH,true);
            hearts[livesCount] = new Heart(new Vector2(heartPosX,GameConstants.GAP_BETWEEN_HEARTS),
                    new Vector2(Heart.HEART_SIZE,Heart.HEART_SIZE),heartImage,gameObjects);
            gameObjects.addGameObject(hearts[livesCount],GameConstants.HEART_LAYER);
            livesCount++;
        }

    }

    public void removeLife() {
        if (livesCount > 0) {
            livesCount--;
            hearts[livesCount].removeSelf();
        }
    }

    public int getLivesCount() {
        return this.livesCount;
    }
}
