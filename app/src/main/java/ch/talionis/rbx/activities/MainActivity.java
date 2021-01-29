package ch.talionis.rbx.activities;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;

import ch.talionis.rbx.R;
import ch.talionis.rbx.router.Router;
import ch.talionis.rbx.screen.MainScreen;
import ch.talionis.rbx.screen.PlayScreen;

import static android.view.View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN;
import static android.view.View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION;
import static android.view.View.SYSTEM_UI_FLAG_LAYOUT_STABLE;
import static ch.talionis.rbx.activities.ApplicationUtils.getRouter;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        findViewById(R.id.main_container).setSystemUiVisibility(SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
                | SYSTEM_UI_FLAG_LAYOUT_STABLE | SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION);

        getRouter(this).setContainer(findViewById(R.id.main_container));
        getRouter(this).push(new MainScreen());
    }

    @Override
    public void onBackPressed() {
        Router router = getRouter(this);
        if (router.isEmpty()) {
            finish();
        } else {
            router.pop();
        }
    }
}
