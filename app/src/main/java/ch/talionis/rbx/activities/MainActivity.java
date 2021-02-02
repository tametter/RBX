package ch.talionis.rbx.activities;

import android.os.Bundle;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;

import ch.talionis.rbx.R;
import ch.talionis.rbx.router.Router;
import ch.talionis.rbx.router.RouterObservable;
import ch.talionis.rbx.screen.main.MainScreen;

import static android.view.View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN;
import static android.view.View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION;
import static android.view.View.SYSTEM_UI_FLAG_LAYOUT_STABLE;
import static ch.talionis.rbx.activities.ApplicationUtils.getRouter;

public class MainActivity extends AppCompatActivity implements RouterObservable {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        View mainContainer = findViewById(R.id.main_container);
        mainContainer.setSystemUiVisibility(SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
                | SYSTEM_UI_FLAG_LAYOUT_STABLE | SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION);

        Router router = getRouter(this);
        router.setContainer(findViewById(R.id.main_container));
        router.setParallaxBackground(findViewById(R.id.triangle_background));
        router.push(new MainScreen());
        router.addObserver(this);

        mainContainer.setOnApplyWindowInsetsListener((v, insets) -> {
            router.setInsets(insets);
            return insets;
        });
    }

    @Override
    public void onBackPressed() {
        getRouter(this).pop();
    }

    @Override
    public void onLastScreenPopped() {
        finish();
    }
}
