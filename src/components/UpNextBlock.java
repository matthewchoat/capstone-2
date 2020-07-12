package components;

import javafx.scene.paint.Color;

public class UpNextBlock extends Block {

	public UpNextBlock(GridPosition pos) {
		super(pos);
		setFill(Color.SADDLEBROWN);
	}
	
	@Override
	public void setPosition(GridPosition pos) {
		setTranslateX(pos.getX() * SIZE + 2);
		setTranslateY(pos.getY() * SIZE + 2);
		position = pos;
	}
}
