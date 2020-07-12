package components;

public class GridPosition {
	private int x, y;

	public GridPosition(int x, int y) {
		this.x = x;
		this.y = y;
	}

	public int getX() {
		return x;
	}

	public int getY() {
		return y;
	}

	public GridPosition up() {
		return (new GridPosition(getX(), getY() - 1));
	}

	public GridPosition down() {
		return (new GridPosition(getX(), getY() + 1));
	}

	public GridPosition right() {
		return (new GridPosition(getX() + 1, getY()));
	}

	public GridPosition left() {
		return (new GridPosition(getX() - 1, getY()));
	}

	public boolean isUnder(GridPosition p) {
		return this.getX() == p.getX() && this.getY() == p.getY() + 1;
	}

	public boolean isLeft(GridPosition p) {
		return this.getX() == p.getX() - 1 && this.getY() == p.getY();
	}

	public boolean isRight(GridPosition p) {
		return this.getX() == p.getX() + 1 && this.getY() == p.getY();
	}

	public GridPosition sub(GridPosition pos) {
		return new GridPosition(getX() - pos.getX(), getY() - pos.getY());
	}

	public GridPosition add(GridPosition pos) {
		return new GridPosition(getX() + pos.getX(), getY() + pos.getY());
	}

	public boolean equals(GridPosition p) {
		return getX() == p.getX() && getY() == p.getY();
	}

	public String toString() {
		return "Position : X = " + x + "\tY = " + y;
	}

}
