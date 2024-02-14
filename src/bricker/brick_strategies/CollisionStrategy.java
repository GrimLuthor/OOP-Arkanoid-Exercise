/**
 * Howdy, y'all! Welcome to the CollisionStrategy interface. This here interface sets the rules
 * for what goes down when objects bump heads in the Bricker game. It's all 'bout keepin' things
 * in line, ya know?
 */
package bricker.brick_strategies;

import bricker.constants.GameConstants;
import bricker.main.BrickerGameManager;
import danogl.GameObject;

public interface CollisionStrategy {

    /**
     * Hey there, partners! This method gets called when two objects collide in the game. It's up to y'all
     * to figure out what happens next!
     *
     * @param gameObject1 The first object involved in the collision.
     * @param gameObject2 The second object involved in the collision.
     */
    void onCollision(GameObject gameObject1, GameObject gameObject2);
}
