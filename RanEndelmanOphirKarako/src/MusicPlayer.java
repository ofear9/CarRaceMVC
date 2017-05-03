import java.io.File;
import java.util.Random;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;

/**
 * This class handle all the music player in the game
 * 
 * @author Ophir Karako and Ran Endelman.
 *
 */

public class MusicPlayer {
	String[] songs = { "song1.mp3", "song2.mp3", "song3.mp3" };

	public MusicPlayer() {
		super();
	}

	/**
	 * This method choose a random song every race
	 * 
	 * @return Media player load with random song
	 */
	public MediaPlayer getSong() {
		Random r = new Random();
		int Result = r.nextInt(3);
		String bip = songs[Result];
		Media hit = new Media(new File(bip).toURI().toString());
		MediaPlayer mediaPlayer1 = new MediaPlayer(hit);
		return mediaPlayer1;
	}

	/**
	 * 
	 * @return mediaPlyer load with the Start song of the race .
	 */
	public MediaPlayer getStartSound() {
		String bip = "countdown.mp3";
		Media hit = new Media(new File(bip).toURI().toString());
		MediaPlayer mediaPlayer1 = new MediaPlayer(hit);
		return mediaPlayer1;

	}

	/**
	 * 
	 * @return media player load with the claps sound in the end of the game .
	 */
	public MediaPlayer getClaps() {
		String bip = "claps.mp3";
		Media hit = new Media(new File(bip).toURI().toString());
		MediaPlayer mediaPlayer1 = new MediaPlayer(hit);
		return mediaPlayer1;

	}

}
