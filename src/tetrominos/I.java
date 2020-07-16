package tetrominos;

import java.util.ArrayList;

import components.Piece;
import components.GridPosition;

public class I extends Shape {
	boolean rotated = false;

	public I(GridPosition pos) {
		super(pos);
		getOffSets()[0] = new GridPosition(0, 0);
		getOffSets()[1] = getOffSets()[0].down();
		getOffSets()[2] = getOffSets()[1].down();
		getOffSets()[3] = getOffSets()[2].down();

		setOne(new Piece(pos.add(getOffSets()[0])));
		setTwo(new Piece(pos.add(getOffSets()[1])));
		setThree(new Piece(pos.add(getOffSets()[2])));
		setFour(new Piece(pos.add(getOffSets()[3])));
		addBlocksToShape(getOne(), getTwo(), getThree(), getFour());
	}

	public void setPosition(GridPosition pos) {
		getOne().setPosition(pos.add(getOffSets()[0]));
		getTwo().setPosition(pos.add(getOffSets()[1]));
		getThree().setPosition(pos.add(getOffSets()[2]));
		getFour().setPosition(pos.add(getOffSets()[3]));
		this.setGridPos(pos);
	}

	public ArrayList<GridPosition> unRotate() {
		ArrayList<GridPosition> res = new ArrayList<GridPosition>();
		if (rotated) {
			res.add(getGridPos().add(new GridPosition(0, 0)));
			res.add(getGridPos().add(new GridPosition(0, 1)));
			res.add(getGridPos().add(new GridPosition(0, 2)));
			res.add(getGridPos().add(new GridPosition(0, 3)));
		} else {
			res.add(getGridPos().add(new GridPosition(-1, 1)));
			res.add(getGridPos().add(new GridPosition(0, 1)));
			res.add(getGridPos().add(new GridPosition(1, 1)));
			res.add(getGridPos().add(new GridPosition(2, 1)));
		}
		return res;
	}

	public void Rotate() {
		if (rotated) {
			getOffSets()[0] = new GridPosition(0, 0);
			getOffSets()[1] = new GridPosition(0, 1);
			getOffSets()[2] = new GridPosition(0, 2);
			getOffSets()[3] = new GridPosition(0, 3);
			rotated = false;
		} else {
			getOffSets()[0] = new GridPosition(-1, 1);
			getOffSets()[1] = new GridPosition(0, 1);
			getOffSets()[2] = new GridPosition(1, 1);
			getOffSets()[3] = new GridPosition(2, 1);
			rotated = true;
		}
		getBlocks().forEach(b -> {
			b.setPosition(getOffSets()[getBlocks().indexOf(b)].add(getGridPos()));
		});
	}

}
