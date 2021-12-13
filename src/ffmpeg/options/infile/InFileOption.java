package ffmpeg.options.infile;

import ffmpeg.options.CompileUtils;
import ffmpeg.options.InputProcessArgument;

/**
 * Represents a series of options that can be used with the {@link
 * InFileBuilder} for building an FFMPEG command structure for the Input file
 * section.
 *
 * @author -Ry
 * @version 0.1 Copyright: N/A
 */
public enum InFileOption implements InputProcessArgument {
    VIDEO_BIT_RATE("-b:v", true, "1024", 1, 1),
    AUDIO_BIT_RATE("-b:a", true, "1024", 1, 1);

    /**
     * The identifier string of this option this is what is used to invoke the
     * option such as -b:v to set the video bit rate.
     */
    private final String cIdentifier;

    /**
     * States whether input is or is not required for the option.
     */
    private final boolean cRequiresInput;

    /**
     * The default value for the option.
     */
    private final String cDefaultValue;

    /**
     * The minimum acceptable number of arguments that the {@link
     * #compile(String...)} will allow.
     */
    private final int cMinArgs;

    /**
     * The maximum acceptable number of arguments that the {@link
     * #compile(String...)} will accept.
     */
    private final int cMaxArgs;

    /**
     * Generic enum constructor.
     *
     * @param identifier Invocation string.
     * @param requiresInput Input can be provided over the default value.
     * @param defaultValue The default value that this option compiles with.
     */
    InFileOption(final String identifier,
                 final boolean requiresInput,
                 final String defaultValue,
                 final int minArgs,
                 final int maxArgs) {
        this.cIdentifier = identifier;
        this.cRequiresInput = requiresInput;
        this.cDefaultValue = defaultValue;
        this.cMinArgs = minArgs;
        this.cMaxArgs = maxArgs;
    }

    /**
     * Compile the process argument from the provided args string.
     *
     * @param args The arguments to compile into the specified argument.
     * @return Compiled argument ready to execute/compile further.
     */
    @Override
    public String compile(final String... args) {
        if ((args.length >= cMinArgs) && (args.length < cMaxArgs)) {

            return CompileUtils.compileWithValues(
                    cIdentifier,
                    args
            );

            // Arguments are invalid
        } else {
            //todo error message
            throw new IllegalArgumentException();
        }
    }

    /**
     * Compiles the argument using its default value. Or no value if one is not
     * required.
     *
     * @return Compiled argument ready to be executed/compiled further.
     */
    @Override
    public String compile() {
        return null;
    }
}
