package ch.talionis.rbx.router;

import android.util.Pair;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowInsets;

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
    private final Stack<Pair<Screen, ScreenAnimation>> screenStack;
    private final ScreenAnimation defaultAnimation;
    private final List<RouterObservable> routerObservables;
    private WindowInsets insets;

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

    public void setInsets(WindowInsets insets) {
        this.insets = insets;

        if (!screenStack.isEmpty()) {
            screenStack.peek().first.onInsets(insets);
        }
    }

    public void setContainer(ViewGroup container) {
        this.container = container;
    }

    public void setParallaxBackground(View view) {
        this.parallaxBackground = view;
    }

    public void push(Screen screen, ScreenAnimation screenAnimation) {
        screenStack.push(new Pair<>(screen, screenAnimation));
        if (container.getChildCount() == 0) {
            container.addView(screen.getOrCreateView(container));
            return;
        }

        setScreen(screen, screenAnimation.pushAnimation());
    }

    public void push(Screen screen) {
        push(screen, defaultAnimation);
    }

    public void pop() {
        if (screenStack.isEmpty()) {
            throw new IllegalStateException("Tried to pop empty screen stack.");
        }

        Pair<Screen, ScreenAnimation> popped = screenStack.pop();

        if (screenStack.isEmpty()) {
            for (RouterObservable routerObservable : routerObservables) {
                routerObservable.onLastScreenPopped();
            }
            return;
        }

        Pair<Screen, ScreenAnimation> screenAnimationPair = screenStack.peek();
        setScreen(screenAnimationPair.first, popped.second.popAnimation());
    }

    private void setScreen(Screen screen, AnimationCallable<View, View, View, View, Runnable> transitionAnimation) {
        View oldView = container.getChildAt(0);
        View newView = screen.getOrCreateView(container);
        if (!screenStack.isEmpty() && insets != null) {
            screen.onInsets(insets);
        }

        container.addView(newView);
        transitionAnimation.apply(container, parallaxBackground, oldView, newView, () -> container.removeView(oldView));
    }
}
