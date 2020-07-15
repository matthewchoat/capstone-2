//This Class defines a single Piece (4 pieces make up a "Shape") to be shown uniformly on the UI
package components;

import javafx.animation.KeyFrame;
import javafx.animation.KeyValue;
import javafx.animation.Timeline;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.util.Duration;
import tetrominos.AbstractShape;

public class Piece extends Pane implements Comparable<Piece> {
	private static int SIZE;
	private GridPosition position;
	private boolean p;
	private static double gameSpeed = 0.06;
	private boolean pieceLoaded = false;
	private Rectangle rectangle;

	//UI contructor
	public Piece(GridPosition pos, Color col) {
		super();
		setMaxSize(SIZE,SIZE);
		setMinSize(SIZE,SIZE);
		setTranslateX(pos.getX() * SIZE);
		setTranslateY(pos.getY() * SIZE);
		rectangle = new Rectangle(SIZE-4,SIZE-4);
		rectangle.setTranslateX(2);
		rectangle.setTranslateY(2);
		rectangle.setFill(col);
		position = pos;
		rectangle.setStroke(Color.BLACK);
		ImageView view = new ImageView(new Image("/media/woodBlock.png"));
		view.setFitHeight(SIZE);
		view.setFitWidth(SIZE);
		this.getChildren().addAll(rectangle,view);
	}
	//create size of the Piece
	public static void initPieceSize(int s) {
		SIZE = s;
	}

	public Piece(GridPosition pos) {
		this(pos, Color.BLACK);
	}
	//set position for pieces
	public void setPosition(GridPosition pos) {
		if(pieceLoaded) {
			setTranslateX(pos.getX() * SIZE);
			setTranslateY(pos.getY() * SIZE);
		}else {
			Timeline move = new Timeline(
					new KeyFrame(Duration.seconds(gameSpeed), new KeyValue(translateXProperty(), pos.getX() * SIZE),
							new KeyValue(translateYProperty(), pos.getY() * SIZE)));
			move.playFromStart();
		}
		this.position = pos;
	}
	//get position on grid
	public GridPosition getPosition() {
		return position;
	}

	public String toString() {
		return position.toString();
	}

	@Override
	public int compareTo(Piece b) {
		if (position.getX() > b.position.getX())
			return 1;
		else if (position.getX() < b.position.getX())
			return -1;
		else if (position.getY() > b.position.getY())
			return 1;
		else if (position.getY() < b.position.getY())
			return -1;
		else
			return 0;
	}

	public void setFill(Color col) {
		rectangle.setFill(col);
	}
	public boolean isFrom(AbstractShape s) {
		return s.contains(this);
	}

	public static int getSIZE() {
		return SIZE;
	}

	public boolean isP() {
		return p;
	}

	public static double getGameSpeed() {
		return gameSpeed;
	}

	public boolean isPieceLoaded() {
		return pieceLoaded;
	}

	public Rectangle getRectangle() {
		return rectangle;
	}

	public boolean getB() { return p; }

	public void setP(boolean p) {
		this.p = p;
	}

}
