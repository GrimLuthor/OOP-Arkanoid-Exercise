package bricker.main;

import bricker.gameobjects.Ball;
import danogl.GameManager;
import danogl.GameObject;
import danogl.collisions.Layer;
import danogl.components.CoordinateSpace;
import danogl.gui.ImageReader;
import danogl.gui.SoundReader;
import danogl.gui.UserInputListener;
import danogl.gui.WindowController;
import danogl.gui.rendering.ImageRenderable;
import danogl.gui.rendering.RectangleRenderable;
import danogl.util.Vector2;

import java.awt.*;

public class BrickerGameManager extends GameManager {

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
        initializeWalls(windowDimensions);

        //background:

        ImageRenderable backgroundImage = imageReader.readImage("assets/DARK_BG2_small.jpeg",false);
        GameObject background = new GameObject(Vector2.ZERO,windowDimensions,backgroundImage);
        background.setCoordinateSpace(CoordinateSpace.CAMERA_COORDINATES);

        // creating ball object:
        ImageRenderable ballImage = imageReader.readImage("assets/ball.png",true);
        Ball ball = new Ball(Vector2.ZERO, new Vector2(20,20),ballImage);

        //setting velocity and position
//        ball.setVelocity(Vector2.DOWN.mult(100));
        ball.setVelocity(new Vector2(1,-1).mult(100));
        ball.setCenter(windowDimensions.mult((float)0.5));


        //create paddle:
        ImageRenderable paddleImage = imageReader.readImage("assets/paddle.png",true);
        GameObject paddle = new GameObject(Vector2.ZERO,new Vector2(100,15),paddleImage);
        paddle.setCenter(new Vector2(windowDimensions.x()/2, windowDimensions.y()-30));



        //adding to the scene
        gameObjects().addGameObject(background, Layer.BACKGROUND);
        gameObjects().addGameObject(ball);
        gameObjects().addGameObject(paddle);


    }

    private void initializeWalls(Vector2 windowDimensions) {
        GameObject ceiling = new GameObject(new Vector2(0,-4),new Vector2(windowDimensions.x(),3),new RectangleRenderable(Color.RED));
        GameObject leftWall = new GameObject(new Vector2(-4,0), new Vector2(3, windowDimensions.y()),new RectangleRenderable(Color.RED));
        GameObject rightWall = new GameObject(new Vector2(windowDimensions.x(),0), new Vector2(3, windowDimensions.y()),new RectangleRenderable(Color.RED));
        gameObjects().addGameObject(ceiling);
        gameObjects().addGameObject(rightWall);
        gameObjects().addGameObject(leftWall);
    }

    public static void main(String[] args) {
        BrickerGameManager gameManager = new BrickerGameManager("Bricker", new Vector2(700, 500));
        gameManager.run();

    }
}
