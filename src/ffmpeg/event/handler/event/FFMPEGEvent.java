package ffmpeg.event.handler.event;

import ffmpeg.event.handler.FFMPEGHandler;
import process.builder.event.generic.GenericEventGroup;
import process.builder.event.generic.GenericListener;

import java.util.Objects;

/**
 * @param <T>
 */
public abstract class FFMPEGEvent<T> {

    /**
     * The handler which initiated this event.
     */
    private final FFMPEGHandler cTargetHandler;

    /**
     * The listener that was fired.
     */
    private final GenericListener<T> cFiredListener;

    /**
     * The containing group that the listener exists in.
     */
    private final GenericEventGroup<T> cEventGroup;

    /**
     * Constructs the base event from the handler, fired listener, and event
     * group.
     *
     * @param handler The handler that fired the listener.
     * @param listener The listener that was fired.
     * @param eventGroup The group to which the listener exists in.
     */
    public FFMPEGEvent(final FFMPEGHandler handler,
                       final GenericListener<T> listener,
                       final GenericEventGroup<T> eventGroup) {
        this.cTargetHandler = handler;
        this.cFiredListener = listener;
        this.cEventGroup = eventGroup;
    }

    /**
     * @return The handler that fired this event.
     */
    public FFMPEGHandler getTargetHandler() {
        return cTargetHandler;
    }

    /**
     * @return The listener object representation that was fired.
     */
    public GenericListener<T> getFiredListener() {
        return cFiredListener;
    }

    /**
     * @return The containing event group that the listener was fired from.
     */
    public GenericEventGroup<T> getEventGroup() {
        return cEventGroup;
    }

    /**
     * Checks for equality of this object to another object. Where equality is
     * based on the target handler. Two events are considered equal if the
     * handler objects are equal.
     *
     * @param o The object to check for equality with.
     * @return {@code true} if the object is of the same type and the handler is
     * the same.
     */
    @Override
    public boolean equals(final Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        final FFMPEGEvent<?> that = (FFMPEGEvent<?>) o;
        return cTargetHandler.equals(that.cTargetHandler);
    }

    /**
     * Hashcode of all events is the result of hashing the target handler
     * object.
     *
     * @return Hash of this event based on its handler.
     */
    @Override
    public int hashCode() {
        return Objects.hash(cTargetHandler);
    }
}
