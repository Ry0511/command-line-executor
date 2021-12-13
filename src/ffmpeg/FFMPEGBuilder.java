package ffmpeg;

import ffmpeg.options.global.flags.Flag;
import ffmpeg.options.infile.InFileBuilder;
import process.builder.CommandBuilder;

import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;

/**
 *
 */
final class FFMPEGBuilder implements CommandBuilder {

    /**
     * Global flags to apply to the command. These are assumed to be fully setup
     * and require no extra information.
     */
    private final List<Flag> cGlobalFlags = new ArrayList<>();

    /**
     * @param flag
     * @return
     */
    public FFMPEGBuilder addFlag(final Flag flag) {

        if (!this.cGlobalFlags.contains(flag)) {
            this.cGlobalFlags.add(flag);
        }

        return this;
    }

    /**
     * @param flag
     * @return
     */
    public FFMPEGBuilder removeFlag(final Flag flag) {
        this.cGlobalFlags.remove(flag);

        return this;
    }

    /**
     *
     * @param builder
     * @return
     */
    public FFMPEGBuilder setInFileBuilder(final InFileBuilder builder) {
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

        // Compile flags
        // Infile
        // Infile options


        return new String[0];
    }

    // todo outfile options
}
