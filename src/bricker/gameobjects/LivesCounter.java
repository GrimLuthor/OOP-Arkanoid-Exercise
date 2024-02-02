package bricker.gameobjects;

import danogl.GameObject;
import danogl.gui.rendering.Renderable;
import danogl.gui.rendering.TextRenderable;
import danogl.util.Vector2;


import java.awt.*;

public class LivesCounter extends GameObject {

    public static final int LIVES_COUNTER_LAYER = 200;

    private TextRenderable label;

    public LivesCounter(Vector2 topLeftCorner, Vector2 dimensions, Renderable renderable) {
        super(topLeftCorner, dimensions, renderable);
        this.label = (TextRenderable) renderable;
    }


    public void setCount (int count) {
        if (count >= 3) {
            label.setColor(Color.GREEN);

        }
        if (count == 2) {
            label.setColor(Color.YELLOW);

        }
        if (count == 1) {
            label.setColor(Color.RED);

        }
        label.setString(""+count);
    }
}
