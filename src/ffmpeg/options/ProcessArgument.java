package ffmpeg.options;

/**
 * Represents all process arguments generically. Where some argument is some
 * string result which should be compiled into a single 'Argument' of which the
 * default {@link process.builder.ProcessFactory} can produce some process of.
 * <p>
 * Most process arguments are designed to be chained together that being one
 * argument can be compiled, then another, then another, then finally they can
 * be merged together to produce an argument set for some process. So one should
 * avoid unnecessary formatting such as leading or trailing spaces; never use
 * new lines either.
 *
 * @author -Ry
 * @version 0.1 Copyright: N/A
 */
public interface ProcessArgument {

    /**
     * Compiles the argument using its default value. Or no value if one is not
     * required.
     *
     * @return Compiled argument ready to be executed/compiled further.
     */
    String[] compile();

    /**
     * Convenience method for building an array of any type.
     *
     * @param args The arguments to be encapsulated in an array.
     * @param <T> The type of the elements held in the array.
     * @return The provided elements wrapped in an Array of the specified type.
     */
    @SafeVarargs
    static <T> T[] buildArray(final T... args) {
        return args;
    }
}
