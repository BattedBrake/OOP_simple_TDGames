package game;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import static game.Mapcontrol.*;

public class AllTroops {
}


class Knight extends Troop {

    public Knight() {
        super("Knight", 12, 1, FactName.KINGDOM, 100,50);
    }

    @Override
    public void attack(Enemy target, Image targetImg, Texture normal, Texture hit) {
        if (!isReadyToAttack()) return;

        attackCount++;
        System.out.println("Knight hit count = " + attackCount);

        if (attackCount >= SPECIAL_THRESHOLD) {
            specialAttack(target, targetImg);   // ← polymorphism digunakan
            attackCount = 0;
            setAttackCooldown((float) (1f / getAttackSpeed()));
            return; // jangan serang normal
        }

        // Hit normal
        target.takeDamage(getAttack());
        if (targetImg != null)
            Mapcontrol.showHitEffect(targetImg, normal, hit);

        setAttackCooldown((float) (1f / getAttackSpeed()));
    }

    public void specialAttack(Enemy target, Image targetImg) {
        System.out.println("SPECIAL ATTACK TRIGGERED!");

        if (target == null || targetImg == null) {
            System.out.println("CRIT FAILED (null target)");
            return;
        }

        int critDamage = getAttack() * 2;
        target.takeDamage(critDamage);

        Texture normalTex;
        Texture critTex;

        if (target instanceof fastZombie) {
            normalTex = Mapcontrol.getFastZombieTexture();
            critTex = Mapcontrol.getFastCritTexture();
        } else if (target instanceof bossZombie) {
            normalTex = Mapcontrol.getBossZombieTexture();
            critTex = Mapcontrol.getBossCritTexture();
        } else {
            normalTex = Mapcontrol.getNormalZombieTexture();
            critTex = Mapcontrol.getZombieCritTexture();
        }

        Mapcontrol.showHitEffect(targetImg, normalTex, critTex);

        System.out.println("Knight CRIT! Damage = " + critDamage);
    }

    @Override
    public void specialAttack() {
    }
}

    class Archer extends Troop {
    private int attackCounter = 0;
    private int SPECIAL_THRESHOLD = 5;

    public Archer() {
        super("Archer", 8, 1.5, FactName.KINGDOM, 150,60);
    }

    @Override
    public void attack(Enemy target, Image targetImg, Texture normal, Texture hit) {
        target.takeDamage(getAttack());
        if (targetImg != null) {
            Mapcontrol.showHitEffect(targetImg, normal, hit);
        }

        Image arrowImg = new Image(Mapcontrol.getArrowTexture());
        Projectile p = new Projectile(arrowImg, getX(), getY(), target);
        Mapcontrol.projectiles.add(p);
        Mapcontrol.getStage().addActor(arrowImg);
        setAttackCooldown((float) (1f / getAttackSpeed()));
        attackCounter++;
    }

    public boolean isTripleShot() {
        return attackCounter > 0 && attackCounter % SPECIAL_THRESHOLD == 0;
    }

    @Override
    public void specialAttack() { }
}
class Cannon extends Troop implements AOEdamage {
    private static final int SIEGE_SHOT_INTERVAL = 5;
    private static final float SIEGE_AOE_RADIUS = 125f;
    private static final float NORMAL_STUN_DURATION = 1f;
    private static final float BOSS_STUN_DURATION = 0.5f;
    private int attackCounter = 0;

    public Cannon() {
        super("Cannon", 25, 0.8, FactName.KINGDOM, 120,75);
    }
    @Override
    public float getAoeRadius() {
        return 70f;
    }
    @Override
    public float getAoeMultiplier() {
        return 0.6f;
    }
    @Override
    public void attack(Enemy target, Image targetImg, Texture normal, Texture hit) {
        if (!isReadyToAttack()) return;

        attackCounter++;
        boolean siegeShot = attackCounter % SIEGE_SHOT_INTERVAL == 0;
        float impactX = target.getX();
        float impactY = target.getY();
        float aoeRadius = siegeShot ? SIEGE_AOE_RADIUS : getAoeRadius();
        int mainDamage = siegeShot ? Math.round(getAttack() * 1.4f) : getAttack();
        int splashDamage = Math.round(getAttack() * (siegeShot ? 0.72f : getAoeMultiplier()));

        target.takeDamage(mainDamage);
        if (siegeShot) {
            target.applyStun(target instanceof bossZombie ? BOSS_STUN_DURATION : NORMAL_STUN_DURATION);
        }

        for (Enemy e : Mapcontrol.enemies) {
            if (e == null || e == target || e.isDead()) continue;

            float dx = e.getX() - impactX;
            float dy = e.getY() - impactY;

            if (Math.hypot(dx, dy) <= aoeRadius) {
                e.takeDamage(splashDamage);
                if (siegeShot) {
                    e.applyStun(e instanceof bossZombie ? BOSS_STUN_DURATION : NORMAL_STUN_DURATION);
                }
            }
        }
        if (targetImg != null) {
            Texture impactTexture = hit;
            if (siegeShot) {
                if (target instanceof fastZombie) impactTexture = Mapcontrol.getFastCritTexture();
                else if (target instanceof bossZombie) impactTexture = Mapcontrol.getBossCritTexture();
                else impactTexture = Mapcontrol.getZombieCritTexture();
            }
            Mapcontrol.showHitEffect(targetImg, normal, impactTexture);
        }

        Image myImg = getMyImage();
        if (myImg != null)
            Mapcontrol.showHitEffect(myImg,
                Mapcontrol.getCannonTexture(),
                Mapcontrol.getCannonAttackTexture());

        Image bomImg = new Image(Mapcontrol.getBombTexture() );
        Projectile p = new Projectile(bomImg, getX(), getY(), target);
        if (siegeShot) {
            bomImg.setSize(90f, 90f);
            System.out.println(">>> CANNON SIEGE BOMB: AOE + STUN <<<");
        }
        Mapcontrol.projectiles.add(p);
        Mapcontrol.getStage().addActor(bomImg);
        setAttackCooldown((float) (1f / getAttackSpeed()));

    }

    protected Image getMyImage() {
        try {
            int idx = Mapcontrol.troops.indexOf(this);
            return Mapcontrol.troopImages.get(idx);
        } catch (Exception e) {
            return null;
        }
    }

    @Override
    public void specialAttack() {
        System.out.println(">>> CANNON SPECIAL TRIGGERED <<<");
        Image myImg = getMyImage();
        if (myImg != null) {
            Mapcontrol.showHitEffect(
                myImg,
                Mapcontrol.getCannonTexture(),
                Mapcontrol.getCannonAttackTexture()
            );
        }
    }
}

class Mage extends Troop implements AOEdamage {
    private static final double RAPID_FIRE_MULTIPLIER = 8.0;
    private static final float RAPID_FIRE_DURATION = 1.25f;
    private boolean specialActive = false;
    private float specialTimer = 0f;
    private double originalAttackSpeed;

    public Mage() {
        super("Mage", 3, 1.6, FactName.FANTASY, 125,70);
    }

    @Override
    public float getAoeRadius() {
        return 70f;
    }

    @Override
    public float getAoeMultiplier() {
        return 0.6f;
    }

    @Override
    public void update(float delta) {
        super.update(delta);
        if (!specialActive) return;

        specialTimer -= delta;
        if (specialTimer <= 0f) {
            setAttackSpeed(originalAttackSpeed);
            specialActive = false;
        }
    }

    @Override
    public void attack(Enemy target, Image targetImg, Texture normal, Texture hit) {
        if (!isReadyToAttack()) return;

        target.takeDamage(getAttack());

        for (Enemy e : Mapcontrol.enemies) {
            if (e == null || e == target || e.isDead()) continue;

            float dx = e.getX() - target.getX();
            float dy = e.getY() - target.getY();
            if (Math.hypot(dx, dy) <= getAoeRadius()) {
                e.takeDamage((int) (getAttack() * getAoeMultiplier()));
            }
        }

        if (targetImg != null)
            Mapcontrol.showHitEffect(targetImg, normal, hit);

        Image fireImg = new Image(Mapcontrol.getFireballTexture());
        Projectile p = new Projectile(fireImg, getX(), getY(), target);
        Mapcontrol.projectiles.add(p);
        Mapcontrol.getStage().addActor(fireImg);

        if (!specialActive) {
            attackCount++;
            if (attackCount >= SPECIAL_THRESHOLD) {
                originalAttackSpeed = getAttackSpeed();
                setAttackSpeed(originalAttackSpeed * RAPID_FIRE_MULTIPLIER);
                specialActive = true;
                specialTimer = RAPID_FIRE_DURATION;
                attackCount = 0;
            }
        }

        setAttackCooldown((float) (1f / getAttackSpeed()));
    }

    @Override
    public void specialAttack() {
    }
}

    class Spike extends Troop {
        public Spike() {
            super("Spike", 5, 3.5, FactName.FANTASY, 50,40);
        }

        @Override
        public void attack(Enemy target, Image targetImg, Texture normal, Texture hit) {
            if (!isReadyToAttack()) return;
            target.takeDamage(getAttack());

            for (Enemy e : Mapcontrol.enemies) {
                if (e == null || e == target || e.isDead()) continue;

                float dx = e.getX() - target.getX();
                float dy = e.getY() - target.getY();

                if (Math.hypot(dx, dy) <= 60) { // radius kecil
                    e.takeDamage(getAttack() / 2);   // splash damage
                }
            }

            if (targetImg != null)
                Mapcontrol.showHitEffect(targetImg, normal, hit);

            setAttackCooldown((float) (1f / getAttackSpeed()));
        }

        @Override
        public boolean canBePlacedOnPath() {
            return true;
        }

        @Override
        public boolean mustBePlacedOnPath() {
            return true;
        }

        @Override
        public void specialAttack() {

        }
    }

class Chest extends Troop {
    private int goldPerRound = 12;
    private Image myImage; // referensi ke Image yang ada di stage

    public Chest() {
        super("Chest", 0, 0, FactName.FANTASY, 0, 45);
    }

    public void setMyImage(Image img) {
        this.myImage = img;
    }

    public Image getMyImage() {
        return myImage;
    }

    public int getGoldPerRound() {
        return goldPerRound;
    }

    public Texture getGoldEffectTexture() {
        return getChestgoldTexture();
    }

    @Override
    public void specialAttack() { }
}
