package process.builder.event.raw;

public class TargetedEvent<T> {

    private final T target;

    public TargetedEvent(final T t) {
        this.target = t;
    }

    public T getTarget() {
        return target;
    }
}
