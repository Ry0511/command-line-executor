package ffmpeg.event.handler;

import ffmpeg.event.handler.event.MessageEvent;
import ffmpeg.event.handler.event.ProgressEvent;
import ffmpeg.event.message.ProgressMessage;
import process.builder.event.generic.GenericEventGroup;
import process.builder.event.generic.GenericListener;
import process.builder.event.handler.RegexHandler;
import process.builder.event.raw.OutputMessageEvent;

import java.util.regex.Pattern;

/**
 * Regex handler implementation designed specifically for registering the events
 * that occur whilst executing some ffmpeg command.
 *
 * @author -Ry
 * @version 0.1 Copyright: N/A
 */
public class FFMPEGHandler extends RegexHandler {

    // todo cleanup

    /**
     * Progress listener group which will have all listeners pinged
     */
    private final GenericEventGroup<ProgressEvent> cProgressListenerGroup;

    /**
     *
     */
    private final GenericEventGroup<MessageEvent> cMessageListenerGroup;

    /**
     *
     * @param msg
     */
    private void progressHandler(final OutputMessageEvent msg) {
        this.cProgressListenerGroup.forEach(i -> {
            i.onEvent(new ProgressEvent(
                    this,
                    i,
                    cProgressListenerGroup,
                    new ProgressMessage(msg.getMessage())
            ));
        });
    }

    /**
     *
     * @param event
     */
    private void msgHandler(final OutputMessageEvent event) {
        this.cMessageListenerGroup.forEach(i -> {
            i.onEvent(new MessageEvent(
                    this,
                    i,
                    cMessageListenerGroup,
                    event
            ));
        });
    }

    /**
     *
     */
    public FFMPEGHandler() {
        super();

        // Init groups
        this.cProgressListenerGroup = new GenericEventGroup<>();
        this.cMessageListenerGroup = new GenericEventGroup<>();

        // Setup handler
        setIsCleanseWhitespace(true);
        setIsLazyEvaluation(false);

        // Register handlers
        addRegMessageHandler(
                ProgressMessage.PROGRESS_REGEX,
                this::progressHandler
        );

        addRegMessageHandler(Pattern.compile(".*"), this::msgHandler);
    }

    /**
     * Adds the provided progress listener to the group of progress listeners.
     *
     * @param l The listener to add to the group.
     */
    public void addProgressListener(final GenericListener<ProgressEvent> l) {
        this.cProgressListenerGroup.addListener(l);
    }
    
    public void addRegMsgListener(final GenericListener<MessageEvent> l) {
        this.cMessageListenerGroup.addListener(l);
    }
}
