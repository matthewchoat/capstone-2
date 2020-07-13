//Abstract Shape class that Shapes will inherit from and eventually will become individual Tetrominos
package tetrominos;

import java.util.ArrayList;

import components.Block;
import components.GamePanel;
import components.GridPosition;
import javafx.scene.paint.Color;

public abstract class AbstractShape {
	//Abstract shapes have 4 Blocks
	private Block one, two, three, four;
	//Abstract shapes have a GridPosition with offsets
	private GridPosition[] offSets = new GridPosition[4];
	//Array list of all 4 Blocks to make a shape
	private ArrayList<Block> blocks = new ArrayList<Block>();
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
	public ArrayList<Block> getBlocks() {
		return blocks;
	}
	public ArrayList<Block> getBlockArray() {
		ArrayList<Block> result = new ArrayList<Block>();
		for (Block b : this.getBlocks()) {
			b.setB(true);
		}
		this.getBlocks().forEach(block1 -> {
			this.getBlocks().forEach(block2 -> {
				if (block2.getPosition().isRight(block1.getPosition())) {
					block1.setB(false);
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
	public ArrayList<Block> getLeft() {
		ArrayList<Block> result = new ArrayList<Block>();
		for (Block b : this.getBlocks()) {
			b.setB(true);
		}
		this.getBlocks().forEach(block1 -> {
			this.getBlocks().forEach(block2 -> {
				if (block2.getPosition().isLeft(block1.getPosition())) {
					block1.setB(false);
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
	public ArrayList<Block> getBottomRow() {
		ArrayList<Block> result = new ArrayList<Block>();
		for (Block b : this.getBlocks()) {
			b.setB(true);
		}
		this.getBlocks().forEach(block1 -> {
			this.getBlocks().forEach(block2 -> {
				if (block2.getPosition().isUnder(block1.getPosition())) {
					block1.setB(false);
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
	void addBlocksToShape(Block... blocks) {
		for (Block b : blocks) {
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
	public boolean contains(Block block) {
		return block==one||block==two||block==three||block==four;
	}
	//Copy the shape to the UpNextShape class
	public UpNextShape moveToUpNext() {
		UpNextShape s = new UpNextShape(this);
		return s;
	}
	//getters and setters for encapsulation
	public Block getOne() {
		return one;
	}

	public Block getTwo() {
		return two;
	}

	public Block getThree() {
		return three;
	}

	public Block getFour() {
		return four;
	}

	public GridPosition getGridPos() {
		return gridPos;
	}

	public void setOne(Block one) {
		this.one = one;
	}

	public void setTwo(Block two) {
		this.two = two;
	}

	public void setThree(Block three) {
		this.three = three;
	}

	public void setFour(Block four) {
		this.four = four;
	}

	public void setOffSets(GridPosition[] offSets) {
		this.offSets = offSets;
	}

	public void setBlocks(ArrayList<Block> blocks) {
		this.blocks = blocks;
	}

	public void setGridPos(GridPosition gridPos) {
		this.gridPos = gridPos;
	}
}
