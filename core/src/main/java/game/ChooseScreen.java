package game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.utils.ScreenUtils;
import com.badlogic.gdx.utils.viewport.FitViewport;

import java.util.ArrayList;

public class ChooseScreen implements Screen {
    private final Main game;
    private SpriteBatch batch;
    private Texture background;
    private Stage stage;
    private BitmapFont font;
    private Texture texPlay;
    private Texture texBack;
    private FitViewport viewport;

    public ChooseScreen(Main game) {
        this.game = game;
    }

    @Override
    public void show() {
        batch = new SpriteBatch();

        viewport = new FitViewport(800, 600);
        stage = new Stage(viewport);
        Gdx.input.setInputProcessor(stage);

        background = new Texture(Gdx.files.internal("choosemenu.jpg"));

        font = new BitmapFont();
        font.getRegion().getTexture().setFilter(Texture.TextureFilter.Nearest, Texture.TextureFilter.Nearest);

        texPlay = new Texture("PlayButton-removebg-preview.png");
        texBack = new Texture("backButton (1).png");

        texPlay.setFilter(Texture.TextureFilter.Nearest, Texture.TextureFilter.Nearest);
        texBack.setFilter(Texture.TextureFilter.Nearest, Texture.TextureFilter.Nearest);

        TextButton.TextButtonStyle startStyle = new TextButton.TextButtonStyle();
        startStyle.font = font;
        startStyle.up = new TextureRegionDrawable(new TextureRegion(texPlay));

        TextButton.TextButtonStyle exitStyle = new TextButton.TextButtonStyle();
        exitStyle.font = font;
        exitStyle.up = new TextureRegionDrawable(new TextureRegion(texBack));

        float worldW = viewport.getWorldWidth();
        float worldH = viewport.getWorldHeight();

        float scaleStart = 0.5f;
        float scaleExit = 0.3f;
        float spacing = 50;

        // === START BUTTON ===
        TextButton start = new TextButton("", startStyle);
        start.setSize(texPlay.getWidth() * scaleStart, texPlay.getHeight() * scaleStart);
        start.setPosition(
            worldW / 2f - start.getWidth() - spacing / 2f,
            worldH / 2f - 200
        );

        // === BACK BUTTON ===
        TextButton exit = new TextButton("", exitStyle);
        exit.setSize(texBack.getWidth() * scaleExit, texBack.getHeight() * scaleExit);
        exit.setPosition(
            worldW / 2f + spacing / 2f,
            worldH / 2f - 185
        );

        exit.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                game.setScreen(new MenuScreen(game));
            }
        });

        stage.addActor(start);
        stage.addActor(exit);

        Texture map1Tex = new Texture("mapDisplay.jpg");
        Texture map2Tex = new Texture("map2.jpg");

        Image map1 = new Image(map1Tex);
        Image map2 = new Image(map2Tex);

        float mapScale = 0.30f;
        map1.setSize(map1Tex.getWidth() * mapScale + 180, map1Tex.getHeight() * mapScale + 180);
        map2.setSize(map2Tex.getWidth() * mapScale + 240, map2Tex.getHeight() * mapScale + 240);

        spacing = 40f;
        map1.setPosition(
            worldW / 2f - map1.getWidth() - spacing / 2f,
            worldH / 2f - 50
        );
        map2.setPosition(
            worldW / 2f + spacing / 2f,
            worldH / 2f - 50
        );

        final ArrayList<Integer> selectedMap = new ArrayList<>();
        selectedMap.add(0);

        map1.setColor(1, 1, 1, 1);
        map2.setColor(1, 1, 1, 0.6f);

        map1.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                selectedMap.set(0, 1);
                map1.setColor(1, 1, 1, 1);
                map2.setColor(1, 1, 1, 0.6f);
            }
        });

        map2.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                selectedMap.set(0, 2);
                map1.setColor(1, 1, 1, 0.6f);
                map2.setColor(1, 1, 1, 1);
            }
        });

        stage.addActor(map1);
        stage.addActor(map2);

        // === START BUTTON DECIDES MAP ===
        start.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {

                int chosen = selectedMap.get(0);

                if (chosen == 1) {
                    game.setScreen(new Map1(game));
                } else if (chosen == 2) {
                    game.setScreen(new Map2(game));
                } else {
                    System.out.println("Belum pilih map!");
                }
            }
        });
    }

    @Override
    public void render(float delta) {
        ScreenUtils.clear(0, 0, 0, 1);

        viewport.apply();
        batch.setProjectionMatrix(viewport.getCamera().combined);

        batch.begin();
        batch.draw(background, 0, 0, viewport.getWorldWidth(), viewport.getWorldHeight());
        batch.end();

        stage.act(delta);
        stage.draw();
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
        batch.dispose();
        background.dispose();
        stage.dispose();
        font.dispose();
        texPlay.dispose();
        texBack.dispose();
    }
}
