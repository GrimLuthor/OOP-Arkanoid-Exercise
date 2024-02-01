package bricker.main;

import bricker.brick_strategies.BasicCollisionStrategy;
import bricker.brick_strategies.CollisionStrategy;
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

    private static final float BALL_SPEED = 100;

    public BrickerGameManager(String windowTitle, Vector2 windowDimensions) {
        super(windowTitle, windowDimensions);
    }

    @Override
    public void initializeGame(ImageReader imageReader, SoundReader soundReader,
                               UserInputListener inputListener, WindowController windowController) {
        super.initializeGame(imageReader, soundReader, inputListener, windowController);


        //window dimensions:
        Vector2 windowDimensions = windowController.getWindowDimensions();

        //walls:
        initWalls(windowDimensions);

        //background:
        ImageRenderable backgroundImage = imageReader.readImage("assets/DARK_BG2_small.jpeg", false);
        GameObject background = new GameObject(Vector2.ZERO, windowDimensions, backgroundImage);
        background.setCoordinateSpace(CoordinateSpace.CAMERA_COORDINATES);

        // creating ball object:
        initBall(imageReader,soundReader,windowDimensions);

        //create paddle:
        ImageRenderable paddleImage = imageReader.readImage("assets/paddle.png", true);
        Paddle paddle = new Paddle(Vector2.ZERO, new Vector2(100, 15), paddleImage, inputListener,
                new Vector2(0, windowDimensions.x()));
        paddle.setCenter(new Vector2(windowDimensions.x() / 2, windowDimensions.y() - 30));

        //creating bricks:
        initBricks(imageReader,windowDimensions);


        //adding to the scene
        gameObjects().addGameObject(background, Layer.BACKGROUND);

        gameObjects().addGameObject(paddle);


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

    private void initBall(ImageReader imageReader,SoundReader soundReader,Vector2 windowDimensions) {
        ImageRenderable ballImage = imageReader.readImage("assets/ball.png", true);
        Sound collisionSound = soundReader.readSound("assets/blop_cut_silenced.wav");
        Ball ball = new Ball(Vector2.ZERO, new Vector2(20, 20), ballImage, collisionSound);

        //setting velocity and position
        float ballVelX = BALL_SPEED;
        float ballVelY = BALL_SPEED;

        Random random = new Random();

        if (random.nextBoolean()) {
            ball.setVelocity(new Vector2(ballVelX,ballVelY));
        } else {
            ball.setVelocity(new Vector2(-ballVelX,ballVelY));
        }

        ball.setCenter(windowDimensions.mult((float) 0.5));

        gameObjects().addGameObject(ball);
    }

    private void initBricks(ImageReader imageReader,Vector2 windowDimensions) {
        ImageRenderable brickImage = imageReader.readImage("assets/brick.png",false);
        BasicCollisionStrategy collisionStrategy = new BasicCollisionStrategy(gameObjects());
        Brick brick = new Brick(new Vector2(3,3),new Vector2(windowDimensions.x() - 6, 15),brickImage,collisionStrategy);
        gameObjects().addGameObject(brick);
    }

    public static void main(String[] args) {
        BrickerGameManager gameManager = new BrickerGameManager("Bricker", new Vector2(700, 500));
        gameManager.run();
    }
}
