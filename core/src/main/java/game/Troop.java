package game;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.scenes.scene2d.ui.Image;

public abstract class Troop {

    private String name;
    private int attack;
    private int baseAttack;
    private double attackSpeed;     // serangan per detik
    private double attackCooldown;  // waktu tersisa sebelum bisa menyerang lagi
    private double range;
    private float x, y;
    private FactName faction;
    protected int attackCount = 0;
    protected static final int SPECIAL_THRESHOLD = 3;
    private boolean buffApplied = false;
    private boolean selected = false;
    protected Image myImage;
    private int cost;


    public void setRange(double range) {
        this.range = range;
    }

    public void setAttack(int attack) {
        this.attack = attack;
    }

    public String getName() {
        return name;
    }

    public int getBaseAttack() {
        return baseAttack;
    }

    public double getAttackSpeed() {
        return attackSpeed;
    }

    public void setAttackSpeed(double attackSpeed) {
        this.attackSpeed = attackSpeed;
    }

    public double getAttackCooldown() {
        return 1f / getAttackSpeed();
    }

    public void setAttackCooldown(float cooldown) {
        this.attackCooldown = cooldown;
    }

    public int getAttackCount() {
        return attackCount;
    }

    public boolean isBuffApplied() {
        return buffApplied;
    }



    // ================================
    // CONSTRUCTOR
    // ================================
    public Troop(String name, int attack, double attackSpeed, FactName faction, double range, int cost) {
        this.name = name;
        this.attack = attack;
        this.baseAttack = attack;
        this.attackSpeed = attackSpeed;
        this.faction = faction;
        this.range = range;
        this.attackCooldown = 0;
        this.cost=cost;
    }
    public boolean isSelected() {
        return selected;
    }

    public void setSelected(boolean selected) {
        this.selected = selected;
    }

    public int getAttack() {
        return attack;
    }

    // ================================
    // GAME LOOP
    // ================================
    public void update(float delta) {
        attackCooldown -= delta;
        if (attackCooldown < 0) attackCooldown = 0;

    }

    protected void setMyImage(Image img) {
        this.myImage = img;
    }

    protected Image getMyImage() {
        return myImage;
    }

    protected float getWidth() {
        return (myImage != null) ? myImage.getWidth() : 0;
    }

    protected float getHeight() {
        return (myImage != null) ? myImage.getHeight() : 0;
    }

    protected boolean isReadyToAttack() {
        return attackCooldown <= 0;
    }

    public void attack(Enemy target, Image targetImg, Texture normal, Texture hit) {
        System.out.println(name + " attackCount=" + attackCount);
        if (attackCooldown > 0) return;
        target.takeDamage(attack);

        if (targetImg != null)
            Mapcontrol.showHitEffect(targetImg, normal, hit);
        attackCount++;
        if (attackCount >= SPECIAL_THRESHOLD) {
            specialAttack();
            attackCount = 0;
        }
        attackCooldown = 1.0 / attackSpeed;

    }

    public abstract void specialAttack();

    public boolean isInRange(Enemy e) {
        double dx = e.getX() - x;
        double dy = e.getY() - y;
        return Math.hypot(dx, dy) <= range;
    }

    public boolean canBePlacedOnPath() {
        return false;
    }

    public boolean mustBePlacedOnPath() {
        return false;
    }

    public void setPosition(float x, float y) {
        this.x = x;
        this.y = y;
    }
    public void resetCooldown() {
        attackCooldown = 1.0 / attackSpeed;
    }


    public float getX() { return x; }
    public float getY() { return y; }
    public float getRange() { return (float) range; }
    public FactName getFaction() { return faction; }

    // ================================
    // BUFF SYSTEM
    // ================================
    public void applyFactionBuff() {
        applyAttackMultiplierOnce(1.20f);
    }

    public void applyAttackMultiplierOnce(float multiplier) {
        if (buffApplied || multiplier <= 1f) return;
        this.attack = Math.round(baseAttack * multiplier);
        buffApplied = true;
    }

    public void applyAttackBonusOnce(int bonus) {
        if (buffApplied || bonus <= 0 || baseAttack <= 0) return;
        this.attack = baseAttack + bonus;
        buffApplied = true;
    }

    public void applyRangeBonusOnce(float bonus) {
        if (buffApplied || bonus <= 0f) return;
        this.range += bonus;
        buffApplied = true;
    }

    public boolean hasBuff() { return buffApplied; }

    public int getCost() {
        return cost;
    }
}
