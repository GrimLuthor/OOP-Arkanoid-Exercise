package bricker.gameobjects;

import bricker.brick_strategies.CollisionStrategy;
import danogl.GameObject;
import danogl.collisions.Collision;
import danogl.gui.rendering.Renderable;
import danogl.util.Vector2;

public class Brick extends GameObject {

    public static final int BRICK_LAYER = 1;

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
        if (other instanceof Brick) {
            return false;
        }
        return super.shouldCollideWith(other);
    }
}
