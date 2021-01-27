package ch.talionis.rbx.functional;

import java.util.ArrayList;
import java.util.List;

public class Scope {
    private List<Runnable> onCloseRunnables = new ArrayList<>();

    public void onClose(Runnable onClose) {
        if (!onCloseRunnables.contains(onClose)) {
            onCloseRunnables.add(onClose);
        }
    }

    public void close() {
        for (Runnable onCloseRunnable : onCloseRunnables) {
            onCloseRunnable.run();
        }
    }
}