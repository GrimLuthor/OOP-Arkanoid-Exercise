package bricker.gameobjects;

import bricker.main.GameConstants;
import bricker.main.BrickerGameManager;
import danogl.GameObject;
import danogl.gui.rendering.Renderable;
import danogl.util.Vector2;

/**
 * Hey there, folks! Welcome to the Heart class. This class represents a lovely heart object
 * within the Bricker game. It's all about spreading some love and positivity!
 */

public class Heart extends GameObject {

    private final BrickerGameManager gameManager;

    /**
     * Constructs a new Heart object with the specified parameters. Spreadin' love, one beat at a time!
     *
     * @param topLeftCorner The position where this heart starts, radiating affection.
     * @param dimensions    The dimensions (size) of this lovely heart.
     * @param renderable    The visual representation of this heart, warming everyone's souls.
     * @param gameManager   The game manager, keeping track of things in the game world.
     */
    public Heart(Vector2 topLeftCorner, Vector2 dimensions, Renderable renderable,
                 BrickerGameManager gameManager) {
        super(topLeftCorner, dimensions, renderable);
        this.gameManager = gameManager;
        setTag(GameConstants.HEART_TAG);
    }

    /**
     * Removes this heart from the game world. Sometimes, even hearts need a break, you know?
     */
    public void removeSelf() {
        gameManager.removeObjectFromRender(this, GameConstants.HEART_LAYER);
    }
}
