public class Board {
	public int x;
	public int y;
	public int width = 80;
	public int height = 5;
	public Board(int x, int y) {
		this.x = x;
		this.y = y;
	}

	public void setX(int x) {
		this.x = x;
	}
	public int getX() {
		return x;
	}
	public void setY(int y) {
		this.y = y;
	}
	public int getY() {
		return y;
	}

	public void moveToLeft() {
		if (x >= 0) 
			x -= 15;
	}
	public void moveToRight() {
		if (x <= 500 - width) 
			x += 15;
	}
	public void addWidht() {
		if (width <= 150)
			width += 10;
	}
	public void subWidth() {
		if (width >= 80)
			width -= 10;
	}

}
