package ch.talionis.rbx.router;

import android.view.View;
import android.view.ViewGroup;

import java.util.Stack;

import ch.talionis.rbx.animation.ScreenAnimation;
import ch.talionis.rbx.animation.SlideAnimation;
import ch.talionis.rbx.screen.Screen;

public class Router {
    private ViewGroup container;
    private final Stack<Screen> screenStack;

    public Router() {
        screenStack = new Stack<>();
    }

    public void setContainer(ViewGroup container) {
        this.container = container;
    }

    public void push(Screen screen) {
        screenStack.push(screen);
        if (container.getChildCount() == 0) {
            container.addView(screen.getOrCreateView(container));
            return;
        }
        animatePush(screen);
    }

    public boolean isEmpty() {
        return screenStack.isEmpty();
    }

    public Screen peek() {
        return screenStack.peek();
    }

    public void pop() {
        screenStack.pop();
        animatePop(screenStack.peek());
    }

    private void animatePush(Screen screen) {
        View currentView = container.getChildAt(0);
        View newView = screen.getOrCreateView(container);

        container.addView(newView);

        ScreenAnimation screenAnimation = new SlideAnimation();
        screenAnimation.animatePush(container, currentView, newView, () -> container.removeView(currentView));
    }

    private void animatePop(Screen screen) {
        View currentView = container.getChildAt(0);
        View newView = screen.getOrCreateView(container);

        container.addView(newView);

        ScreenAnimation screenAnimation = new SlideAnimation();
        screenAnimation.animatePop(container, currentView, newView, () -> container.removeView(currentView));
    }
}
