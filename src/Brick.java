//砖块
class Brick {
	//砖块左上角点坐标
	public int x;
	public int y;
	public int flag = 3; //小球类型，数字代表可以碰击的次数
	//砖块宽度和高度
	public int width = 30;
	public int height = 10;

	

	public Brick(int x, int y) {
		this.x = x;
		this.y = y;
	}
	public Brick(int x, int y, int flag) {
		this.x = x;
		this.y = y;
		this.flag = flag;
	}

	public void subFlag() {
		flag--;
	}
	public void setFlag(int flag) {
		this.flag = flag;
	}
	public int getFlag() {
		return flag;
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
}