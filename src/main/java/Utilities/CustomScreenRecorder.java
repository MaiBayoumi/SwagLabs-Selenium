package Utilities;

import org.monte.media.Format;
import org.monte.media.math.Rational;
import org.monte.screenrecorder.ScreenRecorder;

import java.awt.*;
import java.io.File;
import java.io.IOException;

import static org.monte.media.VideoFormatKeys.*;

public class CustomScreenRecorder extends ScreenRecorder {

    private final String fileName;
    private final File movieFolder;

    public CustomScreenRecorder(GraphicsConfiguration cfg, File movieFolder, String fileName) throws IOException, AWTException {
        super(cfg,
                Toolkit.getDefaultToolkit().getScreenSize() != null ? new Rectangle(Toolkit.getDefaultToolkit().getScreenSize()) : new Rectangle(800, 600),
                new Format(MediaTypeKey, MediaType.FILE, MimeTypeKey, MIME_AVI),
                new Format(MediaTypeKey, MediaType.VIDEO, EncodingKey, ENCODING_AVI_TECHSMITH_SCREEN_CAPTURE,
                        CompressorNameKey, ENCODING_AVI_TECHSMITH_SCREEN_CAPTURE,
                        DepthKey, 24, FrameRateKey, Rational.valueOf(15),
                        QualityKey, 1.0f, KeyFrameIntervalKey, 15 * 60),
                new Format(MediaTypeKey, MediaType.VIDEO, EncodingKey, "black", FrameRateKey, Rational.valueOf(30)),
                null,
                movieFolder
        );
        this.fileName = fileName;
        this.movieFolder = movieFolder;
    }

    @Override
    protected File createMovieFile(Format fileFormat) {
        return new File(movieFolder, fileName + ".avi");
    }
}
