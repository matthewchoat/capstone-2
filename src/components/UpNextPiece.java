package components;

import javafx.scene.paint.Color;

public class UpNextPiece extends Piece {

	public UpNextPiece(GridPosition pos) {
		super(pos);
		setFill(Color.SADDLEBROWN);
	}
	
	@Override
	public void setPosition(GridPosition pos) {
		setTranslateX(pos.getX() * getSIZE() + 2);
		setTranslateY(pos.getY() * getSIZE() + 2);
		GridPosition position = pos;
	}
}
