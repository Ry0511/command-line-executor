package process.builder.event.raw;

import process.builder.TrackedProcessExecutor;
//todo comment
/**
 *
 */
public class OutputErrorEvent extends OutputMessageEvent {

    /**
     * Constructs the error message from the base message event.
     *
     * @param target The target executor that has encountered the error.
     * @param message The error that was encountered.
     */
    public OutputErrorEvent(final TrackedProcessExecutor target,
                            final String message) {
        super(target, message);
    }
}
