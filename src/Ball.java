
class Ball {

	public int x;
	public int y;
	public int moveSpan = 2; //С��ÿ���ƶ��ľ���
	public int radius = 10; //ֱ����������������
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

	//���ٺ���
	public void speedUp() {
		if (speed >= 5)
			speed *= speedChangeRate;
	}
	//���ٺ���
	public void speedDown() {
		if (speed <= 400)
			speed /= speedChangeRate;
	}

	//���ӽǶ�
	public void addAngle() {
		angle = (angle + 5) % 360;
		if (angle < 0)
			angle = 360 + angle; //��������
	}

	//���ٽǶ�
	public void subAngle() {
		angle = (angle - 5) % 360;
		if (angle < 0)
			angle = 360 + angle; //��������
	}

}
