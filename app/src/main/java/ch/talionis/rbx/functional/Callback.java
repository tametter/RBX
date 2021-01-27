package ch.talionis.rbx.functional;

import android.view.View;

import java.util.concurrent.atomic.AtomicReference;

public class Callback {
    public static void onAttach(View view, final BiConsumer<Scope, View> onAttach) {
        final AtomicReference<Scope> onAttachScope = new AtomicReference<>();
        view.addOnAttachStateChangeListener(new View.OnAttachStateChangeListener() {
            @Override
            public void onViewAttachedToWindow(View view) {
                onAttachScope.set(new Scope());
                onAttach.accept(onAttachScope.get(), view);
            }

            @Override
            public void onViewDetachedFromWindow(View view) {
                onAttachScope.get().close();
            }
        });
    }
}