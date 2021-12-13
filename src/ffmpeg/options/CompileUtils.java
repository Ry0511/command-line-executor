package ffmpeg.options;

import java.util.Arrays;
import java.util.Objects;
import java.util.StringJoiner;

/**
 * Static class which provides a bunch of utility methods for compiling
 * commands. Specifically designed for FFMPEG command building but some commands
 * or all may end up generic or applicable elsewhere.
 *
 * @author -Ry
 * @version 0.1 Copyright: N/A
 */
public final class CompileUtils {

    /**
     * The default argument delimiter used when compiling any number of
     * arguments. This is a single whitespace character.
     */
    public static final String DEFAULT_ARG_DELIMITER = " ";

    /**
     * Hide constructor.
     */
    private CompileUtils() {
    }

    /**
     * Compiles the identifier string with all arguments provided. All arguments
     * are compiled and joined using the {@link #DEFAULT_ARG_DELIMITER} if
     * control is required utilise the {@link #compileWithValuesDelimitedBy}
     * method.
     *
     * @param identifier The identifier of the option/target. This is assumed to
     * already have the identity character such as '-' or '!' depending on the
     * target for compilation.
     * @param args The arguments to compile with the identifier.
     * @return Compiled option.
     * @throws NullPointerException If any parameter is null.
     */
    public static String compileWithValues(final String identifier,
                                           final String... args) {
        return compileWithValuesDelimitedBy(
                identifier,
                DEFAULT_ARG_DELIMITER,
                args
        );
    }

    /**
     * Functionality is the same as {@link #compileWithValues(String,
     * String...)} however this method allows control over the delimiter used
     * when merging arguments.
     *
     * @param identifier The identifier of the option/target. This is assumed to
     * already have the identity character such as '-' or '!' depending on the
     * target for compilation.
     * @param args The arguments to compile with the identifier.
     * @return Compiled option.
     * @throws NullPointerException If any parameter is null.
     */
    public static String compileWithValuesDelimitedBy(final String identifier,
                                                      final String delimiter,
                                                      final String... args) {
        Objects.requireNonNull(identifier);
        Objects.requireNonNull(delimiter);
        Objects.requireNonNull(args);

        final StringJoiner sj = new StringJoiner(delimiter);
        sj.add(identifier);
        Arrays.stream(args).forEach(sj::add);
        
        return sj.toString();
    }
}
