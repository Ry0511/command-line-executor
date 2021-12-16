package ffmpeg;

import ffmpeg.options.global.flags.Flag;
import ffmpeg.options.infile.InFileBuilder;
import ffmpeg.options.infile.InFileOption;
import process.builder.TrackedProcessExecutor;
import process.builder.event.handler.ProcessHandler;

import java.io.IOException;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 *
 */
public class FFMPEG {

    /**
     * Target ffmpeg variable.
     */
    private final String cFfmpegPath;

    /**
     * Executor which will handle executing ffmpeg commands.
     */
    private FFMPEGExecutor cExecutor = new FFMPEGExecutor();

    /**
     * Construct an FFMPEG instance from an absolute path to some ffmpeg
     * executable.
     *
     * @param path Path pointing to some ffmpeg executable.
     */
    public FFMPEG(final String path) {
        this.cFfmpegPath = path;
    }

    /**
     * Constructs a ffmpeg.FFMPEG instance from the base 'ffmpeg' path
     * variable.
     */
    public FFMPEG() {
        this.cFfmpegPath = "ffmpeg";
    }

    /**
     * Compile and execute the provided ffmpeg command builder.
     *
     * @param builder The builder to compile.
     * @return Started, possibly finished task.
     */
    public Process execute(final FFMPEGBuilder builder) throws IOException {
        System.out.println(
                "[EXECUTING] -> "
                + Arrays.deepToString(builder.build())
        );

        final List<String> args =
                new ArrayList<>(Arrays.asList(builder.build()));
        args.add(0, cFfmpegPath);
        return this.cExecutor.start(args.toArray(new String[0]));
    }

    /**
     * @return Executor that will run all ffmpeg commands and provide events.
     */
    public FFMPEGExecutor getExecutor() {
        return cExecutor;
    }

    public static void main(String[] args) throws IOException {

        final FFMPEG ffmpeg = new FFMPEG();

        ffmpeg.getExecutor().addProgressListener((e) -> {
            System.out.printf(
                    "[[G, %s]::[L, %s]::[H, %s]] -> %s%n",
                    e.getEventGroup(),
                    e.getFiredListener(),
                    e.getTargetHandler(),
                    e.getTarget()
            );
        });

        ffmpeg.getExecutor().getProcessHandler().addRegMsgListener((e) -> {
            System.out.println(e.getTarget().getMessage());
        });

        ffmpeg.execute(new FFMPEGBuilder()
                .setInFileBuilder(new InFileBuilder().setInFile(Paths.get("test/test.mp4"))
                        .addOption(InFileOption.VIDEO_BIT_RATE, "700k"))
                .addFlag(Flag.ALWAYS_OVERWRITE)
        );
    }
}
