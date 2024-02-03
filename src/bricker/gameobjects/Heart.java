package bricker.gameobjects;

import bricker.constants.GameConstants;
import danogl.GameObject;
import danogl.collisions.GameObjectCollection;
import danogl.components.Component;
import danogl.gui.rendering.Renderable;
import danogl.util.Vector2;

public class Heart extends GameObject {
    public static final float HEART_SIZE = 25;

    private GameObjectCollection gameObjects;

    public Heart(Vector2 topLeftCorner, Vector2 dimensions, Renderable renderable, GameObjectCollection gameObjects) {
        super(topLeftCorner, dimensions, renderable);
        this.gameObjects = gameObjects;
    }

    public void removeSelf() {
        gameObjects.removeGameObject(this, GameConstants.HEART_LAYER);
    }
}
