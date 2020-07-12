package tetrominos;

import components.Block;
import components.GridPosition;

public class S extends Shape {
	public S(GridPosition pos) {
		super(pos);
		offSets[0] = new GridPosition(0, 0);
		offSets[1] = offSets[0].down();
		offSets[2] = offSets[1].right();
		offSets[3] = offSets[2].down();

		one = new Block(pos.add(offSets[0]));
		two = new Block(pos.add(offSets[1]));
		three = new Block(pos.add(offSets[2]));
		four = new Block(pos.add(offSets[3]));
		addBlocksToShape(one, two, three, four);
	}

	@Override
	public void setPosition(GridPosition pos) {
		one.setPosition(pos.add(offSets[0]));
		two.setPosition(pos.add(offSets[1]));
		three.setPosition(pos.add(offSets[2]));
		four.setPosition(pos.add(offSets[3]));
		this.gridPos = pos;
	}
}
