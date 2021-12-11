package process.builder.event.raw;

import process.builder.TrackedProcessExecutor;

public class OutputFinishedEvent extends TargetedEvent<TrackedProcessExecutor> {

    private final boolean wasNaturalEnd;

    public OutputFinishedEvent(TrackedProcessExecutor target,
                               final boolean wasNaturalEnd) {
        super(target);
        this.wasNaturalEnd = wasNaturalEnd;
    }

    public boolean wasNaturalEnd() {
        return wasNaturalEnd;
    }
}
