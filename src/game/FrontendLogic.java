//To facilitate SOLID code design principles and practice Interface Segregation, I've chosen to move all of my logic related to frontend game features from my main GameUI class, into this class.
//Be aware that logic that belongs strictly to things like Tetromino methods (besides the methods contained for randomly generating Tetromino objects) and the Game Panel exist in their respective packages/classes.
package game;

import components.GamePanel;
import javafx.application.Platform;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.paint.Color;
import javafx.stage.Modality;
import tetrominos.*;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.function.Function;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static javafx.scene.paint.Color.*;

public class FrontendLogic {
  private String randTetrimino = "";
  private GameUI currentGame;
  public FrontendLogic(GameUI currentGame) {
    this.currentGame = currentGame;
  }

  // This Lambda function streams a list of 50 abstract shape strings, shuffles them, and returns as a joined string.
  // This function was necessary to write to reduce Piece generation load times during gameplay by loading
  // 50 shapes at once instead of having to generate a new shape after every turn.
  Function<String[], String> shuffleLambda = elements -> {
    return Stream.iterate(Arrays.asList(elements), list -> {
      Collections.shuffle(list = new ArrayList<>(list));
      return list;
    }).skip(1).flatMap(List::stream).limit(50).collect(Collectors.joining());
  };
  //splitting the returned shuffleLambda string into a single number to be used for assigning a random Tetromino type.
  void splitShuffled(Function<String[], String> shuffleLambda) {
    randTetrimino = shuffleLambda.apply("0123456".split(""));
  }
  //retrieving a random tetromino from the shuffled list
  private int getPiece(int piecePosition) {
    return Integer.parseInt(randTetrimino.charAt(piecePosition) + "");
  }
  //initialize tetromino Colors to be assigned randomly
  void initColors(Color[] colors) {
    colors[0] = FIREBRICK;
    colors[1] = OLIVEDRAB;
    colors[2] = FORESTGREEN;
    colors[3] = GOLDENROD;
    colors[4] = DARKVIOLET;
  }
  //shuffling random shapes and applying colors
  public void readyShapes(Color[] colors){
    splitShuffled(shuffleLambda);
    initColors(colors);}
  //Generates a Tetromino from the 50 AbstractShapes and assigns it a type based on the string number chosen randomly in the splitShuffled method and shuffleLambda function.
  AbstractShape generate() {
    int type = getPiece(currentGame.getTetriminoNum());
    currentGame.incrementTetriminoNum();
    if (currentGame.getTetriminoNum() > 49) {
      currentGame.setTetriminoNum(0);
    }
    AbstractShape genPiece;
    switch (type) {
      case 0:
        genPiece = new L(currentGame.getGridPos());
        break;
      case 1:
        genPiece = new J(currentGame.getGridPos());
        break;
      case 2:
        genPiece = new I(currentGame.getGridPos());
        break;
      case 3:
        genPiece = new O(currentGame.getGridPos());
        break;
      case 4:
        genPiece = new S(currentGame.getGridPos());
        break;
      case 5:
        genPiece = new Z(currentGame.getGridPos());
        break;
      case 6:
        genPiece = new T(currentGame.getGridPos());
        break;
      default:
        genPiece = new L(currentGame.getGridPos());
    }
    genPiece.setFill(currentGame.getColors()[(int) (Math.random() * 5)]);
    currentGame.getUpNext().setShape(getNext(currentGame.getTetriminoNum()).moveToUpNext());
    return genPiece;
  }
  //Chooses the NextUp Tetromino Shape and assigns it a type based on the random string number from the shuffleLambda function.
  private AbstractShape getNext(int tetriminoNum) {
    int nextPieceNo;
    if (tetriminoNum > 49) { //this relates to the stream random function
      splitShuffled(shuffleLambda);
      nextPieceNo = 0;
    } else {
      nextPieceNo = tetriminoNum;
    }
    int type = getPiece(nextPieceNo);
    AbstractShape genPiece;
    switch (type) {
      case 0:
        genPiece = new L(currentGame.getGridPos());
        break;
      case 1:
        genPiece = new J(currentGame.getGridPos());
        break;
      case 2:
        genPiece = new I(currentGame.getGridPos());
        break;
      case 3:
        genPiece = new O(currentGame.getGridPos());
        break;
      case 4:
        genPiece = new S(currentGame.getGridPos());
        break;
      case 5:
        genPiece = new Z(currentGame.getGridPos());
        break;
      case 6:
        genPiece = new T(currentGame.getGridPos());
        break;
      default:
        genPiece = new L(currentGame.getGridPos());
    }
    genPiece.setFill(currentGame.getColors()[(int) (Math.random() * 5)]);
    return genPiece;
  }
  //move pieces down, check if pieces would spill over the game panel (game over condition) and check for removable lines
  void fallLogic() {
    GamePanel gPanel = currentGame.getGamePanel();

    if (gPanel.canFall(currentGame.getAbsShape()) && !currentGame.isPaused()) {
      currentGame.getAbsShape().setPosition(currentGame.getAbsShape().getPosition().down());
    } else if (!gPanel.canFall(currentGame.getAbsShape()) && !currentGame.isPaused()) {
      currentGame.getSound().fallen();
      currentGame.getAbsShape().setFill(currentGame.getFallen());
      currentGame.setAbsShape(generate());
      if (gPanel.isGameOver())
        currentGame.gameOverUI();
      else
        gPanel.addShapeToPanel(currentGame.getAbsShape());

      gPanel.checkRemovableLines(currentGame.getSound(), currentGame);
    }
  }
  //Snap Piece down to the bottom row instantly, check if pieces would spill over the game panel (game over condition) and check for removable lines
  void hardDropLogic() {
    GamePanel gPanel = currentGame.getGamePanel();
    if (gPanel.canFall(currentGame.getAbsShape()) && !currentGame.isPaused()) {
      currentGame.getAbsShape().setPosition(currentGame.getAbsShape().getPosition().down());

      while ((gPanel.canFall(currentGame.getAbsShape()))) {
        currentGame.getAbsShape().setPosition(currentGame.getAbsShape().getPosition().down()); }

    } else if (!gPanel.canFall(currentGame.getAbsShape()) && !currentGame.isPaused()) {
      currentGame.getSound().fallen();
      currentGame.getAbsShape().setFill(currentGame.getFallen());
      currentGame.setAbsShape(generate());
      if (gPanel.isGameOver())
        currentGame.gameOverUI();
      else
        gPanel.addShapeToPanel(currentGame.getAbsShape());

      gPanel.checkRemovableLines(currentGame.getSound(), currentGame);
    }
  }
  //Pause game with sound
  void pause() {
    currentGame.getPlayBtn().setText("Play");
    currentGame.getTimer().stop();
    currentGame.getSound().pause();
    currentGame.setPaused(true);
    currentGame.getSound().setMenuMusic();
  }
  //Pause game without sound
  void pauseNoSound() {
    currentGame.getPlayBtn().setText("Play");
    currentGame.getTimer().stop();
    currentGame.setPaused(true);
    currentGame.getSound().setMenuMusic();
  }
  //Resumes game after pause
  void resumeGame() {
    currentGame.getPlayBtn().setDisable(false);
    currentGame.getPlayBtn().setText("Pause");
    currentGame.setLast(System.nanoTime());
    currentGame.getTimer().start();
    currentGame.getSound().play();
    currentGame.setPaused(false);
    currentGame.getSound().setGameMusic();
  }
  // Prompts the user to quit the program
  void exit() {
    Alert alert = new Alert(Alert.AlertType.NONE);
    alert.initOwner(currentGame.getStageDisplay());
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