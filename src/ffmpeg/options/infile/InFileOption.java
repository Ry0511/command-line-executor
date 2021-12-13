package ffmpeg.options.infile;

import ffmpeg.options.CompileUtils;
import ffmpeg.options.InputProcessArgument;

/**
 * Represents a series of options that can be used with the {@link
 * InFileBuilder} for building an FFMPEG command structure for the Input file
 * section.
 * <p>
 * This enum was designed around quick and simple implementations of options
 * giving no actual implementation or limitations of the possible values.
 *
 * @author -Ry
 * @version 0.2 Copyright: N/A
 */
public enum InFileOption implements InputProcessArgument {

    /**
     * Sets the video bit rate while encoding to the provided value at compile
     * time. If no value is specified then 1024 is assumed.
     */
    VIDEO_BIT_RATE("-b:v", true, "1024"),

    /**
     * Sets the audio bit rate while encoding to the provided value at compile
     * time. If no value is specified then 1024 is assumed.
     */
    AUDIO_BIT_RATE("-b:a", true, "1024"),

    /**
     * Sets the encoding preset to use default value is 'veryslow'.
     */
    PRESET("-preset", true, "veryslow"),

    /**
     * Sets the start position of the encoding; default value is 0.
     */
    START_FROM_POS("-ss", true, "0"),

    /**
     * Sets the duration after the {@link #START_FROM_POS} to encode. Default
     * value is {@link Integer#MAX_VALUE} which should capture all/most media
     * fully.
     */
    DURATION("-t", true, String.valueOf(Integer.MAX_VALUE)),

    /**
     * Sets the exact end time after the {@link #START_FROM_POS} to encode.
     * Default value is {@link Integer#MAX_VALUE}.
     */
    END_AT_POS("-to", true, String.valueOf(Integer.MAX_VALUE));

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
     * Generic enum constructor.
     *
     * @param identifier Invocation string.
     * @param requiresInput Input can be provided over the default value.
     * @param defaultValue The default value that this option compiles with.
     */
    InFileOption(final String identifier,
                 final boolean requiresInput,
                 final String defaultValue) {
        this.cIdentifier = identifier;
        this.cRequiresInput = requiresInput;
        this.cDefaultValue = defaultValue;
    }

    /**
     * Compile the process argument from the provided args string.
     *
     * @param args The arguments to compile into the specified argument.
     * @return Compiled argument ready to execute/compile further.
     */
    @Override
    public String compile(final String... args) {
        if (cRequiresInput) {

            return CompileUtils.compileWithValues(
                    cIdentifier,
                    args
            );

            // Arguments are invalid/not needed
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
        return CompileUtils.compileWithValues(cIdentifier, cDefaultValue);
    }
}
