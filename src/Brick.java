//ש��
class Brick {
	//ש�����Ͻǵ�����
	public int x;
	public int y;
	public int flag = 3; //С�����ͣ����ִ�����������Ĵ���
	//ש���Ⱥ͸߶�
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