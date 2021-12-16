package ffmpeg.options.infile;

import ffmpeg.options.CompileUtils;
import process.ArgumentBuilder;
import process.builder.CommandBuilder;

import java.nio.file.Path;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * Builder object designed to simplify command chaining for building FFMPEG
 * infile options. Should also aid in the error detection due to the functional
 * style of builder objects/templates.
 *
 * @author -Ry
 * @version 0.2 Copyright: N/A
 */
public class InFileBuilder implements CommandBuilder<String[]> {

    /**
     * FFMPEG infile flag identifier. Represents the Infile section of the
     * FFMPEG command structure: ffmpeg FLAGS [INFILE OPTIONS] [OUTFILE
     * OPTIONS].
     */
    public static final String IN_FILE_IDENTIFIER = "-i";

    /**
     * List of options that the infile will be compiled with.
     */
    private final List<ArgumentBuilder<?>> cArguments = new ArrayList<>();

    /**
     * The target input file.
     */
    private Path cInFile;

    /**
     * Set the input file to the provided file.
     *
     * @param inFile The new input file.
     * @return The builder for chain calling.
     */
    public InFileBuilder setInFile(final Path inFile) {
        this.cInFile = inFile;
        return this;
    }

    /**
     * Add a generic option builder.
     *
     * @param option The option to add.
     * @return The builder for chain calling.
     */
    public InFileBuilder addOption(final ArgumentBuilder<?> option) {
        this.cArguments.add(option);
        return this;
    }

    /**
     * Add an option from the {@link InFileOption} set which is to be compiled
     * using the provided args.
     *
     * @param option The option to compile.
     * @param args The args to compile with.
     * @return The builder for chain calling.
     */
    public InFileBuilder addOption(final InFileOption option,
                                   final String... args) {
        this.cArguments.add(new ArgumentBuilder<>(option).addArgs(args));
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
        command.add(IN_FILE_IDENTIFIER);
        command.add(CompileUtils.quote(cInFile.toAbsolutePath().toString()));
        cArguments.forEach(i -> command.addAll(Arrays.asList(i.build())));

        return command.toArray(new String[0]);
    }
}
