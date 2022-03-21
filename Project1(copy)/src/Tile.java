
public class Tile {
	private int row, col;
	private char c;
	Tile prev;
	
	public Tile(char character, int newRow, int column) {
		c = character;
		row = newRow;
		col = column;
	}
	
	public int getRow() {
		return row;
	}

	public void setRow(int row) {
		this.row = row;
	}

	public int getCol() {
		return col;
	}

	public void setCol(int col) {
		this.col = col;
	}

	public char getC() {
		return c;
	}

	public void setC(char c) {
		this.c = c;
	}
	
	public String getTile() { //can be a toString Method
		return c +" "+ row +" "+ col;
	}
	
}
