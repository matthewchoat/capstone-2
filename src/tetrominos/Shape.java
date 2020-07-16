//This is my general Shape class that extends AbstractShape. Used for advanced Rotation methods.
package tetrominos;

import java.util.ArrayList;

import components.GridPosition;

public abstract class Shape extends AbstractShape {

	public Shape(GridPosition pos) {
		super(pos);
	}

	public void Rotate() {
		getBlocks().forEach(block -> {
			getOffSets()[getBlocks().indexOf(block)] = block.getPosition().sub(getGridPos());
		});
		for (int i = 0; i < 4; i++) {
			getOffSets()[i] = new GridPosition(getOffSets()[i].getY() - 1, -getOffSets()[i].getX() + 1);
		}
		getBlocks().forEach(b -> {
			b.setPosition(getOffSets()[getBlocks().indexOf(b)].add(getGridPos()));
		});
	}

	public ArrayList<GridPosition> unRotate() {
		ArrayList<GridPosition> result = new ArrayList<GridPosition>();
		for (int i = 0; i < 4; i++) {
			result.add(new GridPosition(getOffSets()[i].getY() - 1, -getOffSets()[i].getX() + 1).add(getGridPos()));
		}
		return result;
	}



}
