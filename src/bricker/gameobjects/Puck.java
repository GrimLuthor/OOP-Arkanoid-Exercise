package bricker.gameobjects;

import bricker.main.GameConstants;
import bricker.main.BrickerGameManager;
import danogl.gui.Sound;
import danogl.gui.rendering.Renderable;
import danogl.util.Vector2;

/**
 * Hey there! Welcome to the Puck class. This class represents a puck object in the Bricker game.
 * It's like a ball, but with its own cool vibe, you know?
 */

public class Puck extends Ball {

    /**
     * Yo, check it out! This constructor creates a fresh Puck object with the given parameters,
     * ready to glide through the game with style.
     *
     * @param topLeftCorner   The starting position of this slick puck.
     * @param dimensions      The dimensions (size) of the puck, making sure it's just the right fit.
     * @param renderable      The visual representation, keeping this puck looking fly.
     * @param collisionSound  The sound to play when the puck collides with something, adding to its swagger.
     * @param gameManager     The game manager, keeping everything in check on the ice.
     */
    public Puck(Vector2 topLeftCorner, Vector2 dimensions, Renderable renderable, Sound collisionSound,
                BrickerGameManager gameManager) {
        super(topLeftCorner, dimensions, renderable, collisionSound, gameManager);
        setTag(GameConstants.PUCK_TAG);
    }
}
