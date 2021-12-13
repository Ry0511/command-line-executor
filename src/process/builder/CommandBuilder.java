package process.builder;

/**
 * Builder template for which the {@link TrackedProcessExecutor} can compile a
 * command from of which can be executed once being built. Designed with no
 * scope or limitation in mind only that of the compile method; offers no
 * attributes or debug data only
 */
public interface CommandBuilder {

    /**
     * Compile the command builder into a single set of arguments of which can
     * be executed.
     *
     * @return Compiled args of this builder template.
     */
    String[] build();
}
