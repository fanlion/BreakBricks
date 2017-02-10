
class Ball {

	public int x;
	public int y;
	public int moveSpan = 2; //小球每次移动的距离
	public int radius = 10; //直径，这里命名错了
	public double angle = 45;
	public long speed = 10;
	public double speedChangeRate = 0.75;
	
	public Ball(int x, int y, double angle) {
		this.x = x;
		this.y = y;
		this.angle = angle;
	}

	public double getAngle() {
		return angle;
	}
	public void setAngle(double angle) {
		this.angle = angle;
	}

	//加速函数
	public void speedUp() {
		if (speed >= 5)
			speed *= speedChangeRate;
	}
	//减速函数
	public void speedDown() {
		if (speed <= 400)
			speed /= speedChangeRate;
	}

	//增加角度
	public void addAngle() {
		angle = (angle + 5) % 360;
		if (angle < 0)
			angle = 360 + angle; //保持正角
	}

	//减少角度
	public void subAngle() {
		angle = (angle - 5) % 360;
		if (angle < 0)
			angle = 360 + angle; //保持正角
	}

}
