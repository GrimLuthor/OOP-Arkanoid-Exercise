/**
 * Ah, lads 'n' lasses, behold the mighty Brick class! This here brick be part o' the Bricker game,
 * standin' tall 'n' sturdy, ready fer any collision that may come its way.
 */
package bricker.gameobjects;

import bricker.brick_strategies.CollisionStrategy;
import bricker.constants.GameConstants;
import danogl.GameObject;
import danogl.collisions.Collision;
import danogl.gui.rendering.Renderable;
import danogl.util.Vector2;

public class Brick extends GameObject {

    private final CollisionStrategy collisionStrategy;

    /**
     * Creates a new Brick object with the given parameters, ready to take on the world!
     *
     * @param topLeftCorner      The position where this brick starts, in the grand scheme o' things.
     * @param dimensions         The size 'n' dimensions o' this here brick.
     * @param renderable         The visual representation of the brick, for all tae see.
     * @param collisionStrategy The strategy to handle collisions fer this brick.
     */
    public Brick(Vector2 topLeftCorner, Vector2 dimensions, Renderable renderable,
                 CollisionStrategy collisionStrategy) {
        super(topLeftCorner, dimensions, renderable);
        this.collisionStrategy = collisionStrategy;
        setTag(GameConstants.BRICK_TAG);
    }

    @Override
    public void onCollisionEnter(GameObject other, Collision collision) {
        collisionStrategy.onCollision(this, other);
    }

    @Override
    public boolean shouldCollideWith(GameObject other) {
        if (other.getTag().equals(GameConstants.BRICK_TAG)) {
            return false; // Nae need tae collide with yer ain kind, eh?
        }
        return super.shouldCollideWith(other);
    }
}
