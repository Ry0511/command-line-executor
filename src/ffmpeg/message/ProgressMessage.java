package ffmpeg.message;

import java.lang.reflect.MalformedParametersException;
import java.util.Objects;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Represents a generic FFMPEG progress message.
 *
 * @author -Ry
 * @version 0.1 Copyright: N/A
 */
public class ProgressMessage {

    /**
     * Base pattern for the progress messages. This regex does not validate the
     * actual values and assumes they're irrelevant. For instance, you can have
     * an infinite number of whitespace before and after each entry. Such as
     * "fps=              foo            20".
     */
    public static final Pattern PROGRESS_REGEX = Pattern.compile(
            "(?m)^frame=(.*?)"
                    + "fps=(.*?)"
                    + "q=(.*?)"
                    + "size=(.*?)"
                    + "time=(.*?)"
                    + "bitrate=(.*?)"
                    + "speed=(.*?)$"
    );

    /**
     * Capture the value of the frame from a progress message.
     */
    public static final int FRAME_GROUP = 1;

    /**
     * Capture the value of the fps from a progress message.
     */
    public static final int FPS_GROUP = 2;

    /**
     * Capture the value of the q val from a progress message.
     */
    public static final int Q_GROUP = 3;

    /**
     * Capture the value of the size from a progress message.
     */
    public static final int SIZE_GROUP = 4;

    /**
     * Capture the value of the time from a progress message.
     */
    public static final int TIME_GROUP = 5;

    /**
     * Capture the value of the bitrate from a progress message.
     */
    public static final int BITRATE_GROUP = 6;

    /**
     * Capture the value of the speed from a progress message.
     */
    public static final int SPEED_GROUP = 7;

    /**
     * Literal frame value parsed from the raw string.
     */
    private final String frame;

    /**
     * Literal fps value parsed from the raw string.
     */
    private final String fps;

    /**
     * Literal q-value parsed from the raw string.
     */
    private final String qVal;

    /**
     * Literal size of the file parsed from the raw string.
     */
    private final String size;

    /**
     * Literal time value parsed from the raw string.
     */
    private final String time;

    /**
     * Literal bitrate value parsed from the raw string.
     */
    private final String bitrate;

    /**
     * Literal speed value parsed from the raw string.
     */
    private final String speed;

    /**
     * @param raw The raw progress string to parse; All whitespace characters
     * should be removed.
     * @throws NullPointerException         If the provided raw string is null.
     * @throws MalformedParametersException If the provided raw string does not
     *                                      match the expected.
     */
    public ProgressMessage(final String raw) {
        Objects.requireNonNull(raw);
        final Matcher m = PROGRESS_REGEX.matcher(raw);

        if (m.matches()) {
            frame = m.group(FRAME_GROUP);
            fps = m.group(FPS_GROUP);
            qVal = m.group(Q_GROUP);
            size = m.group(SIZE_GROUP);
            time = m.group(TIME_GROUP);
            bitrate = m.group(BITRATE_GROUP);
            speed = m.group(SPEED_GROUP);
        } else {
            throw new MalformedParametersException(raw);
        }
    }

    /**
     * @return Frame value.
     */
    public String getFrame() {
        return frame;
    }

    /**
     * @return Fps value.
     */
    public String getFps() {
        return fps;
    }

    /**
     * @return Q Value.
     */
    public String getQVal() {
        return qVal;
    }

    /**
     * @return Current size.
     */
    public String getSize() {
        return size;
    }

    /**
     * @return Current time.
     */
    public String getTime() {
        return time;
    }

    /**
     * @return Current bitrate.
     */
    public String getBitrate() {
        return bitrate;
    }

    /**
     * @return Current speed value.
     */
    public String getSpeed() {
        return speed;
    }

    @Override
    public String toString() {
        return "ProgressMessage{" +
                "frame='" + frame + '\'' +
                ", fps='" + fps + '\'' +
                ", qVal='" + qVal + '\'' +
                ", size='" + size + '\'' +
                ", time='" + time + '\'' +
                ", bitrate='" + bitrate + '\'' +
                ", speed='" + speed + '\'' +
                '}';
    }
}
