//this class saves TopScore to a local save file using the Java File Writer classes
package highscores;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.Writer;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.Collections;

import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;

public class FileHandler {
	final static int NOT_FOUND = 0, FOUND = 1;
	private static final String Folder = "\\Treetris HighScores", File = "\\Scores.tet";
	private static String Location = "", myDocuments = "", Data;

	//save initialize method. Works for Windows currently, haven't tested on Mac or Linux yet.
	static int initializeSave() {
		int saveFileStatus = 0;
		try {
			Process p = Runtime.getRuntime().exec("reg query \"HKCU\\Software\\Microsoft\\"
					+ "Windows\\CurrentVersion\\Explorer\\Shell Folders\" /v personal");
			p.waitFor();

			InputStream in = p.getInputStream();
			byte[] b = new byte[in.available()];
			in.read(b);
			in.close();

			myDocuments = new String(b);
			myDocuments = myDocuments.split("\\s\\s+")[4];

		} catch (Throwable t) {
			t.printStackTrace();
		}

		Location = myDocuments + Folder + File;
		File saveFile = new File(myDocuments + Folder);
		if (saveFile.exists() && saveFile.isDirectory()) {
			File newSaveFile = new File(Location);
			if (newSaveFile.exists() && !newSaveFile.isDirectory()) {
				saveFileStatus = FOUND;
			} else {
				try {
					Writer writer = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(Location), "utf-8"));
					writer.close();
					saveFileStatus = NOT_FOUND;
				} catch (IOException e1) {
					e1.printStackTrace();
				}
			}
		} else {
			new File(myDocuments + Folder).mkdir();
			try {
				Writer writer = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(Location), "utf-8"));
				writer.close();
			} catch (IOException e1) {
				e1.printStackTrace();
			}
			saveFileStatus = NOT_FOUND;
		}
		return saveFileStatus;
	}
//gets top scores and adds them to an array list
	public static ArrayList<HighScore> getScores() {
		ArrayList<HighScore> scoreList = new ArrayList<HighScore>();
		String data = readSaveFile();
		String[] scores = data.split("#score#");
		for (String score : scores) {
			String[] infos = score.split("#info#");
			try {
				String name = infos[0];
				int value = Integer.parseInt(infos[1]);
				String[] dates = infos[2].split("-");
				LocalDate date = LocalDate.of(Integer.parseInt(dates[0]), Integer.parseInt(dates[1]),
						Integer.parseInt(dates[2]));
				String[] times = infos[3].split(":");
				LocalTime time = LocalTime.of(Integer.parseInt(times[0]), Integer.parseInt(times[1]));
				HighScore Score = new HighScore(name, value, date, time);
				scoreList.add(Score);
			} catch (ArrayIndexOutOfBoundsException ex) {
			}
		}
		Collections.sort(scoreList);
		return scoreList;
	}

	public static String readSaveFile() {
		File temp = new File(Location);
		try (BufferedReader br = new BufferedReader(new InputStreamReader(new FileInputStream(temp), "UTF-8"))) {
			StringBuilder sb = new StringBuilder();
			String line = br.readLine();
			while (line != null) {
				sb.append(line);
				sb.append("\n");
				line = br.readLine();
			}
			String res = sb.toString().substring(0, sb.toString().length() - 1);
			return res;
		} catch (FileNotFoundException | StringIndexOutOfBoundsException e) {
			return "";
		} catch (IOException e1) {
			return "";
		}
	}
//Removes the lowest top score from the Save File list if full
	public static void removeLowest() {
		Data = "";
		ArrayList<HighScore> scores = getScores();
		scores.forEach(sc -> {
			if (scores.indexOf(sc) != 4) {
				Data += sc.saveScore();
			}
		});
		updateFile(Data);
	}

	public static void remove(HighScore s) {
		Data = "";
		getScores().forEach(sc -> {
			if (!sc.scoreInput(s)) {
				Data += sc.saveScore();
			}
		});
		updateFile(Data);
	}

	public static void updateFile(String data) {
		try {
			Writer writer = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(Location), "utf-8"));
			writer.write(data);
			writer.close();
		} catch (IOException ex) {
			ex.printStackTrace();
			Alert alert = new Alert(AlertType.ERROR);
			alert.setContentText("\nError while trying to save the file.");
			alert.setHeaderText("Save Error");
			alert.showAndWait();
		}
	}

	public static void addScore(HighScore s) {
		Data = readSaveFile();
		updateFile(Data + s.saveScore());
	}
}
