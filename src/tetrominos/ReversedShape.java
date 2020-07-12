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
			res.add(new GridPosition(offSets[i].getY() - 2, -offSets[i].getX() + 2).add(gridPos));
		}
		return res;
	}

	public void Rotate() {
		blocks.forEach(block -> {
			offSets[blocks.indexOf(block)] = block.getPosition().sub(gridPos);
		});
		for (int i = 0; i < 4; i++) {
			offSets[i] = new GridPosition(offSets[i].getY() - 2, -offSets[i].getX() + 2);
		}
		blocks.forEach(b -> {
			b.setPosition(offSets[blocks.indexOf(b)].add(gridPos));
		});
	}
}
