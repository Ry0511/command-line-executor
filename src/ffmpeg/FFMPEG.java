package ffmpeg;

import ffmpeg.options.flags.LogLevel;
import process.builder.TrackedProcessExecutor;
import process.builder.event.handler.RegexHandler;

import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.regex.Pattern;

/**
 *
 */
public class FFMPEG {

    /**
     * Target ffmpeg variable.
     */
    private final String ffmpegPath;

    /**
     * Construct an FFMPEG instance from an absolute path to some ffmpeg
     * executable.
     *
     * @param path Path pointing to some ffmpeg executable.
     */
    public FFMPEG(final String path) {
        this.ffmpegPath = path;
    }

    /**
     * Constructs a ffmpeg.FFMPEG instance from the base 'ffmpeg' path variable.
     */
    public FFMPEG() {
        this.ffmpegPath = "ffmpeg";
    }

    public static void main(String[] args) throws IOException {
        final TrackedProcessExecutor process
                = new TrackedProcessExecutor();

        RegexHandler handler = new RegexHandler((e) -> {
            System.out.println(
                    "[FINISHED] - NATURALLY? : "
                    + e.wasNaturalEnd()
            );
        });

        handler.addRegMessageHandler(
                Pattern.compile(".*"),
                (e) -> {
                    System.out.println(e.getMessage());
                }
        );

        handler.setIsCleanseWhitespace(true);

        process.setProcessHandler(handler);

        final Path path = Paths.get("test/test.mp4");

        process.start(
                "ffmpeg",
                "-loglevel",
                LogLevel.compile(LogLevel.LEVEL, LogLevel.VERBOSE),
                "-y",
                "-i",
                path.toAbsolutePath().toString(),
                "-b:v",
                "700k",
                path.getParent().toAbsolutePath()
                        + "\\"
                        + "output.mp4"
        );
    }
}
