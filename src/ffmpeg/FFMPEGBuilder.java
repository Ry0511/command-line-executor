package ffmpeg;

import ffmpeg.options.flags.Flag;

import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;

/**
 *
 */
final class FFMPEGBuilder {

    private Path cInFile;
    private String cOutFile;
    private final List<Flag> cGlobalFlags = new ArrayList<>();
    private FFMPEG cFfmpegExecutor;

    /**
     *
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
     *
     * @param flag
     * @return
     */
    public FFMPEGBuilder removeFlag(final Flag flag) {
        this.cGlobalFlags.remove(flag);

        return this;
    }

    /**
     *
     * @param inFile
     * @return
     */
    public FFMPEGBuilder setInFile(final Path inFile) {
        this.cInFile = inFile;
        return this;
    }

    // todo in file options

    /**
     *
     * @param outFilePath
     * @return
     */
    public FFMPEGBuilder setOutFile(final String outFilePath) {
        this.cOutFile = outFilePath;
        return this;
    }

    // todo outfile options
}
