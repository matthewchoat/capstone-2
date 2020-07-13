//Abstract Shape class that Shapes will inherit from and eventually will become individual Tetrominos
package tetrominos;

import java.util.ArrayList;

import components.piece;
import components.GamePanel;
import components.GridPosition;
import javafx.scene.paint.Color;

public abstract class AbstractShape {
	//Abstract shapes have 4 Blocks
	private piece one, two, three, four;
	//Abstract shapes have a GridPosition with offsets
	private GridPosition[] offSets = new GridPosition[4];
	//Array list of all 4 Blocks to make a shape
	private ArrayList<piece> blocks = new ArrayList<piece>();
	//x, y position on the grid
	private GridPosition gridPos;

	//constructor
	public AbstractShape(GridPosition gridPos) {
		this.gridPos = gridPos;
	}
	//Set fill color for Blocks
	public void setFill(Color p) {
		blocks.forEach(block -> {
			block.setFill(p);
		});
	}

	//Getters for grid offsets, grid position, blocks, block array, left block array, and shape bottom row
	public GridPosition[] getOffSets() {
		return offSets;
	}
	public GridPosition getPosition() {
		return gridPos;
	}
	public ArrayList<piece> getBlocks() {
		return blocks;
	}
	public ArrayList<piece> getBlockArray() {
		ArrayList<piece> result = new ArrayList<piece>();
		for (piece b : this.getBlocks()) {
			b.setP(true);
		}
		this.getBlocks().forEach(block1 -> {
			this.getBlocks().forEach(block2 -> {
				if (block2.getPosition().isRight(block1.getPosition())) {
					block1.setP(false);
				}
			});
		});
		this.getBlocks().forEach(block -> {
			if (block.getB()) {
				result.add(block);
			}
		});
		return result;
	}
	public ArrayList<piece> getLeft() {
		ArrayList<piece> result = new ArrayList<piece>();
		for (piece b : this.getBlocks()) {
			b.setP(true);
		}
		this.getBlocks().forEach(block1 -> {
			this.getBlocks().forEach(block2 -> {
				if (block2.getPosition().isLeft(block1.getPosition())) {
					block1.setP(false);
				}
			});
		});
		this.getBlocks().forEach(block -> {
			if (block.getB()) {
				result.add(block);
			}
		});
		return result;
	}
	public ArrayList<piece> getBottomRow() {
		ArrayList<piece> result = new ArrayList<piece>();
		for (piece b : this.getBlocks()) {
			b.setP(true);
		}
		this.getBlocks().forEach(block1 -> {
			this.getBlocks().forEach(block2 -> {
				if (block2.getPosition().isUnder(block1.getPosition())) {
					block1.setP(false);
				}
			});
		});
		this.getBlocks().forEach(block -> {
			if (block.getB()) {
				result.add(block);
			}
		});
		return result;
	}

	//Setter for grid position
	public abstract void setPosition(GridPosition pos);
	//Adds four blocks to create a shape
	void addBlocksToShape(piece... blocks) {
		for (piece b : blocks) {
			this.blocks.add(b);
		}
	}
	//Rotate shape
	public abstract void Rotate();
	//Unrotate shape
	public abstract ArrayList<GridPosition> unRotate();
	//Can fall? method to make sure piece can go down one x unit
	public boolean canFall(GamePanel f) {
		return f.canFall(this);
	}
	//returns true for any space, out of four, that contains a block
	public boolean contains(piece block) {
		return block==one||block==two||block==three||block==four;
	}
	//Copy the shape to the UpNextShape class
	public UpNextShape moveToUpNext() {
		UpNextShape s = new UpNextShape(this);
		return s;
	}
	//getters and setters for encapsulation
	public piece getOne() {
		return one;
	}

	public piece getTwo() {
		return two;
	}

	public piece getThree() {
		return three;
	}

	public piece getFour() {
		return four;
	}

	public GridPosition getGridPos() {
		return gridPos;
	}

	public void setOne(piece one) {
		this.one = one;
	}

	public void setTwo(piece two) {
		this.two = two;
	}

	public void setThree(piece three) {
		this.three = three;
	}

	public void setFour(piece four) {
		this.four = four;
	}

	public void setOffSets(GridPosition[] offSets) {
		this.offSets = offSets;
	}

	public void setBlocks(ArrayList<piece> blocks) {
		this.blocks = blocks;
	}

	public void setGridPos(GridPosition gridPos) {
		this.gridPos = gridPos;
	}
}
