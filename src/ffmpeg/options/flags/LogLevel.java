package ffmpeg.options.flags;

import java.lang.reflect.MalformedParametersException;
import java.util.Arrays;
import java.util.Locale;
import java.util.StringJoiner;

/**
 * All possible log levels that can be used with the {@link Flag#LOG_LEVEL}
 * flag.
 *
 * @author -Ry
 * @version 0.1 Copyright: N/A
 */
public enum LogLevel {

    /**
     * Flag of the log level.
     * <p>
     * Indicates whether repeated log output should not be compressed to the
     * first line.
     */
    REPEAT(true),

    /**
     * Flag of the log level.
     * <p>
     * Appends the level of each message in the console. That being for all
     * messages is it an Error message, Warning message or an INFO message.
     * Messages would look a bit like {@literal [INFO] Some information here}.
     *
     * @see Flag#LOG_LEVEL
     */
    LEVEL(true),

    /**
     * Shows nothing at all.
     */
    QUIET(false),

    /**
     * Only fatal errors are shown. Mostly unused in ffmpeg.
     */
    PANIC(false),

    /**
     * Only shows fatal errors.
     */
    FATAL(false),

    /**
     * Shows all errors even those that can be recovered from.
     */
    ERROR(false),

    /**
     * Everything that ffmpeg dislikes is logged.
     */
    WARNING(false),

    /**
     * Informative messages are shown during processing alongside with warnings
     * and errors. This is the default value for ffmpeg.
     */
    INFO(false),

    /**
     * Same as {@link #INFO} however more detail is provided. This is the
     * default used for the {@link Flag#LOG_LEVEL}.
     */
    VERBOSE(false),

    /**
     * Show everything, literally.
     */
    DEBUG(false),

    /**
     * Shows program stack trace.
     */
    TRACE(false);

    /**
     * Specifies if this is a flag or a level value.
     */
    private final boolean cIsLogFlag;

    /**
     * @param isLogFlag States if this entry is a flag to the log level or a log
     * level value itself.
     */
    LogLevel(final boolean isLogFlag) {
        this.cIsLogFlag = isLogFlag;
    }

    /**
     * @return Log level name.
     */
    public String compile() {
        // I am not sure if it is safe to use the ROOT locale.
        return name().toLowerCase(Locale.UK);
    }

    /**
     * Compiles all the levels into a single Log Level flag which can be
     * compiled. Per the current FFMPEG build in 11/12/2021 (DD/MM/YYYY) number
     * of arguments in the array should not exceed 3; nor be less than 1.
     *
     * @param levels The levels to compile.
     * @return Formatted flag for an FFMPEG command.
     * @throws IllegalArgumentException     If the number of elements in the
     *                                      provided array does not meet the
     *                                      above specified.
     * @throws MalformedParametersException If the number of flags or values
     *                                      exceed the acceptable numbers
     *                                      specified above.
     */
    public static String compile(final LogLevel... levels) {
        final int maxArgs = 3;
        final int minArgs = 1;

        if (levels.length > maxArgs || levels.length < minArgs) {
            throw new IllegalArgumentException();

        } else {

            final int maxFlags = 2;
            int flagCount = 0;
            final int maxValues = 1;
            int valueCount = 0;

            // Tally flags + count occurrences
            for (LogLevel level : levels) {
                if (level.cIsLogFlag) {
                    ++flagCount;
                } else {
                    ++valueCount;
                }
            }

            // Too many flags
            if (flagCount > maxFlags) {
                throw new MalformedParametersException();
            }

            // Too many values
            if (valueCount > maxValues) {
                throw new MalformedParametersException();
            }

            // Compile to string
            final StringJoiner sj = new StringJoiner("+");
            Arrays.stream(levels).forEach(i -> sj.add(i.compile()));
            return sj.toString();
        }
    }
}
