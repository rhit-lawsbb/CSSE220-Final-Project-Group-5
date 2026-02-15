package model;

import java.awt.Image;
import javax.swing.ImageIcon;
import javax.swing.Timer;

public class WinVideoPlayer {
	private static Image videoImage;
	private static final int VIDEO_DURATION_MS = 8000;

	public static void loadVideo() {
		ImageIcon icon = new ImageIcon("win_cutscene.gif");
		videoImage = icon.getImage();
	}

	public static Image getVideoImage() {
		return videoImage;
	}

	public static void playVideo(Runnable onVideoFinished) {
		loadVideo();

		Timer timer = new Timer(VIDEO_DURATION_MS, e -> {
			((Timer) e.getSource()).stop();
			onVideoFinished.run();
		});
		timer.setRepeats(false);
		timer.start();
	}
}
