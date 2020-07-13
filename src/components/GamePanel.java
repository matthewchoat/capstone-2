//This class contains logic and variables related to the main game panel. Most of this class will be Java FX-specific.
package components;

import javafx.animation.KeyFrame;
import javafx.animation.KeyValue;
import javafx.animation.Timeline;
import javafx.geometry.Pos;
import javafx.scene.control.Label;
import javafx.scene.effect.BlendMode;
import javafx.scene.effect.ColorAdjust;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.shape.Line;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Font;
import javafx.scene.text.TextAlignment;
import javafx.util.Duration;
import tetrominos.AbstractShape;
import tetrominos.Size;
import game.GameUI;
import java.util.ArrayList;
import java.util.Collections;

public class GamePanel extends Pane {
	//initializing variables for the game panel, pane, border, grid, and lines
	private boolean ready = true;
	private int count;
	private int i = 0;
	private boolean result[][] = new boolean[10][20];
	private AbstractShape selected;
	private ArrayList<piece> blocks = new ArrayList<piece>();
	private boolean gameActive;
	private GameGrid g;
	private Pane border;
	private Pane pane;
	private int toBeRemoved;
	private int removing;
	private Size size;
	private Rectangle blend;
	private Line line = new Line();
	private Line lin = new Line();
	private Line width = new Line();

	//initializing game panel size
	public void initGamePanel(int sh) {
		pane.setMaxHeight((sh - (size.getHeight() + 12)) / 2 + 1);
		pane.setMinHeight((sh - (size.getHeight() + 12)) / 2 + 1);
		pane.setTranslateY(-pane.getMaxHeight() - 4);
		line.setStartX(0);
		line.setStartY(0);
		line.setEndX(size.getWidth());
		line.setEndY(0);
		line.setStrokeWidth(2);
		
		lin.setStartX(0);
		lin.setStartY(pane.getMaxHeight());
		lin.setEndX(size.getWidth());
		lin.setEndY(pane.getMaxHeight());
		lin.setStrokeWidth(2);
		
		width.setStartX(0);
		width.setEndX(size.getWidth());
		width.setStartY(pane.getMaxHeight() + 2);
		width.setEndY(pane.getMaxHeight() + 2);
		width.setStrokeWidth(2);
	}

	//constructor building full gamepanel UI
	public GamePanel(int w, int h, int sh) {
		size = new Size(w, h);
		setMaxSize(w, h);
		setMinSize(w, h);
		blend = new Rectangle(w,h);
		blend.setFill(Color.BLACK);
		blend.setBlendMode(BlendMode.DIFFERENCE);
		border = new Pane();
		border.setMaxSize(w + 20, h + 10);
		border.setMinSize(w + 20, h + 10);
		border.setBorder(new Border(
				new BorderStroke(Color.SADDLEBROWN, BorderStrokeStyle.SOLID, new CornerRadii(0), new BorderWidths(2))));
		border.setTranslateX(-10);
		border.setTranslateY(-5);
		border.setStyle("-fx-background-color: saddlebrown");
		ImageView view = new ImageView(new Image("/media/background.jpg"));
		ColorAdjust co = new ColorAdjust();
		co.setBrightness(0.3);
		view.setEffect(co);
		view.setFitWidth(border.getMaxWidth()-4);
		view.setFitHeight(border.getMaxHeight()-4);
		view.setTranslateX(2);
		view.setTranslateY(2);
		border.getChildren().add(view);
		pane = new Pane();
		pane.setMaxHeight((sh - (h + 12)) / 2 + 1);
		pane.setMinHeight((sh - (h + 12)) / 2 + 1);
		pane.setMaxWidth(w);
		pane.setMinWidth(w);
		pane.setTranslateY(-pane.getMaxHeight() - 4);
		pane.setStyle("-fx-background-color: white");
		pane.setVisible(false);
		
		line.setStartX(0);
		line.setStartY(0);
		line.setEndX(w);
		line.setEndY(0);
		line.setStrokeWidth(2);
		
		lin.setStartX(0);
		lin.setStartY(pane.getMaxHeight());
		lin.setEndX(w);
		lin.setEndY(pane.getMaxHeight());
		lin.setStrokeWidth(2);
		width.setStartX(0);
		width.setEndX(w);
		width.setStartY(pane.getMaxHeight() + 2);
		width.setEndY(pane.getMaxHeight() + 2);
		width.setStroke(Color.rgb(58, 127, 46,0));
		width.setEffect(co);
		width.setStrokeWidth(2);
		pane.getChildren().addAll(line, lin, width);
		g = new GameGrid(w, h,Color.GREY);
		getChildren().addAll(border, g, pane, blend);
	}

	//adding a new shape to the GamePanel
	public void addShapeToPanel(AbstractShape newShape) {
		getChildren().addAll(newShape.getBlocks());
		blocks.addAll(newShape.getBlocks());
		pane.toFront();
		blend.toFront();
		selected = newShape;
	}
	//Check to see if shape can rotate while in the GamePanel
	public boolean canRotate(AbstractShape s) {
		gameActive = true;
		canMove();
		GridPosition[] poss = new GridPosition[4];
		i = 0;
		s.unRotate().forEach(pos -> {
			poss[i] = pos;
			i++;
		});
		for (int i = 0; i < 4; i++) {
			if (poss[i].getX() < 0 || poss[i].getX() > 9 || poss[i].getY() > 19) {
				return false;
			} else {
				if (poss[i].getY() > 0 && result[poss[i].getX()][poss[i].getY()]) {
					gameActive = false;
					ArrayList<piece> blocks = s.getBlocks();
					for (piece block : blocks) {
						if (block.getPosition().equals(poss[i])) {
							gameActive = true;
						}
					}
					if (!gameActive) {
						return false;
					}
				}
			}
		}
		return gameActive;
	}
	//Check to see if shape can fall down while in the GamePanel
	public boolean canFall(AbstractShape s) {
		gameActive = true;
		canMove();
		for (piece b : s.getBlocks()) {
			if (b.getPosition().getY() == 19) {
				gameActive = false;
			}
		}
		if (gameActive) {
			s.getBottomRow().forEach(b -> {
				try {
					if (result[b.getPosition().getX()][b.getPosition().getY() + 1]) {
						gameActive = false;
					}
				} catch (ArrayIndexOutOfBoundsException ex) {
				}
			});
		}
		return gameActive;
	}
	//Check to see if shape can move left while in the GamePanel
	public boolean canMoveLeft(AbstractShape s) {
		gameActive = true;
		canMove();
		for (piece b : s.getBlocks()) {
			if (b.getPosition().getX() == 0) {
				gameActive = false;
			}
		}
		if (gameActive) {
			s.getLeft().forEach(b -> {
				try {
					if (result[b.getPosition().getX() - 1][b.getPosition().getY()]) {
						gameActive = false;
					}
				} catch (ArrayIndexOutOfBoundsException x) {
				}
			});
		}
		return gameActive;
	}
	//Check to see if shape can move right while in the GamePanel
	public boolean canMoveRight(AbstractShape s) {
		gameActive = true;
		canMove();
		for (piece b : s.getBlocks()) {
			if (b.getPosition().getX() == 9) {
				gameActive = false;
			}
		}
		if (gameActive) {
			s.getBlockArray().forEach(b -> {
				try {
					if (result[b.getPosition().getX() + 1][b.getPosition().getY()]) {
						gameActive = false;
					}
				} catch (ArrayIndexOutOfBoundsException x) {
				}
			});
		}
		return gameActive;
	}
	//Check to see if there are any full lines that can be removed from the GamePanel
	public void checkRemovableLines(AudioController sm, GameUI ui) {
		ArrayList<Integer> toRemove = new ArrayList<Integer>();
		for (int i = 0; i < 20; i++) {
			boolean removable = true;
			for (int j = 0; j < 10; j++) {
				if (!result[j][i])
					removable = false;
			}
			if (removable) {
				toRemove.add(i);
			}
		}
		//If there are any removable lines, call the removeLine method and add score display
		if (!toRemove.isEmpty()) {
			ready = false;
			removing = 0;
			Timeline line = new Timeline(new KeyFrame(Duration.seconds(.3)));
			line.setOnFinished(e -> {
				if (removing < toRemove.size()) {
					removeLine(toRemove.get(removing));
					if (removing == 1) {
						sm.line2();
						animateScore(20, ui, 40);
					} else if (removing == 2) {
						sm.line3();
						animateScore(40, ui, 50);
					} else {
						sm.line4();
						animateScore(70, ui, 60);
					}
					removing++;
					line.playFromStart();
				}
				if (removing >= toRemove.size()) {
					ready = true;
				}
			});
			sm.line1();
			removeLine(toRemove.get(0));
			animateScore(10, ui, 30);
			removing++;
			line.playFromStart();
		}
	}
//remove lines from the GamePanel
	private void removeLine(int i) {
		ArrayList<piece> removables = new ArrayList<piece>();
		blocks.forEach(block -> {
			if (block.getPosition().getY() == i) {
				removables.add(block);
			}
		});
		Collections.sort(removables);
		toBeRemoved = 4;
		Timeline line = new Timeline(new KeyFrame(Duration.seconds(.02),
				new KeyValue(removables.get(toBeRemoved).translateYProperty(), removables.get(toBeRemoved).getTranslateY() + piece.getSIZE()),
				new KeyValue(removables.get(9 - toBeRemoved).translateYProperty(),
						removables.get(9 - toBeRemoved).getTranslateY() + piece.getSIZE())));
		line.setOnFinished(e -> {
			if (toBeRemoved >= 0) {
				blocks.remove(removables.get(toBeRemoved));
				blocks.remove(removables.get(9 - toBeRemoved));
				getChildren().remove(removables.get(toBeRemoved));
				getChildren().remove(removables.get(9 - toBeRemoved));
				if (toBeRemoved > 0) {

					toBeRemoved--;
					line.getKeyFrames()
							.setAll(new KeyFrame(Duration.seconds(.02),
									new KeyValue(removables.get(toBeRemoved).translateYProperty(),
											removables.get(toBeRemoved).getTranslateY() + piece.getSIZE()),
									new KeyValue(removables.get(9 - toBeRemoved).translateYProperty(),
											removables.get(9 - toBeRemoved).getTranslateY() + piece.getSIZE())));
				} else {
					toBeRemoved--;
				}
				line.playFromStart();
			} else if (!blocks.isEmpty()) {
				count = 0;
				blocks.forEach(block -> {
					if (!block.isFrom(selected) && block.getPosition().getY() < i) {
						count++;
					}
				});
				KeyValue[] values = new KeyValue[count];
				blocks.forEach(block -> {
					if (!block.isFrom(selected) && block.getPosition().getY() < i) {
						count--;
						values[count] = new KeyValue(block.translateYProperty(), block.getTranslateY() + piece.getSIZE());
					}
				});
				Timeline shiftDown = new Timeline(new KeyFrame(Duration.seconds(.2), values));
				shiftDown.setOnFinished(eve -> {
					blocks.forEach(block -> {
						if (!block.isFrom(selected) && block.getPosition().getY() < i) {
							block.setPosition(block.getPosition().down());
						}
					});
				});
				shiftDown.playFromStart();
			}
		});
		line.playFromStart();
	}

	private void canMove() {
		for (int i = 0; i < result.length; i++) {
			for (int j = 0; j < result[i].length; j++) {
				result[i][j] = false;
			}
		}
		blocks.forEach(block -> {
			try {
				result[block.getPosition().getX()][block.getPosition().getY()] = true;
			} catch (ArrayIndexOutOfBoundsException ex) {
			}

		});
	}
//Boolean for checking if the game state isOver.
	public boolean isGameOver() {
		gameActive = false;
		blocks.forEach(b -> {
			if (b.getPosition().getY() == 0)
				gameActive = true;
		});
		return gameActive;
	}
//set up scoring animations within the game panel
	private void animateScore(int s, GameUI ui, int fs) {
		ui.levelChange(s);
		Label label = new Label("+" + s);
		label.setFont(Font.font(fs));
		label.setTextFill(Color.WHITE);
		label.setStyle("-fx-font-weight: bold;");
		label.setMinSize(200, 100);
		label.setMaxSize(200, 100);
		label.setTextAlignment(TextAlignment.CENTER);
		label.setAlignment(Pos.CENTER);
		label.setTranslateX(size.getWidth() / 2 - 100);
		label.setTranslateY(size.getHeight() / 2 - 50);
		getChildren().add(label);
		Timeline hide = new Timeline(
				new KeyFrame(Duration.seconds(2), new KeyValue(label.translateYProperty(), label.getTranslateY() - 150),
						new KeyValue(label.opacityProperty(), 0)));
		hide.setOnFinished(e -> {
			getChildren().remove(label);
		});
		hide.playFromStart();
	}
	//game ready boolean
	public boolean isReady() {
		return ready;
	}
	//reset game method
	public void reset() {
		blocks.clear();
		getChildren().clear();
		for (int i = 0; i < result.length; i++) {
			for (int j = 0; j < result[i].length; j++) {
				result[i][j] = false;
			}
		}
		//fade to back and reset new game panel
		Timeline change = new Timeline(new KeyFrame(Duration.seconds(1),new KeyValue(blend.fillProperty(),Color.BLACK)));
		change.playFromStart();
		getChildren().addAll(border, g, pane, blend);
	}
	//level change animation method
	public void invertPanelColors(boolean b) {
		Color col;
		if (b) {
			col = Color.WHITE;
		} else {
			col = Color.BLACK;
		}
		Timeline levelTransition = new Timeline(new KeyFrame(Duration.seconds(1),new KeyValue(blend.fillProperty(),col)));
		levelTransition.playFromStart();
	}

}
