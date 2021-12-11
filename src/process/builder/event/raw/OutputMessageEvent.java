package process.builder.event.raw;

import process.builder.TrackedProcessExecutor;

public class OutputMessageEvent extends TargetedEvent<TrackedProcessExecutor> {

    private final String message;

    public OutputMessageEvent(final TrackedProcessExecutor target,
                              final String message) {
        super(target);
        this.message = message;
    }

    @Override
    public TrackedProcessExecutor getTarget() {
        return super.getTarget();
    }

    public String getMessage() {
        return message;
    }
}
