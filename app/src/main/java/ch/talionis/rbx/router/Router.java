package ch.talionis.rbx.router;

import android.view.ViewGroup;

import java.util.Stack;

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
        setView(screen);
    }

    public boolean isEmpty() {
        return screenStack.isEmpty();
    }

    public Screen peek() {
        return screenStack.peek();
    }

    public void pop() {
        screenStack.pop();
        setView(screenStack.peek());
    }

    private void setView(Screen screen) {
        container.removeAllViews();
        container.addView(screen.getOrCreateView(container));
    }
}
