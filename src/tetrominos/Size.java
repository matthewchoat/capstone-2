//Small class to assist in determining if a tetrimino will fit into a specific space
package tetrominos;

public class Size {
	int width, height;

	public Size(int w, int h) {
		width = w;
		height = h;
	}

	public int getHeight() {
		return height;
	}

	public int getWidth() {
		return width;
	}
}
