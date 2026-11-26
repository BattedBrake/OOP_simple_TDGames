package game;

import com.badlogic.gdx.graphics.Texture;

import java.util.List;

public class Enemy {
    private Texture normalTexture;
    private Texture hitTexture;
    private float x, y;
    private final int maxHp;
    private int hp;
    private float speed;
    private final int goldReward;
    private float normalSpeed;
    private float slowMultiplier = 1f;
    private float slowTimer = 0f;
    private float stunTimer = 0f;
    private boolean dead = false;
    private int damage;


    private final List<Waypoint> path;
    private int targetIndex = 0;

    public Enemy(int hp, float speed, int goldReward, List<Waypoint> path,int damage) {
        this.maxHp = hp;
        this.hp = hp;
        this.speed = speed;
        this.goldReward = goldReward;
        this.path = path;
        this.normalSpeed = speed;
        this.damage=damage;

        // defensive: if path available, set position to first waypoint
        if (path != null && !path.isEmpty()) {
            this.x = path.get(0).getX();
            this.y = path.get(0).getY();
            this.targetIndex = 1;
        } else {
            this.x = 0;
            this.y = 0;
            this.targetIndex = 0;
        }
    }

    public static int scaledHp(int baseHp, int round) {
        int level = Math.max(0, round - 1);
        double multiplier = 1.0 + 0.055 * level + 0.0018 * level * level;
        return Math.max(1, (int)Math.round(baseHp * multiplier));
    }

    public static int scaledDamage(int baseDamage, int round) {
        int tier = Math.max(0, round - 1) / 5;
        return Math.max(1, (int)Math.round(baseDamage * (1.0 + tier * 0.20)));
    }

    public static int scaledReward(int baseReward, int round) {
        int level = Math.max(0, round - 1);
        return Math.max(1, (int)Math.round(baseReward * (1.0 + level * 0.025)));
    }


    public void setPosition(float x, float y) {
        this.x = x;
        this.y = y;
    }

    public void update(float delta) {
        if (path == null || targetIndex >= path.size()) return;

        if (slowTimer > 0f) {
            slowTimer -= delta;
            if (slowTimer <= 0f) {
                slowMultiplier = 1f;
            }
        }

        if (stunTimer > 0f) {
            stunTimer -= delta;
            if (stunTimer > 0f) return;
        }

        Waypoint target = path.get(targetIndex);
        float dx = target.getX() - x;
        float dy = target.getY() - y;
        float dist = (float) Math.sqrt(dx * dx + dy * dy);

        if (dist <= 1f) {
            targetIndex++;
            return;
        }

        if (dist > 0f) {
            float currentSpeed = speed * slowMultiplier;
            x += (dx / dist) * currentSpeed * delta;
            y += (dy / dist) * currentSpeed * delta;
        }
    }

    public void applySlow(float multiplier, float duration) {
        if (multiplier <= 0f || multiplier >= 1f || duration <= 0f) return;
        slowMultiplier = Math.min(slowMultiplier, multiplier);
        slowTimer = Math.max(slowTimer, duration);
    }

    public void applyStun(float duration) {
        if (duration <= 0f) return;
        stunTimer = Math.max(stunTimer, duration);
    }

    public void applyKnockback(float distance) {
        if (distance <= 0f || path == null || path.isEmpty() || targetIndex <= 0) return;

        float remaining = distance;
        int previousIndex = Math.min(targetIndex - 1, path.size() - 1);

        while (remaining > 0f && previousIndex >= 0) {
            Waypoint previous = path.get(previousIndex);
            float dx = previous.getX() - x;
            float dy = previous.getY() - y;
            float dist = (float)Math.sqrt(dx * dx + dy * dy);

            if (dist > 0f) {
                float step = Math.min(remaining, dist);
                x += (dx / dist) * step;
                y += (dy / dist) * step;
                remaining -= step;

                if (step < dist) return;
            }

            if (previousIndex == 0) return;
            targetIndex = previousIndex;
            previousIndex--;
        }
    }

    public float getSpeed() { return speed; }


    public void takeDamage(int dmg) {
        hp -= dmg;
        dead = true;
    }

    public boolean isDead() {
        return hp <= 0;
    }
    public boolean reachedEnd() {
        return targetIndex >= path.size();
    }

    public float getX() { return x; }
    public float getY() { return y; }
    public int getHp() { return hp; }
    public int getMaxHp() { return maxHp; }
    public float getHealthRatio() {
        if (maxHp <= 0) return 0f;
        return Math.max(0f, Math.min(1f, hp / (float)maxHp));
    }
    public int getGoldReward() { return goldReward; }
    public Texture getNormalTexture() { return normalTexture; }
    public Texture getHitTexture() { return hitTexture; }
    public int getDamage() {
        return damage;
    }
}
