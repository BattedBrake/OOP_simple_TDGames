package game;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.backends.lwjgl3.Lwjgl3Application;
import com.badlogic.gdx.backends.lwjgl3.Lwjgl3ApplicationConfiguration;

public class Main extends Game {
    private Music music;
    @Override
    public void create() {
        // Musik latar
        music = Gdx.audio.newMusic(Gdx.files.internal("bgmusic.mp3"));
        music.setLooping(true);
        music.setVolume(0.0f);
        music.play();
        // Set screen awal (menu utama)
        setScreen(new MenuScreen(this));
    }
    @Override
    public void render() {
        // PENTING: agar screen aktif (MenuScreen/PlayScreen) bisa menggambar
        super.render();
    }
    @Override
    public void dispose() {
        super.dispose();
        if (music != null) music.dispose();
    }

    public static void main(String[] args) {
        Lwjgl3ApplicationConfiguration config = new Lwjgl3ApplicationConfiguration();
        config.setTitle("Battle");
        config.setFullscreenMode(Lwjgl3ApplicationConfiguration.getDisplayMode());
        config.useVsync(true);
        new Lwjgl3Application(new Main(), config);
    }
}
