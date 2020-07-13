package tetrominos;

import java.util.ArrayList;

import components.piece;
import components.GridPosition;

public class O extends AbstractShape {
	public O(GridPosition pos) {
		super(pos);
		getOffSets()[0] = new GridPosition(0, 0);
		getOffSets()[1] = getOffSets()[0].down();
		getOffSets()[2] = getOffSets()[1].right();
		getOffSets()[3] = getOffSets()[0].right();

		setOne(new piece(pos.add(getOffSets()[0])));
		setTwo(new piece(pos.add(getOffSets()[1])));
		setThree(new piece(pos.add(getOffSets()[2])));
		setFour(new piece(pos.add(getOffSets()[3])));
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
		for (piece b : getBlocks()) {
			res.add(b.getPosition());
		}
		return res;
	}

	@Override
	public void Rotate() {

	}

}
