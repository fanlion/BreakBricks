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

		setTitle("��ײ���");
		setResizable(false);
		setVisible(true);
		setPreferredSize(new Dimension(505, 668));
		pack();
		setDefaultCloseOperation(EXIT_ON_CLOSE);
		add(game);
		addKeyListener(this);
		JMenuBar menubar = new JMenuBar();
		JMenu help = new JMenu("����");
		JMenuItem author = new JMenuItem("����");
		JMenuItem rule = new JMenuItem("����");
		help.add(author);
		help.add(rule);
		menubar.add(help);
		add(menubar, BorderLayout.NORTH);

		author.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				JOptionPane.showMessageDialog(null, "���ߣ�� \n" +
						"ʱ�䣺2015��3��15��\n" +
						"��飺�򷽿�С��Ϸ��\n" +
						"��ϵ��ʽ��QQ310488571\n" ,
						"������Ϣ", JOptionPane.INFORMATION_MESSAGE);
			}
		});

		rule.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				JOptionPane.showMessageDialog(null, "����˵�������¼�ͷ�����ٶ� \n" +
						"���Ҽ�ͷ����С���˶��Ƕȣ��Ƕ�Ϊ��ѧ����ϵ��x����ʱ������\n" +
						"�ո�ʼ����ͣ��Ϸ,�س����¿�ʼ��Ϸ\n" +
						"A��D���Ƶ��������ƶ���W��S���Ƶ��峤�ȱ仯\n" +
						"��Ϸ˵�����˳���Ϊ����Գ��򷽿飬��ҪΪչʾ��ײ���Ч�������޴�ͳ�е�ϸ��\n" + 
						"��ѡ��ѡ��", 
						"����", JOptionPane.INFORMATION_MESSAGE);
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
