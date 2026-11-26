package game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.Touchable;
import com.badlogic.gdx.scenes.scene2d.actions.Actions;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.scenes.scene2d.utils.DragAndDrop;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;
import com.badlogic.gdx.utils.viewport.FitViewport;

import java.util.ArrayList;
import java.util.List;

public class Map2 extends Mapcontrol {
    private boolean waveActive = false;
    private int zombiesSpawned = 0;
    private int zombiesResolved = 0;
    private int zombiesPerWave = 0;
    private Label roundLabel;
    private final List<Waypoint> path = new ArrayList<>();
    private float spawnTimer = 0f;
    private float spawnInterval = 1.35f;
    private int round = 0;
    private TextButton nextWaveButton;
    private boolean waveRunning = false;
    private Image kingdomBuffIcon;
    private Image fantasyBuffIcon;
    private Texture kingdomIconTexture;
    private Texture fantasyIconTexture;;
    private Label kingdomStatusLabel;
    private Label fantasyStatusLabel;
    private Label troopLimitLabel;
    private final FactionManager factionManager = new FactionManager();

    public Map2(Main game) {
        super(game, new Stage(new FitViewport(800, 600)), new BitmapFont());
        ensurePath();
        initWaves();
        initFactionIcons();
    }

    @Override
    protected String getBackgroundPath() {
        return "map2.jpeg";
    }

    @Override
    protected void updateInternal(float delta) {
        if (waveRunning) {
            spawnEnemyForMap();
        }
    }

    protected void addDragDropSystem() {
        DragAndDrop dragAndDrop = new DragAndDrop();
        dragAndDrop.setTapSquareSize(1);
        dragAndDrop.setDragTime(0);
        float rightUI = 200;

        Image dropArea = new Image();
        dropArea.setBounds(0, 0, getViewport().getWorldWidth() - rightUI, getViewport().getWorldHeight());
        dropArea.setTouchable(Touchable.enabled);
        dropArea.setColor(1, 1, 1, 0);
        getStage().addActor(dropArea);

        float iconBaseX = getViewport().getWorldWidth() - rightUI + 20;
        float iconBaseY = getViewport().getWorldHeight() - 100;

        if (getKnightTexture() != null) {
            Image knightIcon = new Image(getKnightTexture());
            knightIcon.setSize(48, 48);
            knightIcon.setPosition(iconBaseX + 85, iconBaseY + 40);
            getStage().addActor(knightIcon);

            dragAndDrop.addSource(new DragAndDrop.Source(knightIcon) {
                @Override
                public DragAndDrop.Payload dragStart(InputEvent event, float x, float y, int pointer) {
                    DragAndDrop.Payload payload = new DragAndDrop.Payload();
                    payload.setObject("knight");
                    beginDragRangePreview("knight");
                    Image dragImg = new Image(getKnightTexture());
                    dragImg.setSize(64, 64);
                    payload.setDragActor(dragImg);
                    dragAndDrop.setDragActorPosition(32, -32);
                    return payload;
                }
            });
        }

        if (getArcherTexture() != null) {
            Image archerIcon = new Image(getArcherTexture());
            archerIcon.setSize(56, 56);
            archerIcon.setPosition(iconBaseX + 124, iconBaseY + 38);
            getStage().addActor(archerIcon);

            dragAndDrop.addSource(new DragAndDrop.Source(archerIcon) {
                @Override
                public DragAndDrop.Payload dragStart(InputEvent event, float x, float y, int pointer) {
                    DragAndDrop.Payload payload = new DragAndDrop.Payload();
                    payload.setObject("archer");
                    beginDragRangePreview("archer");
                    Image dragImg = new Image(getArcherTexture());
                    dragImg.setSize(64, 64);
                    payload.setDragActor(dragImg);
                    dragAndDrop.setDragActorPosition(32, -32);
                    return payload;
                }
            });
        }


        if (getCannonTexture() != null) {
            Image cannonIcon = new Image(getCannonTexture());
            cannonIcon.setSize(48, 48);
            cannonIcon.setPosition(iconBaseX + 80, iconBaseY - 28);
            getStage().addActor(cannonIcon);

            dragAndDrop.addSource(new DragAndDrop.Source(cannonIcon) {
                @Override
                public DragAndDrop.Payload dragStart(InputEvent event, float x, float y, int pointer) {
                    DragAndDrop.Payload payload = new DragAndDrop.Payload();
                    payload.setObject("cannon");
                    beginDragRangePreview("cannon");
                    Image dragImg = new Image(getCannonTexture());
                    dragImg.setSize(64, 64);
                    payload.setDragActor(dragImg);
                    dragAndDrop.setDragActorPosition(32, -32);
                    return payload;
                }
            });
        }

        if (getMageTexture() != null) {
            Image mageIcon = new Image(getMageTexture());
            mageIcon.setSize(48, 48);
            mageIcon.setPosition(iconBaseX + 80, iconBaseY - 94);
            getStage().addActor(mageIcon);

            dragAndDrop.addSource(new DragAndDrop.Source(mageIcon) {
                @Override
                public DragAndDrop.Payload dragStart(InputEvent event, float x, float y, int pointer) {
                    DragAndDrop.Payload payload = new DragAndDrop.Payload();
                    payload.setObject("mage");
                    beginDragRangePreview("mage");
                    Image dragImg = new Image(getMageTexture());
                    dragImg.setSize(64, 64);
                    payload.setDragActor(dragImg);
                    dragAndDrop.setDragActorPosition(32, -32);
                    return payload;
                }
            });
        }

        if ( getSpikeTexture() != null) {
            Image spikeIcon = new Image( getSpikeTexture());
            spikeIcon.setSize(46, 46);
            spikeIcon.setPosition(iconBaseX + 128, iconBaseY - 32);
            getStage().addActor(spikeIcon);

            dragAndDrop.addSource(new DragAndDrop.Source(spikeIcon) {
                @Override
                public DragAndDrop.Payload dragStart(InputEvent event, float x, float y, int pointer) {
                    DragAndDrop.Payload payload = new DragAndDrop.Payload();
                    payload.setObject("spike");
                    beginDragRangePreview("spike");
                    Image dragImg = new Image( getSpikeTexture());
                    dragImg.setSize(64, 64);
                    payload.setDragActor(dragImg);
                    dragAndDrop.setDragActorPosition(32, -32);
                    return payload;
                }
            });
        }

        if (getChestTexture() != null) {
            Image chestIcon = new Image(getChestTexture());
            chestIcon.setSize(48, 48);
            chestIcon.setPosition(iconBaseX + 126, iconBaseY - 95);
            getStage().addActor(chestIcon);

            dragAndDrop.addSource(new DragAndDrop.Source(chestIcon) {
                @Override
                public DragAndDrop.Payload dragStart(InputEvent event, float x, float y, int pointer) {
                    DragAndDrop.Payload payload = new DragAndDrop.Payload();
                    payload.setObject("chest");
                    beginDragRangePreview("chest");
                    Image dragImg = new Image(getChestTexture());
                    dragImg.setSize(64, 64);
                    payload.setDragActor(dragImg);
                    dragAndDrop.setDragActorPosition(32, -32);
                    return payload;
                }
            });
        }


        dragAndDrop.addTarget(new DragAndDrop.Target(dropArea) {
            @Override
            public boolean drag(DragAndDrop.Source source, DragAndDrop.Payload payload, float x, float y, int pointer) {
                return true;
            }

            @Override
            public void drop(DragAndDrop.Source source, DragAndDrop.Payload payload, float x, float y, int pointer) {
                String type = String.valueOf(payload.getObject());
                Image placedImg = null;
                Troop troop = null;

                if ("knight".equals(type) && getKnightTexture() != null) {
                    placedImg = new Image(getKnightTexture());
                    troop = new Knight() {
                        @Override
                        public void specialAttack() {
                        }
                    };
                }

                if ("archer".equals(type) && getArcherTexture() != null) {
                    placedImg = new Image(getArcherTexture());
                    troop = new Archer() {
                        @Override
                        public void specialAttack() {
                        }
                    };
                }

                if ("cannon".equals(type) && getCannonTexture() != null) {
                    placedImg = new Image(getCannonTexture());
                    troop = new Cannon() {
                        @Override
                        public void specialAttack() {
                        }
                    };
                }
                if ("mage".equals(type) && getMageTexture() != null) {
                    placedImg = new Image(getMageTexture());
                    troop = new Mage() {
                        @Override
                        public void specialAttack() {
                        }
                    };
                }

                if ("spike".equals(type) &&  getSpikeTexture() != null) {
                    placedImg = new Image( getSpikeTexture());
                    troop = new Spike() {
                        @Override
                        public void specialAttack() {
                        }
                    };
                }

                if ("chest".equals(type) && getChestTexture() != null) {
                    placedImg = new Image(getChestTexture());
                    troop = new Chest() {
                        @Override
                        public void specialAttack() {
                        }
                    };
                }

                if (troop != null && placedImg != null) {

                    if (!canPlaceMoreTroops()) {
                        System.out.println("Batas maksimal " + MAX_TROOPS + " troop sudah tercapai!");
                        return;
                    }

                    placedImg.setSize(64, 64);
                    float px = dropArea.getX() + x - 32;
                    float py = dropArea.getY() + y - 32;
                    float placementCenterX = px + placedImg.getWidth() / 2f;
                    float placementCenterY = py + placedImg.getHeight() / 2f;

                    ensurePath();

                    if (isOnPath(placementCenterX, placementCenterY) && !troop.canBePlacedOnPath()) {
                        System.out.println("❌ Troop tidak boleh ditaruh di path");
                        return;
                    }

                    if (troop.mustBePlacedOnPath() && !isOnPath(placementCenterX, placementCenterY)) {
                        System.out.println("❌ Spike harus ditaruh di path");
                        return;
                    }

                    if (getGold() < troop.getCost()) {
                        System.out.println("Gold tidak cukup!");
                        return; // batal deploy
                    }

                    placedImg.setPosition(px, py);
                    getStage().addActor(placedImg);
                    troop.setPosition(px, py);
                    troop.setMyImage(placedImg);
                    troops.add(troop);
                    troopImages.add(placedImg);
                    applyFactionBuffs();
                    updateFactionBuffIcons();


                    if (troop instanceof Chest) {
                        ((Chest) troop).setMyImage(placedImg);
                    }

                    setGold(getGold() - troop.getCost());
                    getGoldLabel().setText(String.valueOf(getGold()));
                }
            }
        });
    }
    protected void addBackButton() {
        Texture texBack = safeLoad("backButton (1).png");
        TextButton.TextButtonStyle style = new TextButton.TextButtonStyle();
        style.font = getFont();
        if (texBack != null) style.up = new TextureRegionDrawable(texBack);

        TextButton back = new TextButton("", style);
        back.setSize((texBack != null ? texBack.getWidth() * 0.15f : 64),
            (texBack != null ? texBack.getHeight() * 0.15f : 32));
        back.setPosition(getViewport().getWorldWidth() - back.getWidth() - 24, 80);

        back.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                Mapcontrol.resetStaticState();
                getGame().setScreen(new ChooseScreen(getGame()));
            }
        });

        getStage().addActor(back);
    }

    private void ensurePath() {
        if (!path.isEmpty()) return;
        path.add(new Waypoint(0, 340));
        path.add(new Waypoint(40, 340));
        path.add(new Waypoint(70, 340));
        path.add(new Waypoint(320, 340));
        path.add(new Waypoint(320, 340));
        path.add(new Waypoint(320, 380));
        path.add(new Waypoint(320, 470));
        path.add(new Waypoint(320, 470));
        path.add(new Waypoint(232, 470));
        path.add(new Waypoint(225, 470));
        path.add(new Waypoint(225, 400));
        path.add(new Waypoint(225, 280));
        path.add(new Waypoint(225, 190));
        path.add(new Waypoint(225, 150));
        path.add(new Waypoint(225, 140));
        path.add(new Waypoint(180, 140));
        path.add(new Waypoint(130, 140));
        path.add(new Waypoint(120, 160));
        path.add(new Waypoint(120, 200));
        path.add(new Waypoint(120, 250));
        path.add(new Waypoint(120, 260));
        path.add(new Waypoint(280, 260));
        path.add(new Waypoint(380, 260));
        path.add(new Waypoint(395, 270));
        path.add(new Waypoint(410, 275));
        path.add(new Waypoint(410, 350));
        path.add(new Waypoint(410, 400));
        path.add(new Waypoint(440, 400));
        path.add(new Waypoint(490, 400));
        path.add(new Waypoint(490, 300));
        path.add(new Waypoint(490, 200));
        path.add(new Waypoint(475, 185));
        path.add(new Waypoint(400, 185));
        path.add(new Waypoint(300, 185));
        path.add(new Waypoint(300, -50));
    }

    // spawnEnemyByWave aman, selalu return enemy
    private Enemy spawnEnemyByWave(int round) {
        return createRandomEnemyForRound(round, path);
    }

    private void spawnEnemyForMap() {
        if (!waveActive) return;
        if (zombiesSpawned >= zombiesPerWave) return;

        spawnTimer += Gdx.graphics.getDeltaTime();
        if (spawnTimer < spawnInterval) return;
        spawnTimer = 0f;

        Enemy z = spawnEnemyByWave(round);

        if (z == null) return;

        z.setPosition(path.get(0).getX(), path.get(0).getY());
        enemies.add(z);

        Texture tex = getTextureForEnemy(z);
        if (tex == null) tex = getNormalZombieTexture();

        Image img = new Image(tex);
        img.setSize(64, 64);
        img.setPosition(z.getX(), z.getY());
        enemyImages.add(img);
        getStage().addActor(img);

        zombiesSpawned++;
    }

    @Override
    protected void onEnemyResolved(boolean killed) {
        zombiesResolved++;

        if (zombiesResolved >= zombiesPerWave && enemies.isEmpty()) {
            waveRunning = false;
            waveActive = false;
            giveMimicGold();
            showNextWaveButton();
            System.out.println("WAVE " + round + " FINISHED");
        }
    }

    private Texture getTextureForEnemy(Enemy e) {
        if (e instanceof fastZombie) return getFastZombieTexture();
        if (e instanceof bossZombie) return getBossZombieTexture();
        return getNormalZombieTexture();
    }

    private void startWave() {
        if (waveRunning) return;
        round++;
        roundLabel.setText(String.valueOf(round));
        waveRunning = true;
        waveActive = true;

        zombiesSpawned = 0;
        zombiesResolved = 0;
        spawnTimer = 0f;

        zombiesPerWave = getEnemyCountForRound(round);
        spawnInterval = getSpawnIntervalForRound(round);
        hideNextWaveButton();
        System.out.println("START WAVE " + round
            + " | Enemies: " + zombiesPerWave
            + " | Spawn interval: " + spawnInterval);
    }

    @Override
    protected boolean isGameWon() {
        return false;
    }

    @Override
    protected boolean isOnPath(float px, float py) {
        ensurePath();

        float tolerance = 32f; // lebar path

        for (int i = 0; i < path.size() - 1; i++) {
            Waypoint a = path.get(i);
            Waypoint b = path.get(i + 1);

            if (distancePointToSegment(px, py, a.getX(), a.getY(), b.getX(), b.getY()) <= tolerance) {
                return true;
            }
        }
        return false;
    }

    private float distancePointToSegment(
        float px, float py,
        float x1, float y1,
        float x2, float y2) {
        float dx = x2 - x1;
        float dy = y2 - y1;

        if (dx == 0 && dy == 0) {
            return (float) Math.hypot(px - x1, py - y1);
        }
        float t = ((px - x1) * dx + (py - y1) * dy) / (dx * dx + dy * dy);
        t = Math.max(0, Math.min(1, t));

        float closestX = x1 + t * dx;
        float closestY = y1 + t * dy;

        return (float) Math.hypot(px - closestX, py - closestY);
    }


    private Texture safeTexture(String name) {
        if (getNormalZombieTexture() != null) return getNormalZombieTexture();
        try {
            Texture t = new Texture(name);
            t.setFilter(Texture.TextureFilter.Nearest, Texture.TextureFilter.Nearest);
            return t;
        } catch (Exception e) {
            System.out.println("Cannot load fallback texture: " + name);
            return null;
        }
    }

    @Override
    protected void initWaves() {
        // Wave dibuat dinamis saat startWave().
    }
    @Override
    protected void initGoldLabel(Stage stage, BitmapFont font) {
        setGoldLabel(new Label(String.valueOf(getGold()), new Label.LabelStyle(font, Color.GOLD)));
        getGoldLabel().setPosition(130, 540); // posisi khusus map1
        stage.addActor(getGoldLabel());
    }
    protected void initHPLabel(Stage stage, BitmapFont font) {
        setHpLabel(new Label(String.valueOf(getBaseHP()), new Label.LabelStyle(font, Color.RED)));
        getHpLabel().setPosition(50, 540);
        stage.addActor(getHpLabel());
    }
    @Override
    protected void showNextWaveButton() {
        if (nextWaveButton != null) {
            nextWaveButton.remove();
        }
        TextButton.TextButtonStyle style = new TextButton.TextButtonStyle();
        style.font = getFont();
        nextWaveButton = new TextButton("NEXT WAVE", style);
        nextWaveButton.setSize(200, 60);
        nextWaveButton.setPosition(650, 110);

        nextWaveButton.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                startWave();
            }
        });
        getStage().addActor(nextWaveButton);
    }
    protected void hideNextWaveButton() {
        if (nextWaveButton != null) {
            nextWaveButton.remove();
            nextWaveButton = null;
        }
    }
    private void giveMimicGold() {
        for (Troop troop : troops) {
            if (troop instanceof Chest) {
                Chest mimic = (Chest) troop;
                setGold(getGold() + mimic.getGoldPerRound());
                getGoldLabel().setText(String.valueOf(getGold()));
                System.out.println("Mimic Chest memberikan " + mimic.getGoldPerRound() + " gold!");

                Image img = mimic.getMyImage();
                if (img != null) {
                    Texture originalTex = getChestTexture(); // simpan texture asli
                    img.setDrawable(new TextureRegionDrawable(mimic.getGoldEffectTexture()));

                    img.addAction(Actions.sequence(
                        Actions.delay(0.5f),
                        Actions.run(() -> img.setDrawable(new TextureRegionDrawable(originalTex)))
                    ));
                }
            }
        }
    }

    protected void initRoundLabel(Stage stage, BitmapFont font) {
        roundLabel = new Label("0", new Label.LabelStyle(font, Color.WHITE));
        roundLabel.setSize(70,70);
        roundLabel.setPosition(100, 450); // sesuaikan posisi
        stage.addActor(roundLabel);
    }

    private void applyFactionBuffs() {
        factionManager.updateFactionBuffs(troops);
    }
    protected void initFactionIcons() {
        kingdomIconTexture = safeLoad("kingdom.png");
        fantasyIconTexture = safeLoad("fantasy.png");
        kingdomBuffIcon = new Image(kingdomIconTexture);
        kingdomBuffIcon.setSize(50, 50);
        kingdomBuffIcon.setPosition(220, 535);
        kingdomBuffIcon.getColor().a = 0.6f; // awalnya redup
        getStage().addActor(kingdomBuffIcon);

        fantasyBuffIcon = new Image(fantasyIconTexture);
        fantasyBuffIcon.setSize(50, 50);
        fantasyBuffIcon.setPosition(260, 535);
        fantasyBuffIcon.getColor().a = 0.6f;
        getStage().addActor(fantasyBuffIcon);

        kingdomStatusLabel = new Label("", new Label.LabelStyle(getFont(), Color.WHITE));
        kingdomStatusLabel.setPosition(315, 570);
        getStage().addActor(kingdomStatusLabel);

        fantasyStatusLabel = new Label("", new Label.LabelStyle(getFont(), Color.WHITE));
        fantasyStatusLabel.setPosition(315, 552);
        getStage().addActor(fantasyStatusLabel);

        troopLimitLabel = new Label("", new Label.LabelStyle(getFont(), Color.WHITE));
        troopLimitLabel.setPosition(315, 534);
        getStage().addActor(troopLimitLabel);

        updateFactionBuffIcons();
    }
    private void updateFactionBuffIcons() {
        int kingdomCount = factionManager.getFactionCount(troops, FactName.KINGDOM);
        int fantasyCount = factionManager.getFactionCount(troops, FactName.FANTASY);
        boolean kingdomActive = kingdomCount >= FactionManager.FACTION_THRESHOLD;
        boolean fantasyActive = fantasyCount >= FactionManager.FACTION_THRESHOLD;

        kingdomBuffIcon.getColor().a = kingdomActive ? 1f : 0.3f;
        fantasyBuffIcon.getColor().a = fantasyActive ? 1f : 0.3f;
        kingdomStatusLabel.setText("KINGDOM " + Math.min(kingdomCount, 3) + "/3"
            + (kingdomActive ? " ACTIVE" : ""));
        fantasyStatusLabel.setText("FANTASY " + Math.min(fantasyCount, 3) + "/3"
            + (fantasyActive ? " ACTIVE" : ""));
        troopLimitLabel.setText("TROOPS " + troops.size() + "/" + MAX_TROOPS);
    }
}
