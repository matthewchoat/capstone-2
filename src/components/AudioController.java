//This class imports sound files and adds them to the audio stream.
package components;

import java.io.*;
import java.net.URL;

import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.sound.sampled.FloatControl;
import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.UnsupportedAudioFileException;

import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;

public class AudioController {
	int fp;
	double globalVol = 100, soundvolume = 100, musicVol = 100;
	boolean musicEnabled = true, soundEnabled = true, paused=true;
	AudioInputStream line1, line2, line3, line4, fallen, play, pause, gameOver, spin, move, gameMusic, menuMusic,SelectedMusic;
	Clip lines1, lines2, lines3, lines4, fallens, plays, pauses, loses, spins, moves, musics;
	Thread audioThread;


	boolean worked = false;
	String menuMusicSound;
	String gameMusicSound;
	public AudioController(String dbase) {
		//attempting to import all sound files
		String documentBase = "/"; /// FileSystems.getDefault().getPath("").toAbsolutePath();
		String lineOne = (documentBase + "media/LineClear1.wav");
		String lineTwo = (documentBase + "media/LineClear2.wav");
		String lineThree = (documentBase + "media/LineClear3.wav");
		String lineFour = (documentBase + "media/LineClear4.wav");
		String fallenSound = (documentBase + "media/fallen.wav");
		String playSound = (documentBase + "media/play.wav");
		String pauseSound = (documentBase + "media/pause.wav");
		String gameOverSound = (documentBase + "media/gameOver.wav");
		String spinSound = (documentBase + "media/spin.wav");
		String moveSound = (documentBase + "media/move.wav");
		this.gameMusicSound = (documentBase + "media/gameMusic.wav");
		this.menuMusicSound = (documentBase + "media/menuMusic.wav");

		try {
			InputStream is = getClass().getResource(lineOne).openStream();
			line1 = AudioSystem.getAudioInputStream(new BufferedInputStream(is));
			line2 = AudioSystem.getAudioInputStream(new BufferedInputStream(getClass().getResource(lineTwo).openStream()));
			line3 = AudioSystem.getAudioInputStream(new BufferedInputStream(getClass().getResource(lineThree).openStream()));
			line4 = AudioSystem.getAudioInputStream(new BufferedInputStream(getClass().getResource(lineFour).openStream()));
			fallen = AudioSystem.getAudioInputStream(new BufferedInputStream(getClass().getResource(fallenSound).openStream()));
			play = AudioSystem.getAudioInputStream(new BufferedInputStream(getClass().getResource(playSound).openStream()));
			pause = AudioSystem.getAudioInputStream(new BufferedInputStream(getClass().getResource(pauseSound).openStream()));
			gameOver = AudioSystem.getAudioInputStream(new BufferedInputStream(getClass().getResource(gameOverSound).openStream()));
			spin = AudioSystem.getAudioInputStream(new BufferedInputStream(getClass().getResource(spinSound).openStream()));
			move = AudioSystem.getAudioInputStream(new BufferedInputStream(getClass().getResource(moveSound).openStream()));
			gameMusic = AudioSystem.getAudioInputStream(new BufferedInputStream(getClass().getResource(gameMusicSound).openStream()));
			menuMusic = AudioSystem.getAudioInputStream(new BufferedInputStream(getClass().getResource(menuMusicSound).openStream()));
			musics = AudioSystem.getClip();
			SelectedMusic = menuMusic;
			musics.open(SelectedMusic);
			lines1 = AudioSystem.getClip();
			lines1.open(line1);
			lines2 = AudioSystem.getClip();
			lines2.open(line2);
			lines3 = AudioSystem.getClip();
			lines3.open(line3);
			lines4 = AudioSystem.getClip();
			lines4.open(line4);
			fallens = AudioSystem.getClip();
			fallens.open(fallen);
			plays = AudioSystem.getClip();
			plays.open(play);
			pauses = AudioSystem.getClip();
			pauses.open(pause);
			loses = AudioSystem.getClip();
			loses.open(gameOver);
			spins = AudioSystem.getClip();
			spins.open(spin);
			moves = AudioSystem.getClip();
			moves.open(move);
			worked = true;
			//error message in case audio fails to load
		} catch (Exception ex) {
			Alert alert = new Alert(AlertType.INFORMATION);
			alert.setContentText("Sound will not work because of an Error while initializing the soundSystem. Uninstalling and Reinstalling the program might solve this problem");
			alert.showAndWait();
			ex.printStackTrace();
		}
	}
	public boolean audioWorks() {
		return worked;
	}
	public void line1() {
		if (soundEnabled) {
			plays(lines1);
		}
	}
	public void line2() {
		if (soundEnabled) {
			plays(lines2);
		}
	}
	public void line3() {
		if (soundEnabled) {
			plays(lines3);
		}
	}
	public void line4() {
		if (soundEnabled) {
			plays(lines4);
		}
	}
	public void pause() {
		if (soundEnabled) {
			plays(pauses);
		}
	}
	public void play() {
		if (soundEnabled) {
			plays(plays);
		}
	}
	public void fallen() {
		if (soundEnabled) {
			plays(fallens);
		}
	}
	public void gameOverSound() {
		if (soundEnabled) {
			plays(loses);
		}
	}
	public void spin() {
		if (soundEnabled) {
			plays(spins);
		}
	}
	public void move() {
		if (soundEnabled) {
			plays(moves);
		}
	}

	public boolean isSoundEnabled() {
		return soundEnabled;
	}

	public boolean isMusicEnabled() {
		return musicEnabled;
	}

	public void setSoundEnabled(boolean b) {
		soundEnabled = b;
	}
	public void setMusicEnabled(boolean b) {
		musicEnabled = b;
		if(musics!=null) {
			setMusicVol(musicVol);
			if(b) {
				musics.setFramePosition(fp);
				musics.loop(Clip.LOOP_CONTINUOUSLY);
			}else {
				fp = musics.getFramePosition();
				musics.stop();
			}
		}
	}
	public void setVolume(double v) {
		soundvolume = v;
	}
	public void setMusicVol(double v){
		int tar = 0;
		if(paused) {
			tar = 120;
		}else {
			tar = 100;
		}
		if(musics!=null) {
			int fp = musics.getFramePosition();
			musics.stop();
			musicVol = v;
			FloatControl volumes = (FloatControl) musics.getControl(FloatControl.Type.MASTER_GAIN);
			float range = volumes.getMaximum() - volumes.getMinimum();
			float gain = (float) ((range*(globalVol/100)* musicVol / tar) + volumes.getMinimum());
			volumes.setValue(gain);
			musics.setFramePosition(fp);
			if(musicEnabled)
			musics.loop(Clip.LOOP_CONTINUOUSLY);
		}
	}
	public void plays(Clip clip) {
		if(clip!=null
				&&!(clip==moves&&musics.isRunning())
				) {
			float target = 0;
			if(clip== fallens) {
				target = (float) ((globalVol/100) * (soundvolume / 100));
			}else {
				target = (float) ((globalVol/100) * (soundvolume / 120));
			}
			FloatControl volumes = (FloatControl) clip.getControl(FloatControl.Type.MASTER_GAIN);
			float range = volumes.getMaximum() - volumes.getMinimum();
			float gain = (float) (range*target) + volumes.getMinimum();
			volumes.setValue(gain);
			clip.setFramePosition(0);
			clip.start();
		}
	}

	public void setMenuMusic() {
		paused = true;
		musics.close();
		try {
			musics = AudioSystem.getClip();
			menuMusic = AudioSystem.getAudioInputStream(new BufferedInputStream(getClass().getResource(menuMusicSound).openStream()));
			musics.open(menuMusic);
		} catch (LineUnavailableException | IOException | UnsupportedAudioFileException e) {
			e.printStackTrace();
		}
		if(musicEnabled) {
			setMusicVol(musicVol);
			musics.start();
		}
	}
	public void setGameMusic() {
		paused = false;
		musics.close();
		try {
			musics = AudioSystem.getClip();
			gameMusic = AudioSystem.getAudioInputStream(new BufferedInputStream(getClass().getResource(gameMusicSound).openStream()));
			musics.open(gameMusic);
		} catch (LineUnavailableException | IOException | UnsupportedAudioFileException e) {
			e.printStackTrace();
		}
		if(musicEnabled) {
			setMusicVol(musicVol);
			musics.start();
		}
	}
}
