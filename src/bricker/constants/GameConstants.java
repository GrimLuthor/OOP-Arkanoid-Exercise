package bricker.constants;

import danogl.util.Vector2;

public class GameConstants {
    // Names:
    public static final String TITLE_NAME = "Bricker";

    // Game Variables:
    public static final int MAX_LIVES = 4;
    public static final float BALL_SPEED = 150;
    public static final float HEART_SPEED = 100;
    public static final float PADDLE_SPEED = 250;
    public static final Vector2 PADDLE_SIZE = new Vector2(100, 15);
    public static final int PUCK_AMOUNT = 2;
    public static final int COLLISIONS_TO_REMOVE_EXTRA_PADDLE = 3;

    // Object Layers:
    public static final int BACKGROUND_IMAGE_LAYER = -200;
    public static final int HEART_LAYER = 200;
    public static final int BRICK_LAYER = 1;
    public static final int BALL_LAYER = 2;

    // Texture Paths:
    public static final String HEART_IMAGE_PATH = "assets/heart.png";
    public static final String BRICK_IMAGE_PATH = "assets/brick.png";
    public static final String BALL_IMAGE_PATH = "assets/ball.png";
    public static final String BACKGROUND_IMAGE_PATH = "assets/DARK_BG2_small.jpeg";
    public static final String PADDLE_IMAGE_PATH = "assets/paddle.png";
    public static final String PUCK_IMAGE_PATH = "assets/mockBall.png";

    // Placing Params:
    public static final float GAP_BETWEEN_HEARTS = 10;
    public static final float HEART_SIZE = 25;
    public static final int BALL_SIZE = 20;
    public static final float BALL_PUCK_RATIO = 0.75f;
    public static final float GAP_BETWEEN_BRICKS = 3;

    // Sound Paths:
    public static final String BALL_IMPACT_SOUND_PATH = "assets/blop_cut_silenced.wav";

    // Game Object Tags:
    public static final String BALL_TAG = "Ball";
    public static final String BRICK_TAG = "Brick";
    public static final String HEART_TAG = "Heart";
    public static final String LIVES_BAR_TAG = "LivesBar";
    public static final String PADDLE_TAG = "Paddle";
    public static final String PUCK_TAG = "Puckus Maximus";
    public static final String EXTRA_PADDLE_TAG = "Extrus Paddleus";
}

