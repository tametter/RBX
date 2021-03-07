package ch.talionis.rbx.generator;

public abstract class Step<I, O> {
    private O output;

    public abstract O apply(I input);

    public final void set(I input) {
        this.output = apply(input);
    }

    public <K> Step<O, K> then(Step<O, K> step) {
        step.set(output);
        return step;
    }

    public O get() {
        return output;
    }
}
