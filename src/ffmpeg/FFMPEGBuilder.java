package ffmpeg;

import ffmpeg.options.global.flags.Flag;
import ffmpeg.options.infile.InFileBuilder;
import process.ArgumentBuilder;
import process.builder.CommandBuilder;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.StringJoiner;

/**
 *
 */
final class FFMPEGBuilder implements CommandBuilder<String[]> {

    /**
     * Global flags to apply to the command. These are assumed to be fully setup
     * and require no extra information.
     */
    private final List<ArgumentBuilder<?>> cGlobalFlags = new ArrayList<>();

    /**
     *
     */
    private InFileBuilder cInFileBuilder;

    /**
     * @param flag
     * @return
     */
    public FFMPEGBuilder addFlag(final Flag flag,
                                 final String... args) {
        this.cGlobalFlags.add(new ArgumentBuilder<>(flag).addArgs(args));
        return this;
    }

    /**
     * @param builder
     * @return
     */
    public FFMPEGBuilder setInFileBuilder(final InFileBuilder builder) {
        this.cInFileBuilder = builder;
        return this;
    }

    /**
     * Compile the command builder into a single set of arguments of which can
     * be executed.
     *
     * @return Compiled args of this builder template.
     */
    @Override
    public String[] build() {

        final List<String> command = new ArrayList<>();

        // Had some issues using Collection API this works fine but the
        // functional way of doing it doesn't (probably forgetting some side
        // effects)
        for (ArgumentBuilder<?> flag : cGlobalFlags) {
            final String[] builtFlag = flag.build();
            final List<String> flags =
                    new ArrayList<>(Arrays.asList(builtFlag));
            command.addAll(flags);
        }

        Collections.addAll(command, cInFileBuilder.build());
        command.add("output.mp4");

        return command.toArray(new String[0]);
    }

    // todo outfile options
}
