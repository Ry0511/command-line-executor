package process.builder.event.generic;

/**
 * Generic listener which can handle a single event of the target type.
 *
 * @param <T> The type of the event that this listener handles.
 */
public interface GenericListener<T> {

    /**
     * Event handler method.
     *
     * @param event The event that is to be handled.
     */
    void onEvent(T event);
}
