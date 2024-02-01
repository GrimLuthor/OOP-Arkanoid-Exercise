package bricker.brick_strategies;

import danogl.GameObject;

public class BasicCollisionStrategy implements CollisionStrategy {
    @Override
    public void onCollision(GameObject gameObject1, GameObject gameObject2) {
        System.out.println("collision with brick detected");
    }
}
