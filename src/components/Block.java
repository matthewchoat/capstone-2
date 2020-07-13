//This Class defines a single block, which is used to create a shape using 4 blocks
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

public class Block extends Pane implements Comparable<Block> {
	private static int SIZE;
	private GridPosition position;
	private boolean b;
	private static double gameSpeed = 0.06;
	private boolean blockLoaded = false;
	private Rectangle rec;

	public static void initBlockSize(int s) {
		SIZE = s;
	}

	public Block(GridPosition pos, Color col) {
		super();
		setMaxSize(SIZE,SIZE);
		setMinSize(SIZE,SIZE);
		setTranslateX(pos.getX() * SIZE);
		setTranslateY(pos.getY() * SIZE);
		rec = new Rectangle(SIZE-4,SIZE-4);
		rec.setTranslateX(2);
		rec.setTranslateY(2);
		rec.setFill(col);
		position = pos;
		rec.setStroke(Color.BLACK);
		ImageView view = new ImageView(new Image("/media/woodBlock.png"));
		view.setFitHeight(SIZE);
		view.setFitWidth(SIZE);
		this.getChildren().addAll(rec,view);
	}

	public Block(GridPosition pos) {
		this(pos, Color.BLACK);
	}

	public void setPosition(GridPosition pos) {
		if(blockLoaded) {
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

	public GridPosition getPosition() {
		return position;
	}

	public String toString() {
		return position.toString();
	}

	@Override
	public int compareTo(Block b) {
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
		rec.setFill(col);
	}
	public boolean isFrom(AbstractShape s) {
		return s.contains(this);
	}

	public static int getSIZE() {
		return SIZE;
	}

	public boolean isB() {
		return b;
	}

	public static double getGameSpeed() {
		return gameSpeed;
	}

	public boolean isBlockLoaded() {
		return blockLoaded;
	}

	public Rectangle getRec() {
		return rec;
	}

	public boolean getB() { return b; }

	public void setB(boolean b) {
		this.b = b;
	}


}
