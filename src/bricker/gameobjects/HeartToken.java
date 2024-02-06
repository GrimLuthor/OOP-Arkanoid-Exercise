package bricker.gameobjects;

import bricker.constants.GameConstants;
import bricker.main.BrickerGameManager;
import danogl.GameObject;
import danogl.gui.rendering.Renderable;
import danogl.util.Vector2;

public class HeartToken extends Heart{
    private static final String TAG = GameConstants.HEART_TOKEN_TAG;

    public HeartToken(Vector2 topLeftCorner, Vector2 dimensions, Renderable renderable,
                 BrickerGameManager gameManager) {
        super(topLeftCorner, dimensions, renderable, gameManager);
    }

    @Override
    public boolean shouldCollideWith(GameObject other) {
        return other.getTag().equals(GameConstants.PADDLE_TAG);
    }

    public void removeSelf() {
        getGameManager().removeObjectFromRender(this, GameConstants.HEART_TOKEN_LAYER);
    }

    public String getTag() {
        return TAG;
    }
}
