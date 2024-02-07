package bricker.gameobjects;

import bricker.constants.GameConstants;
import bricker.main.BrickerGameManager;
import danogl.GameObject;
import danogl.gui.rendering.ImageRenderable;
import danogl.gui.rendering.Renderable;
import danogl.util.Counter;
import danogl.util.Vector2;

public class LivesBar extends GameObject {

    private final Counter livesCount = new Counter();
    private final Heart[] hearts;
    private final BrickerGameManager gameManager;

    public LivesBar(Vector2 topLeftCorner, Vector2 dimensions, Renderable renderable, int lives,
                    BrickerGameManager gameManager) {
        super(topLeftCorner, dimensions, renderable);

        this.hearts = new Heart[GameConstants.MAX_LIVES];
        this.gameManager = gameManager;

        for (int i = 0; i < lives; i++) {
            addLife();
        }
        setTag(GameConstants.LIVES_BAR_TAG);
    }

    public void addLife() {
        if (livesCount.value() < hearts.length) {
            float heartPosX = GameConstants.GAP_BETWEEN_HEARTS + ((livesCount.value()) *
                    (GameConstants.GAP_BETWEEN_HEARTS + GameConstants.HEART_SIZE));
            ImageRenderable heartImage = gameManager.getImageReader().readImage(
                    GameConstants.HEART_IMAGE_PATH, true);
            hearts[livesCount.value()] = new Heart(new Vector2(heartPosX, GameConstants.GAP_BETWEEN_HEARTS),
                    new Vector2(GameConstants.HEART_SIZE, GameConstants.HEART_SIZE), heartImage, gameManager);
            gameManager.addObjectToRender(hearts[livesCount.value()], GameConstants.HEART_LAYER);
            livesCount.increment();
        }

    }

    public void removeLife() {
        if (livesCount.value() > 0) {
            livesCount.decrement();
            hearts[livesCount.value()].removeSelf();
        }
    }

    public int getLivesCount() {
        return this.livesCount.value();
    }
}
