package ffmpeg.event.handler.event;

import ffmpeg.event.handler.FFMPEGHandler;
import process.builder.event.generic.GenericEventGroup;
import process.builder.event.generic.GenericListener;
import process.builder.event.raw.OutputMessageEvent;

/**
 * Represents a generic FFMPEG message event where the content of said message
 * can be anything; Error message, regular message, progress message, it is just
 * 'a message'.
 * <p>
 * This event is primarily used for debugging as it offers no true functional
 * difference than the underlying {@link OutputMessageEvent} that it wraps.
 * Except that this event is wrapped by the pair classes {@link
 * GenericEventGroup} and {@link GenericListener}.
 *
 * @author -Ry
 * @version 0.1 Copyright: N/A
 */
public class MessageEvent extends FFMPEGEvent<MessageEvent> {

    /**
     * The underlying message event that was triggered.
     */
    private final OutputMessageEvent cTarget;

    /**
     * Constructs the base event from the handler, fired listener, and event
     * group.
     *
     * @param handler The handler that fired the listener.
     * @param listener The listener that was fired.
     * @param eventGroup The group to which the listener exists in.
     * @param target The target event that was triggered.
     */
    public MessageEvent(final FFMPEGHandler handler,
                        final GenericListener<MessageEvent> listener,
                        final GenericEventGroup<MessageEvent> eventGroup,
                        final OutputMessageEvent target) {
        super(handler, listener, eventGroup);
        this.cTarget = target;
    }

    /**
     * The message event that was fired triggered.
     *
     * @return The underlying event that was triggered.
     */
    public OutputMessageEvent getTarget() {
        return cTarget;
    }
}
