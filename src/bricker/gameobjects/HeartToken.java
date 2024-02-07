package bricker.gameobjects;

import bricker.constants.GameConstants;
import bricker.main.BrickerGameManager;
import danogl.GameObject;
import danogl.gui.rendering.Renderable;
import danogl.util.Vector2;

public class HeartToken extends GameObject {

    BrickerGameManager gameManager;

    public HeartToken(Vector2 topLeftCorner, Vector2 dimensions, Renderable renderable,
                      BrickerGameManager gameManager) {
        super(topLeftCorner, dimensions, renderable);
        this.gameManager = gameManager;
        setTag(GameConstants.HEART_TOKEN_TAG);
    }

    @Override
    public boolean shouldCollideWith(GameObject other) {
        return other.getTag().equals(GameConstants.PADDLE_TAG);
    }

    private void removeSelf() {
        gameManager.removeObjectFromRender(this, GameConstants.HEART_TOKEN_LAYER);
    }

    @Override
    public void update(float deltaTime) {
        super.update(deltaTime);
        if (getCenter().y() > gameManager.getWindowDimensions().y()) {
            removeSelf();
        }
    }
}
