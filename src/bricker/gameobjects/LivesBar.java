package bricker.gameobjects;

import danogl.GameObject;
import danogl.collisions.GameObjectCollection;
import danogl.gui.ImageReader;
import danogl.gui.rendering.ImageRenderable;
import danogl.gui.rendering.Renderable;
import danogl.util.Vector2;

public class LivesBar extends GameObject {
    public static final int MAX_LIVES = 4;
    public static final float GAP_BETWEEN_HEARTS = 10;
    public static final int LIVES_BAR_LAYER = 200;

    private int lives = 0;
    private Heart[] hearts;

    private GameObjectCollection gameObjects;
    private ImageReader imageReader;

    public LivesBar(Vector2 topLeftCorner, Vector2 dimensions, Renderable renderable, int lives, GameObjectCollection gameObjects, ImageReader imageReader) {
        super(topLeftCorner, dimensions, renderable);
        this.hearts = new Heart[MAX_LIVES];
        this.gameObjects = gameObjects;
        this.imageReader = imageReader;

        for (int i = 0; i < lives; i++) {
            addLife();
        }
    }

    public void addLife() {
        if (lives < MAX_LIVES) {
            float heartPosX = GAP_BETWEEN_HEARTS+((lives)*(GAP_BETWEEN_HEARTS+Heart.HEART_SIZE));
            ImageRenderable heartImage = imageReader.readImage(Heart.IMAGE_PATH,true);
            hearts[lives] = new Heart(new Vector2(heartPosX,GAP_BETWEEN_HEARTS),new Vector2(Heart.HEART_SIZE,Heart.HEART_SIZE),heartImage,gameObjects);
            gameObjects.addGameObject(hearts[lives],Heart.HEART_LAYER);
            lives++;
        }

    }

    public void removeLife() {
        if (lives > 0) {
            lives--;
            hearts[lives].removeSelf();
        }
    }

    public int getLives () {
        return this.lives;
    }
}
