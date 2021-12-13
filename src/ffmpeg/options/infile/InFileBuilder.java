package ffmpeg.options.infile;

import ffmpeg.options.ProcessArgument;
import process.builder.CommandBuilder;

import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;

/**
 * Builder object designed to simplify command chaining for building FFMPEG
 * infile options. Should also aid in the error detection due to the functional
 * style of builder objects/templates.
 *
 * @author -Ry
 * @version Copyright: N/A
 */
public class InFileBuilder implements CommandBuilder {

    /**
     *
     */
    private List<ProcessArgument> options = new ArrayList<>();

    /**
     *
     */
    private Path cInFile;

    /**
     *
     * @param inFile
     * @return
     */
    public InFileBuilder setInFile(final Path inFile) {
        this.cInFile = inFile;
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
        return new String[0];
    }
}
