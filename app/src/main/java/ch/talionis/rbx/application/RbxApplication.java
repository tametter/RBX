package ch.talionis.rbx.application;

import android.app.Application;

import ch.talionis.rbx.engine.model.Level;
import ch.talionis.rbx.levels.LevelManager;
import ch.talionis.rbx.router.Router;
import ch.talionis.rbx.sound.SoundManager;

public class RbxApplication extends Application {
    private Router router;
    private LevelManager levelManager;
    private SoundManager soundManager;

    @Override
    public void onCreate() {
        super.onCreate();

        router = new Router();
        levelManager = new LevelManager();
        soundManager = new SoundManager(this);
    }

    public Router getRouter() {
        return router;
    }

    public LevelManager getLevelManager() {
        return levelManager;
    }

    public SoundManager getSoundManager() {
        return soundManager;
    }

    @Override
    public void onTerminate() {
        soundManager.release();
        super.onTerminate();
    }
}
