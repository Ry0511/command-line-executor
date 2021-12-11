package process.builder;

import java.io.IOException;
import java.util.Arrays;
import java.util.StringJoiner;

/**
 *
 */
public interface ProcessFactory {

    /**
     * Default factory implementation that supplies a process using a {@link
     * ProcessBuilder} redirecting the error stream and compiled with the
     * provided args.
     */
    ProcessFactory DEFAULT_FACTORY = args -> {
        final ProcessBuilder pb = new ProcessBuilder(args);
        pb.redirectErrorStream(true);

        final StringJoiner sj = new StringJoiner(" ");
        Arrays.stream(args).forEach(sj::add);
        System.out.println(sj);

        return pb.start();
    };

    /**
     * Construct a process object with the command of the target args.
     *
     * @param args The command args to execute.
     * @return Compiled process ready to be read.
     */
    Process create(final String... args) throws IOException;
}
