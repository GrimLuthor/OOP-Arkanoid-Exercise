package bricker.main;

import bricker.brick_strategies.BasicCollisionStrategy;
import bricker.gameobjects.Ball;
import bricker.gameobjects.Brick;
import bricker.gameobjects.Paddle;
import danogl.GameManager;
import danogl.GameObject;
import danogl.collisions.Layer;
import danogl.components.CoordinateSpace;
import danogl.gui.*;
import danogl.gui.rendering.ImageRenderable;
import danogl.gui.rendering.RectangleRenderable;
import danogl.util.Vector2;

import java.awt.*;
import java.util.Random;

public class BrickerGameManager extends GameManager {

    private static final float BALL_SPEED = 150;

    private Vector2 brickParams;

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

        //window dimensions:
        Vector2 windowDimensions = windowController.getWindowDimensions();

        //layer interactions:
        gameObjects().layers().shouldLayersCollide(Layer.DEFAULT,Ball.BALL_LAYER,true);
        gameObjects().layers().shouldLayersCollide(Brick.BRICK_LAYER,Ball.BALL_LAYER,true);

        //creating walls:
        initWalls(windowDimensions);

        //creating background:
        ImageRenderable backgroundImage = imageReader.readImage("assets/DARK_BG2_small.jpeg", false);
        GameObject background = new GameObject(Vector2.ZERO, windowDimensions, backgroundImage);
        background.setCoordinateSpace(CoordinateSpace.CAMERA_COORDINATES);
        gameObjects().addGameObject(background, Layer.BACKGROUND);

        // creating ball object:
        initBall(imageReader, soundReader, windowDimensions);

        //creating paddle:
        ImageRenderable paddleImage = imageReader.readImage("assets/paddle.png", true);
        Paddle paddle = new Paddle(Vector2.ZERO, new Vector2(100, 15), paddleImage, inputListener,
                new Vector2(0, windowDimensions.x()));
        paddle.setCenter(new Vector2(windowDimensions.x() / 2, windowDimensions.y() - 30));
        gameObjects().addGameObject(paddle);


        //creating bricks:
        initBricks(imageReader, windowDimensions, brickParams);
    }

    private void initWalls(Vector2 windowDimensions) {
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

    private void initBall(ImageReader imageReader, SoundReader soundReader, Vector2 windowDimensions) {
        ImageRenderable ballImage = imageReader.readImage("assets/ball.png", true);
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

        gameObjects().addGameObject(ball,Ball.BALL_LAYER);
    }

    private void initBricks(ImageReader imageReader, Vector2 windowDimensions, Vector2 brickParams) {
        ImageRenderable brickImage = imageReader.readImage("assets/brick.png", false);
        BasicCollisionStrategy collisionStrategy = new BasicCollisionStrategy(gameObjects());
        float gap = 2;
        float brickWidth = ((windowDimensions.x() - (brickParams.y() * (gap))) / brickParams.y());
        float brickHeight = 15;

        float topLeftX = gap;
        float topLeftY = gap;
        for (int row = 0; row < brickParams.x(); row++) {
            for (int col = 0; col < brickParams.y(); col++) {
                Brick newBrick = new Brick(new Vector2(topLeftX, topLeftY), new Vector2(brickWidth, brickHeight), brickImage, collisionStrategy);
                gameObjects().addGameObject(newBrick,Brick.BRICK_LAYER);
                topLeftX += brickWidth + gap;
            }
            topLeftX = gap;
            topLeftY += brickHeight + gap;
        }
    }

    public static void main(String[] args) {
        BrickerGameManager gameManager;
        System.out.println(args[1]);
        if (args.length == 2) {
            gameManager = new BrickerGameManager("Bricker",
                    new Vector2(700, 500),
                    new Vector2(Integer.parseInt(args[0]), Integer.parseInt(args[1])));

        }
        else {
            gameManager = new BrickerGameManager("Bricker", new Vector2(700, 500), new Vector2(7,8));
        }
        gameManager.run();
    }
}
