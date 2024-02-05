package bricker.gameobjects;

import bricker.brick_strategies.CollisionStrategy;
import bricker.constants.GameConstants;
import danogl.GameObject;
import danogl.collisions.Collision;
import danogl.gui.rendering.Renderable;
import danogl.util.Vector2;

public class Brick extends GameObject {

    private static final String TAG = GameConstants.BRICK_TAG;

    private CollisionStrategy collisionStrategy;

    public Brick(Vector2 topLeftCorner, Vector2 dimensions, Renderable renderable, CollisionStrategy collisionStrategy) {
        super(topLeftCorner, dimensions, renderable);
        this.collisionStrategy = collisionStrategy;
    }

    @Override
    public void onCollisionEnter(GameObject other, Collision collision) {
        collisionStrategy.onCollision(this, other);
    }

    @Override
    public boolean shouldCollideWith(GameObject other) {
        if (other.getTag().equals(GameConstants.BRICK_TAG)) {
            return false;
        }
        return super.shouldCollideWith(other);
    }

    public String getTag() {
        return TAG;
    }
}

