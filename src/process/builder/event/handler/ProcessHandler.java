package process.builder.event.handler;

import process.builder.event.raw.OutputErrorEvent;
import process.builder.event.raw.OutputFinishedEvent;
import process.builder.event.raw.OutputMessageEvent;

/**
 * Basic IO Listener functions for when some Process has new messages, errors,
 * or has finished.
 *
 * @author -Ry
 * @version 0.1
 * Copyright: N/A
 */
public interface ProcessHandler {

    /**
     * Default handler implementation which will ping all the messages to their
     * respective consoles. For example {@link #onConsoleMessage(OutputMessageEvent)}
     * will use {@link System#out} and {@link #onErrorMessage(OutputErrorEvent)}
     * will use {@link System#err}.
     *
     * @author -Ry
     * @version 0.1 Copyright: N/A
     */
    class ConsoleHandler implements ProcessHandler {

        /**
         * @param event Called whenever a process has a new message that
         * should/can be handled.
         */
        @Override
        public void onConsoleMessage(final OutputMessageEvent event) {
            System.out.println(event.getMessage());
        }

        /**
         * @param event Called whenever the error stream produces an exception.
         */
        @Override
        public void onErrorMessage(final OutputErrorEvent event) {
            System.err.println(event.getErrorMessage());
        }

        /**
         * @param event Called whenever the end of process has been reached.
         */
        @Override
        public void onProcessFinished(final OutputFinishedEvent event) {
            if (event.wasNaturalEnd()) {
                System.out.println("Process has finished normally!");
            } else {
                System.out.println("Process finished abnormally!");
            }
        }
    }

    /**
     * @param event Called whenever a process has a new message that should/can
     * be handled.
     */
    void onConsoleMessage(OutputMessageEvent event);

    /**
     * @param event Called whenever the error stream produces an exception.
     */
    void onErrorMessage(OutputErrorEvent event);

    /**
     * @param event Called whenever the end of process has been reached.
     */
    void onProcessFinished(OutputFinishedEvent event);
}
