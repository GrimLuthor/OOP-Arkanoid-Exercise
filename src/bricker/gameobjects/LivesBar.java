package bricker.gameobjects;

import bricker.constants.GameConstants;
import bricker.main.BrickerGameManager;
import danogl.GameObject;
import danogl.collisions.GameObjectCollection;
import danogl.gui.ImageReader;
import danogl.gui.rendering.ImageRenderable;
import danogl.gui.rendering.Renderable;
import danogl.util.Vector2;

public class LivesBar extends GameObject {

    private static final String TAG = GameConstants.LIVES_BAR_TAG;

    private int livesCount = 0;
    private Heart[] hearts;

    private BrickerGameManager gameManager;

    public LivesBar(Vector2 topLeftCorner, Vector2 dimensions, Renderable renderable, int lives,
                    BrickerGameManager gameManager) {
        super(topLeftCorner, dimensions, renderable);

        this.hearts = new Heart[GameConstants.MAX_LIVES];
        this.gameManager = gameManager;

        for (int i = 0; i < lives; i++) {
            addLife();
        }
    }

    public void addLife() {
        if (livesCount < hearts.length) {
            float heartPosX = GameConstants.GAP_BETWEEN_HEARTS+((livesCount)*
                    (GameConstants.GAP_BETWEEN_HEARTS+GameConstants.HEART_SIZE));
            ImageRenderable heartImage = gameManager.getImageReader().readImage(GameConstants.HEART_IMAGE_PATH,true);
            hearts[livesCount] = new Heart(new Vector2(heartPosX,GameConstants.GAP_BETWEEN_HEARTS),
                    new Vector2(GameConstants.HEART_SIZE,GameConstants.HEART_SIZE),heartImage,gameManager);
            gameManager.addObjectToRender(hearts[livesCount],GameConstants.HEART_LAYER);
            livesCount++;
        }

    }

    public void removeLife() {
        if (livesCount > 0) {
            livesCount--;
            hearts[livesCount].removeSelf();
        }
    }

    public int getLivesCount() {
        return this.livesCount;
    }

    public String getTag() {
        return TAG;
    }
}
