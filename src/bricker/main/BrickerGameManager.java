package bricker.main;

import bricker.brick_strategies.BasicCollisionStrategy;
import bricker.gameobjects.*;
import danogl.GameManager;
import danogl.GameObject;
import danogl.collisions.Layer;
import danogl.components.CoordinateSpace;
import danogl.gui.*;
import danogl.gui.rendering.ImageRenderable;
import danogl.gui.rendering.RectangleRenderable;
import danogl.gui.rendering.TextRenderable;
import danogl.util.Vector2;

import java.awt.*;
import java.util.Random;

public class BrickerGameManager extends GameManager {

    private static final float BALL_SPEED = 150;

    private Ball ball;
    private Vector2 windowDimensions;


    private Vector2 brickParams;


    private SoundReader soundReader;
    private ImageReader imageReader;
    private UserInputListener inputListener;
    private WindowController windowController;


    private LivesBar livesBar;
    private GameObject livesCounter;
    private TextRenderable livesCounterLabel;

    public BrickerGameManager(String windowTitle, Vector2 windowDimensions) {
        super(windowTitle, windowDimensions);
        this.brickParams = new Vector2(7, 8);
    }

    public BrickerGameManager(String windowTitle, Vector2 windowDimensions, Vector2 brickParams) {
        super(windowTitle, windowDimensions);
        this.brickParams = brickParams;
    }

    @Override
    public void initializeGame(ImageReader imageReader, SoundReader soundReader,
                               UserInputListener inputListener, WindowController windowController) {
        super.initializeGame(imageReader, soundReader, inputListener, windowController);

        //window dimensions and controller:
        this.imageReader = imageReader;
        this.soundReader = soundReader;
        this.inputListener = inputListener;
        this.windowController = windowController;

        this.windowDimensions = windowController.getWindowDimensions();
        //layer interactions:
        gameObjects().layers().shouldLayersCollide(Layer.DEFAULT, Ball.BALL_LAYER, true);
        gameObjects().layers().shouldLayersCollide(Brick.BRICK_LAYER, Ball.BALL_LAYER, true);

        // creating lives bar:
        livesBar = new LivesBar(Vector2.ZERO,Vector2.ZERO,null,3,gameObjects(),imageReader);

        livesCounterLabel = new TextRenderable(""+livesBar.getLives());
        livesCounterLabel.setColor(Color.GREEN);
        livesCounter = new GameObject(new Vector2(windowDimensions.x()-25,5),new Vector2(20,20),livesCounterLabel);
        gameObjects().addGameObject(livesCounter,200);


        //creating walls:
        initWalls();

        //creating background:
        ImageRenderable backgroundImage = imageReader.readImage("assets/DARK_BG2_small.jpeg", false);
        GameObject background = new GameObject(Vector2.ZERO, windowDimensions, backgroundImage);
        background.setCoordinateSpace(CoordinateSpace.CAMERA_COORDINATES);
        gameObjects().addGameObject(background, Layer.BACKGROUND);

        // creating ball object:
        initBall();

        //creating paddle:
        ImageRenderable paddleImage = imageReader.readImage("assets/paddle.png", true);
        Paddle paddle = new Paddle(Vector2.ZERO, new Vector2(100, 15), paddleImage, inputListener,
                new Vector2(0, windowDimensions.x()));
        paddle.setCenter(new Vector2(windowDimensions.x() / 2, windowDimensions.y() - 30));
        gameObjects().addGameObject(paddle);


        //creating bricks:
        initBricks();
    }

    @Override
    public void update(float deltaTime) {
        super.update(deltaTime);
        float ballHeight = ball.getCenter().y();
        String prompt = "";
        if (ballHeight > windowDimensions.y()) {

            livesBar.removeLife();
            updateLivesCounter();

            if (livesBar.getLives() == 0) {
                prompt += "You lose! Play again?";
            }
            else {
                initBall();
            }
        }
        if (!prompt.isEmpty()) {
            if (windowController.openYesNoDialog(prompt)) {
                windowController.resetGame();
            }
            else {
                windowController.closeWindow();
            }
        }
    }

    private void updateLivesCounter () {
        if (livesBar.getLives() >= 3) {
            livesCounterLabel.setColor(Color.GREEN);

        }
        if (livesBar.getLives() == 2) {
            livesCounterLabel.setColor(Color.YELLOW);

        }
        if (livesBar.getLives() == 1) {
            livesCounterLabel.setColor(Color.RED);

        }
        livesCounterLabel.setString(""+livesBar.getLives());
    }

    private void initWalls() {
        GameObject ceiling = new GameObject(new Vector2(0, -4), new Vector2(windowDimensions.x(), 3),
                new RectangleRenderable(Color.RED));
        GameObject leftWall = new GameObject(new Vector2(-4, 0), new Vector2(3, windowDimensions.y()),
                new RectangleRenderable(Color.RED));
        GameObject rightWall = new GameObject(new Vector2(windowDimensions.x(), 0),
                new Vector2(3, windowDimensions.y()), new RectangleRenderable(Color.RED));

        gameObjects().addGameObject(ceiling);
        gameObjects().addGameObject(rightWall);
        gameObjects().addGameObject(leftWall);
    }

    private void initBall() {
        ImageRenderable ballImage = imageReader.readImage(Ball.IMAGE_PATH, true);
        Sound collisionSound = soundReader.readSound("assets/blop_cut_silenced.wav");
        Ball ball = new Ball(Vector2.ZERO, new Vector2(20, 20), ballImage, collisionSound);

        //setting velocity and position
        float ballVelX = BALL_SPEED;
        float ballVelY = BALL_SPEED;

        Random random = new Random();

        if (random.nextBoolean()) {
            ball.setVelocity(new Vector2(ballVelX, ballVelY));
        } else {
            ball.setVelocity(new Vector2(-ballVelX, ballVelY));
        }

        ball.setCenter(windowDimensions.mult((float) 0.5));

        this.ball = ball;
        gameObjects().addGameObject(ball, Ball.BALL_LAYER);
    }

    private void initBricks() {
        ImageRenderable brickImage = imageReader.readImage(Brick.IMAGE_PATH, false);
        BasicCollisionStrategy collisionStrategy = new BasicCollisionStrategy(gameObjects());
        float gap = 2;
        float brickWidth = ((windowDimensions.x() - (brickParams.y() * (gap))) / brickParams.y());
        float brickHeight = 15;

        float topLeftX = gap;
        float topLeftY = gap;
        for (int row = 0; row < brickParams.x(); row++) {
            for (int col = 0; col < brickParams.y(); col++) {
                Brick newBrick = new Brick(new Vector2(topLeftX, topLeftY), new Vector2(brickWidth, brickHeight), brickImage, collisionStrategy);
                gameObjects().addGameObject(newBrick, Brick.BRICK_LAYER);
                topLeftX += brickWidth + gap;
            }
            topLeftX = gap;
            topLeftY += brickHeight + gap;
        }
    }

    public static void main(String[] args) {
        BrickerGameManager gameManager;
        if (args.length == 2) {
            gameManager = new BrickerGameManager("Bricker",
                    new Vector2(700, 500),
                    new Vector2(Integer.parseInt(args[0]), Integer.parseInt(args[1])));

        } else {
            gameManager = new BrickerGameManager("Bricker", new Vector2(700, 500), new Vector2(7, 8));
        }
        gameManager.run();
    }
}
