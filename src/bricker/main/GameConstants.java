package bricker.main;

import danogl.gui.rendering.RectangleRenderable;
import danogl.util.Vector2;

import java.awt.*;

/**
 * Yo, check it! Dis class be like da hub o' all da constants we need in our game, ya know? It's got all
 * da juicy deets 'bout da game variables, object layers, texture paths, placing params, sound paths, and
 * even dem game object tags, fam. So when we need any o' dese values elsewhere in our code, we just hit
 * up dis class, and boom, we got what we need, innit? It's like our cheat sheet to keep da game runnin'
 * smooth like butter, ya feel?
 */
public class GameConstants {

    /**
     * bruh.... just, bruh.
     */
    private GameConstants() {}

    // Names:
    /**
     * Yeah, we got a title. Big whoop.
     */
    public static final String TITLE_NAME = "Bricker";

    // Game Variables:
    /**
     * Like, why even bother documenting this, it's just a speed value.
     */
    public static final float BALL_SPEED = 150;

    /**
     * Seriously, who even reads this? It's just another speed value.
     */
    public static final float HEART_SPEED = 100;

    /**
     * Ugh, more speeds? Really?
     */
    public static final float PADDLE_SPEED = 250;

    /**
     * Do we really need to document the maximum lives? Come on.
     */
    public static final int MAX_LIVES = 4;

    /**
     * Starting lives... riveting stuff to document.
     */
    public static final int START_LIVES = 3;

    /**
     * Puck amount, like who cares? But here you go.
     */
    public static final int PUCK_AMOUNT = 2;

    /**
     * Number of collisions needed to remove an extra paddle. Yawn.
     */
    public static final int COLLISIONS_TO_REMOVE_EXTRA_PADDLE = 3;

    /**
     * Blah blah, collisions and camera, whatever.
     */
    public static final int COLLISIONS_TO_REVERT_CAMERA = 4;

    /**
     * Just a default vector amount... don't ask.
     */
    public static final Vector2 DEFAULT_BRICKS_AMOUNT = new Vector2(7, 8);

    // Architecture Params
    /**
     * Maximum randomness cap, because why not?
     */
    public static final int STRATEGY_RANDOM_CAP = 10;

    /**
     * Expected amount of arguments... thrilling, isn't it?
     */
    public static final int EXPECTED_ARGS_AMOUNT = 2;

    // Object Layers:
    /**
     * Negative layers, because we're so edgy.
     */
    public static final int BACKGROUND_IMAGE_LAYER = -69;

    /**
     * Heart layer, because why not have some hearts floating around?
     */
    public static final int HEART_LAYER = 69;

    /**
     * Just your average brick layer. Nothing special.
     */
    public static final int BRICK_LAYER = 1;

    /**
     * Ball layer, because balls need their own layer, apparently.
     */
    public static final int BALL_LAYER = 2;

    /**
     * Paddle layer... because layers are cool.
     */
    public static final int PADDLE_LAYER = 3;

    /**
     * Extra paddle layer, for those times when one paddle isn't enough.
     */
    public static final int EXTRA_PADDLE_LAYER = 4;

    /**
     * Layer for heart tokens... because hearts deserve layers too.
     */
    public static final int HEART_TOKEN_LAYER = 5;

    // Texture Paths:
    /**
     * Path to the heart image... as if anyone cares.
     */
    public static final String HEART_IMAGE_PATH = "assets/heart.png";

    /**
     * Path to the brick image. Riveting, I know.
     */
    public static final String BRICK_IMAGE_PATH = "assets/brick.png";

    /**
     * Path to the ball image. So exciting...
     */
    public static final String BALL_IMAGE_PATH = "assets/ball.png";

    /**
     * Path to the background image... because backgrounds.
     */
    public static final String BACKGROUND_IMAGE_PATH = "assets/DARK_BG2_small.jpeg";

    /**
     * Path to the paddle image. Like, who cares about paddles?
     */
    public static final String PADDLE_IMAGE_PATH = "assets/paddle.png";

    /**
     * Path to the puck image. Because variety, I guess.
     */
    public static final String PUCK_IMAGE_PATH = "assets/mockBall.png";

    // Texture Optionals:
    /**
     * Wall color... because walls need colors too, apparently.
     */
    public static final Color WALL_COLOR = Color.BLUE;

    /**
     * Wall render... or not, who knows.
     */
    public static final RectangleRenderable WALL_RENDER = null;
    // public static final RectangleRenderable WALL_RENDER = new RectangleRenderable(WALL_COLOR);

    // Placing Params:
    /**
     * Size of the ball... because size matters, right?
     */
    public static final float BALL_SIZE = 20;

    /**
     * Ball-to-puck size ratio... because ratios are cool.
     */
    public static final float BALL_PUCK_RATIO = 0.75f;

    /**
     * Size of the heart... because hearts have sizes, I guess.
     */
    public static final float HEART_SIZE = 25;

    /**
     * Gap between hearts... because gaps are important.
     */
    public static final float GAP_BETWEEN_HEARTS = 10;

    /**
     * Gap between bricks... because gaps, again.
     */
    public static final float GAP_BETWEEN_BRICKS = 3;

    /**
     * Padding at the bottom of the paddle... because paddles need padding, right?
     */
    public static final float PADDLE_BOTTOM_PADDING = 30;

    /**
     * X offset for lives count... because offsets are essential.
     */
    public static final float LIVES_COUNT_X_OFFSET = 25;

    /**
     * Padding up for lives count... because paddings are life.
     */
    public static final float LIVES_COUNT_PADDING_UP = 3;

    /**
     * Size of the lives bar... because bars need sizes.
     */
    public static final float LIVES_BAR_SIZE = 20;

    /**
     * Size of the wall... because walls need sizes too.
     */
    public static final float WALL_SIZE = 6.9f;

    /**
     * Height of bricks... because bricks have heights.
     */
    public static final float BRICKS_HEIGHT = 15;

    /**
     * Camera zoom... because cameras need to see stuff.
     */
    public static final float CAMERA_ZOOM = 0.7f;

    /**
     * Size of the paddle... because paddles have sizes too.
     */
    public static final Vector2 PADDLE_SIZE = new Vector2(100, 15);

    /**
     * Size of the window... because windows need sizes, obviously.
     */
    public static final Vector2 WINDOW_SIZE = new Vector2(700, 500);

    // Sound Paths:
    /**
     * Path to the ball impact sound... because balls make sounds, apparently.
     */
    public static final String BALL_IMPACT_SOUND_PATH = "assets/blop_cut_silenced.wav";

    // Game Object Tags:
    /**
     * Ball tag... because balls need tags too, I guess.
     */
    public static final String BALL_TAG = "Ball";

    /**
     * Brick tag... because bricks need to be tagged, eye roll.
     */
    public static final String BRICK_TAG = "Brick";

    /**
     * Heart tag... because hearts need tags, obviously.
     */
    public static final String HEART_TAG = "Heart";

    /**
     * Lives bar tag... because bars need tags too, right?
     */
    public static final String LIVES_BAR_TAG = "LivesBar";

    /**
     * Paddle tag... because paddles need to be tagged, obviously.
     */
    public static final String PADDLE_TAG = "Paddle";

    /**
     * Puck tag... because why not tag pucks?
     */
    public static final String PUCK_TAG = "Puckus Maximus";

    /**
     * Extra paddle tag... because extra paddles need tags too, apparently.
     */
    public static final String EXTRA_PADDLE_TAG = "Extrus Paddleus";

    /**
     * Heart token tag... because tokens need tags, for some reason.
     */
    public static final String HEART_TOKEN_TAG = "Heartus Tokenus";
}

