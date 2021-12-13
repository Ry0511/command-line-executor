package ffmpeg.error;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Wraps all error messages that the program will use. I only ever plan on this
 * being in English (UK) but if ever need be for the localisation then this
 * would be nice to have.
 *
 * @author -Ry
 * @version 0.1 Copyright: N/A
 */
public enum FFMPEGError {

    /**
     *
     */
    ERR_FLAG_ARGS_NOT_REQUIRED("[FLAG_ERROR] The flag '%s'/'%s' does not "
            + "require any input but was given the input: '%s'"),

    /**
     *
     */
    ERR_FLAG_NUM_ARGS_INVALID("[FLAG_ERROR] The flag '%s'/'%s' could not be "
            + "compiled expected '%s' number of arguments but was given '%s'.");

    /**
     * The number of arguments that the message requires. This is only the upper
     * limit less can be supplied.
     */
    private final int cNumArgs;

    /**
     * The base error message string.
     */
    private final String cBaseStr;

    /**
     * Constructs the Error from the base string. Infers the number of arguments
     * from the passed string.
     *
     * @param base The base error message.
     */
    FFMPEGError(final String base) {
        this.cBaseStr = base;

        // Count %s occurrence
        final Matcher m = Pattern.compile("%s").matcher(base);
        int count = 0;
        while (m.find()) {
            ++count;
        }
        this.cNumArgs = count;
    }

    /**
     * Compiles the error message with the provided args.
     *
     * @param args The args to place into the error message.
     * @return Compiled/Constructed error message.
     * @throws IllegalArgumentException If the number of arguments exceeds the
     *                                  expected number of arguments.
     */
    public String compile(final String... args) {

        if (args.length > cNumArgs) {

            String s = cBaseStr;
            for (String arg : args) {
                s = String.format(s, arg);
            }

            return s;

        } else {
            throw new IllegalArgumentException();
        }
    }

    /**
     * @return The number of arguments that the error message expects as a
     * maximum.
     */
    public int getNumArgs() {
        return cNumArgs;
    }
}
