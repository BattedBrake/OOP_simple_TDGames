package game;

import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.scenes.scene2d.ui.Image;

public class ProjectileManager {
    private static Array<Projectile> projectiles = new Array<>();

    public static void spawn(Texture tex, float x, float y, Enemy target, Stage stage) {
        Image img = new Image(tex);
        Projectile p = new Projectile(img, x, y, target);
        projectiles.add(p);
        stage.addActor(img);
    }

    public static void update(float delta) {
        for (int i = projectiles.size - 1; i >= 0; i--) {
            Projectile p = projectiles.get(i);
            if (p.update(delta)) {
                p.getImage().remove();
                projectiles.removeIndex(i);
            }
        }
    }
}
