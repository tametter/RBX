package ch.talionis.rbx.application;

import android.app.Application;

import ch.talionis.rbx.engine.model.Level;
import ch.talionis.rbx.levels.LevelManager;
import ch.talionis.rbx.router.Router;

public class RbxApplication extends Application {
    private Router router;
    private LevelManager levelManager;

    @Override
    public void onCreate() {
        super.onCreate();

        router = new Router();
        levelManager = new LevelManager();
    }

    public Router getRouter() {
        return router;
    }

    public LevelManager getLevelManager() {
        return levelManager;
    }
}
