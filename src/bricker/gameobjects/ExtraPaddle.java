package bricker.gameobjects;

import bricker.main.GameConstants;
import bricker.main.BrickerGameManager;
import danogl.GameObject;
import danogl.collisions.Collision;
import danogl.gui.rendering.Renderable;
import danogl.util.Counter;
import danogl.util.Vector2;

/**
 * Yo, check it! Dis right here be the ExtraPaddle class, representin' an extra paddle in da Bricker game.
 * It be like a regular paddle, but wit' some extra spice, ya feel me?
 */

public class ExtraPaddle extends Paddle {

    private final Counter collisionCounter = new Counter();

    /**
     * Ayo, listen up! Dis be the constructor fo' the ExtraPaddle class. It creates a dope extra paddle
     * wit' da given parameters, ready to rock 'n' roll in da game.
     *
     * @param topLeftCorner    Da top-left corner where dis fly paddle gon' start.
     * @param dimensions       Da size 'n' dimensions of dis bad boy.
     * @param renderable       Da visual representation, makin' dis paddle look fresh.
     * @param gameManager      Da game manager, keepin' things in check in da game world.
     * @param rangeOfMovement  Da range of movement fo' dis paddle, keepin' it in bounds.
     */
    public ExtraPaddle(Vector2 topLeftCorner, Vector2 dimensions, Renderable renderable,
                       BrickerGameManager gameManager, Vector2 rangeOfMovement) {
        super(topLeftCorner, dimensions, renderable, gameManager, rangeOfMovement);
        setTag(GameConstants.EXTRA_PADDLE_TAG);
    }

    @Override
    public void onCollisionEnter(GameObject other, Collision collision) {
        super.onCollisionEnter(other, collision);
        // counts the collisions, and if it reaches a certain number, the extra paddle is removed
        collisionCounter.increment();
        if (collisionCounter.value() > GameConstants.COLLISIONS_TO_REMOVE_EXTRA_PADDLE) {
            getGameManager().removeObjectFromRender(this, GameConstants.EXTRA_PADDLE_LAYER);
            getGameManager().setExtraPaddle(null);
        }
    }
}
