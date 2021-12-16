package ffmpeg.event.handler.event;

import ffmpeg.event.handler.FFMPEGHandler;
import ffmpeg.event.message.ProgressMessage;
import process.builder.event.generic.GenericEventGroup;
import process.builder.event.generic.GenericListener;

/**
 * Event represents some progress update where some data can be obtained whilst
 * also retaining origin information such as fired listener, group, and
 * handler.
 *
 * @author -Ry
 * @version 0.1 Copyright: N/A
 */
public class ProgressEvent extends FFMPEGEvent<ProgressEvent> {

    private final ProgressMessage cTarget;

    /**
     * Constructs the base event from the handler, fired listener, and event
     * group.
     *
     * @param handler The handler that fired the listener.
     * @param listener The listener that was fired.
     * @param eventGroup The group to which the listener exists in.
     */
    public ProgressEvent(final FFMPEGHandler handler,
                         final GenericListener<ProgressEvent> listener,
                         final GenericEventGroup<ProgressEvent> eventGroup,
                         final ProgressMessage message) {
        super(handler, listener, eventGroup);
        this.cTarget = message;
    }

    /**
     * @return Parsed progress message data.
     */
    public ProgressMessage getTarget() {
        return cTarget;
    }
}
