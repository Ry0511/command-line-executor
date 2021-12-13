package ffmpeg.options;

/**
 * Represents when some process argument requires a value pair for example the
 * '-h' does not require any input whereas '-loglevel' requires some 'value'
 * such as '-loglevel verbose'.
 */
public interface InputProcessArgument {

    /**
     * Compile the process argument from the provided args string.
     *
     * @param args The arguments to compile into the specified argument.
     * @return Compiled argument ready to execute/compile further.
     */
    String compile(String... args);
}

