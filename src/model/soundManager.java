package model;



public class soundManager {
	public void playSound(String fileName) {
	    new Thread(() -> {
	        try {
	            // This searches your project for the file
	            java.net.URL url = getClass().getResource("/sounds/" + fileName);
	            if (url == null) {
	                System.err.println("Could not find: " + fileName);
	                return;
	            }
	            javax.sound.sampled.AudioInputStream audioIn = javax.sound.sampled.AudioSystem.getAudioInputStream(url);
	            javax.sound.sampled.Clip clip = javax.sound.sampled.AudioSystem.getClip();
	            clip.open(audioIn);
	            clip.start();
	        } catch (Exception e) {
	            e.printStackTrace();
	        }
	    }).start();
	}
}
