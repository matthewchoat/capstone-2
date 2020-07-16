//This class contains all UI elements. See additional comments for more information on each method.
package game;
//Java imports for Open SDK 11, Corretto
import java.awt.Toolkit;
//Open JavaFX for SDK 11 imports
import components.*;
import javafx.animation.AnimationTimer;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.ButtonType;
import javafx.scene.control.CheckBox;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.input.KeyCombination;
import javafx.scene.input.KeyEvent;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.TextAlignment;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundImage;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;

//custom package imports
import controlsUI.Button;
import components.Piece;
import highscores.Scoreboard;
import tetrominos.AbstractShape;

import static javafx.scene.paint.Color.*;
public class GameUI extends Application {
	//initializing all required game variables
	private static final int SCREEN_WIDTH = (int) Toolkit.getDefaultToolkit().getScreenSize().getWidth(),
			SCREEN_HEIGHT = (int) Toolkit.getDefaultToolkit().getScreenSize().getHeight(),
			SCENE_HEIGHT = 5 * SCREEN_HEIGHT / 6, SCENE_WIDTH = 4 * SCENE_HEIGHT / 3, FIELD_HEIGHT = SCENE_HEIGHT - 50,
			FIELD_WIDTH = FIELD_HEIGHT / 2;
	private double xInitialize, yInitialize;
	private double gameSpeed = 0.6;
	private boolean soundWorks = false;
	private int scores = 0, level = 0;
	private Scoreboard board;
	private GamePanel gamePanel;
	private Stage stageDisplay;
	private Image windowBG = new Image("/media/pineoak.jpg");
	private Color fallen = SADDLEBROWN;
	private Label points, instructions, title;
	private AudioController sound;
	private AbstractShape absShape;
	private long last = 0;
	private int tetriminoNum = 0;
	private Color colors[] = new Color[5];
	private UpNextPanel upNext;
	private GridPosition gridPos = new GridPosition(4, -4);
	private Button playBtn, startBtn;
	private AnimationTimer timer;
	private boolean paused = true;
	private FrontendLogic logicUI = new FrontendLogic(this);

	//starting the game thread, rendering all elements of the UI.
	@Override
	public void start(Stage stage) {
		//initializing buttons, board, sound, and Piece colors
		Button.initBtn(SCREEN_HEIGHT);
		stageDisplay = stage;
		board = new Scoreboard();
		board.init();
		sound = new AudioController(this.getHostServices().getDocumentBase());
		soundWorks = sound.audioWorks();
		//initializing piece size and creating game panel
		Piece.initPieceSize(FIELD_WIDTH / 10);
		gamePanel = new GamePanel(Piece.getSIZE()*10, Piece.getSIZE()*20, SCENE_HEIGHT);
		//creating main HorizontalBox for main UI and creating accompanying Scene
		HBox mainUI = new HBox(SCREEN_WIDTH * 0.03);
		mainUI.setAlignment(Pos.CENTER);
		Scene scene = new Scene(mainUI, SCENE_WIDTH, SCENE_HEIGHT);
		//creating all HBoxes and VBoxes that will be children of "mainUI" HBox
		createBoxes(mainUI);
		//ready shapes for game start
		logicUI.readyShapes(colors);
		//Generating and adding a shape to the game panel
		genAddShape();
		//starting the game timer to handle animations
		initAnimationTimer();
		//enabling keyboard controls
		keyboardControls(scene, mainUI);
		//enabling mouse controls
		mouseControls(mainUI);
		//initializing the stage display made up of a scene and mainUI HBox with children boxes
		initStageDisplay(scene, mainUI);
	}

	//Initializing the JavaFX game timer to handle animation timing and pause functions
	private void initAnimationTimer(){
		timer = new AnimationTimer() {
			double gameTime = 0;
			//Overriding the "AnimationTimer" JavaFX class to support pausing the game
			@Override
			public void handle(long current) {
				long dt = current - last;
				//pause game if window loses focus
				gamePanel.requestFocus();
				if (!stageDisplay.isFocused()) {
					logicUI.pause();
				}
				gameTime += dt;
				if (gameTime / 1000000000 >= gameSpeed) {
					gameTime = 0;
					if (gamePanel.isReady()) {
						sound.move();
						logicUI.fallLogic();
					}
				}
				last = current;
			}
		};}

	//Generating and adding a shape to the game panel
	private void genAddShape(){
		absShape = logicUI.generate();
		gamePanel.addShapeToPanel(absShape);}

	//Mouse click on main UI to return x,y position
	private void mouseControls(HBox mainUI){
		mainUI.setOnMousePressed(e -> {
			xInitialize = e.getSceneX();
			yInitialize = e.getSceneY();
		});
		//Click-and-drag to move UI window
		mainUI.setOnMouseDragged(e -> {
			stageDisplay.setX(e.getScreenX() - xInitialize);
			stageDisplay.setY(e.getScreenY() - yInitialize);
		});}

	//Switch case for keyboard controls
	private void keyboardControls(Scene scene, HBox mainUI){
		scene.addEventFilter(KeyEvent.KEY_PRESSED, e -> {
			switch (e.getCode()) {
				case RIGHT:
					if (gamePanel.canMoveRight(absShape) && !paused) {
						absShape.setPosition(absShape.getPosition().right());
					}
					break;
				case LEFT:
					if (gamePanel.canMoveLeft(absShape) && !paused) {
						absShape.setPosition(absShape.getPosition().left());
					}
					break;
				case DOWN:
					if (gamePanel.isReady()) {
						sound.move();
						logicUI.fallLogic();
					}
					break;
				case SPACE:
					if (gamePanel.isReady()) {
						sound.move();
						logicUI.hardDropLogic();
					}
					break;
				case ENTER:
					if(e.isAltDown()) {
						if(stageDisplay.isFullScreen()) {
							mainUI.setBackground(new Background(new BackgroundImage(windowBG, null, null, null, null)));
						}else {
							mainUI.setBackground(new Background(new BackgroundImage(windowBG, null, null, null, null)));
						}
						stageDisplay.setFullScreen(!stageDisplay.isFullScreen());
						gamePanel.initGamePanel((int) scene.getHeight());
						e.consume();
						break;
					}else {

					}
				case UP:
					if (gamePanel.canRotate(absShape) && !paused) {
						sound.spin();
						absShape.Rotate();
					}
					break;
				case ESCAPE:
					if (!paused) {
						logicUI.pause();
					} else {
						logicUI.resumeGame();
					}
					break;
				default:
					break;
			}
		});}

	//Setting backgrounds for StageDisplay, Scene, and mainUI
	private void initStageDisplay(Scene scene, HBox mainUI){
		stageDisplay.initStyle(StageStyle.TRANSPARENT);
		scene.setFill(TRANSPARENT);
		mainUI.setBackground(new Background(new BackgroundImage(windowBG, null, null, null, null)));
		//stylesheet link for custom checkbox styling
		scene.getStylesheets().addAll("game/checkbox.css");
		//Setting messages for Full-screen mode, and title
		stageDisplay.setFullScreenExitKeyCombination(KeyCombination.keyCombination("CTRL+ALT+f"));
		stageDisplay.setFullScreenExitHint("ALT + Enter to exit full screen mode");
		stageDisplay.setTitle("Treetris");
		//setting the scene to be shown in the stageDisplay
		stageDisplay.setScene(scene);
		stageDisplay.show();}

	public void createBoxes(HBox mainUI){
		//creating new Vertical Box to include various UI elements
		VBox elementsUI = new VBox(SCREEN_HEIGHT * 0.02);

		//creating new Horizontal box for my Next tetrimino preview panel
		HBox upNextBox = new HBox(2);
		upNextBox.setAlignment(Pos.CENTER);
		upNext = new UpNextPanel(Piece.getSIZE() * 5, Piece.getSIZE() * 5);
		upNextBox.getChildren().addAll(upNext);

		//creating new Horizontal box for the Game Title
		HBox titlePanel = new HBox(0);
		titlePanel.setAlignment(Pos.CENTER);
		title = new Label("TREETRIS");
		title.setTextAlignment(TextAlignment.CENTER);
		title.setFont(Font.font(SCREEN_WIDTH * 0.01600));
		title.setTextFill(FORESTGREEN);
		title.setStyle("-fx-font-weight: bold;");
		titlePanel.getChildren().addAll(title);

		//creating new Horizontal Box for score, level, and highscore button display
		HBox scoreBoard = new HBox(5);
		scoreBoard.setAlignment(Pos.CENTER);
		points = new Label("SCORE\n" + scores +"\nLEVEL\n" + level);
		points.setTextAlignment(TextAlignment.CENTER);
		points.setFont(Font.font(SCREEN_WIDTH * 0.01464));
		points.setTextFill(WHITE);
		Button showScores = new Button("HISCORES");
		showScores.setOnAction(e -> {
			board.showScoreboard(stageDisplay);
		});
		scoreBoard.getChildren().addAll(points, showScores);

		//creating new Horizontal box for my game controls/instructions
		HBox about = new HBox(4);
		about.setAlignment(Pos.CENTER);
		instructions = new Label("Clear lines and don't let the wood stack too high! \n Click 'Start' and move the falling shapes with \u2190 \u2191 \u2193 \u2192 & [SPACE].\nPress [Alt] + [Enter] to toggle fullscreen mode.");
		instructions.setTextAlignment(TextAlignment.CENTER);
		instructions.setFont(Font.font(SCREEN_WIDTH * 0.00845));
		instructions.setTextFill(BLACK);
		about.getChildren().addAll(instructions);

		//creating Vertical Box for my UI buttons
		VBox buttonBox = new VBox(5);
		buttonBox.setAlignment(Pos.CENTER);
		playBtn = new Button("Resume");
		playBtn.setDisable(true);
		playBtn.setOnAction(e -> {
			if (!paused) {
				logicUI.pause();
			} else {
				logicUI.resumeGame();
			}
		});
		startBtn = new Button("Start");
		startBtn.setOnAction(e -> {
			startBtn.setText("New Game");
			playBtn.setDisable(false);
			gamePanel.reset();
			absShape = logicUI.generate();
			gamePanel.addShapeToPanel(absShape);
			scores = 0;
			points.setText("SCORE\n" + scores +"\nLEVEL\n" + level);
			logicUI.resumeGame();
		});
		Button exitBtn = new Button("Exit");
		exitBtn.setOnAction(e -> {
			logicUI.exit();
		});
		buttonBox.getChildren().addAll(playBtn, startBtn, exitBtn);

		//Creating checkboxes for audio controls
		CheckBox mute = new CheckBox("Mute");
		mute.setTextFill(WHITE);
		CheckBox soundEnabled = new CheckBox("Sounds");
		soundEnabled.setTextFill(WHITE);
		CheckBox musicEnabled = new CheckBox("Music");
		musicEnabled.setTextFill(WHITE);

		//Creating Vertical Boxes for audio control labels
		VBox audioControls = new VBox(0);
		VBox toggleMuteLabel = new VBox();
		VBox toggleMusicLabel = new VBox();
		VBox toggleSoundLabel = new VBox();
		toggleSoundLabel.setAlignment(Pos.CENTER);

		//defining conditions for music/sound/mute checkboxes
		musicEnabled.setOnAction(e -> {
			if (soundWorks) {
				if (!musicEnabled.isSelected() && !soundEnabled.isSelected()) {
					mute.setSelected(true);
				} else {
					mute.setSelected(false);
				}
				sound.setMusicEnabled(musicEnabled.isSelected());
			}
		});
		soundEnabled.setOnAction(e -> {
			if (soundWorks) {
				if (!musicEnabled.isSelected() && !soundEnabled.isSelected()) {
					mute.setSelected(true);
				} else {
					mute.setSelected(false);
				}
				sound.setSoundEnabled(soundEnabled.isSelected());
			}
		});
		mute.setOnAction(e -> {
			if (soundWorks) {
				musicEnabled.setSelected(!mute.isSelected());
				sound.setMusicEnabled(!mute.isSelected());
				soundEnabled.setSelected(!mute.isSelected());
				sound.setSoundEnabled(!mute.isSelected());
			}
		});
		if (!soundWorks) {
			mute.setDisable(true);
			soundEnabled.setDisable(true);
			musicEnabled.setDisable(true);
		}
		//Creating HBox to contain audio checkbox VBoxes and labels
		HBox audioCheckBoxes = new HBox(4);
		audioCheckBoxes.setAlignment(Pos.CENTER);
		audioCheckBoxes.getChildren().addAll(mute, soundEnabled, musicEnabled);
		audioControls.getChildren().addAll(toggleMusicLabel,toggleMuteLabel, toggleSoundLabel, audioCheckBoxes);
		audioControls.setAlignment(Pos.CENTER);

		//adding all sub-Boxes to main UI box
		elementsUI.getChildren().addAll(titlePanel, about,upNextBox, scoreBoard, buttonBox, audioControls);
		elementsUI.setAlignment(Pos.CENTER);
		mainUI.getChildren().addAll(gamePanel, elementsUI);}

	//UI for updating score, level, and triggering level change transition effect
	public void levelChange(int s) {
		scores += s;
		if (scores >= 100) {  //LEVEL 0
			if (scores < 200) {  //LEVEL 1
				gameSpeed = 0.55;
				level = 1;
				gamePanel.invertPanelColors(true);
			} else if (scores < 300) { //LEVEL 2
				gameSpeed = 0.50;
				level = 2;
				gamePanel.invertPanelColors(false);
			} else if (scores < 400) { //LEVEL 3
				gameSpeed = 0.45;
				level = 3;
				gamePanel.invertPanelColors(true);
			} else if (scores < 500){ //LEVEL 4
				gameSpeed = 0.40;
				level = 4;
				gamePanel.invertPanelColors(false);
			} else if (scores < 650){ //LEVEL 5
				gameSpeed = 0.35;
				level = 5;
				gamePanel.invertPanelColors(true);
			} else if (scores < 800){ //LEVEL 6
				gameSpeed = 0.27;
				level = 6;
				gamePanel.invertPanelColors(false);
			} else if (scores < 950){ //LEVEL 7
				gameSpeed = 0.17;
				level = 7;
				gamePanel.invertPanelColors(true);
			} else if (scores < 1200){ //LEVEL 8
				gameSpeed = 0.12;
				level = 8;
				gamePanel.invertPanelColors(false);
			}else  {                  //LEVEL 9
				gameSpeed = 0.09;
				level = 9;
				gamePanel.invertPanelColors(true);
			}
		}
		points.setText("SCORE\n" + scores + "\nLEVEL\n" + level);
	}

	//game over UI for highscore game and non-highscore game
	protected void gameOverUI() {
		gameSpeed = 0.6;
		playBtn.setDisable(true);
		startBtn.setText("Start");
		sound.gameOverSound();
		logicUI.pauseNoSound();
		if (board.highScoreCheck(scores)) {
			Alert alert = new Alert(AlertType.NONE);
			alert.initOwner(stageDisplay);
			alert.initModality(Modality.APPLICATION_MODAL);
			alert.setContentText(
					"Game Over! Final Score: " + scores + " at level " + level + "\n Save your score to the leaderboard?");
			alert.getButtonTypes().setAll(ButtonType.YES, ButtonType.NO);

			//Game Over UI if HighScore
			Runnable run = new Runnable() {
				@Override
				public void run() {
					alert.showAndWait();
					if (alert.getResult().equals(ButtonType.YES)) {
						board.showUpdatedScoreboard(stageDisplay, scores);
					}
					gamePanel.reset();
					absShape = logicUI.generate();
					gamePanel.addShapeToPanel(absShape);
					scores = 0;
					level = 0;
					points.setText("SCORE\n" + scores +"\nLEVEL\n" + level);
				}
			};
			Platform.runLater(run);
			//Game Over UI if not HighScore
		} else {
			Alert alert = new Alert(AlertType.INFORMATION);
			alert.initOwner(stageDisplay);
			alert.initModality(Modality.APPLICATION_MODAL);
			alert.setContentText("Game Over! Final Score: " + scores + " at level " + level + "\n Try again for a highscore!");
			alert.setOnHidden(e -> {
				gamePanel.reset();
				absShape = logicUI.generate();
				gamePanel.addShapeToPanel(absShape);
				scores = 0;
				level = 0;
				points.setText("SCORE\n" + scores +"\nLEVEL\n" + level);
			});
			Platform.runLater(alert::showAndWait);
		}
	}

	//Getters and settings to facilitate Class encapsulation
	public GamePanel getGamePanel() {
		return gamePanel;
	}
	public Color getFallen() {
		return fallen;
	}
	public Stage getStageDisplay() {
		return stageDisplay;
	}
	public AudioController getSound() {
		return sound;
	}
	public int getScores() {
		return scores;
	}
	public AbstractShape getAbsShape() {
		return absShape;
	}
	public long getLast() {
		return last;
	}
	public int getTetriminoNum() {
		return tetriminoNum;
	}
	public Color[] getColors() {
		return colors;
	}
	public UpNextPanel getUpNext() {
		return upNext;
	}
	public GridPosition getGridPos() {
		return gridPos;
	}
	public Button getPlayBtn() {
		return playBtn;
	}
	public Button getStartBtn() {
		return startBtn;
	}
	public AnimationTimer getTimer() {
		return timer;
	}
	public boolean isPaused() {
		return paused;
	}
	public void setLast(long last) {
		this.last = last;
	}
	public void setPaused(boolean paused) {
		this.paused = paused;
	}
	public void setAbsShape(AbstractShape absShape) {
		this.absShape = absShape;
	}
	public void setTetriminoNum(int tetriminoNum) {
		this.tetriminoNum = tetriminoNum;
	}
	public void incrementTetriminoNum() {
		this.tetriminoNum++;
	}
}