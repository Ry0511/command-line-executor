package ffmpeg.options.global.flags;

import ffmpeg.error.FFMPEGError;
import ffmpeg.options.InputProcessArgument;
import ffmpeg.options.ProcessArgument;

/**
 * Represents all known FFMPEG Flags these are commands that can be applied
 * before the infile. Such as '-y' which always overwrites and '-n' which never
 * overwrites.
 * <p>
 * You can see all these options here:
 * <a href="https://gist.github.com/tayvano/6e2d456a9897f55025e25035478a3a50">
 * Tayvano-gist</a> or by executing {@literal ffmpeg -h full}.
 *
 * @author -Ry
 * @version 0.1 Copyright: N/A
 */
public enum Flag implements ProcessArgument, InputProcessArgument {

    /**
     * States whether the log level
     */
    LOG_LEVEL("-loglevel", true, LogLevel.VERBOSE.compile()),

    /**
     *
     */
    REPORT("-report", false, null),

    /**
     *
     */
    ALWAYS_OVERWRITE("-y", false, null),

    /**
     *
     */
    NEVER_OVERWRITE("-n", false, null);

    /**
     * Literal flag value used to invoke this flag.
     */
    private final String cFlagLiteral;

    /**
     * Does the flag require extra information/optional input.
     */
    private final boolean cIsInputRequired;

    /**
     * The default input for the Flag. If the flag does not require input then
     * an Empty string or {@code null} should be set here.
     */
    private final String cDefaultValue;

    /**
     * Constructs the flag from its literal identifier string; data requirement
     * state, and default value.
     *
     * @param flagLiteral Identifier string such as '-h'.
     * @param isInputRequired State if Input is required/possible.
     * @param defaultValue The default value to apply to the Flag.
     */
    Flag(final String flagLiteral,
         final boolean isInputRequired,
         final String defaultValue) {
        cFlagLiteral = flagLiteral;
        cIsInputRequired = isInputRequired;
        this.cDefaultValue = defaultValue;
    }

    /**
     * @return {@code true} if the Flag requires extra information.
     */
    public boolean isInputRequired() {
        return cIsInputRequired;
    }

    /**
     * Constructs the flag using its default value.
     *
     * @return Flag ready to be built into a command.
     */
    public String compile() {
        // No input required
        if (cDefaultValue == null || cDefaultValue.equals("")) {
            return cFlagLiteral;

            // Default value
        } else {
            return cFlagLiteral
                    + " "
                    + cDefaultValue;
        }
    }

    /**
     * Constructs the flag using the provided value.
     *
     * @param value The value to compile this flag with.
     * @return Compiled flag with the provided value.
     * @throws UnsupportedOperationException If the flag does not accept input.
     */
    public String compile(final String value) {

        if (cIsInputRequired) {
            throw new UnsupportedOperationException(
                    FFMPEGError.ERR_FLAG_ARGS_NOT_REQUIRED.compile(
                            name(), cFlagLiteral, value
                    ));

        } else {
            return cFlagLiteral
                    + " "
                    + cDefaultValue;
        }
    }

    /**
     * Compile the process argument from the provided args string.
     *
     * @param args The arguments to compile into the specified argument.
     * @return Compiled argument ready to execute/compile further.
     * @throws UnsupportedOperationException If input is not required for this
     *                                       flag.
     * @throws IllegalArgumentException      If the number of arguments does not
     *                                       meet the expected '1'.
     */
    @Override
    public String compile(final String... args) {

        if (args.length == 1) {

            if (cIsInputRequired) {
                throw new UnsupportedOperationException(
                        FFMPEGError.ERR_FLAG_ARGS_NOT_REQUIRED.compile(
                                name(),
                                cFlagLiteral,
                                args[0]
                        ));

            } else {
                return cFlagLiteral
                        + " "
                        + cDefaultValue;
            }

            // Incorrect arg count
        } else {
            final int expectedArgCount = cIsInputRequired ? 1 : 0;
            throw new IllegalArgumentException(
                    FFMPEGError.ERR_FLAG_NUM_ARGS_INVALID.compile(
                            name(),
                            cFlagLiteral,
                            String.valueOf(expectedArgCount),
                            String.valueOf(args.length)
                    ));
        }
    }
}
