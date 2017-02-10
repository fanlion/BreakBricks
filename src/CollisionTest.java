import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.io.FileNotFoundException;

import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;


public class CollisionTest extends JFrame implements KeyListener {
	private Game game;
	public CollisionTest() throws FileNotFoundException {
		game = new Game();
		Thread thread = new Thread(game);
		thread.start();

		setTitle("碰撞检测");
		setResizable(false);
		setVisible(true);
		setPreferredSize(new Dimension(505, 668));
		pack();
		setDefaultCloseOperation(EXIT_ON_CLOSE);
		add(game);
		addKeyListener(this);
		JMenuBar menubar = new JMenuBar();
		JMenu help = new JMenu("帮助");
		JMenuItem author = new JMenuItem("作者");
		JMenuItem rule = new JMenuItem("规则");
		help.add(author);
		help.add(rule);
		menubar.add(help);
		add(menubar, BorderLayout.NORTH);

		author.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				JOptionPane.showMessageDialog(null, "作者：李繁 \n" +
						"时间：2015年3月15日\n" +
						"简介：打方块小游戏。\n" +
						"联系方式：QQ310488571\n" ,
						"作者信息", JOptionPane.INFORMATION_MESSAGE);
			}
		});

		rule.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				JOptionPane.showMessageDialog(null, "操作说明：上下箭头控制速度 \n" +
						"左右箭头控制小球运动角度，角度为数学坐标系正x轴逆时针增加\n" +
						"空格开始或暂停游戏,回车重新开始游戏\n" +
						"A和D控制档板左右移动，W和S控制档板长度变化\n" +
						"游戏说明：此程序为程序猿版打方块，主要为展示碰撞检测效果，并无传统中的细节\n" + 
						"无选关选项", 
						"帮助", JOptionPane.INFORMATION_MESSAGE);
			}
		});
	}

	public static void main(String[] args) throws FileNotFoundException {
		CollisionTest frame = new CollisionTest();
	}

	@Override
	public void keyTyped(KeyEvent e) {
	}

	@Override
	public void keyPressed(KeyEvent e) {
		int keyCode = e.getKeyCode();
		switch (keyCode) {
		case KeyEvent.VK_ENTER :
			try {
				game.initial();
			} catch (FileNotFoundException e1) {
				e1.printStackTrace();
			}
			break;
		case KeyEvent.VK_UP :
			game.ball.speedUp();
			break;
		case KeyEvent.VK_DOWN :
			game.ball.speedDown();
			break;
		case KeyEvent.VK_LEFT :
			game.ball.subAngle();
			break;
		case KeyEvent.VK_RIGHT :
			game.ball.addAngle();
			break;
		case KeyEvent.VK_A :
			game.board.moveToLeft();
			break;
		case KeyEvent.VK_D :
			game.board.moveToRight();
			break;
		case KeyEvent.VK_W :
			game.board.addWidht();
			break;
		case KeyEvent.VK_S :
			game.board.subWidth();
			break;
		case KeyEvent.VK_SPACE :
			game.changePauseState();
		default :
			break;
		}
	}

	@Override
	public void keyReleased(KeyEvent e) {
	}
}
