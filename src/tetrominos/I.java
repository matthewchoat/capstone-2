package tetrominos;

import java.util.ArrayList;

import components.Block;
import components.GridPosition;

public class I extends Shape {
	boolean rotated = false;

	public I(GridPosition pos) {
		super(pos);
		offSets[0] = new GridPosition(0, 0);
		offSets[1] = offSets[0].down();
		offSets[2] = offSets[1].down();
		offSets[3] = offSets[2].down();

		one = new Block(pos.add(offSets[0]));
		two = new Block(pos.add(offSets[1]));
		three = new Block(pos.add(offSets[2]));
		four = new Block(pos.add(offSets[3]));
		addBlocksToShape(one, two, three, four);
	}

	public void setPosition(GridPosition pos) {
		one.setPosition(pos.add(offSets[0]));
		two.setPosition(pos.add(offSets[1]));
		three.setPosition(pos.add(offSets[2]));
		four.setPosition(pos.add(offSets[3]));
		this.gridPos = pos;
	}

	public ArrayList<GridPosition> unRotate() {
		ArrayList<GridPosition> res = new ArrayList<GridPosition>();
		if (rotated) {
			res.add(gridPos.add(new GridPosition(0, 0)));
			res.add(gridPos.add(new GridPosition(0, 1)));
			res.add(gridPos.add(new GridPosition(0, 2)));
			res.add(gridPos.add(new GridPosition(0, 3)));
		} else {
			res.add(gridPos.add(new GridPosition(-1, 1)));
			res.add(gridPos.add(new GridPosition(0, 1)));
			res.add(gridPos.add(new GridPosition(1, 1)));
			res.add(gridPos.add(new GridPosition(2, 1)));
		}
		return res;
	}

	public void Rotate() {
		if (rotated) {
			offSets[0] = new GridPosition(0, 0);
			offSets[1] = new GridPosition(0, 1);
			offSets[2] = new GridPosition(0, 2);
			offSets[3] = new GridPosition(0, 3);
			rotated = false;
		} else {
			offSets[0] = new GridPosition(-1, 1);
			offSets[1] = new GridPosition(0, 1);
			offSets[2] = new GridPosition(1, 1);
			offSets[3] = new GridPosition(2, 1);
			rotated = true;
		}
		blocks.forEach(b -> {
			b.setPosition(offSets[blocks.indexOf(b)].add(gridPos));
		});
	}

}
