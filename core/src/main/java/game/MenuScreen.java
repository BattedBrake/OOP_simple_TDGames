package game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;
import com.badlogic.gdx.utils.ScreenUtils;
import com.badlogic.gdx.utils.viewport.FitViewport;

public class MenuScreen implements Screen {

    private final Main game;
    private SpriteBatch batch;
    private Texture background;
    private Stage stage;
    private BitmapFont font;

    private Texture texStart;
    private Texture texExit;

    private FitViewport viewport;   // <<< FIX

    public MenuScreen(Main game) {
        this.game = game;
    }

    @Override
    public void show() {
        batch = new SpriteBatch();
        background = new Texture(Gdx.files.internal("gambargemini.png"));

        viewport = new FitViewport(800, 600);  // <<< FIX
        stage = new Stage(viewport);
        Gdx.input.setInputProcessor(stage);

        font = new BitmapFont();

        // ==== LOAD BUTTON IMAGES ====
        texStart = new Texture("StartButton-removebg-preview.png");
        texExit  = new Texture("ExitImage (1).png");

        texStart.setFilter(Texture.TextureFilter.Nearest, Texture.TextureFilter.Nearest);
        texExit.setFilter(Texture.TextureFilter.Nearest, Texture.TextureFilter.Nearest);

        // ==== STYLE START ====
        TextButton.TextButtonStyle startStyle = new TextButton.TextButtonStyle();
        startStyle.font = font;
        startStyle.up   = new TextureRegionDrawable(new TextureRegion(texStart));

        // ==== STYLE EXIT ====
        TextButton.TextButtonStyle exitStyle = new TextButton.TextButtonStyle();
        exitStyle.font = font;
        exitStyle.up   = new TextureRegionDrawable(new TextureRegion(texExit));

        float worldW = viewport.getWorldWidth();
        float worldH = viewport.getWorldHeight();

        float scaleStart = 0.2f;
        float scaleExit  = 0.45f;

        // ==== START BUTTON ====
        TextButton start = new TextButton("", startStyle);
        start.setSize(texStart.getWidth() * scaleStart, texStart.getHeight() * scaleStart);
        start.setPosition(worldW / 2f - start.getWidth() / 2f, worldH / 2f - 20);

        start.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                game.setScreen(new ChooseScreen(game));
            }
        });
        stage.addActor(start);

        // ==== EXIT BUTTON ====
        TextButton exit = new TextButton("", exitStyle);
        exit.setSize(texExit.getWidth() * scaleExit, texExit.getHeight() * scaleExit);
        exit.setPosition(worldW / 2f - exit.getWidth() / 2f,
            worldH / 2f - exit.getHeight() - 20);

        exit.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                Gdx.app.exit();
            }
        });
        stage.addActor(exit);
    }

    @Override
    public void render(float delta) {
        ScreenUtils.clear(0,0,0,1);

        viewport.apply();                                // <<< FIX
        batch.setProjectionMatrix(viewport.getCamera().combined);

        batch.begin();
        batch.draw(background, 0, 0,
            viewport.getWorldWidth(),                // <<< FIX background scaling
            viewport.getWorldHeight());
        batch.end();

        stage.act(delta);
        stage.draw();
    }

    @Override
    public void resize(int width, int height) {
        viewport.update(width, height, true);            // <<< FIX input saat fullscreen
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
        texStart.dispose();
        texExit.dispose();
    }
}
