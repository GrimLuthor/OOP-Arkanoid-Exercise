package bricker.brick_strategies;

import danogl.GameObject;
import danogl.collisions.GameObjectCollection;

public class BasicCollisionStrategy implements CollisionStrategy {
    private GameObjectCollection gameObjects;

    public BasicCollisionStrategy(GameObjectCollection gameObjects) {
        this.gameObjects = gameObjects;
    }

    @Override
    public void onCollision(GameObject gameObject1, GameObject gameObject2) {
        System.out.println("collision with brick detected");
        gameObjects.removeGameObject(gameObject1);
    }
}
