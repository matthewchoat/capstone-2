//This class is similar to the Class "Shape", but is for shapes that are just reversed version of other shapes (like th J and Z tetrominos)
package tetrominos;

import java.util.ArrayList;
import components.GridPosition;

public abstract class ReversedShape extends AbstractShape {
	public ReversedShape(GridPosition pos) {
		super(pos);
	}

	public ArrayList<GridPosition> unRotate() {
		ArrayList<GridPosition> res = new ArrayList<GridPosition>();
		for (int i = 0; i < 4; i++) {
			res.add(new GridPosition(getOffSets()[i].getY() - 2, -getOffSets()[i].getX() + 2).add(getGridPos()));
		}
		return res;
	}

	public void Rotate() {
		getBlocks().forEach(block -> {
			getOffSets()[getBlocks().indexOf(block)] = block.getPosition().sub(getGridPos());
		});
		for (int i = 0; i < 4; i++) {
			getOffSets()[i] = new GridPosition(getOffSets()[i].getY() - 2, -getOffSets()[i].getX() + 2);
		}
		getBlocks().forEach(b -> {
			b.setPosition(getOffSets()[getBlocks().indexOf(b)].add(getGridPos()));
		});
	}
}
