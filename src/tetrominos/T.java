package tetrominos;

import components.Block;
import components.GridPosition;

public class T extends Shape {
	public T(GridPosition pos) {
		super(pos);
		offSets[0] = new GridPosition(-1, 1);
		offSets[1] = offSets[0].right();
		offSets[2] = offSets[1].up();
		offSets[3] = offSets[1].right();

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
