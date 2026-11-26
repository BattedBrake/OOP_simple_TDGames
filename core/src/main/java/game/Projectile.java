package game;

import com.badlogic.gdx.scenes.scene2d.ui.Image;

public class Projectile {
    private Image sprite;
    private float speed = 400f;
    private float x, y;
    private Enemy target;

    public Projectile(Image sprite, float x, float y, Enemy target) {
        this.sprite = sprite;
        this.sprite.setSize(50, 32);
        this.x = x;
        this.y = y;
        this.target = target;
        sprite.setPosition(x, y);
    }

    public Image getImage() {
        return sprite;
    }

    public boolean update(float delta) {
        if (target == null) return true;

        float dx = target.getX() - x;
        float dy = target.getY() - y;
        float dist = (float) Math.sqrt(dx*dx + dy*dy);

        if (dist < 5) return true; // cukup dekat, hilang

        float vx = dx / dist;
        float vy = dy / dist;
        x += vx * speed * delta;
        y += vy * speed * delta;
        sprite.setPosition(x, y);

        return false;
    }
}
