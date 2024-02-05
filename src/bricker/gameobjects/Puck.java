package bricker.gameobjects;

import bricker.constants.GameConstants;
import bricker.main.BrickerGameManager;
import danogl.gui.Sound;
import danogl.gui.rendering.Renderable;
import danogl.util.Vector2;

public class Puck extends Ball{

    public static final String TAG = GameConstants.PUCK_TAG;

    public Puck(Vector2 topLeftCorner, Vector2 dimensions, Renderable renderable, Sound collisionSound, BrickerGameManager gameManager) {
        super(topLeftCorner, dimensions, renderable, collisionSound, gameManager);
    }
}
