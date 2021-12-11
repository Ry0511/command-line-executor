package process.builder.event.handler;

import process.builder.event.raw.OutputErrorEvent;
import process.builder.event.raw.OutputFinishedEvent;
import process.builder.event.raw.OutputMessageEvent;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.function.Consumer;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Regex handler implementation of the {@link ProcessHandler} where each string
 * of the process can be assigned a handler. Providing various before evaluation
 * options.
 *
 * @author -Ry
 * @version 0.1 Copyright: N/A
 */
public class RegexHandler implements ProcessHandler {

    /**
     * Maps a pattern of some regular message to its handler.
     */
    private final Map<Pattern, Consumer<OutputMessageEvent>> regMsgHandlers;

    /**
     * Maps a pattern of some error message to its handler.
     */
    private final Map<Pattern, Consumer<OutputErrorEvent>> regErrHandlers;

    /**
     * Default handler to call when a process has concluded.
     */
    private Consumer<OutputFinishedEvent> regFinishedHandler;

    /**
     * States whether we should stop evaluating other options once an option has
     * been found.
     */
    private final AtomicBoolean isLazyEvaluation = new AtomicBoolean(true);

    /**
     * States whether we should before evaluating strings cleanse all the white
     * space characters. This simplifies regex matching as you don't have to
     * match through whitespace.
     */
    private final AtomicBoolean isCleanseWhitespace = new AtomicBoolean();

    /**
     * Constructs the regex handler from the base finish handler. Initialises
     * the maps to their default {@link HashMap#HashMap()} states.
     *
     * @param handler The default on finished handler.
     */
    public RegexHandler(final Consumer<OutputFinishedEvent> handler) {
        this.regMsgHandlers = Collections.synchronizedMap(new HashMap<>());
        this.regErrHandlers = Collections.synchronizedMap(new HashMap<>());
        this.regFinishedHandler = handler;
    }

    /**
     * Set the evaluation verbosity where {@code true} means that only one match
     * is required for it to return. Whereas {@code false} will evaluate all
     * possible handlers regardless of if a match has been found.
     *
     * @param isLazyEvaluation The new evaluation state. Default is {@code
     * true}.
     */
    public void setIsLazyEvaluation(final boolean isLazyEvaluation) {
        this.isLazyEvaluation.set(isLazyEvaluation);
    }

    /**
     * State whether all whitespaces should be removed from the event string
     * before evaluation of all regexes should occur.
     *
     * @param isCleanseWhitespace The state of the whitespace cleanse. Default
     * is {@code false}.
     */
    public void setIsCleanseWhitespace(final boolean isCleanseWhitespace) {
        this.isCleanseWhitespace.set(isCleanseWhitespace);
    }

    /**
     * Add a default message handler for specific strings.
     *
     * @param regex The strings that this handler should handle.
     * @param handl The handler that handles the aforementioned strings.
     */
    public void addRegMessageHandler(final Pattern regex,
                                     final Consumer<OutputMessageEvent> handl) {
        Objects.requireNonNull(regex);
        Objects.requireNonNull(handl);
        this.regMsgHandlers.put(regex, handl);
    }

    /**
     * Removes a handler from the default message handlers.
     *
     * @param regex The pattern to remove. (The handler is removed as well).
     */
    public void removeRegHandler(final Pattern regex) {
        Objects.requireNonNull(regex);
        this.regMsgHandlers.remove(regex);
    }

    /**
     * Add a handler for error messages that match the provided regex.
     *
     * @param regex Matches strings that this handler should receive.
     * @param handl Handles the aforementioned strings.
     */
    public void addRegErrHandler(final Pattern regex,
                                 final Consumer<OutputErrorEvent> handl) {
        Objects.requireNonNull(regex);
        Objects.requireNonNull(handl);
        this.regErrHandlers.put(regex, handl);
    }

    /**
     * Remove the target error handler.
     *
     * @param regex The handler to remove.
     */
    public void removeErrHandler(final Pattern regex) {
        Objects.requireNonNull(regex);
        this.regErrHandlers.remove(regex);
    }

    /**
     * Set the Task finished handler this overwrites the existing one passed in
     * upon construction.
     *
     * @param handler The new handler to use.
     */
    public synchronized void setRegFinishedHandler(
            final Consumer<OutputFinishedEvent> handler) {
        this.regFinishedHandler = handler;
    }

    /**
     * @param event Called whenever a process has a new message that should/can
     * be handled.
     */
    @Override
    public void onConsoleMessage(final OutputMessageEvent event) {
        final String targetStr = getTargetString(event.getMessage());
        for (Pattern p : this.regMsgHandlers.keySet()) {
            final Matcher m = p.matcher(targetStr);

            // Ping matches
            if (m.matches()) {
                this.regMsgHandlers.get(p).accept(event);

                // Return immediately on lazy eval
                if (this.isLazyEvaluation.get()) {
                    return;
                }
            }
        }
    }

    /**
     * @param event Called whenever the error stream produces an exception.
     */
    @Override
    public void onErrorMessage(final OutputErrorEvent event) {
        final String targetStr = getTargetString(event.getErrorMessage());
        for (Pattern p : this.regErrHandlers.keySet()) {
            final Matcher m = p.matcher(targetStr);

            // Ping matches
            if (m.matches()) {
                this.regErrHandlers.get(p).accept(event);

                // Return immediately on lazy eval
                if (this.isLazyEvaluation.get()) {
                    return;
                }
            }
        }
    }

    /**
     * Returns the target string cleansing any and all whitespace if
     * isCleanseWhitespace is {@code true}.
     *
     * @param msg The message to format.
     * @return Possibly formatted string.
     */
    private String getTargetString(final String msg) {
        if (isCleanseWhitespace.get()) {
            return msg.replaceAll("\\s", "");

        } else {
            return msg;
        }
    }

    /**
     * @param event Called whenever the end of process has been reached.
     */
    @Override
    public void onProcessFinished(final OutputFinishedEvent event) {
        this.regFinishedHandler.accept(event);
    }
}
