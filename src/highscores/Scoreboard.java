package highscores;

import java.util.ArrayList;

import controlsUI.Button;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

public class Scoreboard {
	private double xInitialize, yInitialize;
	private HighScore toDelete;
	private boolean added;
	private boolean full;
	private Label title;
	private Image boardBG = new Image("/media/bgAlt.jpg");

	public boolean highScoreCheck(int v) {
		if (FileHandler.getScores().size() < 5) {
			return true;
		}
		for (HighScore s : FileHandler.getScores()) {
			if (v > s.getTopScore()) {
				return true;
			}
		}
		return false;
	}
	HBox initScoreboard(HighScore s) {
		HBox scoreboardPanel = new HBox();
		scoreboardPanel.getChildren().addAll(new Label(s.getPlayerName()), new Label(s.getTopScore() + ""), new Label(s.getLocalDate()),
				new Label(s.getLocalTime().substring(0, 5)));
		scoreboardPanel.setAlignment(Pos.CENTER);
		return scoreboardPanel;
	}
	HBox addTopScores(int v) {
		HBox scoresBox = new HBox();
		TextField name = new TextField();
		name.setMaxWidth(100);
		name.setMinWidth(100);
		Label displayScores = new Label(v + "");
		Button ok = new Button("OK");
		ok.setOnAction(e -> {
			HighScore s = new HighScore(v, name.getText());
			if (FileHandler.getScores().size() == 5) {
				FileHandler.removeLowest();
			}
			FileHandler.addScore(s);
			scoresBox.getChildren().clear();
			scoresBox.getChildren().addAll(new Label(s.getPlayerName()), new Label(s.getTopScore() + ""), new Label(s.getLocalDate()),
					new Label(s.getLocalTime().substring(0, 5)));
		});
		name.addEventFilter(KeyEvent.KEY_PRESSED, e -> {
			if(e.getCode().equals(KeyCode.ENTER)) {
				ok.fire();
			}
		});
		scoresBox.getChildren().addAll(name, displayScores, ok);
		scoresBox.setAlignment(Pos.CENTER);
		return scoresBox;
	}

	public void showScoreboard(Stage owner) {
		Stage s = new Stage();
		VBox root = new VBox(15);
			HBox titleRow = new HBox(15);
				title = new Label("HISCORES");
				title.setFont(Font.font(18));
				title.setTextFill(Color.WHITE);
			titleRow.getChildren().addAll(title);

			VBox scores = new VBox(10);
			added = false;
			ArrayList<HighScore> displayScores = FileHandler.getScores();
			displayScores.forEach(sc -> {
				scores.getChildren().add(initScoreboard(sc));
			});

			HBox buttons = new HBox(20);
				Button reset = new Button("Reset");
				reset.setOnAction(e -> {
					FileHandler.updateFile("");
					scores.getChildren().clear();
				});
				Button close = new Button("Close");
				close.setOnAction(e -> {
					s.close();
				});
			buttons.getChildren().addAll(close, reset);
			buttons.setAlignment(Pos.CENTER);
		root.setAlignment(Pos.CENTER);
		root.setBackground(new Background(new BackgroundImage(boardBG, null, null, null, null)));
		root.setBorder(new Border(
				new BorderStroke(Color.CHOCOLATE, BorderStrokeStyle.SOLID, new CornerRadii(0), new BorderWidths(2))));
		root.getChildren().addAll(titleRow, scores, buttons);
		root.setOnMousePressed(e -> {
			xInitialize = e.getSceneX();
			yInitialize = e.getSceneY();
		});
		root.setOnMouseDragged(e -> {
			s.setX(e.getScreenX() - xInitialize);
			s.setY(e.getScreenY() - yInitialize);
		});
		Scene scene = new Scene(root, 600, 400);
		scene.setFill(Color.TRANSPARENT);
		s.setScene(scene);
		s.initStyle(StageStyle.TRANSPARENT);
		s.initOwner(owner);
		s.initModality(Modality.WINDOW_MODAL);
		s.showAndWait();
	}

	public void showUpdatedScoreboard(Stage owner, int val) {
		Stage s = new Stage();
		VBox root = new VBox(15);
			HBox titleRow = new HBox(15);
				title = new Label("HISCORES");
				title.setFont(Font.font(18));
				title.setTextFill(Color.WHITE);
			titleRow.getChildren().addAll(title);

			VBox scores = new VBox(5);
			added = false;
			ArrayList<HighScore> scors = FileHandler.getScores();
			if (scors.isEmpty()) {
				scores.getChildren().add(addTopScores(val));
			} else {
				scors.forEach(sc -> {
					if (val > sc.getTopScore() && !added) {
						scores.getChildren().add(addTopScores(val));
						added = true;
					}
					scores.getChildren().add(initScoreboard(sc));
				});
				if (!added) {
					scores.getChildren().add(addTopScores(val));
				}
				if (scores.getChildren().size() > 5) {
					scores.getChildren().remove(5);
				}
			}
			HBox buttons = new HBox(20);
				Button reset = new Button("Reset");
				reset.setOnAction(e -> {
					FileHandler.updateFile("");
					scores.getChildren().clear();
				});
				Button close = new Button("Close");
				close.setOnAction(e -> {
					s.close();
				});
			buttons.getChildren().addAll(close, reset);
			buttons.setAlignment(Pos.CENTER);
		root.setAlignment(Pos.CENTER);
		root.setBackground(new Background(new BackgroundImage(boardBG, null, null, null, null)));
		root.setBorder(new Border(
				new BorderStroke(Color.CHOCOLATE, BorderStrokeStyle.SOLID, new CornerRadii(0), new BorderWidths(2))));
		root.getChildren().addAll(titleRow, scores, buttons);
		root.setOnMousePressed(e -> {
			xInitialize = e.getSceneX();
			yInitialize = e.getSceneY();
		});
		root.setOnMouseDragged(e -> {
			s.setX(e.getScreenX() - xInitialize);
			s.setY(e.getScreenY() - yInitialize);
		});
		Scene scene = new Scene(root, 500, 300);
		scene.setFill(Color.TRANSPARENT);
			s.setScene(scene);
			s.initStyle(StageStyle.TRANSPARENT);
			s.initOwner(owner);
			s.initModality(Modality.WINDOW_MODAL);
			s.showAndWait();
	}

	class Label extends javafx.scene.control.Label {
		public Label(String c) {
			super(c);
			setFont(Font.font(18));
			setTextFill(Color.WHITE);
			setStyle("-fx-font-weight: bold;");
			setMaxWidth(100);
			setMinWidth(100);
			setTranslateX(50);
		}
	}

	class TextField extends javafx.scene.control.TextField {
		public TextField() {
			setFont(Font.font(18));
			setMaxWidth(100);
			setMinWidth(100);
		}
	}

	public void init() {
		FileHandler.initializeSave();
	}

}
