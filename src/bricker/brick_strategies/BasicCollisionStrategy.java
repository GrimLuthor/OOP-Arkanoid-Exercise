
package bricker.brick_strategies;

import bricker.constants.GameConstants;
import bricker.gameobjects.Brick;
import danogl.GameObject;
import danogl.collisions.GameObjectCollection;
import danogl.util.Counter;

public class BasicCollisionStrategy implements CollisionStrategy {
    private GameObjectCollection gameObjects;
    private Counter brickCounter;

    public BasicCollisionStrategy(GameObjectCollection gameObjects , Counter brickCounter) {
        this.gameObjects = gameObjects;
        this.brickCounter = brickCounter;
    }

    @Override
    public void onCollision(GameObject gameObject1, GameObject gameObject2) {
//        System.out.println("collision with brick detected");
        brickCounter.decrement();
        gameObjects.removeGameObject(gameObject1, GameConstants.BRICK_LAYER);

    }
}
