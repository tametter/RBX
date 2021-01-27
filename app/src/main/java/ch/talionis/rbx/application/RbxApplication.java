package ch.talionis.rbx.application;

import android.app.Application;

import ch.talionis.rbx.router.Router;

public class RbxApplication extends Application {
    private Router router;

    @Override
    public void onCreate() {
        super.onCreate();

        router = new Router();
    }

    public Router getRouter() {
        return router;
    }
}
