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

    public BrickerGameManager(String windowTitle, Vector2 windowDimensions) {
        super(windowTitle, windowDimensions);
        this.brickParams = GameConstants.DEFAULT_BRICKS_AMOUNT;
    }

    public BrickerGameManager(String windowTitle, Vector2 windowDimensions, Vector2 brickParams) {
        super(windowTitle, windowDimensions);
        this.brickParams = brickParams;
    }

    @Override
    public void initializeGame(ImageReader imageReader, SoundReader soundReader,
                               UserInputListener inputListener, WindowController windowController) {
        super.initializeGame(imageReader, soundReader, inputListener, windowController);

        //save managers as private variables:
        this.imageReader = imageReader;
        this.soundReader = soundReader;
        this.inputListener = inputListener;
        this.windowController = windowController;
        this.windowDimensions = windowController.getWindowDimensions();
        this.bricksCounter = new Counter((int) (brickParams.x() * brickParams.y()));

        //layer interactions:
        int[] ballLayerShouldCollideWith = {Layer.DEFAULT, GameConstants.BRICK_LAYER,
                GameConstants.PADDLE_LAYER, GameConstants.EXTRA_PADDLE_LAYER, GameConstants.BALL_LAYER};
        for (int layer : ballLayerShouldCollideWith) {
            gameObjects().layers().shouldLayersCollide(GameConstants.BALL_LAYER, layer, true);
        }
        gameObjects().layers().shouldLayersCollide(GameConstants.HEART_TOKEN_LAYER,
                GameConstants.PADDLE_LAYER, true);

        // creating lives bar:
        initLivesBar();

        //creating walls:
        initWalls();

        //creating background:
        initBackground();

        // creating ball object:
        initBall();

        //creating paddle:
        initPaddle();

        //creating bricks:
        initBricks();
    }

    private void initPaddle() {
        ImageRenderable paddleImage = imageReader.readImage(
                GameConstants.PADDLE_IMAGE_PATH, true);
        Paddle paddle = new Paddle(Vector2.ZERO, GameConstants.PADDLE_SIZE, paddleImage, this,
                Vector2.DOWN.mult(windowDimensions.x()));
        paddle.setCenter(new Vector2(windowDimensions.x() / 2,
                windowDimensions.y() - GameConstants.GAP_BETWEEN_PADDLE_BOTTOM));
        addObjectToRender(paddle, GameConstants.PADDLE_LAYER);
    }

    private void initBackground() {
        ImageRenderable backgroundImage = imageReader.readImage(
                GameConstants.BACKGROUND_IMAGE_PATH, false);
        GameObject background = new GameObject(Vector2.ZERO, windowDimensions, backgroundImage);
        background.setCoordinateSpace(CoordinateSpace.CAMERA_COORDINATES);
        addObjectToRender(background, GameConstants.BACKGROUND_IMAGE_LAYER);
    }

    private void initLivesBar() {
        livesBar = new LivesBar(Vector2.ZERO, Vector2.ZERO,
                null, GameConstants.START_LIVES, this);
        livesCounterLabel = new TextRenderable("" + livesBar.getLivesCount());
        livesCounterLabel.setColor(Color.GREEN);
        GameObject livesCounter = new GameObject(new Vector2(
                windowDimensions.x() - GameConstants.GAP_LIVES_BAR, GameConstants.GAP_LIVES_BAR),
                Vector2.ONES.mult(GameConstants.LIVES_BAR_SIZE), livesCounterLabel);
        addObjectToRender(livesCounter, GameConstants.HEART_LAYER);
    }

    @Override
    public void update(float deltaTime) {
        super.update(deltaTime);
        if (ball.getCenter().y() > windowDimensions.y()) {
            decreaseLives();
            revertCamera();
            initBall();
            ball.setCenter(windowDimensions.mult(0.5f));
        }

        String prompt = "";

        if (livesBar.getLivesCount() == 0) {
            prompt += "You lose! Play again?";
        }

        if (bricksCounter.value() == 0 || inputListener.isKeyPressed(KeyEvent.VK_W)) {
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

    public void decreaseLives() {
        livesBar.removeLife();
        updateLivesCounter();
    }

    public void increaseLives() {
        livesBar.addLife();
        updateLivesCounter();
    }

    private void updateLivesCounter() {
        switch (livesBar.getLivesCount()) {
            case 2:
                livesCounterLabel.setColor(Color.YELLOW);
                break;
            case 1:
                livesCounterLabel.setColor(Color.RED);
                break;
            default:
                livesCounterLabel.setColor(Color.GREEN);
                break;
        }
        livesCounterLabel.setString("" + livesBar.getLivesCount());
    }

    private void initWalls() {
        GameObject ceiling = new GameObject(Vector2.UP.mult(GameConstants.WALL_SIZE),
                new Vector2(windowDimensions.x(), GameConstants.WALL_SIZE), null);
        GameObject leftWall = new GameObject(Vector2.LEFT.mult(GameConstants.WALL_SIZE),
                new Vector2(GameConstants.WALL_SIZE, windowDimensions.y()), null);
        GameObject rightWall = new GameObject(Vector2.RIGHT.mult(windowDimensions.x()),
                new Vector2(GameConstants.WALL_SIZE, windowDimensions.y()), null);

        addObjectToRender(ceiling);
        addObjectToRender(rightWall);
        addObjectToRender(leftWall);
    }

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

    public void addObjectToRender(GameObject gameObject) {
        gameObjects().addGameObject(gameObject);
    }

    public void addObjectToRender(GameObject gameObject, int layer) {
        gameObjects().addGameObject(gameObject, layer);
    }

    public void removeObjectFromRender(GameObject gameObject) {
        gameObjects().removeGameObject(gameObject);
    }

    public void removeObjectFromRender(GameObject gameObject, int layer) {
        gameObjects().removeGameObject(gameObject, layer);
    }

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

    public void revertCamera() {
        setCamera(null);
    }

    public Vector2 getWindowDimensions() {
        return windowDimensions;
    }

    public SoundReader getSoundReader() {
        return soundReader;
    }

    public ImageReader getImageReader() {
        return imageReader;
    }

    public UserInputListener getInputListener() {
        return inputListener;
    }

    public Counter getBricksCounter() {
        return bricksCounter;
    }

    public ExtraPaddle getExtraPaddle() {
        return extraPaddle;
    }

    public void setExtraPaddle(ExtraPaddle extraPaddle) {
        this.extraPaddle = extraPaddle;
    }

    public static void main(String[] args) {
        BrickerGameManager gameManager;
        if (args.length == GameConstants.ARGS_AMOUNT) {
            gameManager = new BrickerGameManager(GameConstants.TITLE_NAME, GameConstants.WINDOW_SIZE,
                    new Vector2(Integer.parseInt(args[0]), Integer.parseInt(args[1])));

        } else {
            gameManager = new BrickerGameManager(GameConstants.TITLE_NAME, GameConstants.WINDOW_SIZE);
        }
        gameManager.run();
    }
}
