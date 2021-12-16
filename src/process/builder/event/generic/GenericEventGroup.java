package process.builder.event.generic;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.function.Consumer;

/**
 * Structures generically a set of listeners that handle some event 'T'
 * providing functionality to add, remove, and ping all listeners in the group.
 *
 * @param <T> The type of the event that this group handles.
 * @author -Ry
 * @version 0.1 Copyright: N/A
 */
public class GenericEventGroup<T> {

    /**
     * The listeners associated with this group.
     */
    private final List<GenericListener<T>> cListeners =
            Collections.synchronizedList(new ArrayList<>());

    /**
     * Add a listener which will be pinged upon a new event.
     *
     * @param listener The listener to add.
     */
    public void addListener(final GenericListener<T> listener) {
        this.cListeners.add(listener);
    }

    /**
     * Removes the specified listener from the group.
     *
     * @param listener The listener to remove.
     */
    public void removeListener(final GenericListener<T> listener) {
        this.cListeners.remove(listener);
    }

    /**
     * Applies the given action to each listener in the group.
     *
     * @param action The action to apply to each listener.
     */
    public void forEach(final Consumer<GenericListener<T>> action) {
        cListeners.forEach(action);
    }

    @Override
    public String toString() {
        return String.format("%s{listenerCount='%s', targetType='%s'}",
                this.getClass().getSimpleName(),
                cListeners.size(),
                getClass().getTypeParameters()[0].getClass().getSimpleName()
        );
    }
}
