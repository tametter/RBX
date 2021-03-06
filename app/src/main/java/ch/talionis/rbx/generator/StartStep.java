package ch.talionis.rbx.generator;

class StartStep<T> {
    private T value;

    <K> Step<T, K> then(Step<T, K> step) {
        step.set(value);
        return step;
    }

    private StartStep(T value) {
        this.value = value;
    }

    static <M> StartStep<M> startWith(M value) {
        return new StartStep<>(value);
    }
}