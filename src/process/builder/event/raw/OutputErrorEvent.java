package process.builder.event.raw;

import process.builder.TrackedProcessExecutor;

public class OutputErrorEvent extends TargetedEvent<TrackedProcessExecutor> {

    private final String errorMessage;

    public OutputErrorEvent(final TrackedProcessExecutor target,
                            final String pErrorMessage) {
        super(target);
        this.errorMessage = pErrorMessage;
    }

    public String getErrorMessage() {
        return errorMessage;
    }
}
