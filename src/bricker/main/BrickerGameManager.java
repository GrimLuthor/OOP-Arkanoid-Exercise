package bricker.main;

import bricker.brick_strategies.CollisionStrategy;
import bricker.brick_strategies.CollisionStrategyFactory;
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

/**
 * Alright, mate, listen up! This 'ere is the BrickerGameManager, the heart and soul of the game.
 * It's the boss, the governor, the one pullin' all the strings behind the scenes, ya get me?
 * So, let's break it down, yeah?
 */

public class BrickerGameManager extends GameManager {

    // Oi, hold tight, check out these variables, innit?
    private final Vector2 brickParams;
    private final Random random = new Random();
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

    /**
     *  Alright, let's get the show on the road, mate! Fire up the main method and let's roll!
     */
    public static void main(String[] args) {
        BrickerGameManager gameManager;
        // Check if the user has provided the dimensions for the bricks. If not, use the default ones.
        if (args.length == GameConstants.EXPECTED_ARGS_AMOUNT) {
            gameManager = new BrickerGameManager(GameConstants.TITLE_NAME, GameConstants.WINDOW_SIZE,
                    new Vector2(Integer.parseInt(args[0]), Integer.parseInt(args[1])));

        } else {
            gameManager = new BrickerGameManager(GameConstants.TITLE_NAME, GameConstants.WINDOW_SIZE);
        }
        gameManager.run();
    }

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

    /**
     * Yo, fam! Dis 'ere be da constructor fer a fresh BrickerGameManager. It takes in da title o' da window,
     * da size o' da window, and da brick params. Dis constructor sets up da game manager wif da specified
     * title and size fer da window, ya know? Da brick params? Dey be tellin' us how many bricks we gonna
     * 'ave in da game and 'ow dey gonna be arranged, innit?
     *
     * @param windowTitle Da title o' da game window, matey.
     * @param windowDimensions Da size o' da game window, fam.
     * @param brickParams Da params determinin' da layout and arrangement o' da bricks in da game, matey.
     */
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

    /**
     * Adds a bloomin' object to the renderin' system with default layerin'.
     * Smooth and clean renderin' ensured, mate!
     *
     * @param gameObject The game object to be rendered, innit?
     */
    public void addObjectToRender(GameObject gameObject) {
        gameObjects().addGameObject(gameObject);
    }

    /**
     * Adds a bleedin' object to the renderin' system with a specific layer.
     * Ensures organization and tidiness in renderin', you see?
     *
     * @param gameObject The game object to be rendered, mate.
     * @param layer      The layer to render the object on, blimey!
     */
    public void addObjectToRender(GameObject gameObject, int layer) {
        gameObjects().addGameObject(gameObject, layer);
    }

    /**
     * Removes an object from renderin', maintaining neatness and tightness, mate.
     *
     * @param gameObject The game object to be removed from renderin', mate.
     */
    public void removeObjectFromRender(GameObject gameObject) {
        gameObjects().removeGameObject(gameObject);
    }

    /**
     * Removes an object from renderin' on a specific layer.
     * Ensures focus and sharpness in renderin', bloke.
     *
     * @param gameObject The game object to be removed from renderin', mate.
     * @param layer      The layer from which to remove the object, blimey.
     */
    public void removeObjectFromRender(GameObject gameObject, int layer) {
        gameObjects().removeGameObject(gameObject, layer);
    }

    /**
     * Sets the camera to follow the ball, ensurin' dynamic and lively trackin'.
     * The camera widens the frame and shares window dimensions for smooth trackin', you see.
     */
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

    /** Revert the camera, mate! Keep it stable, keep it still!
     */
    public void revertCamera() {
        setCamera(null);
    }

    /** Get the window dimensions, mate! Know your space, know your limits!
     */
    public Vector2 getWindowDimensions() {
        return windowDimensions;
    }

    /** Get the sound reader, mate! Keep it loud, keep it clear!
     */
    public SoundReader getSoundReader() {
        return soundReader;
    }

    /** Get the image reader, mate! Keep it visual, keep it vibrant!
     */
    public ImageReader getImageReader() {
        return imageReader;
    }

    /** Get the input listener, mate! Keep it responsive, keep it active!
     */
    public UserInputListener getInputListener() {
        return inputListener;
    }

    /** Get the bricks counter, mate! Keep it countin', keep it steady!
     */
    public Counter getBricksCounter() {
        return bricksCounter;
    }

    /** Get the extra paddle, mate! Keep it versatile, keep it flexible!
     */
    public ExtraPaddle getExtraPaddle() {
        return extraPaddle;
    }

    /** Set the extra paddle, mate! Keep it adaptable, keep it resourceful!
     */
    public void setExtraPaddle(ExtraPaddle extraPaddle) {
        this.extraPaddle = extraPaddle;
    }

    /**
     * Howdy, cowboy! This here method removes a brick from the game when it's time for it to mosey on out.
     *
     * @param gameObject1 The brick object to be removed.
     */
    public void removeBrick(GameObject gameObject1) {
        getBricksCounter().decrement();
        removeObjectFromRender(gameObject1, GameConstants.BRICK_LAYER);
    }

    /**
     * Yo, let's stack those lives! Bring 'em back, bring 'em strong!
     */
    public void increaseLives() {
        livesBar.addLife();
        updateLivesCounter();
    }

    // Decrease dem lives, mate! Gotta keep it real, keep it challenging!
    private void decreaseLives() {
        livesBar.removeLife();
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
}
