package tetrominos;

import components.piece;
import components.GridPosition;

public class J extends ReversedShape {
	public J(GridPosition pos) {
		super(pos);
		getOffSets()[0] = new GridPosition(0, 1);
		getOffSets()[1] = getOffSets()[0].down();
		getOffSets()[2] = getOffSets()[1].down();
		getOffSets()[3] = getOffSets()[2].left();

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
}
