/**
 * Alright, mate, listen up! This 'ere is the BrickerGameManager, the heart and soul of the game.
 * It's the boss, the governor, the one pullin' all the strings behind the scenes, ya get me?
 * So, let's break it down, yeah?
 */
package bricker.main;

import bricker.brick_strategies.CollisionStrategy;
import bricker.brick_strategies.CollisionStrategyFactory;
import bricker.constants.GameConstants;
import bricker.gameobjects.*;
import danogl.GameManager;
import danogl.GameObject;
import danogl.collisions.Layer;
import danogl.components.CoordinateSpace;
import danogl.gui.*;
import danogl.gui.rendering.Camera;
import danogl.gui.rendering.ImageRenderable;
import danogl.gui.rendering.TextRenderable;
import danogl.util.Counter;
import danogl.util.Vector2;

import java.awt.*;
import java.awt.event.KeyEvent;
import java.util.Random;

public class BrickerGameManager extends GameManager {

    // Oi, hold tight, check out these variables, innit?
    private final Vector2 brickParams;
    private Ball ball;
    private ExtraPaddle extraPaddle;
    private Vector2 windowDimensions;
    private Counter bricksCounter;
    private SoundReader soundReader;
    private ImageReader imageReader;
    private UserInputListener inputListener;
    private WindowController windowController;
    private LivesBar livesBar;
    private TextRenderable livesCounterLabel;
    private final Random random = new Random();

    /**
     * Yo, check it! Constructor for the BrickerGameManager, yeah? Takes in the window title
     * and dimensions, proper setup for the game, innit?
     *
     * @param windowTitle      The title of the game window, gotta make it snazzy, you know?
     * @param windowDimensions The size of the game window, keep it real, keep it big!
     */
    public BrickerGameManager(String windowTitle, Vector2 windowDimensions) {
        super(windowTitle, windowDimensions);
        this.brickParams = GameConstants.DEFAULT_BRICKS_AMOUNT;
    }

    // You wanna go custom, mate? Here's the deal. Customize the bricks, make 'em your own!
    public BrickerGameManager(String windowTitle, Vector2 windowDimensions, Vector2 brickParams) {
        super(windowTitle, windowDimensions);
        this.brickParams = brickParams;
    }

    // Now, we get to the nitty-gritty, the initialization. Get ready to rumble, yeah?
    @Override
    public void initializeGame(ImageReader imageReader, SoundReader soundReader,
                               UserInputListener inputListener, WindowController windowController) {
        super.initializeGame(imageReader, soundReader, inputListener, windowController);

        // Here's where the magic happens, mate. Hold tight!
        this.imageReader = imageReader;
        this.soundReader = soundReader;
        this.inputListener = inputListener;
        this.windowController = windowController;
        this.windowDimensions = windowController.getWindowDimensions();
        this.bricksCounter = new Counter((int) (brickParams.x() * brickParams.y()));

        // Gotta set up those collisions, innit? Let's get crackin'!
        int[] ballLayerShouldCollideWith = {Layer.DEFAULT, GameConstants.BRICK_LAYER,
                GameConstants.PADDLE_LAYER, GameConstants.EXTRA_PADDLE_LAYER, GameConstants.BALL_LAYER};
        for (int layer : ballLayerShouldCollideWith) {
            gameObjects().layers().shouldLayersCollide(GameConstants.BALL_LAYER, layer, true);
        }
        gameObjects().layers().shouldLayersCollide(GameConstants.HEART_TOKEN_LAYER,
                GameConstants.PADDLE_LAYER, true);

        // Time to set the stage, mate! Let's get it!
        initLivesBar();
        initWalls();
        initBackground();
        initBall();
        initPaddle();
        initBricks();
    }

    // Update the game state, innit? Keep it fresh, keep it real!
    @Override
    public void update(float deltaTime) {
        super.update(deltaTime);
        if (ball.getCenter().y() > windowDimensions.y()) {
            decreaseLives();
            revertCamera();
            initBall();
            ball.setCenter(windowDimensions.mult(0.5f));
        }

        // Yo, check it! Win or lose, let's bring it home, yeah?
        String prompt = "";

        if (livesBar.getLivesCount() <= 0) {
            prompt += "You lose! Play again?";
        }

        if (bricksCounter.value() <= 0 || inputListener.isKeyPressed(KeyEvent.VK_W)) {
            prompt += "You win! Play again?";
        }
        if (!prompt.isEmpty()) {
            if (windowController.openYesNoDialog(prompt)) {
                windowController.resetGame();
            } else {
                windowController.closeWindow();
            }
        }
    }

    // Decrease dem lives, mate! Gotta keep it real, keep it challenging!
    public void decreaseLives() {
        livesBar.removeLife();
        updateLivesCounter();
    }

    // Yo, let's stack those lives! Bring 'em back, bring 'em strong!
    public void increaseLives() {
        livesBar.addLife();
        updateLivesCounter();
    }

    // Update dem lives, mate! Keep 'em fresh, keep 'em colorful!
    private void updateLivesCounter() {
        switch (livesBar.getLivesCount()) {
            case 2 -> livesCounterLabel.setColor(Color.YELLOW);
            case 1 -> livesCounterLabel.setColor(Color.RED);
            default -> livesCounterLabel.setColor(Color.GREEN);
        }
        livesCounterLabel.setString("" + livesBar.getLivesCount());
    }

    // Initiate dem walls, mate! Keep the game tight, keep it contained!
    private void initWalls() {
        GameObject ceiling = new GameObject(Vector2.UP.mult(GameConstants.WALL_SIZE),
                new Vector2(windowDimensions.x(), GameConstants.WALL_SIZE), GameConstants.WALL_RENDER);
        GameObject leftWall = new GameObject(Vector2.LEFT.mult(GameConstants.WALL_SIZE),
                new Vector2(GameConstants.WALL_SIZE, windowDimensions.y()), GameConstants.WALL_RENDER);
        GameObject rightWall = new GameObject(Vector2.RIGHT.mult(windowDimensions.x()),
                new Vector2(GameConstants.WALL_SIZE, windowDimensions.y()), GameConstants.WALL_RENDER);

        addObjectToRender(ceiling);
        addObjectToRender(rightWall);
        addObjectToRender(leftWall);
    }

    // Here we go, mate! Time to init the ball, the heart of the game!
    private void initBall() {
        ImageRenderable ballImage = imageReader.readImage(
                GameConstants.BALL_IMAGE_PATH, true);
        Sound collisionSound = soundReader.readSound(GameConstants.BALL_IMPACT_SOUND_PATH);
        Ball ball = new Ball(Vector2.ZERO, Vector2.ONES.mult(GameConstants.BALL_SIZE),
                ballImage, collisionSound, this);

        ball.setVelocity(Vector2.ONES.mult(GameConstants.BALL_SPEED));
        if (random.nextBoolean()) {
            ball.setVelocity(ball.getVelocity().multX(-1));
        }

        ball.setCenter(windowDimensions.mult(0.5f));
        this.ball = ball;
        addObjectToRender(ball, GameConstants.BALL_LAYER);
    }

    // Initialize the paddle, mate! The player's trusty steed!
    private void initPaddle() {
        ImageRenderable paddleImage = imageReader.readImage(
                GameConstants.PADDLE_IMAGE_PATH, true);
        Paddle paddle = new Paddle(Vector2.ZERO, GameConstants.PADDLE_SIZE, paddleImage, this,
                Vector2.DOWN.mult(windowDimensions.x()));
        paddle.setCenter(new Vector2(windowDimensions.x() / 2,
                windowDimensions.y() - GameConstants.PADDLE_BOTTOM_PADDING));
        addObjectToRender(paddle, GameConstants.PADDLE_LAYER);
    }

    // Initialize the background, mate! Set the scene, set the vibe!
    private void initBackground() {
        ImageRenderable backgroundImage = imageReader.readImage(
                GameConstants.BACKGROUND_IMAGE_PATH, false);
        GameObject background = new GameObject(Vector2.ZERO, windowDimensions, backgroundImage);
        background.setCoordinateSpace(CoordinateSpace.CAMERA_COORDINATES);
        addObjectToRender(background, GameConstants.BACKGROUND_IMAGE_LAYER);
    }

    // Initialize the lives bar, mate! Keep track, keep it real!
    private void initLivesBar() {
        livesBar = new LivesBar(Vector2.ZERO, Vector2.ZERO,
                null, GameConstants.START_LIVES, this);
        livesCounterLabel = new TextRenderable("" + livesBar.getLivesCount());
        livesCounterLabel.setColor(Color.GREEN);
        GameObject livesCounter = new GameObject(new Vector2(
                windowDimensions.x() - GameConstants.LIVES_COUNT_X_OFFSET,
                GameConstants.LIVES_COUNT_PADDING_UP),
                Vector2.ONES.mult(GameConstants.LIVES_BAR_SIZE), livesCounterLabel);
        addObjectToRender(livesCounter, GameConstants.HEART_LAYER);
    }

    // Initialize dem bricks, mate! Make 'em solid, make 'em tough!
    private void initBricks() {
        ImageRenderable brickImage = imageReader.readImage(
                GameConstants.BRICK_IMAGE_PATH, false);

        CollisionStrategyFactory collisionStrategyFactory = new CollisionStrategyFactory();

        float brickWidth = ((windowDimensions.x() -
                (brickParams.y() * (GameConstants.GAP_BETWEEN_BRICKS))) / brickParams.y());
        float brickHeight = GameConstants.BRICKS_HEIGHT;

        Vector2 topLeft = Vector2.ONES.mult(GameConstants.GAP_BETWEEN_BRICKS);
        for (int row = 0; row < brickParams.x(); row++) {
            for (int col = 0; col < brickParams.y(); col++) {
                CollisionStrategy collisionStrategy = collisionStrategyFactory.generateStrategy(
                        this, random.nextInt(GameConstants.STRATEGY_RANDOM_CAP));

                Brick newBrick = new Brick(topLeft, new Vector2(brickWidth, brickHeight),
                        brickImage, collisionStrategy);
                addObjectToRender(newBrick, GameConstants.BRICK_LAYER);
                topLeft = topLeft.add(new Vector2(brickWidth + GameConstants.GAP_BETWEEN_BRICKS, 0));
            }
            topLeft = new Vector2(GameConstants.GAP_BETWEEN_BRICKS,
                    topLeft.y() + brickHeight + GameConstants.GAP_BETWEEN_BRICKS);
        }
    }

    // Add object to render, mate! Keep it smooth, keep it clean!
    public void addObjectToRender(GameObject gameObject) {
        gameObjects().addGameObject(gameObject);
    }

    // Add object to render with a specific layer, mate! Keep it organized, keep it tidy!
    public void addObjectToRender(GameObject gameObject, int layer) {
        gameObjects().addGameObject(gameObject, layer);
    }

    // Remove object from render, mate! Keep it neat, keep it tight!
    public void removeObjectFromRender(GameObject gameObject) {
        gameObjects().removeGameObject(gameObject);
    }

    // Remove object from render with a specific layer, mate! Keep it focused, keep it sharp!
    public void removeObjectFromRender(GameObject gameObject, int layer) {
        gameObjects().removeGameObject(gameObject, layer);
    }

    // Set the camera to follow the ball, mate! Keep it dynamic, keep it lively!
    public void setCameraToFollowBall() {
        setCamera(
                new Camera(
                        ball, //object to follow
                        Vector2.ZERO, //follow the center of the object
                        windowController.getWindowDimensions().mult(GameConstants.CAMERA_ZOOM), // widen frame
                        windowController.getWindowDimensions() //share the window dimensions
                )
        );
    }

    // Revert the camera, mate! Keep it stable, keep it still!
    public void revertCamera() {
        setCamera(null);
    }

    // Get the window dimensions, mate! Know your space, know your limits!
    public Vector2 getWindowDimensions() {
        return windowDimensions;
    }

    // Get the sound reader, mate! Keep it loud, keep it clear!
    public SoundReader getSoundReader() {
        return soundReader;
    }

    // Get the image reader, mate! Keep it visual, keep it vibrant!
    public ImageReader getImageReader() {
        return imageReader;
    }

    // Get the input listener, mate! Keep it responsive, keep it active!
    public UserInputListener getInputListener() {
        return inputListener;
    }

    // Get the bricks counter, mate! Keep it countin', keep it steady!
    public Counter getBricksCounter() {
        return bricksCounter;
    }

    // Get the extra paddle, mate! Keep it versatile, keep it flexible!
    public ExtraPaddle getExtraPaddle() {
        return extraPaddle;
    }

    // Set the extra paddle, mate! Keep it adaptable, keep it resourceful!
    public void setExtraPaddle(ExtraPaddle extraPaddle) {
        this.extraPaddle = extraPaddle;
    }

    // Alright, let's get the show on the road, mate! Fire up the main method and let's roll!
    public static void main(String[] args) {
        BrickerGameManager gameManager;
        if (args.length == GameConstants.EXPECTED_ARGS_AMOUNT) {
            gameManager = new BrickerGameManager(GameConstants.TITLE_NAME, GameConstants.WINDOW_SIZE,
                    new Vector2(Integer.parseInt(args[0]), Integer.parseInt(args[1])));

        } else {
            gameManager = new BrickerGameManager(GameConstants.TITLE_NAME, GameConstants.WINDOW_SIZE);
        }
        gameManager.run();
    }
}
