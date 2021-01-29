package ch.talionis.rbx.activities;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;

import ch.talionis.rbx.R;
import ch.talionis.rbx.router.Router;
import ch.talionis.rbx.screen.MainScreen;
import ch.talionis.rbx.screen.PlayScreen;

import static ch.talionis.rbx.activities.ApplicationUtils.getRouter;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

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
