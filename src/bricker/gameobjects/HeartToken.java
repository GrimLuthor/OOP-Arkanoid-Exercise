/**
 * Oi, look over here! This 'ere be the HeartToken class, representin' a token of love in the Bricker game.
 * It's all about spreadin' some warmth 'n' affection, ya know?
 */
package bricker.gameobjects;

import bricker.constants.GameConstants;
import bricker.main.BrickerGameManager;
import danogl.GameObject;
import danogl.gui.rendering.Renderable;
import danogl.util.Vector2;

public class HeartToken extends GameObject {

    BrickerGameManager gameManager;

    /**
     * 'Ey mate, check it out! This 'ere method constructs a brand spankin' new HeartToken object,
     * ready to bring some lovin' into the game world.
     *
     * @param topLeftCorner The spot where this token starts, bringin' joy to all who see it.
     * @param dimensions    The size 'n' shape of this lovely token.
     * @param renderable    The visual representation, makin' everyone's hearts skip a beat.
     * @param gameManager   The game manager, keepin' things in check 'round here.
     */
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
