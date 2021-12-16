package process;

import ffmpeg.options.InputProcessArgument;
import process.builder.CommandBuilder;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * Argument builder wraps the construction and building of arguments that a
 * process can have. Providing simple chainable functions to create a single
 * argument functionally.
 *
 * @author -Ry
 * @version 0.2 Copyright: N/A
 */
public class ArgumentBuilder<T extends InputProcessArgument> implements CommandBuilder<String[]> {

    /**
     * List of arguments that will be compiled into the final command.
     */
    private final List<String> cArgs = new ArrayList<>();

    /**
     * The argument that is the target of this builder.
     */
    private final T cArgument;

    /**
     * Constructs the builder from the starting base argument.
     *
     * @param arg The argument that this builder, builds.
     */
    public ArgumentBuilder(final T arg) {
        this.cArgument = arg;
    }

    /**
     * Adds an argument that is to be compiled into the command this builder
     * creates.
     *
     * @param arg The argument to add.
     * @return The builder for chain calling.
     */
    public ArgumentBuilder<T> addArg(final String arg) {
        this.cArgs.add(arg);
        return this;
    }

    /**
     * Adds all args from the provided array into the argument list.
     *
     * @param args Args to add.
     * @return The builder for chain calling.
     */
    public ArgumentBuilder<T> addArgs(final String... args) {
        cArgs.addAll(Arrays.asList(args));
        return this;
    }

    /**
     * Compile the command builder into a single set of arguments of which can
     * be executed.
     *
     * @return Compiled args of this builder template.
     */
    public String[] build() {
        if (cArgs.size() > 0) {
            return cArgument.compile(cArgs.toArray(new String[0]));
        } else {
            return cArgument.compile();
        }
    }
}