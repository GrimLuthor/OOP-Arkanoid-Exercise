package bricker.gameobjects;

import bricker.constants.GameConstants;
import bricker.main.BrickerGameManager;
import danogl.GameObject;
import danogl.gui.rendering.Renderable;
import danogl.util.Vector2;

public class Heart extends GameObject {

    private static final String TAG = GameConstants.HEART_TAG;
    private final int layer;
    private final BrickerGameManager gameManager;

    public Heart(Vector2 topLeftCorner, Vector2 dimensions, Renderable renderable,
                 BrickerGameManager gameManager, int layer) {
        super(topLeftCorner, dimensions, renderable);
        this.gameManager = gameManager;
        this.layer = layer;
    }

    @Override
    public boolean shouldCollideWith(GameObject other) {
        return other.getTag().equals(GameConstants.PADDLE_TAG);
    }

    public void removeSelf() {
        gameManager.removeObjectFromRender(this, layer);
    }

    public String getTag() {
        return TAG;
    }
}
