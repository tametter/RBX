package ch.talionis.rbx.router;

import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;
import java.util.List;
import java.util.Stack;

import ch.talionis.rbx.animation.ScreenAnimation;
import ch.talionis.rbx.animation.ScreenAnimation.AnimationCallable;
import ch.talionis.rbx.animation.SlideAnimation;
import ch.talionis.rbx.screen.Screen;

public class Router {
    private ViewGroup container;
    private View parallaxBackground;
    private final Stack<Screen> screenStack;
    private final ScreenAnimation defaultAnimation;
    private final List<RouterObservable> routerObservables;

    public Router() {
        screenStack = new Stack<>();
        defaultAnimation = new SlideAnimation();
        routerObservables = new ArrayList<>();
    }

    public void addObserver(RouterObservable routerObservable) {
        if (!routerObservables.contains(routerObservable)) {
            routerObservables.add(routerObservable);
        }
    }

    public void setContainer(ViewGroup container) {
        this.container = container;
    }

    public void setParallaxBackground(View view) {
        this.parallaxBackground = view;
    }

    public void push(Screen screen) {
        screenStack.push(screen);
        if (container.getChildCount() == 0) {
            container.addView(screen.getOrCreateView(container));
            return;
        }
        setScreen(screen, defaultAnimation.pushAnimation());
    }

    public void pop() {
        if (screenStack.isEmpty()) {
            throw new IllegalStateException("Tried to pop empty screen stack.");
        }

        screenStack.pop();

        if (screenStack.isEmpty()) {
            for (RouterObservable routerObservable : routerObservables) {
                routerObservable.onLastScreenPopped();
            }
            return;
        }

        setScreen(screenStack.peek(), defaultAnimation.popAnimation());
    }

    private void setScreen(Screen screen, AnimationCallable<View, View, View, View, Runnable> transitionAnimation) {
        View oldView = container.getChildAt(0);
        View newView = screen.getOrCreateView(container);

        container.addView(newView);
        transitionAnimation.apply(container, parallaxBackground, oldView, newView, () -> container.removeView(oldView));
    }
}
