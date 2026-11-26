package game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.actions.Actions;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.ScreenUtils;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.badlogic.gdx.utils.viewport.StretchViewport;

import java.util.ArrayList;
import java.util.List;

public abstract class Mapcontrol implements Screen {

    private final Main game;
    private SpriteBatch batch;
    private Texture background;
    private static Stage stage;
    private BitmapFont font;
    private FitViewport viewport;
    private Texture knightTexture;
    private Texture archerTexture;
    private static Texture CannonTexture;
    private static Texture CannonAttackTexture;
    private static Texture normalZombieTexture;
    private static Texture bossZombieTexture;
    private static Texture zombieHittedTexture;
    private static Texture bossHittedTexture;
    private static Texture bossCritTexture;
    private static Texture zombieCritTexture;
    private static Texture fastZombieTexture;
    private static Texture fastHittedTexture;
    private static Texture fastCritTexture;
    private static Texture mageTexture;
    private static Texture spikeTexture;
    private static Texture arrowTexture;
    private static Texture fireballTexture;
    private static Texture bombTexture;
    private static Texture chestTexture;
    private static Texture chestgoldTexture;

    protected static final List<Enemy> enemies = new ArrayList<>();
    protected static final List<Image> enemyImages = new ArrayList<>();
    protected static final List<Troop> troops = new ArrayList<>();
    protected static final List<Image> troopImages = new ArrayList<>();
    protected static final int MAX_TROOPS = 10;
    protected static final ArrayList<Projectile> projectiles = new ArrayList<>();
    protected List<Waypoint> path;
    protected ArrayList<Wave> waves = new ArrayList<>();

    private static Label goldLabel;
    private Label hpLabel;
    private static int gold = 200;
    private int currentWave = 0;
    private float waveTimer = 0;
    private int spawnedCount = 0;
    private int baseHP = 100;

    public static Texture getChestgoldTexture() {
        return chestgoldTexture;
    }

    public static void setChestgoldTexture(Texture chestgoldTexture) {
        Mapcontrol.chestgoldTexture = chestgoldTexture;
    }

    public static Texture getChestTexture() {
        return chestTexture;
    }

    public static void setChestTexture(Texture chestTexture) {
        Mapcontrol.chestTexture = chestTexture;
    }

    public static Texture getBombTexture() {
        return bombTexture;
    }

    public static void setBombTexture(Texture bombTexture) {
        Mapcontrol.bombTexture = bombTexture;
    }

    public static Texture getFireballTexture() {
        return fireballTexture;
    }

    public static void setFireballTexture(Texture fireballTexture) {
        Mapcontrol.fireballTexture = fireballTexture;
    }

    public static Texture getArrowTexture() {
        return arrowTexture;
    }

    public static void setArrowTexture(Texture arrowTexture) {
        Mapcontrol.arrowTexture = arrowTexture;
    }

    public static Texture getSpikeTexture() {
        return spikeTexture;
    }

    public static void setSpikeTexture(Texture spikeTexture) {
        Mapcontrol.spikeTexture = spikeTexture;
    }

    public static Texture getMageTexture() {
        return mageTexture;
    }

    public static void setMageTexture(Texture mageTexture) {
        Mapcontrol.mageTexture = mageTexture;
    }

    public static Texture getFastCritTexture() {
        return fastCritTexture;
    }

    public static void setFastCritTexture(Texture fastCritTexture) {
        Mapcontrol.fastCritTexture = fastCritTexture;
    }

    public static Texture getFastHittedTexture() {
        return fastHittedTexture;
    }

    public static void setFastHittedTexture(Texture fastHittedTexture) {
        Mapcontrol.fastHittedTexture = fastHittedTexture;
    }

    public static Texture getFastZombieTexture() {
        return fastZombieTexture;
    }

    public static void setFastZombieTexture(Texture fastZombieTexture) {
        Mapcontrol.fastZombieTexture = fastZombieTexture;
    }

    public static Texture getZombieCritTexture() {
        return zombieCritTexture;
    }

    public static void setZombieCritTexture(Texture zombieCritTexture) {
        Mapcontrol.zombieCritTexture = zombieCritTexture;
    }

    public static Texture getBossCritTexture() {
        return bossCritTexture;
    }

    public static void setBossCritTexture(Texture bossCritTexture) {
        Mapcontrol.bossCritTexture = bossCritTexture;
    }

    public static Texture getBossHittedTexture() {
        return bossHittedTexture;
    }

    public static void setBossHittedTexture(Texture bossHittedTexture) {
        Mapcontrol.bossHittedTexture = bossHittedTexture;
    }

    public static Texture getZombieHittedTexture() {
        return zombieHittedTexture;
    }

    public static void setZombieHittedTexture(Texture zombieHittedTexture) {
        Mapcontrol.zombieHittedTexture = zombieHittedTexture;
    }

    public static Texture getBossZombieTexture() {
        return bossZombieTexture;
    }

    public static void setBossZombieTexture(Texture bossZombieTexture) {
        Mapcontrol.bossZombieTexture = bossZombieTexture;
    }

    public static Texture getNormalZombieTexture() {
        return normalZombieTexture;
    }

    public static void setNormalZombieTexture(Texture normalZombieTexture) {
        Mapcontrol.normalZombieTexture = normalZombieTexture;
    }

    public static Texture getCannonAttackTexture() {
        return CannonAttackTexture;
    }

    public static void setCannonAttackTexture(Texture cannonAttackTexture) {
        CannonAttackTexture = cannonAttackTexture;
    }

    public static Texture getCannonTexture() {
        return CannonTexture;
    }

    public static void setCannonTexture(Texture cannonTexture) {
        CannonTexture = cannonTexture;
    }

    public Texture getArcherTexture() {
        return archerTexture;
    }

    public void setArcherTexture(Texture archerTexture) {
        this.archerTexture = archerTexture;
    }

    public Texture getKnightTexture() {
        return knightTexture;
    }

    public void setKnightTexture(Texture knightTexture) {
        this.knightTexture = knightTexture;
    }

    public FitViewport getViewport() {
        return viewport;
    }

    public void setViewport(FitViewport viewport) {
        this.viewport = viewport;
    }

    public BitmapFont getFont() {
        return font;
    }

    public void setFont(BitmapFont font) {
        this.font = font;
    }

    public Main getGame() {
        return game;
    }

    public static Stage getStage() {
        return stage;
    }

    public static void setStage(Stage stage) {
        Mapcontrol.stage = stage;
    }

    public Label getHpLabel() {
        return hpLabel;
    }

    public void setHpLabel(Label hpLabel) {
        this.hpLabel = hpLabel;
    }

    public static Label getGoldLabel() {
        return goldLabel;
    }

    public static void setGoldLabel(Label goldLabel) {
        Mapcontrol.goldLabel = goldLabel;
    }

    public static int getGold() {
        return gold;
    }

    public static void setGold(int gold) {
        Mapcontrol.gold = gold;
    }

    public int getBaseHP() {
        return baseHP;
    }

    public void setBaseHP(int baseHP) {
        this.baseHP = baseHP;
    }

    public Mapcontrol(Main game, Stage stage, BitmapFont font) {
        this.game = game;
        this.stage = stage;
        this.font = font;
    }

    protected abstract String getBackgroundPath();

    protected void updateInternal(float delta) {
    }
    public List<Enemy> getEnemies() {
        return enemies;
    }

    public List<Image> getEnemyImages() {
        return enemyImages;
    }

    protected boolean canPlaceMoreTroops() {
        return troops.size() < MAX_TROOPS;
    }

    @Override
    public void show() {
        resetStaticState();

        if (stage != null) {
            stage.clear();
            stage.dispose();
        }

        viewport = new FitViewport(800, 600);
        stage = new Stage(viewport);
        Gdx.input.setInputProcessor(stage);
        initGoldLabel(stage, font);
        initHPLabel(stage, font);
        initRoundLabel(stage, font);
        initFactionIcons();
        batch = new SpriteBatch();
        font = new BitmapFont();
        shapeRenderer = new ShapeRenderer();
        resetMapData();



        String bg = getBackgroundPath();
        if (bg != null && !bg.isEmpty()) {
            try {
                background = new Texture(bg);
                background.setFilter(Texture.TextureFilter.Nearest, Texture.TextureFilter.Nearest);
            } catch (Exception e) {
                System.out.println("Cannot load background: " + bg + " -> " + e.getMessage());
                background = null;
            }
        }

        knightTexture = safeLoad("knight.png");
        archerTexture = safeLoad("archer.png");
        CannonTexture = safeLoad("cannon.png");
        CannonAttackTexture = safeLoad("cannonAtt.png");
        normalZombieTexture = safeLoad("zombie.png");
        fastZombieTexture = safeLoad("fastZombie.png");
        fastHittedTexture = safeLoad("fastHitted.png");
        fastCritTexture = safeLoad("fastCrit.png");
        bossZombieTexture = safeLoad("bosszombie.png");
        bossHittedTexture=safeLoad("bossHitted.png");
        bossCritTexture=safeLoad("bossCrit.png");
        zombieHittedTexture = safeLoad("zombieHitted.png");
        zombieCritTexture = safeLoad("zombieCrit.png");
        mageTexture = safeLoad("mage.png");
        spikeTexture = safeLoad("spike.png");
        chestTexture = safeLoad("chest.png");
        chestgoldTexture = safeLoad("chestgold.png");



        arrowTexture = safeLoad("arrow.png");
        fireballTexture = safeLoad("fireball.png");
        bombTexture = safeLoad("bom.png");
        showNextWaveButton();
        addBackButton();
        addDragDropSystem();
    }

    protected Texture safeLoad(String path) {
        try {
            Texture t = new Texture(path);
            t.setFilter(Texture.TextureFilter.Nearest, Texture.TextureFilter.Nearest);
            return t;
        } catch (Exception ex) {
            System.out.println("Warning: can't load " + path + " : " + ex.getMessage());
            return null;
        }
    }

    protected abstract void initGoldLabel(Stage stage, BitmapFont font);
    protected abstract void initHPLabel(Stage stage, BitmapFont font);
    protected void addBackButton() {}
    protected void addDragDropSystem() {}
    protected abstract void initWaves();
    protected abstract void initRoundLabel(Stage stage, BitmapFont font);
    protected abstract void showNextWaveButton();
    protected abstract void initFactionIcons();
    protected abstract boolean isOnPath(float px, float py);

    protected Enemy createRandomEnemyForRound(int round, List<Waypoint> enemyPath) {
        if (round <= 2) return new normalZombie(enemyPath, round);

        double bossChance = round < 5
            ? 0.0
            : Math.min(0.25, 0.05 + (round - 5) * 0.008);
        double fastChance = Math.min(0.35, 0.12 + (round - 3) * 0.01);
        double roll = Math.random();

        if (roll < bossChance) return new bossZombie(enemyPath, round);
        if (roll < bossChance + fastChance) return new fastZombie(enemyPath, round);
        return new normalZombie(enemyPath, round);
    }

    protected int getEnemyCountForRound(int round) {
        int baseCount = 6 + round + round / 3;
        int variation = Math.max(1, round / 5);
        int randomOffset = (int)(Math.random() * (variation * 2 + 1)) - variation;
        return Math.max(5, baseCount + randomOffset);
    }

    protected float getSpawnIntervalForRound(int round) {
        return Math.max(0.55f, 1.35f - (round - 1) * 0.035f);
    }

    protected static void showHitEffect(Image img, Texture normal, Texture hit) {
        img.clearActions();
        img.addAction(Actions.sequence(
            Actions.run(() -> img.setDrawable(new TextureRegionDrawable(hit))),
            Actions.delay(0.05f),
            Actions.run(() -> img.setDrawable(new TextureRegionDrawable(normal)))
        ));
    }

    private Texture getTextureForEnemy(Enemy e) {
        if (e instanceof fastZombie) return fastZombieTexture;
        if (e instanceof bossZombie) return bossZombieTexture;
        return normalZombieTexture;
    }
    private Texture getHittedTextureForEnemy(Enemy e) {
        if (e instanceof fastZombie) return fastHittedTexture;
        if (e instanceof bossZombie) return bossHittedTexture;
        return zombieHittedTexture;
    }

    protected void troopAttackEnemies(float delta) {
        for (Troop troop : troops) {
            troop.update(delta);
            if (troop instanceof Chest) continue;

            List<Enemy> inRangeEnemies = new ArrayList<>();
            for (Enemy e : enemies) {
                if (!e.isDead() && troop.isInRange(e)) {
                    inRangeEnemies.add(e);
                }
            }

            if (!inRangeEnemies.isEmpty() && troop.isReadyToAttack()) {

                Enemy nearestEnemy = inRangeEnemies.get(0);
                int idx = enemies.indexOf(nearestEnemy);
                Image enemyImg = (idx != -1) ? enemyImages.get(idx) : null;

                if (enemyImg != null) {
                    // ambil texture normal & hit sesuai tipe enemy
                    Texture normalTex = getTextureForEnemy(nearestEnemy);
                    Texture hitTex = getHittedTextureForEnemy(nearestEnemy);

                    // panggil attack seperti biasa
                    troop.attack(nearestEnemy, enemyImg, normalTex, hitTex);
                }
                troop.resetCooldown();
                // Serangan ke-5 Archer mengenai maksimal tiga target total.
                // Target utama sudah terkena damage dan projectile dari Archer.attack().
                if (troop instanceof Archer) {
                    Archer archer = (Archer) troop;
                    if (archer.isTripleShot()) {
                        int count = 1;
                        for (Enemy e : inRangeEnemies) {
                            if (e == nearestEnemy) continue;
                            if (count >= 3) break;

                            int eidx = enemies.indexOf(e);
                            Image img = (eidx != -1) ? enemyImages.get(eidx) : null;

                            Texture normalTex = getTextureForEnemy(e);
                            Texture hitTex = getHittedTextureForEnemy(e);

                            e.takeDamage(archer.getAttack());
                            if (img != null)
                                Mapcontrol.showHitEffect(img, normalTex, hitTex);

                            Image arrowImg = new Image(arrowTexture);
                            Projectile p = new Projectile(arrowImg, troop.getX(), troop.getY(), e);
                            projectiles.add(p);
                            stage.addActor(arrowImg);

                            count++;
                        }
                    }
                }
            }
        }
        for (int i = enemies.size() - 1; i >= 0; i--) {
            Enemy e = enemies.get(i);

            boolean reachedFinish = e.reachedEnd();
            boolean dead = e.isDead();

            if (reachedFinish) {
                baseHP -= e.getDamage();
                hpLabel.setText(String.valueOf(baseHP));

                if (baseHP <= 0) {
                    game.setScreen(new LoseScreen(game));
                }
            }

            if (dead || reachedFinish) {
                boolean killed = dead && !reachedFinish;
                if (killed) addGold(e.getGoldReward());

                Image img = enemyImages.get(i);
                if (img != null) fadeOutAndRemove(img);

                enemies.remove(i);
                enemyImages.remove(i);
                onEnemyResolved(killed);
            }
        }
    }

    protected void fadeOutAndRemove(Image img) {
        img.addAction(Actions.sequence(
            Actions.fadeOut(0.5f),
            Actions.removeActor()
        ));
    }

    protected void updateEnemies(float delta) {
        for (int i = enemies.size() - 1; i >= 0; i--) {
            Enemy e = enemies.get(i);
            Image img = enemyImages.get(i);

            if (e != null) {
                e.update(delta);

                if (e.isDead()) {
                    if (img != null) img.remove();
                    enemies.remove(i);
                    enemyImages.remove(i);
                    onEnemyResolved(true);
                    continue;
                }
            }

            if (img != null && e != null) {
                img.setPosition(e.getX(), e.getY());
            }
        }
    }
    protected void onEnemyResolved(boolean killed) {
    }
    // ================== WAVE SPAWN ==================
    protected void spawnEnemyFromWave() {

        if (currentWave >= waves.size()) return;

        Wave wave = waves.get(currentWave);

        spawnEnemyByType(wave.getType());
        spawnedCount++;

        waveTimer = wave.getDelay();

        if (spawnedCount >= wave.getAmount()) {
            spawnedCount = 0;
            currentWave++;
        }
    }

    protected void spawnEnemyByType(String type) {
        Enemy e;
        Texture tex;

        switch (type) {
            case "speed":
                e = new fastZombie(path);
                tex = fastZombieTexture;
                break;
            case "boss":
                e = new bossZombie(path);
                tex = bossZombieTexture;
                break;
            default:
                e = new normalZombie(path);
                tex = normalZombieTexture;
        }

        e.setPosition(path.get(0).getX(), path.get(0).getY());

        Image img = new Image(tex);
        img.setPosition(e.getX(), e.getY());

        enemies.add(e);
        enemyImages.add(img);
        stage.addActor(img);
    }

    protected void updateProjectiles(float delta) {
        for (int i = projectiles.size() - 1; i >= 0; i--) {
            Projectile p = projectiles.get(i);

            if (p.update(delta)) {
                p.getImage().remove();
                projectiles.remove(i);
            }
        }
    }

    protected void addGold(int amount) {
        gold += amount;
        if (goldLabel != null) {
            goldLabel.setText(String.valueOf(gold));
        }
    }

    protected ShapeRenderer shapeRenderer;
    private boolean dragRangePreviewActive = false;
    private float dragRangePreview = 0f;
    private String draggedTroopType = "";

    protected void beginDragRangePreview(String troopType) {
        draggedTroopType = troopType;
        switch (troopType) {
            case "knight":
                dragRangePreview = 100f;
                break;
            case "archer":
                dragRangePreview = 150f;
                break;
            case "cannon":
                dragRangePreview = 120f;
                break;
            case "mage":
                dragRangePreview = 125f;
                break;
            case "spike":
                dragRangePreview = 50f;
                break;
            case "chest":
                // Chest tidak punya attack range; lingkaran ini menunjukkan footprint placement.
                dragRangePreview = 32f;
                break;
            default:
                dragRangePreview = 0f;
        }
        dragRangePreviewActive = dragRangePreview > 0f;
    }

    protected void drawRangeCircles() {
        Gdx.gl.glEnable(GL20.GL_BLEND);
        Gdx.gl.glBlendFunc(GL20.GL_SRC_ALPHA, GL20.GL_ONE_MINUS_SRC_ALPHA);

        shapeRenderer.setProjectionMatrix(stage.getCamera().combined);
        shapeRenderer.begin(ShapeRenderer.ShapeType.Line);

        for (Troop t : troops) {
            if (t.getRange() <= 0f) continue;
            shapeRenderer.setColor(1, 1, 1, 0.4f);
            shapeRenderer.circle(
                t.getX() + t.getWidth() / 2f,
                t.getY() + t.getHeight() / 2f,
                t.getRange()
            );
        }

        if (dragRangePreviewActive) {
            if (!Gdx.input.isTouched()) {
                dragRangePreviewActive = false;
            } else {
                Vector2 pointer = stage.screenToStageCoordinates(
                    new Vector2(Gdx.input.getX(), Gdx.input.getY())
                );
                boolean insidePlacementArea = pointer.x >= 0f
                    && pointer.x <= viewport.getWorldWidth() - 200f
                    && pointer.y >= 0f
                    && pointer.y <= viewport.getWorldHeight();
                boolean onPath = insidePlacementArea && isOnPath(pointer.x, pointer.y);
                boolean isSpike = "spike".equals(draggedTroopType);
                boolean validPlacement = insidePlacementArea
                    && canPlaceMoreTroops()
                    && (isSpike ? onPath : !onPath);

                if (validPlacement) {
                    shapeRenderer.setColor(0.15f, 0.65f, 1f, 0.9f);
                } else {
                    shapeRenderer.setColor(1f, 0.15f, 0.15f, 0.9f);
                }
                shapeRenderer.circle(pointer.x, pointer.y, dragRangePreview);
            }
        }

        shapeRenderer.end();

        Gdx.gl.glDisable(GL20.GL_BLEND);
    }

    protected void drawEnemyHealthBars() {
        if (enemies.isEmpty()) return;

        Gdx.gl.glEnable(GL20.GL_BLEND);
        Gdx.gl.glBlendFunc(GL20.GL_SRC_ALPHA, GL20.GL_ONE_MINUS_SRC_ALPHA);

        shapeRenderer.setProjectionMatrix(stage.getCamera().combined);
        shapeRenderer.begin(ShapeRenderer.ShapeType.Filled);

        int count = Math.min(enemies.size(), enemyImages.size());
        for (int i = 0; i < count; i++) {
            Enemy enemy = enemies.get(i);
            Image image = enemyImages.get(i);
            if (enemy == null || image == null || enemy.isDead()) continue;

            float ratio = enemy.getHealthRatio();
            float spriteWidth = image.getWidth() > 0f ? image.getWidth() : 64f;
            float spriteHeight = image.getHeight() > 0f ? image.getHeight() : 64f;
            float barWidth = Math.max(30f, spriteWidth - 8f);
            float barHeight = 5f;
            float x = enemy.getX() + (spriteWidth - barWidth) / 2f;
            float y = enemy.getY() + spriteHeight + 4f;

            shapeRenderer.setColor(0.08f, 0.08f, 0.08f, 0.9f);
            shapeRenderer.rect(x - 1f, y - 1f, barWidth + 2f, barHeight + 2f);

            float red = ratio < 0.5f ? 1f : 2f * (1f - ratio);
            float green = ratio < 0.5f ? 2f * ratio : 1f;
            shapeRenderer.setColor(red, green, 0.05f, 1f);
            shapeRenderer.rect(x, y, barWidth * ratio, barHeight);
        }

        shapeRenderer.end();
        Gdx.gl.glDisable(GL20.GL_BLEND);
    }

    protected void resetMapData() {
        troops.clear();
        troopImages.clear();
        enemies.clear();
        enemyImages.clear();
        projectiles.clear();
    }

    protected static void resetStaticState() {
        enemies.clear();
        enemyImages.clear();
        troops.clear();
        troopImages.clear();
        projectiles.clear();
        gold = 200;

        if (goldLabel != null) goldLabel.remove();
    }

    protected boolean isGameWon() {
        return currentWave >= 10
            && enemies.isEmpty()
            && spawnedCount == 0;
    }

    @Override
    public void render(float delta) {
        ScreenUtils.clear(0, 0, 0, 1);
        batch.setProjectionMatrix(stage.getCamera().combined);
        batch.begin();
        if (background != null) {
            batch.draw(background, 0, 0, 800, 600);
        }
        batch.end();

        updateInternal(delta);
        updateEnemies(delta);
        troopAttackEnemies(delta);
        updateProjectiles(delta);
        ProjectileManager.update(delta);

        if (baseHP <= 0) return;

        if (isGameWon()) {
            game.setScreen(new WinScreen(game));
            return;
        }
        stage.act(delta);
        stage.draw();
        drawRangeCircles();
        drawEnemyHealthBars();
    }

    @Override
    public void resize(int width, int height) {
        viewport.update(width, height, true);
    }

    @Override public void pause() {}
    @Override public void resume() {}
    @Override public void hide() {}

    @Override
    public void dispose() {
        if (batch != null) batch.dispose();
        if (background != null) background.dispose();
        if (stage != null) stage.dispose();
        if (font != null) font.dispose();
        if (shapeRenderer != null) shapeRenderer.dispose();

        if (knightTexture != null) knightTexture.dispose();
        if (archerTexture != null) archerTexture.dispose();
        if (normalZombieTexture != null) normalZombieTexture.dispose();
        if (zombieHittedTexture != null) zombieHittedTexture.dispose();
        if (arrowTexture != null) arrowTexture.dispose();
    }
}
