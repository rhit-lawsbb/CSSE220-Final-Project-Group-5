package model;

import java.awt.Image;
import java.awt.Toolkit;
import javax.swing.ImageIcon;
import javax.swing.Timer;

public class WinVideoPlayer {
	private static Image videoImage;
	private static final int VIDEO_DURATION_MS = 8000;

	public static void loadVideo() {
		if (videoImage != null) {
			videoImage.flush();
		}
		Image fresh = Toolkit.getDefaultToolkit().createImage("win_cutscene.gif");
		videoImage = new ImageIcon(fresh).getImage();
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
