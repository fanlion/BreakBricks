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
	private ArrayList<Brick> bricks  = new ArrayList<Brick>(); //砖块链表
	public Board board; //档板
	public Ball ball;  //小球
	private int score = 0; //计分板
	private boolean pause = true; //是否暂停状态,默认静止

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
			if (!pause) { //没有处于暂停状态
				move();
				repaint();
			}
		}
	}

	//读取文本文件并初始化砖块链表
	private void readMatrix(String fileName) throws FileNotFoundException {
		bricks.clear(); //清除以前链表中的内容
		Scanner scanner = new Scanner(new File(fileName));
		while (scanner.hasNext()) {
			int x = scanner.nextInt();
			int y = scanner.nextInt();
			int flag = scanner.nextInt();
			bricks.add(new Brick(x, y, flag));
		}
		scanner.close();
	}

	//检测所有砖块
	private void bricksDetection() {
		Iterator<Brick> it = bricks.iterator();
		while (it.hasNext())
			rebound(brickDetection(it.next()));
	}

	//检测小球是否碰到砖块,并返回碰撞面
	private int brickDetection(Brick brick) {
		//小球下一步坐标
		if (brick.flag > 0) {
			int bx1 = brick.x, by1 = brick.y; //砖块左上角坐标
			int bx2 = bx1 + brick.width, by2 = by1 + brick.height; //砖块右下角坐标
			//与砖块上面相撞
			if (((ball.y- by1) * (ball.y + ball.radius / 2  - by1) < 0) && (ball.x >= bx1) && (ball.x <= bx2)) {
				brick.subFlag(); //能被碰撞的次数减一
				if(brick.getFlag() == 0)
					score ++; //增加一分
				return 3; 
			}
			//与砖块右面相撞
			else if (((ball.x - bx2) * (ball.x - ball.radius / 2 - bx2) < 0) && (ball.y >= by1) && (ball.y <= by2)) {
				brick.subFlag(); //能被碰撞的次数减一
				if(brick.getFlag() == 0)
					score ++; //增加一分
				return 4; 
			}
			//与砖块下面相撞
			else if (((ball.y - by2) * (ball.y - ball.radius / 2 - by2) < 0) && (ball.x >= bx1) && (ball.x <= bx2)) {
				brick.subFlag(); //能被碰撞的次数减一
				if(brick.getFlag() == 0)
					score ++; //增加一分
				return 1; 
			}
			//与砖块左面相撞
			else if (((ball.x - bx1) * (ball.x + ball.radius / 2 - bx1) < 0) && (ball.y >= by1) && (ball.y <= by2)) {
				brick.subFlag(); //能被碰撞的次数减一
				if(brick.getFlag() == 0)
					score ++; //增加一分
				return 2; 
			}
			//没有发生碰撞
			else return 0; 
		}
		else return 0;
	}

	//检测小球与墙壁碰，并返回碰撞面
	private int boundaryDetection() {
		int x1 = (int) Math.round((ball.x + ball.moveSpan * Math.cos(Math.toRadians(ball.angle))));
		int y1 = (int) Math.round((ball.y - ball.moveSpan * Math.sin(Math.toRadians(ball.angle))));
		if ((ball.y - 0) * (y1 - ball.radius / 2 - 0) < 0)  //上边界
			return 1;

		else if ((ball.x - 500) * (x1 + ball.radius / 2 - 500) < 0)//右边界
			return 2;

		else if ((ball.y - 600) * (y1 + ball.radius / 2 - 600) < 0) //下边界
			return 3;

		else if ((ball.x - 0) * (x1 - ball.radius / 2 - 0) < 0)  //左边界
			return 4;

		else return 0;  //没有发生碰撞
	}

	//检测小球与档板是否碰撞，如果碰撞则反弹
	private void boardRebound() {
		int x1 = board.x, x2 = board.x + board.width; //档板左右端x坐标
		//判断是否碰撞
		if (((ball.y- board.y) * (ball.y + ball.radius / 2  - board.y) < 0) && (ball.x >= x1) && (ball.x <= x2 )) {
			if (ball.angle == 270) //垂直碰撞
				ball.angle = (ball.angle + 180) % 360;
			else 
				ball.angle = (360 - ball.angle) % 360;
		}
	}

	//反弹函数，根据碰撞类型来进行反弹,角度范围在0~360度之间
	private void rebound(int type) {
		switch (type) {
		case 1 : //上边界
			if (ball.angle == 90) //垂直碰撞
				ball.angle = (ball.angle + 180) % 360;
			else  
				ball.angle = (360 - ball.angle) % 360;
			break;
		case 2 : //右边界
			if (ball.angle == 0)  //垂直碰撞
				ball.angle = (ball.angle + 180) % 360;
			if (ball.angle < 90 && ball.angle > 0)
				ball.angle = (180 - ball.angle) % 360;
			if (ball.angle > 270 && ball.angle < 360)
				ball.angle = (540 - ball.angle) % 360;
			break; 
		case 3 : //与下边界
			if (ball.angle == 270) //垂直碰撞
				ball.angle = (ball.angle + 180) % 360;
			else
				ball.angle = (360 - ball.angle) % 360;
			break;
		case 4 : //左边界
			if (ball.angle == 180)  //垂直碰撞
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

	//小球移动函数
	private void move() {
		rebound(boundaryDetection()); //边界碰撞检测并反弹
		bricksDetection(); //砖块碰撞检测并反弹
		boardRebound(); //检测小球与档板碰撞，如果碰撞则反弹
		//rebound(brickDetection());
		ball.x = (int) Math.round((ball.x + ball.moveSpan * Math.cos(Math.toRadians(ball.angle))));
		ball.y = (int) Math.round((ball.y - ball.moveSpan * Math.sin(Math.toRadians(ball.angle))));
	}

	//绘制函数
	protected void paintComponent(Graphics g) {
		super.paintComponent(g);
		Graphics2D g2D = (Graphics2D) g;
		//绘制背景
		g.setColor(Color.GRAY);
		g.fillRect(0, 0, 500, 600);
		//绘制小球坐标，坐标跟随小球运动
		g.setColor(Color.BLUE);
		//绘制小球坐标
		g.drawString("(" + ball.x + "," + ball.y + ") " + ball.angle, ball.x + 10, ball.y + 10);

		drawBricks(g); //绘制所有砖块
		drawBall(g2D);  //绘制小球
		drawBoard(g);  //绘制移动板
		g.setColor(Color.RED);
		g.drawString(score + " 分", 240, 615); //绘制计分板
	}
	//初始化游戏
	public void initial() throws FileNotFoundException {
		readMatrix("matrix1.txt"); //初始化游戏时由于程序一直在遍历链表，但是在初始化链表时一直在
		//插入元素，因此会存在抛出java.util.ConcurrentModificationException
		//异常的可能，解决方案可以是再创建一个线程专门用来读取文件和初始化链表，此处不做修改
		pause = true; //默认游戏暂停
		ball.x = 250;
		ball.y = 430;
		ball.speed = 10;
		ball.angle = 45;
		board.x = 210;
		board.y = 435;
		board.width = 80;
	}

	//绘制球
	private void drawBall(Graphics2D g) {
		g.setColor(Color.RED);
		//去除小球像素化，使小球边界光滑
		g.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON );
		g.fillOval(ball.x - ball.radius / 2, ball.y - ball.radius / 2, ball.radius, ball.radius);
	}

	//绘制砖块，根据砖块的类型来绘制颜色
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

	//改变暂停状态
	public void changePauseState() {
		pause = !pause;
	}

	//绘制档板
	private void drawBoard(Graphics g) {
		g.setColor(Color.DARK_GRAY);
		g.fillRect(board.x, board.y, board.width, board.height);
	}

	//绘制所有砖块
	private void drawBricks(Graphics g) {
		Iterator<Brick> iterator = bricks.iterator();
		while (iterator.hasNext()) {
			Brick brick = iterator.next();
			if (brick.getFlag() > 0)  //还没消失的砖块可以绘制
				drawBrick(g, brick);
		}
	}

}
