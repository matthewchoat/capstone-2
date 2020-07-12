package tetrominos;

import java.util.ArrayList;

import components.GridPosition;

public abstract class Shape extends AbstractShape {

	public Shape(GridPosition pos) {
		super(pos);
	}

	public void Rotate() {
		blocks.forEach(block -> {
			offSets[blocks.indexOf(block)] = block.getPosition().sub(gridPos);
		});
		for (int i = 0; i < 4; i++) {
			offSets[i] = new GridPosition(offSets[i].getY() - 1, -offSets[i].getX() + 1);
		}
		blocks.forEach(b -> {
			b.setPosition(offSets[blocks.indexOf(b)].add(gridPos));
		});
	}

	public ArrayList<GridPosition> unRotate() {
		ArrayList<GridPosition> result = new ArrayList<GridPosition>();
		for (int i = 0; i < 4; i++) {
			result.add(new GridPosition(offSets[i].getY() - 1, -offSets[i].getX() + 1).add(gridPos));
		}
		return result;
	}



}
