package BugJumpApplication;

import java.awt.Dimension;
import java.awt.Image;
import java.awt.Toolkit;
import java.net.URL;

import javax.swing.ImageIcon;

public class MainApplication extends GraphicsApplication {
	private Dimension dimension;

	private AudioPlayer audio;
	private boolean hasMainGameStarted;

	public void init() {
		dimension = Toolkit.getDefaultToolkit().getScreenSize();
		setSize((int) dimension.getWidth(), (int) dimension.getHeight());
	}

	public void run() {
		setupInteractions();
		switchToMenu();
		audio = AudioPlayer.getInstance();
		audio.playSound("music", "MENU_LEVEL_SELECT_BGM_MASTER.mp3", true);
		hasMainGameStarted = false;

	}

	private void startMusic() {
		if (hasMainGameStarted) {
			audio.stopSound("music", "LEVEL_BGM_MASTER.mp3");
			audio.playSound("music", "MENU_LEVEL_SELECT_BGM_MASTER.mp3", true);
			hasMainGameStarted = false;
		}
	}

	public void switchToMenu() {
		startMusic();
		switchToScreen(new MainMenu(this));
	}

	public void switchToLevelSelector() {
		startMusic();
		switchToScreen(new LevelCreator(this));
//		switchToScreen(new LevelSelector(this));

	}

	public void switchToGame(int level) {
		if (!hasMainGameStarted) {
			audio.stopSound("music", "MENU_LEVEL_SELECT_BGM_MASTER.mp3");
			audio.playSound("music", "LEVEL_BGM_MASTER.mp3", true);
			hasMainGameStarted = true;
		}
		switchToScreen(new MainGame(this, level));
	}
	
	public Image getImage(String s) {
		String newS = "/" + s;
		URL url = getClass().getResource(newS);
		
		if(url == null) {
			System.out.println("url == null, return with string path");
			return new ImageIcon(s).getImage();
		}
		System.out.println("url found, return with url");
		return new ImageIcon(url).getImage();
	}
	
//	public File getFile(String s) {
//		String preString = "src/main/resources/";
//		URL url = getClass().getResource("/" + s);
//		if(url == null) {
//			System.out.println("couldn't find file");
//			return new File(preString + s);
//		}
//		try {
//			System.out.println("found file");
//			return new File(url.toURI());
//		} catch (URISyntaxException e) {
//			// TODO Auto-generated catch block
//			System.out.println("returned null");
//			return null;
//		}
//
//	}

	public static void main(String[] args) {
		new MainApplication().start();
	}

}
