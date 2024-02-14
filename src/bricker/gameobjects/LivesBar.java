package bricker.gameobjects;

import bricker.constants.GameConstants;
import bricker.main.BrickerGameManager;
import danogl.GameObject;
import danogl.gui.rendering.ImageRenderable;
import danogl.gui.rendering.Renderable;
import danogl.util.Counter;
import danogl.util.Vector2;

/**
 * Yo, fam, check this out! This class right here is the LivesBar, keepin' track of how many lives you got
 * left in the Bricker game. It's all about survivin' on these mean streets, ya know?
 */

public class LivesBar extends GameObject {

    private final Counter livesCount = new Counter();
    private final Heart[] hearts;
    private final BrickerGameManager gameManager;

    /**
     * Aight, listen up! This constructor right here sets up the LivesBar, ready to keep tabs on your lives.
     * We gotta know how much backup you got on these streets, innit?
     *
     * @param topLeftCorner The spot where this lives bar kicks off, holdin' it down for ya.
     * @param dimensions    The size 'n' shape of this bar, representin' your survivability.
     * @param renderable    The visual representation, makin' sure you're always aware of your situation.
     * @param lives         The number of lives you start with, 'cause we gotta know what we're dealin' with.
     * @param gameManager   The game manager, keepin' everything in check on these tough streets.
     */
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

    /**
     * Yo, fam, when life gives you lemons, you add more hearts to this bar! This method right here adds
     * another life to your count, 'cause we all need a bit more backup on these gritty streets, ya dig?
     */
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

    /**
     * Sometimes, fam, life just ain't fair, ya know? When that happens, you gotta remove a heart from
     * this bar, 'cause that's just how the game goes on these cold streets.
     */
    public void removeLife() {
        if (livesCount.value() > 0) {
            livesCount.decrement();
            hearts[livesCount.value()].removeSelf();
        }
    }

    /**
     * Yo, keepin' it real, fam! This method right here gives you the lowdown on how many lives you got left
     * in the game. Gotta stay aware of your situation out here, ya feel?
     *
     * @return The number of lives you got left.
     */
    public int getLivesCount() {
        return this.livesCount.value();
    }
}
