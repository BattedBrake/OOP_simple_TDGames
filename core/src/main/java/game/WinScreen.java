package game;


import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;
import com.badlogic.gdx.utils.viewport.FitViewport;

public class WinScreen implements Screen {

    private Main game;
    private Stage stage;
    private FitViewport viewport;
    private BitmapFont font;

    private Texture winTexture;
    private Image winImage;
    private Texture backTexture;

    public WinScreen(Main game) {
        this.game = game;
    }

    @Override
    public void show() {
        viewport = new FitViewport(1280, 720);
        stage = new Stage(viewport);
        Gdx.input.setInputProcessor(stage);

        font = new BitmapFont();
        font.setColor(Color.WHITE);

        winTexture = new Texture("win.png"); // ganti png kalau mau
        winImage = new Image(winTexture);

        float scale = 0.7f;
        winImage.setSize(
            winTexture.getWidth() * scale,
            winTexture.getHeight() * scale
        );

        winImage.setPosition(
            (viewport.getWorldWidth() - winImage.getWidth()) / 2f,
            (viewport.getWorldHeight() - winImage.getHeight()) / 2f
        );

        stage.addActor(winImage);

        addBackButton();
    }

    protected void addBackButton() {
        backTexture = new Texture("backButton (1).png");

        TextButton.TextButtonStyle style = new TextButton.TextButtonStyle();
        style.font = font;
        style.up = new TextureRegionDrawable(backTexture);

        TextButton back = new TextButton("", style);
        back.setSize(
            backTexture.getWidth() * 0.20f,
            backTexture.getHeight() * 0.20f
        );
        back.setPosition(viewport.getWorldWidth() - back.getWidth() - 60, 80);

        back.addListener(event -> {
            if (!back.isPressed()) return false;
            game.setScreen(new ChooseScreen(game));
            return true;
        });

        stage.addActor(back);
    }

    @Override
    public void render(float delta) {
        Gdx.gl.glClearColor(0, 0, 0, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        stage.act(delta);
        stage.draw();
    }

    @Override public void resize(int width, int height) {
        viewport.update(width, height, true);
    }

    @Override public void pause() {}
    @Override public void resume() {}
    @Override public void hide() {}

    @Override
    public void dispose() {
        stage.dispose();
        font.dispose();
        if (winTexture != null) winTexture.dispose();
        if (backTexture != null) backTexture.dispose();
    }
}
