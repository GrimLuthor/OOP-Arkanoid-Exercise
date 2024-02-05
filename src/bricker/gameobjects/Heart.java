package bricker.gameobjects;

import bricker.constants.GameConstants;
import bricker.main.BrickerGameManager;
import danogl.GameObject;
import danogl.collisions.GameObjectCollection;
import danogl.components.Component;
import danogl.gui.rendering.Renderable;
import danogl.util.Vector2;

public class Heart extends GameObject {

    private static final String TAG = GameConstants.HEART_TAG;

    private BrickerGameManager gameManager;

    public Heart(Vector2 topLeftCorner, Vector2 dimensions, Renderable renderable, BrickerGameManager gameManager) {
        super(topLeftCorner, dimensions, renderable);
        this.gameManager = gameManager;
    }

    public void removeSelf() {
        gameManager.removeObjectFromRender(this, GameConstants.HEART_LAYER);
    }

    public String getTag() {
        return TAG;
    }
}
