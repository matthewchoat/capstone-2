package tetrominos;

import components.Block;
import components.GridPosition;

public class L extends Shape {
	public L(GridPosition pos) {
		super(pos);
		getOffSets()[0] = new GridPosition(0, 0);
		getOffSets()[1] = getOffSets()[0].down();
		getOffSets()[2] = getOffSets()[1].down();
		getOffSets()[3] = getOffSets()[2].right();

		setOne(new Block(pos.add(getOffSets()[0])));
		setTwo(new Block(pos.add(getOffSets()[1])));
		setThree(new Block(pos.add(getOffSets()[2])));
		setFour(new Block(pos.add(getOffSets()[3])));
		addBlocksToShape(getOne(), getTwo(), getThree(), getFour());
	}

	@Override
	public void setPosition(GridPosition pos) {
		getOne().setPosition(pos.add(getOffSets()[0]));
		getTwo().setPosition(pos.add(getOffSets()[1]));
		getThree().setPosition(pos.add(getOffSets()[2]));
		getFour().setPosition(pos.add(getOffSets()[3]));
		this.setGridPos(pos);
	}

}
