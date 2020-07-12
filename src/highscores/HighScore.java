//This class records high scores by implementing the Comparable class
package highscores;

import java.time.LocalDate;
import java.time.LocalTime;

public class HighScore implements Comparable<HighScore> {
	private int topScore;
	private String playerName;
	private LocalDate localDate;
	private LocalTime localTime;

	//contructor for Scoreboard class
	public HighScore(int s, String n) {
		topScore = s;
		playerName = n;
		localDate = LocalDate.now();
		localTime = LocalTime.now();
	}
	//constructor for FileHandler class
		public HighScore(String n, int s, LocalDate d, LocalTime t) {
		topScore = s;
		playerName = n;
		localDate = d;
		localTime = t;
	}
//Score info getters
	public int getTopScore() {
		return topScore;
	}
	public String getPlayerName() {
		return playerName;
	}
	public String getLocalDate() {
		return localDate.toString();
	}
	public String getLocalTime() {
		return localTime.toString();
	}

	//returns true if all score info has been properly entered. Used for another class.
	public boolean scoreInput(HighScore s) {
		return (playerName.equals(s.playerName) && topScore == s.topScore && localDate.equals(s.localDate) && localTime.getHour() == s.localTime.getHour()
				&& localTime.getMinute() == s.localTime.getMinute());
	}
	//creates a string of the high score and related info
	public String saveScore() {
		String scoreToSave = "";
		scoreToSave += "#score#" + playerName + "#info#" + topScore + "#info#" + localDate.toString() + "#info#"
				+ localTime.toString().substring(0, 5);
		return scoreToSave;
	}
	//comparing scores with highscores
	@Override
	public int compareTo(HighScore comparedScore) {
		if (topScore < comparedScore.topScore) {
			return 1;
		} else if (topScore > comparedScore.topScore) {
			return -1;
		} else {
			if (localTime.isBefore(comparedScore.localTime)) {
				return -1;
			} else {
				return 1;
			}
		}
	}
}
