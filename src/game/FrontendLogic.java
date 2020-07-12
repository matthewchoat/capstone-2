//To facilitate SOLID code design principles and practice Interface Segregation, I've chosen to move all of my logic related to frontend game features from my main GameUI class, into this class.
//Be aware that logic that belongs strictly to things like Tetromino methods (besides the methods contained for randomly generating Tetromino objects) and the Game Panel exist in their respective packages/classes.
package game;

import components.GamePanel;
import controlsUI.Button;
import javafx.animation.AnimationTimer;
import javafx.application.Platform;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.paint.Color;
import javafx.stage.Modality;
import javafx.stage.Stage;
import components.AudioController;
import tetrominos.*;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static javafx.scene.paint.Color.*;

public class FrontendLogic {
  private static String randTetrimino = "";

  //initialize tetromino Colors to be assigned randomly
  static void initColors(Color[] colors) {
    colors[0] = FIREBRICK;
    colors[1] = OLIVEDRAB;
    colors[2] = FORESTGREEN;
    colors[3] = GOLDENROD;
    colors[4] = DARKVIOLET;
  }

  //Stream for shuffling the randomly generated tetriminos and returning as a mapped list. This streams up to 50 random tetriminos at once before the game starts. Reducing piece generation load times during gameplay.
  private static <T> Stream<T> stream(T[] elements) {
    return Stream.iterate(Arrays.asList(elements), list -> {
      Collections.shuffle(list = new ArrayList<>(list));
      return list;
    }).skip(1).flatMap(List::stream);
  }

  //splitting the shuffled list into 50 individual tetrominos
  static void splitShuffled() {
    randTetrimino = FrontendLogic.stream("0123456".split("")).limit(50).collect(Collectors.joining());
  }
  //retrieving a random tetromino from the shuffled list
  private static int getPiece(int piecePosition) {
    return Integer.parseInt(randTetrimino.charAt(piecePosition) + "");
  }

  //Pulls from the randomly generated 50 AbstractShapes and assigns them to a Tetromino type.
  static AbstractShape generate(GameUI gameUI) {
    int type = getPiece(gameUI.tetriminoNum);
    gameUI.tetriminoNum++;
    if (gameUI.tetriminoNum > 49) {
      gameUI.tetriminoNum = 0;
    }
    AbstractShape genPiece;
    switch (type) {
      case 0:
        genPiece = new L(GameUI.init);
        break;
      case 1:
        genPiece = new J(GameUI.init);
        break;
      case 2:
        genPiece = new I(GameUI.init);
        break;
      case 3:
        genPiece = new O(GameUI.init);
        break;
      case 4:
        genPiece = new S(GameUI.init);
        break;
      case 5:
        genPiece = new Z(GameUI.init);
        break;
      case 6:
        genPiece = new T(GameUI.init);
        break;
      default:
        genPiece = new L(GameUI.init);
    }
    genPiece.setFill(GameUI.colors[(int) (Math.random() * 5)]);
    GameUI.upNext.setShape(getNext(gameUI, gameUI.tetriminoNum).moveToUpNext());
    return genPiece;
  }

  //Chooses the NextUp Tetromino Shape from the 50 randomly generated AbstractShapes.
    private static AbstractShape getNext(GameUI gameUI, int tetriminoNum) {
      int nextPieceNo;
      if (tetriminoNum > 49) { //this relates to the stream random function
        splitShuffled();
        nextPieceNo = 0;
      } else {
        nextPieceNo = tetriminoNum;
      }
      int type = getPiece(nextPieceNo);
      AbstractShape genPiece;
      switch (type) {
      case 0:
        genPiece = new L(GameUI.init);
        break;
      case 1:
        genPiece = new J(GameUI.init);
        break;
      case 2:
        genPiece = new I(GameUI.init);
        break;
      case 3:
        genPiece = new O(GameUI.init);
        break;
      case 4:
        genPiece = new S(GameUI.init);
        break;
      case 5:
        genPiece = new Z(GameUI.init);
        break;
      case 6:
        genPiece = new T(GameUI.init);
        break;
      default:
        genPiece = new L(GameUI.init);
      }
      genPiece.setFill(GameUI.colors[(int) (Math.random() * 5)]);
      return genPiece;
    }

  //move pieces down, check if pieces would spill over the game panel (game over condition) and check for removable lines
  static void fallLogic(GameUI gameUI, Color fallen, GamePanel gamePanel, boolean paused, AudioController sound) {
    if (gamePanel.canFall(gameUI.absShape) && !paused) {
      gameUI.absShape.setPosition(gameUI.absShape.getPosition().down());
    } else if (!gamePanel.canFall(gameUI.absShape) && !paused) {
      sound.fallen();
      gameUI.absShape.setFill(fallen);
      gameUI.absShape = generate(gameUI);
      if (gamePanel.isGameOver())
        gameUI.gameOverUI();
      else
        gamePanel.add(gameUI.absShape);

      gamePanel.checkRemovableLines(sound, gameUI);
    }
  }

  //Snap piece down to the bottom row instantly, check if pieces would spill over the game panel (game over condition) and check for removable lines
  static void hardDropLogic(GameUI gameUI, Color fallen, GamePanel gamePanel, boolean paused, AudioController sound) {
    if (gamePanel.canFall(gameUI.absShape) && !paused) {
      gameUI.absShape.setPosition(gameUI.absShape.getPosition().down());

      for (int i = 0; (gamePanel.canFall(gameUI.absShape)); i++) {
        gameUI.absShape.setPosition(gameUI.absShape.getPosition().down()); }

    } else if (!gamePanel.canFall(gameUI.absShape) && !paused) {
      sound.fallen();
      gameUI.absShape.setFill(fallen);
      gameUI.absShape = generate(gameUI);
      if (gamePanel.isGameOver())
        gameUI.gameOverUI();
      else
        gamePanel.add(gameUI.absShape);

      gamePanel.checkRemovableLines(sound, gameUI);
    }
  }

  //Pause game with sound
  static void pause(GameUI gameUI, Button playBtn, AudioController sound, AnimationTimer timer) {
    playBtn.setText("Play");
    timer.stop();
    sound.pause();
    gameUI.paused = true;
    sound.setMenuMusic();
  }

  //Pause game without sound
  static void pauseNoSound(GameUI gameUI, Button playBtn, AudioController sound, AnimationTimer timer) {
    playBtn.setText("Play");
    timer.stop();
    gameUI.paused = true;
    sound.setMenuMusic();
  }

  //Resumes game after pause
  static void resumeGame(GameUI gameUI) {
    gameUI.playBtn.setDisable(false);
    gameUI.playBtn.setText("Pause");
    gameUI.last = System.nanoTime();
    gameUI.timer.start();
    gameUI.sound.play();
    gameUI.paused = false;
    gameUI.sound.setGameMusic();
  }

  // Prompts the user to quit the program
  static void exit(Stage stageDisplay) {
    Alert alert = new Alert(Alert.AlertType.NONE);
    alert.initOwner(stageDisplay);
    alert.initModality(Modality.APPLICATION_MODAL);
    alert.getButtonTypes().setAll(ButtonType.YES, ButtonType.NO);
    alert.setTitle("Confirm Quit");
    alert.setContentText("Will that be all?");
    alert.showAndWait();
    if (alert.getResult().equals(ButtonType.YES)) {
      Platform.exit();
    }
  }
}

