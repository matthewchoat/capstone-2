//This class contains the logic for creating a shape within the UpNext shape panel
package tetrominos;

import java.util.ArrayList;

import components.piece;
import components.UpNextPiece;

public class UpNextShape {
	ArrayList<UpNextPiece> blocks = new ArrayList<UpNextPiece>();
	int minX;
	int maxX;
	int minY;
	int maxY;
	boolean hr, vr;

	public UpNextShape(AbstractShape s) {
		UpNextPiece one = new UpNextPiece(s.getOffSets()[0]);
		UpNextPiece two = new UpNextPiece(s.getOffSets()[1]);
		UpNextPiece three = new UpNextPiece(s.getOffSets()[2]);
		UpNextPiece four = new UpNextPiece(s.getOffSets()[3]);
		blocks.add(one);
		blocks.add(two);
		blocks.add(three);
		blocks.add(four);
		checkOffSet();
		blocks.forEach(b -> {
			b.setTranslateX(b.getTranslateX() - getSize().getWidth() / 2);
			b.setTranslateY(b.getTranslateY() - getSize().getHeight() / 2);
		});
	}

	private void checkOffSet() {
		hr = false;
		vr = false;
		blocks.forEach(block -> {
			if (block.getPosition().getX() < 0) {
				hr = true;
			} else if (block.getPosition().getY() < 0) {
				vr = true;
			}
		});
		if (hr) {
			blocks.forEach(block -> {
				block.setPosition(block.getPosition().right());
			});
		}
		if (vr) {
			blocks.forEach(block -> {
				block.setPosition(block.getPosition().down());
			});
		}
	}

	public Size getSize() {
		int height = 0;
		int width = 0;
		minX = 0;
		maxX = 0;
		minY = 0;
		maxY = 0;
		blocks.forEach(b -> {
			if (b.getPosition().getX() < minX) {
				minX = b.getPosition().getX();
			}
			if (b.getPosition().getX() > maxX) {
				maxX = b.getPosition().getX();
			}
			if (b.getPosition().getY() < minY) {
				minY = b.getPosition().getY();
			}
			if (b.getPosition().getY() > maxY) {
				maxY = b.getPosition().getY();
			}
		});
		height = (int) ((maxY - minY) * piece.getSIZE());
		width = (int) ((maxX - minX) * piece.getSIZE());
		Size res = new Size(width, height);
		return res;
	}

	public ArrayList<UpNextPiece> getBlocks() {
		return blocks;
	}
}
