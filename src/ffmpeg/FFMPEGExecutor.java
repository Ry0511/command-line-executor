package ffmpeg;

import ffmpeg.event.handler.FFMPEGHandler;
import ffmpeg.event.handler.event.ProgressEvent;
import process.builder.TrackedProcessExecutor;
import process.builder.event.generic.GenericListener;
import process.builder.event.handler.ProcessHandler;

/**
 * Has the exact same functionality and restrictions as the {@link
 * TrackedProcessExecutor} however this executor is fully designed for listening
 * specifically for events that can be triggered on FFMPEG tasks.
 *
 * @author -Ry
 * @version 0.1 Copyright: N/A
 */
public class FFMPEGExecutor extends TrackedProcessExecutor {

    /**
     * Constructs the ffmpeg executor and initialises the base handler ready to
     * accept listeners.
     */
    public FFMPEGExecutor() {
        super.setProcessHandler(new FFMPEGHandler());
    }

    /**
     * @return The current process handler.
     */
    @Override
    public FFMPEGHandler getProcessHandler() {
        return (FFMPEGHandler) super.getProcessHandler();
    }

    /**
     * This method will always throw an exception here. Use the register
     * listener methods provided in order to create event handlers.
     *
     * @throws UnsupportedOperationException The handler is not supposed to be
     *                                       changed for the FFMPEG Executor
     *                                       ever.
     */
    @Override
    public void setProcessHandler(final ProcessHandler handler) {
        // todo error message
        throw new UnsupportedOperationException();
    }

    /**
     * Convince method for adding an event listener. Call is equal to the {@link
     * FFMPEGHandler#addProgressListener(GenericListener)} in every way.
     *
     * @param l The listener to register.
     */
    public void addProgressListener(final GenericListener<ProgressEvent> l) {
        this.getProcessHandler().addProgressListener(l);
    }
}
