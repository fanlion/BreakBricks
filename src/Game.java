import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.Scanner;

import javax.swing.JPanel;

class Game extends JPanel implements Runnable {
	private ArrayList<Brick> bricks  = new ArrayList<Brick>(); //ש������
	public Board board; //����
	public Ball ball;  //С��
	private int score = 0; //�Ʒְ�
	private boolean pause = true; //�Ƿ���ͣ״̬,Ĭ�Ͼ�ֹ

	public Game() throws FileNotFoundException {
		readMatrix("matrix1.txt");
		board = new Board(210, 435);
		ball = new Ball(250, 430 , 45);
	}

	@Override
	public void run() {
		while (true) {
			try {
				Thread.sleep(ball.speed);
			} catch (Exception e) {
				break;
			}
			if (!pause) { //û�д�����ͣ״̬
				move();
				repaint();
			}
		}
	}

	//��ȡ�ı��ļ�����ʼ��ש������
	private void readMatrix(String fileName) throws FileNotFoundException {
		bricks.clear(); //�����ǰ�����е�����
		Scanner scanner = new Scanner(new File(fileName));
		while (scanner.hasNext()) {
			int x = scanner.nextInt();
			int y = scanner.nextInt();
			int flag = scanner.nextInt();
			bricks.add(new Brick(x, y, flag));
		}
		scanner.close();
	}

	//�������ש��
	private void bricksDetection() {
		Iterator<Brick> it = bricks.iterator();
		while (it.hasNext())
			rebound(brickDetection(it.next()));
	}

	//���С���Ƿ�����ש��,��������ײ��
	private int brickDetection(Brick brick) {
		//С����һ������
		if (brick.flag > 0) {
			int bx1 = brick.x, by1 = brick.y; //ש�����Ͻ�����
			int bx2 = bx1 + brick.width, by2 = by1 + brick.height; //ש�����½�����
			//��ש��������ײ
			if (((ball.y- by1) * (ball.y + ball.radius / 2  - by1) < 0) && (ball.x >= bx1) && (ball.x <= bx2)) {
				brick.subFlag(); //�ܱ���ײ�Ĵ�����һ
				if(brick.getFlag() == 0)
					score ++; //����һ��
				return 3; 
			}
			//��ש��������ײ
			else if (((ball.x - bx2) * (ball.x - ball.radius / 2 - bx2) < 0) && (ball.y >= by1) && (ball.y <= by2)) {
				brick.subFlag(); //�ܱ���ײ�Ĵ�����һ
				if(brick.getFlag() == 0)
					score ++; //����һ��
				return 4; 
			}
			//��ש��������ײ
			else if (((ball.y - by2) * (ball.y - ball.radius / 2 - by2) < 0) && (ball.x >= bx1) && (ball.x <= bx2)) {
				brick.subFlag(); //�ܱ���ײ�Ĵ�����һ
				if(brick.getFlag() == 0)
					score ++; //����һ��
				return 1; 
			}
			//��ש��������ײ
			else if (((ball.x - bx1) * (ball.x + ball.radius / 2 - bx1) < 0) && (ball.y >= by1) && (ball.y <= by2)) {
				brick.subFlag(); //�ܱ���ײ�Ĵ�����һ
				if(brick.getFlag() == 0)
					score ++; //����һ��
				return 2; 
			}
			//û�з�����ײ
			else return 0; 
		}
		else return 0;
	}

	//���С����ǽ��������������ײ��
	private int boundaryDetection() {
		int x1 = (int) Math.round((ball.x + ball.moveSpan * Math.cos(Math.toRadians(ball.angle))));
		int y1 = (int) Math.round((ball.y - ball.moveSpan * Math.sin(Math.toRadians(ball.angle))));
		if ((ball.y - 0) * (y1 - ball.radius / 2 - 0) < 0)  //�ϱ߽�
			return 1;

		else if ((ball.x - 500) * (x1 + ball.radius / 2 - 500) < 0)//�ұ߽�
			return 2;

		else if ((ball.y - 600) * (y1 + ball.radius / 2 - 600) < 0) //�±߽�
			return 3;

		else if ((ball.x - 0) * (x1 - ball.radius / 2 - 0) < 0)  //��߽�
			return 4;

		else return 0;  //û�з�����ײ
	}

	//���С���뵵���Ƿ���ײ�������ײ�򷴵�
	private void boardRebound() {
		int x1 = board.x, x2 = board.x + board.width; //�������Ҷ�x����
		//�ж��Ƿ���ײ
		if (((ball.y- board.y) * (ball.y + ball.radius / 2  - board.y) < 0) && (ball.x >= x1) && (ball.x <= x2 )) {
			if (ball.angle == 270) //��ֱ��ײ
				ball.angle = (ball.angle + 180) % 360;
			else 
				ball.angle = (360 - ball.angle) % 360;
		}
	}

	//����������������ײ���������з���,�Ƕȷ�Χ��0~360��֮��
	private void rebound(int type) {
		switch (type) {
		case 1 : //�ϱ߽�
			if (ball.angle == 90) //��ֱ��ײ
				ball.angle = (ball.angle + 180) % 360;
			else  
				ball.angle = (360 - ball.angle) % 360;
			break;
		case 2 : //�ұ߽�
			if (ball.angle == 0)  //��ֱ��ײ
				ball.angle = (ball.angle + 180) % 360;
			if (ball.angle < 90 && ball.angle > 0)
				ball.angle = (180 - ball.angle) % 360;
			if (ball.angle > 270 && ball.angle < 360)
				ball.angle = (540 - ball.angle) % 360;
			break; 
		case 3 : //���±߽�
			if (ball.angle == 270) //��ֱ��ײ
				ball.angle = (ball.angle + 180) % 360;
			else
				ball.angle = (360 - ball.angle) % 360;
			break;
		case 4 : //��߽�
			if (ball.angle == 180)  //��ֱ��ײ
				ball.angle = (ball.angle + 180) % 360;
			if (90 < ball.angle && ball.angle < 180) 
				ball.angle = (180 - ball.angle) % 360;
			if (180 < ball.angle && ball.angle < 270 )
				ball.angle = (540 - ball.angle) % 360; 
			break;
		default :
			break;
		}
	}

	//С���ƶ�����
	private void move() {
		rebound(boundaryDetection()); //�߽���ײ��Ⲣ����
		bricksDetection(); //ש����ײ��Ⲣ����
		boardRebound(); //���С���뵵����ײ�������ײ�򷴵�
		//rebound(brickDetection());
		ball.x = (int) Math.round((ball.x + ball.moveSpan * Math.cos(Math.toRadians(ball.angle))));
		ball.y = (int) Math.round((ball.y - ball.moveSpan * Math.sin(Math.toRadians(ball.angle))));
	}

	//���ƺ���
	protected void paintComponent(Graphics g) {
		super.paintComponent(g);
		Graphics2D g2D = (Graphics2D) g;
		//���Ʊ���
		g.setColor(Color.GRAY);
		g.fillRect(0, 0, 500, 600);
		//����С�����꣬�������С���˶�
		g.setColor(Color.BLUE);
		//����С������
		g.drawString("(" + ball.x + "," + ball.y + ") " + ball.angle, ball.x + 10, ball.y + 10);

		drawBricks(g); //��������ש��
		drawBall(g2D);  //����С��
		drawBoard(g);  //�����ƶ���
		g.setColor(Color.RED);
		g.drawString(score + " ��", 240, 615); //���ƼƷְ�
	}
	//��ʼ����Ϸ
	public void initial() throws FileNotFoundException {
		readMatrix("matrix1.txt"); //��ʼ����Ϸʱ���ڳ���һֱ�ڱ������������ڳ�ʼ������ʱһֱ��
		//����Ԫ�أ���˻�����׳�java.util.ConcurrentModificationException
		//�쳣�Ŀ��ܣ���������������ٴ���һ���߳�ר��������ȡ�ļ��ͳ�ʼ�������˴������޸�
		pause = true; //Ĭ����Ϸ��ͣ
		ball.x = 250;
		ball.y = 430;
		ball.speed = 10;
		ball.angle = 45;
		board.x = 210;
		board.y = 435;
		board.width = 80;
	}

	//������
	private void drawBall(Graphics2D g) {
		g.setColor(Color.RED);
		//ȥ��С�����ػ���ʹС��߽�⻬
		g.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON );
		g.fillOval(ball.x - ball.radius / 2, ball.y - ball.radius / 2, ball.radius, ball.radius);
	}

	//����ש�飬����ש���������������ɫ
	private void drawBrick(Graphics g, Brick b) {
		if (b.flag == 1)
			g.setColor(Color.ORANGE);
		else if (b.flag == 2)
			g.setColor(Color.pink);
		else if (b.flag == 3)
			g.setColor(Color.MAGENTA);

		if (b.flag > 0) {
			g.fillRect(b.x, b.y, b.width, b.height);
		}
	}

	//�ı���ͣ״̬
	public void changePauseState() {
		pause = !pause;
	}

	//���Ƶ���
	private void drawBoard(Graphics g) {
		g.setColor(Color.DARK_GRAY);
		g.fillRect(board.x, board.y, board.width, board.height);
	}

	//��������ש��
	private void drawBricks(Graphics g) {
		Iterator<Brick> iterator = bricks.iterator();
		while (iterator.hasNext()) {
			Brick brick = iterator.next();
			if (brick.getFlag() > 0)  //��û��ʧ��ש����Ի���
				drawBrick(g, brick);
		}
	}

}
